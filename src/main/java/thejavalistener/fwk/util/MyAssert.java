package thejavalistener.fwk.util;

import java.util.function.Supplier;

public class MyAssert
{
	public static void test(boolean expr,String msgErr)
	{
		if( !expr )
		{
			throw new RuntimeException(msgErr);
		}
	}
	
	public static boolean throwsException(Supplier<?> p)
	{
		try
		{
			p.get();
			return false;
		}
		catch(Exception e)
		{
		}
		
		return true;
	}
}
