package thejavalistener.fwk.awt;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.swing.JTextField;

import thejavalistener.fwk.util.string.MyString;

public class MyParametersFieldImple extends JTextField implements MyJParametersField
{
	private boolean retenido = false;
	private int currProp = 0;
	private int paramCount = 0;
	private List<String> parameters;
//	private List<String> defaultValues;
	
	private Map<String,	List<BiFunction<Character,String,Character>>> customMasks;
	
	public MyParametersFieldImple()
	{
		super(25);
		parameters = new ArrayList<>();
		customMasks = new HashMap<String,List<BiFunction<Character,String,Character>>>();
		addKeyListener(new FunctionMaskListener());
	}
	
	@Override
	public void addCustomMask(String pName,MyJParameterMask mask)
	{
		if( mask!=null )
		{
			customMasks.get(pName).add(mask);
		}
	}
	
	@Override	
	public void addParameter(String pName,String pDefValue)
	{
		addParameter(pName,pDefValue,null);
	}
	
	public void addParamater(MyJParameter p)
	{
		addParameter(p.getParamName(),p.getParamDefValue());
		for(MyJParameterMask m:p.getParamMasks())
		{
			addCustomMask(p.getParamName(),m);
		}
	}
	
	@Override
	public void addParameter(String pName,String pDefValue,MyJParameterMask mask)
	{
		parameters.add(pName);
		customMasks.put(pName,new ArrayList<>());
		
		addCustomMask(pName,mask);
		
		StringBuffer sb = new StringBuffer(getText());
		if(!sb.isEmpty() )
		{
			sb.append(", ");
		}
		
		paramCount++;
				
		sb.append(pName).append("=[").append(pDefValue).append("]");
		setText(sb.toString());
	}
	
	public void setParameters(List<MyJParameter> params)
	{
		for(MyJParameter p:params)
		{
			addParamater(p);
		}
	}
	
	@Override
	public String getParameterValue(String pName)
	{
		String txt = getText();
		String toSearch = pName+"=[";
		int pos = txt.indexOf(toSearch)+toSearch.length();
		return txt.substring(pos,txt.indexOf(']',pos));
	}

	@Override
	public void selectMyParameterValue(int idx)
	{
		String txt = getText();
		int pos1 = MyString.indexOfN(txt,'[',idx+1);
		int pos2 = MyString.indexOfN(txt,']',idx+1);
		select(pos1+1,pos2);
	}
	
	// 0         1         2         3 
	// 0123456789012345678901234567890123456789	
	// prop1=[val1], prop2=[val2], prop3=[val3]
	private boolean isCaretInParameterValue()
	{
		String txt = getText();
		int pos = getCaretPosition();
		
		if( pos==0 || pos==txt.length()-1 ) return false;

		int i=pos;
		char c=txt.charAt(i);
		while(i>=0 && c!='[' && c!=']')
		{
			c=txt.charAt(i);
			i--;
		}

		int j=pos;
		c=txt.charAt(j);
		while(j<txt.length() && c!='[' && c!=']')
		{
			c=txt.charAt(j);
			j++;
		}
		
		return i>=0 && txt.charAt(i+1)=='[' && j<=txt.length() && txt.charAt(j-1)==']';
	}
	
	// prop1=[val1], prop2=[val2], prop3=[val3]
	private int getCurrentCaretParameterIndex()
	{
		String txt = getText();
		int pos = getCaretPosition();
		
		if( isCaretInParameterValue() || txt.charAt(pos)==']')
		{
			// OJOOOOOOO COMENTE ESTA LINEA !!!
			//pos = txt.charAt(pos)==']'?pos-1:pos;
			return MyString.charCount(txt.substring(0,pos),'[')-1;
		}
		return -1;
	}
	
	// prop1=[val1], prop2=[val2], prop3=[val3]
	private String getCurrentCaretParameter()
	{
		String ret = null;
		String txt = getText();
		int pos = getCurrentCaretParameterIndex();
		if( pos>=0 )
		{
			int h = MyString.indexOfN(txt,'=',pos+1);
			int d = pos==0?0:MyString.prevIndexOf(txt,',',h);
			int offset = pos==0?0:1;
			ret = txt.substring(d+offset,h).trim();
		}
		return ret;
	}
	
	// a=[dadad], b=[ewrrwrw], c=[ad52]	
	@Override
	public Map<String,String> getParameterValues()
	{
		Map<String,String> m = new LinkedHashMap<>();
		for(String pName:parameters)
		{
			m.put(pName,getParameterValue(pName));
		}
		
		return m;
	}

	@Override
	protected void processKeyEvent(KeyEvent e)
	{
		final int LEFT=37;
		final int RIGHT=39;
		final int HOME=36;
		final int END=35;
		final int BACKSPACE=8;
		
		if( isMyParameterValueSelected(getCurrentCaretParameter()))
		{
			super.processKeyEvent(e);
			return;
		}
		

		boolean accept = false;
		
		String txt = getText();
		int pos = getCaretPosition();
				
		if(pos==0) selectMyParameterValue(0);
		if(pos>=txt.length()) selectMyParameterValue(paramCount-1);
		
		switch(e.getKeyCode())
		{
			case LEFT,RIGHT,HOME,END: 
				accept = true;
				break;
			case BACKSPACE: 
				accept = (isCaretInParameterValue() && txt.charAt(pos-1)!='[') || txt.charAt(pos-1)!='[' && txt.charAt(pos)==']';
				break;
			default:		
				accept = isCaretInParameterValue() || txt.charAt(pos)==']'; //&& txt.charAt(pos-2)!='=';
				break;
		}

//		boolean seleccionado = getSelectedText().equals(getParameterValue(getCurrentCaretParameter()));
//		if( seleccionado )
//		{
//			setParameterValue(getCurrentCaretParameter(),"");
//		}
		
		if( accept )
		{
			super.processKeyEvent(e);
		}
		else
		{
			e.consume();
		}
	}

	@Override
	public boolean isMyParameterValueSelected(String pName)
	{
		String selectedText = getSelectedText();
		return selectedText!=null && selectedText.equals(getParameterValue(getCurrentCaretParameter()));
	}
	
	@Override
	public void setParameterValue(String pName,String pValue)
	{
		String txt = getText();
		int fr = txt.indexOf(pName+"=[")+(pName+"=[").length();
		int to = txt.indexOf(']',fr);
		
		StringBuffer sb = new StringBuffer();
		sb.append(txt.substring(0,fr)).append(pValue).append(txt.substring(to));
		setText(sb.toString());
	}

	@Override	
	protected void processFocusEvent(FocusEvent e)
	{
		if( e.getID()==FocusEvent.FOCUS_LOST )
		{
			selectMyParameterValue(currProp++);
			retenido=currProp<=paramCount;
			if( retenido )
			{				
				requestFocus();
			}
			else
			{
				currProp=0;
			}
		}
		else
		{
			if( !retenido && e.getID()==FocusEvent.FOCUS_GAINED )
			{
				selectMyParameterValue(currProp++);
			}
		}
		
		super.processFocusEvent(e);
	}
	
	class FunctionMaskListener extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
			String pName = getCurrentCaretParameter();
			if(pName!=null )
			{
				List<BiFunction<Character,String,Character>> lst = customMasks.get(pName);
				for(BiFunction<Character,String,Character> f:lst)
				{
					Character c = f.apply(e.getKeyChar(),getParameterValue(pName));
					if( c!=null )
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
	}	
	
	// 0         1         2         3 
	// 0123456789012345678901234567890123456789
	// prop1=[val1], prop2=[val2], prop3=[val3]
	public static void main(String args[])
	{
		int caretPos = 20;
		MyParametersFieldImple x = new MyParametersFieldImple();
		x.setText("prop1=[val1], prop2=[val2], prop3=[val3]");
		x.setCaretPosition(caretPos);
		
		System.out.println(x.isCaretInParameterValue());
		System.out.println(x.getCurrentCaretParameterIndex()+", "+x.getCurrentCaretParameter());
		System.out.println(x.getParameterValue("prop1"));
		System.out.println(x.getParameterValue("prop2"));
		System.out.println("["+x.getParameterValue("prop3")+"]");
		
		x.setParameterValue("prop3","LOCOMIA");
		System.out.println(x.getParameterValue("prop3"));
	}	
	
}