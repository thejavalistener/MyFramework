package thejavalistener.fwk.awt.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.textarea.MyTextField;

public class MyInstantForm 
{
	private JDialog dialog = null;
	private MyForm form; 
	private List<String> ids;
	private List<String> labels;
	private List<MyTextField> fields;
	private JButton bAccept;
	private JButton bCancel;
	
	private JFrame parent;
	
	private boolean cancelado = false;
	
	private Map<String,String> returnValue = new HashMap<>();
	
	public MyInstantForm(String title,JFrame parent)
	{
		form = new MyForm();
		fields = new ArrayList<>();
		labels = new ArrayList<>();
		ids = new ArrayList<>();
		
		dialog = new JDialog(parent,title);
		dialog.setModal(true);
		dialog.addWindowListener(new EscuchaDialog());
		
		bAccept = new JButton("Aceptar");
		bAccept.addActionListener(new EscuchaAccept());
		bCancel = new JButton("Cancelar");
		bCancel.addActionListener(new EscuchaCancel());

		this.parent = parent;
	}
	
	public boolean wasCanceled()
	{
		return cancelado;
	}
	
	public String getValue(String id)
	{
		return returnValue!=null?returnValue.get(id):null;
	}
		
	public FieldConfigurator addField(String id,String label)
	{
		MyTextField tf = new MyTextField();
		form.addRow().add(label).add(tf.c()).layout(.3,.7);;
		ids.add(id);
		labels.add(label);
		fields.add(tf);	
		
		return new FieldConfigurator(tf);
	}
	
	public class FieldConfigurator
	{
		MyTextField tf;
		
		FieldConfigurator(MyTextField t)
		{
			tf = t;
		}
		
		public FieldConfigurator valid(Function<String,Boolean> f,String errMssg)
		{
			tf.addValidation(f,errMssg,null);
			return this;
		}
		
		public FieldConfigurator mask(BiFunction<Character,String,Character> m)
		{
			tf.addMask(m);
			return this;
		}	
		
		public FieldConfigurator value(Object defValue)
		{
			if( defValue!=null )
			{
				tf.setText(defValue.toString());
			}
			
			return this;
		}
	}
	
	
	
	public Map<String,String> showForm()
	{
		form.addSeparator(.8);		
		form.addRow().add(bAccept).add(bCancel);
		form.makeForm();
		
		dialog.add(form.c(),BorderLayout.CENTER);
		
		dialog.pack();
		MyAwt.center(dialog,parent);
		dialog.setVisible(true);
		return returnValue.isEmpty()?null:returnValue;
	}
	
	private void _closeDialog(boolean canceled)
	{
		cancelado = canceled;
		dialog.setVisible(false);
		dialog.dispose();		
	}

	
	class EscuchaAccept implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for(int i=0; i<fields.size(); i++)
			{				
				try
				{
					fields.get(i).runValidations();
				}
				catch(MyException e1)
				{
					JOptionPane.showMessageDialog(dialog,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}				
			}
			
			returnValue = new HashMap<>();
			for(int i=0; i<fields.size(); i++) 
			{
				String txt = fields.get(i).getText();
				String value = txt!=null?txt.trim():"";
				returnValue.put(ids.get(i),value);
			}

			_closeDialog(false);
		}
	}
		
	class EscuchaDialog extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			_closeDialog(true);
		}
	}
	
	class EscuchaCancel implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			_closeDialog(true);
		}
	}

}
