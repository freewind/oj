/*
 * Toolbox.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.mop;

import java.util.Enumeration;
import java.util.Hashtable;

import openjava.ptree.Parameter;
import openjava.ptree.ParameterList;
import openjava.ptree.TypeName;

/**
 * The class <code>Toolbox</code> is an utility class.
 * <p>
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  $Id: Toolbox.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 */
public abstract class Toolbox {
	/**
	 * Generates an array of classes containing the declared classes and
	 * the based classes except the declared one.
	 *
	 * @param  declareds  declared classes to override
	 * @param  bases  based classes.
	 * @return  classes which contains the declared classes and the based
	 * 		classes except the declared one.
	 * @see openjava.mop.OJClass
	 */
	public static final OJClass[] overridesOn(
		OJClass[] declareds,
		OJClass[] bases) {
		Hashtable table = new Hashtable();
		for (int i = 0; i < bases.length; ++i) {
			table.put(bases[i].signature(), bases[i]);
		}
		for (int i = 0; i < declareds.length; ++i) {
			table.put(declareds[i].signature(), declareds[i]);
		}

		OJClass[] result = new OJClass[table.size()];
		Enumeration it = table.elements();
		for (int i = 0; it.hasMoreElements(); ++i) {
			result[i] = (OJClass) it.nextElement();
		}

		return result;
	}

	/**
	 * Generates an array of fields containing the declared fields and
	 * the based fields except the declared one.
	 *
	 * @param  declareds  declared fields to override
	 * @param  bases  based fields.
	 * @return  fields which contains the declared fields and the based
	 * 		fields except the declared one.
	 * @see openjava.mop.OJField
	 */
	public static final OJField[] overridesOn(
		OJField[] declareds,
		OJField[] bases) {
		Hashtable table = new Hashtable();
		for (int i = 0; i < bases.length; ++i) {
			table.put(bases[i].signature(), bases[i]);
		}
		for (int i = 0; i < declareds.length; ++i) {
			table.put(declareds[i].signature(), declareds[i]);
		}

		OJField[] result = new OJField[table.size()];
		Enumeration it = table.elements();
		for (int i = 0; it.hasMoreElements(); ++i) {
			result[i] = (OJField) it.nextElement();
		}

		return result;
	}

	/**
	 * Generates an array of methods containing the declared methods and
	 * the based methods except the declared one.
	 *
	 * @param  declareds  declared methods to override
	 * @param  bases  based methods.
	 * @return  methods which contains the declared methods and the based
	 * 		methods except the declared one.
	 * @see openjava.mop.OJMethod
	 */
	public static final OJMethod[] overridesOn(
		OJMethod[] declareds,
		OJMethod[] bases) {
		Hashtable table = new Hashtable();
		for (int i = 0; i < bases.length; ++i) {
			table.put(bases[i].signature(), bases[i]);
		}
		for (int i = 0; i < declareds.length; ++i) {
			table.put(declareds[i].signature(), declareds[i]);
		}

		OJMethod[] result = new OJMethod[table.size()];
		Enumeration it = table.elements();
		for (int i = 0; it.hasMoreElements(); ++i) {
			result[i] = (OJMethod) it.nextElement();
		}

		return result;
	}

	/**
	 * Generates an array of classes containing the source classes
	 * except ones with private access modifier.
	 *
	 * @param  src_classes  source classes.
	 * @return  classes except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJClass[] removeThePrivates(OJClass[] src_classes) {
		int dest_length = 0;
		for (int i = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (!modif.isPrivate())
				dest_length++;
		}

		OJClass[] result = new OJClass[dest_length];
		for (int i = 0, count = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (!modif.isPrivate())
				result[count++] = src_classes[i];
		}

		return result;
	}

	/**
	 * Generates an array of fields containing the source fields
	 * except ones with private access modifier.
	 *
	 * @param  src_fields  source fields.
	 * @return  fields except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJField[] removeThePrivates(OJField[] src_fields) {
		int dest_length = 0;
		for (int i = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (!modif.isPrivate())
				dest_length++;
		}

		OJField[] result = new OJField[dest_length];
		for (int i = 0, count = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (!modif.isPrivate())
				result[count++] = src_fields[i];
		}

		return result;
	}

	/**
	 * Generates an array of methods containing the source methods
	 * except ones with private access modifier.
	 *
	 * @param  src_methods  source methods.
	 * @return  methods except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJMethod[] removeThePrivates(OJMethod[] src_methods) {
		int dest_length = 0;
		for (int i = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (!modif.isPrivate())
				dest_length++;
		}

		OJMethod[] result = new OJMethod[dest_length];
		for (int i = 0, count = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (!modif.isPrivate())
				result[count++] = src_methods[i];
		}

		return result;
	}

	/**
	 * Generates an array of constructors containing the source
	 * constructors except ones with private access modifier.
	 *
	 * @param  src_constrs  source constructors.
	 * @return  constructors except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJConstructor[] removeThePrivates(OJConstructor[] src_constrs) {
		int dest_length = 0;
		for (int i = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (!modif.isPrivate())
				dest_length++;
		}

		OJConstructor[] result = new OJConstructor[dest_length];
		for (int i = 0, count = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (!modif.isPrivate())
				result[count++] = src_constrs[i];
		}

		return result;
	}

	/**
	 * Generates an array of classes containing the source classes
	 * except ones with private access modifier.
	 *
	 * @param  src_classes  source classes.
	 * @return  classes except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJClass[] removeTheDefaults(OJClass[] src_classes) {
		int dest_length = 0;
		for (int i = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				dest_length++;
			}
		}

		OJClass[] result = new OJClass[dest_length];
		for (int i = 0, count = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				result[count++] = src_classes[i];
			}
		}

		return result;
	}

	/**
	 * Generates an array of fields containing the source fields
	 * except ones with private access modifier.
	 *
	 * @param  src_fields  source fields.
	 * @return  fields except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJField[] removeTheDefaults(OJField[] src_fields) {
		int dest_length = 0;
		for (int i = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				dest_length++;
			}
		}

		OJField[] result = new OJField[dest_length];
		for (int i = 0, count = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				result[count++] = src_fields[i];
			}
		}

		return result;
	}

	/**
	 * Generates an array of methods containing the source methods
	 * except ones with private access modifier.
	 *
	 * @param  src_methods  source methods.
	 * @return  methods except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJMethod[] removeTheDefaults(OJMethod[] src_methods) {
		int dest_length = 0;
		for (int i = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				dest_length++;
			}
		}

		OJMethod[] result = new OJMethod[dest_length];
		for (int i = 0, count = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				result[count++] = src_methods[i];
			}
		}

		return result;
	}

	/**
	 * Generates an array of constructors containing the source
	 * constructors except ones with private access modifier.
	 *
	 * @param  src_constrs  source constructors.
	 * @return  constructors except ones with private access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJConstructor[] removeTheDefaults(OJConstructor[] src_constrs) {
		int dest_length = 0;
		for (int i = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				dest_length++;
			}
		}

		OJConstructor[] result = new OJConstructor[dest_length];
		for (int i = 0, count = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (modif.isPrivate() || modif.isProtected() || modif.isPublic()) {
				result[count++] = src_constrs[i];
			}
		}

		return result;
	}

	/**
	 * Generates an array of classes containing the source classes
	 * except ones with non-public access modifier;
	 * one of private, protected or package level access modifiers.
	 *
	 * @param  src_classes  source classes.
	 * @return  classes except ones with non-public access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJClass[] removeTheNonPublics(OJClass[] src_classes) {
		int dest_length = 0;
		for (int i = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (modif.isPublic())
				dest_length++;
		}

		OJClass[] result = new OJClass[dest_length];
		for (int i = 0, count = 0; i < src_classes.length; ++i) {
			OJModifier modif = src_classes[i].getModifiers();
			if (modif.isPublic())
				result[count++] = src_classes[i];
		}

		return result;
	}

	/**
	 * Generates an array of fields containing the source fields
	 * except ones with non-public access modifier;
	 * one of private, protected or package level access modifiers.
	 *
	 * @param  src_fields  source fields.
	 * @return  fields except ones with non-public access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJField[] removeTheNonPublics(OJField[] src_fields) {
		int dest_length = 0;
		for (int i = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (modif.isPublic())
				dest_length++;
		}

		OJField[] result = new OJField[dest_length];
		for (int i = 0, count = 0; i < src_fields.length; ++i) {
			OJModifier modif = src_fields[i].getModifiers();
			if (modif.isPublic())
				result[count++] = src_fields[i];
		}

		return result;
	}

	/**
	 * Generates an array of methods containing the source methods
	 * except ones with non-public access modifier;
	 * one of private, protected or package level access modifiers.
	 *
	 * @param  src_methods  source methods.
	 * @return  methods except ones with non-public access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJMethod[] removeTheNonPublics(OJMethod[] src_methods) {
		int dest_length = 0;
		for (int i = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (modif.isPublic())
				dest_length++;
		}

		OJMethod[] result = new OJMethod[dest_length];
		for (int i = 0, count = 0; i < src_methods.length; ++i) {
			OJModifier modif = src_methods[i].getModifiers();
			if (modif.isPublic())
				result[count++] = src_methods[i];
		}

		return result;
	}

	/**
	 * Generates an array of constructors containing the source constructors
	 * except ones with non-public access modifier;
	 * one of private, protected or package level access modifiers.
	 *
	 * @param  src_constrs  source constructors.
	 * @return  constructors except ones with non-public access modifier.
	 * @see openjava.mop.OJModifier
	 */
	public static final OJConstructor[] removeTheNonPublics(OJConstructor[] src_constrs) {
		int dest_length = 0;
		for (int i = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (modif.isPublic())
				dest_length++;
		}

		OJConstructor[] result = new OJConstructor[dest_length];
		for (int i = 0, count = 0; i < src_constrs.length; ++i) {
			OJModifier modif = src_constrs[i].getModifiers();
			if (modif.isPublic())
				result[count++] = src_constrs[i];
		}

		return result;
	}

	/**
	 * Pick up a field with the specified name in the source array
	 * of fields.
	 *
	 * @param  src_constrs  source fields.
	 * @param  name  a name to specify.
	 * @return  a field with the specified name.
	 * @see openjava.mop.OJClass
	 */
	public static final OJField pickupField(
		OJField[] src_fields,
		String name) {
		for (int i = 0; i < src_fields.length; ++i) {
			if (name.equals(src_fields[i].getName())) {
				return src_fields[i];
			}
		}
		return null;
	}

	/**
	 * Pick up a method with the specified signature in the source
	 * array of methods.
	 *
	 * @param  src_methods  source methods.
	 * @param  name  a name to specify.
	 * @param  param_types  parameter types to specify.
	 * @return  a method with the specified signature.
	 *		This returns null if it doesn't exist.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod pickupMethod(
		OJMethod[] src_methods,
		String name,
		OJClass[] param_types) {
		src_methods = pickupMethodsByName(src_methods, name);
		return pickupMethodByParameterTypes(src_methods, param_types);
	}

	/**
	 * Pick up a method with the signature acceptable the specified
	 * signature in the source array of methods.
	 *
	 * @param  src_constrs  source methods.
	 * @param  name  a name to specify.
	 * @param  param_types  parameter types to specify.
	 * @return  a method with the specified signature.
	 *		This returns null if it doesn't exist.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod pickupAcceptableMethod(
		OJMethod[] src_methods,
		String name,
		OJClass[] param_types) {
		src_methods = pickupAcceptableMethods(src_methods, name, param_types);
		return pickupMostSpecified(src_methods);
	}

	/**
	 * Generates an array of methods containing the methods with the
	 * signature acceptable the specified signature in the source
	 * array of methods.
	 *
	 * @param  src_methods  source methods.
	 * @param  name  a name to specify.
	 * @param  param_types  parameter types to specify.
	 * @return  methods with the specified signature.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod[] pickupAcceptableMethods(
		OJMethod[] src_methods,
		String name,
		OJClass[] param_types) {
		src_methods = pickupMethodsByName(src_methods, name);
		return pickupAcceptableMethodsByParameterTypes(
			src_methods,
			param_types);
	}

	/**
	 * Pick up a constructor with the specified signature in the source
	 * array of constructors.
	 *
	 * @param  src_constrs  source constructors.
	 * @param  name  a name to specify.
	 * @param  param_types  parameter types to specify.
	 * @return  a Constructor with the specified signature.
	 *		This returns null if it doesn't exist.
	 * @see openjava.mop.OJClass
	 */
	public static final OJConstructor pickupConstructor(
		OJConstructor[] src_constrs,
		OJClass[] param_types) {
		if (param_types == null)
			param_types = new OJClass[0];
		for (int i = 0; i < src_constrs.length; ++i) {
			OJClass[] accepter = src_constrs[i].getParameterTypes();
			if (isSame(accepter, param_types))
				return src_constrs[i];
		}
		return null;
	}

	/**
	 * Pick up a constructor with the signature acceptable the specified
	 * signature in the source array of constructors.
	 *
	 * @param  src_constrs  source constructors.
	 * @param  name  a name to specify.
	 * @param  param_types  parameter types to specify.
	 * @return  a constructor with the specified signature.
	 *		This returns null if it doesn't exist.
	 * @see openjava.mop.OJClass
	 */
	public static final OJConstructor pickupAcceptableConstructor(
		OJConstructor[] src_constrs,
		OJClass[] param_types) {
		src_constrs = pickupAcceptableConstructors(src_constrs, param_types);
		return pickupMostSpecified(src_constrs);
	}

	/**
	 * Generates an array of constructors containing the constructors
	 * with the specified parameter types in the source array of
	 * constructors.
	 *
	 * @param  src_constrs  source constructors.
	 * @param  param_types  parameter types to specify.
	 * @return  constructors acceptable the specified parameter types.
	 * @see openjava.mop.OJClass
	 */
	public static final OJConstructor[] pickupAcceptableConstructors(
		OJConstructor[] src_constrs,
		OJClass[] param_types) {
		param_types = (param_types == null) ? new OJClass[0] : param_types;
		int dest_length = 0;
		for (int i = 0; i < src_constrs.length; ++i) {
			OJClass[] accepter = src_constrs[i].getParameterTypes();
			if (isAcceptable(accepter, param_types))
				dest_length++;
		}

		OJConstructor[] result = new OJConstructor[dest_length];
		for (int i = 0, count = 0; i < src_constrs.length; ++i) {
			OJClass[] accepter = src_constrs[i].getParameterTypes();
			if (isAcceptable(accepter, param_types)) {
				result[count++] = src_constrs[i];
			}
		}

		return result;
	}

	/**
	 * Generates an array of methods containing the methods with the
	 * specified name in the source array of methods.
	 *
	 * @param  src_methods  source methods.
	 * @param  name  a name to specify.
	 * @return  methods with the specified name.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod[] pickupMethodsByName(
		OJMethod[] src_methods,
		String name) {
		int dest_length = 0;
		for (int i = 0; i < src_methods.length; ++i) {
			if (name.equals(src_methods[i].getName()))
				dest_length++;
		}

		OJMethod[] result = new OJMethod[dest_length];
		for (int i = 0, count = 0; i < src_methods.length; ++i) {
			if (name.equals(src_methods[i].getName())) {
				result[count++] = src_methods[i];
			}
		}

		return result;
	}

	/**
	 * Picks up a method with the specified parameter types in the source
	 * array of methods.
	 *
	 * @param  src_methods  source methods.
	 * @param  param_types  parameter types to specify.
	 * @return  a method with the specified parameter types.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod pickupMethodByParameterTypes(
		OJMethod[] src_methods,
		OJClass[] param_types) {
		if (param_types == null)
			param_types = new OJClass[0];
		for (int i = 0; i < src_methods.length; ++i) {
			OJClass[] accepter = src_methods[i].getParameterTypes();
			if (isSame(accepter, param_types))
				return src_methods[i];
		}
		return null;
	}

	/**
	 * Generates an array of methods containing the methods with the
	 * parameter types acceptable specified parameter types in the source
	 * array of methods.
	 *
	 * @param  src_methods  source methods.
	 * @param  param_types  parameter types to specify.
	 * @return  methods acceptable the specified parameter types.
	 * @see openjava.mop.OJClass
	 */
	public static final OJMethod[] pickupAcceptableMethodsByParameterTypes(
		OJMethod[] src_methods,
		OJClass[] param_types) {
		if (param_types == null)
			param_types = new OJClass[0];
		int dest_length = 0;
		for (int i = 0; i < src_methods.length; ++i) {
			OJClass[] accepter = src_methods[i].getParameterTypes();
			if (isAcceptable(accepter, param_types))
				dest_length++;
		}

		OJMethod[] result = new OJMethod[dest_length];
		for (int i = 0, count = 0; i < src_methods.length; ++i) {
			OJClass[] accepter = src_methods[i].getParameterTypes();
			if (isAcceptable(accepter, param_types)) {
				result[count++] = src_methods[i];
			}
		}

		return result;
	}

	public static final boolean isSame(
		OJClass[] accepter,
		OJClass[] acceptee) {
		if (accepter.length != acceptee.length)
			return false;
		for (int i = 0; i < acceptee.length; ++i) {
			if (accepter[i] != acceptee[i])
				return false;
		}
		return true;
	}

	public static final boolean isAcceptable(
		OJClass[] accepter,
		OJClass[] acceptee) {
		if (accepter.length != acceptee.length)
			return false;
		for (int i = 0; i < acceptee.length; ++i) {
			if (!accepter[i].isAssignableFrom(acceptee[i]))
				return false;
		}
		return true;
	}

	public static final boolean isAdaptableTo(
		OJClass[] adapter,
		OJClass[] adaptee) {
		if (adapter.length != adaptee.length)
			return false;
		for (int i = 0; i < adaptee.length; ++i) {
			if (!adaptee[i].isAssignableFrom(adapter[i]))
				return false;
		}
		return true;
	}

	public static final OJConstructor pickupMostSpecified(OJConstructor[] constrs) {
		if (constrs.length == 0)
			return null;
		OJConstructor most = constrs[0];
		for (int i = 0; i < constrs.length; ++i) {
			OJClass[] adapter = most.getParameterTypes();
			OJClass[] adaptee = constrs[i].getParameterTypes();
			if (!isAdaptableTo(adapter, adaptee))
				most = constrs[i];
		}
		return most;
	}

	public static final OJMethod pickupMostSpecified(OJMethod[] methods) {
		if (methods.length == 0)
			return null;
		OJMethod most = methods[0];
		for (int i = 0; i < methods.length; ++i) {
			OJClass[] adapter = most.getParameterTypes();
			OJClass[] adaptee = methods[i].getParameterTypes();
			if (!isAdaptableTo(adapter, adaptee))
				most = methods[i];
		}
		return most;
	}

	public static final OJClass[] append(OJClass[] a, OJClass[] b) {
		OJClass[] result = new OJClass[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static final OJField[] append(OJField[] a, OJField[] b) {
		OJField[] result = new OJField[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static final OJMethod[] append(OJMethod[] a, OJMethod[] b) {
		OJMethod[] result = new OJMethod[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static final OJConstructor[] append(
		OJConstructor[] a,
		OJConstructor[] b) {
		OJConstructor[] result = new OJConstructor[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static final String nameForJavaClassName(String jcname) {
		if (!jcname.startsWith("["))
			return jcname;

		String stripped = stripHeadBracket(jcname);
		String result;

		if (stripped.startsWith("[")) {
			/* array of array like "[[Ljava.lang.Object;" or "[[I" */
			result = (nameForJavaClassName(stripped) + "[]");
		} else if (stripped.endsWith(";")) {
			/* array of class type like "[Ljava.lang.Object;" */
			result = (stripped.substring(1, stripped.length() - 1) + "[]");
		} else {
			/* array of primitive type like "[I" */
			switch (stripped.charAt(stripped.length() - 1)) {
				case 'Z' :
					return "boolean[]";
				case 'B' :
					return "byte[]";
				case 'C' :
					return "char[]";
				case 'D' :
					return "double[]";
				case 'F' :
					return "float[]";
				case 'I' :
					return "int[]";
				case 'J' :
					return "long[]";
				case 'S' :
					return "short[]";
				default :
					return "<unknown primitive type>";
			}
		}
		return result.replace('$', '.');
	}

	public static final String nameToJavaClassName(String ojcname) {
		if (!ojcname.endsWith("[]"))
			return ojcname;

		String stripped = stripBrackets(ojcname);

		if (stripped.endsWith("[]")) {
			/* array of array like "java.lang.Object[][]" or "int[][]" */
			return ("[" + nameToJavaClassName(stripped));
		} else if (stripped.equals("boolean")) {
			return "[Z";
		} else if (stripped.equals("byte")) {
			return "[B";
		} else if (stripped.equals("char")) {
			return "[C";
		} else if (stripped.equals("double")) {
			return "[D";
		} else if (stripped.equals("float")) {
			return "[F";
		} else if (stripped.equals("int")) {
			return "[I";
		} else if (stripped.equals("long")) {
			return "[J";
		} else if (stripped.equals("short")) {
			return "[S";
		} else {
			return ("[L" + stripBrackets(ojcname) + ";");
		}
	}

	private static final String stripHeadBracket(String jcname) {
		return jcname.substring(1);
	}

	private static final String stripBrackets(String ojcname) {
		return ojcname.substring(0, ojcname.length() - 2);
	}

	public static final OJClass forNameAnyway(Environment env, String name) {
		name = env.toQualifiedName(name);
		OJClass result = env.lookupClass(name);
		if (result != null)
			return result;
		try {
			return OJClass.forName(name);
		} catch (OJClassNotFoundException e) {
			System.err.println(
				"OJClass.forNameAnyway() failed for : " + name + " " + e);
			System.err.print(env);
			return OJClass.forClass(Object.class);
		}
	}

	public static final OJClass[] arrayForNames(
		Environment env,
		String[] names) {
		OJClass[] result = new OJClass[names.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = forNameAnyway(env, names[i]);
		}
		return result;
	}

	public static final TypeName[] TNsForOJClasses(OJClass[] classes) {
		TypeName[] result =
			new TypeName[(classes == null) ? 0 : classes.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = TypeName.forOJClass(classes[i]);
		}
		return result;
	}

	private static final String PARAMETER_NAME = "oj_param";
	public static final ParameterList generateParameters(OJClass[] parameterTypes) {
		ParameterList result = new ParameterList();
		if (parameterTypes == null)
			return result;
		for (int i = 0; i < parameterTypes.length; ++i) {
			TypeName type = TypeName.forOJClass(parameterTypes[i]);
			Parameter param = new Parameter(type, PARAMETER_NAME + i);
			result.add(param);
		}
		return result;
	}

	public static final ParameterList generateParameters(
		OJClass[] parameterTypes,
		String[] parameterNames) {
		ParameterList result = new ParameterList();
		if (parameterTypes == null)
			return result;
		for (int i = 0; i < parameterTypes.length; ++i) {
			TypeName type = TypeName.forOJClass(parameterTypes[i]);
			Parameter param = new Parameter(type, parameterNames[i]);
			result.add(param);
		}
		return result;
	}

}
