package thejavalistener.fwk.awt.table;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import thejavalistener.fwk.util.MyReflection;

public class MyTable<T>
{
	private MyTable<T> outer;

	private MyJTable jTable;
	private DefaultTableModel tableModel;
	private List<T> dataO;
	private List<Object[]> dataA;

	public ListSelectionListener listSelectionListener;
	public ActionListener actionListener;

	private Function<T,Object[]> beanToObjectArrayPolicy = (o)->MyReflection.object.getValues(o).toArray();
	private Function<Class<?>,Object[]> beanClassToHeaderPolicy = (c)->MyReflection.clasz.isFinalClass(c)?new Object[]{c.getSimpleName()}:MyReflection.clasz.getAttributes(c).toArray();
	
	private boolean hayHeaders = false;
	private  boolean autoSortable = true;

	public void setBackground(Color c)
	{
		jTable.setBackground(c);
	}
	
	public void setForeground(Color c)
	{
		jTable.setForeground(c);
	}
	
	public void setBeanToObjectArrayPolicy(Function<T,Object[]> f)
	{
		this.beanToObjectArrayPolicy=f;
	}

	public void setBeanClassToHeaderPolicy(Function<Class<?>,Object[]> f)
	{
		this.beanClassToHeaderPolicy=f;
	}

	public void setEnableAlternateRowColor(boolean b)
	{
		jTable.setEnableAlternateRowColor(b);
	}

	public MyTable()
	{
		outer=this;
		tableModel=new MyTableModel();
		jTable=new MyJTable(tableModel);
		jTable.addMouseListener(new EscuchaDobleClick());
		jTable.getTableHeader().addMouseListener(new EscuchaHeader());
		dataO=new ArrayList<>();
		dataA=new ArrayList<>();


		// Permitir selección múltiple
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Habilitar selección de filas y columnas
		jTable.setRowSelectionAllowed(true);
		jTable.setColumnSelectionAllowed(true);

		
//		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(new EscuchaTable());
		
		jTable.setBorder(null);
		
		jTable.getActionMap().put("copyRow", new EscuchaCopy());
	
	}
	
	class EscuchaCopy extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			int row = jTable.getSelectedRow();
	        if (row >= 0) {
	            StringBuilder sb = new StringBuilder();
	            for (int col = 0; col < jTable.getColumnCount(); col++) {
	                Object value = jTable.getValueAt(row, col);
	                sb.append(value != null ? value.toString() : "").append("\t");
	            }
	            String rowText = sb.toString().trim();
	            Toolkit.getDefaultToolkit().getSystemClipboard()
	                   .setContents(new StringSelection(rowText), null);
	            System.out.println("Fila copiada: " + rowText);
	        }
		}
		
		
	}

	public MyTable<T> headers(Object... headers)
	{
		if( !hayHeaders )
		{
			tableModel.setColumnIdentifiers(headers);
			hayHeaders=true;			
		}
		
		return this;
	}

	public MyTable<T> layout(int... widths)
	{
		for(int i=0; i<widths.length; i++)
		{
			TableColumn column=jTable.getColumnModel().getColumn(i);
			column.setPreferredWidth(widths[i]);
		}

		return this;
	}
	
	public MyTable<T> addRow(T obj)
	{
		if(!hayHeaders)
		{
			headers(beanClassToHeaderPolicy.apply(obj.getClass()));
		}

		dataO.add(obj);
		
		if( !MyReflection.clasz.isFinalClass(obj.getClass()))
		{
			addRow(beanToObjectArrayPolicy.apply(obj));			
		}
		else
		{
			addRow(new Object[]{obj});						
		}
		
		return this;
	}
	
	public MyTable<T> addRow(List<Object> rowData)
	{
		addRow(rowData.toArray());
		return this;
	}

	public MyTable<T> addRow(Object... rowData)
	{
		dataA.add(rowData);
		tableModel.addRow(rowData);
		return this;
	}

	public int getSelectedIndex()
	{
		return jTable.getSelectedRow();
	}
	
	public void setAutoSortable(boolean b)
	{
		this.autoSortable = b;
	}

	public T getSelectedObject()
	{
		int idx=getSelectedIndex();
		return idx<0?null:dataO.get(idx);
	}

	public Object[] getSelectedRow()
	{
		int idx=getSelectedIndex();
		return idx<0?null:dataA.get(idx);
	}

	// data puede ser lo que venga...
	
	// opciones:
	//     OK List<Object[]>
	//     OK List<Bean>, donde Object es un Bean
	// 	   List<Object>, donde Object es un objeto final
	//     List<List<Object>>
	//     Object[][]
	//	   Object final: una única celda
	//     Object bean: una única fila
	
	public void setData(Object data)
	{
		if( data instanceof List)
		{
			List<?> lst = (List<?>) data;
			if( lst.size()<=0 ) return;

			
			// List<Object[]>
			if( lst.get(0) instanceof Object[] )
			{	
				List<Object[]> lstX = (List<Object[]>)lst;
				_generateHeaders(lstX.get(0).length);
				for(Object[] o:lstX) addRow(o);
				return;
			}
			
			// List<Bean>
			if( !MyReflection.clasz.isFinalClass(lst.get(0).getClass()) && !(lst.get(0) instanceof List) ) 
			{
//				for(Object o:lst) addRow(beanToObjectArrayPolicy.apply((T)o) );
				
				List<T> lstT = (List<T>)lst;
				headers(beanClassToHeaderPolicy.apply(lstT.get(0).getClass()));
				
//				_generateHeaders(MyBean.getAttributes(lstT.get(0)).size());
				for(T o:lstT) addRow(o);
				return;
			}

			// List<ObjFinal>
			if( MyReflection.clasz.isFinalClass(lst.get(0).getClass()) ) 
			{
				headers(new Object[]{lst.get(0).getClass().getSimpleName()});
				for(Object o:lst) addRow(o);
				jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				return;
			}
			
			// List<List<Object>>
			if( lst.get(0) instanceof List ) 
			{
				List<List<Object>> lstX = (List<List<Object>>)lst;
				_generateHeaders(lstX.get(0).size());
				for(List<Object> o:lstX) addRow(o);
				return;
			}
		}
		
		if( data instanceof Object[][] )
		{
			Object[][] mat = (Object[][])data;
			if( mat.length==0) return;
			
			_generateHeaders(mat[0].length);
			for(Object[] o:mat) addRow(o);
			return;
		}
		
		addRow((T)data);
	}
	

	private void _generateHeaders(int n)
	{
		if( !hayHeaders ) 
		{
			Object[] ret = new Object[n];
			for(int i=0; i<n; i++)
			{
				ret[i] = "c"+i;
			}
			
			headers(ret);
		}
	}

	public void clear()
	{
		tableModel.setRowCount(0);
	}

	public JTable c()
	{
		jTable.setAutoCreateRowSorter(autoSortable);
		return jTable;
	}

	class EscuchaTable implements ListSelectionListener
	{
		int i=0;

		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if(listSelectionListener!=null&&i==0)
			{
				listSelectionListener.valueChanged(e);
			}

			i=++i%2;
		}
	}

	class EscuchaDobleClick extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount()==2&&actionListener!=null)
			{
				int selectedRow=getSelectedIndex();
				if(selectedRow>=0)
				{
					ActionEvent ae=new ActionEvent(outer,1,"doubleclick");
					actionListener.actionPerformed(ae);
				}
			}
		}
	}

	public void setActionListener(ActionListener listener)
	{
		this.actionListener=listener;
	}

	public void setListSelectionListener(ListSelectionListener listener)
	{
		this.listSelectionListener=listener;
	}

	public static void main(String[] args) throws Exception
	{
		// seteo el look and feel
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		MyTable<?> myTable=new MyTable<>();
		myTable.setAutoSortable(true);

		// opciones:
		//     List<Object[]>
		//     List<Bean>, donde Object es un Bean
		// 	   List<Object>, donde Object es un objeto final
		//     List<List<Object>>
		//     Object[][]
		//	   Object final: una única celda
		//     Object bean: una única fila

//		List<Object[]> lst = new ArrayList<>();
//		lst.add(new Object[]{1,new Date(),MyString.generateRandom()});
//		lst.add(new Object[]{2,new Date(),MyString.generateRandom()});
//		lst.add(new Object[]{3,new Date(),MyString.generateRandom()});
//		lst.add(new Object[]{4,new Date(),MyString.generateRandom()});
//		myTable.setData(lst);
		
//		List<Persona> lst = new ArrayList<>();
//		lst.add(new Persona(1,"Pablo","San Martin 123"));
//		lst.add(new Persona(2,"Antonio","Remedios 341"));
//		lst.add(new Persona(3,"Carlos","Segui 653"));
//		lst.add(new Persona(4,"Jaun","Avellaneda 235"));
//		lst.add(new Persona(5,"Pedro","San Juan 721"));
//		myTable.setData(lst);

//		List<Integer> lst = new ArrayList<>();
//		lst.add(3);
//		lst.add(6);
//		lst.add(5);
//		lst.add(1);
//		lst.add(3);
//		myTable.setData(lst);
		
//		List<List<Object>> lst = new ArrayList<>();
//		lst.add(List.of(1,"Pablo","San Martin 123"));  
//		lst.add(List.of(2,"Antonio","Remedios 341"));  
//		lst.add(List.of(3,"Carlos","Segui 653"));      
//		lst.add(List.of(4,"Jaun","Avellaneda 235"));   
//		lst.add(List.of(5,"Pedro","San Juan 721"));		
//		myTable.setData(lst);

//		Object[][] x = {{1,2,3,4},{"x","y","z","t"},{12.2,34.1,2631.2,34.5}};
//		myTable.setData(x);
		
		myTable.setData(123);
		
		
		// Ejemplo de uso
		JFrame frame=new JFrame("Ejemplo de MyTable");
		 
		frame.add(new JScrollPane(myTable.c()));
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	class EscuchaHeader extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount()==2)
			{
				JTableHeader header=(JTableHeader)e.getSource();
				int column=header.columnAtPoint(e.getPoint());
				TableRowSorter<TableModel> sorter=(TableRowSorter<TableModel>)jTable.getRowSorter();
				sorter.toggleSortOrder(column);
			}
		}
	}

}
