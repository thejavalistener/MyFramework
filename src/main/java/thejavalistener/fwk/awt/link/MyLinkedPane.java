package thejavalistener.fwk.awt.link;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JSplitPane;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyCardLayout;
import thejavalistener.fwk.awt.splitpane.MySplitPane;
import thejavalistener.fwk.frontend.MyAbstractScreen;

public class MyLinkedPane
{
	public static final int REMOVE_PREV=-1;
	public static final int REMOVE_NEXT=1;
	
	private int onRemoveSelectedPolicy = REMOVE_PREV;
	
	private MySplitPane splitPane;
	
	private MyLinkGroup linkGroup;
	private MyLinkPanel linkPanel;
	private MyCardLayout cardLayout;
	private Map<MyLink,Component> components;
	
	private ActionListener listener;

	private MyLinkedPaneStyle style = new MyLinkedPaneStyle();
	
	private Character separator = null;
	
	private boolean listenerWorking = true;
	
	// estilo
	private Color background;
		
	public static final int VERTICAL = MyLinkPanel.VERTICAL;
	public static final int HORIZONTAL = MyLinkPanel.HORIZONTAL;

	private int orientation = HORIZONTAL;
	
	public MyLinkedPane(int orientation)
	{
		this.orientation = orientation;
		
		// linkPanel
		linkPanel = new MyLinkPanel(orientation,0,0,0,0);
		
		// linkGroup
		linkGroup = new MyLinkGroup();
		
		linkGroup.setActionListener(new EscuchaLinkGroup());

		// cardLayout
		cardLayout = new MyCardLayout();
		
		components = new LinkedHashMap<>();
		
		
		int splitOrientation = orientation!=HORIZONTAL?JSplitPane.HORIZONTAL_SPLIT:JSplitPane.VERTICAL_SPLIT;
		splitPane = new MySplitPane(splitOrientation,linkPanel.c(),cardLayout.c());	
	}	
	
	public MyLink addTab(String title,Component c)
	{
		return addTab(title,c,false);
	}
		
	public MyLink addTab(MyLink lnk,Component c)
	{
		return addTab(lnk,c,false);
	}
	
	public MyLink addTab(String title,Component c,boolean selected)
	{
		MyLink lnk = new MyLink(title);
		return addTab(lnk,c,selected);
	}
	
	public void setEnabled(boolean b)
	{
		linkGroup.setEnabled(b);
	}
	
	public void setOthersEnabled(boolean b)
	{
		linkGroup.setOthersEnabled(b);
	}
	
	private MyLink addTab(MyLink lnk,Component c,boolean selected)
	{
		boolean prev = setListenerWorking(false);
		
		lnk.setStyle(style.linkStyle);
		lnk.applyStyle();
		
		// asigno estilo y agrego al linkPanel
		linkPanel.addLink(lnk);
		
		if( separator!=null )
		{
			linkPanel.addSeparatorBefore(separator,lnk);
		}
		
		
		linkGroup.addLink(lnk);

		// agrego el componente al cardLayout
		String title = lnk.getText();		
		cardLayout.add(title,c);
		components.put(lnk,c);

		if( linkPanel.getLinkCount()==1 || selected )
		{
			int curr = linkPanel.getLinkCount()-1;
			setSelectedTab(curr);
		}
		
		splitPane.c().validate();
		splitPane.c().resetToPreferredSizes();
		
		setListenerWorking(prev);
		
		return lnk;
	}
	
	public boolean setListenerWorking(boolean b)
	{
		boolean prev = listenerWorking;
		listenerWorking = b;
		return prev;
	}
	
	public void setStyle(MyLinkedPaneStyle style)
	{
		this.style = style;
		applyStyle();
	}
	
	public MyLinkedPaneStyle getStyle()
	{
		return style;
	}
	
	public void applyStyle()
	{
//		linkPanel.setBackground(style.linkPanelBackground);
		linkPanel.setInsets(style.linkPanelInsets);
		splitPane.setBackground(style.linkPanelBackground);
		splitPane.setDividerSize(style.dividerSize);
		for(MyLink lnk:getLinks())
		{
			lnk.setStyle(style.linkStyle);
			lnk.applyStyle();
		}
	}
	
	public List<MyLink> getLinks()
	{
		return linkPanel.getLinks();
	}
	
	public MyLink getLink(int idx)
	{
		return linkPanel.getLinks().get(idx);
	}
	
	public JSplitPane c()
	{
		applyStyle();
		return splitPane.c();
	}
	
	public void setBackground(Color bg)
	{
		MyAwt.setBackground(linkPanel.c(),bg);
	}
	
	public void removeAll()
	{
		cardLayout.removeAll();
		linkPanel.removeAll();
		components.clear();
	}

	public int getTabCount()
	{
		return linkPanel.getLinkCount();
	}
	
	public Component getComponent(int idx)
	{
		return components.get(getLink(idx));
	}

	
	public void setSelectedTab(int idx)
	{
		setSelectedTab(idx,false);
	}
	
	public void setSelectedTab(int idx,boolean throwEvent)
	{
		String title = getLinks().get(idx).getText();
		linkGroup.setSelected(idx,throwEvent);
		cardLayout.show(title);
	}	
	
	public Component removeLast()
	{
		return removeTab(linkPanel.getLinkCount()-1);
	}

	public Component removeTab(int i)
	{
		MyLink lnk = linkGroup.removeLink(i);
		linkPanel.removeLink(i);
		cardLayout.remove(i);

		Component c = components.remove(lnk);
		
		
		if( getTabCount()>0 )
		{
			if( i==0 && onRemoveSelectedPolicy==REMOVE_PREV )
			{
				setSelectedTab(0);			
			}
			else
			{
				if( i==getTabCount() && onRemoveSelectedPolicy==REMOVE_NEXT )
				{
					setSelectedTab(getTabCount()-1);
				}
				else
				{
					// 0  1  3  4  5
					setSelectedTab(i-1);
				}
			}
		}
		
		return c;
	}
	
	public void setLinkPanelInsets(Insets insets)
	{
		linkPanel.setInsets(insets);
	}

	public void removeSelectedTab()
	{
		int idx = linkGroup.getSelectedIndex();
		removeTab(idx);
	}
	
	public int getSelectedIndex()
	{
		return linkGroup.getSelectedIndex();
	}

	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	public void setOnRemoveSelectedPolicy(int policy)
	{
		onRemoveSelectedPolicy = policy;
	}
	
	public void setInsets(int top, int left, int bottom, int right)
	{
		linkPanel.setInsets(top,left,bottom,right);
	}
	
	class EscuchaLinkGroup implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(e.getActionCommand());
			if( listener!=null) listener.actionPerformed(e);
		}
	}
	
	public void showLinks(boolean b)
	{
		splitPane.showComponent(0,b);
	}

	public void setSeparatorBeforeLinks(char sep)
	{
		this.separator = sep;
	}
}
