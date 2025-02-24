package thejavalistener.fwk.util.reflect;

import java.lang.reflect.Field;

import thejavalistener.fwk.util.string.MyString;

public class MyMethod
{
	public static String getSetterName(String attName)
	{
		return "set"+MyString.switchCase(attName,0);
	}
	
	public static String getGetterName(String attName)
	{
		return "get"+MyString.switchCase(attName,0);
	}
		
	public static String getSetterName(Field f)
	{
		return getSetterName(f.getName());
	}
	
	public static String getGetterName(Field f)
	{
		return "get"+MyString.switchCase(f.getName(),0);
	}
}
