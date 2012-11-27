/*
 * OJEditableClass.java
 * 
 * Created on 2003/11/02 $Id: OJEditableClass.java,v 1.1 2003/11/03 00:45:10 tatsubori Exp $
 */
package openjava.mop.edit;

import openjava.mop.CannotAlterException;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.mop.OJConstructor;
import openjava.mop.OJField;
import openjava.mop.OJMethod;
import openjava.mop.OJSystem;

/**
 * The type OJEditableClass.
 * 
 * @author Michiaki Tatsubori
 * @version 1.0 2003/11/02
 */
public class OJEditableClass extends OJClass {

    public OJEditableClass(OJClass original)
        throws CannotAlterException {
        super(
            new FileEnvironment(
                OJSystem.env,
                original.getPackage(),
                original.getSimpleName()),
            original.getDeclaringClass(),
            original.getSourceCode());
    }

    public OJClass addClass(OJClass clazz)
        throws CannotAlterException {
        return super.addClass(clazz);
    }

    public OJConstructor addConstructor(
        OJConstructor constr)
        throws CannotAlterException {
        return super.addConstructor(constr);
    }

    public OJField addField(OJField field)
        throws CannotAlterException {
        return super.addField(field);
    }

    public void addInterface(OJClass clazz)
        throws CannotAlterException {
        super.addInterface(clazz);
    }

    public OJMethod addMethod(OJMethod method)
        throws CannotAlterException {
        return super.addMethod(method);
    }

}
