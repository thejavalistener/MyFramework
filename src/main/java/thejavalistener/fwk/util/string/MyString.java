package thejavalistener.fwk.util.string;

import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import thejavalistener.fwk.util.MyThread;
import thejavalistener.fwk.util.UDate;

public class MyString
{
	public static String ts()
	{
		return ts(26);
	}
	
	public static int countDifferentChars(String s, char c)
	{
		return s.length()-charCount(s,c);
	}

	public static boolean isPrintableChar(char c)
	{
		return Character.isDigit(c)||Character.isAlphabetic(c)||isSymbol(c); //||c==' ';
		// Character.UnicodeBlock block=Character.UnicodeBlock.of(c);
		// return
		// (!Character.isISOControl(c))&&c!=KeyEvent.CHAR_UNDEFINED&&block!=null&&block!=Character.UnicodeBlock.SPECIALS;
	}

	public static boolean isSymbol(char c)
	{
		// Verificar si el carácter es un símbolo
		String symbols="!@#$%^&*()-_=+[]{}|;:'\",.<>/?`~\\¨";
		return symbols.indexOf(c)>=0;
	}

	public static boolean isSOControlOrDeadKey(int keyCode, int keyChar)
	{
		System.out.println(keyCode);
		// Verificar teclas de función (F1-F12)
		if(keyCode>=KeyEvent.VK_F1&&keyCode<=KeyEvent.VK_F12)
		{
			return true;
		}

		// Verificar teclas especiales
		switch(keyCode)
		{
			case KeyEvent.VK_CONTROL:
			case KeyEvent.VK_ALT:
			case KeyEvent.VK_ALT_GRAPH:
			case KeyEvent.VK_WINDOWS:
			case KeyEvent.VK_SHIFT:
			case KeyEvent.VK_CAPS_LOCK:
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_BACK_SPACE:
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_CONTEXT_MENU:
			case KeyEvent.VK_PRINTSCREEN:
			case KeyEvent.VK_SCROLL_LOCK:
			case KeyEvent.VK_PAUSE:
			case KeyEvent.VK_INSERT:
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_NUM_LOCK:
			case 129: // apóstrofe hacia la der´
				return true;
		}

		switch(keyChar)
		{
			case '´':
			case '`':
			case '^':
				return true;
		}

		// Verificar si es un carácter muerto (diéresis, acentos, etc.)
		if(Character.getType(keyChar)==Character.NON_SPACING_MARK)
		{
			return true;
		}

		return false;
	}

	public static String ts(int radix)
	{
		MyThread.sleep(30);
		return Long.toString(System.currentTimeMillis(),radix).toUpperCase();

	}

	public static String[] extract(String s, String iDelim, String fDelim)
	{
		return extract(s,iDelim,fDelim,new StringBuffer());
	}

	public static String[] extract(String s, String iDelim, String fDelim, StringBuffer mainString)
	{

		ArrayList<String> resultList=new ArrayList<>();
		mainString.setLength(0); // Limpiar el contenido del StringBuffer

		int start=0;
		while(start<s.length())
		{
			int startIndex=s.indexOf(iDelim,start);
			if(startIndex==-1)
			{
				mainString.append(s.substring(start));
				break;
			}

			mainString.append(s.substring(start,startIndex));
			int endIndex=s.indexOf(fDelim,startIndex+iDelim.length());
			if(endIndex==-1)
			{
				break;
			}

			String command=s.substring(startIndex,endIndex+fDelim.length());
			resultList.add(command);

			start=endIndex+fDelim.length();
		}

		return resultList.toArray(new String[0]);
	}

	public static int indexOf(String s, BiFunction<String,Integer,Integer> func)
	{
		Integer ret;
		for(int i=0; i<s.length(); i++)
		{
			ret=func.apply(s,i);
			if(ret!=null)
			{
				return ret;
			}
		}

		return -1;
	}

	public static boolean matches(String s, Function<Character,Boolean> func)
	{
		for(int i=0; i<s.length(); i++)
		{
			char c=s.charAt(i);
			if(func.apply(c))
			{
				return true;
			}
		}

		return false;
	}
	
	public static int[] findParagraphBounds(String text, int index)
	{
		try
		{
			int[] bounds=new int[2];

			// Verificar si el índice es válido
			if(text==null||index<0||index>text.length())
			{
				return null; // Retornar null si el índice es inválido
			}

			if(text.length()>0&&text.indexOf('\n')<0)
			{
				return new int[] {0, text.length()};
			}

			// Buscar el inicio del párrafo actual
			int start=text.lastIndexOf("\n\n",index);
			bounds[0]=start==-1?0:start+2; // Si no hay, el inicio es 0

			// Buscar el final del párrafo actual
			int end=text.indexOf("\n\n",index);
			bounds[1]=end==-1?text.length():end; // Si no hay, el final es el
													// largo del texto
			if(start==end)
			{
				if(start<0)
				{
					return new int[] {0, text.length()};
				}
				if(start==0||start==text.length())
				{
					return null;
				}
				else
				{
					return findParagraphBounds(text,index-1);
				}
			}
			else
			{
				return bounds;
			}

		}
		catch(StringIndexOutOfBoundsException e)
		{
			return null;
		}

	}
	
	public static List<String> wordList(String s)
	{
		return wordList(s,x -> x);
	}

	public static List<String> wordList(String s, Function<String,String> wordTransform)
	{
		String aux;
		ArrayList<String> w=new ArrayList<>();
		StringBuffer sb=new StringBuffer();

		s=s.trim()+" ";
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i)!=' ')
			{
				sb.append(s.charAt(i));
			}
			else
			{
				if(sb.length()>0)
				{
					aux=removeChars(sb.toString(),"\n\r\t");
					w.add(wordTransform.apply(aux));
					sb.delete(0,sb.length());
				}
			}
		}

		return w;
	}

	public static int wordCount(String s)
	{
		return wordList(s).size();
	}

	public static String wordCapitalizeFirst(String s)
	{
		String ret=s;
		if(!isEmptyOrNull(s))
		{
			StringBuffer sb=new StringBuffer();
			sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase());
			ret=sb.toString();
		}

		return ret;
	}

	public static String wordCapitalize(String s)
	{
		StringBuffer sb=new StringBuffer();
		StringTokenizer st=new StringTokenizer(s," ");
		while(st.hasMoreTokens())
		{
			sb.append(wordCapitalizeFirst(st.nextToken())).append(" ");
		}

		return sb.toString();
	}

	public static int prevIndexOf(String s, String toSearch, int fromIndex)
	{
		if(s==null||toSearch==null||fromIndex<0)
		{
			return -1; // Valores inválidos de entrada
		}

		// Ajustar fromIndex si es mayor que la longitud de la cadena
		fromIndex=Math.min(fromIndex,s.length()-1);

		// Buscar hacia atrás desde fromIndex
		for(int i=fromIndex; i>=0; i--)
		{
			// Verificar si la subcadena desde la posición actual hasta el final
			// de toSearch
			// coincide con toSearch
			if(i+toSearch.length()<=s.length()&&s.substring(i,i+toSearch.length()).equals(toSearch))
			{
				return i; // Retornar la posición de la primera ocurrencia
			}
		}
		return -1; // Retornar -1 si no se encuentra toSearch
	}

	// Esto es un cosa
	// mas dificil de lo que pense
	//
	// porque no me di cuenta
	//
	// de lo mucho
	// que me cuesta
	// ser tu
	//
	// amigo

	public static int prevLine(String s, int fromIndex, StringBuffer line)
	{
		if(fromIndex==0) return -1;

		line.delete(0,line.length());

		// busco el \n anterior
		int p0=prevIndexOf(s,'\n',fromIndex);

		// busco el \n posterior
		int p1=s.indexOf('\n',fromIndex);

		if(p0>=0&&p1>=0)
		{
			line.append(s.substring(p0+1,p1));
			// return p0-1;
			return p0;
		}

		if(p0<0&&p1<0)
		{
			line.append(s);
			return -1;
		}

		if(p0<0)
		{
			line.append(s.substring(0,p1));
			return 0;
		}
		else
		{
			line.append(s.substring(p0+1));
			return p0-1;
		}
	}

	public static int nextLine(String s, int fromIndex, StringBuffer line)
	{
		line.delete(0,line.length());

		// busco el \n anterior
		int p0=prevIndexOf(s,'\n',fromIndex);

		// busco el \n posterior
		int p1=s.indexOf('\n',fromIndex);

		if(p0>=0&&p1>=0)
		{
			line.append(s.substring(p0+1,p1));
			return p1+1;
		}

		if(p0<0&&p1<0)
		{
			line.append(s);
			return -1;
		}

		if(p0<0)
		{
			line.append(s.substring(0,p1));
			return p1+1;
		}
		else
		{
			line.append(s.substring(p1+1));
			return -1;
		}
	}

	public static int prevIndexOf(String s, char c, int fromIndex)
	{
		while(--fromIndex>=0&&s.charAt(fromIndex)!=c)
			;
		return fromIndex;
	}

	public static boolean isHexDigit(char c)
	{
		c=Character.toUpperCase(c);
		return isInRange(c,'0','9')||isInRange(c,'A','F');
	}

	public static boolean isInRange(String s, int lo, int up)
	{
		if(MyString.isEmptyOrNull(s))
		{
			return false;
		}

		int i=Integer.parseInt(s);
		return i>=lo&&i<=up;
	}

	public static boolean isInRange(char c, char lo, char up)
	{
		return c>=lo&&c<=up;
	}

	public static String format(char sep, Object... cols)
	{
		StringBuffer sb=new StringBuffer(Character.toString(sep));
		int n=cols.length;
		for(int i=0; i<n; i+=2)
		{
			int size=(Integer)cols[i+1];
			Object mssg=cols[i]==null?"null":cols[i];
			Object mssgOk;
			if(mssg instanceof Number)
			{
				mssgOk=lpad(mssg.toString(),' ',size);
			}
			else
			{
				mssgOk=rpad(mssg.toString(),' ',size);
			}

			sb.append(mssgOk);
			sb.append(sep);
		}

		return sb.toString();
	}

	public static List<String> loadLinesFromFile(String filename)
	{
		try
		{
			return Files.readAllLines(Paths.get(filename));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String loadFromFile(String filename)
	{
		try
		{
			return Files.readString(Paths.get(filename));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String lpad(String s, char c, int n)
	{
		int x=n-s.length();
		return x>0?replicate(c,x)+s:s;
	}

	public static String replicate(char c, int n)
	{
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<n; i++)
		{
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * retorna la subcadena de $s que va desde el inicio hasta la primera
	 * ocurrencia de $x, o null si $s no contiene a $x
	 */
	public static String substringBeforeFirst(String s, String x)
	{
		return substringBeforeFirst(s,x,null);
	}

	/**
	 * Retorna la subcadena de $s que va desde el inicio hasta la ultima
	 * ocurrencia de $x, o null si $s no contiene a $x
	 */
	public static String substringBeforeLast(String s, String x)
	{
		return substringBeforeLast(s,x,null);
	}

	public static String substringBeforeLast(String s, String x, String sdefault)
	{
		int p=s.lastIndexOf(x);
		if(p>=0)
		{
			return s.substring(0,p);
		}
		else
		{
			return sdefault;
		}
	}

	/**
	 * retorna la subcadena de $s que va desde el inicio hasta la primera
	 * ocurrencia de $x, o $sdefault si $s no contiene a $x
	 */
	public static String substringBeforeFirst(String s, String x, String sdefault)
	{
		int pos=s.indexOf(x);
		return pos>=0?s.substring(0,pos):sdefault;
	}

	/**
	 * retorna la subcadena de $s que va desde la ultima ocurrencia de $x hasta
	 * el final, o null si $s no contiene a $x
	 */
	public static String substringAfterLast(String s, String x)
	{
		return substringAfterLast(s,x,null);
	}

	/**
	 * retorna la subcadena de $s que va desde la ultima ocurrencia de $x hasta
	 * el final, o $sdefault si $s no contiene a $x
	 */
	public static String substringAfterLast(String s, String x, String sdefault)
	{
		int pos=s.lastIndexOf(x);
		return pos>=0?s.substring(pos+x.length()):sdefault;
	}

	public static boolean isEmptyOrNull(String s)
	{
		return s==null||s.isEmpty();
	}

	public static String remove(String s, char c)
	{
		return remove(s,Character.toString(c));
	}

	public static String remove(String s, String... pattern)
	{
		String aux=s;
		for(String x:pattern)
		{
			aux=remove(aux,x);
		}

		return aux;
	}
	
	public static String remove(String s, String charsToRemove) {
	    // Escapá los caracteres especiales de regex dentro de un "character class"
	    String regex = "[" + Pattern.quote(charsToRemove) + "]";
	    return s.replaceAll(regex, "");
	}
	
	public static String replace(String s, Function<Character,Character> func)
	{
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<s.length(); i++)
		{
			sb.append(func.apply(s.charAt(i)));
		}

		return sb.toString();
	}

	public static String removeAcute(String s)
	{
		StringBuffer sb=new StringBuffer(s);
		for(int i=0; i<sb.length(); i++)
		{
			char c=sb.charAt(i);
			switch(c)
			{
				case 'á':
					c='a';
					break;
				case 'é':
					c='e';
					break;
				case 'í':
					c='i';
					break;
				case 'ó':
					c='o';
					break;
				case 'ú':
					c='u';
					break;
			}

			sb.setCharAt(i,c);
		}

		return sb.toString();

	}

	public static int charCount(String s, Function<Character,Boolean> f)
	{
		int cont=0;
		for(int i=0; i<s.length(); i++)
		{
			char c=s.charAt(i);
			if(f.apply(c))
			{
				cont++;
			}
		}

		return cont;
	}

	public static int charCount(String s, char c)
	{
		return charCount(s,x -> x==c);
	}

	public static String removeChars(String s, String chars)
	{
		StringBuffer sb=new StringBuffer();
		for(int i=0; i<s.length(); i++)
		{
			char c=s.charAt(i);
			if(chars.indexOf(c)<0)
			{
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String replace(String s, String pattern, String toreplace)
	{
		List<String> split=split(s,pattern);
		StringBuffer sb=new StringBuffer();

		for(int i=0; i<split.size(); i++)
		{
			sb.append(split.get(i));
			if(i<split.size()-1)
			{
				sb.append(toreplace);
			}
		}

		return sb.toString();
	}

	public static List<String> split(String s, String delim, Function<String,String> f)
	{
		ArrayList<String> ret=new ArrayList<>();

		if(isEmptyOrNull(s))
		{
			return ret;
		}

		int offset=0;
		int p=s.indexOf(delim);

		if(p<0)
		{
			ret.add(s);
		}

		while(p>=0)
		{
			String toAdd=f.apply(s.substring(offset,p));
			ret.add(toAdd);

			offset=p+delim.length();
			p=s.indexOf(delim,offset);

			if(p<0)
			{
				ret.add(f.apply(s.substring(offset)));
			}
		}

		return ret;
	}

	public static List<String> split(String s, String delim)
	{
		ArrayList<String> ret=new ArrayList<>();

		if(isEmptyOrNull(s))
		{
			return ret;
		}

		int offset=0;
		int p=s.indexOf(delim);

		if(p<0)
		{
			ret.add(s);
		}

		while(p>=0)
		{
			ret.add(s.substring(offset,p));

			offset=p+delim.length();
			p=s.indexOf(delim,offset);

			if(p<0)
			{
				ret.add(s.substring(offset));
			}
		}

		return ret;
	}

	public static String switchCase(String s, int index)
	{
		StringBuffer sb=new StringBuffer(s);
		char c=s.charAt(index);
		if(Character.isUpperCase(c))
		{
			sb.setCharAt(index,Character.toLowerCase(c));
		}
		else
		{
			sb.setCharAt(index,Character.toUpperCase(c));
		}

		return sb.toString();
	}

	/** Retorna todas las subcadenas encerradas entre $ldelim y $rdelim */
	public static List<String> pickBetween(String s, String ldelim, String rdelim)
	{
		return pickBetween(s,ldelim,rdelim,(x) -> x);
	}

	/**
	 * transforma y retorna todas las subcadenas delimitadas entre $ldelim y
	 * $rdelim
	 */
	public static List<String> pickBetween(String s, String ldelim, String rdelim, Function<String,String> fTranf)
	{
		ArrayList<String> ret=new ArrayList<>();

		int p1=s.indexOf(ldelim);
		while(p1>=0)
		{
			p1+=ldelim.length();
			int p2=s.indexOf(rdelim,p1);
			ret.add(fTranf.apply(s.substring(p1,p2)));
			p2+=rdelim.length();
			p1=s.indexOf(ldelim,p2);
		}

		return ret;
	}

	public static String removeBetween(String s, String ldelim, String rdelim)
	{
		return replaceBetween(s,ldelim,rdelim,"");
	}

	public static String replaceBetween(String s, String ldelim, String rdelim, String r)
	{
		return transformBetween(s,ldelim,rdelim,(str) -> r);
	}

	/**
	 * transforma y reemplaza todas las subcadenas delimitadas entre $ldelim y
	 * $rdelim
	 */
	public static String transformBetween(String s, String ldelim, String rdelim, Function<String,String> fTranf)
	{
		StringBuffer sb=new StringBuffer();

		int p0=0;
		int p1=s.indexOf(ldelim);
		while(p1>=0)
		{
			p1+=ldelim.length();
			int p2=s.indexOf(rdelim,p1);

			sb.append(s.substring(p0,p1));
			sb.append(fTranf.apply(s.substring(p1,p2)));
			p0=p2;
			p2+=rdelim.length();
			p1=s.indexOf(ldelim,p2);
		}

		sb.append(s.substring(p0));

		return sb.toString();
	}

	private static String firstChars(String s, int n)
	{
		int len=Math.min(s.length(),n);
		return s.substring(0,len);
	}

	public static int getDecimalPart(double d, int prec)
	{
		String s=Double.toString(d);
		int p=s.indexOf('.');
		p=p>=0?p:-1;
		return Integer.parseInt(firstChars(s.substring(p+1),prec));
	}

	/** Retorna true si s.charAt(idx) está contenido en chars */
	public static boolean isCharAtIn(String s, int idx, String chars)
	{
		return isCharAtIn(s,idx,chars.toCharArray());
	}

	/** Retorna true si s.charAt(idx) está contenido en chars */
	public static boolean isCharAtIn(String s, int idx, char... chars)
	{
		char c=s.charAt(idx);
		for(int i=0; i<chars.length; i++)
		{
			if(c==chars[i])
			{
				return true;
			}
		}

		return false;
	}

	public static Double parseDouble(String text)
	{
		try
		{
			return Double.parseDouble(text);
		}
		catch(Exception e)
		{
		}

		return null;
	}

	public static Integer parseInteger(String text)
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch(Exception e)
		{
		}

		return null;
	}
	
	public static Integer parseInteger(String text,Integer defValue)
	{
		if( text==null || text.isEmpty() )
		{
			return defValue;
		}
		
		try
		{
			if( defValue==null )
			{
				return null;
			}
			else
			{
				return Integer.parseInt(text);
			}
		}
		catch(Exception e)
		{
			return defValue;
		}
	}


	public static Date parseSqlDate(String s)
	{
		return UDate.validate(s)?new UDate().toSqlDate():null;
	}

	public static Boolean parseBoolean(String text)
	{
		try
		{
			return Boolean.parseBoolean(text);
		}
		catch(Exception e)
		{
		}

		return null;
	}

	public static boolean isInteger(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	// public static int indexOfN(String s,char c,int n)
	// {
	// int cont = 0;
	// for(int i=0; i<s.length(); i++)
	// {
	// if( s.charAt(i)==c )
	// {
	// cont++;
	// if( cont==n )
	// {
	// return i;
	// }
	// }
	// }
	//
	// return -1;
	// }

	public static String lpadn(String s, int n)
	{
		return lpad(s,'0',n);
	}

	public static String cpad(char s, char c, int n)
	{
		return cpad(Character.toString(s),c,n);
	}

	/** Remueve la subcadena posterior a la ultima ocurrencia de c */
	public static String removeAfter(String s, char c)
	{
		int p=s.lastIndexOf(c);
		return s.substring(0,p<0?s.length():p);
	}

	public static String cpad(String s, char c, int n)
	{
		int diff,ni,nd;
		diff=n-s.length();

		nd=diff/2;
		ni=diff-nd;

		return replicate(c,ni)+s+replicate(c,nd);
	}

	public static String rpad(String s, char c, int n)
	{
		String ret=s+replicate(c,n-s.length());
		return ret.substring(0,n);
	}

	public static String assureAtEnd(String s, String toAssure)
	{
		return s.endsWith(toAssure)?s:s+toAssure;
	}

	public static String assureNotEndWith(String s, String toAssure)
	{
		if(s.endsWith(toAssure))
		{
			return s.substring(0,s.length()-toAssure.length());
		}
		else
		{
			return s;
		}
	}
	
	public static String ifEmpty(String s,String defaultIfEmpty)
	{
		return s!=null&&s.trim().isEmpty()?defaultIfEmpty:s;
	}


	public static String ifNull(Object o, String defaultIfNull)
	{
		return o!=null?o.toString():defaultIfNull;
	}

	public static String ifEmtyOrNull(String s, String defaultValue)
	{
		return s==null||s.isEmpty()?defaultValue:s;
	}

	public static String generateRandom()
	{
		return generateRandom('A','Z',5,5);
	}

	public static String generateRandom(char from, char to, int minLen, int maxLen)
	{
		StringBuffer sb=new StringBuffer();
		int n=ThreadLocalRandom.current().nextInt(minLen,maxLen+1);

		for(int i=0; i<n; i++)
		{
			int randomNum=ThreadLocalRandom.current().nextInt(from,to+1);
			char c=(char)randomNum;
			sb.append(c);
		}
		return sb.toString();
	}

	public static String assureNotStartsWith(String s, String t)
	{
		if(s.startsWith(t))
		{
			return s.substring(t.length());
		}
		else
		{
			return s;
		}
	}

	public static String assureStartsWith(String s, String t)
	{
		if(!s.startsWith(t))
		{
			return t+s;
		}
		else
		{
			return s;
		}
	}

	public static void forEach(String s, Consumer<Character> c)
	{
		for(int i=0; i<s.length(); i++)
		{
			c.accept(s.charAt(i));
		}
	}

	public static int tokenCount(String s, char sep)
	{
		int n=MyString.charCount(s,sep);
		return n==0?1:n+1;
	}

	public static int indexOfN(String s, Function<Character,Boolean> func, int n)
	{
		int i=0;
		int cont=0;
		for(; i<s.length()&&cont<n; i++)
		{
			if(func.apply(s.charAt(i)))
			{
				cont++;
			}
		}

		return cont<n?-1:i-1;
	}

	public static int indexOfN(String s, char c, int n)
	{
		int offset=0;
		int cont=0;
		while(cont<n)
		{
			offset=s.indexOf(c,offset)+1;
			cont++;
		}

		return offset-1;
	}

	public static String getTokenAt(String s, char sep, int pos)
	{
		if(pos==0)
		{
			int p1=indexOfN(s,sep,1);
			p1=p1<0?s.length():p1; // 1er tk, no existe sep => toda la cadena
			return s.substring(0,p1);
		}
		else
		{
			if(pos<tokenCount(s,sep)-1)
			{
				int p1=indexOfN(s,sep,pos);
				int p2=Math.min(indexOfN(s,sep,pos+1),s.length()-1);
				return s.substring(p1+1,p2);
			}
			else
			{
				int p1=indexOfN(s,sep,pos);
				return s.substring(p1+1);
			}
		}
	}

	public static String rTrunc(String s, int n)
	{
		int y=Math.min(s.length(),n);
		return s.substring(0,y);
	}

	public static String lTrunc(String s, int n)
	{
		int y=Math.max(s.length()-n,0);
		return s.substring(y);
	}

	public static String rAssureLength(String s, int n, char c)
	{
		String x=rTrunc(s,n);
		return rpad(x,c,n);
	}

	public static String lAssureLength(String s, int n, char c)
	{
		String x=lTrunc(s,n);
		return lpad(x,c,n);
	}

	public static String cAssureLength(String s, int n, char c)
	{
		String x=rTrunc(lTrunc(s,n),n);
		return cpad(x,c,n);
	}

	public static Map<String,String> splitParameters(String s)
	{
		HashMap<String,String> params=new HashMap<>();
		List<String> lines=split(s,",");
		for(String x:lines)
		{
			if(!x.isEmpty())
			{
				String k=getTokenAt(x,'=',0);
				String v=getTokenAt(x,'=',1);
				params.put(k.trim(),v.trim());
			}
		}

		return params;
	}

	public static void clear(StringBuffer sb)
	{
		sb.delete(0,sb.length());
	}

	public static boolean isNumber(String s)
	{
		try
		{
			Integer.parseInt(s);
			Double.parseDouble(s);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static boolean regex(String s, String regex)
	{
		return Pattern.matches(regex,s);
	}

	public static String toString(Double d)
	{
		return d!=null?d.toString():"null";
	}

	public static String toString(Map<?,?> m)
	{
		StringBuffer ret=new StringBuffer();
		m.forEach((k, v) -> ret.append(k+":["+v+"]\n"));
		return ret.toString();
	}

	/**
	 * Retorna un objeto a partir de s. Por ejemplo: parseTo("2.34",Double.type)
	 * retorna un double
	 */
	public static Object parseTo(String s, Class<?> type)
	{
		if(s==null)
		{
			return null;
		}

		switch(type.getName())
		{
			case "char", "java.lang.Character":
				return s.charAt(0);
			case "short", "java.lang.Short":
				return Short.parseShort(s);
			case "int", "java.lang.Integer":
				return Integer.parseInt(s);
			case "long", "java.lang.Long":
				return Long.parseLong(s);
			case "float", "java.lang.Float":
				return Float.parseFloat(s);
			case "double", "java.lang.Double":
				return Double.parseDouble(s);
			case "boolean", "java.lang.Boolean":
				return Boolean.parseBoolean(s);
			case "java.lang.String":
				return s;
			case "java.sql.Date":
				return new UDate(s).toSqlDate();
			case "java.sql.Timestamp":
				return new UDate(s).toSqlTimestamp();
			case "java.sql.Time":
				return new UDate(s).toSqlTime();
			default:
				throw new RuntimeException(s+" no concuerda con nungún tipo de dato conocido");
		}
	}

	public static String getWordAt(String s, int i)
	{
		return wordList(s).get(i);
	}

	public static boolean oneOf(String s, String... options)
	{
		for(String x:options)
		{
			if(x.equals(s))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean isDouble(String x)
	{
		try
		{
			Double.parseDouble(x);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static String normalize(String s)
	{
	    String normalizado = Normalizer.normalize(s, Normalizer.Form.NFD);
	    return normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	public static String removeSuffix(String s, String suff)
	{
		return s.endsWith(suff)?s.substring(0,s.lastIndexOf(suff)):s;
	}
}