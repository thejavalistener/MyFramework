package thejavalistener.fwk.awt.list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.panel.MyPanel;

public class MyDualListSelector<T>
{
	public static final int NO_ALLOWS=0;
	public static final int ALLOW_ADD=1;
	public static final int ALLOW_UDT=2;
	public static final int ALLOW_RMV=4;

	private int allows=0;

	private MyPanel contentPane;
	private MyList<T> lstLeft;
	private MyList<T> lstRight;
	private MyPanel panelIzquierdo;
	private MyDualListListener<T> listener;

	private JButton btnAdd = null;
	private JButton btnUdt = null;
	private JButton btnRmv = null;
	
	public MyDualListSelector(int allows)
	{
		this.allows=allows;
		contentPane=new MyPanel(0,0,0,0);
		contentPane.setLayout(new GridLayout(1,2,5,0));

		lstLeft=new MyList<T>();
		
		EscuchaItems escuchaItems = new EscuchaItems();
		lstLeft.setListSelectionListener(escuchaItems);
		lstRight=new MyList<>();

		// Panel izquierdo con lista y botones
		panelIzquierdo=new MyBorderLayout();
		panelIzquierdo.add(new MyScrollPane(lstLeft.c()),BorderLayout.CENTER);
		panelIzquierdo.add(crearPanelBotones(),BorderLayout.SOUTH);

		// Panel derecho sólo con lista
		MyPanel panelDerecho=new MyBorderLayout();
		panelDerecho.add(new MyScrollPane(lstRight.c()),BorderLayout.CENTER);

		lstLeft.setMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2)
				{
					T item=lstLeft.getSelectedItem();
					if(item!=null)
					{
						T t=lstLeft.removeSelectedItem();
						lstRight.addItem(t);
						lstRight.setSelectedItem(x->x.equals(t));
						lstRight.ensureSelectedIsVisible();
						_enableDisableButtons(false);
					}
				}
			}
		});

		lstRight.setMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2)
				{
					T item=lstRight.getSelectedItem();
					if(item!=null)
					{
						lstLeft.addItem(lstRight.removeSelectedItem());
						lstLeft.sort((a,b)->a.toString().compareTo(b.toString()));
						lstLeft.setSelectedItem(x->x.equals(item));
						lstLeft.ensureSelectedIsVisible();
						_enableDisableButtons(true);
					}
				}
			}
		});

		contentPane.add(panelIzquierdo);
		contentPane.add(panelDerecho);
	}
	
	public T getLeftSelectedItem()
	{
		return lstLeft.getSelectedItem();
	}
	
	public T getRightSelectedItem()
	{
		return lstRight.getSelectedItem();
	}
	
	public void _setEnabledButton(JButton btn,boolean b)
	{
		if( btn!=null ) 
		{
			btn.setEnabled(b);
		}
	}
	
	private Map<JComponent,Boolean> estadoAnterior = new HashMap<>();
	public void setEnabled(boolean b)
	{
		if( btnAdd!=null )
		{
			estadoAnterior.put(btnAdd,btnAdd.isEnabled());
			btnAdd.setEnabled(b);
		}
		if( btnRmv!=null )
		{
			estadoAnterior.put(btnRmv,btnRmv.isEnabled());
			btnRmv.setEnabled(b);
		}
		if( btnUdt!=null )
		{
			estadoAnterior.put(btnUdt,btnUdt.isEnabled());
			btnUdt.setEnabled(b);
		}
		
		estadoAnterior.put(lstLeft.c(),lstLeft.c().isEnabled());
		lstLeft.setEnabled(b);

		estadoAnterior.put(lstRight.c(),lstRight.c().isEnabled());
		lstRight.setEnabled(b);
	}
	
	public List<T> getLeftItems()
	{
		return lstLeft.getItems();
	}
	
	public List<T> getRightItems()
	{
		return lstRight.getItems();
	}
	
	public void restorePreviousEnabledState()
	{
		estadoAnterior.forEach((c,b)->c.setEnabled(b));
		estadoAnterior.clear();
	}

	public void setItems(List<T> all)
	{
		setItems(all,new ArrayList<>());
	}

	public void setItems(List<T> all, List<T> selected)
	{
		lstRight.removeAllItems();
		lstLeft.removeAllItems();
		
		for(T t:all)
		{
			if(selected.contains(t))
			{
				lstRight.addItem(t);
			}
			else
			{
				lstLeft.addItem(t);
			}
		}
	}

	private MyPanel crearPanelBotones()
	{
		btnAdd = null;
		btnUdt = null;
		btnRmv = null;
		
		if( (allows&ALLOW_ADD)!=0 )
		{
			btnAdd = new JButton("+");
			btnAdd.addActionListener(e -> 
			{
				if(listener!=null)
				{
					T nnew = listener.createItemRequested();
					if( nnew!=null )
					{
						lstLeft.addItem(nnew);
						
						Function<T,Boolean> f = t->t.equals(nnew);
						lstLeft.setSelectedItem(f);
						
						listener.afterItemChangeHook(lstLeft);
						lstLeft.ensureSelectedIsVisible();
						
					}
				}
			});
		}
		
		if( (allows&ALLOW_UDT)!=0 )
		{
			btnUdt.setEnabled(false);
			btnUdt = new JButton("#");
			btnUdt.setEnabled(false);
			btnUdt.addActionListener(e -> 
			{
				if(listener!=null )
				{
					T curr = lstLeft.getSelectedItem();
					T modif = listener.updateItemRequested(curr);
					if( modif!=null )
					{
						lstLeft.removeSelectedItem();
						lstLeft.addItem(modif);
						
						Function<T,Boolean> f = t->t.equals(modif);
						lstLeft.setSelectedItem(f);
						
						listener.afterItemChangeHook(lstLeft);
						lstLeft.ensureSelectedIsVisible();
						
					}
				}
			});
		}
		
		if( (allows&ALLOW_RMV)!=0 )
		{
			btnRmv = new JButton("-");
			btnRmv.setEnabled(false);
			btnRmv.addActionListener(e -> 
			{
				if(listener!=null ) 
				{
					T curr = lstLeft.getSelectedItem();
	
					if( listener.removeItemRequested(curr) )
					{
						lstLeft.removeSelectedItem();
						listener.afterItemChangeHook(lstLeft);
					}
				}
			});
		
		}
		
		if( allows==0 )
		{
			return new MyPanel(0,0,0,0);
		}
		else
		{
			MyPanel panel=new MyCenterLayout(0,0,0,0);
	
			if( btnAdd!=null )
				panel.add(btnAdd,BorderLayout.WEST);

			if( btnUdt!=null )
				panel.add(btnUdt);
			
			if( btnRmv!=null )
				panel.add(btnRmv);

			return panel;
		}
	}

	public void setDualListListener(MyDualListListener<T> l)
	{
		this.listener=l;
	}

	public List<T> getItemsSeleccionados()
	{
		return lstRight.getItems();
	}

	public List<T> getItemsNoSeleccionados()
	{
		return lstLeft.getItems();
	}

	public Component c()
	{
		return contentPane;
	}
	
	private void _enableDisableButtons(boolean b)
	{
		if( btnUdt!=null )
			btnUdt.setEnabled(b);
		if( btnRmv!=null )
			btnRmv.setEnabled(b);
	}
	
	class EscuchaItems implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{			
			int idx = lstLeft.getSelectedIndex();
			_enableDisableButtons(idx>=0);
		}
	}
}
