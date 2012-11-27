/*
 * ClosedEnvironment.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.mop;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;

import openjava.tools.DebugOut;


/**
 * The class <code>ClosedEnvironment</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: ClosedEnvironment.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class ClosedEnvironment extends Environment
{
    protected Hashtable table = new Hashtable();
    protected Hashtable symbol_table = new Hashtable();

    /**
     *
     *
     * @param
     * @return
     * @exception
     * @see java.lang.Object
     */
    public ClosedEnvironment( Environment env ) {
        parent = env;
    }

    public String toString() {
        StringWriter str_writer = new StringWriter();
        PrintWriter out = new PrintWriter( str_writer );

	out.println( "ClosedEnvironment" );
        out.println( "class object table : " + table );
        out.println( "binding table : " + symbol_table );
	out.println( "parent env : " + parent );

        out.flush();
        return str_writer.toString();
    }

    public void record( String name, OJClass clazz ) {
	DebugOut.println( "ClosedEnvironment#record() : "
			 + name + " "  + clazz.getName() );
        Object result = table.put( name, clazz );
        if (result != null) {
          System.err.println( name + " is already binded on "
			     + result.toString() );
        }
    }

    public OJClass lookupClass( String name ) {
	OJClass result = (OJClass) table.get( name );
	if (result != null)  return result;
        return parent.lookupClass( name );
    }

    /**
     * binds a name to the class type.
     *
     * @param     name            the fully-qualified name of the class
     * @param     clazz           the class object associated with that name
     */
    public void bindVariable( String name, OJClass clazz ) {
	symbol_table.put( name, clazz );
    }

    public OJClass lookupBind( String name ) {
	OJClass type = (OJClass) symbol_table.get( name );
	if (type != null)  return type;
	if (parent == null)  return null;
	return parent.lookupBind( name );
    }

    /*
    public String toQualifiedName(String name) {
        if (name.indexOf('.') == -1) {
            java.util.Enumeration ite = table.keys();
            while (ite.hasMoreElements()) {
                String qname = (String) ite.nextElement();
                if (qname.endsWith("." + name))  return qname;
            }
        }
        return parent.toQualifiedName(name);
    }
    */
    public String toQualifiedName(String name) {
        java.util.Enumeration ite = table.keys();
        while (ite.hasMoreElements()) {
            String qname = (String) ite.nextElement();
            if (qname.equals(name))  return qname;
        }
        return parent.toQualifiedName(name);
    }

}
