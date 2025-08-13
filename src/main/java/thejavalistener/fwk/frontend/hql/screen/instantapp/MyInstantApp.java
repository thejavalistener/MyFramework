package thejavalistener.fwk.frontend.hql.screen.instantapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.awt.panel.MyRightLayout;
import thejavalistener.fwk.awt.tabbedpane.MyTabbedPane;
import thejavalistener.fwk.frontend.MyAbstractScreen;
import thejavalistener.fwk.util.MyCollection;

@Component
@Scope("prototype")
public class MyInstantApp
{
	private JDialog dialog;
	private MyTabbedPane tabbedPane;
	
	private EscuchaButton escuchaButtons;
	private MyRightLayout pButtons;
	
	private Map<String,Object> sharedObjects;
	
	private Map<String,JButton> buttons = new LinkedHashMap<>();
	
	@Autowired
	private ApplicationContext ctx;
	
	private List<MyInstantAppScreen> screens;
	
	private Integer currScreenIdx = null;

	private Container parent;

	private boolean inited = false;
	
	public MyInstantApp()
	{
		dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		screens = new ArrayList<>();
		
		tabbedPane = new MyTabbedPane();
		dialog.add(tabbedPane.c(),BorderLayout.CENTER);
		
		escuchaButtons = new EscuchaButton();
		pButtons = new MyRightLayout(5,5,8,5);
		dialog.add(pButtons,BorderLayout.SOUTH);

		sharedObjects = new HashMap<>();
		
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				close();
			};
		});
	}
	
	public void configureWestPanel(JPanel panel)
	{
		dialog.add(panel,BorderLayout.WEST);
	}
	
	public void configureEastPanel(JPanel panel)
	{
		dialog.add(panel,BorderLayout.EAST);
	}

	public void close()
	{
		MyInstantAppScreen curr = screens.get(currScreenIdx);
		if( curr.stop() )
		{
			curr.setState(MyInstantAppScreen.STOPPED);
			System.out.println(dialog.getSize());
			dialog.setVisible(false);
			dialog.dispose();
		}
	}
	
	public void addButton(String label,String action)
	{
		JButton b = new JButton(label);
		b.setActionCommand(action);
		b.addActionListener(escuchaButtons);
		buttons.put(action,b);
		pButtons.add(b);
	}
	
	class EscuchaButton implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{		
			JButton b = (JButton)e.getSource();
			MyInstantAppScreen curr = screens.get(currScreenIdx);
			curr.onButtonPressed(b.getActionCommand());
		}
	}
	
	public void addScreenPanel(String label,Class<? extends MyInstantAppScreen> panelClazz)
	{
		addScreenPanel(label,panelClazz,false);
	}
	
	public void addScreenPanel(String label,Class<? extends MyInstantAppScreen> panelClazz,boolean selected)
	{	
		try
		{
			MyInstantAppScreen panel;
			if(panelClazz.getAnnotation(Component.class)!=null )
			{
				panel = ctx.getBean(panelClazz);
			}
			else
			{
				panel = panelClazz.getDeclaredConstructor().newInstance();			
			}
			
			panel.setMyInstantApp(this);
			
			screens.add((MyInstantAppScreen)panel);			
			tabbedPane.addTab(label,panel,false);
			
			int currSelected = tabbedPane.getSelectedIndex();
			if( !selected )
			{
				tabbedPane.setSelectedTab(currSelected);
			}
			
			
		}
		catch(ClassCastException e)
		{
			throw new RuntimeException("la clase "+panelClazz.getName()+"debe ser subclase de MyInstantAppScreen");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public JDialog c()
	{
		return dialog;
	}
	
	public void dataUpdated()
	{
		for(MyInstantAppScreen s:screens)
		{
			if( !s.equals(screens.get(currScreenIdx)) )
			{
				s.dataUpdated();
			}
		}
	}
	
	public void setParent(MyAbstractScreen parent) 
	{
		this.parent = parent;
	}
	
	public void init(Object ...args)
	{
		for(MyInstantAppScreen s:screens)
		{
			s.init(args);
			s.setState(MyInstantAppScreen.INITED);
		}
		
		tabbedPane.setChangeListener(new EscuchaTab(),true);
		inited = true;
	}
	
	public MyInstantApp size(int w,int h)
	{
		dialog.setSize(w,h);
		return this;
	}
	
	public void show()
	{
		if( !inited ) throw new RuntimeException("Primero debe invocar al método init()");
		dialog.setLocationRelativeTo(parent);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public void setSelected(Class<? extends MyInstantAppScreen> clazz)
	{
		MyException.throwIf(()->!inited,"Primero debes invocar al método init sobre la instancias de MyInstantApp");		

		int pos = MyCollection.findPos(screens,clazz,(s,c)->s.getClass().equals(c));
		
		tabbedPane.setSelectedTab(pos);
		currScreenIdx = pos;
	}
	
	public JDialog getDialog()
	{
		return dialog;
	}
	
	class EscuchaTab implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			// primera vez
			if( currScreenIdx==null )
			{
				currScreenIdx = tabbedPane.c().getSelectedIndex();
				screens.get(currScreenIdx).start();
			}
			else
			{
				MyInstantAppScreen curr = screens.get(currScreenIdx);
				
				if( curr.getState()==MyInstantAppScreen.STARTED && !curr.stop() )
				{
					tabbedPane.setListenerWorking(false);
					tabbedPane.setSelectedTab(currScreenIdx);
					tabbedPane.setListenerWorking(true);
					curr.setState(MyInstantAppScreen.STOPPED);
				}
				else
				{
					currScreenIdx = tabbedPane.c().getSelectedIndex();
					curr = screens.get(currScreenIdx);
					curr.start();
					curr.setState(MyInstantAppScreen.STARTED);
				}
			}
		}
	}

	public MyInstantApp center(Container c)
	{
		MyAwt.center(dialog,MyAwt.getMainWindow(c));
		return this;
	}
	
	public <T> T getSharedObject(String key)
	{
		return (T)sharedObjects.get(key);
	}

	public void shareObject(String key,Object shared)
	{
		currScreenIdx = currScreenIdx==null?0:currScreenIdx;
		MyInstantAppScreen pantallaActual = screens.get(currScreenIdx);
		
		sharedObjects.put(key,shared);
		for(MyInstantAppScreen scr:screens)
		{
			if( !scr.equals(pantallaActual) )
			{
				scr.handleSharedObject(key,shared,pantallaActual);
			}
		}
	}

	public void setTitle(String title)
	{
		dialog.setTitle(title);
	}
}
