/*
 * TokenSourceAdapter.java
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

import openjava.syntax.TokenSource;

/**
 * The class <code>TokenSourceAdapter</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: TokenSourceAdapter.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public final class TokenSourceAdapter extends ParserTokenManager {
	private TokenSource token_src;

	public TokenSourceAdapter(TokenSource token_src) {
		super(null);
		this.token_src = token_src;
	}

	public Token getNextToken() {
		return token_src.getNextToken();
	}

	public Token getToken(int i) {
		return token_src.getToken(i);
	}
}
