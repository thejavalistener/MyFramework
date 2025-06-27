package thejavalistener.fwk.awt.list;

public interface MyDualListListener<R>
{
/** Retorna true si acepta que se remueva el item */
	public boolean removeItemRequested(final R item);
	
	/** Retorna el item modificado o null si no acepta la modificacion */
	public R updateItemRequested(final R item);
	
	/** Retorna el item que se acaba de crear o null si no acepta agregar nada nuevo */
	public R createItemRequested();
	
	public void afterItemChangeHook(MyList<R> list);
	
//	public void afterRequest(MyList<String> list);
	
}
