package thejavalistener.fwk.awt.dialog;

import javax.swing.JButton;
import javax.swing.JPanel;

import thejavalistener.fwk.util.UDate;

public class MyDialogTest
{
	public static void main(String[] args)
	{
		ReturnablePanel rp = new ReturnablePanel();
		MyDialog dlg = new MyDialog(null,rp,"Demo MyDialog");
		Object ret = dlg.configurator().size(300,300).apply();
		
		System.out.println(ret);
	}
	
	static class ReturnablePanel extends JPanel implements Returnable
	{
		private Object returnValue = null;
		private MyDialog dlg;
		
		public ReturnablePanel()
		{
			JButton b1 = new JButton("1");
			add(b1);
			b1.addActionListener(l->dlg.closeDialog(1));
			JButton b2 = new JButton("now()");
			add(b2);
			b2.addActionListener(l->dlg.closeDialog(new UDate()));
			JButton b3 = new JButton("Hola");
			add(b3);
			b3.addActionListener(l->dlg.closeDialog("Hola"));
		}
		
		@Override
		public void setMyDialog(MyDialog dlg)
		{
			this.dlg = dlg;
		}
		
		@Override
		public Object getReturnValue()
		{
			return returnValue;
		}
		
		@Override
		public void setReturnValue(Object returnValue)
		{
			this.returnValue = returnValue;
		}
		
	}
}
