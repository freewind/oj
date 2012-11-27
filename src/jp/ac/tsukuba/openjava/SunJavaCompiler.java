/*
 * SunJavaCompiler.java
 * Workaround for Runtime.exec() environment handling incompatibility..
 *
 * A work based on jp.ac.tsukuba.openjava.SunJavaCompiler
 *
 * Apr 16, 1999  Michiaki Tatsubori (mt@is.tsukuba.ac.jp)
 * Oct  1, 1999  Shiro Kawai (shiro@squareusa.com)
 * Nov 22, 1999  Michiaki Tatsubori
 */
package jp.ac.tsukuba.openjava;

import java.io.BufferedInputStream;
import java.io.InputStream;

import openjava.ojc.JavaCompiler;

/**
 * The class <code>SunJavaCompiler</code> is an adapter for Sun's javac.
 *
 * Message-Id: 19990930154627G.shiro@squareusa.com
 * <p>
 * I tried OpenJava1.0a1 on my IRIX box w/ SGI's JDK1.2
 * and had a problem to run ojc.  Somehow, Runtime.exec()
 * didn't pass all the environment variables to the invoked
 * process (more specifically, it only passed TZ).
 * Consequently the CLASSPATH env was not passed to javac kicked
 * by JP.ac.tsukuba.openjava.SunJavaCompiler.complie(), which
 * prevented ojc from finishing compilation.
 * <p>
 * So far I couldn't find exact specification about how the
 * environment variables should be treated in Java specification
 * and API documents.  I guess it may depend on platforms.
 * <p>
 * We avoided the problem by explicitly passing CLASSPATH to
 * the subprocess my modifying SunJavaCompiler class, but wondering
 * if there'd be a better way to handle it...
 */
public class SunJavaCompiler implements JavaCompiler {
	public static void main(String[] args) {
		new SunJavaCompiler().compile(args);
	}

	public void compile(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		try {
			String classpath =
				"CLASSPATH=" + System.getProperty("java.class.path");
			String[] envp = new String[1];
			envp[0] = classpath;
			Process p = runtime.exec("javac " + strs2str(args), envp);
			InputStream in = new BufferedInputStream(p.getErrorStream());
			byte[] buf = new byte[1024];
			for (int len = in.read(buf); len != -1; len = in.read(buf)) {
				System.err.write(buf, 0, len);
			}
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String strs2str(String[] strs) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < strs.length; ++i) {
			buf.append(strs[i]).append(" ");
		}
		return buf.toString();
	}

}
