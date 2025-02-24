package thejavalistener.fwk.util;
//package framework.util;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//import javax.persistence.Id;
//
//import app.mapping.Alumno;
//import app.mapping.Respuesta;
//
//public class MyBeanBKP
//{
//	/** Los fields pueden contener '.', por ejemplo: "alumno.curso.idCurso" */
//	public static Object[] invokeGetters(Object v,String ...fields)
//	{
//		Object[] ret = new Object[fields.length];
//		for(int i=0; i<fields.length; i++)
//		{
//			ret[i] = invokeGetter(v,fields[i]);
//		}
//		
//		return ret;
//	}
//	
//	public static void main(String[] args)
//	{
//		Respuesta r = new Respuesta();
//		r.setAlumno(new Alumno());
//		r.setIdRespuesta(2);
//		r.getAlumno().setNombre("Pepin");
//
//		Object[] ret=invokeGetters(r,"alumno.nombre","idRespuesta");
//		for(Object x:ret)
//		{
//			System.out.println(x);
//		}
//		
//	}
//
//	
//	public static void unsoportedMethod()
//	{
//		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
//		int p = ste.length>2?2:0;
//		throw new RuntimeException("Unsoported Method:"+ste[p].getMethodName()+" in class:"+ste[p].getClassName());
//	}
//
//	public static StackTraceElement peekStackTrace()
//	{
//		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
//		int p = ste.length>3?3:0;
//		return ste[p];
//	}
//	
//	public static List<String> getAttributes(Object o)
//	{
//		return getAttributes(o.getClass());
//	}
//	
//	public static String toString(Object o,Function<Field,Boolean> func)
//	{
//		StringBuffer sb = new StringBuffer();
//		Field[] fields = o.getClass().getDeclaredFields();
//		
//		for(int i=0; i<fields.length; i++)
//		{
//			Field f = fields[i];
//			
//			if( func.apply(f) )
//			{
//				sb.append(f.getName()+"="+invokeGetter(o,f.getName()));
//				sb.append(i<fields.length-1?",":"");
//			}
//		}
//		
//		return sb.toString();
//	}
//	
//	
//	@SuppressWarnings({"unchecked", "rawtypes"})
//	public static boolean isAnnotedWith(Object o,Class annClass)
//	{
//		return o.getClass().getAnnotation(annClass)!=null;
//	}
//	
//	public static List<String> getAttributes(Class<?> clazz)
//	{
//		ArrayList<String> getters = new ArrayList<>();
//		ArrayList<String> setters = new ArrayList<>();
//		
//		Method mtds[] = clazz.getDeclaredMethods();
//		for(int i=0; i<mtds.length; i++)
//		{
//			String mtdName = mtds[i].getName();
//			if( mtdName.startsWith("get") )
//			{
//				getters.add(mtdName.substring(3));
//			}
//			else
//			{
//				if( mtdName.startsWith("set") )
//				{
//					setters.add(mtdName.substring(3));
//				}
//			}
//		}
//		
//		ArrayList<String> ret = new ArrayList<>();
//		for(int i=0; i<getters.size(); i++)
//		{
//			String mtdName = getters.get(i);
//			if( setters.contains(mtdName) )
//			{
//				ret.add(MyString.switchCase(mtdName,0));
//			}
//		}
//		
//		return ret;
//	}
//
//	/** Los fields pueden contener '.', por ejemplo: "alumno.curso.idCurso" */
//	public static Object invokeGetter(Object o,String att)
//	{
//		Object aux = o;
//		List<String> partes = MyString.split(att,".");
//		for(String a:partes)
//		{
//			Object r = _invokeGetter(aux,a);
//			if( isFinalClass(r.getClass()) )
//			{
//				return r;
//			}
//			else
//			{
//				aux = r;
//			}
//		}
//		
//		return null;
//	}
//	
//	public static Object _invokeGetter(Object o,String att)
//	{
//		try
//		{
//			String mtdName = "get"+MyString.switchCase(att,0);
//			Method mtd = o.getClass().getMethod(mtdName);
//			return mtd.invoke(o);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//	
//	public static void invokeSetter(Object o,String att,Object value)
//	{
//		try
//		{
//			String mtdName = "set"+MyString.switchCase(att,0);
//			Method mtd = o.getClass().getMethod(mtdName,value.getClass());
//			mtd.invoke(o,value);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static List<Object> getValues(Object o)
//	{
//		List<Object> ret = new ArrayList<>();
//		for(String att:getAttributes(o))
//		{
//			ret.add(invokeGetter(o,att));
//		}
//		
//		return ret;
//	}
//	
//
//	
//	public static Field[] getDeclaredFields(Class<?> clazz,Function<Field,Boolean> func)
//	{
//		Field[] fields = clazz.getDeclaredFields();
//		return Stream.of(fields).filter(f->func.apply(f)!=null).toArray(Field[]::new);
//	}
//	
//
//	public static Field[] getDeclaredFields(Class<?> clazz,Class annotationClass)
//	{
//		Field[] fields = clazz.getDeclaredFields();
//		return Stream.of(fields).filter(f->f.getAnnotation(annotationClass)!=null).toArray(Field[]::new);
//	}
//	
//	public static boolean isFinalClass(Class<?> clazz)
//	{
//		switch(clazz.getCanonicalName())
//		{
//			case "char","java.lang.Character":
//			case "short","java.lang.Short":
//			case "int","java.lang.Integer":
//			case "long","java.lang.Long":
//			case "float","java.lang.Float":
//			case "double","java.lang.Double":
//			case "boolean","java.lang.Boolean":
//			case "java.lang.String":
//			case "java.sql.Date":
//			case "java.sql.Timestamp":
//			case "java.sql.Time":
//				return true;
//			default:
//				return false;
//		}
//	}
//
//
//	public static Field getDeclaredField(Class<?> clazz,Class<?> annotationClass)
//	{
//		Field f[] = getDeclaredFields(clazz,annotationClass);
//		return f!=null&&f.length>0?f[0]:null;
//	}
//
//	public static Class<?> newInstance(String entityName)
//	{
//		try
//		{
//			return Class.forName(entityName);			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	public static boolean equalsById(Object a, Object b)
//	{
//		if( a==null && b==null) return true;
//		if( a==null && b!=null || a!=null && b==null) return false;
//		
//		Field aId = getDeclaredField(a.getClass(),Id.class);
//		Field bId = getDeclaredField(b.getClass(),Id.class);
//		
//		Object aV = invokeGetter(a,aId.getName());
//		Object bV = invokeGetter(b,bId.getName());
//		
//		return aV.equals(bV);
//	}
//	
//	public static Method getMethod(Class<?> clazz,String mtdName,Class<?> argTypes[])
//	{
//		try
//		{
//			Method mtd = null;
//			Class<?> aux = clazz;
//			while( !aux.equals(Object.class) && mtd==null )
//			{
//				// todos los metodos de la clase
//				Method mtds[] = aux.getDeclaredMethods();
//
//				// filtro los metodos que coinciden con el nombre mtdName
//				List<Method> mtds1 = MyCollection.arrayToList(mtds,t->t.getName().equals(mtdName)?t:null);
//
//				if( mtds1.size()>0 )
//				{
//					// filtro por cantidad de parametros
//					List<Method> mtds2 = MyCollection.listToList(mtds1,t->((Method)t).getParameterCount()==argTypes.length?t:null);
//					if( mtds2.size()>0 )
//					{
//						// filtro por tipo de parametros
//						List<Method> mtds3 = MyCollection.listToList(mtds2,t->_concuerdanParametros(t.getParameterTypes(),argTypes)?t:null);
//						if( mtds3.size()>0 )
//						{
//							return mtds3.get(0);
//						}
//					}
//				}
//				
//				aux = aux.getSuperclass();
//			}
//			
//			return mtd;		
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			throw new RuntimeException();
//		}			
//	}
//	
//	private static boolean _concuerdanParametros(Class<?> paramTypes[],Class<?> argTypes[])
//	{
//		for(int i=0;i<paramTypes.length;i++)
//		{
//			Class<?> p = getWrapperFor(paramTypes[i]);
//			Class<?> a = getWrapperFor(argTypes[i]);
//			if( !p.equals(a) )
//			{
//				return false;
//			}
//		}
//		
//		return true;
//	}
//	
//	public static Class<?> getWrapperFor(Class<?> primitiveClass)
//	{
//		switch( primitiveClass.getName() )
//		{
//			case "byte":
//				return Byte.class;
//			case "char":
//				return Character.class;
//			case "short":
//				return Short.class;
//			case "int":
//				return Integer.class;
//			case "long":
//				return Long.class;
//			case "float":
//				return Float.class;
//			case "double":
//				return Double.class;
//			case "boolean":
//				return Boolean.class;
//			default:
//				return primitiveClass;
//		}		
//	}
//	
//	public static Class<?> getPrimitiveFor(Class<?> wrapperClass)
//	{
//		switch( wrapperClass.getName() )
//		{
//			case "java.lang.Byte":
//				return Byte.TYPE;
//			case "java.lang.Character":
//				return Character.TYPE;
//			case "java.lang.Short":
//				return Short.TYPE;
//			case "java.lang.Integer":
//				return Integer.TYPE;
//			case "java.lang.Long":
//				return Long.TYPE;
//			case "java.lang.Float":
//				return Float.TYPE;
//			case "java.lang.Double":
//				return Double.TYPE;
//			case "java.lang.Boolean":
//				return Boolean.TYPE;
//			default:
//				return wrapperClass;
//		}		
//	}
//
//	public static Object invokeMethod(Object target,String mtdName,Object ...args)
//	{
//		try
//		{
//			Class<?>[] argTypes = MyCollection.arrayToArray(args,(t)->t.getClass());
//			Method mtd = getMethod(target.getClass(),mtdName,argTypes);
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
//	
//}
