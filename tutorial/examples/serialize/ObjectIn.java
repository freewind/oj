package examples.serialize;

import java.io.*;
import java.util.*;

public class ObjectIn extends DataInputStream
{
    private Vector readObjects = new Vector();
    
    public ObjectIn(InputStream is) {
	super(is);
    }
    
    public Marshalable readObject() throws IOException {
	int id = readInt();
	if (id == -1)  return null; /* -1 implies null  */
	if (id == -2) {
	    id = readInt();
	    return (Marshalable) readObjects.elementAt(id);
	}
	
	String classname = readUTF();
	Marshalable obj;
	try {
	    obj = (Marshalable) Class.forName(classname).newInstance();
	} catch (Exception ex) {
	    throw new IOException(ex.toString());
	}
	readObjects.addElement(obj);
	obj.readObject(this);
	
	return obj;
    }
}
