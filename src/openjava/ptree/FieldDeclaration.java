/*
 * FieldDeclaration.java 1.0
 *
 * Jun 11, 1997
 *  
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated: Sep 4, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import java.util.Hashtable;

import openjava.ptree.util.ParseTreeVisitor;

/**
 * The FieldDeclaration class presents for node of parse tree.
 * FieldDeclaration
 *   :=  ModifierList TypeName VariableDeclarator SEMI
 *
 */
public class FieldDeclaration extends NonLeaf implements MemberDeclaration {
	private Hashtable suffixes = null;

	/**
	 * Allocates this object
	 *
	 */
	public FieldDeclaration(
		ModifierList e0,
		TypeName e1,
		VariableDeclarator e2) {
		super();
		set(e0, e1, e2);
	}

	public FieldDeclaration(
		ModifierList e0,
		TypeName e1,
		String e2,
		VariableInitializer e3) {
		super();
		VariableDeclarator vd = new VariableDeclarator(e2, e3);
		set(e0, e1, vd);
	}

	/**
	 * Is needed for recursive copy.
	 */
	FieldDeclaration() {
		super();
	}

	/**
	 * Gets modifier list of this field.
	 *
	 * @return  modifier list. if there is no modifiers, this returns
	 *          an empty list.
	 */
	public ModifierList getModifiers() {
		return (ModifierList) elementAt(0);
	}

	/**
	 * Sets modifier list of this field.
	 *
	 * @param  modifs  modifiers to set
	 */
	public void setModifiers(ModifierList modifs) {
		setElementAt(modifs, 0);
	}

	/**
	 * Gets type specifier of this field variable.
	 * Any modification on obtained objects is never reflected on
	 * this object.
	 *
	 * @return  the type specifier for this field.
	 */
	public TypeName getTypeSpecifier() {
		TypeName result;
		TypeName tn = (TypeName) elementAt(1);
		VariableDeclarator vd = (VariableDeclarator) elementAt(2);
		result = (TypeName) tn.makeCopy();
		result.addDimension(vd.getDimension());
		return result;
	}

	/**
	 * Sets type specifier of this field variable.
	 *
	 * @param  tspec  type specifier to set
	 */
	public void setTypeSpecifier(TypeName tspec) {
		setElementAt(tspec, 1);
	}

	/**
	 * Gets variable declarator of this field
	 *
	 * @return  variable declarator
	 * @see openjava.ptree.VariableDeclarator
	 * @deprecate
	 */
	public VariableDeclarator getVariableDeclarator() {
		return (VariableDeclarator) elementAt(2);
	}

	/**
	 * Sets type specifier of this field variable.
	 *
	 * @param  vdeclr  variable declarator to set
	 * @deprecate
	 */
	public void setVariableDeclarator(VariableDeclarator vdeclr) {
		setElementAt(vdeclr, 2);
	}

	/**
	 * Gets variable name of this field.
	 *
	 * @return  identifier of field variable
	 */
	public String getVariable() {
		return (String) getVariableDeclarator().getVariable();
	}

	/**
	 * Gets variable name of this field.
	 *
	 * @return  identifier of field variable
	 * @deprecate
	 */
	public String getName() {
		return (String) getVariableDeclarator().getVariable();
	}

	/**
	 * Sets variable name of this field.
	 *
	 * @param  name  identifier of field variable
	 */
	public void setVariable(String name) {
		getVariableDeclarator().setVariable(name);
	}

	/**
	 * Gets variable initializer of this field.
	 *
	 * @return  variable initializer
	 */
	public VariableInitializer getInitializer() {
		return (VariableInitializer) getVariableDeclarator().getInitializer();
	}

	/**
	 * Gets variable initializer of this field.
	 *
	 * @return  variable initializer
	 */
	public void setInitializer(VariableInitializer vinit) {
		getVariableDeclarator().setInitializer(vinit);
	}

	public void setSuffixes(Hashtable suffixes) {
		this.suffixes = suffixes;
	}

	public Hashtable getSuffixes() {
		return this.suffixes;
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

}
