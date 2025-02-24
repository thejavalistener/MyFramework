
package thejavalistener.fwk.util.string;

public class MyStringIterator
{
	public static final int TOLEFT=1;
	public static final int TORIGHT=2;

	private String valueToIterate;
	private int nchars;
	private int direction;
	private int currentIndex;

	public MyStringIterator(String valueToIterate,int nchars,int direction)
	{
		this.valueToIterate=valueToIterate;
		this.nchars=nchars;
		this.direction=direction;
		this.currentIndex=(direction==TORIGHT)?0:valueToIterate.length();
	}

	public boolean hasNext()
	{
		if(direction==TORIGHT)
		{
			return currentIndex<valueToIterate.length();
		}
		else
		{
			return currentIndex>0;
		}
	}

	public String next()
	{
		String subString="";
		if(direction==TORIGHT&&hasNext())
		{
			subString=valueToIterate.substring(currentIndex,Math.min(currentIndex+nchars,valueToIterate.length()));
			currentIndex+=nchars;
		}
		else if(direction==TOLEFT&&hasNext())
		{
			int start=Math.max(currentIndex-nchars,0);
			subString=valueToIterate.substring(start,currentIndex);
			currentIndex-=nchars;
		}
		return subString;
	}
}
