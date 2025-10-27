package thejavalistener.fwk.properties;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.backend.DaoSupport;
import thejavalistener.fwk.util.MyAssert;
import thejavalistener.fwk.util.MyJson;
import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.Pair;
import thejavalistener.fwk.util.string.MyString;

@Component
public class MyProperties 
{
	@Autowired
	private DaoSupport daoSupport;
	
	@Transactional
	public void putString(Class<?> domainClass,String name,String value)
	{
		putString(_fullName(domainClass,name),value);
	}
	
	@Transactional
	public void putString(String name,String value)
	{
		put(name,value);
	}
	
	@Transactional
	public void put(Class<?> domainClass,String name,Object value)
	{
		put(_fullName(domainClass,name),value);
	}
	
	@Transactional
	public void put(String name,Object value)
	{
		MyAssert.test(name.indexOf('$')<0,"La propiedad no puede tener $ en el nombre ni el dominio puede ser una inner class");
		
		String pkg = getClass().getPackageName();
		String sql = "FROM "+pkg+".MyFrameworkProperty p WHERE p.name='"+name+"'";
		MyFrameworkProperty p = daoSupport.querySingleRow(sql);

		String jsonString = MyJson.toJson(value);
		
		if( p!=null )
		{
			p.setValue(jsonString);
		}
		else
		{
			p = new MyFrameworkProperty();
			p.setName(name);
			p.setValue(jsonString);
			daoSupport.insert(p);
		}
	}
	
	
	@Transactional
	public <T> T discover(Class<?> domainClass,String name, T defaultValue)
	{
		return discover(_fullName(domainClass,name),defaultValue);
	}

	@Transactional
	public <T> T discover(String name, T defaultValue)
	{
		T t = get(name);
		if( t==null )
		{
			put(name,defaultValue);
			t = defaultValue;
		}
		
		return t;
	}
	
	public List<Pair> getAll(Class<?> domainClass)
	{
		return getAll(domainClass,"");
	}
	
	public List<Pair> getAll(Class<?> domainClass,String subdomain)
	{
		String dom = _fullName(domainClass,subdomain);
		dom+=dom.endsWith(".")?"":".";
		
		String pkg = getClass().getPackageName();
		String sql = "FROM "+pkg+".MyFrameworkProperty p WHERE p.name LIKE '"+dom+"%' ";
		List<MyFrameworkProperty> lst = daoSupport.queryMultipleRows(sql);
		
		List<Pair> ret = new ArrayList<>();
		for(MyFrameworkProperty p:lst)
		{
			String pName = MyString.remove(p.getName(),dom);
			Object pValue = MyJson.fromJson(p.getValue());
			ret.add(new Pair(pName,pValue));
		}
		
		return ret;
	}
	
	public String getString(Class<?> domainClass,String name)
	{
		return getString(_fullName(domainClass,name));
	}
	
	public String getString(String name)
	{
		return get(name);
	}
	
	public <T> T get(Class<?> domainClass,String name)
	{
		return get(_fullName(domainClass,name));
	}
	
	public <T> T get(String name)
	{
		String pkg = getClass().getPackageName();
		String sql = "SELECT p.value FROM "+pkg+".MyFrameworkProperty p WHERE p.name='"+name+"' ";
		MyLog.println("name="+name);
		MyLog.println(sql);
		
		String jsonString = daoSupport.querySingleRow(sql);
		
		if( jsonString==null )
		{
			return null;
		}
				
		return MyJson.fromJson(jsonString);
	}
	
	
	private String _fullName(Class<?> domainClass,String name)
	{
		return domainClass.getName()+"."+name;
	}

	@Transactional 
	public <T> T remove(Class<?> domainClass,String name)
	{
		return remove(_fullName(domainClass,name));
	}
	
	@Transactional
	public <T> T remove(String name)
	{
		T s = get(name);
		if( s!=null )
		{
			String pkg = getClass().getPackageName();
			String sql = "DELETE "+pkg+".MyFrameworkProperty p WHERE p.name=:name";
			daoSupport.update(sql,"name",name);
		}
		
		return s;
	}
	
	@Transactional
	public int removeAll()
	{
		String pkg = getClass().getPackageName();
		String sql = "DELETE "+pkg+".MyFrameworkProperty p ";
		return daoSupport.update(sql);
	}

}