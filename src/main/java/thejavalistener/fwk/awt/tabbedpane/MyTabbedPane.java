package thejavalistener.fwk.awt.tabbedpane;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyInsets;
import thejavalistener.fwk.awt.panel.MyLeftLayout;

public class MyTabbedPane
{
	private JTabbedPane tabbedPane;
	private List<JComponent> pestanias;
	private ChangeListener listener;

	public MyTabbedPane()
	{
		tabbedPane=new JTabbedPane();	
		tabbedPane.setBorder(null);
		tabbedPane.setFocusable(false);
		pestanias = new ArrayList<>();
		
		tabbedPane.addChangeListener(new EscuchaSelect());
	}
	
	public void setChangeListener(ChangeListener lst,boolean working)
	{
		this.listener =lst;
		setListenerWorking(working);
	}
	
	public boolean setListenerWorking(boolean b)
	{
		boolean ret = listener!=null;
		
		if( listener!=null )
		{
			if( !b )
			{
				tabbedPane.removeChangeListener(listener);
			}
			else
			{
				tabbedPane.addChangeListener(listener);						
			}
		}
		
		return ret;
	}

	public void addTab(String title, Component component,boolean closeable)
	{
		_addTab(title,component,closeable,null);
	}
	
	public void addTab(Component component,boolean closeable,String tip)
	{
		String title = "Result"+(tabbedPane.getTabCount()+1);
		_addTab(title,component,closeable,tip);		
	}
	
	public void addTab(Component component,boolean closeable)
	{
		String title = "Result"+(tabbedPane.getTabCount()+1);
		_addTab(title,component,closeable,null);				
	}
	
	public void addTab(String title, Component component,boolean closeable,String tip)
	{
		_addTab(title,component,false,tip);
	}

	public void removeTab(int idx)
	{
		tabbedPane.remove(idx);
	}
	
	public void removeAllTabs()
	{
		tabbedPane.removeAll();
	}
	
	public JTabbedPane c()
	{
		return tabbedPane;
	}

	private void _addTab(String title, Component component,boolean closeable,String tip)
	{		

		// pestaña		
		MyLeftLayout pestania = new MyLeftLayout(0,0,0,0);		
		pestania.add(new JLabel(title));
		pestania.setBackground(tabbedPane.getBackground());
		pestanias.add(pestania);
		
		if( closeable )
		{
			MyClosableTabButton closeButton = new MyClosableTabButton(this,component);		
			pestania.add(new MyInsets(closeButton,0,5,0,0));
		}
		
		tabbedPane.addTab(title,null,component,tip);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(component),pestania);
		tabbedPane.setSelectedComponent(component);
		
	}
	
	public int getSelectedIndex() 
	{
		return tabbedPane.getSelectedIndex();
	}

	class EscuchaSelect implements ChangeListener
	{
		Color prevColor = null;
		JComponent prevCmp = null;
		
		@Override
		public void stateChanged(ChangeEvent e)
		{
			int idx = tabbedPane.getSelectedIndex();
			
			if( idx>=0 && pestanias.size()>idx)
			{
				if( prevCmp!=null )
				{
					MyAwt.setBackground(prevCmp,prevColor);
				}
				
				JComponent cmp = pestanias.get(idx);
				
				prevCmp = cmp;
				prevColor = cmp.getBackground();

				MyAwt.setBackground(cmp,Color.WHITE);				
			}
			
		}		
	}

	public void setSelectedTab(int i)
	{
		tabbedPane.setSelectedIndex(i);
	}
}
