package thejavalistener.fwk.util;

public class Timer
{
	private long msBegin;
	private long msEnd;
	
	public void begin()
	{
		msBegin = System.currentTimeMillis();
	}
	
	public void end()
	{
		msEnd = System.currentTimeMillis(); 
	}
	
	public long elapsed()
	{
		return msEnd-msBegin;
	}	
}
