package thejavalistener.fwk.frontend.texttable3;

import java.sql.Date;
import java.sql.Timestamp;

public class Cell
{
	private StyleManager stMgr = null;
	private Object value;
	private boolean isNull;
	private boolean isNumber;
	private boolean isDouble;
	private boolean isInteger;
	private boolean isDate;
	private boolean isTimestamp;
	private boolean isDateTime;
	
	public Cell(MyAbstractTextTable ownerTable,Object value)
	{
		stMgr = new StyleManager(value,ownerTable.getStyleDelimiters());
		
		this.value = stMgr.getContent();
		this.isNull = value==null;
		
		if( !isNull )
		{
			isDouble = value instanceof Double || value instanceof Float;
			isInteger = value instanceof Integer || value instanceof Long;
			isNumber = isDouble || isInteger;
			
			isDate = value instanceof Date;
			isTimestamp = value instanceof Timestamp;
			isDateTime = isDate || isTimestamp;
		}
	}
	
	public StyleManager getStyleManager()
	{
		return stMgr;
	}
	
	@Override
	public String toString()
	{
		if( !isNull )
		{
			return value.toString();
		}
		else
		{
			return "NULL";
		}
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value=value;
	}

	public boolean isNull()
	{
		return isNull;
	}

	public void setNull(boolean isNull)
	{
		this.isNull=isNull;
	}

	public boolean isNumber()
	{
		return isNumber;
	}

	public void setNumber(boolean isNumber)
	{
		this.isNumber=isNumber;
	}

	public boolean isDouble()
	{
		return isDouble;
	}

	public void setDouble(boolean isDouble)
	{
		this.isDouble=isDouble;
	}

	public boolean isInteger()
	{
		return isInteger;
	}

	public void setInteger(boolean isInteger)
	{
		this.isInteger=isInteger;
	}

	public boolean isDate()
	{
		return isDate;
	}

	public void setDate(boolean isDate)
	{
		this.isDate=isDate;
	}

	public boolean isTimestamp()
	{
		return isTimestamp;
	}

	public void setTimestamp(boolean isTimestamp)
	{
		this.isTimestamp=isTimestamp;
	}

	public boolean isDateTime()
	{
		return isDateTime;
	}

	public void setDateTime(boolean isDateTime)
	{
		this.isDateTime=isDateTime;
	}
	
	
}
