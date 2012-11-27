/*
 * CompilationUnit.java 1.0
 *
 * This subclass of symbol represents (at least) terminal symbols returned 
 * by the scanner and placed on the parse stack.  At present, this 
 * class does nothing more than its super class.
 *
 * Jun 11, 1997 by mich
 * Sep 27, 1997 by mich
 *   
 * @see openjava.ptree.ParseTree
 * @version 1.0  last updated: Sep 27, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

/**
 * The CompilationUnit class presents for the whole parse tree in a file.
 *
 * CompilationUnits consists of
 * (package statement) (import statement list) (type declaration list)
 * QualifiedName       ImportStatementList     ClassDeclarationList
 * 
 * @see openjava.ptree.ClassDeclarationList
 */
public class CompilationUnit extends NonLeaf {
	/**
	 * Allocates this object with specified parse-tree elements.
	 *
	 */
	public CompilationUnit(String e0, String[] e1, ClassDeclarationList e2) {
		super();
		if (e1 == null)
			e1 = new String[0];
		if (e2 == null)
			e2 = new ClassDeclarationList();
		set(e0, e1, e2);
	}

	/**
	 * Sets the package of this compilation unit
	 *
	 * @param  qn  the qualified name indicating this package
	 */
	public void setPackage(String qn) {
		setElementAt(qn, 0);
	}

	/**
	 * Obtains the package of this compilation unit
	 *
	 * @return  the qualified name indicating this package
	 */
	public String getPackage() {
		return (String) elementAt(0);
	}

	/**
	 * Sets the import statement list of this compilation unit
	 *
	 * @param  islst  the import statement list of this compilation unit
	 */
	public void setDeclaredImports(String[] islst) {
		setElementAt(islst, 1);
	}

	/**
	 * Obtains the import statement list of this compilation unit
	 *
	 * @return  the import statement list of this compilation unit
	 */
	public String[] getDeclaredImports() {
		return (String[]) elementAt(1);
	}

	/**
	 * Sets the type declaration list of this compilation unit
	 *
	 * @param  tdlst  the type declaration list of this compilation unit
	 */
	public void setClassDeclarations(ClassDeclarationList tdlst) {
		setElementAt(tdlst, 2);
	}

	/**
	 * Obtains the type declaration list of this compilation unit
	 *
	 * @return  the type declaration list of this compilation unit
	 */
	public ClassDeclarationList getClassDeclarations() {
		return (ClassDeclarationList) elementAt(2);
	}

	/**
	 * Obtains the public class in this compilation unit.
	 *
	 * @return  the public class
	 * @exception  ParseTreeException  if not one public class is declared.
	 */
	public ClassDeclaration getPublicClass() throws ParseTreeException {
		ClassDeclaration ret = null;

		ClassDeclarationList tdecls = getClassDeclarations();
		for (int i = 0, len = tdecls.size(); i < len; ++i) {
			ClassDeclaration cdecl = tdecls.get(i);
			if (cdecl.getModifiers().contains(ModifierList.PUBLIC)) {
				if (ret != null) {
					throw new ParseTreeException(
						"getPublicClass() "
							+ "in CompileationUnit : "
							+ "multiple public class");
				}
				ret = cdecl;
			}
		}

		return ret;
	}

	/**
	 * Tests if the declared import string represents on demand
	 * importation.  For example, if the specified string is
	 * <code>java.lang.*</code>, this returns true, and if
	 * <code>java.lang.Object</code>, returns false;
	 *
	 * @param  import_decl  declared importation.
	 * @return  true if the string ends with ".*".
	 **/
	public static boolean isOnDemandImport(String import_decl) {
		return (import_decl.endsWith(".*"));
	}

	/**
	 * Removes ".*" at tail if it exists.
	 *
	 * @param  import_decl  declared importation.
	 * @return  a string trimmed ".*" off
	 **/
	public static String trimOnDemand(String import_decl) {
		if (isOnDemandImport(import_decl)) {
			return import_decl.substring(0, import_decl.length() - 2);
		}
		return import_decl;
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}
}
