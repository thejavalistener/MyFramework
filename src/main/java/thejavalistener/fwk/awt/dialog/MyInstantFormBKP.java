package thejavalistener.fwk.awt.dialog;
//package myframework.awt.dialog;
//
//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import myframework.awt.MyException;
//import myframework.awt.MyJTextField;
//import myframework.awt.MyTextField;
//import myframework.awt.form.MyForm;
//
//public class MyInstantForm 
//{
//	private MyDialog dialog = null;
//	private MyForm form; 
//	private List<String> ids;
//	private List<String> labels;
//	private List<MyTextField> fields;
//	private JButton bAccept;
//	private JButton bCancel;
//	private ReturnableForm returnablePanel;
//	
//	private boolean cancelado = false;
//	
//	public MyInstantForm(String title,JFrame parent)
//	{
//		form = new MyForm();
//		fields = new ArrayList<>();
//		labels = new ArrayList<>();
//		ids = new ArrayList<>();
//		
//		returnablePanel = new ReturnableForm();
//		dialog = new MyDialog(parent,returnablePanel,title);
//		dialog.setMyDialogListener(new EscuchaDialog());
//		dialog.setCloseable(false);
//		
//		bAccept = new JButton("Aceptar");
//		bAccept.addActionListener(new EscuchaAccept());
//		bCancel = new JButton("Cancelar");
//		bCancel.addActionListener(new EscuchaCancel());
//	}
//	
//	public void addField(String id,String lbl)
//	{
//		addField(id,lbl,s->true,"");
//	}
//	
//	public void addField(String id,String lbl,Function<String,Boolean> f,String errMssg)
//	{
//		addField(id,lbl,f,errMssg,null);
//	}
//	
//	public void addField(String id,String lbl,Function<String,Boolean> f,String errMssg,BiFunction<Character,String,Character> mask)
//	{
//		MyTextField tf = new MyJTextField();
//		tf.addValidation(f,errMssg,"");
//		
//		if(mask!=null)
//			tf.addMask(mask);
//		
//		form.addRow().add(lbl).add(tf.c()).layout(.3,.7);;
//		
//		ids.add(id);
//		labels.add(lbl);
//		fields.add(tf);		
//	}
//	
////	public MyDialog dialog()
////	{
////		return dialog;
////	}
//	
//	public Map<String,String> showForm()
//	{
//		form.addSeparator(.8);		
//		form.addRow().add(bAccept).add(bCancel);
//		form.makeForm();
//		return (Map<String,String>) dialog.configurator().pack().apply();
//	}
//
//	
//	class ReturnableForm extends JPanel implements Returnable
//	{
//		public ReturnableForm()
//		{
//			setBorder(null);
//			setLayout(new BorderLayout(0,0));
//			add(form.c(),BorderLayout.CENTER);
//		}
//		
//		private Map<String,String> returnValue;
//		@Override
//		public void setReturnValue(Object returnValue)
//		{
//			this.returnValue = (Map<String,String>)returnValue;
//		}
//
//		@Override
//		public Object getReturnValue()
//		{
//			if( !cancelado )
//			{
//				Map<String,String> ret = new HashMap<>();
//				for(int i=0; i<fields.size(); i++) 
//				{
//					String txt = fields.get(i).getText();
//					String value = txt!=null?txt.trim():"";
//					ret.put(ids.get(i),value);
//				}
//				return ret;
//			}
//			else
//			{
//				return null;
//			}
//		}
//
//		@Override
//		public void setMyDialog(MyDialog dlg)
//		{
//		}
//	}
//	
//	class EscuchaAccept implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			for(int i=0; i<fields.size(); i++)
//			{				
//				try
//				{
//					fields.get(i).runValidations();
//				}
//				catch(MyException e1)
//				{
//					JOptionPane.showMessageDialog(returnablePanel,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
//					return;
//				}				
//			}
//
//			dialog.closeDialog(returnablePanel.getReturnValue());
//		}
//	}
//	
//	class EscuchaDialog implements MyDialogListener
//	{
//		@Override
//		public void onEvent(int eventType)
//		{
//			cancelado=true;
////			dialog.closeDialog(null);			
//		}
//	}
//	
//	class EscuchaCancel implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			cancelado=true;
//			dialog.closeDialog(null);
//		}
//	}
//
//}
