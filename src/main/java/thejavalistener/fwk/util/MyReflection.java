package thejavalistener.fwk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import thejavalistener.fwk.util.reflect.MyClass;
import thejavalistener.fwk.util.string.MyString;

public class MyReflection
{
//	public static Object invokeGetter(Object o, String att)
//	{
//		try
//		{
//			String mtdName="get"+MyString.switchCase(att,0);
//			Method mtd=o.getClass().getMethod(mtdName);
//			return mtd.invoke(o);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}

//	public static void invokeSetter(Object o, String att, Object value)
//	{
//		try
//		{
//			Class<?> oClass=o.getClass();
//			String mtdName="set"+MyString.switchCase(att,0);
//
//			Method mtd;
//
//			if(value!=null)
//			{
//				mtd=oClass.getMethod(mtdName,value.getClass());
//			}
//			else
//			{
//				mtd=oClass.getMethod(mtdName,oClass.getDeclaredField(att).getType());
//			}
//
//			mtd.invoke(o,value);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}


//	public static boolean equalsById(Object a, Object b)
//	{
//		if(a==null&&b==null) return true;
//		if(a==null&&b!=null||a!=null&&b==null) return false;
//
//		Field aId=getDeclaredField(a.getClass(),Id.class);
//		Field bId=getDeclaredField(b.getClass(),Id.class);
//
//		Object aV=invokeGetter(a,aId.getName());
//		Object bV=invokeGetter(b,bId.getName());
//
//		return aV.equals(bV);
//	}
	
	static class runtime
	{
		public static void unsoportedMethod()
		{
			StackTraceElement ste[]=Thread.currentThread().getStackTrace();
			int p=ste.length>2?2:0;
			throw new RuntimeException("Unsoported Method:"+ste[p].getMethodName()+" in class:"+ste[p].getClassName());
		}

		public static StackTraceElement peekStackTrace()
		{
			StackTraceElement ste[]=Thread.currentThread().getStackTrace();
			int p=ste.length>3?3:0;
			return ste[p];
		}		
	}

	public static class clasz
	{
		@SuppressWarnings({"unchecked", "rawtypes"})
		public static boolean isAnnotedWith(Class<?> c, Class annClass)
		{
			return c.getAnnotation(annClass)!=null;
		}
		
		public static List<String> getAttributes(Class<?> clazz)
		{
			ArrayList<String> getters=new ArrayList<>();
			ArrayList<String> setters=new ArrayList<>();

			Method mtds[]=clazz.getDeclaredMethods();
			for(int i=0; i<mtds.length; i++)
			{
				String mtdName=mtds[i].getName();
				if(mtdName.startsWith("get"))
				{
					getters.add(mtdName.substring(3));
				}
				else
				{
					if(mtdName.startsWith("set"))
					{
						setters.add(mtdName.substring(3));
					}
				}
			}

			ArrayList<String> ret=new ArrayList<>();
			for(int i=0; i<getters.size(); i++)
			{
				String mtdName=getters.get(i);
				if(setters.contains(mtdName))
				{
					ret.add(MyString.switchCase(mtdName,0));
				}
			}

			return ret;
		}
		
		/** Retorna los declared fields que cumplan con la condición establecida por $func */
		public static Field[] getDeclaredFields(Class<?> clazz, Function<Field,Boolean> func)
		{
			Field[] fields=clazz.getDeclaredFields();
			return Stream.of(fields).filter(f -> func.apply(f)).toArray(Field[]::new);
		}

		/** Retorna los declared fields anotados con annClass */
		@SuppressWarnings({"unchecked", "rawtypes"})
		public static Field[] getDeclaredFields(Class<?> clazz, Class annClass)
		{
			Field[] fields=clazz.getDeclaredFields();
			return Stream.of(fields).filter(f -> f.getAnnotation(annClass)!=null).toArray(Field[]::new);
		}
		
		public static boolean isFinalClass(Class<?> c)
		{
			switch(c.getCanonicalName())
			{
				case "char", "java.lang.Character":
				case "short", "java.lang.Short":
				case "int", "java.lang.Integer":
				case "long", "java.lang.Long":
				case "float", "java.lang.Float":
				case "double", "java.lang.Double":
				case "boolean", "java.lang.Boolean":
				case "java.lang.String":
				case "java.sql.Date":
				case "java.sql.Timestamp":
				case "java.sql.Time":
					return true;
				default:
					return false;
			}
		}
		
		/** Retorna el primer field anotado con $annClass o null si no existe ninguno */
		public static Field getDeclaredField(Class<?> c, Class<?> annClass)
		{
			Field f[]=clasz.getDeclaredFields(c,annClass);
			return f!=null&&f.length>0?f[0]:null;
		}

		/** Es el viejo Class.forName pero capturando la excepción */
		public static Class<?> forName(String classname)
		{
			try
			{
				return Class.forName(classname);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		/** Retorna c.getConstructor().newInstance() pero capturando la excepción */
		public static <T> T newInstance(Class<T> c)
		{
			try
			{
				return c.getConstructor().newInstance();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public static class object
	{
		public static String toString(Object o, Function<Field,Boolean> func)
		{
			StringBuffer sb=new StringBuffer();
			Field[] fields=o.getClass().getDeclaredFields();

			for(int i=0; i<fields.length; i++)
			{
				Field f=fields[i];

				if(func.apply(f))
				{
					sb.append(f.getName()+"="+invokeGetter(o,f.getName()));
					sb.append(i<fields.length-1?",":"");
				}
			}

			return sb.toString();
		}
		
		/** Retorna los fieldValue de los campos de $target */
		public static List<Object> getValues(Object target)
		{
			List<Object> ret=new ArrayList<>();
			for(String att:clasz.getAttributes(target.getClass()))
			{
				ret.add(invokeGetter(target,att));
			}

			return ret;
		}
		
		/** Retorna el valor de $fieldName */
		public static Object getFieldValue(Object target, String fieldName)
		{
			try
			{
				Field f=target.getClass().getDeclaredField(fieldName);
				f.setAccessible(true);
				return f.get(target);
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		
		public static void invokeSetter(Object target,String fieldName,Object value)
		{
			if( value==null ) return;
			String setterName = method.getSetterName(fieldName);
			invokeMethod(target,setterName,new Object[]{value});
		}
		
		public static Object invokeGetter(Object target,String fieldName)
		{
			String getterName = method.getGetterName(fieldName);
			return invokeMethod(target,getterName);
		}
		
		/** Ejemplo: facade.validarPerfil o obj1.obj2.ejecutarTalCosa 
		 *  Los parentesis son opcionales, pueden estar o no. */
		public static Object invokeMethodByPath(Object target,String fullPathMethod,Object ...args)
		{
			// separo el string por "."
			List<String> lst = MyString.split(fullPathMethod,".");
			
			// obtengo el nombre del metodo
			String mtd = lst.get(lst.size()-1);
			
			// recorro a traves del path
			Object aux = target;
			for(int i=0; i<lst.size()-1;i++ )
			{
				String fieldName = lst.get(i);
				aux = object.getFieldValue(aux,fieldName);
			}
			
			// invoco
			return invokeMethod(aux,mtd,args);
		}
		
		/** Simplifica el viejo $target.getClass().getMethod($mtdName,$args) */
		public static Object invokeMethod(Object target,String mtdName,Object ...args)
		{
			try
			{
				List<Class<?>> argTypes = MyCollection.extract(args,(t)->t.getClass());
				Method mtd = MyClass.getMethod(target.getClass(),mtdName,argTypes);
				if( mtd!=null )
				{
					return mtd.invoke(target,args);
				}			
				else
				{
					throw new RuntimeException("El metodo: "+mtdName+", no se encuentra en: "+target.getClass().getName());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	static class method
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

}
