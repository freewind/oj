/*
 * TypeNameRule.java
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
import openjava.ptree.TypeName;

/**
 * The class <code>TypeNameRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: TypeNameRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public class TypeNameRule extends AbstractSyntaxRule {
	public final ParseTree consume(TokenSource token_src)
		throws SyntaxException {
		return consumeTypeName(token_src);
	}

	public TypeName consumeTypeName(TokenSource token_src)
		throws SyntaxException {
		TypeName result = JavaSyntaxRules.consumeTypeName(token_src);
		if (result == null)
			throw JavaSyntaxRules.getLastException();
		Environment env = token_src.getEnvironment();
		result.setName(env.toQualifiedName(result.getName()));
		return result;
	}

}
