package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;
import java.awt.Container;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyPanel;
import thejavalistener.fwk.console.MyConsole;
import thejavalistener.fwk.console.MyConsoleBase;
import thejavalistener.fwk.console.MyConsoleListener;

@Component
public abstract class ScreenConsoleTemplate extends MyAbstractScreen
{
	private MyConsole console;
	private MyForm form;
				
	public ScreenConsoleTemplate()
	{
		setLayout(new BorderLayout());

		// instancio el form y la consola
		console = new MyConsole(this);
		console.addListener(new EscuchaConsola());

		form = new MyForm();
		
		// armo la UI
		MyPanel pCenter = new MyBorderLayout();
		
		// agrego la consola
		pCenter.add(configureConsoleArea());

		// segundo pido el form
		pCenter.add(configureFormArea(),BorderLayout.WEST);
		add(pCenter,BorderLayout.CENTER);
	}
	
	protected Container configureConsoleArea()
	{
		return console.getScrollPane();
	}
	
	protected Container configureFormArea()
	{
		return form.c();		
	}
	
	protected MyConsole getConsole()
	{
		return console;
	}
	
	protected MyForm getForm()
	{
		return form;
	}
	
	class EscuchaConsola implements MyConsoleListener
	{
		@Override
		public void waitingForUserInput(boolean b)
		{
			// si la consola esta en espera => no admito cambio de apps
			allowAppSwitch(!b);
		}
	}
}
