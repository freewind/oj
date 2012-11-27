/*
 * TestMetaclass.java
 * Michiaki Tatsubori
 *
 */
package metatest;

/**
 * This code is 
 */
public class TestMetaclass {

	static String[] userModifiers =
		{ "changable", "persistant", "distributed", "nop" };
	static String[] userKeywords = { "on", "of", "with" };

	public static boolean isRegisteredModifier(String name) {
		if (name == null || userModifiers == null)
			return false;
		for (int i = 0; i < userModifiers.length; i++) {
			if (name.equals(userModifiers[i]))
				return true;
		}
		return false;
	}

	public static boolean isRegisteredKeyword(String name) {
		if (name == null || userKeywords == null)
			return false;
		for (int i = 0; i < userKeywords.length; i++) {
			if (name.equals(userKeywords[i]))
				return true;
		}
		return false;
	}

}
