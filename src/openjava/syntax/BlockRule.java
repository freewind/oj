/*
 * BlockRule.java
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


/**
 * The class <code>BlockRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: BlockRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public class BlockRule extends AbstractSyntaxRule
{
    private Environment env;

    public BlockRule( Environment env ) {
	this.env = env;
    }

    public BlockRule() {
	this ( null );
    }

    public ParseTree consume( TokenSource token_src )
	throws SyntaxException
    {
	ParseTree result = JavaSyntaxRules.consumeBlock( token_src, env );
	if (result == null)  throw JavaSyntaxRules.getLastException();
	return result;
    }

}
