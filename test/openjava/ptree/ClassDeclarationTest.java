/*
 * ClassDeclarationTest.java
 * Created on 2005/04/04 by Michiaki Tatsubori
 * @version $Id$
 */
package openjava.ptree;

import junit.framework.TestCase;

/**
 * ClassDeclarationTest
 * @author mich
 */
public class ClassDeclarationTest extends TestCase {

    public void testBeInterface() {
        ModifierList modifs = new ModifierList(ModifierList.PUBLIC);
        MemberDeclarationList members = new MemberDeclarationList();
        ClassDeclaration cd = new ClassDeclaration(modifs, "Dummy", null, null, members);
        assertFalse(cd.isInterface());
        System.out.println(cd.toString());
        cd.beInterface(true);
        assertTrue(cd.isInterface());
        System.out.println(cd.toString());        
    }

}
