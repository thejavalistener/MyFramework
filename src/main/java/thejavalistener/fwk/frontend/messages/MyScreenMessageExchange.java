package thejavalistener.fwk.frontend.messages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.frontend.MyAbstractScreen;

@Component
public class MyScreenMessageExchange
{
	private Map<Class<? extends MyAbstractScreen>,List<MyScreenMessageListener>> suscribers;
	
	public MyScreenMessageExchange()
	{
		suscribers = new LinkedHashMap<>();
	}
		
	public void registerAsSuscriber(Class<? extends MyAbstractScreen> sender,MyScreenMessageListener listener)
	{
		List<MyScreenMessageListener> lst = suscribers.get(sender);
		if( lst==null )
		{
			suscribers.put(sender,lst = new ArrayList<>());			
		}
		
		lst.add(listener);
	}
	
	public void notifySuscribers(MyAbstractScreen screen,String action)
	{
		notifySuscribers(screen,action,null);
	}
	
	public void notifySuscribers(MyAbstractScreen screen,String action,Object attachedValue)
	{
		for(MyScreenMessageListener a:suscribers.get(screen.getClass()))
		{
			MyScreenMessageEvent ae = new MyScreenMessageEvent(screen.getClass(),action,attachedValue);
			a.onMessageEvent(ae);
		}
	}
}
