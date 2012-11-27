/*
 * ClassEnvironment.java
 *
 * Jul 28, 1998 by mich
 */
package openjava.mop;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;


public class ClassEnvironment extends ClosedEnvironment
{
    private OJClass clazz = null;
    private String className = null;
    private Vector memberClasses = new Vector();

    public ClassEnvironment( Environment e, String name ) {
	super( e );
	className = toSimpleName( name );
    }

    public ClassEnvironment( Environment e ) {
	super( e );
	className = null;
    }

    public ClassEnvironment( Environment e, OJClass clazz ) {
        super( e );
	className = toSimpleName( clazz.getName() );
	OJClass[] memclazz = clazz.getDeclaredClasses();
	for (int i = 0; i < memclazz.length; ++i) {
	    memberClasses.addElement( memclazz[i].getSimpleName() );
	}
    }

    public String getClassName() {
        if (className != null)  return className;
	return "<unknown class>";
    }

    public Vector getMemberClasses() {
	return memberClasses;
    }

    public String toString() {
        StringWriter str_writer = new StringWriter();
        PrintWriter out = new PrintWriter( str_writer );

        out.println( "ClassEnvironment" );
        out.println( "class : " + getClassName() );
        out.println( "member classes : " + getMemberClasses() );
	out.print( "parent env : " + parent );

        out.flush();
        return str_writer.toString();
    }

    public void recordClassName( String name ) {
	this.className = toSimpleName( name );
    }

    public void recordMemberClass( String name ) {
	memberClasses.addElement( name );
    }

    public OJClass lookupClass( String name ) {
	/* should hold and return member classes
         * Currently, the global environment holds all the top inner classes.
         */
        return parent.lookupClass( name );
    }

    public OJClass lookupBind(String name) {
        String current = currentClassName();
        OJClass declarer = lookupClass(current);
        if (declarer == null) {
	    System.err.println( "unexpected error : unknown class " + current);
            return parent.lookupBind(name);
        }
        OJField field = pickupField(declarer, name);
        if (field != null)  return field.getType();
        /* not a field name of this class */
	return parent.lookupBind(name);
    }

    public static OJField pickupField(OJClass reftype, String name) {
        try {
            return reftype.getField(name, reftype);
        } catch ( NoSuchMemberException e ) {}
        /* tries member fields of its inner classes */
        return pickupField(reftype.getDeclaredClasses(), name);
    }

    public static OJField pickupField(OJClass[] reftypes, String name) {
	for (int i = 0; i < reftypes.length; ++i) {
            OJField result = pickupField(reftypes[i], name);
	    if (result != null)  return result;
	}
	return null;
    }

    /**
     * Obtains the fully-qualified name of the given class name.
     *
     * @param  name  a simple class name or a fully-qualified class name
     * @return  the fully-qualified name of the class
     */
    public String toQualifiedName( String name ) {
        if (name == null)  return null;
        if (name.endsWith("[]")) {
            String stripped = name.substring( 0, name.length() - 2 );
            return toQualifiedName( stripped ) + "[]";
        }
	if (name.indexOf( "." ) != -1) {
	    /* may be simple name + innerclass */
	    String top = getFirst( name );
	    String qtop = toQualifiedName( top );
	    if (qtop == null || qtop.equals( top ))  return name;
	    return qtop + "." + getRest( name );
	}
	if (name.equals( getClassName() )) {
            if (isMostOuter()) {
                /* most outer class */
                String pack = getPackage();
                if (pack == null || pack.equals( "" ))  return name;
                return pack + "." + name;
            }
	    /* inner class */
            String parentName = parentName();
            if (parentName != null)  return parentName() + "." + name;
	    /* anonymous/local class */
	    return name;
	}
	if (memberClasses.indexOf( name ) >= 0) {
	    return currentClassName() + "." + name;
	}
	return parent.toQualifiedName( name );
    }

    private static final String getFirst( String qname ) {
	int dot = qname.indexOf( "." ) ;
	if (dot == -1)  return qname;
	return qname.substring( 0, dot );
    }
    private static final String getRest( String qname ) {
	int dot = qname.indexOf( "." ) ;
	if (dot == -1)  return qname;
	return qname.substring( dot + 1 );
    }
    /*
    private String parentName() {
	Environment ancestor = parent;
	while (ancestor != null && ! (ancestor instanceof ClassEnvironment)) {
	    ancestor = ancestor.parent;
	}
	if (ancestor == null)  return null;
	return ancestor.currentClassName();
    }
    */
    private String parentName() {
        if (parent == null || ! (parent instanceof ClassEnvironment)) {
            return null;
        }
	return parent.currentClassName();
    }
    private boolean isMostOuter() {
        return (parent instanceof FileEnvironment);
    }

    /**
     * Returns the qualified class name.
     *
     * @return the qualified name of the class which organizes this
     *         environment.
     */
    public String currentClassName() {
	return toQualifiedName( getClassName() );
    }

}
