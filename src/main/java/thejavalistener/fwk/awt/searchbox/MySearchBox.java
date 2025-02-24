package thejavalistener.fwk.awt.searchbox;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.panel.MyGridLayout;
import thejavalistener.fwk.awt.panel.MyInsets;
import thejavalistener.fwk.awt.panel.MyLeftLayout;
import thejavalistener.fwk.awt.table.MyTable;
import thejavalistener.fwk.awt.textarea.MyTextField;

public class MySearchBox<T>
{
	private JDialog jDialog;
	private JButton bSearch;
	private MyTextField tfSearch;
	private MyTable<T> table;
	private MyLink label;
	
	private JButton bAccept;
	private JButton bCancel;
	
	private T returnValue=null;
	
	private EscuchaSearch escuchaSearch;
	private EscuchaEnter escuchaEnter;
	
	private MySearchBoxController<T> controller;
	
	public MySearchBox(Window parent,MySearchBoxController<T> controller)
	{
		escuchaSearch = new EscuchaSearch();
		escuchaEnter = new EscuchaEnter();
		
		jDialog = new JDialog(parent);
		tfSearch = new MyTextField();
		tfSearch.c().addKeyListener(escuchaEnter);
		
		table = new MyTable<>();
		table.setListSelectionListener(new EscuchaTable());
		table.setActionListener(l->{returnValue=table.getSelectedObject();dispose();});
		bSearch = new JButton("Search");
		bSearch.addActionListener(escuchaSearch);
		label = new MyLink("Search pattern");
		this.controller = controller;
		
		JPanel p = new MyBorderLayout(10,10,10,10);
		
		// norte
		MyGridLayout pNorth = new MyGridLayout(0,0,0,0);
		pNorth.setGridDimensions(2,1);
		
		JPanel pNorth0 = new MyLeftLayout(0,0,0,0);
		pNorth0.add(label.c());
		pNorth.add(pNorth0);
		
		JPanel pNorth1 = new MyBorderLayout(0,0,10,0);
		pNorth1.add(tfSearch.c(),BorderLayout.CENTER);
		pNorth1.add(new MyInsets(bSearch,0,10,0,0),BorderLayout.EAST);
		pNorth.add(pNorth1);
		
		p.add(pNorth,BorderLayout.NORTH);
		
		// center
		JScrollPane jsp= new JScrollPane(table.c());
		jsp.setBorder(null);
		p.add(jsp,BorderLayout.CENTER);
		
		// south
		bCancel = new JButton("Cancel");
		bCancel.addActionListener(new EscuchaCancel());
		bAccept = new JButton("Accept");
		bAccept.addActionListener(new EscuchaAccept());
		bAccept.setEnabled(false);
		
		MyCenterLayout pSouth= new MyCenterLayout(5,0,0,0);
		pSouth.add(bCancel);
		pSouth.add(bAccept);
		
		p.add(pSouth,BorderLayout.SOUTH);
		
		jDialog.add(p,BorderLayout.CENTER);
	}
	
	public void setAlternateRowColor(boolean b)
	{
		table.setEnableAlternateRowColor(b);
	}
	
	public void setSearchLabel(String lbl)
	{
		label.setText(lbl);
	}
	
	public MyTable<T> getTable()
	{
		return table;
	}
	
	public void setSize(int w,int h)
	{
		jDialog.setSize(w,h);
	}
	
	public JDialog getDialog()
	{
		return jDialog;
	}
		
	public T show()
	{
		jDialog.setModal(true);
		jDialog.setVisible(true);
		return getReturnValue();
	}

	T getReturnValue()
	{
		return returnValue;
	}	
	
	class EscuchaSearch implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			table.clear();
			List<T> data = controller.dataRequested(tfSearch.getText());
			table.setData(data);
		}
	}
	
	class EscuchaEnter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if( e.getKeyCode()==KeyEvent.VK_ENTER )
			{
				table.clear();
				List<T> data = controller.dataRequested(tfSearch.getText());
				table.setData(data);
			}
		}
	}

	
	public void dispose()
	{
		jDialog.setVisible(false);
		jDialog.dispose();
	}
	
	class EscuchaTable implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			bAccept.setEnabled(table.getSelectedIndex()>=0);
		}
	}
	
	class EscuchaAccept implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			returnValue = table.getSelectedObject();
			dispose();
		}
	}
	
	class EscuchaCancel implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			returnValue = null;
			dispose();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		MyAwt.setWindowsLookAndFeel();
		
		MySearchBox<String> sb = new MySearchBox<>(null,new Controller());
		String r = sb.show();
		
		System.out.println(r);
	}
	

	
	static class Controller implements MySearchBoxController<String>
	{
		static List<String> palabras;
		static
		{
			palabras = List.of("Alberto","Analia","Amelia","Bertha","Carlos","Carola","Daniel","Danilo","Debora");
		}
		
		@Override
		public List<String> dataRequested(String toSearch)
		{
			ArrayList<String> a = new ArrayList<>();
			for(String s:palabras) 
			{
				if( s.startsWith(toSearch) ) a.add(s); 
			}
			
			return a;
		}
		
	}
}
