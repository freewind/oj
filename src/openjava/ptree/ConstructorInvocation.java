/*
 * ConstructorInvocation.java 1.0
 *
 * Jun 20, 1997 mich
 * Sep 29, 1997 bv
 * Oct 11, 1997 mich
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Oct 11, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

/**
 * The ConstructorInvocation class presents expression statement node
 * of parse tree
 *
 * @see openjava.ptree.ParseTree
 * @see openjava.ptree.NonLeaf
 * @see openjava.ptree.Statement
 * @see openjava.ptree.ExpressionList
 */
public class ConstructorInvocation extends NonLeaf {

	private boolean _isSelfInvocation = true;

	/**
	 * Constructs a new constructor invocation.
	 * i.e. <code>this(..)</code>
	 *
	 * @param exprs arguments for this constructor invocation
	 */
	public ConstructorInvocation(ExpressionList exprs) {
		super();
		this._isSelfInvocation = true;
		if (exprs == null)
			exprs = new ExpressionList();
		set(exprs);
	}

	/**
	 * Constructs a new constructor invocation.
	 * i.e. <code>super(..)</code>
	 *
	 * @param exprs arguments for this constructor invocation
	 * @param enclosing outerclass qualifier.
	 */
	public ConstructorInvocation(ExpressionList exprs, Expression enclosing) {
		super();
		this._isSelfInvocation = false;
		if (exprs == null)
			exprs = new ExpressionList();
		set(exprs, enclosing);
	}

	ConstructorInvocation() {
		super();
	}

	/*********need modification for copy******/

	public boolean isSelfInvocation() {
		return _isSelfInvocation;
	}

	/**
	 * Gets the expressions as arguments for this invocation.
	 *
	 * @return  the expressions.
	 */
	public ExpressionList getArguments() {
		return (ExpressionList) elementAt(0);
	}

	public Expression getEnclosing() {
		return (Expression) elementAt(1);
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

}
