package thejavalistener.fwk.awt.list;

public interface MyListListener<T> //extends ListSelectionListener
{
	public void valueChanged(MyListEvent<T> e);
}
