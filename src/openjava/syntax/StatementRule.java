/*
 * StatementRule.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.syntax;

import openjava.mop.Environment;
import openjava.ptree.ParseTree;
import openjava.ptree.Statement;

/**
 * The class <code>StatementRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: StatementRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public class StatementRule extends AbstractSyntaxRule {
	private Environment env;

	public StatementRule(Environment env) {
		this.env = env;
	}

	public StatementRule() {
		this(null);
	}

	public final ParseTree consume(TokenSource token_src)
		throws SyntaxException {
		return consumeStatement(token_src);
	}

	public Statement consumeStatement(TokenSource token_src)
		throws SyntaxException {
		Statement result = JavaSyntaxRules.consumeStatement(token_src, env);
		if (result == null)
			throw JavaSyntaxRules.getLastException();
		return result;
	}

}
