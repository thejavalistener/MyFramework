package thejavalistener.fwk.awt.link;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import thejavalistener.fwk.awt.link.MyLink;

public class MyJLinkPanelVOLAR extends JPanel implements MyLinkPanelVOLAR,ActionListener
{
	private Insets defaultInsets = new Insets(5,10,0,0);
	private int lblCount = 0;
	private GridBagLayout gbl = null;
	private JPanel pLabels = null;
	public ActionListener listener;
	private MyLink lblSelected = null;
	private List<MyLink> links = null;
	
	private Color backgroundColor = null;

	
	public MyJLinkPanelVOLAR()
	{
		setLayout(new BorderLayout());
		gbl = new GridBagLayout();

		pLabels = new JPanel(gbl);
		
		links = new ArrayList<>();
		
		add(pLabels,BorderLayout.NORTH);
	}
		
	@Override
	public MyLink getSelected()
	{
		return lblSelected;
	}
	
	@Override
	public int getSelectedIndex()
	{
		return (Integer)lblSelected.getRelatedObject("pos");
	}
	
	public void setBackgroundColor(Color c)
	{
		throw new RuntimeException("No implementado todavía");
	}
	
	@Override
	public void addLink(String text)
	{
		addLink(new MyLink(text));
	}
	
	@Override
	public void addLink(MyLink lbl)
	{
		
//		lbl.setUnselectedBackground(backgroundColor);
		lbl.getStyle().linkBackgroundUnselected = backgroundColor;

		lbl.addRelatedObject("pos",links.size()); // idx
		links.add(lbl);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = lblCount++;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = defaultInsets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		pLabels.add(lbl.c(),gbc);
		
		lbl.configureAs(MyLink.TOGGLE_ON);		
		lbl.setActionListener(this);
	}
	
	public void setDefaultInsets(Insets i)
	{
		this.defaultInsets = i;
	}
	
	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void setSelected(int i)
	{
		setSelected(i,false);
	}

	@Override
	public void setSelected(int i,boolean throwEvent)
	{
		if( lblSelected!=null )
		{
			lblSelected.setSelected(false);
		}
		
		lblSelected = links.get(i);
		lblSelected.setSelected(true);

		if( throwEvent )
		{
			ActionEvent e = new ActionEvent(lblSelected,1,lblSelected.getText());
			this.listener.actionPerformed(e);		
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyLink x = (MyLink)e.getSource();
		
		if( lblSelected!=null )
		{
			lblSelected.setSelected(false);
		}
		
		lblSelected = x;
		this.listener.actionPerformed(e);
	}
}