/*
 * CustomTokenManager.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.tools.parser;


import openjava.mop.Environment;
import openjava.syntax.TokenSource;


/**
 * The class <code>CustomTokenManager</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: CustomTokenManager.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public final class CustomTokenManager
    extends ParserTokenManager
    implements TokenSource
{
    private Parser parser;
    private Environment env;

    int pointer = 0;
    int offset = 0;

    public CustomTokenManager( Parser parser, Environment env ) {
        super( null );
        this.parser = parser;
	this.env = env;
    }

    public Environment getEnvironment() {
	return env;
    }

    public void assume() { offset = pointer; }
    public void restore() { pointer = offset; }
    public void fix() {
        for (int i = offset; i < pointer; ++i) {
             parser.getNextToken();
        }
        pointer = 0;  offset = 0;
    }
    public Token getNextToken() {
        return parser.getToken( ++pointer );
    }

    public Token getToken( int i ) {
        return parser.getToken( pointer + i );
    }

}
