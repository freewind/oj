/*
 * TestPrinter.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package examples.print;

import java.lang.Object;
import openjava.mop.*;
import openjava.ptree.ClassDeclaration;
import openjava.syntax.*;

/**
 * The class <code>TestPrinter</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: TestPrinter.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class TestPrinter extends OJClass {
	public void translateDefinition() throws MOPException {
		OJMethod[] m;

		/*
		System.out.println( "SELF" );
		m = getInheritedMethods();
		for (int i = 0; i < m.length; ++i) {
		System.out.println( m[i].toString() );
		}
		*/

		System.out.println("STATEMENTLIST");
		OJClass clazz = OJClass.forName("openjava.ptree.StatementList");

		m = clazz.getMethods(this);
		/*
		for (int i = 0; i < m.length; ++i) {
		System.out.println( m[i].toString() );
		}
		*/

		System.out.println("INSERTELEMENTAT");
		OJMethod method;
		try {
			OJClass[] params =
				new OJClass[] {
					OJClass.forName("openjava.ptree.Statement"),
					OJClass.forName("int")};
			method = clazz.getMethod("insertElementAt", params);
			m = Toolbox.pickupMethodsByName(m, "insertElementAt");
			System.out.println("name matches : " + m.length);
			System.err.println(m[0].toString());
			OJClass[] mparams = m[0].getParameterTypes();
			System.err.println("M " + mparams[0] + " " + mparams[1]);
			System.err.println("P " + params[0] + " " + params[1]);
			boolean assign0 = mparams[0].isAssignableFrom(params[0]);
			System.out.println("assignable0 : " + assign0);
			boolean assign1 = mparams[1].isAssignableFrom(params[1]);
			System.out.println("assignable1 : " + assign1);
			System.out.println("equality : " + (mparams[0] == params[0]));

			boolean isa = Toolbox.isAcceptable(mparams, params);
			System.out.println("acceptable : " + isa);
			m = Toolbox.pickupAcceptableMethodsByParameterTypes(m, params);
			System.out.println("param matches : " + m.length);
		} catch (NoSuchMethodException e) {
			method = null;
		}
		if (method == null) {
			System.out.println("NOT FOUND");
		} else {
			System.out.println(method.toString());
		}
	}

	public TestPrinter(
		Environment outer_env,
		OJClass declarer,
		ClassDeclaration ptree) {
		super(outer_env, declarer, ptree);
	}

	public TestPrinter(Class javaclass, MetaInfo minfo) {
		super(javaclass, minfo);
	}

}
