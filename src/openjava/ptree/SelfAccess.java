/*
 * SelfAccess.java 1.0
 *
 * Jun 20, 1997 by micn
 * Sep 29, 1997 by bv
 * Oct 10, 1997 by mich
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Oct 10, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.mop.Environment;
import openjava.mop.OJClass;
import openjava.ptree.util.ParseTreeVisitor;

/**
 * The class <code>SelfAccess</code> represents an access to
 * <pre>this</pre> object.
 * this or super
 *
 */
public class SelfAccess extends Leaf implements Expression {
	public static final int THIS = 0;
	public static final int SUPER = 1;

	private static SelfAccess _constantSuper = null;
	private static SelfAccess _constantThis = null;

	protected String qualifier = null;

	int id = -1;

	SelfAccess() {
		this(null);
	}

	SelfAccess(String q) {
		super(((q == null) ? "" : q + ".") + "this");
		this.qualifier = q;
		this.id = THIS;
	}

	SelfAccess(int id) {
		super(id == SUPER ? "super" : "this");
		this.id = id;
	}

	public String getQualifier() {
		return this.qualifier;
	}

	public int getAccessType() {
		return id;
	}

	public boolean isSuperAccess() {
		return (id == SUPER);
	}

	public static SelfAccess makeSuper() {
		return constantSuper();
	}

	public static SelfAccess makeThis() {
		return constantThis();
	}

	public static SelfAccess makeThis(String qualifier) {
		if (qualifier == null || qualifier.equals("")) {
			return constantThis();
		}
		return new SelfAccess(qualifier);
	}

	public static SelfAccess constantSuper() {
		if (_constantSuper == null) {
			_constantSuper = new SelfAccess(SUPER);
		}
		return _constantSuper;
	}

	public static SelfAccess constantThis() {
		if (_constantThis == null) {
			_constantThis = new SelfAccess();
		}
		return _constantThis;
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

	public OJClass getType(Environment env) throws Exception {
		OJClass current = env.lookupClass(env.currentClassName());
		if (isSuperAccess())
			return current.getSuperclass();
		String qualifier = getQualifier();
		if (qualifier == null)
			return current;
		return env.lookupClass(env.toQualifiedName(qualifier));
	}

}
