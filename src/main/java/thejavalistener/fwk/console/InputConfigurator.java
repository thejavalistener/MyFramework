package thejavalistener.fwk.console;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import thejavalistener.fwk.util.TriFunction;
import thejavalistener.fwk.util.string.MyString;

public class InputConfigurator
{
	private MyConsoleBase console;

	// por defecto todo valida y todo matchea
	private TriFunction<Character,Integer,String,Character> mask = (c,i,s)->c;
	private Function<String,Boolean> valid = s->true;
	private String regex = ".*";
	private Pattern pattern = Pattern.compile(regex);
	private boolean allowMouseEvents = true;
	private Object defaultValue = "";
	
	public InputConfigurator(MyConsoleBase c)
	{
		this.console = c;
	}
	
	public InputConfigurator defval(Object dv)
	{
		this.defaultValue = dv;
		return this;
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
	
	public String oneOfln(String ...options)
	{
		String x = oneOf(options);
		console.println();
		return x;
	}
	public String oneOf(String ...options)
	{
		boolean todoMayus = Stream.of(options).allMatch(s -> s.equals(s.toUpperCase()));
		if( todoMayus )
		{
			return mask(MyConsole.UPPERCASE).valid(s->MyString.oneOf(s,options)).read();
		}
		else
		{
			boolean todoMinus = Stream.of(options).allMatch(s -> s.equals(s.toUpperCase()));
			if( todoMinus )
			{
				return mask(MyConsole.LOWERCASE).valid(s->MyString.oneOf(s,options)).read();				
			}
			else
			{
				return valid(s->MyString.oneOf(s,options)).read();								
			}
		}
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
	
	public Object getDefaultValue()
	{
		return defaultValue;
	}
}
