/*
 * DefaultListRule.java
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


import openjava.ptree.ObjectList;
import openjava.ptree.ParseTree;


/**
 * The class <code>DefaultListRule</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: DefaultListRule.java,v 1.2 2003/02/19 02:54:32 tatsubori Exp $
 * @see java.lang.Object
 */
public final class DefaultListRule extends SeparatedListRule
{
    private ObjectList list = null;

    public DefaultListRule( SyntaxRule elementRule,
			    int separator_token, boolean allowsEmpty ) {
	super( elementRule, separator_token, allowsEmpty );
    }

    public DefaultListRule( SyntaxRule elementRule, int separator_token ) {
	this( elementRule, separator_token, false );
    }

    protected void initList() {
	list = new ObjectList();
    }

    protected void addListElement( Object elem ) {
	list.add( elem );
    }

    protected ParseTree getList() {
	return list;
    }

}
