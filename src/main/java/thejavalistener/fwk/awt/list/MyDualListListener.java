package thejavalistener.fwk.awt.list;

public interface MyDualListListener<T>
{
/** Retorna true si acepta que se remueva el item */
	public boolean removeItemRequested(final T item);
	
	/** Retorna el item modificado o null si no acepta la modificacion */
	public T updateItemRequested(final T item);
	
	/** Retorna el item que se acaba de crear o null si no acepta agregar nada nuevo */
	public T createItemRequested();
	
	public void afterItemChangeHook(MyList<T> list);

	public void itemMoved(T item, MyList<T> fromList, MyList<T> toList);
	
//	public void afterRequest(MyList<String> list);
	
}
