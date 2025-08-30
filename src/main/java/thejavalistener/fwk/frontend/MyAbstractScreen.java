package thejavalistener.fwk.frontend;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.dialog.MyInstantForm;
import thejavalistener.fwk.awt.searchbox.MySearchBox;
import thejavalistener.fwk.awt.searchbox.MySearchBoxController;

@Component
public abstract class MyAbstractScreen extends MyAbstractScreenBase
{
	private static ArrayList<MyAbstractScreen> instancias = new ArrayList<>();

	private Object[] params;
	
	public void init(){};
	public void start(){};
	public void stop(){};
	public void destroy(){};
	
//	private Object returnValue;
//
	protected void onDataUpdated() {}
	protected void createUI() {}

	protected void preInit()
	{
		if(!instancias.contains(this))
			instancias.add(this);
		
		
	}
	
	protected final void dataUpdated()
	{
		for(MyAbstractScreen screen:instancias) screen.onDataUpdated();
	}
	
	public abstract String getName();
	
	public MyAbstractScreen()
	{
	}
	
	public void allowAppSwitch(boolean b)
	{
		getMyApp().allowSwitch(b);
	}
	
//	public Object getReturnValue()
//	{
//		return returnValue;
//	}
//	public void setReturnValue(Object returnValue)
//	{
//		this.returnValue=returnValue;
//	}

	public MyAbstractScreen getOuter()
	{
		return this;
	}
	
	public void setParameters(Object ...params)
	{
		this.params = params;
	}
	
	public Object[] getParameters()
	{
		return params;
	}
	
//	public void exit()
//	{
//		exit(null);
//	}
//	
//	public void exit(Object returnValue)
//	{
//		setReturnValue(returnValue);
//		getMyApp().popScreen();
//	}
	
	public <T> MySearchBox<T> createSearchBox(Class<T> clazz,MySearchBoxController<T> controller)
	{
		// parent
		Window parent = getMyApp().getMyAppContainer().c();
		
		// instancio
		MySearchBox<T> msb = new MySearchBox<>(parent,controller);
		msb.setAlternateRowColor(true);
		MyAwt.setProportionalSize(.6,msb.getDialog(),parent);
		MyAwt.centerH(150,msb.getDialog(),parent);
		
		return msb;
	}	
	
	public MyInstantForm createInstantForm(String title)
	{
		return new MyInstantForm(title,getMyApp().getMyAppContainer().c());
	}
	
	public void setTitle(String title)
	{
		getMyApp().getMyAppContainer().c().setTitle(title);
	}
}
