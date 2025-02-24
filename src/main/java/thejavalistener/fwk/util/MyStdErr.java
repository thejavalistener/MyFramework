package thejavalistener.fwk.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.springframework.stereotype.Component;

@Component
public class MyStdErr
{
    private ByteArrayOutputStream os = new ByteArrayOutputStream();
    private PrintStream defaultStdErr;
    private PrintStream customStdErr;

    public MyStdErr()
    {
    	defaultStdErr = System.err;    	
        os = new ByteArrayOutputStream();
        customStdErr = new PrintStream(os);
    }
    
    public void open()
	{
        System.setErr(customStdErr);
	}
    
    public String close()
    {
    	try
		{
        	os.flush();
        	String s = os.toString();
        	os.reset();
        	
        	System.setErr(defaultStdErr);
        	
        	
        	
        	
        	return s;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public static void main(String[] args)
	{
		MyStdErr x = new MyStdErr();
		x.open();
		
		Integer.parseInt("popo");
		
		x.close();
		Integer.parseInt("pepepe");
	}
}
