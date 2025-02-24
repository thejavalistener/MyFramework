package thejavalistener.fwk.frontend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import thejavalistener.fwk.awt.MyException;

public class MyValidation
{
	private Map<String,List<Supplier<Boolean>>> validations;
	private Map<String,List<String>> errorMessages;
	
	public MyValidation()
	{
		validations = new LinkedHashMap<>();
		errorMessages = new LinkedHashMap<>();
	}
	
	public void assertTrue(String key,String errMssg,Supplier<Boolean> f)
	{
		List<Supplier<Boolean>> lstFunc = validations.get(key);
		List<String> lstErr = errorMessages.get(key);
		if( lstFunc==null )
		{
			lstFunc = new ArrayList<>();
			validations.put(key,lstFunc);

			lstErr = new ArrayList<>();
			errorMessages.put(key,lstErr);
		}
		
		lstFunc.add(f);
		lstErr.add(errMssg);
	}
	
	public void assertKey(String key) throws MyException
	{
		List<Supplier<Boolean>> lstFunc = validations.get(key);
		List<String> errMssg = errorMessages.get(key);
		
		for(int i=0; i<lstFunc.size(); i++)
		{
			Supplier<Boolean> f =  lstFunc.get(i);
			String e = errMssg.get(i);

			if( !f.get() )
			{
				MyException ex = new MyException(e,"Error de validación",JOptionPane.ERROR_MESSAGE);
				throw ex;
			}
		}
	}
	
	public void assertAll() throws MyException
	{
		Iterator<Map.Entry<String,List<Supplier<Boolean>>>> iterator=validations.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry<String,List<Supplier<Boolean>>> entry = iterator.next();
			
			String k = entry.getKey();
			assertKey(k);
		}
	}

}
