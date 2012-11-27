/*
 * SyntaxException.java
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

import openjava.tools.parser.ParseException;
import openjava.tools.parser.Token;

/**
 * The class <code>SyntaxException</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: SyntaxException.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public class SyntaxException extends ParseException {
	public SyntaxException(ParseException e) {
		super(e.currentToken, e.expectedTokenSequences, e.tokenImage);
	}

	public SyntaxException(
		Token currentToken,
		int[][] expectedToken,
		String[] tokenImage) {
		super(currentToken, expectedToken, tokenImage);
	}

	public SyntaxException() {
		super();
	}

	public SyntaxException(String message) {
		super(message);
	}
}
