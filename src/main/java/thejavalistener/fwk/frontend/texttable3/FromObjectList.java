package thejavalistener.fwk.frontend.texttable3;

import java.util.List;

import thejavalistener.fwk.util.MyReflection;

public class FromObjectList implements DataLoader
{
	private List<?> data = null;
	private int nextRowIdx=0;
	
	public FromObjectList(List<?> data)
	{
		this.data = data;
	}
	
	@Override
	public Object[] nextRow()
	{
		if(nextRowIdx>=data.size()) return null;
		
		Object o = data.get(nextRowIdx++);

		List<Object> values = MyReflection.object.getValues(o);	
		
		if( values.size()==0 )
		{
			values = List.of(o);				
		}

		int i = 0;
		Object ret[] = new Object[values.size()];
		for(Object v:values)
		{
			ret[i++] = v;
		}
		
		return ret;
	}
	
	@Override
	public String[] headers()
	{
		if( data.size()>0 )
		{
			List<String> atts = MyReflection.clasz.getAttributes(data.get(0).getClass());
			if(atts.size()==0) atts.add("c1");
			String[] ret = new String[atts.size()];
			int i=0;
			for(String s:atts)
			{
				ret[i++] = s;
			}
			
			return ret;
		}
		
		return new String[]{};
	}
	
	
}
