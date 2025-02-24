package thejavalistener.fwk.awt.optionpane;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.panel.MyLeftLayout;
import thejavalistener.fwk.awt.textarea.MyTextField;
import thejavalistener.fwk.util.string.MyString;

public class MyInputPane extends MyAbstractOptionPane
{
	private String message;
	private MyTextField textField;
	
	public MyInputPane(String mssg,String title,Window parent)
	{
		super(title,parent);
		this.message = mssg;
		textField = new MyTextField();
	}

	@Override
	protected JPanel getMessagePane()
	{
		MyLeftLayout p = new MyLeftLayout(10,3,3,0);
		MyLink l = new MyLink(message);
		p.add(l.c());
		return p;
	}

	@Override
	protected JPanel getComponentPane()
	{
		MyBorderLayout p = new MyBorderLayout(0,10,0,10);
		p.add(textField.c(),BorderLayout.NORTH);
		return p;
	}
	
	public MyInputPane addMask(BiFunction<Character,String,Character> mask)
	{
		textField.addMask(mask);
		return this;
	}

	public MyInputPane addValidation(Function<String,Boolean> valid)
	{
		textField.addValidation(valid,"","");
		return this;
	}

	@Override
	protected JPanel getButtonPane()
	{
		MyCenterLayout p = new MyCenterLayout(9,0,10,0);
		JButton bAceptar= new JButton("Aceptar");
		textField.onENTER(()->MyAwt.triggerActionEvent(bAceptar));

		bAceptar.addActionListener(e->{
			try
			{
				textField.runValidations();
				closeDialog();
			}
			catch(Exception e2)
			{
				textField.selectAll();
				textField.requestFocus();
			}
		});
		p.add(bAceptar);
		return p;
	}
	

	@Override
	protected Object getReturnValue()
	{
		return textField.getValue();
	}
	
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
//		MyPanel.DEBUG_MODE = true;
		MyInputPane p = new MyInputPane("Ingrese número:","Input",null);
		p.setWidth(350);
		p.addValidation(s->MyString.isNumber(s)).addValidation(s->Integer.parseInt(s)<1000);
		Object x = p.show(30,30);
		System.out.println(x);
		System.exit(0);
	}
}
