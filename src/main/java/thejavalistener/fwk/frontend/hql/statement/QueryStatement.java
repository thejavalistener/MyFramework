package thejavalistener.fwk.frontend.hql.statement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.util.string.MyString;

@Component
@Primary
public class QueryStatement extends AbstractStatement
{
	@PersistenceContext
	private EntityManager em; 
	
	@Override
	public void process()
	{
		String sql = getSql();
		
		// Proceso la lapalbra LIMIT 
		int limit=_procesarLIMIT(sql);
		if( limit>=0 )
		{
			sql = _removerLIMIT(sql);
		}
		
		try
		{
			// Creo el Query
			Query q = em.createQuery(sql);
			if( limit>0 )
			{
				q.setMaxResults(limit);
			}
		
			List<?> lst = q.getResultList();
			getScreen().addResult(sql,lst);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			MySqlException mex = new MySqlException(e);
			getScreen().showExceptionMessage(mex);
		}
		
	}
	
	
	
	private int _procesarLIMIT(String sql)
	{
		String aux = sql.toLowerCase().replace('\n',' ').trim();
		List<String> words = MyString.wordList(aux);
		int n = words.size();
		if( n>1 && words.get(n-2).equalsIgnoreCase("limit") )
		{
			return Integer.parseInt(words.get(n-1));
		}
		
		return -1;
	}
	
	public String _removerLIMIT(String sql)
	{
		int p = sql.toLowerCase().lastIndexOf("limit");
		return sql.substring(0,p);
	}
	
}
