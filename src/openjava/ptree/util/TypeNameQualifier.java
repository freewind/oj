/*
 * TypeNameQualifier.java
 *
 * Make typenames qualified.
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.ptree.util;

import openjava.mop.Environment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassLiteral;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.TypeName;

/**
 * The class <code>TypeNameQualifier</code> is a utility class
 * to be usede for making a copy of ptree work well without import
 * statements.
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: TypeNameQualifier.java,v 1.2 2003/02/19 02:55:00 tatsubori Exp $
 * @see java.lang.Object
 */
public class TypeNameQualifier extends ScopeHandler {
	private String newName = null;

	/**
	 * Constructs a new visitor for parse tree for qualifying
	 * each type name appearing there.
	 *
	 * @param env  environment for qualifying type names.
	 */
	public TypeNameQualifier(Environment env) {
		this(env, null);
	}

	/**
	 * Constructs a new visitor for parse tree for qualifying
	 * each type name appearing there.
	 *
	 * @param env  environment for qualifying type names.
	 * @param newClassName  the class name for constructors.
	 *        If <code>null</code> is specified, the name for constructors
	 *        will remain as is.
	 */
	public TypeNameQualifier(Environment env, String newClassName) {
		super(env);
		this.newName = newClassName;
	}

	private String qualify(String org) {
		return getEnvironment().toQualifiedName(org);
	}

	private TypeName qualifyName(TypeName name) {
		return new TypeName(qualify(name.getName()), name.getDimension());
	}

	private TypeName[] qualifyNames(TypeName[] names) {
		for (int i = 0; i < names.length; ++i) {
			names[i] = qualifyName(names[i]);
		}
		return names;
	}

	public void visit(ClassDeclaration c)
		throws openjava.ptree.ParseTreeException {
		c.setBaseclasses(qualifyNames(c.getBaseclasses()));
		c.setInterfaces(qualifyNames(c.getInterfaces()));
		super.visit(c);
	}

	public void visit(ConstructorDeclaration c)
		throws openjava.ptree.ParseTreeException {
		if (newName != null)
			c.setName(newName);
		c.setThrows(qualifyNames(c.getThrows()));
		super.visit(c);
	}

	public void visit(MethodDeclaration m)
		throws openjava.ptree.ParseTreeException {
		m.setThrows(qualifyNames(m.getThrows()));
		super.visit(m);
	}

	public void visit(TypeName tname)
		throws openjava.ptree.ParseTreeException {
		tname.setName(qualify(tname.getName()));
		super.visit(tname);
	}

	/** This is needed because of a bug. */
	public void visit(ClassLiteral clit)
		throws openjava.ptree.ParseTreeException {
		clit.replace(new ClassLiteral(qualifyName(clit.getTypeName())));
	}
}
