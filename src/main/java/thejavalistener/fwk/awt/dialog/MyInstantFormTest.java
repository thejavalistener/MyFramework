package thejavalistener.fwk.awt.dialog;

import java.util.Map;
import java.util.function.Function;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.textarea.MyTextField;
import thejavalistener.fwk.util.string.MyString;

public class MyInstantFormTest
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		
		
		MyInstantForm frm = new MyInstantForm("Pepino",null);
		frm.addField("num","Numero").valid(s->MyString.isInteger(s),"Debe ser entero").mask(MyTextField.MASK_INTEGER);
		frm.addField("fec","Fecha").mask(MyTextField.MASK_INTEGER);
		frm.addField("loco","Numero").valid(s->s.equals("x"),"Debe ser x");
		
		Map<String,String> ret = frm.showForm();
		
		System.out.println(ret);
	}

}
