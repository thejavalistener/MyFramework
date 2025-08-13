package thejavalistener.fwk.frontend.hql.screen.instantapp;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyBorderLayout;

public class MyInstantAppScreen extends MyBorderLayout
{
	static final int INITED = 1;
	static final int STARTED = 2;
	static final int STOPPED = 3;
	
	int currState = 0;
	
	void setState(int newState)
	{
		currState = newState;
	}
	
	int getState()
	{
		return currState;
	}
	
	private MyInstantApp app;
	
	public void init(Object ...args) {};
	public void dataUpdated() {};
	public void start() {};
	public boolean stop() {return true;};

	public void onButtonPressed(String action)
	{
	}
	
	void setMyInstantApp(MyInstantApp app)
	{
		this.app = app;
	}
	
	protected MyInstantApp getMyInstantApp()
	{
		return app;
	}
	
	public void showErrorMessage(String mssg,String title)
	{
		MyAwt.showErrorMessage(mssg,title,app.c());
	}
	public void showInformationMessage(String mssg,String title)
	{
		MyAwt.showInformationMessage(mssg,title,app.c());
	}
	public void showWarningMessage(String mssg,String title)
	{
		MyAwt.showWarningMessage(mssg,title,app.c());
	}
	
	public int showConfirmYES_NO(String mssg,String title)
	{
		return MyAwt.showConfirmYES_NO(mssg,title,app.c());		
	}
	public void handleSharedObject(String key,Object shared, MyInstantAppScreen sender)
	{
	}	
}
