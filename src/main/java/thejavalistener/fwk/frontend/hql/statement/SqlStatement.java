package thejavalistener.fwk.frontend.hql.statement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.util.string.MyString;

@Component
public class SqlStatement extends AbstractStatement
{
	@PersistenceContext
	private EntityManager em; 
	
	@Override
	public String getSql()
	{
		int p = super.getSql().indexOf("sql");
		return super.getSql().substring(p+3).trim(); 
	}

	@Override
	public void process()
	{
		String sql = getSql();
		Query q = em.createNativeQuery(sql);
		List<?> lst = q.getResultList();
		getScreen().addResult(sql,lst);
	}
}
