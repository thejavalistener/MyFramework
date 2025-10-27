//package thejavalistener.fwk.util.reflect;
//
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.List;
//
//import thejavalistener.fwk.util.MyCollection;
//import thejavalistener.fwk.util.string.MyString;
//
//public class MyObject
//{
//	/**
//	 * Asigna a los $fields de $target el valor que dichos fields tienen en $object.
//	 */
////	public static <T> void clone(T object,T target,List<Field> fields)
////	{
////		clone(object,target,fields,(f)->true);
////	}
//
//	
//	/**
//	 * Copia todos los campos (locales y de las subclases), publicos o por accessors.
//	 */
//	public static <T> void clone(T object,T target)
//	{
//		List<Field> fields = MyClass.getFieldsInDept(object.getClass(),(f)->true);
//		clone(object,target,fields);
//	}
//	
//	/**
//	 * Asigna a los $fields de $target el valor que dichos fields tienen en $object.
//	 */
//	public static <T> void clone(T object,T target,List<Field> fields)
//	{
//		for(Field field:fields)
//		{
//			int mod = field.getModifiers();
//			if( !Modifier.isStatic(mod)&&!Modifier.isFinal(mod) )
//			{
//				Object value = getFieldValue(object,field);
//				setFieldValue(target,field.getName(),value);
//			}
//		}
//	}
//	
//	
////	public static <T> void clone(T object,T target,Field[] fields,Function<Field,Boolean> f)
////	{
////		List<Field> lstFields = ListUtil.toList(fields);
////		clone(object,target,lstFields,f);
////	}
//	
////	public static <T> void clone(T o1,T target,List<Field> fields,Function<Field,Boolean> filter)
////	{
////		for(Field field:fields)
////		{
////			int mod = field.getModifiers();
////			if( !Modifier.isStatic(mod)&&!Modifier.isFinal(mod) )
////			{
////				if( filter.apply(field) )
////				{
////					Object value = getFieldValue(o1,field);
////					setFieldValue(target,field.getName(),value);
////				}
////			}
////		}
////	}
//
//	public static void setFieldValueFromString(Object target,String fieldName,String sValue)
//	{
//		Field field = MyClass.getFieldInDepth(target.getClass(),fieldName);
//		setFieldValueFromString(target,field,sValue);
//	}
//	
//	public static void setFieldValueFromString(Object target,Field field,String sValue)
//	{
//		Object value = sValue;
//		String sType = field.getType().getName();
//		switch(sType)
//		{
//			case "java.lang.Integer": 
//			case "int": 
//				value = Integer.parseInt(sValue);
//				break;
//			case "long": 
//			case "java.lang.Long": 
//				value = Long.parseLong(sValue);
//				break;
//			case "short": 
//			case "java.lang.Short": 
//				value = Short.parseShort(sValue);
//				break;
//			case "java.char": 
//			case "java.lang.Character": 
//				value = sValue.charAt(0);
//				break;
//			case "double": 
//			case "java.lang.Double": 
//				value = Double.parseDouble(sValue);
//				break;
//			case "float":
//			case "java.lang.Float":
//				value = Float.parseFloat(sValue);
//				break;
//			case "boolean": 
//			case "java.lang.Boolean": 
//				value = sValue.toLowerCase().equals("true");
//		}
//		
//		setFieldValue(target,field.getName(),value);
//	}
//	
//	public static void setFieldValue(Object target,String fieldName,Object value)
//	{
//		Class<?> clazz = target.getClass();
//		Field field = null;
//
//		// orden: publico, local, o por setter
//		try
//		{
//			// si es publico => OK
//			field = clazz.getField(fieldName);			
//		}
//		catch(NoSuchFieldException e)
//		{
//			try
//			{
//				// publico no es. Entonces, si es local => OK
//				field = clazz.getDeclaredField(fieldName);
//				field.setAccessible(true);				
//			}
//			catch(Exception e2)
//			{
//			}
//		}
//
//		try
//		{
//			// si tengo acceso al campo, lo toco directo
//			if( field!=null )	
//			{
//				field.set(target,value);
//			}
//			else
//			{
//				// si no tengo acceso, voy por setter
//				invokeSetter(target,fieldName,value);
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//	
//	public static Object getFieldValue(Object target,Field field)
//	{
//		return getFieldValue(target,field.getName());
//	}
//	
//	public static Object getFieldValue(Object target,String fieldName)
//	{
//		Class<?> clazz = target.getClass();
//		Field field = null;
//
//		// orden: publico, local, o por getter
//		try
//		{
//			field = clazz.getField(fieldName);			
//		}
//		catch(NoSuchFieldException e)
//		{
//			try
//			{
//				field = clazz.getDeclaredField(fieldName);
//				field.setAccessible(true);				
//			}
//			catch(Exception e2)
//			{
//			}
//		}
//
//		try
//		{
//			// si tengo acceso al campo, lo toco directo
//			if( field!=null )	
//			{
//				return field.get(target);
//			}
//			else
//			{
//				// si no tengo acceso, voy por setter
//				return invokeGetter(target,fieldName);
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		
//	}
//	
//	public static void invokeSetter(Object target,String fieldName,Object value)
//	{
//		if( value==null ) return;
//		String setterName = MyMethod.getSetterName(fieldName);
//		invokeMethod(target,setterName,new Object[]{value});
//	}
//	
//	public static Object invokeGetter(Object target,String fieldName)
//	{
//		String getterName = MyMethod.getGetterName(fieldName);
//		return invokeMethod(target,getterName);
//	}
//	
//	/** Ejemplo: facade.validarPerfil o obj1.obj2.ejecutarTalCosa 
//	 *  Los parentesis son opcionales, pueden estar o no. */
//	public static Object invokeMethodByPath(Object target,String pathEndsWithMethod,Object ...args)
//	{
//		// separo el string por "."
//		List<String> lst = MyString.split(pathEndsWithMethod,".");
//		
//		// obtengo el nombre del metodo
//		String mtd = lst.get(lst.size()-1);
//		
//		// recorro a traves del path
//		Object aux = target;
//		for(int i=0; i<lst.size()-1;i++ )
//		{
//			String fieldName = lst.get(i);
//			aux = getFieldValue(aux,fieldName);
//		}
//		
//		// invoco
//		return invokeMethod(aux,mtd,args);
//	}
//	
//	public static Object invokeMethod(Object target,String mtdName,Object ...args)
//	{
//		try
//		{
//			List<Class<?>> argTypes = MyCollection.extract(args,(t)->t.getClass());
//			Method mtd = MyClass.getMethod(target.getClass(),mtdName,argTypes);
//			if( mtd!=null )
//			{
//				return mtd.invoke(target,args);
//			}			
//			else
//			{
//				throw new RuntimeException("El metodo: "+mtdName+", no se encuentra en: "+target.getClass().getName());
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//}
