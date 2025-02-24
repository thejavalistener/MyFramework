package thejavalistener.fwk.awt.table;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel
{
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	 @Override
     public Class<?> getColumnClass(int columnIndex) 
	 {
		 if( getRowCount()>0 )
		 {
			 Object v = getValueAt(0,columnIndex);
			 return v.getClass();
		 }
		 
		 return String.class;
     }
}
