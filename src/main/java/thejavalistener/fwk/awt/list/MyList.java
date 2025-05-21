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