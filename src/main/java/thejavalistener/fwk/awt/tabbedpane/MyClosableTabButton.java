package thejavalistener.fwk.awt.tabbedpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MyClosableTabButton extends JButton
{
	private JButton outer;
	
	private Color mouseOver = Color.RED;
	private Color defaultColor = Color.LIGHT_GRAY;
	private MyTabbedPane tabbedPane;
	private Component component;
	
	
	public MyClosableTabButton(MyTabbedPane tabbedPane,Component component)
	{
		super("x");

		this.outer = this;
		this.tabbedPane = tabbedPane;
		this.component = component;
		
		addMouseListener(new EscuchaMouse());
		addActionListener(new EscuchaButton());
		setBorder(null);
		setForeground(defaultColor);
		setBackground(tabbedPane.c().getBackground());
		setFocusable(false);
	}

	class EscuchaButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = tabbedPane.c().indexOfComponent(component);
			if(index!=-1)
			{
				tabbedPane.removeTab(index);
			}
		}
	}
	
	class EscuchaMouse extends MouseAdapter
	{
		@Override
		public void mouseEntered(MouseEvent e)
		{
			setForeground(mouseOver);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			setForeground(defaultColor);
		}
	}
}
