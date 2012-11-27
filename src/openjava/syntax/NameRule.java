/*
 * NameRule.java
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


import openjava.ptree.ParseTree;
import openjava.ptree.Variable;
import openjava.tools.parser.Token;


/**
 * The interface <code>NameRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: NameRule.java,v 1.2 2003/02/19 02:54:31 tatsubori Exp $
 * @see java.lang.Object
 */
public class NameRule extends AbstractSyntaxRule
{
    /**
     * Consumes a dot-separated name like <tt>java.lang.String</tt>.
     *
     * @param  token_src  token source
     * @return  a dummy <code>Variable</code> object.
     * @see openjava.ptree.Variable
     */
    public final ParseTree consume( TokenSource token_src )
	throws SyntaxException
    {
	return consumeQualifiedName( token_src );
    }

    /**
     * To override for modifying rule.
     */
    public Variable consumeQualifiedName( TokenSource token_src )
	throws SyntaxException
    {
	IdentifierRule rule = new IdentifierRule();
	Variable ident = rule.consumeIdentifier( token_src );
	StringBuffer buf = new StringBuffer( ident.toString() );
	while (lookaheadRest( token_src )) {
	    buf.append( token_src.getNextToken().image ); /* DOT */
	    buf.append( token_src.getNextToken().image ); /* IDENTIFIER */
	}
	return new Variable( buf.toString() );
    }

    /**
     * A hard-coded lookahead for performance reason.
     *   (. <IDENTIFIER>)
     */
    protected static final boolean lookaheadRest( TokenSource token_src ) {
	Token t1 = token_src.getToken( 1 );
	Token t2 = token_src.getToken( 2 );
	return (t1.kind == DOT && t2.kind == IDENTIFIER);
    }

}
