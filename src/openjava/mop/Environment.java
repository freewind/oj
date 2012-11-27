/*
 * Environment.java
 *
 *
 * Jul 28, 1998 by mich
 */
package openjava.mop;




public abstract class Environment
{

    protected Environment parent;
  
    public Environment() {
        parent = null;
    }
  
    public Environment( Environment e ) {
        parent = e;
    }

    public abstract String toString();
  
    /**
     * Gets the package name.
     */
    public String getPackage()
    {
        if (parent == null) {
	    System.err.println( "Environment.getPackage() : not specified." );
	    return null;
	}
        return parent.getPackage();
    }
  
    /**
     * Looks a class object up.
     *
     * @param	name		the name of the fully-qualified name of
     *				the class looked for
     */
    public OJClass lookupClass( String name ) {
        if (parent == null) {
	    System.err.println( "Environment.lookupClass() : not specified." );
	    return null;
	}
        return parent.lookupClass( name );
    }
  
    /**
     * Records a class object.
     *
     * @param	name		the fully-qualified name of the class
     * @param	clazz		the class object associated with that name
     */
    public abstract void record( String name, OJClass clazz );
  
    /**
     * Looks up a binded type of the given variable or field name.
     *
     * @param	name		the fully-qualified name of the class
     * @param	clazz		the class object associated with that name
     */
    public OJClass lookupBind( String name ) {
        if (parent == null)  return null;
        return parent.lookupBind( name );
    }
  
    /**
     * binds a name to the class type.
     *
     * @param	name		the fully-qualified name of the class
     * @param	clazz		the class object associated with that name
     */
    public abstract void bindVariable( String name, OJClass clazz );
  
    /**
     * Obtains the fully-qualified name of the given class name.
     *
     * @param  name  a simple class name or a fully-qualified class name
     * @return  the fully-qualified name of the class
     */
    public String toQualifiedName( String name ) {
	if (parent == null)  return name;
	return parent.toQualifiedName( name );
    }
  
    /**
     * Tests if the given name is a qualified name or not.
     */
    public static boolean isQualifiedName( String name ) {
        return name.indexOf( '.' ) >= 0;
    }
  
    /**
     * Converts a fully-qualified name to the corresponding simple-name.
     *
     * <pre>
     * For example :
     *   toSimpleName( "java.lang.Class" ) returns  "Class".
     * </pre>
     *
     * @return	the given name <I>as is</I> if it is not a qualified name
     */
    public static String toSimpleName( String qualified_name ) {
        int index = qualified_name.lastIndexOf( '.' );
        if (index < 0) {
  	  return qualified_name;
        } else {
  	  return qualified_name.substring( index + 1 );
        }
    }

    /**
     * Converts a fully-qualified name to the corresponding package name.
     *
     * <pre>
     * For example :
     *   toPackageName( "java.lang.Class" ) returns  "java.lang".
     *   toPackageName( "MyClass" ) returns  "".
     * </pre>
     *
     * @return	the given name <I>as is</I> if it is not a qualified name
     */
    public static String toPackageName( String qualified_name ) {
        int index = qualified_name.lastIndexOf( '.' );
        return (index < 0) ? "" : qualified_name.substring( 0, index );
    }
  
    public String currentClassName() {
        if (parent == null)  return null;
        return parent.currentClassName();
    }

    /******************************/
    public boolean isRegisteredModifier( String str ) {
        if (parent == null)  return false;
        return parent.isRegisteredModifier( str );
    }
  
}
