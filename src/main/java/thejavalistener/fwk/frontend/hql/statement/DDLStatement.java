package thejavalistener.fwk.frontend.hql.statement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.util.string.MyString;

@Component
@Primary
public class DDLStatement extends AbstractStatement
{
	@PersistenceContext
	private EntityManager em; 
	
	@Override
	@Transactional
	public void process() 
	{
		String sql = getSql();
		Query q = em.createNativeQuery(sql);
		
		String w = MyString.getWordAt(sql,0).toLowerCase();

		String mssgWarn = null;
				
		switch(w)
		{
			case "drop":
				mssgWarn = "La sentencia eliminará datos. ¿Desea continuar?";
				break;
			case "alter":
				mssgWarn = "La sentencia modificará la estructura de datos. ¿Desea continuar?";
				break;
		}
		
		try
		{		
			if( mssgWarn!=null )
			{
				if( getScreen().showConfirmWarningMessage(mssgWarn,"ATENCIÓN") ) 
				{
					q.executeUpdate();				
					getScreen().showInformationMessage("La sentencia se ejecutó con éxito","Ejecución exitosa");
				}
			}
			else
			{
				q.executeUpdate();			
				getScreen().showInformationMessage("La sentencia se ejecutó con éxito","Ejecución exitosa");
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			MySqlException mex = new MySqlException(ex);
			getScreen().showExceptionMessage(mex);
		}
	}
}
