package thejavalistener.fwk.frontend;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.dialog.MyDialog;
import thejavalistener.fwk.awt.link.MyLinkedPane;
import thejavalistener.fwk.awt.link.MyLinkedPaneStyle;

@Component
@Scope("prototype")
public class MyApp
{
	@Autowired
	private ApplicationContext ctx;
	
	private static final int STARTED=2;
	private static final int STOPED=3;
	
	private int state;
	
	private MyAppListener listener;
	
	private String appName = null;
	private MyAppContainer appContainer = null;
	
	private MyLinkedPaneStyle style = new MyLinkedPaneStyle();
	
	private MyLinkedPane screens = null;
	private MyAbstractScreen currentScreen = null;
	
	private boolean allowSwitch = true;

	public MyApp(String appName)
	{
		screens = new MyLinkedPane(MyLinkedPane.HORIZONTAL);
		screens.setSeparatorBeforeLinks('»');
		screens.setActionListener(new EscuchaScreens());
		this.appName = appName;
	}
	
	public void setAppListener(MyAppListener listener)
	{
		this.listener = listener;
	}
	
	public void showScreens(boolean b)
	{
		screens.showLinks(b);
	}
	
	public void allowSwitch(boolean b)
	{
		getMyAppContainer().allowSwitch(b);
	}
	
	public boolean isSwitchAllowed()
	{
		return allowSwitch;
	}
	
	public void pushScreen(Class<? extends MyAbstractScreen> screenClass,Object ...args)
	{
		if(!allowSwitch )
		{
			return;
		}
		
		// instancio la pantalla principal
		MyAbstractScreen screen = ctx.getBean(screenClass);
		screen.setParameters(args);
		screen.setMyApp(this);

		if( currentScreen!=null )
		{
			currentScreen.stop();
		}		
		
		currentScreen = screen;
		
		// ciclo de vida
		currentScreen.createUI();
		currentScreen.onDataUpdated();
		currentScreen.preInit();
		currentScreen.init();
		
		screens.addTab(screen.getName(),screen,true);

		if( state==STARTED )
		{
			currentScreen.start();
		}
		
		applyStyle();		
	}
		
	public void popScreen()
	{
		if( !allowSwitch )
		{
			return;
		}
		
		// ciclo de vida
		currentScreen.stop();
		currentScreen.destroy();
		
		// remuevo la solapa
		screens.removeLast();
		
		currentScreen = null;
		if( getScreenCount()>0 )
		{
			currentScreen = (MyAbstractScreen)screens.getComponent(getScreenCount()-1);
			currentScreen.start();
		}		
	}
	
//	public MyDialogConfigurator showScreen(Class<? extends MyAbstractScreen> screenClass,Object ...args)
	public MyDialog.Configurator showScreen(Class<? extends MyAbstractScreen> screenClass,Object ...args)
	{
		if( currentScreen!=null )
		{
			currentScreen.stop();
		}		
		
		// instancio el screen
		MyAbstractScreen screen = ctx.getBean(screenClass);	
		screen.setParameters(args);
		screen.setMyApp(this);
		

		MyDialog dlg = new MyDialog(getMyAppContainer().c(),screen,screen.getName());
		dlg.setMyDialogListener(l->{
			screen.stop();
			screen.destroy();
			currentScreen.start();});
		
		screen.createUI();
		screen.onDataUpdated();
		screen.preInit();
		screen.init();
		screen.start();


		return dlg.configurator();
//		
//		MyDialogConfigurator cfg = new MyDialogConfigurator(dlg);
//		return cfg;
	}
		
//	public void changeTopScreen(Class<? extends MyAbstractScreen2> newScreenClass,MyAbstractScreen2 currScreen,Object ...args)
//	{
//		currScreen.exit(showScreen(newScreenClass,args));
//	}
		
	public int getScreenCount()
	{
		return screens.getTabCount();
	}
	
	public void setStyle(MyLinkedPaneStyle style)
	{
		this.style = style;
		applyStyle();
	}
	
	public void applyStyle()
	{
		screens.setStyle(style);
	}
	
	void setName(String name)
	{
		this.appName = name;
	}
	
	public String getName()
	{
		return appName;
	}
	
	public Container c()
	{
		return screens.c();
	}
	
	void setMyAppContainer(MyAppContainer cnt)
	{
		this.appContainer = cnt;
	}
	
	public MyAppContainer getMyAppContainer()
	{
		return this.appContainer;
	}
	
	void start()
	{
		state = STARTED;
		if( currentScreen!=null )
		{
			currentScreen.start();
		}
	}
	
	void stop()
	{
		state = STOPED;
		currentScreen.stop();
	}
	
	void destroy()
	{
		while( getScreenCount()>0 )
		{
			popScreen();
		}
		
		if( listener!=null )
		{
			listener.appDestroyed(this);
		}
	}
	
	private Map<?,?> currState = null;
	public void setDisabledTemporally(boolean disable)
	{
		if( disable )
		{
			currState = MyAwt.disableTemporally(c());
		}
		else
		{
			MyAwt.restoreDisabled(currState);			
		}
	}

	
	class EscuchaScreens implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int idx = screens.getSelectedIndex();

			while( screens.getTabCount()>idx+1 )
			{
				popScreen();
			}
		}
	}
}
