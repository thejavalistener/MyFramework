package thejavalistener.fwk.awt.link;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyGridLayout;
import thejavalistener.fwk.awt.panel.MyLeftLayout;
import thejavalistener.fwk.awt.panel.MyPanel;

public class MyLinkPanel 
{
	// linkPanel
	private Insets horizontalInsets = new Insets(0,0,0,0);
	private Insets verticalInsets = new Insets(0,0,0,0);
	private Color background = null;
	
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	private List<MyLink> links;
	
	private MyPanel contentPANE;
	private MyPanel contentPane;
	private MyPanel linkPanel;
	private MyBorderLayout verticalPanel;
		
	private int orientation;
	
	public MyLinkPanel(int orientation)
	{
		this(orientation,new Insets(0,0,0,0));
	}

	public MyLinkPanel(int orientation,int t,int l,int b,int r)
	{
		this(orientation,new Insets(t,l,b,l));
	}
	
	public MyLinkPanel(int orientation,Insets insets)
	{
		this.orientation =orientation;
		
		_createUI(orientation,insets);
		
		links = new ArrayList<>();
	}
	
	public void applyStyle()
	{
		Insets insets = orientation==HORIZONTAL?horizontalInsets:verticalInsets;
		contentPane.setInsets(insets);
		contentPane.setBackground(background);
	}
	
	public void setInsets(int t,int l,int b,int r)
	{
		setInsets(new Insets(t,l,b,r));
	}
	
	private void _createUI(int orientation,Insets insets)
	{
		contentPane = new MyBorderLayout();		
		contentPANE = new MyBorderLayout(contentPane,insets,BorderLayout.CENTER);

		if( orientation == HORIZONTAL )
		{
			linkPanel = new MyLeftLayout(insets);
			contentPane.add(linkPanel,BorderLayout.CENTER);

			int alto = linkPanel.getPreferredSize().height+12;
			MyAwt.setPreferredHeight(alto,contentPane);
		}
		else
		{
			linkPanel = new MyGridLayout(insets);
			((MyGridLayout)linkPanel).setGridDimensions(0,1);
			verticalPanel = new MyBorderLayout(linkPanel,insets,BorderLayout.NORTH);

			contentPane.add(verticalPanel,BorderLayout.CENTER);
		}		
	}
	
	private Map<MyLink,MyLink> separators = new HashMap<>();
	
	int max = 0;
	public void addLink(MyLink lnk)
	{
		links.add(lnk);	
		linkPanel.add(lnk.c());
		
		max = Math.max(max,lnk.c().getPreferredSize().width);
		MyAwt.setPreferredWidth(max,contentPane);
		MyAwt.setPreferredHeight(lnk.c().getPreferredSize().height,contentPane);
		contentPane.validate();
	}
	
	public MyLink addSeparatorBefore(char s,MyLink lnk)
	{
		int pos = linkPanel.getComponentZOrder(lnk.c());
		MyLink lnkSep = new MyLink(Character.toString(s));
		
		lnkSep.getStyle().linkBackgroundInsets.right = 0;
		lnkSep.getStyle().linkInsets.right = 0;
		
		if( pos>0 )
		{
			lnkSep.getStyle().linkBackgroundInsets.left = 0;
			lnkSep.getStyle().linkInsets.left = 0;
			
		}
		
		lnkSep.applyStyle();
		linkPanel.add(lnkSep.c(),pos);
		
		separators.put(lnk,lnkSep);
		
		return lnkSep;
	}
	
	public MyLink addSeparatorAfter(char s,MyLink lnk)
	{
		int pos = linkPanel.getComponentZOrder(lnk.c());
		MyLink lnkSep = new MyLink(Character.toString(s));
		
		lnkSep.getStyle().linkBackgroundInsets.right = 0;
		lnkSep.getStyle().linkInsets.right=0;
		
		lnk.getStyle().linkBackgroundInsets.left=100;
		lnk.getStyle().linkInsets.right=100;
		lnk.applyStyle();
		
		lnkSep.applyStyle();
		linkPanel.add(lnkSep.c(),pos+1);
		
		separators.put(lnk,lnkSep);
		
		return lnkSep;
	}

	
	public void setLinksFrom(MyLinkGroup linkGroup)
	{
		for(MyLink lnk:linkGroup.getLinks())
		{
			addLink(lnk);
		}
	}
		
	public void removeLink(MyLink lnk)
	{
		int idx = links.indexOf(lnk);
		removeLink(idx);
	}
	
	public void removeLink(int idx)
	{
		MyLink x = links.remove(idx);
		linkPanel.remove(x.c());
		
		MyLink lnkSep = separators.remove(x);
		if( lnkSep!=null )
		{
			linkPanel.remove(lnkSep.c());
		}
		
		linkPanel.revalidate();
		linkPanel.repaint();
		contentPane.validate();
	}
			
	public JPanel c()
	{
		applyStyle();
		return contentPANE;
	}

	public void removeAll()
	{
		while(links.size()>0)
		{
			removeLink(0);
		}
	}

	public int getLinkCount()
	{
		return links.size();
	}

	public Insets getInsets()
	{
		return orientation==HORIZONTAL?horizontalInsets:verticalInsets;
	}

	public void setInsets(Insets insets)
	{
		if( orientation==HORIZONTAL) 
		{
			horizontalInsets = insets;
		}
		else
		{
			verticalInsets = insets;
		}
		
		linkPanel.setInsets(insets);
		contentPane.setInsets(insets);
		
		if( verticalPanel!=null) verticalPanel.setInsets(insets);
		
		contentPANE.setInsets(insets);
	}

	public Color getBackground()
	{
		return background;
	}
	
//	public void setBackground(Color background)
//	{
//		this.background=background;
//		linkPanel.setBackground(background);
//		contentPane.setBackground(background);
//		contentPANE.setBackground(background);
//		if( verticalPanel!=null ) verticalPanel.setBackground(background);
//	}
	
	public List<MyLink> getLinks()
	{
		return links;
	}	
}
