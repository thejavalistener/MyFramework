package thejavalistener.fwk.backend;

import java.util.List;

public class HqlQuery
{
	private String hql;
	private Object args[];
	
	public HqlQuery(String hql,Object ...args)
	{
		this.hql = hql;
		this.args = args;
	}

	public String getHql()
	{
		return hql;
	}

	public void setHql(String hql)
	{
		this.hql=hql;
	}

	public Object[] getArgs()
	{
		return args;
	}

	public void setArgs(Object[] args)
	{
		this.args=args;
	}
}
