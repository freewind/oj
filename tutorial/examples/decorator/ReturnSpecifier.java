package examples.decorator;


import java.io.Writer;
import java.io.IOException;


/*
 * Here assumes there's no implementation in Writer
 * like 'implements Writer'
 */
public class ReturnSpecifier
    extends Writer
{
    private static final char[] mark = { ' ', '<' };

    private Writer out;

    public ReturnSpecifier(Writer out) {
	this.out = out;
    }

    public void write(char cbuf[], int off, int len)
	throws IOException 
    {
	int done = off;
	for (int i = done; i < off + len; ++i) {
	    if (cbuf[i] == Character.LINE_SEPARATOR
		|| cbuf[i] == '\n')
	    {
		out.write(cbuf, done, i - done);
		// print a mark for carriage return
		out.write(mark, 0, mark.length);
		// print a carriage return
		out.write(cbuf, i, 1);
		done = i + 1;
	    }
	}
	out.write(cbuf, done, off + len - done);
    }

    public void close() throws IOException {
	out.close();
    }

    public void flush() throws IOException {
	out.flush();
    }

/*****
    public void write(char[] cbuf) throws IOException {
	out.write(cbuf);
    }

    public void write(int c) throws IOException {
	out.write(c);
    }

    public void write(String str) throws IOException {
	out.write(str);
    }

    public void write(String str, int off, int len) throws IOException {
	out.write(str, off, len);
    }
*****/
}
