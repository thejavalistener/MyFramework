package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.link.MyLinkedPane;
import thejavalistener.fwk.util.MyFileProperties;
import thejavalistener.fwk.util.hotkey.KeyIdentifier;

@Component
public class MyAppContainer
{
	@Autowired
	private MyFileProperties properties;
	
	@Autowired
	private ApplicationContext ctx;
	
	private JFrame jFrame = null;
	
	private MyLinkedPane applications;

	private MyApp currApp = null;
	private MyApp prevApp = null;
	
	private MyAppContainerStyle style = new MyAppContainerStyle();
	
	public MyAppContainer()
	{
		// ventana principal
		jFrame = new JFrame();
		jFrame.addWindowListener(new EscuchaWindow());

		// linkedPane de aplicaciones
		applications = new MyLinkedPane(MyLinkedPane.VERTICAL);
		applications.setActionListener(new EscuchaApplications());
		jFrame.add(applications.c(),BorderLayout.CENTER);		
	}
	
	public int getMyAppCount()
	{
		return applications.getTabCount();
	}
	
	public MyApp getMyApp(int idx)
	{
		return (MyApp)applications.getLinks().get(idx).getRelatedObject("app");
	}
	
	public void setTitle(String title)
	{
		jFrame.setTitle(title);
	}

	
	// ------------- HOT KEY ------------
	private final Map<KeyIdentifier, Runnable> hotKeyActions = new HashMap<>();

	public void addHotkey(String combo, Runnable action) {
	    combo = combo.trim().toUpperCase();

	    // Separar partes (ej: ["CTRL", "ALT", "ENTER"])
	    String[] parts = combo.split("\\+");
	    String keyPart = parts[parts.length - 1]; // última parte = tecla principal

	    int keyCode = parseKeyCode(keyPart);
	    int modifiers = 0;

	    for (int i = 0; i < parts.length - 1; i++) {
	        switch (parts[i]) {
	            case "CTRL"     -> modifiers |= InputEvent.CTRL_DOWN_MASK;
	            case "ALT"      -> modifiers |= InputEvent.ALT_DOWN_MASK;
	            case "SHIFT"    -> modifiers |= InputEvent.SHIFT_DOWN_MASK;
	            case "ALTGR"    -> modifiers |= InputEvent.ALT_GRAPH_DOWN_MASK;
	            case "META"     -> modifiers |= InputEvent.META_DOWN_MASK;
	        }
	    }

	    hotKeyActions.put(new KeyIdentifier(keyCode, modifiers), action);
	}
	
	private int parseKeyCode(String key) {
	    return switch (key) {
	        case "ENTER"   -> KeyEvent.VK_ENTER;
	        case "ESCAPE"  -> KeyEvent.VK_ESCAPE;
	        case "TAB"     -> KeyEvent.VK_TAB;
	        case "SPACE"   -> KeyEvent.VK_SPACE;
	        case "BACKSPACE" -> KeyEvent.VK_BACK_SPACE;
	        case "DELETE"  -> KeyEvent.VK_DELETE;
	        case "UP"      -> KeyEvent.VK_UP;
	        case "DOWN"    -> KeyEvent.VK_DOWN;
	        case "LEFT"    -> KeyEvent.VK_LEFT;
	        case "RIGHT"   -> KeyEvent.VK_RIGHT;
	        default -> {
	            if (key.matches("F\\d{1,2}")) {
	                int n = Integer.parseInt(key.substring(1));
	                yield KeyEvent.VK_F1 + (n - 1);
	            } else {
	                yield KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
	            }
	        }
	    };
	}
	
	private void _registerHotKeys() 
	{
		if( !hotKeyActions.isEmpty() )
		{
		    KeyboardFocusManager.getCurrentKeyboardFocusManager()
		        .addKeyEventDispatcher(e -> {
		            if (e.getID() != KeyEvent.KEY_PRESSED)
		                return false;
	
		            KeyIdentifier id = new KeyIdentifier(e.getKeyCode(), e.getModifiersEx());
		            Runnable r = hotKeyActions.get(id);
	
		            if (r != null) {
		                r.run();
		                return true;
		            }
	
		            return false;
		        });
		}
	}
	
	// ------------- HOT KEY ------------

		
    public static JTextPane TRUCHOOfindTextPane(java.awt.Component component) {
        if (component instanceof JTextPane) {
            return (JTextPane) component; // Encontrado, lo retornamos
        }

        if (component instanceof Container) {
            for (java.awt.Component child : ((Container) component).getComponents()) {
                JTextPane found = TRUCHOOfindTextPane(child); // Llamada recursiva
                if (found != null) {
                    return found;
                }
            }
        }

        return null; // No encontrado
    }
	

    private boolean appsAreDisplayed = true;
    public void showApps(boolean b)
    {
    	appsAreDisplayed = b;
    	applications.showLinks(b);
    }
    
    public void toggleApps()
    {
    	showApps(!appsAreDisplayed);
    }
    
	public void init()
	{
		init(null);
	}
	
	public void allowSwitch(boolean b)
	{
		applications.setOthersEnabled(b);
	}
	
	public void init(Double porc)
	{
		applications.setSelectedTab(0);
		
		currApp = getMyApp(0);
		currApp.start();

		_resizeFrame(porc,jFrame);
		
		applyStyle();

		
		_registerHotKeys();
		jFrame.setVisible(true);
	}
	
	private void _resizeFrame(Double porc,JFrame jFrame)
	{
		Rectangle bounds = properties.getObject("myappcontainer.bounds");
		
		if( bounds==null )
		{
			// maximizado
			if( porc==null )
			{
		        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
			else
			{
				MyAwt.setProportionalSize(porc,jFrame,null);
				MyAwt.center(jFrame,null);			
			}
		}
		else
		{
			jFrame.setBounds(bounds);
		}
	}
		
	public void createApp(String appName,Class<? extends MyAbstractScreen> mainScreenClass)
	{
		createApp(appName,mainScreenClass,null);
	}
	
	public void createApp(String appName,Class<? extends MyAbstractScreen> mainScreenClass,Class<? extends MyAppListener> listenerClass)
	{		
		// creo la aplicacion y la agrego al linkedPane de aplicaciones
		MyApp app = ctx.getBean(MyApp.class,appName);
		app.setMyAppContainer(this);

		if( listenerClass!=null )
		{
			MyAppListener listener= ctx.getBean(listenerClass);
			app.setAppListener(listener);
			listener.appInited(app);
		}
		
		
		
		app.pushScreen(mainScreenClass);

		// ACAAAA............
		
		
		applications.addTab(appName,app.c()).addRelatedObject("app",app);
		applyStyle();
	}
	
	class EscuchaApplications implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			MyLink lnk = (MyLink) e.getSource();
			MyApp selectedApp = (MyApp)lnk.getRelatedObject("app");
						
			if( selectedApp!=currApp )
			{
				prevApp = currApp;
				if( prevApp!=null ) prevApp.stop();
				
				currApp = selectedApp;
				currApp.start();
			}
		}
	}
	
	public void switchToApp(int i)
	{
		applications.setSelectedTab(i);
	}
	
	public void showNextApp()
	{
		int idx = applications.getSelectedIndex();
		int next = ++idx%applications.getTabCount();
		switchToApp(next);
	}
	
	public void setStyle(MyAppContainerStyle style)
	{
		this.style = style;
		applyStyle();
	}
	
	public void applyStyle()
	{
		applications.setStyle(style.appLinkedStyle);
		
		for(int i=0; i<getMyAppCount(); i++)
		{
			MyApp app = getMyApp(i);
			app.setStyle(style.screenLinkedStyle);
		}		
	}
	
	public JFrame c()
	{
		return jFrame;
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

	
	// -----------------------------------------------------------
	// --- cuando cierra la ventana también cierra la conexion ---
	
	
	@Autowired
	private DataSource ds;	
	class EscuchaWindow extends WindowAdapter
	{
		@Transactional
		@Override
		public void windowClosing(WindowEvent e)
		{
			try
			{
				// grabo la posición de la ventana
				properties.putObject("myappcontainer.bounds",jFrame.getBounds());
				
				for(int i=0; i<getMyAppCount(); i++)
				{
					MyApp app = getMyApp(i);
					app.destroy();
				}
				
				PreparedStatement pstm = null;
				
				// cierro la database
				Connection con = ds.getConnection();
				pstm = con.prepareStatement("shutdown");
				pstm.execute();
				
				// destructores
				List<Destroyable> lst = new ArrayList<>(ctx.getBeansOfType(Destroyable.class).values());
				lst.sort(Comparator.comparingInt(Destroyable::getPriority));

				for(Destroyable d:lst)
				{
					d.destroy();
				}
				
				// cierro el contexto
				if( ctx!=null )
				{
					ClassPathXmlApplicationContext x = (ClassPathXmlApplicationContext)ctx;
					if( x.isActive() )
					{
						x.close();
					}
				}
												
				// cierro la ventana
				jFrame.setVisible(false);
				jFrame.dispose();
				

				Thread.sleep(100);
				System.exit(0);
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
