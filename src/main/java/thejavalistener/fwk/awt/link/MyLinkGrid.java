package thejavalistener.fwk.awt.link;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.function.Consumer;

import javax.swing.JPanel;

import thejavalistener.fwk.awt.panel.GridLayout2;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyLeftLayout;

public class MyLinkGrid 
{
	private JPanel contentPane;
	private JPanel gridPane;
	private JPanel currRow;
	private MyLink justAddedLink;
	private Color backgroundColor;
	
	private Consumer<MyLink> defaultTreatment = (lnk)->{};
	
	public MyLinkGrid()
	{
		contentPane = new MyBorderLayout();
		gridPane = new JPanel(new GridLayout2(0,1,0,0));
		contentPane.add(gridPane,BorderLayout.NORTH);
	}
	
	public void setDefaultTreatment(Consumer<MyLink> c)
	{
		this.defaultTreatment = c;
	}
	
	public MyLinkGrid addNewRow()
	{
		currRow = new MyLeftLayout();
		currRow.setBackground(backgroundColor);
		gridPane.add(currRow);
		return this;
	}
	
	public MyLinkGrid add(String lbl)
	{
		justAddedLink = new MyLink(lbl);
		defaultTreatment.accept(justAddedLink);
		return addCell(justAddedLink);
	}
	
	public MyLink getJustAddedLink()
	{
		return justAddedLink;
	}

	public MyLinkGrid addCell(MyLink lnk)
	{
		currRow.add(lnk.c());
		return this;
	}
	
	public void setBackground(Color bg)
	{
		backgroundColor = bg;
 		contentPane.setBackground(bg);
 		gridPane.setBackground(bg);
	}
	
	public Component c()
	{
		return contentPane;	
	}
}
