package thejavalistener.fwk.frontend.hql.statement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class UpdateStatement extends AbstractStatement
{
	@PersistenceContext
	private EntityManager em; 
		
	@Override
	@Transactional
	public void process()
	{
		String sql = getSql(); 
		
		Query q = em.createQuery(sql);				
		
		int updateCount = q.executeUpdate();
		
		if( updateCount<=0 )
		{			
			getScreen().showWarningMessage("Ninguna fila resultó afectada","Sin cambios");
			throw new MyRollbackException();
		}
		else
		{
			if( _confirmTransaction(updateCount) )
			{				
				getScreen().showInformationMessage(updateCount+" filas fueron afecatadas","Transacción exitosa");
			}
			else
			{
				getScreen().showInformationMessage("Ninguna fila resultó afectada","Transacción revertida");
				throw new MyRollbackException();
			}
		}		
	}


	private boolean _confirmTransaction(int updateCount)
	{
		return getScreen().showConfirmWarningMessage(updateCount+" filas resultarán afectadas.\n¿Confirma o revierte?","Confirmar o revertir");
	}
}
