/*
 * Printer.java
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
 * The class <code>Printer</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: Printer.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class Printer extends OJClass {
	public void translateDefinition() throws MOPException {
		System.out.println("CONSTRUCTORS");
		OJConstructor[] c = getConstructors();
		for (int i = 0; i < c.length; ++i) {
			System.out.println(c[i].toString());
		}

		System.out.println("FIELDS");
		OJField[] f = getAllFields();
		for (int i = 0; i < f.length; ++i) {
			System.out.println(f[i].toString());
		}

		System.out.println("METHODS");
		OJMethod[] m = getAllMethods();
		for (int i = 0; i < m.length; ++i) {
			System.out.println(m[i].toString());
		}
	}

	public Printer(
		Environment outer_env,
		OJClass declarer,
		ClassDeclaration ptree) {
		super(outer_env, declarer, ptree);
	}

	public Printer(Class javaclass, MetaInfo minfo) {
		super(javaclass, minfo);
	}

}
