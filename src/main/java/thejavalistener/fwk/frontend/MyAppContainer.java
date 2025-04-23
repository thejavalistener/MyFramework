package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import thejavalistener.fwk.properties.MyProperties;

@Component
public class MyAppContainer
{
	@Autowired
	private MyProperties properties;
	
	
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
	
    public void showApps(boolean b)
    {
    	applications.showLinks(b);
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

		jFrame.setVisible(true);
	}
	
	private void _resizeFrame(Double porc,JFrame jFrame)
	{
		Rectangle bounds = properties.get(MyAppContainer.class,"bounds");
		
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
		// creo la aplicacion y la agrego al linkedPane de aplicaciones
		MyApp app = ctx.getBean(MyApp.class,appName);

		// ESTA LINEA ESTABA.....
		app.setMyAppContainer(this);
		
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
			for(int i=0; i<getMyAppCount(); i++)
			{
				MyApp app = getMyApp(i);
				app.destroy();
			}
						
			PreparedStatement pstm = null;
			
			try
			{
				// grabo la posición de la ventana
				properties.put(MyAppContainer.class,"bounds",jFrame.getBounds());
				
				// cierro la database
				Connection con = ds.getConnection();
				pstm = con.prepareStatement("shutdown");
				pstm.execute();
				
				// cierro la ventana
				jFrame.setVisible(false);
				jFrame.dispose();
				
				// cierro el contexto
				if( ctx!=null )
				{
					ClassPathXmlApplicationContext x = (ClassPathXmlApplicationContext)ctx;
					if( x.isActive() )
					{
						x.close();
					}
				}

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
