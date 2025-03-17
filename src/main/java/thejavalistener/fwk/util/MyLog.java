package thejavalistener.fwk.util;

import java.util.function.Function;

public class MyLog
{
	private static final Function<StackTraceElement,Boolean> disposable=(ste) -> ste.getClassName().startsWith("java.lang")||ste.getClassName().equals(MyLog.class.getName());

	private static String sout()
	{
		StackTraceElement ste=currStackTrace();

		// Obtener el nombre de la clase y el método
		String className=ste.getClassName();
		String methodName=ste.getMethodName();
		int lineNumber=ste.getLineNumber();

		return className+", "+methodName+":"+lineNumber;
	}

	public static void println()
	{
		System.out.println(sout());
	}

	public static void println(Object o)
	{
		String mssg = o!=null?o.toString():"null (param is null)";
		System.out.println(mssg+" ("+sout()+")");
	}
	
//	public static void println(String message)
//	{
//		System.out.println(message+" ("+sout()+")");
//	}

	public static StackTraceElement currStackTrace()
	{
		return currStackTrace(f -> false);
	}

	public static StackTraceElement currStackTrace(Function<StackTraceElement,Boolean> discart)
	{
		StackTraceElement ste[]=Thread.currentThread().getStackTrace();
		int i=0;
		while(disposable.apply(ste[i]))
		{
			i++;
		}

		while(discart.apply(ste[i]))
		{
			i++;
		}

		return i<ste.length?ste[i]:null;
	}
}
