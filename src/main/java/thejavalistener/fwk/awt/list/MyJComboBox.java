package thejavalistener.fwk.awt.list;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.JComboBox;

import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.util.string.MyString;

public class MyJComboBox<T> implements MyComboBox<T>
{
	private JComboBox<String> comboBox;
	private Function<T,String> tToString;
	private List<T> data = new ArrayList<>();
	private String specialItem = null;
	private ItemListener itemListener;

	private boolean itemListenerWorking = true;
	
	public MyJComboBox()
	{
		this(null,null,true);
	}

	public MyJComboBox(ItemListener itemListener)
	{
		this(itemListener,true);
	}
	public MyJComboBox(ItemListener itemListener,boolean listenerWorking)
	{
		this(null,itemListener,listenerWorking);
	}
		
	public MyJComboBox(Function<T,String> tToString)
	{
		this(tToString,null,true);
	}

	public MyJComboBox(Function<T,String> tToString,ItemListener itemListener,boolean listenerWorking)
	{
		this.tToString = tToString;
		this.itemListener = itemListener;
		this.comboBox = new JComboBox<String>();
		this.comboBox.addItemListener(new EscuchaItem());
		this.itemListenerWorking = listenerWorking;
	}
	
	@Override
	public void addItem(T t)
	{
		addItem(t,false);
	}
	
	@Override
	public void addItem(T t,boolean selected)
	{
		boolean prev = setItemListenerWorking(false);
		data.add(t);
		String ts = tToString!=null?tToString.apply(t):t.toString();
		comboBox.addItem(ts);
		
		if( selected )
		{
			setSelectedItem(x->tToString.apply(x).equals(ts));
		}
		
		setItemListenerWorking(prev);
	}
	
	@Override
	public void addItem(T t,int idx)
	{
		boolean prev = setItemListenerWorking(false);
		data.add(idx,t);
		String ts = tToString!=null?tToString.apply(t):t.toString();
		comboBox.insertItemAt(ts,idx);
		setItemListenerWorking(prev);
	}
	
	@Override
	public void setSelectedItem(int i)
	{
		comboBox.setSelectedIndex(i);
	}

	
	@Override
	public void setSelectedItem(Function<T,Boolean> tEqT)
	{
		boolean prev = setItemListenerWorking(false);
		int i=0;
		boolean fin = false;
		while( i<data.size() && !fin )
		{
			T d = data.get(i);
			if( d!=null )
			{
				if( tEqT.apply(d) )
				{
					fin = true;
				}
				else
				{
					i++;
				}
			}
			else
			{
				i++;
			}
		}
				
		if( i<data.size() )
		{
			setSelectedItem(i);
		}
		
		setItemListenerWorking(prev);
	}
	
	@Override
	public void setTToString(Function<T,String> f)
	{
		this.tToString = f;
	}
	
	@Override
	public void setSpecialItem(String spi)
	{		
		// si aun no fue seteado, lo agrego...
		if( specialItem==null && spi!=null )
		{
			boolean prev = setItemListenerWorking(false);
			comboBox.insertItemAt(spi,0);
			data.add(0,null);		
			this.specialItem = spi;
			setItemListenerWorking(prev);
			return;
		}

		// si ya habia sido seteado, lo reemplazo...
		if( specialItem!=null && spi!=null )
		{
			if( comboBox.getItemCount()>0)
			{
				boolean prev = setItemListenerWorking(false);
				comboBox.removeItemAt(0);
				comboBox.insertItemAt(spi,0);
				this.specialItem = spi;
				setItemListenerWorking(prev);
				return;
			}
		}

		// si spi es null, lo anula...		
		if( spi==null )
		{
			if( specialItem!=null && data.size()>0 )
			{
				boolean prev = setItemListenerWorking(false);
				data.remove(0);
				comboBox.removeItemAt(0);
				specialItem=null;
				setItemListenerWorking(prev);
			}			
		}
	}
	
	@Override
	public void selectSpecialItem()
	{
		if( specialItem!=null )
		{
			boolean prev = setItemListenerWorking(false);
			comboBox.setSelectedIndex(0);
			setItemListenerWorking(prev);
		}
		else
		{
			throw new RuntimeException("No se ha especificado el specialItem");
		}
	}
	
	@Override
	public boolean isSpecialItemSelected()
	{
		if( specialItem!=null )
		{
			return getSelectedIndex()==0;
		}
		else
		{
			throw new RuntimeException("No se ha especificado el specialItem");
		}
	}
		
	
	/** Deja en blanco el combo, sin ningun item seleccionado */
	@Override
	public void setUnselected()
	{
		boolean prev = setItemListenerWorking(false);
		comboBox.setSelectedIndex(-1);
		setItemListenerWorking(prev);
	}
	
	@Override
	public T getSelectedItem()
	{
		int idx = getSelectedIndex();
		return idx>=0?data.get(idx):null;
	}
	
	@Override
	public int getSelectedIndex()
	{
		return comboBox.getSelectedIndex();
	}
	
	@Override
	public T getItemAt(int i)
	{
		return data.get(i);
	}
	
	@Override
	public T removeItemAt(int i)
	{
		i = itemListener==null?i:i+1;
		return _removeItemAt(i);
	}
	
	private T _removeItemAt(int i)
	{
		boolean prev = setItemListenerWorking(false);		
		T t = data.remove(i);
		comboBox.removeItemAt(i);
		setItemListenerWorking(prev);
		return t;		
	}
	
	
	
	@Override
	public boolean removeItem(Function<T,Boolean>tEqT)
	{
		int i=specialItem==null?0:1; 
		while(i<data.size() && !tEqT.apply(getItemAt(i)))
		{
			i++;
		}
		
		if( i<data.size() )
		{
			_removeItemAt(i);
			return true;
		}
		else
		{
			return false;
		}
	}
	
//	@Override
//	public void setSelectedItem(Function<T,Boolean> tEqT)
//	{
//		boolean prev = setItemListenerWorking(false);
//		int i=0;
//		boolean fin = false;
//		while( i<data.size() && !fin )
//		{
//			T d = data.get(i);
//			if( d!=null && tEqT.apply(d) )
//			{
//				setSelectedItem(i);
//				fin = true;
//			}
//			else
//			{
//				i++;
//			}
//		}
//		
//		setItemListenerWorking(prev);
//	}
	
	@Override
	public void forceItemEvent()
	{
		T t  = getSelectedItem();
		ItemEvent e = new ItemEvent(c(),1,t,ItemEvent.ITEM_STATE_CHANGED);
		itemListener.itemStateChanged(e);
	}
	
	@Override
	public List<T> getItems()
	{
		return data;
	}
	
	@Override
	public T removeSelectedItem()
	{
		boolean prev = setItemListenerWorking(false);
		T t =  removeItemAt(getSelectedIndex());
		setItemListenerWorking(prev);
		return t;
	}
	
	@Override
	public void removeAllItems()
	{
		boolean prev = setItemListenerWorking(false);

		int desde = specialItem==null?0:1;
		while(data.size()>desde)
		{
			comboBox.removeItemAt(desde);
			data.remove(desde);
		}

		setItemListenerWorking(prev);			
		
//		boolean prev = setItemListenerWorking(false);
//		comboBox.removeAllItems();
//		data.clear();
//		setItemListenerWorking(prev);
	}
	
	@Override
	public void setItems(List<T> items)
	{
		boolean prev = setItemListenerWorking(false);

		removeAllItems();
		
		if( specialItem!=null )
		{
			setSpecialItem(specialItem);
		}

		for(T t:items)
		{
			addItem(t);
		}
		
		setItemListenerWorking(prev);
	}
	
	
	@Override
	public boolean isUnselected()
	{
		return getSelectedIndex()<0;
	}

	@Override
	public void validateNotUnselected(String mssg,String title) throws MyException
	{
		if( isUnselected() )
		{
			requestFocus();
			throw new MyException(mssg,title,MyException.ERROR);
		}
	}

	@Override
	public void setItemListener(ItemListener lst)
	{
		setItemListener(lst,true);
	}

	@Override
	public void setItemListener(ItemListener lst,boolean itemListenerIsWorking)
	{
		this.itemListener = lst;
		this.itemListenerWorking = itemListenerIsWorking;		
	}

	@Override
	public void removeItemListener()
	{
		this.itemListener=null;
	}	
		
	@Override
	public boolean setItemListenerWorking(boolean working)
	{
		boolean prev = itemListenerWorking;
		itemListenerWorking = working;
		return prev;
	}
	
	@Override
	public boolean isItemListenerWorking()
	{
		return itemListenerWorking;
	}
	
	@Override
	public JComboBox<String> c()
	{
		return comboBox;
	}
	
	/** Si isSpecialItemSelected o IsUnselected retorna null, de otro modo retorna el item */
	@Override
	public Object getValue()
	{
		if( isSpecialItemSelected() || isUnselected() )
		{
			return null;
		}
		else
		{
			return getSelectedItem();			
		}
		
	}
	
	class EscuchaItem implements ItemListener
	{
		int veces = 0;
		
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			if( itemListener!=null && itemListenerWorking )
			{
				veces++;
				if( veces == 1)
				{
					itemListener.itemStateChanged(e);
				}
				else
				{
					if( veces>1)
					{
						veces = 0;
					}
				}
			}
		}
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		comboBox.setEnabled(b);
	}
	
	@Override
	public void resetValue()
	{
		setUnselected();
	}

	@Override
	public void requestFocus()
	{
		comboBox.requestFocus();
	}

	
	static class Numero
	{
		public int valor;
		public String nombre;
		public String romano;
		
		public Numero(int v,String n,String r) {valor = v; nombre = n; romano = r;}
		public Numero()
		{
			valor = (int)(Math.random()*100);
			nombre = MyString.generateRandom('A','Z',2,2);
			romano = MyString.generateRandom('a','z',2,2);
		}
	}
	
	static class EscuchaNum implements ItemListener
	{
		int i=0;
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			System.out.println("EscuchaCNum: "+i++);
		}	
	}
	
	@Override
	public void sort(BiFunction<T,T,Integer> cmp)
	{
		int desde = specialItem!=null?1:0;
		
		ArrayList<T> data2 = new ArrayList<>(data.subList(desde,data.size()));
		for(int i=0;i<data2.size();i++)
		{
			for(int j=0;j<data2.size()-1;j++)
			{
				if( cmp.apply(data2.get(j),data2.get(j+1))>0 )
				{
					T aux = data2.get(j);
					data2.set(j,data2.get(j+1));
					data2.set(j+1,aux);
				}
			}
		}
		
		setItems(data2);
		if( specialItem!=null )
		{
			setSpecialItem(specialItem);
		}
	}
}
