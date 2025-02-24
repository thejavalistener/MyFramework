package thejavalistener.fwk.awt;

import java.util.function.BiFunction;

import thejavalistener.fwk.util.string.MyString;

public interface MyJParameterMask extends BiFunction<Character,String,Character>
{
	public static final MyJParameterMask HEX = (c,s)->MyString.isHexDigit(c)?c:null;
	public static final MyJParameterMask INTEGER = (c,s)->Character.isDigit(c)?c:null;
	public static final MyJParameterMask UPPERCASE = (c,s)->Character.toUpperCase(c);
	public static final MyJParameterMask LOWERCASE = (c,s)->Character.toLowerCase(c);
	
}
