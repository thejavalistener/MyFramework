package thejavalistener.fwk.frontend.messages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.frontend.MyAbstractScreen;

@Component
public class MyScreenValuesExchange
{
	private Map<Class<?>,Map<Class<?>,Map<String,Object>>> values;
	
	public MyScreenValuesExchange()
	{
		values = new LinkedHashMap<>();
	}
	
	public void putValueFor(Class<? extends MyAbstractScreen> target,String valueName,Object value,MyAbstractScreen me)
	{
		Map<Class<?>,Map<String,Object>> x = values.get(target);
		if( x==null )
		{
			x = new LinkedHashMap<>();
			values.put(target,x);
		}
		
		Map<String,Object> y = x.get(me.getClass());
		if( y==null )
		{
			y = new LinkedHashMap<>();
			x.put(me.getClass(),y);
		}
		
		y.put(valueName,value);
	}
	
	public Object getValueFrom(MyAbstractScreen me,Class<? extends MyAbstractScreen> sender,String valueName)
	{
		Map<Class<?>,Map<String,Object>> x = values.get(me.getClass());
		if( x!=null )
		{
			Map<String,Object> y = x.get(sender);
			if( y!=null )
			{
				return y.get(valueName);
			}
		}
		
		return null;
	}
	
	public List<Object> getValues(MyAbstractScreen me,String valueName)
	{
		List<Object> ret = new ArrayList<>();
		// sender   , m
		Map<Class<?>,Map<String,Object>> x = values.get(me.getClass());
		if( x!=null )
		{
			x.forEach((k,v)->v.forEach((kk,vv)->ret.add(vv)));
		}
		
		return ret;
	}
	
	
	public Object removeValueFrom(MyAbstractScreen me,Class<? extends MyAbstractScreen> sender,String valueName)
	{
		Map<Class<?>,Map<String,Object>> x = values.get(me.getClass());
		if( x!=null )
		{
			Map<String,Object> y = x.get(sender);
			if( y!=null )
			{
				return y.remove(valueName);
			}
		}
		
		return null;
		
	}
}
