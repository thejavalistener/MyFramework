package thejavalistener.fwk.frontend.hql.statement;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.frontend.hql.screen.HQLScreen;

@Component
public abstract class AbstractStatement
{
	private String sql = null;
	private HQLScreen screen;
	
	public abstract void process();

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql=sql;
	}

	public HQLScreen getScreen()
	{
		return screen;
	}

	public void setScreen(HQLScreen screen)
	{
		this.screen=screen;
	}		
	
	
}
