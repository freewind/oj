/*
 * Meta2.java
 * Michiaki Tatsubori
 *
 */
package metatest;


/**
 * This code is 
 */
public class Meta2
{

  static String[] userModifiers
  = {};
  static String[] userKeywords
  = { "from", "to" };

  public static boolean isRegisteredModifier( String name )
  {
    if(name == null || userModifiers == null)  return false;
    for( int i = 0; i < userModifiers.length; i++ ){
      if(name.equals( userModifiers[i] ))  return true;
    }
    return false;
  }

  public static boolean isRegisteredKeyword( String name )
  {
    if(name == null || userKeywords == null)  return false;
    for( int i = 0; i < userKeywords.length; i++ ){
      if(name.equals( userKeywords[i] ))  return true;
    }
    return false;
  }

}
