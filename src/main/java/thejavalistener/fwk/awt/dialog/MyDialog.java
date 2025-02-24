package thejavalistener.fwk.awt.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.MyAssert;

public class MyDialog 
{
	private MyDialog outer;
	private JDialog jDialog = null;
	private MyDialogListener listener = null;
	private JFrame parent = null;
	private Returnable content = null;
	private boolean closeable = true;
		
	public MyDialog(JFrame parent,Container content,String title)
	{
		jDialog = new JDialog(parent,title,true);
		jDialog.getContentPane().add(content,BorderLayout.CENTER);
		jDialog.addWindowListener(new EscuchaWindow());	
		this.parent = parent;
		
		MyAssert.test(content instanceof Returnable,"La clase "+content.getClass().getName()+" debe implementar "+Returnable.class.getName());
		this.content = (Returnable)content;
		this.content.setMyDialog(this);
		
		outer = this;
	}
	
	public void setCloseable(boolean b)
	{
		closeable = b;
	}
	
	public Configurator configurator()
	{		
		Configurator cfg = new Configurator();
		return cfg;		
	}	
	
	private void setSize(double porc)
	{
		jDialog.setSize(MyAwt.getSize(parent,porc));
	}

	public void setTitle(String t)
	{
		jDialog.setTitle(t);
	}

	private void center()
	{
		MyAwt.center(jDialog,parent);
	}

	private void centerV(int offSetFromLeft)
	{
		MyAwt.centerV(offSetFromLeft,jDialog,parent);
	}

	private void centerH(int offSetFromTop)
	{
		MyAwt.centerH(offSetFromTop,jDialog,parent);
	}
	
	public JDialog c()
	{
		return jDialog;
	}
	
	Object getReturnValue()
	{
		return content.getReturnValue();
	}
	
	public void closeDialog()
	{
		closeDialog(null);
	}

	public void closeDialog(Object returnValue)
	{
		if( returnValue!=null)
			((Returnable)content).setReturnValue(returnValue);
		
		if( listener!=null)
			listener.onEvent(MyDialogListener.WindowClosing);

		jDialog.setVisible(false);
		jDialog.dispose();
	}
	
	public void setMyDialogListener(MyDialogListener l)
	{
		this.listener = l;
	}

	class EscuchaWindow extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			if( !closeable )
				return;
			
			if( listener!=null )
				listener.onEvent(MyDialogListener.WindowClosing);
		}
	}
	
	public class Configurator
	{		
		// fija el tamaño y centra el dlg
		public Configurator size(int w,int h)
		{
			outer.c().setSize(w,h);
			return this;
		}
		
		// fija el tamaño proporcional y centra el dlg
		public Configurator size(double d)
		{
			outer.setSize(d);
			outer.center();
			return this;
		}
		
		public Configurator pack()
		{
			outer.c().pack();
			outer.center();
			return this;
		}
		
		public Configurator centerH(int offsetFromTop)
		{
			outer.centerH(offsetFromTop);
			return this;
		}
		
		public Configurator centerV(int offsetFromLeft)
		{
			outer.centerV(offsetFromLeft);
			return this;
		}
		
		public Object apply()
		{
			outer.c().validate();
			
			if( !outer.c().isVisible() )
			{
				outer.c().setVisible(true);
			
			}
			return outer.getReturnValue();
		}	
	}
}
