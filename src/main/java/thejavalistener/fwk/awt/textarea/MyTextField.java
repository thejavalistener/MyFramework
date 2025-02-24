package thejavalistener.fwk.awt.textarea;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener; 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JTextField;

import thejavalistener.fwk.awt.MyComponent;
import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.util.MyAssert;
import thejavalistener.fwk.util.UDate;
import thejavalistener.fwk.util.string.MyString;

public class MyTextField implements MyComponent
{
	public static final BiFunction<Character,String,Character> MASK_HEX = (c,s)->MyString.isHexDigit(c)?c:null;
	public static final BiFunction<Character,String,Character> MASK_CHAR = (c,s)->s.length()<3?c:null;
	public static final BiFunction<Character,String,Character> MASK_INTEGER = (c,s)->Character.isDigit(c)||s.length()==0&&c=='-'?c:null;
	public static final BiFunction<Character,String,Character> MASK_DOUBLE = (c,s)->s.isEmpty()&&c=='-' || Character.isDigit(c) || c=='.' && s.indexOf('.')<0?c:null;
	public static final BiFunction<Character,String,Character> MASK_BOOLEAN = (c,s)->MyString.parseBoolean(s)!=null?c:null;
	public static final BiFunction<Character,String,Character> MASK_UPPERCASE = (c,s)->Character.toUpperCase(c);
	public static final BiFunction<Character,String,Character> MASK_LOWERCASE = (c,s)->Character.toLowerCase(c);
	public static final BiFunction<Character,String,Character> MASK_FLAG = (c,s)->c=='1'||c=='0'&&s.length()<2?c:null;
	public static final BiFunction<Character,String,Character> MASK_AÑZ = (c,s)->c>='A' && c<='Z' || c=='Ñ'?c:null;
	public static final BiFunction<Character,String,Character> MASK_AZ = (c,s)->c>='A' && c<='Z'?c:null;
	
	public static final Function<String,Boolean> VALID_NOTEMPTY = (s)->!s.isEmpty();
	public static final Function<String,Boolean> VALID_DATE = (s)->UDate.validate(s);
	public static final Function<String,Boolean> VALID_DATE_OR_EMPTY = (s)->UDate.validate(s) || s.isEmpty();
	public static final Function<String,Boolean> VALID_SHORT = (s)->!MyAssert.throwsException(()->Short.parseShort(s));
	public static final Function<String,Boolean> VALID_INTEGER = (s)->!MyAssert.throwsException(()->Integer.parseInt(s));
	public static final Function<String,Boolean> VALID_INTEGER_OR_ENTPY = (s)->!MyAssert.throwsException(()->Integer.parseInt(s)) || s.isEmpty();
	public static final Function<String,Boolean> VALID_LONG = (s)->!MyAssert.throwsException(()->Long.parseLong(s));
	public static final Function<String,Boolean> VALID_DOUBLE = (s)->!MyAssert.throwsException(()->Double.parseDouble(s));
	public static final Function<String,Boolean> VALID_DOUBLE_OR_EMPTY = (s)->!MyAssert.throwsException(()->Double.parseDouble(s))||s.isEmpty();
	public static final Function<String,Boolean> VALID_FLOAT = (s)->!MyAssert.throwsException(()->Float.parseFloat(s));
	public static final Function<String,Boolean> VALID_CHARACTER = (s)->s.length()==1;
	public static final Function<String,Boolean> VALID_BOOLEAN = (s)->s.trim().equalsIgnoreCase("true")||s.trim().equalsIgnoreCase("false");
	public static final Function<String,Boolean> VALID_FLAG = (s)->s.equals("1") || s.equals("0");
	public static final Function<String,Boolean> VALID_FLAG_OR_EMPTY = (s)->s.equals("1") || s.equals("0")|| s.isEmpty();
	
	private JTextField textField;
	
	private List<BiFunction<Character,String,Character>> customMask;
	private List<Function<String,Boolean>> validations;
	private List<String[]> validationsMssgTitle;
	
	private Runnable doOnENTER = null;

	public MyTextField(int cols)
	{
		this();
		setColumns(cols);
	}
	
	public MyTextField()
	{
		textField = new JTextField();
		
		customMask=new ArrayList<>();
		validations=new ArrayList<>();
		validationsMssgTitle=new ArrayList<>();

		textField.addKeyListener(new EscuchaKey());
//		addFocusListener(escuchaFocus=new EscuchaFocus());
	}
	
	public void setColumns(int c)
	{
		textField.setColumns(c);
	}

	class EscuchaKey extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
			if( e.getKeyChar()==KeyEvent.VK_ENTER && doOnENTER!=null )
			{
				doOnENTER.run();
				return;
			}
			
			for(BiFunction<Character,String,Character> f:customMask)
			{
				Character c=f.apply(e.getKeyChar(),getText());
				if(c!=null)
				{
					e.setKeyChar(c);
				}
				else
				{
					e.consume();
				}
			}
		}
	}
	
	public void onENTER(Runnable r)
	{
		this.doOnENTER = r;
	}
	
	public void clear()
	{
		setText("");
	}

//	public void setHint(String s)
//	{
//
//		// super.setHint(s);
//	}

	public MyTextField addMask(BiFunction<Character,String,Character> bf)
	{
		customMask.add(bf);
		return this;
	}

	public MyTextField addValidation(Function<String,Boolean> f, String mssg, String title)
	{
		validations.add(f);
		validationsMssgTitle.add(new String[] {mssg, title});
		return this;
	}

	public void runValidations() throws MyException
	{

		for(int i=0; i<validations.size(); i++)
		{
			runValidation(validations.get(i),validationsMssgTitle.get(i)[0],validationsMssgTitle.get(i)[1]);
		}
	}

	public void runValidation(Function<String,Boolean> f, String mssg, String title) throws MyException
	{
		if(!f.apply(getText()))
		{
			requestFocus();
			selectAll();
			throw new MyException(mssg,title,MyException.ERROR);
		}
	}

	@Override
	public void resetValue()
	{
		setText("");
	}

	public JComponent c()
	{
		return textField;
	}

	/** Si isEmpty retorna null, de otro modo retorna getText */
	@Override
	public Object getValue()
	{
		String txt=getText();
		return txt.length()==0?null:txt;
	}

	public void setBackground(Color c)
	{
		textField.setBackground(c);
	}
	
	public void setForeground(Color c)
	{
		textField.setForeground(c);
	}
	
	class EscuchaFocus implements FocusListener
	{
		String hint="Mi hint loco";

		@Override
		public void focusGained(FocusEvent e)
		{
			if(getText().equals(hint))
			{
				setText("");
			}
			else
			{
				setText(getText());
			}
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			if(getText().equals(hint)||getText().length()==0)
			{
				setText(hint);
				setForeground(Color.GRAY);
			}
			else
			{
				setText(getText());
				setForeground(Color.BLACK);
			}
		}
	}
	
	public boolean valid(Function<String,Boolean> valid)
	{
		return valid.apply(getText());
	}

	public String getText()
	{
		return textField.getText();
	}

	public void setText(String t)
	{
		textField.setText(t);
	}

	@Override
	public void requestFocus()
	{
		textField.requestFocus();		
	}

	public void selectAll()
	{
		textField.selectAll();
	}

	@Override
	public void setEnabled(boolean b)
	{
		textField.setEnabled(b);
	}

	public boolean isEnabled()
	{
		return textField.isEnabled();
	}
}
