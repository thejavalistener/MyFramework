package thejavalistener.fwk.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MyCollection
{
	public static <T> boolean isNotIn(T t,T ...args)
	{
		return !isIn(t,args);
	}
	
	public static <T> boolean isIn(T t,T ...args)
	{
		if(t!=null )
		{
			for(T i:args)
			{
				if( t.equals(i) )
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static <T> void forEach(T[] arr,Consumer<T> c)
	{
		for(T t:arr)
		{
			c.accept(t);
		}
	}
	
	public static <T> void forEach(List<T> lst,Consumer<T> c)
	{
		for(T t:lst)
		{
			c.accept(t);
		}
	}
	
	public static <K,T> void forEach(Map<K,T> map,BiConsumer<K,T> c)
	{
		for(Map.Entry<K,T> entry : map.entrySet()) 
		{
		    K key = entry.getKey();
		    T value = entry.getValue();
		    c.accept(key,value);
		}
	}

	public static <T,R> R reduce(T[] arr,R initValue,BiFunction<T,R,R> f)
	{
		return reduce(toList(arr),initValue,f);
	}

	public static <T,R> R reduce(List<T> lst,R initValue,BiFunction<T,R,R> f)
	{
		R r = initValue;
		for(T t:lst)
		{
			r = f.apply(t,r);
		}		
		
		return r;
	}
	
//	public static <T> T[] toArray(List<T> lst)
//	{
//		T ret[] = (T[])new Object[lst.size()];
//		int i=0;
//		for(T t:lst)
//		{
//			ret[i++] = t;
//		}
//		
//		return ret;
//	}
	
	public static <T,R> List<R> extract(T[] arr,Function<T,R> tToR)
	{
		List<T> list = toList(arr);
		return extract(list,tToR);
	}
	
	public static <T,R> List<R> extract(Collection<T> coll,Function<T,R> tToR)
	{
		return extract(coll,tToR,f->true);
	}

	public static <T,R> List<R> extract(T[] arr,Function<T,R> tToR,Function<T,Boolean> filter)
	{
		List<T> list = toList(arr);
		return extract(list,tToR,filter);
	}
	
	public static <T,R> List<R> extract(Collection<T> coll,Function<T,R> tToR,Function<T,Boolean> filter)
	{
		List<R> ret = new ArrayList<>();
		for(T t:coll)
		{
			if( filter.apply(t) )
			{
				ret.add(tToR.apply(t));
			}
		}
		
		return ret;
	}
	
	/** Busca K k en T arr[], retorna T o null si no lo encuentra */
	public static <T> T findElm(T[] arr,Function<T,Boolean> func)
	{
		List<T> list = toList(arr);
		return findElm(list,func);
	}
	
	/** Busca K k en List<T> list, retorna T o null si no lo encuentra */
	public static <T> T findElm(List<T> list,Function<T,Boolean> func)
	{
		for(T t:list)
		{
			if( func.apply(t) )
			{
				return t;
			}
		}
		
		return null;
	}

	/** Busca K k en T arr[], retorna T o null si no lo encuentra */
	public static <T,K> T findElm(T[] arr,K k,BiFunction<T,K,Boolean> func)
	{
		List<T> list = toList(arr);
		return findElm(list,k,func);
	}
	
	/** Busca K k en List<T> list, retorna T o null si no lo encuentra */
	public static <T,K> T findElm(List<T> list,K k,BiFunction<T,K,Boolean> func)
	{
		for(T t:list)
		{
			if( func.apply(t,k) )
			{
				return t;
			}
		}
		
		return null;
	}
	
	/** Busca K k en T[] t, retorna su posicion o -1 si no lo encuentra */
	public static <T> int findPos(T[] arr,Function<T,Boolean> func)
	{
		List<T> list = toList(arr);
		return findPos(list,func);
	}
	
	/** Busca K k en List<T> list, retorna su posicion o -1 si no lo encuentra */
	public static <T> int findPos(List<T> list,Function<T,Boolean> func)
	{
		int i=0;
		for(T t:list)
		{
			if( func.apply(t) )
			{
				return i;
			}
			
			i++;
		}
		
		return -1;
	}

	
	/** Busca K k en T[] t, retorna su posicion o -1 si no lo encuentra */
	public static <T,K> int findPos(T[] arr,K k,BiFunction<T,K,Boolean> func)
	{
		List<T> list = toList(arr);
		return findPos(list,k,func);
	}
	
	/** Busca K k en List<T> list, retorna su posicion o -1 si no lo encuentra */
	public static <T,K> int findPos(List<T> list,K k,BiFunction<T,K,Boolean> func)
	{
		int i=0;
		for(T t:list)
		{
			if( func.apply(t,k) )
			{
				return i;
			}
			
			i++;
		}
		
		return -1;
	}

	public static <T> boolean equals(List<T> a,List<T> b)
	{
		return equals(a,b,(x,y)->x.equals(y));
	}
	
	public static <T,U> boolean equals(List<T> a,List<U> b,BiFunction<T,U,Boolean> func)
	{
		boolean ret = false;
		if( a.size()==b.size() )
		{
			for(int i=0; i<a.size(); i++)
			{
				if( !func.apply(a.get(i),b.get(i)) )
				{
					return false;
				}
			}
			
			ret = true;
		}
		
		return ret;
	}
	
	
	public static <T> List<T> toList(T ...arr)
	{
		ArrayList<T> lst = new ArrayList<>();
		int i=0;
		for(T t:arr)
		{
			lst.add(arr[i++]);
		}
		return lst;
	}	
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list,Class<?> target)
	{
		T arr[] = (T[])Array.newInstance(target,list.size());	
		int i=0;
		for(T t:list)
		{
			arr[i++]=(T)t;
		}
		
		return arr;
	}	
	
	public static <T> void invoke(List<T> lst,Consumer<T> f)
	{
		for(T t:lst)
		{
			f.accept(t);
		}
	}

	public static <K,V> Map<K,V> toMap(Object ...p)
	{
		HashMap<K,V> ret = new HashMap<>();
		for(int i=0;i<p.length;i+=2)
		{
			ret.put((K)p[i],(V)p[i+1]);
		}
		
		return ret;
	}
	
	public static <K,V,T> Map<K,V> extract(Map<K,T> m,BiFunction<K,T,V> func)
	{
		HashMap<K,V> ret = new HashMap<>();
	    for(Map.Entry<K,T> entry : m.entrySet())
	    {
	    	ret.put(entry.getKey(),func.apply(entry.getKey(),entry.getValue()));
	    }
	    
	    return ret;
	}

	public static <T> T[] createArray(int arrSize,T defaultValue)
	{
		T[] arr = (T[])Array.newInstance(defaultValue.getClass(),arrSize);
		for(int i=0; i<arrSize; i++)
		{
			arr[i]=defaultValue;
		}
		
		return arr;
	}

	public static <T> T findElm(Set<T> set, Function<T,Boolean> f)
	{
		for(T t:set)
		{
			if( f.apply(t) )
			{
				return t;
			}
		}
		
		return null;
	}

	public static <T> String toString(List<T> lst,String sep)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<lst.size(); i++)
		{
			sb.append(lst.get(i));
			if( i<lst.size()-1 )
			{
				sb.append(sep);
			}
		}
		
		return sb.toString();
	}

	public static <T> List<T> clone(List<T> lst)
	{
		return extract(lst,t->t);
	}
}
