package thejavalistener.fwk.frontend.hql.screen;
//package myframework.frontend.hql.screen;
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import framework.awt.MyJTextField;
//import framework.awt.MyTextField;
//import framework.awt.dialog.MyDialog;
//import framework.awt.dialog.Returnable;
//import framework.awt.form.MyForm;
//import framework.awt.link.MyLink;
//import framework.awt.list.MyComboBox;
//import framework.awt.list.MyJComboBox;
//import framework.frontend.MyAbstractScreen;
//import framework.util.MyProperties;
//import framework.util.NamedValue;
//import framework.util.string.ParametrizedString;
//
//@Component
//public class HQLSelectPredefQuery extends MyAbstractScreen implements Returnable
//{
//	@Autowired
//	private HQLFacade facade;
//	
//	private List<String> params = new ArrayList<>();	
//	private List<MyTextField> myTextFields = new ArrayList<>();	
//	
//	private MyForm form;
//		
//	private MyComboBox<NamedValue<String>> cbQueries;
//	
//	private JButton bExecute;
//	private JButton bCancel;
//	
//	private Object returnValue;
//	private MyDialog myDialog;
//	
//	
//	
//	
//	
//	
//	
//	@Override
//	protected void createUI()
//	{
//		form = new MyForm();
//		JScrollPane scrollForm = new JScrollPane();
//		scrollForm.setBorder(null);
//		
//		JPanel pp = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		pp.add(form.c());
//		scrollForm.setViewportView(pp);
//		
//		add(scrollForm,BorderLayout.WEST);
//		bCancel = new JButton("Cancel");
//		bCancel.addActionListener(new EscuchaCancel());
//		bExecute = new JButton("Execute");
//		bExecute.addActionListener(new EscuchaExecute());
//	}
//	
//	protected void onDataUpdated()
//	{
//		// entities
//		cbQueries = new MyJComboBox<>();
//		cbQueries.c().setPreferredSize(new Dimension(300,cbQueries.c().getPreferredSize().height));
//		cbQueries.setItemListener(new EscuchaQueries());
//		cbQueries.setTToString(nv->nv.getName());
//		
//		MyProperties properties = new MyProperties("hql.properties");
//		cbQueries.setItems(properties.getProperties());
//		cbQueries.sort((a,b)->a.getName().compareTo(b.getName()));
//		
//		// si me pasan como parametro cual consulta quieren invocar por default
//		Object[] params = getParameters();
//		if(params.length>0)
//		{
//			cbQueries.setSelectedItem(nv->nv.getName().equals(params[0].toString()));
//		}
//		
//		// genero los fields
//		cbQueries.forceItemEvent();		
//	}
//	
//	class EscuchaQueries implements ItemListener
//	{
//		@Override
//		public void itemStateChanged(ItemEvent e)
//		{
//			_generarForm(cbQueries.getSelectedItem());
////			getMyDialog().setTitle("Crear objeto: "+cbQueries.getSelectedItem().getName());
//		}
//	}
//	
//	private void _generarForm(NamedValue<String> namedValue)
//	{
//		params.clear(); // parametros y labels
//		myTextFields.clear(); // textfields
//		
//		double wL=.3;
//		double wC=.7;
//		
//		form.reset();
//		
//		
//		form.addRow().add(cbQueries.c());
//		form.addSeparator();
//		
//		// trate todos los parametros delimitados con ${}
//		ParametrizedString ps = new ParametrizedString(namedValue.getValue());
//		params = ps.getParameters();
//		
//		if( params.size()>0 )
//		{
//			for(int i=0; i<params.size(); i++)
//			{
//				MyTextField tf = new MyJTextField();			
//				myTextFields.add(tf);
//				
//				MyLink lbl = new MyLink(params.get(i));
////				form.addRow().add(lbl.c(),wL).add(tf.c(),wC);				
//				form.addRow().add(lbl.c()).add(tf.c()).layout(wL,wC);				
//			}
//		
//			form.addSeparator(0.8);
//		}
//		
//		form.addRow().add(bCancel).add(bExecute);
//		
//		form.makeForm();
//		
//		if( myTextFields.size()>0 )
//		{
//			myTextFields.get(0).requestFocus();
//		}
//		else
//		{
//			bExecute.requestFocus();
//		}
//		
//		myDialog.configurator().pack().centerH(150).apply();
////		getMyDialog().configurator().pack().centerH(150);
////		getMyDialog().c().pack();
////		getMyDialog().centerH(150);
////		validate();		
//	}
//	
//	private void _generarObject(ParametrizedString ps)
//	{
//		for(int i=0; i<params.size(); i++)
//		{
//			ps.setParameterValue(params.get(i),myTextFields.get(i).getText());
//		}
//	}
//		
//
//	class EscuchaExecute implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			try
//			{
//				for(MyTextField tf:myTextFields)
//				{
//					tf.runValidation(s->!s.isEmpty(),"El parámametro no puede quedar vacío","Error");
//				}
//				
//				ParametrizedString ps = new ParametrizedString(cbQueries.getSelectedItem().getValue());
//				_generarObject(ps);
//				
//				NamedValue<String> ret = new NamedValue<>();
//				ret.setName(cbQueries.getSelectedItem().getName());
//				ret.setValue(ps.toString());
//				
////				exit(ret);
//			}
//			catch(Exception ex)
//			{
//				showExceptionMessage(ex);
//			}
//		}
//	}
//	
//	class EscuchaNew implements ActionListener
//	{
//		private Class<?> typeClass;
//		public EscuchaNew(Class<?> typeClass)
//		{
//			this.typeClass = typeClass;
//		}
//		
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			putValueFor(HQLSelectPredefQuery.class,"entityClass",typeClass);
////			getMyApp().showScreen(HQLSelectPredefQuery.class);
//		}
//	}
//	
//	class EscuchaCancel implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
////			exit();
//		}
//	}
//	
//	private void _resetTextFields()
//	{
//		myTextFields.forEach((c)->c.resetValue());
//		myTextFields.get(0).requestFocus();		
//	}
//	
//
//	@Override
//	public String getName()
//	{
//		return "Queries predefinidos";
//	}
//
//	@Override
//	public void setReturnValue(Object returnValue)
//	{
//		this.returnValue = returnValue;
//	}
//
//	@Override
//	public Object getReturnValue()
//	{
//		return returnValue;
//	}
//
//	@Override
//	public void setMyDialog(MyDialog dlg)
//	{
//		this.myDialog = dlg;
//	}
//	
//}