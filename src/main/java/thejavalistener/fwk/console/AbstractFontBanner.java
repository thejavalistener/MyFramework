package thejavalistener.fwk.console;

public abstract class AbstractFontBanner
{
	public abstract String[][] getChar(char c);
	
	public int getWidth()
	{
		return getChar('A')[0].length;
	}
	
	public int getHeight()
	{
		return getChar('A').length;
	}
}
