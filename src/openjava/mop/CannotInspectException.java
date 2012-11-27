/*
 * CannotInspectException.java
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
 * The exception <code>CannotInspectException</code> is thrown if the
 * requested introspection cannot be performed on the class object,
 * the method object, or the field object.
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: CannotInspectException.java,v 1.2 2003/02/19 02:55:01 tatsubori Exp $
 * @see java.lang.Object
 */
public class CannotInspectException extends MOPException {

	public CannotInspectException() {
		super();
	}

	public CannotInspectException(String message) {
		super(message);
	}

}
