package thejavalistener.fwk.frontend.hql.screen.instantapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyRightLayout;
import thejavalistener.fwk.awt.tabbedpane.MyTabbedPane;
import thejavalistener.fwk.frontend.MyAbstractScreen;

@Component
@Scope("prototype")
public class MyInstantApp
{
	private JDialog dialog;
	private MyTabbedPane tabbedPane;
//	private JButton bAccept,bCancel;
	
	private EscuchaButton escuchaButtons;
	private MyRightLayout pButtons;
	
	
	private Map<String,JButton> buttons = new LinkedHashMap<>();
	
	@Autowired
	private ApplicationContext ctx;
	
	private List<MyInstantAppScreen> screens;
	
	private Integer currScreen = null;

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
		
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				close();
			};
		});
	}
	
	public void close()
	{
		MyInstantAppScreen curr = screens.get(currScreen);
		if( curr.stop() )
		{
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
			MyInstantAppScreen curr = screens.get(currScreen);
			curr.onButtonPressed(b.getActionCommand());
		}
	}
	
	public void addScreenPanel(String label,Class<? extends MyInstantAppScreen> panelClazz)
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
			if( !s.equals(screens.get(currScreen)) )
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
	
	public void setSelected(int i)
	{
		if( currScreen!=null )
		{
			screens.get(currScreen).stop();
		}
		
		currScreen = i;
		screens.get(i).start();
		tabbedPane.setSelectedTab(i);
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
			if( currScreen!=null )
			{
				boolean okCambio = screens.get(currScreen).stop();
				if( !okCambio )
				{
					tabbedPane.setListenerWorking(false);
					tabbedPane.setSelectedTab(currScreen);
					tabbedPane.setListenerWorking(true);
					return;
				}
			}
			
			currScreen = tabbedPane.c().getSelectedIndex();
			screens.get(currScreen).start();
		}
	}

	public MyInstantApp center(Container c)
	{
		MyAwt.center(dialog,MyAwt.getMainWindow(c));
		return this;
	}
	
}
