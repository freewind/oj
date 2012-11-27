/*
 * FieldAccess.java 1.0
 *
 * Jun 20, 1997
 * Sep 29, 1997
 * Oct 10, 1997
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Oct 10, 1997
 * @author  Michiaki Tatsubori
 */
package openjava.ptree;

import openjava.mop.Environment;
import openjava.mop.NoSuchMemberException;
import openjava.mop.OJClass;
import openjava.mop.OJField;
import openjava.ptree.util.ParseTreeVisitor;

/**
 * The <code>FieldAccess</code> class represents
 * a field access like :
 * <br><blockquote><pre>
 *     f().str
 * </pre></blockquote><br>
 * In this field access,
 * you can get <code>f()</code> by <code>getReferenceExpr()</code>
 * and can get <code>str</code> by <code>getName()</code> .
 * <br>
 * Warning this class may has bugs around the expression.
 *
 * @see openjava.ptree.NonLeaf
 * @see openjava.ptree.Expression
 */
public class FieldAccess extends NonLeaf implements Expression {

	/**
	 * An access to the specified field of the given expression.
	 */
	public FieldAccess(Expression expr, String name) {
		super();
		set(expr, name);
	}

	/**
	 * An access to the specified static field of the type.
	 */
	public FieldAccess(TypeName typename, String name) {
		super();
		set(typename, name);
	}

	/**
	 * An access to the specified static field of the type.
	 */
	public FieldAccess(OJClass clazz, String name) {
		this(TypeName.forOJClass(clazz), name);
	}

	/**
	 * An access to the specified field of self.
	 */
	public FieldAccess(String name) {
		this((Expression) null, name);
	}

	FieldAccess() {
		super();
	}

	public ParseTree getReference() {
		return (ParseTree) elementAt(0);
	}

	public boolean isTypeReference() {
		return (getReference() instanceof TypeName);
	}

	/**
	 * Gets the expression accessed.
	 *
	 * @return  the expression accessed.
	 */
	public Expression getReferenceExpr() {
		if (isTypeReference())
			return null;
		return (Expression) getReference();
	}

	/**
	 * Sets the expression accessed.
	 *
	 * @param  expr  the expression accessed.
	 */
	public void setReferenceExpr(Expression expr) {
		setElementAt(expr, 0);
	}

	public TypeName getReferenceType() {
		if (!isTypeReference())
			return null;
		return (TypeName) getReference();
	}

	public void setReferenceType(TypeName typename) {
		setElementAt(typename, 0);
	}

	/**
	 * Gets the field name.
	 *
	 * @return  the field name.
	 */
	public String getName() {
		return (String) elementAt(1);
	}

	/**
	 * Sets the field name.
	 *
	 * @param  name  the field name.
	 */
	public void setName(String name) {
		setElementAt(name, 1);
	}

	public void accept(ParseTreeVisitor v) throws ParseTreeException {
		v.visit(this);
	}

	public OJClass getType(Environment env) throws Exception {
		OJClass selftype = env.lookupClass(env.currentClassName());

		Expression refexpr = getReferenceExpr();
		String name = getName();

		OJClass reftype = null;

		if (refexpr != null) {
			reftype = refexpr.getType(env);
		} else {
			TypeName refname = getReferenceType();
			if (refname != null) {
				String qname = env.toQualifiedName(refname.toString());
				reftype = env.lookupClass(qname);
			}
		}

		OJField field = null;

		if (reftype != null)
			field = pickupField(reftype, name);
		if (field != null)
			return field.getType();

		/* try to consult this class and outer classes */
		if (reftype == null) {
			OJClass declaring = selftype;
			while (declaring != null) {
				field = pickupField(declaring, name);
				if (field != null)
					return field.getType();

				/* consult innerclasses */
				OJClass[] inners = declaring.getDeclaredClasses();
				for (int i = 0; i < inners.length; ++i) {
					field = pickupField(inners[i], name);
					if (field != null)
						return field.getType();
				}

				declaring = declaring.getDeclaringClass();
			}
			reftype = selftype;
		}

		NoSuchMemberException e = new NoSuchMemberException(name);
		field = reftype.resolveException(e, name);
		return field.getType();
	}

	private static OJField pickupField(OJClass reftype, String name) {
		try {
			return reftype.getField(name, reftype);
		} catch (NoSuchMemberException e) {
			return null;
		}
	}

}
