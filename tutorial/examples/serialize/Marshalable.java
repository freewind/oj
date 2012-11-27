package examples.serialize;

import java.io.*;

public interface Marshalable
{
	void readObject(ObjectIn is) throws IOException;
	void writeObject(ObjectOut os) throws IOException;
}
