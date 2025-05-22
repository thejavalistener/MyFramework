package thejavalistener.fwk.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import thejavalistener.fwk.util.MyCollection;

public class MyClass
{
	@SuppressWarnings("unchecked")
	public static <T> Class<T> forName(String classname)
	{
		try
		{
			return (Class<T>)Class.forName(classname);
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
			return clazz.getDeclaredConstructor().newInstance();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T newInstance(String classname)
	{
		Class<T> clazz = forName(classname);
		return newInstance(clazz);
	}

	public static Class<?> getWrapperFor(Class<?> primitiveClass)
	{
		switch( primitiveClass.getName() )
		{
			case "byte":
				return Byte.class;
			case "char":
				return Character.class;
			case "short":
				return Short.class;
			case "int":
				return Integer.class;
			case "long":
				return Long.class;
			case "float":
				return Float.class;
			case "double":
				return Double.class;
			case "boolean":
				return Boolean.class;
			default:
				return primitiveClass;
		}		
	}
	
	public static Class<?> getPrimitiveFor(Class<?> wrapperClass)
	{
		switch( wrapperClass.getName() )
		{
			case "java.lang.Byte":
				return Byte.TYPE;
			case "java.lang.Character":
				return Character.TYPE;
			case "java.lang.Short":
				return Short.TYPE;
			case "java.lang.Integer":
				return Integer.TYPE;
			case "java.lang.Long":
				return Long.TYPE;
			case "java.lang.Float":
				return Float.TYPE;
			case "java.lang.Double":
				return Double.TYPE;
			case "java.lang.Boolean":
				return Boolean.TYPE;
			default:
				return wrapperClass;
		}		
	}
	

	public static boolean isFinalType(Class<?> clazz)
	{
		switch( clazz.getName() )
		{
			case "byte":
			case "char":
			case "short":
			case "int":
			case "long":
			case "float":
			case "double":
			case "boolean":
			case "java.lang.Byte":
			case "java.lang.Character":
			case "java.lang.Short":
			case "java.lang.Integer":
			case "java.lang.Long":
			case "java.lang.Float":
			case "java.lang.Double":
			case "java.lang.Boolean":
			case "java.util.Date":
			case "java.sql.Date":
			case "java.sql.Timestamp":
			case "java.sql.Time":
				return true;
			default:
				return false;
		}		
	}

	public static Method getSetter(Class<?> clazz,String fieldName)
	{
		Field field = getFieldInDepth(clazz,fieldName);
		return getSetter(clazz,field);			
	}
	
	public static Method getSetter(Class<?> clazz,Field f)
	{
		String setterName = MyMethod.getSetterName(f);
		return getMethod(clazz,setterName,new Class[]{f.getType()});
	}

	public static Method getGetter(Class<?> clazz,String fieldName)
	{
		Field field = getFieldInDepth(clazz,fieldName);
		return getGetter(clazz,field);					
	}
	
	public static Method getGetter(Class<?> clazz,Field f)
	{
		String getterName = MyMethod.getGetterName(f);
		return getMethod(clazz,getterName,new Class[]{});
	}

	/**
	 * Retorna el Field correspondiente a $attName, sea de la clase o de una clase base.
	 */	
	public static Field getFieldInDepth(Class<?> clazz,String attName)
	{
		Field field = null;
		Class<?> aux = clazz;
		while( !aux.equals(Object.class) && field==null )
		{
			try
			{
				field = aux.getDeclaredField(attName);				
			}
			catch(NoSuchFieldException e)
			{
				aux = aux.getSuperclass();
			}
		}	
		
		return field;
	}		
	
	public static List<Field> getFields(Class<?> clazz,Function<Field,Boolean> filter)
	{
		Field arr[] = clazz.getDeclaredFields();
		return MyCollection.extract(arr,(f)->f,filter);
	}
	
	public static List<Field> getFieldsInDept(Class<?> clazz,Function<Field,Boolean> filter)
	{
		ArrayList<Field> ret = new ArrayList<>();
		Class<?> aux = clazz;
		while( !aux.equals(Object.class) )
		{
			for(Field field:aux.getDeclaredFields())
			{
				if( filter.apply(field) )
				{
					ret.add(field);
				}
			}
			
			aux = aux.getSuperclass();
		}
		
		return ret;
	}

	public static Method getMethod(Class<?> clazz,String mtdName,List<Class<?>> argTypes)
	{
		Class<?>[] arrArgTypes = MyCollection.toArray(argTypes,Class.class);
		return getMethod(clazz,mtdName,arrArgTypes);
	}
	
	public static Method getMethod(Class<?> clazz,String mtdName,Class<?> argTypes[])
	{
		try
		{
			Method mtd = null;
			Class<?> aux = clazz;
			while( !aux.equals(Object.class) && mtd==null )
			{
				// todos los metodos de la clase
				Method mtds[] = aux.getDeclaredMethods();

				// filtro los metodos que coinciden con el nombre mtdName
				List<Method> mtds1 = MyCollection.extract(mtds,(t)->t,(t)->t.getName().equals(mtdName));

				if( mtds1.size()>0 )
				{
					// filtro por cantidad de parametros
					List<Method> mtds2 = MyCollection.extract(mtds1,(t)->t,(t)->t.getParameterCount()==argTypes.length);
					if( mtds2.size()>0 )
					{
						// filtro por tipo de parametros
						List<Method> mtds3 = MyCollection.extract(mtds2,(t)->t,(t)->_concuerdanParametros(t.getParameterTypes(),argTypes));
						if( mtds3.size()>0 )
						{
							return mtds3.get(0);
						}
					}
				}
				
				aux = aux.getSuperclass();
			}
			
			return mtd;		
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException();
		}		
	}
	
	public static Class<?> getGeneric(Class<?> clazz, String attname)
	{
		try
		{
			Field field=clazz.getDeclaredField(attname);
			Type genericType=field.getGenericType();

			if(genericType instanceof ParameterizedType)
			{
				Type[] typeArguments=((ParameterizedType)genericType).getActualTypeArguments();
				if(typeArguments.length>0&&typeArguments[0] instanceof Class<?>)
				{
					return (Class<?>)typeArguments[0];
				}
			}
		}
		catch(NoSuchFieldException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return null;
	}


	private static boolean _concuerdanParametros(Class<?> paramTypes[],Class<?> argTypes[])
	{
		for(int i=0;i<paramTypes.length;i++)
		{
			Class<?> p = getWrapperFor(paramTypes[i]);
			Class<?> a = getWrapperFor(argTypes[i]);
			if( !p.equals(a) )
			{
				return false;
			}
		}
		
		return true;
	}
}
