package thejavalistener.fwk.awt.link;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import thejavalistener.fwk.util.MyCollection;

public class MyLinkGroup
{
	private List<MyLink> links;
	private MyLink selected;
	
	private ActionListener listener;
	private boolean listenerWorking = true;
	
	private EscuchaLinks escuchaLinks;
	
	public MyLinkGroup()
	{
		links = new ArrayList<>();
		escuchaLinks = new EscuchaLinks();
	}
	
	public void setEnabled(boolean b)
	{
		for(MyLink lnk:links)
		{
			lnk.c().setEnabled(b);
		}
	}
	
	public void setOthersEnabled(boolean b)
	{
		for(MyLink lnk:links)
		{
			if( !lnk.isSelected() )
			{
				lnk.setEnabled(b);				
			}
		}
	}
	
	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	public void addLink(MyLink lnk)
	{
		boolean prev = setListenerWorking(false);
		
		lnk.configureAs(MyLink.TOGGLE_ON);
		links.add(lnk);
		lnk.setActionListener(escuchaLinks);
		lnk.setSelected(false);
		
		setListenerWorking(prev);
	}
	
	public void setSelected(int idx)
	{
		setSelected(idx,false);
	}
	
	public void setSelected(int idx,boolean throwEvent)
	{
		if( selected!=null )
		{
			selected.setSelected(false);
		}
		
		selected = links.get(idx);
		selected.setSelected(true);

		if( listener!=null && listenerWorking && throwEvent )
		{
			ActionEvent e = new ActionEvent(selected,1,selected.getText());
			listener.actionPerformed(e);
		}

	}
	
	public MyLink removeLink(int idx)
	{
		return links.remove(idx);			
	}
	
	public List<MyLink> getLinks()
	{
		return links;
	}
	
	public boolean setListenerWorking(boolean b)
	{
		boolean prev = listenerWorking;
		listenerWorking = b;
		return prev;
	}
	
	class EscuchaLinks implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			MyLink lnk = (MyLink)e.getSource();
			if( selected!=null )
			{
				selected.setSelected(false);
			}
			
			selected = lnk;
			selected.setSelected(true);

			if( listener!=null && listenerWorking ) 
			{
				listener.actionPerformed(e);
			}
		}
	}
	

	public int getSelectedIndex()
	{
		return MyCollection.findPos(links,lnk->lnk.isSelected());
	}
	
	public MyLink getSelected()
	{
		return selected;
	}

	public void removeAll()
	{
		links.clear();
	}

}
