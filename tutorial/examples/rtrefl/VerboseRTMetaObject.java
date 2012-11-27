package examples.rtrefl;


import java.lang.reflect.*;


/**
 * The class VerboseRTMetaObject
 * Exception handling is not implemented.
 */
public class VerboseRTMetaObject extends RTMetaObject
{
    RTMetaLevel baseObj;

    public VerboseRTMetaObject(RTMetaLevel base_obj) {
	super(base_obj);
	this.baseObj = base_obj;
    }

    public Object trapMethodCall(String name, Class[] paramTypes, Object[] args)
    {
	System.out.println(baseObj.getClass().getName() + "." +
			   name + "()" + " is called." );
	return super.trapMethodCall(name, paramTypes, args);
    }

    public Object trapFieldRead(String name) {
	System.out.println(baseObj.getClass().getName() + "." +
			   name + " is read." );
	return super.trapFieldRead(name);
    }

    public void trapFieldWrite(String name, Object rvalue) {
	System.out.println(baseObj.getClass().getName() + "." +
			   name + " is written." );
	super.trapFieldWrite(name, rvalue);
    }
}
