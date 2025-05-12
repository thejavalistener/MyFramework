package thejavalistener.fwk.awt.list;

import java.awt.event.MouseListener;
import java.util.function.BiFunction;

import javax.swing.event.ListSelectionListener;

public interface MyList<T> extends MyComboBox<T>
{
	public void setListSelectionListener(ListSelectionListener lst);
	public void setListSelectionListener(ListSelectionListener lst, boolean itemListenerWorking);
	public void setMouseListener(MouseListener lst);
	public void forceListSelectionEvent();
	public void removeListSelectionListener();
	public void sort(BiFunction<T,T,Integer> cmp);
	public void ensureSelectedIsVisible();
	public void setVisibleRowCount(int n);
}
	
	
	
//	private ArrayList<T> data;
//	private DefaultListModel<String> model;
//	private Function<T,String> tToString;
//	
//	public MyJList()
//	{
//		data = new ArrayList<>();
//		model = new DefaultListModel<>();
//		setModel(model);
//	}
//	
//	@Override
//	public void addMyItem(T t)
//	{
//		data.add(t);
//		model.addElement(tToString!=null?tToString.apply(t):t.toString());
//	}
//
//	@Override
//	public T getMySelectedItem()
//	{
//		return data.get(getSelectedIndex());
//	}
//
//	@Override
//	public int getMySelectedIndex()
//	{
//		return getSelectedIndex();
//	}
//
//	@Override
//	public T getMyItemAt(int i)
//	{
//		return data.get(getMySelectedIndex());
//	}
//
//	@Override
//	public T removeMyItemAt(int i)
//	{
//		T t = data.get(i);
//		model.remove(i);
//		return t;
//	}
//
//	@Override
//	public T removeMySelectedItem()
//	{
//		return removeMyItemAt(getMySelectedIndex());
//	}
//
//	@Override
//	public void removeAllMyItems()
//	{
//		while(data.size()>0)
//		{
//			data.remove(0);
//			model.remove(0);
//		}
//	}
//
//	@Override
//	public void addMyItems(List<T> items)
//	{
//		removeAllMyItems();
//		for(T t:items)
//		{
//			addMyItem(t);
//		}
//	}
//	
//	public void selectMyObject(T t,BiFunction<T,T,Boolean> tEqT)
//	{
//		int i=0;
//		while( i<data.size() && tEqT.apply(t,data.get(i))) i++;
//		if( i<data.size() )
//		{
//			setSelectedIndex(i);
//		}
//	}
//
//	@Override
//	public void setTToString(Function<T,String> f)
//	{
//		this.tToString = f;
//	}
//
//	@Override
//	public void unselectedMyItem()
//	{
//		clearSelection();
//	}
//	public void addAllMyItems(Map<String,T> items)
//	{
//		removeAllMyItems();
//		items.forEach((k,v)->addMyItem(v));
//	}
//

