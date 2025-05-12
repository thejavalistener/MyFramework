package thejavalistener.fwk.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import thejavalistener.fwk.util.string.MyString;
import thejavalistener.fwk.util.string.ParametrizedString;

public class UDate
{
	private Calendar calendar;
	
	public UDate()
	{
		calendar = Calendar.getInstance();
	}
	
	public UDate yesterday()
	{
		return this.addDays(-1);
	}
	
	public UDate tomorrow()
	{
		return this.addDays(1);
	}
	
	public UDate(Date d)
	{
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(d.getTime());
	}
	
	public UDate(UDate ud)
	{
		this.calendar=ud.calendar;
	}

	public int cmp(UDate ud,boolean fullCmp)
	{
		return cmp(ud.calendar.getTimeInMillis(),fullCmp);
	}
	
	public int cmp(Date d)
	{
		return cmp(d.getTime(),false);
	}
	
	public String asISO8601()
	{
		int y = getYear();
		int m = getMonth();
		int d = getDay();
		
        LocalDateTime localDateTime = LocalDateTime.of(y, m, d, 0, 0);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return DateTimeFormatter.ISO_INSTANT.format(instant);
	}


	public int cmp(Timestamp ts,boolean fullCmp)
	{
		return cmp(ts.getTime(),fullCmp);
	}
	
	public int cmp(long ts,boolean fullCmp)
	{
		if( fullCmp )
		{
			long ln = calendar.getTimeInMillis()-ts;
			return ln<0?-1:ln>0?1:0;			
		}
		else
		{
			int y = calendar.get(Calendar.YEAR);
			int m = calendar.get(Calendar.MONTH);
			int d = calendar.get(Calendar.DAY_OF_MONTH);
			
			Calendar nc = new GregorianCalendar();
			nc.setTimeInMillis(ts);
			
			int oy = nc.get(Calendar.YEAR);
			int om = nc.get(Calendar.MONTH);
			int od = nc.get(Calendar.DAY_OF_MONTH);

			return y!=oy?y-oy:m!=om?m-om:d-od;
			
//			if( y==oy )
//			{
//				if( m==om )
//				{
//					return d-od;
//				}
//				
//			}
//			
//			
//			
//			long ln = calendar.getTimeInMillis()-ts;
//			return ln<0?-1:ln>0?1:0;						
		}		
	}
	
	public UDate newInstance()
	{
		return new UDate(this);
	}

	public UDate(int d,int m,int a)
	{
		calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,d);
		calendar.set(Calendar.MONTH,m-1);
		calendar.set(Calendar.YEAR,a);
	}

	public UDate(String sDate,String sTime)
	{
		this(sDate);
		UTime x = new UTime(sTime);		
		calendar.set(Calendar.HOUR_OF_DAY,x.getHour());
		System.out.println(new UDate(calendar.getTimeInMillis()));
		calendar.set(Calendar.MINUTE,x.getMinute());
		System.out.println(new UDate(calendar.getTimeInMillis()));
	}
	
	public static boolean validate(int y,int m,int d)
	{
		try
		{
			LocalDate.of(y,m,d);
			return true;
		}
		catch(DateTimeException ex)
		{
			return false;
		}
	}
	
	public static boolean validate(String s)
	{
		switch(s.trim().toLowerCase())
		{
			case "today","today()":
			case "sysdate","sysdate()":
			case "now","now()":
			return true;
		}
		
		String regex = "(\\d+[^0-9])\\d+[^0-9]\\d+";
        Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);

		if( !matcher.matches() )
		{
			return false;
		}
		
		
		Function<Character,Boolean> func = c->!Character.isDigit(c);
		int p1 = MyString.indexOfN(s,func,1);
		int p2 = MyString.indexOfN(s,func,2);
		
		if(p1<=0 || p2<=0 )
		{
			return false;
		}
		
		// veo si es yyyy-mm-dd o dd-mm-yyyy
		String s1 = s.substring(0,p1);
		String s2 = s.substring(p1+1,p2);
		String s3 = s.substring(p2+1);
		
		int i1 = Integer.parseInt(s1);
		int i2 = Integer.parseInt(s2);
		int i3 = Integer.parseInt(s3);
		
		if( s1.length()==4 )
		{
			return validate(i1,i2,i3);
		}
		else
		{
			return validate(i3,i2,i1);
		}
	}

	public UDate(String sDate)
	{
		this(sDate,false);
	}

	public UDate(String sDate,boolean iso8601)
	{
		if( !iso8601 )
		{
			String x = sDate.toLowerCase().trim();
			switch(x)
			{
				case "today","today()":
				case "sysdate","sysdate()":
				case "now","now()":
					calendar = Calendar.getInstance();
					break;
				default:
					setDate(sDate);
			}
		}
		else
		{
			calendar = Calendar.getInstance();

	        // 🔹 Convertir a `Instant`
	        Instant instant = Instant.parse(sDate);

	        // 🔹 Convertir a `LocalDate` en la zona horaria deseada (ejemplo: Argentina)
	        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

	        int dia = localDate.getDayOfMonth();
	        int mes = localDate.getMonthValue(); 
	        int anio = localDate.getYear();
	        setDate(anio,mes,dia);
		}
	}

	private static int[] _splitDate(String sDate)
	{
		// busco el primer caracter NO digito
		int i=-1;
		while(++i<sDate.length()&& Character.isDigit(sDate.charAt(i)));
		char sep = sDate.charAt(i);
		
		// posiciones de los separadores
		int pos1 = sDate.indexOf(sep);
		int pos2 = sDate.lastIndexOf(sep);
		
		// tres partes de la fecha
		String p1 = sDate.substring(0,pos1);
		String p2 = sDate.substring(pos1+1,pos2);
		String p3 = sDate.substring(pos2+1);
		
		int ret[] = new int[3];
		
		// si p1 es el anio...
		if( p1.length()>2 )
		{
			ret[0] = Integer.parseInt(p1);
			ret[1] = Integer.parseInt(p2);
			ret[2] = Integer.parseInt(p3);
		}
		else
		{
			ret[2] = Integer.parseInt(p3);
			ret[1] = Integer.parseInt(p2);
			ret[0] = Integer.parseInt(p1);			
		}
		
		return ret;
	}
	
	public UDate(Timestamp ts)
	{
		this(ts.getTime());
	}
	
	public UDate(long ts)
	{
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(ts);		
	}
	
	public UDate(Date date,Time time)
	{
		this(date);
		UTime ut = new UTime(time);
		calendar.set(Calendar.HOUR_OF_DAY,ut.getHour());
		calendar.set(Calendar.MINUTE,ut.getMinute());
	}

	public int getDayOfWeek(boolean firstIsMonday)
	{
		int dom = calendar.get(Calendar.DAY_OF_WEEK);
		if( firstIsMonday )
		{
			dom+=6;
			dom = dom<=7?dom:dom-7;				
		}
		
		return dom;
	}
		
	private UDate _add(int calendarField,int amount)
	{
		calendar.add(calendarField,amount);
		return this;
	}
	
	private void _set(int calendarField,int v){calendar.set(calendarField,v);}
	private int _get(int calendarField){return calendar.get(calendarField);}
	
	public UDate addDays(int amount){return _add(Calendar.DAY_OF_MONTH,amount);}
	public UDate addMounts(int amount){return _add(Calendar.MONTH,amount);}
	public UDate addYears(int amount){return _add(Calendar.YEAR,amount);}
	
	public void setDay(int d){_set(Calendar.DAY_OF_MONTH,d);}
	public void setMonth(int m){_set(Calendar.MONTH,m-1);}
	public void setYear(int y){_set(Calendar.YEAR,y);}
	
	public int getDay(){return _get(Calendar.DAY_OF_MONTH);}
	public int getMonth(){return _get(Calendar.MONTH)+1;}
	public int getYear(){return _get(Calendar.YEAR);}

	public UDate setTime(int h,int m)
	{
		_set(Calendar.HOUR_OF_DAY,h);
		_set(Calendar.MINUTE,m);
		_set(Calendar.SECOND,0);
		return this;
	}
	
	public UDate setTime(Time t)
	{
		UTime ut = new UTime(t);
		return setTime(ut.getHour(),ut.getMinute());
	}
	
	public Date toSqlDate()
	{
		return new Date(calendar.getTimeInMillis());
	}
	
	public Time toSqlTime()
	{
		return new UTime(_get(Calendar.HOUR_OF_DAY),_get(Calendar.MINUTE)).toSqlTime();
	}
	
	public String asYYYYMMDD(char sep)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(MyString.lpad(Integer.toString(getYear()),'0',4)+sep);
		sb.append(MyString.lpad(Integer.toString(getMonth()),'0',2)+sep);
		sb.append(MyString.lpad(Integer.toString(getDay()),'0',2));
		return sb.toString();
	}
	
	public int getHour()
	{
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMin()
	{
		return calendar.get(Calendar.MINUTE);		
	}

	public int getSec()
	{
		return calendar.get(Calendar.SECOND);		
	}
	
	public int getMS()
	{
		return calendar.get(Calendar.MILLISECOND);
	}
	
	/** Parámetros: dd,mm,yy,yyyy,h,m,s,ms */ 
	public String toString(String pstring)
	{
		ParametrizedString ps = new ParametrizedString(pstring);
		for(String pname:ps.getParameters())
		{
			switch(pname.toLowerCase())
			{
				case "dd":
					ps.setParameterValue(pname,Integer.toString(getDay()));
					break;
				case "mm":
					ps.setParameterValue(pname,Integer.toString(getMonth()));
					break;
				case "yy":
					ps.setParameterValue(pname,Integer.toString(getYear()).substring(2));
					break;
				case "yyyy":
					ps.setParameterValue(pname,Integer.toString(getYear()));
					break;
				case "h":
					ps.setParameterValue(pname,Integer.toString(getHour()));
					break;
				case "m":
					ps.setParameterValue(pname,Integer.toString(getMin()));
					break;
				case "s":
					ps.setParameterValue(pname,Integer.toString(getSec()));
					break;
				case "ms":
					ps.setParameterValue(pname,Integer.toString(getMS()));
					break;
			}
		}		
		
		return ps.toString();
	}
	
	public String asDDMMYYYY(char sep)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(MyString.lpad(Integer.toString(getDay()),'0',2)+sep);
		sb.append(MyString.lpad(Integer.toString(getMonth()),'0',2)+sep);
		sb.append(MyString.lpad(Integer.toString(getYear()),'0',4));
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return asDDMMYYYY('/');
	}
	

	
		
	public Timestamp toSqlTimestamp()
	{
		return new Timestamp(calendar.getTimeInMillis());
	}

	public int remainingDays()
	{
		long lF = calendar.getTimeInMillis();
		long lH = System.currentTimeMillis();
		long lD = Math.abs(lF-lH);
		long x = 86400000;
		return (int) (lD / x);
	}
	
	public boolean isToday()
	{
		return _isInNDays(0);
//		GregorianCalendar hoy = new GregorianCalendar();
//		hoy.setTimeInMillis(System.currentTimeMillis());
//		
//		boolean mismoAnio = calendar.get(Calendar.YEAR)==hoy.get(Calendar.YEAR);
//		boolean mismoMes = calendar.get(Calendar.MONTH)==hoy.get(Calendar.MONTH);
//		boolean mismoDia = calendar.get(Calendar.DAY_OF_MONTH)==hoy.get(Calendar.DAY_OF_MONTH);
//
//		return mismoDia&&mismoMes&&mismoAnio;
	}

	public boolean isTomorrow()
	{
		return _isInNDays(1);
	}
	
	public boolean isAfterTomorrow()
	{
		return _isInNDays(2);
	}
	
	
	private boolean _isInNDays(int n)
	{
		GregorianCalendar x = new GregorianCalendar();
		x.add(Calendar.DAY_OF_MONTH,n);
		
		boolean mismoAnio = calendar.get(Calendar.YEAR)==x.get(Calendar.YEAR);
		boolean mismoMes = calendar.get(Calendar.MONTH)==x.get(Calendar.MONTH);
		boolean mismoDia = calendar.get(Calendar.DAY_OF_MONTH)==x.get(Calendar.DAY_OF_MONTH);

		return mismoDia&&mismoMes&&mismoAnio;		
	}
	

	public UDate nextDayOfWeek(int dayOfWeek, boolean firstIsMonday)
	{
		int hoy=getDayOfWeek(firstIsMonday);
		if( hoy<dayOfWeek )
		{
			return addDays(dayOfWeek-hoy);
		}
		else
		{
			return addDays(7-hoy+dayOfWeek);			
		}
	}

	public UDate setDate(String sDate)
	{
		int aFec[] = _splitDate(sDate);
		int d = Math.min(aFec[0],aFec[2]);
		int a = Math.max(aFec[0],aFec[2]);
		int m = aFec[1];
		
		calendar = Calendar.getInstance();
		setDay(d);
		setMonth(m);
		setYear(a);
		return this;
	}	
	
	public UDate setDate(int y,int m,int d)
	{
		setYear(y);
		setMonth(m);
		setDay(d);
		return this;
	}

	public Date random(int fromYear,int toYear)
	{
	    // Generar un año aleatorio en el rango dado
	    int anio = ThreadLocalRandom.current().nextInt(fromYear, toYear + 1);

	    // Generar un mes aleatorio (1 a 12)
	    int mes = ThreadLocalRandom.current().nextInt(1, 13);

	    // Obtener el número máximo de días en ese mes y año
	    int diaMax = LocalDate.of(anio, mes, 1).lengthOfMonth();

	    // Generar un día válido (1 al máximo del mes)
	    int dia = ThreadLocalRandom.current().nextInt(1, diaMax + 1);

	    LocalDate fecha = LocalDate.of(anio, mes, dia);
	    return Date.valueOf(fecha);
	}	

}

