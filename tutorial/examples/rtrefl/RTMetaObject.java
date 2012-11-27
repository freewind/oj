package examples.rtrefl;


import java.lang.reflect.*;


/**
 * The class RTMetaObject
 * Exception handling is not implemented.
 */
public class RTMetaObject
{
    private RTMetaLevel baseObj;

    public RTMetaObject(RTMetaLevel base_obj) {
	this.baseObj = base_obj;
    }

    public Object trapMethodCall(String name, Class[] paramTypes, Object[] args)
    {
	try {
	    Method method = baseObj.getClass().getMethod(name, paramTypes);
	    return method.invoke(baseObj, args);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public Object trapFieldRead(String name) {
	try {
	    Field field = baseObj.getClass().getField(name);
	    return field.get(baseObj);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void trapFieldWrite(String name, Object rvalue) {
	try {
	    Field field = baseObj.getClass().getField(name);
	    field.set(baseObj, rvalue);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}


/*****
class Foo {
    int f(int a) {}
    int l;
}
...
Foo foo;  foo.l = expr;  a = foo.l;

->

class Foo implements Metalevel {
    public examples.rtrefl.RTMetaObject mt;
    int org_f(int a) {}
    int f(int a) {
        Object[] args = new Object[]{ new Integer(a) };
        Class[] argTypes = new Class[]{ int.class };
	Object result = mt.trapMethodCall("org_f", argTypes, args);
        return ((Integer) result).intValue();
    }
    int read_l() {
        Object result = mt.trapFieldRead("l");
        return ((Integer) result).intValue();
    }
    int write_l(int rvalue) {
        mt.trapFieldWrite("l", new Integer(rvalue));
        return rvalue;
    }
}
...
Foo foo;  foo.write_l(expr);  a = foo.read_l();
*****/
