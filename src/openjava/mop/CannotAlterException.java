/*
 * CannotAlterException.java
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.mop;




/**
 * CannotAlterException is thrown if the requested change cannot be
 * performed on the class object, the method object, or the field object.
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: CannotAlterException.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class CannotAlterException extends MOPException {
    public CannotAlterException( String access ) {
	super( access );
    }
}
