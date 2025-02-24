package thejavalistener.fwk.frontend.hql.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import thejavalistener.fwk.util.NamedValue;

public class Hql
{
	private String hqlName;
	private String hqlQuery;
	private String domain;
	
	public Hql(String hql)
	{
		this(new NamedValue<String>("Unnamed",hql));
	}
	
	public Hql(NamedValue<String> namedHql)
	{
		this.hqlName = namedHql.getName();
		this.hqlQuery = namedHql.getValue();
//		_processDomain(this.hqlQuery);
		_processDomain(this.hqlName);
	}
	
	
	public static List<Hql> toHqlList(List<?> lst)
	{
		return toHqlList(lst,x->true);
	}
	
	public static List<Hql> toHqlList(List<?> lst,Function<Hql,Boolean> accept)
	{
		// null -> null
		if( lst==null) return null;
		
		ArrayList<Hql> ret = new ArrayList<>();
		
		// empty -> empty
		if( lst.isEmpty() ) return ret;

		for(Object o:lst)
		{
			Hql hql = Hql.toHql(o);
			if( accept.apply(hql) )
			{
				ret.add(hql);
			}
		}
		
		return ret;
	}
	
	public static List<String> toDomainList(List<?> lst)
	{
		List<String> ret = new ArrayList<>();
		
		for(Hql x:toHqlList(lst))
		{
			if( !ret.contains(x.getDomain()))
			{
				ret.add(x.getDomain());
			}
		}
		
		return ret;
	}
	
	public static List<NamedValue<String>> toNamedValue(List<?> lst)
	{
		return toNamedValue(lst,x->true);
	}
	
	public static List<NamedValue<String>> toNamedValue(List<?> lst,Function<Hql,Boolean> accept)
	{
		List<NamedValue<String>> ret = new ArrayList<>();
		for(Hql x:toHqlList(lst,accept))
		{
			ret.add(new NamedValue<String>(x.hqlName,x.getQuery()));
		}
		
		return ret;
	}
	
	public static List<String> toQueryList(List<?> lst)
	{
		return toQueryList(lst,f->true);
	}

	public static List<String> toQueryList(List<?> lst,String domain) 
	{
		return toQueryList(lst,f->f.getDomain().equals(domain));
	}
	
	public static List<String> toQueryList(List<?> lst,Function<Hql,Boolean> accept)
	{
		ArrayList<String> ret = new ArrayList<>();
		for(Hql hql:toHqlList(lst))
		{
			if( accept.apply(hql) )
			{
				ret.add(hql.getQuery());
			}
		}
		
		return ret;
	}

	public static Hql toHql(Object o)
	{
		if( o instanceof String)
		{
			return new Hql(o.toString());
		}
		
		if( o instanceof NamedValue)
		{
			return new Hql(((NamedValue<String>)o));
		}
		
		throw new RuntimeException("Unsoported object: "+o.getClass());
	}
	
	private void _processDomain(String hql)
	{
		String aux = hql.trim();
		int pos = aux.indexOf(',');
		if(pos<0)
		{
			pos=aux.indexOf(' ');
			if( pos<0 )
			{
				pos=aux.length();
			}
		}
		
		this.domain = aux.substring(0,pos);
		
//		String aux = hql;
//		int pos1 = aux.toLowerCase().indexOf("from")+4;
//		aux = aux.substring(pos1).trim();
//		pos1 = aux.indexOf(' ');
//		this.domain=aux.substring(0,pos1);
	}
	
	public String getQuery()
	{
		return hqlQuery;
	}
	
	public String getDomain()
	{
		return domain;
	}
	
	public String getName()
	{
		return this.hqlName;
	}	
}
