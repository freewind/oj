package examples.decorator;


import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class Test
{
    public static void main(String[] args) {
	Writer baseout
	    = new ReturnSpecifier(new OutputStreamWriter(System.out));
	PrintWriter out = new PrintWriter(baseout);
	out.println( "Hi there." );
	out.println( "This is a test." );
	out.println( "Thank you." );
	out.flush();
    }
	    
}
