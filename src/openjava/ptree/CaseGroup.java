/*
 * CaseGroupjava 1.0
 *
 *
 * Jun 20, 1997 by mich
 * Sep 29, 1997 by mich
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Sep 29, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

/**
 *
 */
public class CaseGroup extends NonLeaf {

	public CaseGroup(ExpressionList cll, StatementList bsl) {
		super();
		set(cll, bsl);
	}

	CaseGroup() {
		super();
	}

	public ExpressionList getLabels() {
		return (ExpressionList) elementAt(0);
	}

	public StatementList getStatements() {
		return (StatementList) elementAt(1);
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

}
