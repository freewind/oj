package examples.serialize;

import java.io.*;
import java.util.*;

class ObjectOut extends DataOutputStream
{
    private Vector writtenObjects = new Vector();
    
    public ObjectOut(OutputStream os) {
	super(os);
    }
    
    public void writeObject(Marshalable obj) throws IOException {
	if (obj == null) {
	    writeInt(-1);
	} else {
	    int id = 0;
	    int size = writtenObjects.size();
	    for (; id < size; ++id) {
		if (writtenObjects.elementAt(id) == obj)  break;
	    }
	    if (id < size) {
		/* this object is already written to this stream */
		writeInt(-2);
		writeInt(id);
	    } else {
		/* this object is not regisered in the table */
		writeInt(id);
		writtenObjects.addElement(obj);
		obj.writeObject(this);
	    }
	}
    }
    
}
