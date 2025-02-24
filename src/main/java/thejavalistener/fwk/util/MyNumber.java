package thejavalistener.fwk.util;

import java.text.DecimalFormat;

import thejavalistener.fwk.util.string.MyString;

public class MyNumber
{

	public static double parseDouble(Object o)
	{
		return (Double)o;
	}

	public static int length(Object o)
	{
		return o.toString().length();
	}

	public static boolean isDouble(Object o)
	{
		Class<?> clazz=o.getClass();
		if(clazz.isAssignableFrom(Double.class)||clazz.isAssignableFrom(Double.TYPE))
		{
			return true;
		}
		if(clazz.isAssignableFrom(Float.class)||clazz.isAssignableFrom(Float.TYPE))
		{
			return true;
		}

		return false;
	}

	public static boolean isNumber(Object o)
	{
		Class<?> clazz=o.getClass();
		if(clazz.isAssignableFrom(Integer.class)||clazz.isAssignableFrom(Integer.TYPE))
		{
			return true;
		}

		if(clazz.isAssignableFrom(Long.class)||clazz.isAssignableFrom(Long.TYPE))
		{
			return true;
		}
		if(clazz.isAssignableFrom(Short.class)||clazz.isAssignableFrom(Short.TYPE))
		{
			return true;
		}

		if(clazz.isAssignableFrom(Double.class)||clazz.isAssignableFrom(Double.TYPE))
		{
			return true;
		}
		if(clazz.isAssignableFrom(Float.class)||clazz.isAssignableFrom(Float.TYPE))
		{
			return true;
		}

		return false;
	}

	public static int rndInt(int max)
	{
		return rndInt(0,max);
	}

	public static int rndInt(int min, int max)
	{
		return (int)(Math.random()*(max-min))+min;
	}

	public static int round(double d, int criterio)
	{
		int parteEntera=(int)d;
		double parteDecimal=d-parteEntera;

		if(parteDecimal<0.5)
		{
			return parteEntera;
		}
		if(parteDecimal>0.5)
		{
			return parteEntera+1;
		}
		if(criterio<0)
		{
			// Redondeo pesimista
			return parteEntera;
		}
		// Redondeo optimista
		return parteEntera+1;
	}
	
	/** Omite el sufijo .00 según prefNoDec sea true o false */
	public static String format(double d,boolean prefNoDec)
	{
		String x = format(d);
		return x.endsWith(".00")&&prefNoDec?x.substring(0,x.indexOf('.')):x;
	}
	
	/** Retorna la representación de d  con dos decimales */
	public static String format(double d)
	{
		return format(d,"0.00");
	}
	
	/** Retorna la representación de d según patter, usando DateFormat */
	public static String format(double d,String pattern)
	{
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(d);
	}
	
	/** Si n es double o float lo retorna con dos decimales. Si es entero lo retorna con 3 espacios a la derecha */
	public static String formatNumber(Number n)
	{
		String ret;
		if(n instanceof Double )
		{
			ret = format((Double)n);
		}
		else
		{
			if( n instanceof Float )
			{
				ret = format((Float)n);
			}
			else
			{
				ret = n.toString()+"   ";
			}
		}
		
		return ret;
	}


	
	public static void main(String[] args)
	{
		System.out.println( format(round(2.5,-1)));
		
	}
}
