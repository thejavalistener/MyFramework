package thejavalistener.fwk.awt.optionpane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.panel.GridLayout2;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyInsets;

public abstract class MyOptionPaneBKP //extends JDialog
{
	private Window parent;
	
	private JPanel buttonPanel;
	private JPanel messagePanel;
	private JPanel componentPanel;
	private JDialog jDialog;
	
	private boolean closeable = true;
	
	public abstract JPanel getMessagePane();
	public abstract JPanel getComponentPane();
	public abstract JPanel getButtonPane();
	
	public abstract Object getReturnValue();
	
	private Thread thread;
	
	public MyOptionPaneBKP(String title,Window parent)
	{
		jDialog = new JDialog(parent,title);
		this.parent = parent;
		Image transparentIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		jDialog.setIconImage(transparentIcon);

//		jDialog.setResizable(false);
	}
	
	public void setCloseable(boolean b)
	{
		this.closeable = b;
		
		if( closeable )
		{
			jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);			
		}
		else
		{
			jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);			
		}
	}
	
	public boolean isCloseable()
	{
		return closeable;
	}
	
	public Object show()
	{
		return show(false);
	}
	
	public Object show(boolean async)
	{
//		MyForm f = new MyForm();
//		
//		MyBorderLayout b1 = new MyBorderLayout();
//		b1.add(getMessagePane(),BorderLayout.SOUTH);
//		f.addRow().add(b1);
//		f.addRow().add(getComponentPane()).layout(1);
//		f.addRow().add(getButtonPane());
//		f.makeForm();
//
//		jDialog.setLayout(new BorderLayout(0,0));
//		jDialog.add(f.c(),BorderLayout.CENTER);
//
//		jDialog.pack();
////		jDialog.setSize(375,132);
		

		messagePanel = getMessagePane();
		componentPanel = getComponentPane();
		buttonPanel = getButtonPane();

		MyBorderLayout b = new MyBorderLayout();
		b.add(messagePanel,BorderLayout.NORTH);
		b.add(componentPanel,BorderLayout.CENTER);
		b.add(buttonPanel,BorderLayout.SOUTH);
		
		jDialog.setLayout(new BorderLayout(0,0));
		jDialog.add(b,BorderLayout.CENTER);

		jDialog.setSize(375,132);
		
		MyAwt.center(jDialog,parent);

		if( async )
		{
			Runnable r = ()->{jDialog.setModal(true);jDialog.setVisible(true);};
			thread = new Thread(r);
			thread.start();	
			return null;
		}
		else
		{
			jDialog.setModal(true);
			jDialog.setVisible(true);
			return getReturnValue();
		}
	}
	
	public JButton getButton(String actionCommand)
	{
		for(Component c: buttonPanel.getComponents())
		{
			JButton button = (JButton)c;
			if( button.getActionCommand().equals(actionCommand) )
			{
				return button;
			}
		}
		
		return null;
	}
	
	public void dispose()
	{
		thread.interrupt();
		jDialog.setVisible(false);
		jDialog.dispose();
	}
}
