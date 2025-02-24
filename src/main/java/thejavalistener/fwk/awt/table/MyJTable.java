package thejavalistener.fwk.awt.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

class MyJTable extends JTable
{
	private Color alternateColor1=Color.WHITE;
	private Color alternateColor2 = new Color(239,244,249);//new Color(243,255,255);//new Color(243,246,246);//new Color(231,246,250);
	private boolean enableAlternateRowColor=false;

	public MyJTable(TableModel tableModel)
	{
		super(tableModel);
		setColumnModel(new MyColumnModel(new MyTableCellRender()));
	}
	
	public void setAlternateRowColor(Color c2)
	{
		setAlternateRowColor(alternateColor1,c2);
	}

	public void setAlternateRowColor(Color c1, Color c2)
	{
		this.alternateColor1=c1;
		this.alternateColor2=c2;
	}

	public void setEnableAlternateRowColor(boolean b)
	{
		enableAlternateRowColor=b;
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component c=super.prepareRenderer(renderer,row,column);
		if(enableAlternateRowColor&&!isRowSelected(row))
		{
			c.setBackground(row%2==0?alternateColor1:alternateColor2);
		}

		return c;
	}	
}
