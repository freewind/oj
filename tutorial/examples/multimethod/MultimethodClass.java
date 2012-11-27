/*
 * MultimethodClass.oj
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package examples.multimethod;


import java.util.*;
import openjava.mop.*;
import openjava.ptree.*;


/**
 * The class <code>MultimethodClass</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: MultimethodClass.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class MultimethodClass extends OJClass
{
    private static final String MULTIFORM = "multiform";
    private static final String RUNTIME_EXCEPTION
	= "examples.multimethod.NoAddaptableMethodException";

    public void translateDefinition() throws MOPException {
	Hashtable table = new Hashtable();
	OJMethod[] m = getDeclaredMethods();

	for (int i = 0; i < m.length; ++i) {
	    if (! m[i].getModifiers().has( MULTIFORM ))  continue;
	    ParameterSetTree set
		= (ParameterSetTree) table.get( makeHashkey( m[i] ) );
	    if (set != null) {
		set.add( m[i] );
	    } else {
		set = new ParameterSetTree( m[i].getParameterTypes(), m[i] );
	    }
	    table.put( makeHashkey( m[i] ), set );
	}

	Enumeration ite = table.elements();
	while (ite.hasMoreElements()) {
	    ParameterSetTree set = (ParameterSetTree) ite.nextElement();
	    modifyToBeGeneric( set );
	    System.out.println( set );
	}
    }

    private void modifyToBeGeneric( ParameterSetTree set )
	throws MOPException
    {
	if (! set.hasChildren())  return;

	OJMethod method = set.getValue();
	if (method == null)  addMethod( (method = genSelector( set )) );

	StatementList result = new StatementList();
	ParameterSetTree[] children = set.getChildren();
	for (int i = 0; i < children.length; ++i) {
	    Expression cond
		= makeCond( method, children[i].getParameterTypes() );
	    StatementList ifbody
		= makeQualifiedCall( method, children[i].getParameterTypes() );
	    result.add( new IfStatement( cond, ifbody ) );
	    modifyToBeGeneric( children[i] );
	}
	result.addAll( method.getBody() );
	method.addExceptionType( OJClass.forName( RUNTIME_EXCEPTION ) );
	method.setBody( result );
    }

    /* generates a method only for testing params to invoke another */
    private OJMethod genSelector( ParameterSetTree set )
	throws MOPException
    {
	/* representer of other features such as throwing exceptions */
	OJMethod rep = pickupOne( set );
	return new OJMethod(
	    this, rep.getModifiers(), rep.getReturnType(), rep.getName(),
	    set.getParameterTypes(),  rep.getExceptionTypes(),
	    makeStatementList( "throw new " + RUNTIME_EXCEPTION + "();" )
	    );
    }

    private static Expression makeCond( OJMethod holder, OJClass[] ptypes )
	throws MOPException
    {
	String[] params = holder.getParameters();
	StringBuffer strbuf = new StringBuffer();
	for (int i = 0; i < ptypes.length; ++i) {
	    if (i != 0)  strbuf.append( " && " );
	    strbuf.append( "(" );
	    strbuf.append( params[i] );
	    strbuf.append( " instanceof " );
	    strbuf.append( ptypes[i].getName() );
	    strbuf.append( ")" );
	}
	return makeExpression( holder.getEnvironment(), strbuf.toString() );
    }

    private static StatementList
    makeQualifiedCall( OJMethod holder, OJClass[] ptypes )
	throws MOPException
    {
	String[] params = holder.getParameters();
	StringBuffer strbuf = new StringBuffer();
	strbuf.append( "this." ).append( holder.getName() ).append( "( " );
	for (int i = 0; i < ptypes.length; ++i) {
	    if (i != 0)  strbuf.append( ", " );
	    strbuf.append( "(" ).append( ptypes[i].getName() ).append( ") " );
	    strbuf.append( params[i] );
	}
	strbuf.append( " )" );
	Expression mtdcall
	    = makeExpression( holder.getEnvironment(), strbuf.toString() );

	StatementList result = new StatementList();
	if (holder.getReturnType() == OJSystem.VOID) {
	    result.add( new ExpressionStatement( mtdcall ) );
	    result.add( new ReturnStatement() );
	} else {
	    result.add( new ReturnStatement( mtdcall ) );
	}
	return result;
    }

    private static String makeHashkey( OJMethod method ) {
        return method.getReturnType().getName() + "%" + method.getName() +
	    "%" + method.getParameterTypes().length;
    }

    private static OJMethod pickupOne( ParameterSetTree set ) {
	while (set.getChildren().length != 0)  set = set.getChildren()[0];
	return set.getValue();
    }

    /* syntax extension */

    public static boolean isRegisteredModifier( String keyword ) {
        return keyword.equals( MULTIFORM );
    }

    /* constructors */

    public MultimethodClass( Environment outer_env, OJClass declarer,
			     ClassDeclaration ptree )
    {
	super( outer_env, declarer, ptree );
    }

    public MultimethodClass( Class javaclass, MetaInfo minfo ) {
        super( javaclass, minfo );
    }

}
