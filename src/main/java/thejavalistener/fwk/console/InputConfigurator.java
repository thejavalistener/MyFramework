package thejavalistener.fwk.console;

import java.awt.event.KeyEvent;
import java.util.function.Function;
import java.util.regex.Pattern;

import thejavalistener.fwk.util.TriFunction;

public class InputConfigurator
{
	private MyConsoleBase console;

	// por defecto todo valida y todo matchea
	private TriFunction<Character,Integer,String,Character> mask = (c,i,s)->c;
	private Function<String,Boolean> valid = s->true;
	private String regex = ".*";
	private Pattern pattern = Pattern.compile(regex);
	private boolean allowMouseEvents = true;
	
	public InputConfigurator(MyConsoleBase c)
	{
		this.console = c;
	}
	
	public InputConfigurator mask(TriFunction<Character,Integer,String,Character> mask)
	{
		this.mask = mask;
		return this;
	}
	
	public InputConfigurator valid(Function<String,Boolean> valid)
	{
		this.valid = valid;
		return this;
	}
	
	public void setAllowMouseEvents(boolean b)
	{
		this.allowMouseEvents = b;
	}
	
	public boolean isAllowMouseEvents()
	{
		return allowMouseEvents;
	}
	
	public InputConfigurator regex(String regex)
	{
		this.regex = regex;
		this.pattern = Pattern.compile(regex);
		return this;
	}
	
	public String read()
	{
		String ret = console._readString(this);
		return ret;
	}

	public String readln()
	{
		String ret = console._readString(this);
		console.println();
		return ret;
	}

	public TriFunction<Character,Integer,String,Character> getMask()
	{
		return mask;
	}

	public Function<String,Boolean> getValid()
	{
		return valid;
	}

	public Pattern getRegex()
	{
		return pattern;
	}	
}
