package thejavalistener.fwk.frontend;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.console.MyConsole;
import thejavalistener.fwk.console.MyConsoleListener;
import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.MyThread;

@Component
public abstract class ScreenConsoleTemplate extends MyAbstractScreen
{
	protected MyConsole console = null;
				
	public ScreenConsoleTemplate()
	{
		setLayout(new BorderLayout());

		// instancio la consola y la agrego al center
		console = new MyConsole();
		console.addListener(new EscuchaConsola());
		add(console.c(),BorderLayout.CENTER);
	}
	
	@Override
	protected void preInit()
	{
		String mssg = "";
		mssg+="ATENCIÓN: Debo sacar esto que es re trucho y borrar preInit() de esta clase\n";
		mssg+="Ver cómo mierda resolver el problema del wait con el ALT+TAB, \n";
		mssg+="sacar este startDelayed y ponerlo como corresponde si es que sirve para algo.";
		MyLog.println(mssg);
		
//		MyThread.startDelayed(()->{
//			
//		console.mainWindow = getMyApp().getMyAppContainer().c();
//		console.mainWindow.addWindowFocusListener(new WindowFocusListener()
//		{
//			@Override
//			public void windowLostFocus(WindowEvent e)
//			{
//				try
//				{					
//					MyLog.println("CHAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
//					JOptionPane.showMessageDialog(console.mainWindow,"En espera");
//				}
//				catch(Exception e2)
//				{
//					e2.printStackTrace();
//					throw new RuntimeException(e2);
//				}
//			}
//			
//			@Override
//			public void windowGainedFocus(WindowEvent e)
//			{
//				try
//				{					
//					MyLog.println("OOOOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");	
//				}
//				catch(Exception e2)
//				{
//					e2.printStackTrace();
//					throw new RuntimeException(e2);
//				}
//			}
//		});
//		
//		
//		},5000);
//		super.preInit();
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
