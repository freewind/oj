/*
 * OJMember.java
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


/**
 * The class <code>OJMember</code> is equivalent to Member
 *
 *
 * @author   Michiaki Tatsubori
 * @version  $Id: OJMember.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see	java.lang.reflect.Member
 */
public interface OJMember {
    public static final int PUBLIC = 0;
    public static final int DECLARED = 1;

    public OJClass getDeclaringClass();
    public String getName();
    public OJModifier getModifiers();
    public Signature signature();
    public Environment getEnvironment();
}
