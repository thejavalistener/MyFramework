package thejavalistener.fwk.frontend.texttable3;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.function.Function;

import thejavalistener.fwk.util.MyNumber;
import thejavalistener.fwk.util.UDate;
import thejavalistener.fwk.util.string.MyString;

public class Column
{
	public static final int LEFT_ALIGN = -1;
	public static final int CENTER_ALIGN = 0;
	public static final int RIGHT_ALIGN = 1;
	public static final int DEFAULT_ALIGN = -2;
	
	private static Function<Number,String> defaultNumberPolicy = (n) -> MyNumber.formatNumber(n);
	private static Function<Double,String> defaultDoublePolicy = (d) -> MyNumber.format(d,true);
	private static Function<Date,String> defaultDatePolicy = (d) -> new UDate(d).asDDMMYYYY('-');
	private static Function<Timestamp,String> defaultTimestampPolicy = (d) -> new UDate(d).toString("${dd}-${mm}-${yyyy},${h}:${m}:${s}.${ms}");
	
	private String header;
	private int widht;
	private int align;
	
	public Column(String header,int width,int align)
	{
		this.header = header;
		this.widht = width;
		this.align = align;
	}
	
	@Override
	public String toString()
	{
		return header+" ("+widht+","+_getAlign()+")";
	}
	
	private String _getAlign()
	{
		switch(align)
		{
			case LEFT_ALIGN: return "LEFT";
			case CENTER_ALIGN: return "CENTER";
			case RIGHT_ALIGN: return "RIGHT";
			case DEFAULT_ALIGN: return "DEFAULT";
			default: return null;
		}
	}
	
	public String getAlignedCellValue(Cell c)
	{
		String sCell = c.toString();
		if( !c.isNull() )
		{
			if( c.isDouble() )
				sCell = Column.defaultDoublePolicy.apply((Double)c.getValue());				
			if( c.isDate() )
				sCell = Column.defaultDatePolicy.apply((Date)c.getValue());				
			if( c.isTimestamp() )
				sCell = Column.defaultTimestampPolicy.apply((Timestamp)c.getValue());				
		}
		
		switch(align)
		{
			case LEFT_ALIGN:
				sCell = MyString.rAssureLength(sCell,widht,' ');
				break;
			case CENTER_ALIGN:
				sCell = MyString.cAssureLength(sCell,widht,' ');
				break;
			case RIGHT_ALIGN:
				sCell = MyString.lAssureLength(sCell,widht,' ');
				break;
			default:
				if( c.isNumber() )
				{
					sCell = MyString.lAssureLength(sCell,widht,' ');
				}
				else
				{
					sCell = MyString.rAssureLength(sCell,widht,' ');
				}
		}
		
		return sCell;
	}
	
	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header=header;
	}

	public int getWidht()
	{
		return widht;
	}

	public void setWidht(int widht)
	{
		this.widht=widht;
	}

	public int getAlign()
	{
		return align;
	}

	public void setAlign(int align)
	{
		this.align=align;
	}

	public void setFinalWidth(int w)
	{
		widht = Math.max(w,widht);
	}
	
	public static void setDefaultDoublePolicy(Function<Double,String> f)
	{
		defaultDoublePolicy = f;
	}
	
	public static Function<Double,String> getDefaultDoublePolicy()
	{
		return defaultDoublePolicy;
	}

	public static Function<Date,String> getDefaultDatePolicy()
	{
		return defaultDatePolicy;
	}

	public static void setDefaultDatePolicy(Function<Date,String> defaultDatePolicy)
	{
		Column.defaultDatePolicy=defaultDatePolicy;
	}

	public static Function<Timestamp,String> getDefaultTimestampPolicy()
	{
		return defaultTimestampPolicy;
	}

	public static void setDefaultTimestampPolicy(Function<Timestamp,String> defaultTimestampPolicy)
	{
		Column.defaultTimestampPolicy=defaultTimestampPolicy;
	}
	
	

}
