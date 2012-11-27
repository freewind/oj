/*
 * ParameterSetTree.java
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package examples.multimethod;


import java.util.*;
import java.io.*;
import openjava.mop.*;
import openjava.ptree.*;


/**
 * The class <code>ParameterSetTree</code> sorts parameters set of method
 * and hold it.
 *
 * @author   Michiaki Tatsubori
 * @see openjava.mop.OJMethod
 */
public class ParameterSetTree implements Cloneable
{
    private OJClass[] params;
    private OJMethod value = null;
    private ParameterSetTree[] children = new ParameterSetTree[0];

    public ParameterSetTree( OJClass[] params, OJMethod value ) {
	this.params = params;
	this.value = value;
    }

    ParameterSetTree( OJClass[] params ) {
        this( params, null );
    }

    public OJClass[] getParameterTypes() {
	OJClass[] result = new OJClass[params.length];
	System.arraycopy( params, 0, result, 0, params.length );
	return result;
    }

    public OJMethod getValue() {
	return value;
    }

    public ParameterSetTree[] getChildren() {
	if (children.length == 0)  return children;
	ParameterSetTree[] result = new ParameterSetTree[children.length];
	System.arraycopy( children, 0, result, 0, children.length );
	return result;
    }
    
    public boolean hasChildren() {
	return children.length != 0;
    }

    public String toString() {
        StringWriter str_writer = new StringWriter();
        PrintWriter out = new PrintWriter( str_writer );
	writeState( out );
        out.flush();
        return str_writer.toString();
    }

    public void writeState( PrintWriter out ) {
	writeState( out, 0 );
    }

    private void writeState( PrintWriter out, int level ) {
	for (int i = 0; i < level; ++i)  out.print( "  " );

	out.print( "{ ");
	out.print( params[0].getName() );
	for (int i = 1; i < params.length; ++i) {
	    out.print( "," );
	    out.print( params[i].getName() );
	}
	out.print( " } " );
	out.println( value != null );

	for (int i = 0; i < children.length; ++i) {
	    children[i].writeState( out, level + 1 );
	}
    }

    /* must adjust tree */
    OJMethod add( OJMethod method ) {
        OJClass[] params = method.getParameterTypes();

        if (this.isSameAs( params )) {
	    /* exactly same */
	    OJMethod result = this.value;
	    this.value = method;
	    return result;
	}
	if (this.isAssignableFrom( params )) {
	    /* a subset of this */
	    for (int i = 0; i < children.length; ++i) {
		if (children[i].isAssignableFrom( params )) {
		    /* a subset of one of children */
		    return children[i].add( method );
		}
	    }
	    addChild( new ParameterSetTree( params, method ) );
	    return null;
	}

	/* a superset or an independent order set of this */
	ParameterSetTree copy = shallowCopy();
	OJClass[] common = commonBaseTypes( this.params, params );
	this.params = common;
	this.value = null;
	if (isAssignable( params, common )) {
	    /* a superset */
	    this.params = params;
	    this.value = method;
	    this.children = new ParameterSetTree[]{ copy };
	} else {
	    /* a subset of the common superset */
	    this.params = common;
	    this.value = null;
	    this.children = new ParameterSetTree[]{
		copy,
		new ParameterSetTree( params, method ) };
	}
	return null;
    }

    private ParameterSetTree shallowCopy() {
	try {
	    return (ParameterSetTree) clone();
	} catch ( CloneNotSupportedException e ){
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Childrens must not have any smaller common set than this set.
     */
    private void addChild( ParameterSetTree newbie ) {
	int tobelong = 0;
	OJClass[] subcommon = null;

        for (; tobelong < children.length; ++tobelong) {
	    subcommon = commonBaseTypes( newbie.params,
					 children[tobelong].params );
	    if (! isSameAs( subcommon ))  break;
	}

	if (tobelong == children.length) {
	    /* simply add */
	    ParameterSetTree[] result
		= new ParameterSetTree[children.length + 1];
	    System.arraycopy( children, 0, result, 0, children.length );
	    result[children.length] = newbie;
	    children = result;
	    return;
	}

	/* at least two has a sub common signature */
	if (children[tobelong].isSameAs( subcommon )) {
	    /* newbie is a subset */
	    children[tobelong].addChild( newbie );
	} else {
	    ParameterSetTree common_set = new ParameterSetTree( subcommon );
	    common_set.addChild( children[tobelong] );
	    common_set.addChild( newbie );
	    children[tobelong] = common_set;
	}
    }

    public boolean isAssignableFrom( OJClass[] params ) {
	return isAssignable( this.params, params );
    }

    public boolean isSameAs( OJClass[] params ) {
        if (this.params.length != params.length)  return false;
	for (int i = 0; i < this.params.length; ++i) {
	    if (this.params[i] != params[i])  return false;
	}
	return true;
    }

    private static boolean isAssignable( OJClass[] p1, OJClass[] p2 ) {
        if (p1.length != p2.length)  return false;
	for (int i = 0; i < p1.length; ++i) {
	    if (! p1[i].isAssignableFrom( p2[i] ))  return false;
	}
	return true;
    }
        
    private static OJClass[] commonBaseTypes( OJClass[] a, OJClass b[] ) {
	OJClass[] result = new OJClass[a.length];
	for (int i = 0; i < a.length; ++i) {
	    result[i] = commonBaseType( a[i], b[i] );
	}
	return result;
    }

    /**
     * this is also available in the class <code>Signature</code>
     */
    private static OJClass commonBaseType( OJClass a, OJClass b ) {
        if (a.isAssignableFrom( b ))  return a;
        if (b.isAssignableFrom( a ))  return b;
        return commonBaseType( a.getSuperclass(), b.getSuperclass() );
    }

}
