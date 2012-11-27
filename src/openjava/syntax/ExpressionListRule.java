/*
 * ExpressionListRule.java
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
import openjava.ptree.ExpressionList;
import openjava.ptree.ParseTree;


/**
 * The class <code>ExpressionListRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: ExpressionListRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public final class ExpressionListRule extends SeparatedListRule
{
    private ExpressionList exprList = null;

    public ExpressionListRule( ExpressionRule exprRule, boolean allowsEmpty ) {
	super( exprRule, TokenID.COMMA, allowsEmpty );
    }

    public ExpressionListRule( ExpressionRule exprRule ) {
	this( exprRule, false );
    }

    public ExpressionListRule( Environment env, boolean allowsEmpty ) {
	this( new ExpressionRule( env ), allowsEmpty );
    }

    public ExpressionListRule( Environment env ) {
	this( env, false );
    }

    protected void initList() {
	exprList = new ExpressionList();
    }

    protected void addListElement( Object elem ) {
	exprList.add( (Expression) elem );
    }

    protected ParseTree getList() {
	return exprList;
    }

}
