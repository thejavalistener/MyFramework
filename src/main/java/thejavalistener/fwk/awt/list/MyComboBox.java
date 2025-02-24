package thejavalistener.fwk.awt.list;

import java.awt.event.ItemListener;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import thejavalistener.fwk.awt.MyComponent;
import thejavalistener.fwk.awt.MyException;

public interface MyComboBox<T> extends MyComponent
{
	/** Agrega un item al final de la lista. */
	public void addItem(T o);
	public void addItem(T o,boolean selected);

	/** Inserta un item en la i-esima posicion de la lista. */
	public void addItem(T t,int i);	
	
	/** Setea el conjunto de items de la lista, removiendo los anteriores si los hubiere. */
	public void setItems(List<T> items);
	
	/** Seleciona el item en la i-esima posicion */
	public void setSelectedItem(int i);
	
	/** Selecciona el item segun el critero que establece la funcion. */
	public void setSelectedItem(Function<T,Boolean> tEqT);
	
	/** Indica como sera la cadena de caracteres que representara al objeto en el combo. */
	public void setTToString(Function<T,String> f);
	
	/** Podria ser "TODOS", podria ser "   ", es un item especial */
	public void setSpecialItem(String item);	
	public void selectSpecialItem();
	public boolean isSpecialItemSelected();
	
	
	/** Retorna el item actualmente seleccionado.*/
	public T getSelectedItem();

	/** Retorna la posicion del item actualmente seleccionado.*/
	public int getSelectedIndex();
	
	/** Retorna el item ubicado en la i-esima posicion.*/
	public T getItemAt(int i);
	
	/** Remueve el item ubicado en la i-esima posicion. */
	public T removeItemAt(int i);
		
	/** Remueve el item concordante con la funcion tEtT. */
	public boolean removeItem(Function<T,Boolean>tEqT);

	/** Remueve el item actualmente seleccionado. */	
	public T removeSelectedItem();
	
	/** Remueve todos los items el combo dejandolo vacio. */
	public void removeAllItems();
	

	/** Deja en blanco el combo, sin ningun item seleccionado. */
	public void setUnselected();
	public boolean isUnselected();

	
	public void setEnabled(boolean b);
	public void requestFocus();
	public void setItemListener(ItemListener lst);
	public void setItemListener(ItemListener lst,boolean itemListenerWorking);
	public void removeItemListener();
	
	/** Establece si el combo debe generar eventos o no, y retorna el estado anterior. */
	public boolean setItemListenerWorking(boolean b);
	public boolean isItemListenerWorking();
	
	public void validateNotUnselected(String mssgError,String title) throws MyException;
	
	/** Ocasiona que se genere un evento. */
	public void forceItemEvent();
	
	/** Retorna la lista de items */
	public List<T> getItems();
	
	public void sort(BiFunction<T,T,Integer> cmp);
}
