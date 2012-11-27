/*
 * VerboseClass.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1999 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package examples.verbose2;


import openjava.mop.*;
import openjava.ptree.*;
import openjava.syntax.*;
import openjava.ptree.util.PartialParser;


public class VerboseClass extends OJClass
{

    /* overrides for translation */
    public void translateDefinition() throws MOPException {
        OJMethod[] methods = getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
	    StatementList body = methods[i].getBody();
	    String str = PartialParser.replace(
	        "java.lang.System.out.println( \"#s\" );",
		methods[i].getName() + " is called."
		);
	    Statement printer = makeStatement( str );
	    body.insertElementAt( printer, 0 );
        }
    }

    /* constructor */

    public VerboseClass( Environment outer_env, OJClass declarer,
                     ClassDeclaration ptree ) {
        super( outer_env, declarer, ptree );
    }

    public VerboseClass( Class javaclass, MetaInfo minfo ) {
        super( javaclass, minfo );
    }

}

