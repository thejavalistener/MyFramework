package thejavalistener.fwk.frontend.texttable3;

import java.util.List;

public class FromObjectArrayList implements DataLoader
{
	private List<Object[]> data = null;
	private int nextRowIdx=0;
	
	public FromObjectArrayList(List<Object[]> data)
	{
		this.data = data;
	}
	
	@Override
	public Object[] nextRow()
	{
		if(nextRowIdx>=data.size() ) return null;
		return data.get(nextRowIdx++);
	}

	@Override
	public String[] headers()
	{
		if( data.size()==0 ) return new String[0];
		
		// n columnas
		int n=data.get(0).length;
		
		String[] ret = new String[n];
		for(int i=0; i<n; i++)
		{
			ret[i] = "c"+Integer.toString(i+1);
		}
		
		return ret;
	}
}
