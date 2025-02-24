package thejavalistener.fwk.frontend.texttable3;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import thejavalistener.fwk.util.MyNumber;
import thejavalistener.fwk.util.string.MyString;

public abstract class MyAbstractTextTable
{
	public static final int TOP_BORDER=1;
	public static final int LEFT_BORDER=2;
	public static final int RIGHT_BORDER=4;
	public static final int BOTTON_BORDER=8;

	protected char leftCornerChar = '+';
	protected char rightCornerChar = '+';
	protected char leftChar = '|';
	protected char rightChar = '|';
	protected char colSeparatorChar='|';

	protected int borders = TOP_BORDER+LEFT_BORDER+RIGHT_BORDER+BOTTON_BORDER;

	public boolean lineSeparators = false;
	
	private String noDataFoundMessage;
	
	protected List<Box> boxes;
	protected List<List<Cell>> rows;
	protected List<Column> columns;
	
	protected boolean maked = false;
	
	private String openStyleDelimiter = "[";
	private String closeStyleDelimiter = "]";
	
	public MyAbstractTextTable()
	{
		columns = new ArrayList<>();
		rows = new ArrayList<>();
		boxes = new ArrayList<>();	
	}
	
	public void setStyleDelimiters(String openDelim,String closeDelim)
	{
		openStyleDelimiter = openDelim;
		closeStyleDelimiter = closeDelim;
	}
	
	public String[] getStyleDelimiters()
	{
		return new String[]{openStyleDelimiter,closeStyleDelimiter};
	}
	
	public MyAbstractTextTable autolayout()
	{
		for(int i=0; i<columns.size(); i++)
		{
			Column column=columns.get(i);
			
			int max = column.getWidht();
			for(int j=0; j<rows.size(); j++)
			{
				Cell cell = rows.get(j).get(i);
				String sCell = cell.toString();
				if( cell.isDouble() )
					sCell = Column.getDefaultDoublePolicy().apply((Double)cell.getValue());
				if( cell.isDate() )
					sCell = Column.getDefaultDatePolicy().apply((Date)cell.getValue());				
				if( cell.isTimestamp() )
					sCell = Column.getDefaultTimestampPolicy().apply((Timestamp)cell.getValue());				

				max = Math.max(max,sCell.length());
			}
			
			column.setWidht(max);
		}
		
		return this;
	}
	
	public String getNoDataFoundMessage()
	{
		return noDataFoundMessage;
	}

	public void setNoDataFoundMessage(String noDataFoundMessage)
	{
		this.noDataFoundMessage=noDataFoundMessage;
	}

	public void addRow(Object ...row)
	{
		List<Cell> lst = new ArrayList<>();
		for(Object o:row)
		{
			lst.add(new Cell(this,o));
		}
		rows.add(lst);		
	}
	
	public void showLineSeparators(boolean b)
	{
		this.lineSeparators = b;
	}
	
	public MyAbstractTextTable headers(String ...headers)
	{
		for(String header:headers)
		{
			Column c = new Column(header,header.length(),Column.DEFAULT_ALIGN);
			columns.add(c);
		}
		
		return this;
	}
	
	public MyAbstractTextTable layout(Double ...widths)
	{
		int i=0;
		Integer[] ints = new Integer[widths.length];
		for(Double d:widths)
		{
			ints[i++] = MyNumber.round(d,1);
		}
		
		return layout(ints);
	}
	public MyAbstractTextTable layout(Integer ...widths)
	{
		for(int i=0; i<widths.length; i++ )
		{
			Column c = columns.get(i);
			c.setFinalWidth(widths[i]);
		}

		return this;
	}

	
	public MyAbstractTextTable aligns(Integer ...aligns)
	{
		for(int i=0; i<aligns.length; i++ )
		{
			columns.get(i).setAlign(aligns[i]);
		}
		
		return this;		
	}
	
	protected abstract void premakeTableHook();
	
	public MyAbstractTextTable makeTable()
	{
		maked = true;
	
		// tiro un hook para que las subclases hagan lo que necesiten
		premakeTableHook();

		// agrego los headers para ser tratados como un row más
		_addHeadersToRows();
		
		// proceso las cajas
		processBoxes();		
		
		return this;
	}
	
	private void _addHeadersToRows()
	{
		List<Cell> newRow = new ArrayList<>();
		for(int i=0; i<columns.size(); i++)
		{
			Cell cell = new Cell(this,columns.get(i).getHeader());
			newRow.add(cell);
		}
		
		rows.add(0,newRow);
	}
	
	public int getTableWidth()
	{
		int sum=0;
		for(Column c:columns)
		{
			sum+=c.getWidht();
		}
		
		return sum+columns.size()+1;
	}
		
	public MyAbstractTextTable addBox(Object content,double wPorc)
	{
		boxes.add(new Box(this,null,content,wPorc));
		return this;
	}
	
	public MyAbstractTextTable addLabeledBox(String label,Object content,double wPorc)
	{
		boxes.add(new Box(this,label,content,wPorc));
		return this;		
	}

	public void setDefaultDoublePolicy(Function<Double,String> f)
	{
		Column.setDefaultDoublePolicy(f);
	}
		
	public void setDefaultDatePolicy(Function<Date,String> f)
	{
		Column.setDefaultDatePolicy(f);
	}
		
	public void setDefaultTimestampPolicy(Function<Timestamp,String> f)
	{
		Column.setDefaultTimestampPolicy(f);
	}
		
	protected void processBoxes()
	{
		int tableWidth = getTableWidth()-(boxes.size())-1;
		int dispo = tableWidth;
		
		for(int i=0; i<boxes.size(); i++)
		{
			Box b = boxes.get(i);
			int w;
			if( i<boxes.size()-1)
			{
				w = (int)(b.getWidth()*tableWidth);
			}
			else
			{
				w = dispo;//tableWidth-sum-2;
			}
			
			
			b.charWidth=w;
			dispo-=b.charWidth;					
		}
	}

	protected void setColSeparatorChar(char c)
	{
		this.colSeparatorChar = c;
	}
	
	protected void setLeftCornerChar(char c)
	{
		this.leftCornerChar = c;
	}
	
	protected void setRightCornerChar(char c)
	{
		this.rightCornerChar = c;
	}
	
	protected void setLeftChar(char c)
	{
		this.leftChar = c;
	}
	
	protected void setRightChar(char c)
	{
		this.rightChar = c;
	}

	public String drawUnstyledTable()
	{
		String t = drawTable();
		StringBuffer x = new StringBuffer();
		MyString.extract(t,openStyleDelimiter,closeStyleDelimiter,x);
		return x.toString();
	}

	public String drawTable()
	{
		if(!maked )
		{
			throw new RuntimeException("Primero debe invocar al método makeTable()");
		}
		
		if(rows.size()==1) return getNoDataFoundMessage();
		
		StringBuffer sb = new StringBuffer();
		
		// encabezado
		
		if( (borders&TOP_BORDER) !=0)
		{
			sb.append(drawSeparator()).append('\n');
		}
		
		sb.append(drawRow(rows.get(0))).append('\n');
		sb.append(drawSeparator()).append('\n');
		
		for(int i=1; i<rows.size(); i++)
		{
			List<Cell> r = rows.get(i);
			sb.append(drawRow(r)).append('\n');
			
			if( lineSeparators && i<rows.size()-1) 
			{
				sb.append(drawSeparator()).append('\n');
			}
		}
	
		if( boxes.size()==0 )
		{		
			if( (borders&BOTTON_BORDER)!=0 )
			{
				sb.append(drawSeparator());
			}
		}
		else
		{
			_makeBoxes(sb);
		}
		
		return sb.toString();
	}
	
	private void _makeBoxes(StringBuffer sb)
	{
		// lineas p/separador, contenidos y labels
		StringBuffer sbSep1 = new StringBuffer(drawSeparator());		
		StringBuffer sbSep2 = new StringBuffer("+"+MyString.replicate('-',getTableWidth()-2)+"+");		
		StringBuffer sbLabel = new StringBuffer();
		StringBuffer sbContent = new StringBuffer();
		sbContent.append('|');
		sbLabel.append('|');

		int sum=1;
		for(int i=0; i<boxes.size(); i++)
		{
			Box box = boxes.get(i);
			
			sum+=box.charWidth;
			sbSep1.setCharAt(sum,'+');
			sbSep2.setCharAt(sum,'+');
			sum++;
			
			if( box.getContent()!=null && MyNumber.isDouble(box.getContent()))
			{
				box.setContent(Column.getDefaultDoublePolicy().apply((Double)box.getContent()));
			}
			
			String ccontent = box.getContent()!=null?box.getContent().toString():"NULL";
			String content = MyString.cAssureLength(ccontent,box.charWidth,' ');

			for(String style:box.getContentStyleManager().getStyles())
			{
				sbContent.append(style);
			}
			
			sbContent.append(content);
			
			for(int x=0; x<box.getContentStyleManager().countStyles();x++)
			{
				sbContent.append(openStyleDelimiter).append("x").append(closeStyleDelimiter);
			}
			
			sbContent.append('|');

			if( box.getLabel()!=null )
			{
				String label = MyString.rAssureLength(box.getLabel(),box.charWidth,' ');				
				
				for(String style:box.getLabelStyleManager().getStyles())
				{
					sbLabel.append(style);
				}

				sbLabel.append(label);
				
				for(int x=0; x<box.getLabelStyleManager().countStyles();x++)
				{
					sbLabel.append(openStyleDelimiter).append("x").append(closeStyleDelimiter);
				}
				
				sbLabel.append('|');
			}
		}

		// armo las cajas
		sb.append(sbSep1).append('\n');
		if( sbLabel.length()>1 )
		{
			sb.append(sbLabel).append('\n');
		}
		sb.append(sbContent).append('\n');
		sb.append(sbSep2);//.append('\n');
	}
	public void setBorders(int borders)
	{
		this.borders = borders;
	}
	
	protected String drawSeparator()
	{
		int nCols = rows.get(0).size();
		List<Cell> sep = new ArrayList<>();
		for(int i=0; i<nCols; i++)
		{
			String value = MyString.replicate('-',columns.get(i).getWidht());
			sep.add(new Cell(this,value));
		}
		
		return _drawLine(sep,leftCornerChar,leftCornerChar,leftCornerChar);		
	}

	private String _drawLine(List<Cell> row,char leftChar,char separator,char rightChar)
	{
		StringBuffer sb = new StringBuffer(Character.toString(leftChar));

		for(int i=0; i<row.size(); i++)
		{
			Cell cell = row.get(i);
			Column column = columns.get(i);						
			String sCellAligned = column.getAlignedCellValue(cell);
			
			for(String style:cell.getStyleManager().getStyles())
			{
				sb.append(style);
			}
			
			sb.append(sCellAligned);

			for(int x=0; x<cell.getStyleManager().countStyles();x++)
			{
				sb.append(openStyleDelimiter).append("x").append(closeStyleDelimiter);
			}

			char x = i<row.size()-1?separator:rightChar;
			sb.append(x);
		}

		return sb.toString();
	}
	protected String drawRow(List<Cell> row)
	{
		char l = (borders&LEFT_BORDER)!=0?leftChar:' ';
		char r = (borders&RIGHT_BORDER)!=0?rightChar:' ';
		
		return _drawLine(row,l,colSeparatorChar,r);
	}
	
	@Override
	public String toString()
	{
		return drawTable();
	}		
}
