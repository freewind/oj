/*
 * SunLibCompiler.java
 *
 * Apr 16, 1999  Michiaki Tatsubori
 */
package jp.ac.tsukuba.openjava;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import openjava.ojc.JavaCompiler;

/**
 * The class <code>SunLibCompiler</code> is an adapter for the compiler
 * which invokes Sun's library javac.
 * <p>
 * The class path must includes lib/tools.jar in the jdk package.
 *
 * @since jdk1.2
 */
public class SunLibCompiler implements JavaCompiler {
	Object sunJavac;
	//sun.tools.javac.Main m;
	Method compileMethod;

	public SunLibCompiler() {
		//m = new sun.tools.javac.Main(System.err, "javac");
		try {
			Class clazz = Class.forName("sun.tools.javac.Main");
			Constructor cons =
				clazz.getConstructor(
					new Class[] { OutputStream.class, String.class });
			sunJavac = cons.newInstance(new Object[] { System.err, "javac" });
			compileMethod =
				clazz.getMethod("compile", new Class[] { String[].class });
		} catch (Exception ex) {
			throw new RuntimeException(ex.toString());
		}

	}

	public static void main(String[] args) {
		new SunLibCompiler().compile(args);
	}

	public void compile(String[] args) {
		//sun.tools.javac.Main.main( args );
		//m.compile(args);
		try {
			compileMethod.invoke(sunJavac, new Object[] { args });
		} catch (Exception ex) {
			throw new RuntimeException(ex.toString());
		}
	}

}
