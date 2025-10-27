//package thejavalistener.fwk.util.reflect;
//
//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.function.Function;
//
//import thejavalistener.fwk.util.MyCollection;
//
//public class MyPackage
//{
//	public static List<Class<?>> getClasses(String packageName,boolean recursive) 
//	{
//		return getClasses(packageName,recursive,(c)->true);
//	}
//	
//	public static List<Class<?>> getClasses(String packageName,boolean recursive,Function<Class<?>,Boolean> filter) 
//	{
//		try
//		{
//			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
//			assert classLoader!=null;
//			
//			String path=packageName.replace('.','/');
//			Enumeration<URL> resources = classLoader.getResources(path);
//			List<File> dirs = new ArrayList<File>();
//			while(resources.hasMoreElements())
//			{
//				URL resource=resources.nextElement();
//				dirs.add(new File(resource.getFile()));
//			}
//			
//			List<Class<?>> ret = new ArrayList<>();
//			for(File directory:dirs)
//			{
//				ret.addAll(_findClasses(directory,packageName,recursive));
//			}
//			
//			ret = MyCollection.extract(ret,(t)->t,filter);
//			return ret;
//			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	private static List<Class<?>> _findClasses(File dir, String auxPkgName,boolean recursive) 
//	{
//		try
//		{
//			List<Class<?>> ret = new ArrayList<>();
//			if(!dir.exists())
//			{
//				return ret;
//			}
//			
//			File[] files=dir.listFiles();
//			for(File file:files)
//			{
//				if(file.isDirectory() && recursive)
//				{
//					assert !file.getName().contains(".");
//					ret.addAll(_findClasses(file,auxPkgName+"."+file.getName(),recursive));
//				}
//				else if(file.getName().endsWith(".class"))
//				{
//					Class<?> clazz = Class.forName(auxPkgName+'.'+file.getName().substring(0,file.getName().length()-6));
//					ret.add(clazz);
//				}
//			}
//			return ret;			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//	
////	public static void main(String[] args)
////	{
//////		Function<Class<?>,Boolean> filter = (c)->c.getAnnotation(Form.class)!=null;
//////		Function<Class<?>,Boolean> filter = (c)->true;
////		List<Class<?>> lst = MyPackage.getClasses("z.futbol.screens",true,filter);
////		for(Class<?> clazz:lst)
////		{
////			System.out.println(clazz.getName());
////		}
////	}
//}
