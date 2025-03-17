package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.console.MyConsole;
import thejavalistener.fwk.console.MyConsoleBase;
import thejavalistener.fwk.console.MyConsoleListener;

@Component
public abstract class ScreenFormConsoleTemplate extends ScreenConsoleTemplate
{
	protected MyForm form = null;
				
	public ScreenFormConsoleTemplate()
	{
		// instancio el form y lo agrego al west (en el center está la consola)
		form = new MyForm();				
		add(form.c(),BorderLayout.WEST);
	}
	
	protected MyForm getForm()
	{
		return form;		
	}	
}
