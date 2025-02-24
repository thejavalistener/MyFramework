package thejavalistener.fwk.frontend.hql.screen;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.backend.DaoSupport;

@Component
public class HQLFacade extends DaoSupport
{
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void persist(Object o)
	{
		em.persist(o);
	}
	
	public Metamodel getMetamodel()
	{
		return em.getMetamodel();
	}
	
	public boolean isEntity(String entityName)
	{
		return getFullEntityName(entityName)!=null;
	}
	
	public String getFullEntityName(String entityName)
	{
		Metamodel  mm = getMetamodel();
		for(Iterator<EntityType<?>> it=mm.getEntities().iterator();it.hasNext();)
		{
			EntityType<?> et = it.next();
			if(et.getName().endsWith(entityName))
			{
				return et.getJavaType().getName();
			}
		}
		
		return null;		
	}

}
