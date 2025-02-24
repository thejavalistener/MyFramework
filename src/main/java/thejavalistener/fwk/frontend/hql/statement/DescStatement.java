package thejavalistener.fwk.frontend.hql.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.frontend.hql.Attribute;
import thejavalistener.fwk.util.MyCollection;
import thejavalistener.fwk.util.string.MyString;

@Component
public class DescStatement extends AbstractStatement
{
	@Autowired
	private ApplicationContext ctx;

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void process()
	{
		String sql = getSql();
		List<String> words = MyString.wordList(sql);

		Set<EntityType<?>> set = em.getMetamodel().getEntities();
		

		List<String[]> ret = new ArrayList<>();
		
		// solo hay "desc"
		if( words.size()==1 )
		{
			set.forEach((b)->{
				EntityType<?> et = b;
				thejavalistener.fwk.frontend.hql.Entity e = new thejavalistener.fwk.frontend.hql.Entity(et.getJavaType());
				ret.add(new String[]{e.getClassName(),e.getTableName().toUpperCase()});
			});
			getScreen().addResult(sql,ret,new String[]{"Class","Table"});
		}
		else
		{
			if( words.size()==2 )
			{
				// sería, por ejemplo: "desc Alumno"
				String ent = words.get(1);
				EntityType<?> x = MyCollection.findElm(set,(et)->et.getJavaType().getSimpleName().equals(ent));
				if( x!=null )
				{
					thejavalistener.fwk.frontend.hql.Entity e = new thejavalistener.fwk.frontend.hql.Entity(x.getJavaType());
					for(Attribute att:e.getAttributes())
					{
						String add = att.isId()?" (*)":att.isFk()?" (fk)":"";
						ret.add(new String[]{att.getName()+add
								            ,att.getType().getSimpleName()
								            ,att.getFieldName().toUpperCase()
						});					
					}
					
					getScreen().addResult(sql,ret,new String[]{"Attribute","Type","Field"});
				}
				else
				{
					getScreen().showErrorMessage("No se encontró la entidad: "+ent,"Error");
				}	
			}
			else
			{
					String tablename = words.get(2).toUpperCase();
					
					sql = "";
					sql+="SELECT COLUMN_NAME, CASE ";
					sql+="WHEN DATA_TYPE = 'CHARACTER VARYING' THEN 'VARCHAR' ";
					sql+="ELSE DATA_TYPE ";
					sql+="END AS DATA_TYPE, IS_NULLABLE ";
					sql+="FROM INFORMATION_SCHEMA.COLUMNS ";
					sql+="WHERE TABLE_NAME = '"+tablename+"' ";
					sql+="ORDER BY ORDINAL_POSITION ";	
					
					List<?> lst = em.createNativeQuery(sql).getResultList();
					getScreen().addResult(sql,lst,new String[]{"Column","Type","Nullable?"});
			}
		}
		
	}
}
