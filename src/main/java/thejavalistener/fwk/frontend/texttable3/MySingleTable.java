package thejavalistener.fwk.frontend.texttable3;

import java.sql.ResultSet;
import java.util.List;

import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.MyNumber;
import thejavalistener.fwk.util.string.MyString;

public class MySingleTable extends MyAbstractTextTable
{
	public boolean lineNumbers = false;

	public void showLineNumbers(boolean b)
	{
		lineNumbers = b;
	}
	
	/** determina qué dataLoader usar y lo usa */
	public MySingleTable loadData(Object data)
	{
		// verifico si es una lista
		if( data instanceof List )
		{
			List<?> lst = (List<?>)data;
			if( lst.size()==0) return this;
			
			// verifico si es de Object o Object[]
			if( lst.get(0) instanceof Object[] )
			{
				List<Object[]> lstOA = (List<Object[]>)lst;
				return loadData(new FromObjectArrayList(lstOA));
			}
			else
			{
				List<Object> lstO = (List<Object>)lst;
				return loadData(new FromObjectList(lstO));
			}
		}
		
		// verifico si es un ResultSet
		if( data instanceof ResultSet )
		{
			return loadData(new FromResultSet((ResultSet)data));
		}
		
		return this;
	}
	
	public MySingleTable loadData(DataLoader dl)
	{
		// cargo los datos
		MyLog.println("OJO ACA...");
		if( columns.size()==0)
			headers(dl.headers());
		
		Object[] row = dl.nextRow();
		while(row!=null)
		{
			addRow(row);
			row = dl.nextRow();
		}

		return this;
	}
	
	
	@Override
	protected void premakeTableHook()
	{
		if( lineNumbers )
		{
			// primero el header
			int w = MyNumber.length(rows.size());
			String rNo = MyString.lAssureLength("#",w,' ');
			
			Column c = new Column(rNo,w,Column.RIGHT_ALIGN);
			columns.add(0,c);
						
			// luego las filas
			for(int i=0; i<rows.size(); i++)
			{
				rows.get(i).add(0,new Cell(this,i));			
			}
		}
	}
}
