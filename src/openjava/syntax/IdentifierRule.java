/*
 * IdentifierRule.java
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
 * The class <code>IdentifierRule</code> represents syntax rule of
 * Identifier.
 * returns as <code>Variable</code>.
 * <p>
 * return
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: IdentifierRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public class IdentifierRule extends AbstractSyntaxRule
{
    
    private String specifying;

    /**
     * For specified identifier.
     *
     * @param  specifying  a string value specifying the identifier
     *                     to consume.
     */
    public IdentifierRule( String specifying ) {
	this.specifying = specifying;
    }

    /**
     * For any identifier. 
     *
     */
    public IdentifierRule() {
	this( null );
    }

    public final ParseTree consume( TokenSource token_src )
	throws SyntaxException
    {
	return consumeIdentifier( token_src );
    }

    public Variable consumeIdentifier( TokenSource token_src )
	throws SyntaxException
    {
	Token t = token_src.getNextToken();
	if (t.kind != IDENTIFIER) {
	    /***** to become specified error report */
	    throw new SyntaxException( "needs Identifier" );
	}
	if (specifying != null && ! specifying.equals( t.image )) {
	    /***** to become specified error report */
	    throw new SyntaxException( "needs " + specifying );
	}
	return new Variable( t.image );
    }

}
