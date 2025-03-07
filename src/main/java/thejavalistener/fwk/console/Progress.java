package thejavalistener.fwk.console;

import java.awt.EventQueue;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;

import thejavalistener.fwk.util.MyCollection;
import thejavalistener.fwk.util.MyThread;

public abstract class Progress
{
	protected MyConsoleBase console;
	private EventQueue eventQueue;
	private SecondaryLoop secondaryLoop;
	
	protected int ant;
	protected int curr;
	protected int top;
	
	protected long initProgressTime;
	protected long finishProgressTime;

	protected abstract void begin();
	
	public abstract void increase();
	public abstract void increase(String mssg);

	protected Progress(MyConsoleBase c)
	{
		this.console = c;
		eventQueue=Toolkit.getDefaultToolkit().getSystemEventQueue();
		secondaryLoop=eventQueue.createSecondaryLoop();
	}
	
	 public MyConsoleBase execute(Runnable r) 
	 {
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        SecondaryLoop loop = eventQueue.createSecondaryLoop();
        
        begin();
        
		MyCollection.invoke(console.getListeners(),t->t.waitingForUserInput(true));

        MyThread.start(() -> {
        	
            r.run();
            loop.exit();
            finish();
    		MyCollection.invoke(console.getListeners(),t->t.waitingForUserInput(false));
        });

        loop.enter();
        return console;
    }
	 
	protected long finish()
	{
		while(curr<top)
		{
			increase();
		}
		
		console.X();

		console.skipFwd();

		finishProgressTime = System.currentTimeMillis()-initProgressTime;
		
		secondaryLoop.exit();
		return finishProgressTime;
	}
	
	public long elapsedTime()
	{
		return finishProgressTime;
	}
	
	

}
