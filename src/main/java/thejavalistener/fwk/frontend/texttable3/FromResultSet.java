package thejavalistener.fwk.frontend.texttable3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class FromResultSet implements DataLoader
{
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	private int nCols = 0;
	
	public FromResultSet(ResultSet rs)
	{
		try
		{
			this.rs = rs;			
			this.rsmd = rs.getMetaData();
			nCols = rsmd.getColumnCount();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object[] nextRow()
	{
		try
		{
			if(!rs.next() ) return null;
			
			Object[] ret = new Object[nCols];
			for(int i=1; i<=nCols; i++)
			{
				ret[i-1] = rs.getObject(i);
			}
			
			return ret;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[] headers()
	{
		try
		{
			String[] ret = new String[nCols];
			for(int i=1; i<=nCols; i++)
			{
				ret[i-1] = rsmd.getColumnLabel(i);
			}			
			
			return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
