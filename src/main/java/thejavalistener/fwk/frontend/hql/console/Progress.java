package thejavalistener.fwk.frontend.hql.console;

import java.awt.EventQueue;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import thejavalistener.fwk.console.MyConsoleBase;

public class Progress
{
	private MyConsoleBase console;
	private EventQueue eventQueue;
	private SecondaryLoop sl;

	public Progress(MyConsoleBase c)
	{
		this.console = c;
		eventQueue=Toolkit.getDefaultToolkit().getSystemEventQueue();
		sl=eventQueue.createSecondaryLoop();
	}

	public long start(int size,int top,Runnable r)
	{
		Long ts[] = new Long[1];
		
		List<Runnable> tasks = new ArrayList<>();
		tasks.add(()->console.beginProgressBar(size,top));
		tasks.add(r);
		tasks.add(()->{ts[0] = console.finishProgress();});
		tasks.add(()->sl.exit());
		
		new Thread(()->{
			for(Runnable t:tasks)
			{
				t.run();
			}
		}).start();
		
		sl.enter();
		
		return ts[0];
	}
	
	

}
