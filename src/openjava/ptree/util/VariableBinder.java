/*
 * VariableBinder.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.ptree.util;

import openjava.mop.Environment;
import openjava.mop.OJClass;
import openjava.mop.OJClassNotFoundException;
import openjava.ptree.ForStatement;
import openjava.ptree.Parameter;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Statement;
import openjava.ptree.TypeName;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.VariableDeclarator;
import openjava.tools.DebugOut;

/**
 * The class <code>VariableBinder</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: VariableBinder.java,v 1.2 2003/02/19 02:55:00 tatsubori Exp $
 * @see java.lang.Object
 */
public class VariableBinder extends ScopeHandler {

	public VariableBinder(Environment env) {
		super(env);
	}

	public Statement evaluateDown(VariableDeclaration ptree)
		throws ParseTreeException {
		super.evaluateDown(ptree);
		bindLocalVariable(ptree, getEnvironment());

		return ptree;
	}

	public Statement evaluateDown(ForStatement ptree)
		throws ParseTreeException {
		super.evaluateDown(ptree);
		TypeName tspec = ptree.getInitDeclType();
		if (tspec == null)
			return ptree;
		VariableDeclarator[] vdecls = ptree.getInitDecls();
		bindForInit(tspec, vdecls, getEnvironment());

		return ptree;
	}

	public Parameter evaluateDown(Parameter ptree) throws ParseTreeException {
		super.evaluateDown(ptree);
		bindParameter(ptree, getEnvironment());

		return ptree;
	}

	private static void bindLocalVariable(
		VariableDeclaration var_decl,
		Environment env) {
		String type = var_decl.getTypeSpecifier().toString();
		String name = var_decl.getVariable();
		bindName(env, type, name);
	}

	private static void bindForInit(
		TypeName tspec,
		VariableDeclarator[] vdecls,
		Environment env) {
		for (int i = 0; i < vdecls.length; ++i) {
			String type = tspec.toString() + vdecls[i].dimensionString();
			String name = vdecls[i].getVariable();
			bindName(env, type, name);
		}
	}

	private static void bindParameter(Parameter param, Environment env) {
		String type = param.getTypeSpecifier().toString();
		String name = param.getVariable();
		bindName(env, type, name);
	}

	private static void bindName(Environment env, String type, String name) {
		String qtypename = env.toQualifiedName(type);
		try {
			OJClass clazz = env.lookupClass(qtypename);
			if (clazz == null)
				clazz = OJClass.forName(qtypename);
			env.bindVariable(name, clazz);
			DebugOut.println("binds variable\t" + name + "\t: " + qtypename);
		} catch (OJClassNotFoundException e) {
			System.err.println(
				"VariableBinder.bindName() "
					+ e.toString()
					+ " : "
					+ qtypename);
			System.err.println(env);
		}
	}

}
