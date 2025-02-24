package thejavalistener.fwk.awt.list;

import javax.swing.event.ListSelectionEvent;

public class MyListEvent<T> extends ListSelectionEvent
{
	public static final int ITEM_DOUBLECLICKED=0;
	public static final int ITEM_SELECTED=1;
	public static final int ITEM_UNSELECTED=2;

	private T item;
	private T prevItem;
	private int clickCount;
	private MyList<T> source = null;
	private int listEventType = 0;
	
	public MyListEvent(int listEventType,MyList<T> source,T item,T prevItem,int nClick)
	{
		super(source.c(),0,0,true);
		this.source=source;
		this.listEventType=listEventType;
		this.item = item;
		this.prevItem = prevItem;
		this.clickCount = nClick;
	}
	
	

	public int getClickCount()
	{
		return clickCount;
	}



	public T getPrevItem()
	{
		return prevItem;
	}



	public MyList<T> getSource()
	{
		return source;
	}
	public int getListEventType()
	{
		return listEventType;
	}
	public T getItem()
	{
		return this.item;
	}
	
	@Override
	public String toString()
	{
		String evt[]= {"ITEM_DOUBLECLICKED","ITEM_SELECTED","ITEM_UNSELECTED"};
		return "Event type:"+evt[listEventType]+",item="+item+",prevItem="+prevItem+",clickCount="+clickCount;
	}
}
