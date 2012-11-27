/*
 * MemberInitializer.java 1.0
 *
 * Jun 20, 1997
 * Sep 29, 1997
 * Oct 10, 1997
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Oct 10, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

/**
 * The InstanceInitilizer class represents instance initializer block
 * of parse tree.
 *
 * @see openjava.ptree.NonLeaf
 * @see openjava.ptree.MemberDeclaration
 * @see openjava.ptree.StatementList
 */
public class MemberInitializer extends NonLeaf implements MemberDeclaration {
	private boolean _isStatic = false;
	/**
	 * Allocates a new object.
	 *
	 * @param  stmts  the statement list of this instance initializer block
	 */
	public MemberInitializer(StatementList block) {
		this(block, false);
	}

	public MemberInitializer(StatementList block, boolean is_static) {
		super();
		this._isStatic = is_static;
		set(block);
	}

	public MemberInitializer() {
		super();
	}

	public boolean isStatic() {
		return _isStatic;
	}

	/**
	 * Gets the body of this instance initializer.
	 *
	 * @return  statement list.
	 */
	public StatementList getBody() {
		return (StatementList) elementAt(0);
	}

	/**
	 * Sets the body of this instance initializer.
	 *
	 * @param  stmts  statement list to set.
	 */
	public void setBody(StatementList stmts) {
		setElementAt(stmts, 0);
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

}
