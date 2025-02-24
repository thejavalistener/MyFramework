package thejavalistener.fwk.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Function;

import thejavalistener.fwk.util.string.MyString;

public class UTime implements Comparable<UTime>
{
	private int hour;
	private int minute;

	public UTime(int h,int m)
	{
		this.hour=h;
		this.minute=m;
	}

	public UTime(String s)
	{
		String sOk=_format(s);
		int pos=sOk.indexOf(':');
		this.hour=Integer.parseInt(sOk.substring(0,pos));
		this.minute=Integer.parseInt(sOk.substring(pos+1));
	}

	public UTime(long ms)
	{
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTimeInMillis(ms);
		this.hour=gc.get(Calendar.HOUR_OF_DAY);
		this.minute=gc.get(Calendar.MINUTE);
	}

	public UTime(Time t)
	{
		this(t.getTime());
	}

	public UTime(Timestamp ts)
	{
		this(ts.getTime());
	}

	public UTime()
	{
		this(System.currentTimeMillis());
	}

	public int compareTo(UTime x)
	{
		int difH=this.hour-x.hour;
		return difH!=0?difH:this.minute-x.minute;
	}

	public UTime elapsed(UTime t2)
	{
		int min1=hour*60+minute;
		int min2=t2.hour*60+t2.minute;
		int dif=Math.abs(min1-min2);
		return new UTime(dif/60,dif%60);
	}

	private String _format(String t)
	{
		// reemplazo cualquier separador por ":"
		Function<Character,Character> func=(c) -> Character.isDigit(c)?c:':';
		String tOk=MyString.replace(t,func);

		int charCount=MyString.charCount(tOk,':');

		if(charCount==0)
		{
			return tOk+":00";
		}
		if(charCount==1)
		{
			return tOk;
		}

		return tOk.substring(0,MyString.indexOfN(tOk,':',2));
	}

	@Override
	public String toString()
	{
		String sMin=Integer.toString(minute);
		String sHour=Integer.toString(hour);
		return MyString.lpad(sHour,'0',2)+":"+MyString.lpad(sMin,'0',2);
	}

	public Time toSqlTime()
	{
		return Time.valueOf(toString()+":00");
	}

	public int getHour()
	{
		return hour;
	}

	public void setHour(int hour)
	{
		this.hour=hour;
	}

	public int getMinute()
	{
		return minute;
	}

	public void setMinute(int minute)
	{
		this.minute=minute;
	}

	public String asHHMM(String sep)
	{
		StringBuffer sb=new StringBuffer();
		sb.append(MyString.lpad(Integer.toString(getHour()),'0',2)+sep);
		sb.append(MyString.lpad(Integer.toString(getMinute()),'0',2));
		return sb.toString();
	}

	public static String asHHMMSSMMM(Timestamp ts)
	{
		// to string
		String sts=ts.toString().trim();

		// sin fecha
		sts=sts.substring(sts.lastIndexOf(' ')+1);

		// separo ms
		int posPto=sts.indexOf('.');
		String hms=sts.substring(0,posPto);
		String ms=sts.substring(posPto+1);

		// paddeo
		ms=MyString.lpad(ms,'0',3);

		return hms+"."+ms;
	}

	public static String asDHM(long millis)
	{
		if( millis<0 )
		{
			return "desconocido";
		}
		
		long seconds=millis/1000;
		long minutes=seconds/60;
		long hours=minutes/60;
		long days=hours/24;

		String sD=days>1?"s":"";
		String sH=hours>1?"s":"";
		String sM=minutes>1?"s":"";

		if(days>0)
		{
			hours%=24;
			minutes%=60;
			return String.format("%d día"+sD+", %d hora"+sH+" y %d minuto"+sM,days,hours,minutes);
		}
		else if(hours>0)
		{
			minutes%=60;
			return String.format("%d hora"+sH+" y %d minuto"+sM,hours,minutes);
		}
		else if(minutes>0)
		{
			return String.format("%d minuto"+sM,minutes);
		}
		else
		{
			seconds%=60;
			return String.format("%d segundos",seconds);
		}
	}
}
