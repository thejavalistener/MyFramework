package thejavalistener.fwk.frontend.messages;

import thejavalistener.fwk.frontend.MyAbstractScreen;

public class MyScreenMessageEvent
{
	private Class<? extends MyAbstractScreen> clazz;
	private String action;
	private Object attachedValue;
	
	public MyScreenMessageEvent(Class<? extends MyAbstractScreen> clazz,String action,Object attachedValue)
	{
		this.clazz = clazz;
		this.action = action;
		this.attachedValue = attachedValue;
	}

	public Class<? extends MyAbstractScreen> getClazz()
	{
		return clazz;
	}

	public void setClazz(Class<? extends MyAbstractScreen> clazz)
	{
		this.clazz=clazz;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action=action;
	}

	public Object getAttachedValue()
	{
		return attachedValue;
	}

	public void setAttachedValue(Object attachedValue)
	{
		this.attachedValue=attachedValue;
	}
}
