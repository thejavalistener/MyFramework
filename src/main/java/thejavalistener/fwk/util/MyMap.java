package thejavalistener.fwk.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

public class MyMap<K,V> extends LinkedHashMap<K,V>
{
	public V discover(K k,Supplier<V> ifAddIsNeeded)
	{
		V v = get(k);
		if( v==null )
		{
			v = ifAddIsNeeded.get();
			put(k,v);
		}
		
		return v;
	}
	
	public List<K> keys()
	{
		return new ArrayList<>(keySet());
	}
	
	public V getByIndex(int idx)
	{
		return get(keys().get(idx));
	}
}
