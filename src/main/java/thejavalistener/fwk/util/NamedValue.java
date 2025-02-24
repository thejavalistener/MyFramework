package thejavalistener.fwk.util;

import java.util.ArrayList;
import java.util.List;

public class NamedValue<T>
{
	private T value;
	private String name;
	
	public NamedValue()
	{
	}
	
	public static List<NamedValue<?>> listOf(Object ...arr)
	{
		List<NamedValue<?>> ret = new ArrayList<>();
		for(int i=0; i<arr.length; i+=2)
		{
			ret.add(new NamedValue<>(arr[i].toString(),arr[i+1].toString()));
		}
		
		return ret;
	}
	
	public static void main(String[] args)
	{
		System.out.println(listOf("uno",1,"dos",2,"tres",3));
	}
	
	public NamedValue(String name,T value)
	{
		this.value = value;
		this.name = name;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public String getName()
	{
		return name;
	}

	public void setValue(T value)
	{
		this.value=value;
	}

	public void setName(String name)
	{
		this.name=name;
	}	
	
	@Override
	public String toString()
	{
		return "name=["+name+"],value["+value+"]";
	}
}
