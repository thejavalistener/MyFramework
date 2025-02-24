package thejavalistener.fwk.awt.textarea;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.dialog.MyDialog;
import thejavalistener.fwk.awt.dialog.Returnable;
import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.util.string.ParametrizedString;

public class MyParameterPane extends MyBorderLayout implements Returnable
{
	private List<String> params = new ArrayList<>();	
	private List<MyTextField> myTextFields = new ArrayList<>();	
	
	private MyForm form;
		
	private JButton bExecute;
	private JButton bCancel;
	
	private Object returnValue;
	private MyDialog myDialog;
	
	private ParametrizedString ps;
	
	public MyParameterPane(ParametrizedString ps)
	{
		this.ps = ps;
		
		bCancel = new JButton("Cancel");
		bCancel.addActionListener(new EscuchaCancel());
		bExecute = new JButton("Execute");
		bExecute.addActionListener(new EscuchaExecute());

		form = new MyForm();
		_generarForm();
		

		MyScrollPane scrollForm = new MyScrollPane(form.c());
		add(scrollForm,BorderLayout.CENTER);

//		myDialog.configurator().pack().centerH(150).apply();

		
//		MyCenterLayout cl = new MyCenterLayout();
	
//		cl.add(bCancel);
//		cl.add(bExecute);
//		add(cl,BorderLayout.SOUTH);
//		pack();
	}
	
	private void _generarForm()
	{
		params.clear(); // parametros y labels
		myTextFields.clear(); // textfields
		
		double wL=.3;
		double wC=.7;
		
		form.reset();
		
		// trate todos los parametros delimitados con ${}
		params = ps.getParameters();
		
		if( params.size()>0 )
		{
			for(int i=0; i<params.size(); i++)
			{
				MyTextField tf = new MyTextField();			
				myTextFields.add(tf);
				
				MyLink lbl = new MyLink(params.get(i));
				form.addRow().add(lbl.c()).add(tf.c()).layout(wL,wC);				
			}
		
			form.addSeparator(0.8);
		}
		
		form.addRow().add(bCancel).add(bExecute);
		
		form.makeForm();
		
		if( myTextFields.size()>0 )
		{
			myTextFields.get(0).requestFocus();
		}
		else
		{
			bExecute.requestFocus();
		}
		
//		getMyDialog().configurator().pack().centerH(150);
//		getMyDialog().c().pack();
//		getMyDialog().centerH(150);
//		validate();		
	}
	
	private void _generarObject(ParametrizedString ps)
	{
		for(int i=0; i<params.size(); i++)
		{
			ps.setParameterValue(params.get(i),myTextFields.get(i).getText());
		}
	}
		
	class EscuchaExecute implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				for(MyTextField tf:myTextFields)
				{
					tf.runValidation(s->!s.isEmpty(),"El parámametro no puede quedar vacío","Error");
				}
				
				_generarObject(ps);
				
				myDialog.closeDialog(ps.toString());
			}
			catch(MyException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	class EscuchaCancel implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			myDialog.closeDialog();
		}
	}
	
	private void _resetTextFields()
	{
		myTextFields.forEach((c)->c.resetValue());
		myTextFields.get(0).requestFocus();		
	}		
	
	@Override
	public void setMyDialog(MyDialog dlg)
	{
		this.myDialog = dlg;
	}
	
	@Override
	public Object getReturnValue()
	{
		return this.returnValue;
	}

	@Override
	public void setReturnValue(Object returnValue)
	{
		this.returnValue = returnValue;
	}
	
	
}