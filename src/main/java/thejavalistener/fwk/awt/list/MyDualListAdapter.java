package thejavalistener.fwk.awt.list;

public class MyDualListAdapter<T> implements MyDualListListener<T>
{
	@Override
	public boolean removeItemRequested(T item)
	{
		return true;
	}

	@Override
	public T updateItemRequested(T item)
	{
		return item;
	}

	@Override
	public T createItemRequested()
	{
		return null;
	}

	@Override
	public void afterItemChangeHook(MyList<T> list)
	{
	}

	@Override
	public void itemMoved(T item, MyList<T> fromList, MyList<T> toList)
	{
	}
}
