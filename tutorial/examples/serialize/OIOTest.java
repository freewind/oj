package examples.serialize;

import javax.microedition.midlet.MIDlet;
import java.io.*;

public class OIOTest extends MIDlet
{

	public void startApp() {
		try {
			main();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main() throws IOException {
		ColoredPoint p = new ColoredPoint();
		p.point.x = 10;  p.point.y = 20;

		/* Writes objects */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOut oos = new ObjectOut(baos);

		oos.writeObject(p);
		p.color.g = (byte)2;
		oos.writeObject(p);
		oos.flush();

		byte[] buf = baos.toByteArray();
		
		/* Reads objects */
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		ObjectIn ois = new ObjectIn(bais);

		ColoredPoint p2 = (ColoredPoint) ois.readObject();
		ColoredPoint p3 = (ColoredPoint) ois.readObject();
		
		System.out.println("p2.color.r = " + p2.color.r);
		System.out.println("p2.color.g = " + p2.color.g);
		System.out.println("p2.color.b = " + p2.color.b);
		System.out.println("p2.point.x = " + p2.point.x);
		System.out.println("p2.point.y = " + p2.point.y);
		System.out.println("p3.color.r = " + p3.color.r);
		System.out.println("p3.color.g = " + p3.color.g);
		System.out.println("p3.color.b = " + p3.color.b);

	}

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

}
