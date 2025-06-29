package thejavalistener.fwk.awt.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.frontend.MyValidation;
import thejavalistener.fwk.util.string.MyString;

public class MyList<T> 
{
	private ArrayList<T> data;
	private Function<T,String> tToString=null;

	private JList<String> jList;
	private DefaultListModel<String> model;

	private MyListListener<T> listener=null;
	private boolean listenerIsWorking=true;
	private EscuchaList escuchaList;

	private T prevItemSelected=null;

	private MyList<T> outer=null;

	public MyList()
	{
		data=new ArrayList<>();
		model=new DefaultListModel<>();

		jList=new JList<>();
		jList.setModel(model);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		escuchaList=new EscuchaList();
		// jList.addListSelectionListener(escuchaList);
		jList.addMouseListener(escuchaList);

		this.outer=this;
	}

	public void setVisibleRowCount(int n)
	{
		jList.setVisibleRowCount(n);
	}

	public void sort(BiFunction<T,T,Integer> cmp)
	{
		T currSelected=getSelectedItem();

		ArrayList<T> data2=new ArrayList<>(data);
		for(int i=0; i<data2.size(); i++)
		{
			for(int j=0; j<data2.size()-1; j++)
			{
				if(cmp.apply(data2.get(j),data2.get(j+1))>0)
				{
					T aux=data2.get(j);
					data2.set(j,data2.get(j+1));
					data2.set(j+1,aux);
				}
			}
		}

		setItems(data2);

		if(currSelected!=null)
		{
			int i=0;
			while(i<data2.size()&&cmp.apply(currSelected,data2.get(i))!=0)
			{
				i++;
			}
			setSelectedItem(i);
		}
	}

	public void addItem(T t)
	{
		addItem(t,false);
	}

	public void addItem(T t, boolean selected)
	{
		boolean prev=isItemListenerWorking();
		data.add(t);
		String item=tToString!=null?tToString.apply(t):t.toString();

		int pos=model.size();
		model.addElement(item);

		if(selected)
		{
			setSelectedItem(pos);
		}

		setItemListenerWorking(prev);
	}

	public T getSelectedItem()
	{
		int idx=getSelectedIndex();
		return idx>=0?data.get(idx):null;
	}

	public int getSelectedIndex()
	{
		return jList.getSelectedIndex();
	}

	public T getItemAt(int i)
	{
		return data.get(i);
	}

	public T removeItemAt(int i)
	{
		boolean prev=isItemListenerWorking();
		T t=data.remove(i);
		model.remove(i);
		setItemListenerWorking(prev);
		return t;
	}

	public T removeSelectedItem()
	{
		boolean prev=isItemListenerWorking();
		int currIdx=getSelectedIndex();
		T t=null;
		if(currIdx>=0)
		{
			t=removeItemAt(getSelectedIndex());
		}
		setItemListenerWorking(prev);
		return t;
	}

	public List<T> removeAllItems()
	{
		List<T> ret = new ArrayList<>();
		boolean prev=isItemListenerWorking();
		while(data.size()>0)
		{
			ret.add(data.remove(0));
			model.remove(0);
		}
		setItemListenerWorking(prev);
		
		return ret;
	}

	public void setItems(List<T> items)
	{
		boolean prev=isItemListenerWorking();
		removeAllItems();
		for(T t:items)
		{
			addItem(t);
		}
		setItemListenerWorking(prev);
	}

	public void setTToString(Function<T,String> f)
	{
		this.tToString=f;
	}

	public void setUnselected()
	{
		boolean prev=isItemListenerWorking();
		jList.clearSelection();
		setItemListenerWorking(prev);
	}

	public void addItem(T t, int i)
	{
		boolean prev=isItemListenerWorking();
		data.add(i,t);
		String s=tToString!=null?tToString.apply(t):t.toString();
		model.add(i,s);
		setItemListenerWorking(prev);
	}

	public void setSelectedItem(int i)
	{
		boolean prev=isItemListenerWorking();
		jList.setSelectedIndex(i);
		setItemListenerWorking(prev);
	}

	public void ensureSelectedIsVisible()
	{
		int i=getSelectedIndex();
		if(i>=0)
		{
			jList.ensureIndexIsVisible(i);
		}
	}

	public void setSelectedItem(Function<T,Boolean> tEqT)
	{
		boolean prev=isItemListenerWorking();
		int i=0;
		while(i<data.size()&&!tEqT.apply(data.get(i)))
		{
			i++;
		}

		if(i<data.size())
		{
			setSelectedItem(i);
		}
		else
		{
			setUnselected();
		}
		setItemListenerWorking(prev);
	}

	public boolean removeItem(Function<T,Boolean> tEqT)
	{
		boolean prev=isItemListenerWorking();
		boolean ret=false;
		int i=0;
		while(i<data.size()&&!tEqT.apply(data.get(i)))
		{
			i++;
		}

		if(i<data.size())
		{
			model.remove(i);
			data.remove(i);
			ret=true;
		}

		setItemListenerWorking(prev);
		return ret;
	}

	public boolean isUnselected()
	{
		return jList.getSelectedIndex()<0;
	}

	public void setEnabled(boolean b)
	{
		jList.setEnabled(b);
	}

	public void requestFocus()
	{
		jList.requestFocus();
	}

	public void setListListener(MyListListener<T> lst)
	{
		setListListener(lst,true);
	}
	public void setListListener(MyListListener<T> lst,boolean listenerWorking)
	{
		this.listener=lst;
		this.listenerIsWorking=listenerWorking;
	}
	
//	public void setListSelectionListener(ListSelectionListener lst)
//	{
//		setListSelectionListener(lst,true);
//	}
//
//	public void setListSelectionListener(ListSelectionListener lst, boolean itemListenerWorking)
//	{
//		this.listener=lst;
//		this.listenerIsWorking=itemListenerWorking;
//	}

	public void removeListSelectionListener()
	{
		this.listener=null;
	}

	public boolean setItemListenerWorking(boolean b)
	{
		boolean prev=this.listenerIsWorking;
		this.listenerIsWorking=b;
		return prev;
	}

	public boolean isItemListenerWorking()
	{
		return this.listenerIsWorking;
	}

	public void validateNotUnselected(String mssgError, String title) throws MyException
	{
		if(isUnselected())
		{
			throw new MyException(mssgError,title,JOptionPane.ERROR_MESSAGE);
		}
	}

//	public void forceListSelectionEvent()
//	{
//		if(listener!=null&&listenerIsWorking)
//		{
//			MyListEvent<T> xx = new MyListEvent(0,jList,getSelectedItem(),getSelectedItem(),1);
//
//			ListSelectionEvent e=new ListSelectionEvent(jList,getSelectedIndex(),getSelectedIndex(),false);
//			listener.valueChanged(e);
//		}
//	}

	public List<T> getItems()
	{
		return data;
	}

	public JComponent c()
	{
		return jList;
	}

	public Object getValue()
	{
		return data.get(getSelectedIndex());
	}

	public void resetValue()
	{
		setUnselected();
	}

	public int size()
	{
		return model.size();
	}
	
	public void addItems(List<T> items)
	{
		boolean prev = setItemListenerWorking(false);
		for(T t:items)
		{
			addItem(t);
		}
		setItemListenerWorking(prev);
	}

	class EscuchaList extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(listenerIsWorking&&listener!=null)
			{
				MyListEvent<T> x=null;
				T item=getSelectedItem();
				int nClick=e.getClickCount();
				int eventType;
				switch(nClick)
				{
					case 1:
						if(prevItemSelected==item) return;
						eventType=isUnselected()?MyListEvent.ITEM_UNSELECTED:MyListEvent.ITEM_SELECTED;
						x=new MyListEvent<>(eventType,outer,item,prevItemSelected,1);
						break;
					case 2:
						eventType=MyListEvent.ITEM_DOUBLECLICKED;
						x=new MyListEvent<>(eventType,outer,item,prevItemSelected,2);
						break;
				}
				
				if(x!=null)
				{
					listener.valueChanged(x);
					prevItemSelected=item;
				}
			}
		}

	}

	static class EscuchaAdd implements ActionListener
	{
		JTextField tf;
		MyList<String> lst;

		public EscuchaAdd(MyList<String> l,JTextField t)
		{
			lst=l;
			tf=t;
		}

		public void actionPerformed(ActionEvent e)
		{
			if(!tf.getText().isEmpty())
			{
				JList xx=(JList)lst.c();
			}
			else
			{
				lst.addItem(MyString.generateRandom('A','Z',5,50));
			}
		}
	}

	public boolean getIsEmpty()
	{
		return getItems().size()==0;
	}
}
