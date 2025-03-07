package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.console.MyConsole;
import thejavalistener.fwk.console.MyConsoleBase;
import thejavalistener.fwk.console.MyConsoleListener;

@Component
public abstract class ScreenConsoleTemplate extends MyAbstractScreen
{
	protected MyConsole console = null;
				
	public ScreenConsoleTemplate()
	{
		setLayout(new BorderLayout());

		// instancio la consola y la agrego al center
		console = new MyConsole(this);
		console.addListener(new EscuchaConsola());
		add(console.getScrollPane(),BorderLayout.CENTER);
	}
	
	protected MyConsole getConsole()
	{
		return console;
	}
		
	class EscuchaConsola implements MyConsoleListener
	{
		@Override
		public void waitingForUserInput(boolean waiting)
		{
			// si la consola esta en espera => no admito cambio de apps
			//allowAppSwitch(!b);

			getMyApp().getMyAppContainer().setDisabledTemporally(waiting);
			console.getTextPane().setEnabled(true);
		}
	}
}
