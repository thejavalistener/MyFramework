package thejavalistener.fwk.frontend.texttable3;

import java.util.ArrayList;
import java.util.List;

public class MyTwoEntryTable extends MyAbstractTextTable
{ 
	private List<String> rowLabels = null;
	private String title = null;
	private Integer rowLenWidth = 0;
	private Integer rowLabelAlign;
	
	public MyTwoEntryTable()
	{
		this("");
	}
	public MyTwoEntryTable(String title)
	{
		this(title,title.length());
	}
	
	public MyTwoEntryTable(String title,int rowLenWidth)
	{
		this(title,rowLenWidth,Column.RIGHT_ALIGN);
	}
	
	public MyTwoEntryTable(String title,int rowLenWidth,int rowLabelAlign)
	{
		rowLabels = new ArrayList<>();
		this.title = title;
		this.rowLenWidth = rowLenWidth;
		this.rowLabelAlign = rowLabelAlign;

		setBorders(BOTTON_BORDER+RIGHT_BORDER);
	}
	
	public MyTwoEntryTable newEntry(String rowLabel)
	{
		rowLabels.add(rowLabel);		
		return this;
	}
	
	@Override
	protected void premakeTableHook()
	{
		int len = Math.max(title.length(),rowLenWidth);
		Column c = new Column(title,len,rowLabelAlign);
		
		// calculo el ancho final de la columna
		int max = rowLabels.stream().mapToInt(String::length).max().orElse(0);
		c.setFinalWidth(max);
		columns.add(0,c);
		
		// agrego los rowLabels como celdas 0 en las filas 
		for(int i=0; i<rowLabels.size(); i++)
		{
			// hay tantos labels (rowLabels) como filas
			List<Cell> row = rows.get(i);
			row.add(0,new Cell(this,rowLabels.get(i)));
		}				
	}
	
}
