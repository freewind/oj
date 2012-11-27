/*
 * OJSystemTest.java
 * 
 * Created on 2003/11/02 $Id: OJSystemTest.java,v 1.2 2003/11/14 13:52:02 tatsubori Exp $
 */
package openjava.mop;

import java.io.File;

import junit.framework.TestCase;
import openjava.mop.edit.OJEditableClass;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.StatementList;

/**
 * The type OJSystemTest.
 * 
 * @author Michiaki Tatsubori
 * @version 1.0 2003/11/02
 */
public class OJSystemTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(OJSystemTest.class);
    }

    /*
	 * @see TestCase#setUp()
	 */
    protected void setUp() throws Exception {
        OJSystem.initConstants();
    }

    class TestFileEnvironment extends FileEnvironment {
        public TestFileEnvironment(Environment env) {
            super(env);
        }
        public String getPackage() {
            return "test";
        }
        public File getFile() {
            return new File("test", "A.oj");
        }
        public String currentClassName() {
            return "A";
        }
    }

    public void testAddNewClasses() throws MOPException {
        FileEnvironment fenv = new TestFileEnvironment(OJSystem.env);
        StatementList stmts =
            OJClass.makeStatementList(OJSystem.env, "class A { }");
        ClassDeclaration cdecl = (ClassDeclaration) stmts.get(0);

        OJEditableClass newClass =
            new OJEditableClass(new OJClass(OJSystem.env, null, cdecl));
        OJSystem.addNewClass(newClass);
    }

}
