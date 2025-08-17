package thejavalistener.fwk.awt.panel;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class MyCardLayout
{
	private CardLayout cardLayout;
	private MyPanel cardPanel;
	
	public MyCardLayout()
	{
		cardPanel = new MyPanel(0,0,0,0);
		cardPanel.setBorder(null);
		cardPanel.setLayout(cardLayout = new CardLayout());
	}
	
	public void add(String id,Component c)
	{
		cardPanel.add(c,id);
	}
	
	public void show(String id)
	{
		cardLayout.show(cardPanel,id);
	}
	
	public JPanel c()
	{
		return cardPanel;
	}
	
	public void removeAll()
	{
		cardPanel.removeAll();
		cardPanel.revalidate();
		cardPanel.repaint();
		cardLayout = new CardLayout();
		
	}

	public void remove(int idx)
	{
		Component c = cardPanel.getComponent(idx);
		cardLayout.removeLayoutComponent(c);
	}

	public void toggleNext() {
		cardLayout.next(cardPanel);
	}}
