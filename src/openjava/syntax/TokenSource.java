/*
 * TokenSource.java
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
import openjava.tools.parser.Token;

/**
 * The class <code>TokenSource</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: TokenSource.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public interface TokenSource {
	public Environment getEnvironment();
	public Token getNextToken();
	public Token getToken(int i);

}
