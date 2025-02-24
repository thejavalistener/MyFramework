package thejavalistener.fwk.awt.table;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class MyColumnModel extends DefaultTableColumnModel
{
	private MyTableCellRender cellRender;
	
	public MyColumnModel(MyTableCellRender cellRender)
	{
		this.cellRender = cellRender;
	}
	
	@Override
	public TableColumn getColumn(int columnIndex)
	{
		TableColumn tc= super.getColumn(columnIndex);
		tc.setCellRenderer(cellRender);
		return tc;
	}
}
