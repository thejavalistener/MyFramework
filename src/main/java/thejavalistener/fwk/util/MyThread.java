package thejavalistener.fwk.util;

import java.util.Timer;
import java.util.TimerTask;

public class MyThread
{
	public static void sleep(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void randomSleep(int maxMillis)
	{
		int n = (int)(Math.random()*maxMillis);
		sleep(n);
	}

	public static Thread start(Runnable r)
	{
		Thread t = new Thread(r);
		t.start();
		return t;
	}

	public static void startAndJoin(Runnable r)
	{
		try
		{
			start(r).join();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void startDelayed(Runnable r, int millis)
	{
		Timer t = new Timer();
		t.schedule(new MyDelayedTimerTask(r),millis);
	}
	
	static class MyDelayedTimerTask extends TimerTask
	{
		Runnable r;
		MyDelayedTimerTask(Runnable r)
		{
			this.r=r;
		}
		@Override
		public void run()
		{
			this.r.run();
		}
	}
	
}
