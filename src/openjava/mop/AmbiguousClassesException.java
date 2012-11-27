/*
 * AmbiguousClassesException.java
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.mop;




/**
 * The exception <code>AmbiguousClassesException</code> is thrown if the
 * additional <code>OJClass</code> object has the same name with another
 * <code>OJClass</code> object's.
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: AmbiguousClassesException.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class AmbiguousClassesException extends MOPException {
    public AmbiguousClassesException( String access ) {
	super( access );
    }
}
