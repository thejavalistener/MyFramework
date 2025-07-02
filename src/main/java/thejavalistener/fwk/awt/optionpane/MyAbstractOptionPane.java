package thejavalistener.fwk.awt.optionpane;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyAwt;

public abstract class MyAbstractOptionPane
{
	private Window parent;

	private JPanel buttonPanel;
	private JPanel messagePanel;
	private JPanel componentPanel;
	private JDialog jDialog;
	private Thread thread;

	private boolean closeable=true;
	private boolean canceled=false;

	protected abstract JPanel getMessagePane();
	protected abstract JPanel getComponentPane();
	protected abstract JPanel getButtonPane();
	protected abstract Object getReturnValue();

	public MyAbstractOptionPane(String title,Window parent)
	{
		jDialog=new JDialog(parent,title);
		jDialog.addWindowListener(new EscuchaWindow());
		
		this.parent=parent;
		// Image transparentIcon = new BufferedImage(1, 1,
		// BufferedImage.TYPE_INT_ARGB_PRE);
		// jDialog.setIconImage(transparentIcon);

		// jDialog.setResizable(false);
	}

	public void setCloseable(boolean b)
	{
		this.closeable=b;
	}

	public boolean isCloseable()
	{
		return closeable;
	}

	public Object show()
	{
		return show(null,null);
	}
	
	public Object show(Integer x,Integer y)
	{
		return show(false,x,y);
	}


	private int width=400;

	public Object show(boolean async,Integer x,Integer y)
	{
		registrarEscucharESC();
		messagePanel=getMessagePane();
		componentPanel=getComponentPane();
		buttonPanel=getButtonPane();

		GridBagLayout gbl=new GridBagLayout();
		jDialog.setLayout(gbl);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.weightx=1.0;
		jDialog.add(messagePanel,gbc);

		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=1.0;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		jDialog.add(componentPanel,gbc);

		gbc.gridx=0;
		gbc.gridy=2;
		gbc.weightx=1.0;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		jDialog.add(buttonPanel,gbc);

		// jDialog.setSize(375,132);

		jDialog.pack();

		if(width>0)
		{
			int w=Math.max(jDialog.getSize().width,width);
			MyAwt.setWidth(w,jDialog);
		}

		if( x==null || y==null)
		{
			MyAwt.center(jDialog,parent);
		}
		else
		{
			jDialog.setLocation(x,y);
		}
		
		if(async)
		{
			Runnable r=() -> {
				jDialog.setModal(true);
				jDialog.setVisible(true);
			};
			thread=new Thread(r);
			thread.start();
			return null;
		}
		else
		{
			jDialog.setModal(true);
			jDialog.setVisible(true);

			if(!canceled)
			{
				return getReturnValue();
			}
			else
			{
				return null;
			}
		}
	}


	
	
	public void setWidth(int w)
	{
		this.width=w;
	}

	public JButton getButton(String actionCommand)
	{
		for(Component c:buttonPanel.getComponents())
		{
			JButton button=(JButton)c;
			if(button.getActionCommand().equals(actionCommand))
			{
				return button;
			}
		}

		return null;
	}

	public void closeDialog()
	{
		if(thread!=null) thread.interrupt();
		jDialog.setVisible(false);
		jDialog.dispose();
	}

	class EscuchaWindow extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			if(closeable)
			{
				closeDialog();
				canceled=true;
			}
		}
	}

	public void registrarEscucharESC()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
		{
			@Override
			public boolean dispatchKeyEvent(KeyEvent e)
			{
				if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_ESCAPE)
				{
					canceled = true;
					closeDialog();
					return true;
				}
				return false;
			}
		});
	}
}
