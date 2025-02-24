package thejavalistener.fwk.frontend.hql.statement;
//package myframework.frontend.hql.statement;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.stereotype.Component;
//
//import myframework.util.string.MyString;
//
//@Component
//public class DescStatement extends AbstractStatement
//{
//	@PersistenceContext
//	private EntityManager em; 
//	
//	@Override
//	public String getSql()
//	{
//		// tomo el sql original
//		String sql = super.getSql();
//		
//		List<String> words = MyString.wordList(sql,x->x.toUpperCase());
//		boolean hayTabla = words.size()>1;
//		
//		if( !hayTabla )
//		{
//			sql = "";
//			sql+="SELECT DISTINCT(TABLE_NAME) ";
//			sql+="FROM INFORMATION_SCHEMA.COLUMNS ";
//			sql+="WHERE TABLE_SCHEMA = 'PUBLIC' ";
//			sql+="ORDER BY TABLE_NAME ";
//		}
//		else
//		{
//			String tablename = words.get(1);
//			
//			sql = "";
//			sql+="SELECT COLUMN_NAME, CASE ";
//			sql+="WHEN DATA_TYPE = 'CHARACTER VARYING' THEN 'VARCHAR' ";
//			sql+="ELSE DATA_TYPE ";
//			sql+="END AS DATA_TYPE, IS_NULLABLE ";
//			sql+="FROM INFORMATION_SCHEMA.COLUMNS ";
//			sql+="WHERE TABLE_NAME = '"+tablename+"' ";
//			sql+="ORDER BY ORDINAL_POSITION ";		
//		}
//		
//		return sql;
//	}
//
//	@Override
//	public void process()
//	{
//		String sql = getSql();
//		Query q = em.createNativeQuery(sql);
//		List<?> lst = q.getResultList();
//		getScreen().addResult(sql,lst);
//	}
//}
