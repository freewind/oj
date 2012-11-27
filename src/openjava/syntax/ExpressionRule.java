/*
 * ExpressionRule.java
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
import openjava.ptree.Expression;
import openjava.ptree.ParseTree;


/**
 * The class <code>ExpressionRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: ExpressionRule.java,v 1.2 2003/02/19 02:54:31 tatsubori Exp $
 * @see java.lang.Object
 */
public class ExpressionRule extends AbstractSyntaxRule
{
    private Environment env;

    public ExpressionRule( Environment env ) {
	this.env = env;
    }

    public ExpressionRule() {
	this( null );
    }

    public final ParseTree consume( TokenSource token_src )
	throws SyntaxException
    {
	return this.consumeExpression( token_src );
    }

    /**
     * Subclasses of this class can override this method to
     * extend its returnable expressions.
     *
     * @param token_src token source
     * @return expression
     * @exception SyntaxException
     */
    public Expression consumeExpression( TokenSource token_src )
	throws SyntaxException
    {
	Expression result
	    = JavaSyntaxRules.consumeExpression( token_src, env );
	if (result == null)  throw JavaSyntaxRules.getLastException();
	return result;
    }

}
