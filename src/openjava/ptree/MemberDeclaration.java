/*
 * MemberDeclaration.java 1.0
 *
 * This interface is made to type ptree-node into the field
 * declaration in the body of class.
 *
 * Jun 20, 1997
 * Aug 20, 1997
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Aug 20, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

/**
 * The MemberDeclaration interface types ptree-node into the member
 * declaration in the body of class.
 *
 * @see openjava.ptree.ParseTree
 * @see openjava.ptree.FieldDeclaration
 * @see openjava.ptree.MethodDeclaration
 * @see openjava.ptree.ConstructorDeclaration
 * @see openjava.ptree.MemberInitializer
 * @see openjava.ptree.ClassDeclaration
 */
public interface MemberDeclaration extends ParseTree {
	/** The FIELD is a kind of MemberDeclaration */
	public static final int FIELD = 48;

	/** The METHOD is a kind of MemberDeclaration */
	public static final int METHOD = 49;

	/** The CONSTRUCTOR is a kind of MemberDeclaration */
	public static final int CONSTRUCTOR = 50;

	/** The STATICINIT is a kind of MemberDeclaration */
	public static final int STATICINIT = 32;

	/** The TYPE is a kind of MemberDeclaration */
	public static final int TYPE = 40;

	/** This is same as STATICINIT */
	public static final int STATICINITIALIZER = 32;

	public boolean equals(ParseTree p);

}
