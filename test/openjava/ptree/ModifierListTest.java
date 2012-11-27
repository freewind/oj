/*
 * ModifierListTest.java
 * Created on 2005/04/04 by Michiaki Tatsubori
 * @version $Id$
 */
package openjava.ptree;

import junit.framework.TestCase;

/**
 * ModifierListTest
 * @author mich
 */
public class ModifierListTest extends TestCase {

    /*
     * Class under test for void ModifierList()
     */
    public void testModifierList() {
        ModifierList modifs = new ModifierList();
        assertEquals(0, modifs.size());
        modifs.add(ModifierList.PUBLIC);
        assertEquals("public", modifs.toString());
        // standard modifiers do not affect the size of the list
        assertEquals(0, modifs.size());
        assertTrue(modifs.contains(ModifierList.PUBLIC));
        assertTrue(modifs.contains("public"));
    }

}
