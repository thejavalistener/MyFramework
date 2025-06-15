package thejavalistener.fwk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.persistence.Id;

import thejavalistener.fwk.util.string.MyString;

public class MyBean
{
	public static void unsoportedMethod()
	{
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
		int p = ste.length>2?2:0;
		throw new RuntimeException("Unsoported Method:"+ste[p].getMethodName()+" in class:"+ste[p].getClassName());
	}

	public static StackTraceElement peekStackTrace()
	{
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
		int p = ste.length>3?3:0;
		return ste[p];
	}
	
	public static List<String> getAttributes(Object o)
	{
		return getAttributes(o.getClass());
	}
	
	public static String toString(Object o,Function<Field,Boolean> func)
	{
		StringBuffer sb = new StringBuffer();
		Field[] fields = o.getClass().getDeclaredFields();
		
		for(int i=0; i<fields.length; i++)
		{
			Field f = fields[i];
			
			if( func.apply(f) )
			{
				sb.append(f.getName()+"="+invokeGetter(o,f.getName()));
				sb.append(i<fields.length-1?",":"");
			}
		}
		
		return sb.toString();
	}
	
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static boolean isAnnotedWith(Object o,Class annClass)
	{
		return o.getClass().getAnnotation(annClass)!=null;
	}
	
	public static List<String> getAttributes(Class<?> clazz)
	{
		ArrayList<String> getters = new ArrayList<>();
		ArrayList<String> setters = new ArrayList<>();
		
		Method mtds[] = clazz.getDeclaredMethods();
		for(int i=0; i<mtds.length; i++)
		{
			String mtdName = mtds[i].getName();
			if( mtdName.startsWith("get") )
			{
				getters.add(mtdName.substring(3));
			}
			else
			{
				if( mtdName.startsWith("set") )
				{
					setters.add(mtdName.substring(3));
				}
			}
		}
		
		ArrayList<String> ret = new ArrayList<>();
		for(int i=0; i<getters.size(); i++)
		{
			String mtdName = getters.get(i);
			if( setters.contains(mtdName) )
			{
				ret.add(MyString.switchCase(mtdName,0));
			}
		}
		
		return ret;
	}
	
	public static Object invokeGetter(Object o,String att)
	{
		try
		{
			String mtdName = "get"+MyString.switchCase(att,0);
			Method mtd = o.getClass().getMethod(mtdName);
			return mtd.invoke(o);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void invokeSetter(Object o,String att,Object value)
	{
		try
		{
			Class<?> oClass = o.getClass();
			String mtdName = "set"+MyString.switchCase(att,0);
			
			Method mtd;
			
			if( value!=null )
			{
				mtd = oClass.getMethod(mtdName,value.getClass());
			}
			else
			{
				mtd = oClass.getMethod(mtdName,oClass.getDeclaredField(att).getType());				
			}
			
			mtd.invoke(o,value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	

	public static List<Object> getValues(Object o)
	{
		List<Object> ret = new ArrayList<>();
		for(String att:getAttributes(o))
		{
			ret.add(invokeGetter(o,att));
		}
		
		return ret;
//		return ret.size()>0?ret:List.of(o);  // <-- SI DA PROBLEMAS VOLVER AL: return ret;
	}
	
	public static void main(String[] args)
	{
	}

	
	public static Field[] getDeclaredFields(Class<?> clazz,Function<Field,Boolean> func)
	{
		Field[] fields = clazz.getDeclaredFields();
		return Stream.of(fields).filter(f->func.apply(f)).toArray(Field[]::new);
	}
	

	public static Field[] getDeclaredFields(Class<?> clazz,Class annotationClass)
	{
		Field[] fields = clazz.getDeclaredFields();
		return Stream.of(fields).filter(f->f.getAnnotation(annotationClass)!=null).toArray(Field[]::new);
	}
	
	public static boolean isFinalClass(Class<?> clazz)
	{
		switch(clazz.getCanonicalName())
		{
			case "char","java.lang.Character":
			case "short","java.lang.Short":
			case "int","java.lang.Integer":
			case "long","java.lang.Long":
			case "float","java.lang.Float":
			case "double","java.lang.Double":
			case "boolean","java.lang.Boolean":
			case "java.lang.String":
			case "java.sql.Date":
			case "java.sql.Timestamp":
			case "java.sql.Time":
				return true;
			default:
				return false;
		}
	}


	public static Field getDeclaredField(Class<?> clazz,Class<?> annotationClass)
	{
		Field f[] = getDeclaredFields(clazz,annotationClass);
		return f!=null&&f.length>0?f[0]:null;
	}

	public static Class<?> forName(String entityName)
	{
		try
		{
			return Class.forName(entityName);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T newInstance(Class<T> clazz)
	{
		try
		{
			return clazz.getConstructor().newInstance();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public static boolean equalsById(Object a, Object b)
	{
		if( a==null && b==null) return true;
		if( a==null && b!=null || a!=null && b==null) return false;
		
		Field aId = getDeclaredField(a.getClass(),Id.class);
		Field bId = getDeclaredField(b.getClass(),Id.class);
		
		Object aV = invokeGetter(a,aId.getName());
		Object bV = invokeGetter(b,bId.getName());
		
		return aV.equals(bV);
	}
	
	
}
