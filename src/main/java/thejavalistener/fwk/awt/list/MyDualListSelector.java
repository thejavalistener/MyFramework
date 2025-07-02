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
	public static final int ALLOW_SALL=8;
	public static final int ALLOW_RALL=16;

	private int allows=0;

	private MyPanel contentPane;
	private MyList<T> lstLeft;
	private MyList<T> lstRight;
	private MyPanel panelIzquierdo;
	private MyDualListListener<T> listener;

	private JButton btnAdd = null;
	private JButton btnUdt = null;
	private JButton btnRmv = null;
	private JButton btnSAll = null;
	private JButton btnRAll = null;
	
	public MyDualListSelector(int allows)
	{
		this.allows=allows;
		contentPane=new MyPanel(0,0,0,0);
		contentPane.setLayout(new GridLayout(1,2,5,0));

		lstLeft=new MyList<T>();
		lstRight=new MyList<>();

		// Panel izquierdo con lista y botones
		panelIzquierdo=new MyBorderLayout();
		panelIzquierdo.add(new MyScrollPane(lstLeft.c()),BorderLayout.CENTER);
		
		if( allows!=0 )
		{
			panelIzquierdo.add(crearPanelBotones(),BorderLayout.SOUTH);
		}
		
		// Panel derecho sólo con lista
		MyPanel panelDerecho=new MyBorderLayout();
		panelDerecho.add(new MyScrollPane(lstRight.c()),BorderLayout.CENTER);
		lstLeft.setListListener(new EscuchaLeft());
		lstRight.setListListener(new EscuchaRight());

		contentPane.add(panelIzquierdo);
		contentPane.add(panelDerecho);
	}
	
	private void _enableDisableAllButtons()
	{
		if(btnSAll!=null)
			btnSAll.setEnabled(lstLeft.size()>0);
		if(btnRAll!=null)
			btnRAll.setEnabled(lstRight.size()>0);
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
		
		_enableDisableAllButtons();
	}

	private MyPanel crearPanelBotones()
	{
		btnAdd = null;
		btnUdt = null;
		btnRmv = null;
		btnSAll = null;
		btnRAll = null;
		
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
			btnUdt = new JButton("#");
			btnUdt.setEnabled(false);
			btnUdt.addActionListener(e -> 
			{
				if(listener!=null )
				{
					T curr = lstLeft.getSelectedItem();
					if( curr!=null )
					{
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
	
					if( curr!=null && listener.removeItemRequested(curr) )
					{
						lstLeft.removeSelectedItem();
						listener.afterItemChangeHook(lstLeft);
					}
				}
			});			
		}
		
		if( (allows&ALLOW_SALL)!=0 )
		{
			btnSAll = new JButton(">>");
			btnSAll.setEnabled(false);
			btnSAll.addActionListener(e -> 
			{
				List<T> items = lstLeft.removeAllItems();
				lstRight.addItems(items);
				if( listener!=null ) 
					for(T i:items)
						listener.itemMoved(i,lstLeft,lstRight);

			});
		}

		if( (allows&ALLOW_RALL)!=0 )
		{
			btnRAll = new JButton("<<");
			btnRAll.setEnabled(false);
			btnRAll.addActionListener(e -> 
			{
				List<T> items = lstRight.removeAllItems();				
				lstLeft.addItems(items);
				if( listener!=null ) 
					for(T i:items)
						listener.itemMoved(i,lstRight,lstLeft);

			});
		}

		
		if( allows==0 )
		{
			return new MyPanel(0,0,0,0);
		}
		else
		{
			MyPanel panel=new MyCenterLayout(3,0,3,0);
	
			if( btnAdd!=null )
				panel.add(btnAdd);

			if( btnUdt!=null )
				panel.add(btnUdt);
			
			if( btnRmv!=null )
				panel.add(btnRmv);
		
			if( btnRAll!=null )
				panel.add(btnRAll);
		
			if( btnSAll!=null )
				panel.add(btnSAll);
		
			return panel;
		}
	}

	public void setDualListListener(MyDualListListener<T> l)
	{
		this.listener=l;
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

	public void removeAll()
	{
		lstLeft.removeAllItems();
		lstRight.removeAllItems();
		if( btnSAll!=null ) btnSAll.setEnabled(false);
		if( btnRAll!=null ) btnRAll.setEnabled(false);
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
	
	class EscuchaLeft implements MyListListener<T>
	{
		@Override
		public void valueChanged(MyListEvent<T> e)
		{
			if(e.getClickCount()==2)
			{
				T item=lstLeft.getSelectedItem();
				if(item!=null)
				{
					T t=lstLeft.removeSelectedItem();						
					lstRight.addItem(t);
					if( listener!=null ) listener.itemMoved(item,lstLeft,lstLeft);
					lstRight.setSelectedItem(x->x.equals(t));
					lstRight.ensureSelectedIsVisible();
					_enableDisableButtons(false);						
					_enableDisableAllButtons();
				}
			}	
		}		
	}
	
	class EscuchaRight implements MyListListener<T>
	{
		@Override
		public void valueChanged(MyListEvent<T> e)
		{
			if(e.getClickCount()==2)
			{
				T item=lstRight.getSelectedItem();
				if(item!=null)
				{
					lstLeft.addItem(lstRight.removeSelectedItem());
					if( listener!=null ) listener.itemMoved(item,lstRight,lstLeft);
					lstLeft.sort((a,b)->a.toString().compareTo(b.toString()));
					lstLeft.setSelectedItem(x->x.equals(item));
					lstLeft.ensureSelectedIsVisible();
					_enableDisableButtons(true);
					_enableDisableAllButtons();
				}
			}			
		}
		
	}


}
