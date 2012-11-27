/*
 * AnonymousClassEnvironment.java
 *
 * Aug 2, 2002 by Michiaki Tatsubori
 */
package openjava.mop;

import openjava.ptree.FieldDeclaration;
import openjava.ptree.MemberDeclarationList;

public class AnonymousClassEnvironment extends ClassEnvironment
{
    private MemberDeclarationList members;
    private String base;

    public AnonymousClassEnvironment(Environment e, String baseClassName, MemberDeclarationList mdecls) {
	super(e);
        this.members = mdecls;
        this.base = baseClassName;
    }

    public String getClassName() {
	return "<anonymous class>";
    }

    public String toString() {
        return super.toString();
    }

    public OJClass lookupBind(String name) {
        for (int i = 0, len = members.size(); i < len; ++i) {
            if (!(members.get(i) instanceof FieldDeclaration))  continue;
            FieldDeclaration field = (FieldDeclaration) members.get(i);
            if (! name.equals(field.getName()))  continue;
            return lookupClass(field.getName());
        }
        OJField field = pickupField(lookupClass(base), name);
        if (field != null)  return field.getType();
        /* not a field name of this class */
        return parent.lookupBind(name);
    }

    /**
     * Returns the qualified class name.
     *
     * @return the qualified name of the class which organizes this
     *         environment.
     */
    public String currentClassName() {
	return getClassName();
    }

}
