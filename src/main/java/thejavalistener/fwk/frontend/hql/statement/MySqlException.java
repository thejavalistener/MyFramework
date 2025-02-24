package thejavalistener.fwk.frontend.hql.statement;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLSyntaxErrorException;

import org.hibernate.engine.spi.EffectiveEntityGraph;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

public class MySqlException extends Exception
{
	private Exception efectiveEx = null;
	
	public MySqlException(Exception ex)
	{
		efectiveEx = ex;

		Throwable aux = ex;
  		while( aux!=null )
		{
			if( aux instanceof QuerySyntaxException )
			{
				efectiveEx = (QuerySyntaxException)aux;				
			}
			else
			{
				if( aux instanceof SQLGrammarException)
				{
					efectiveEx = (SQLGrammarException)aux;
				}
				else
				{
					if( aux instanceof SQLSyntaxErrorException )
					{
						efectiveEx = (SQLSyntaxErrorException)aux;
					}
				}
			}
			
			aux = aux.getCause();
		}		
	}
	
	@Override
	public String getMessage()
	{
		return efectiveEx.getMessage();
	}
	
	@Override
	public StackTraceElement[] getStackTrace()
	{
		return efectiveEx.getStackTrace();
	}
	
	@Override
	public synchronized Throwable getCause()
	{
		return efectiveEx.getCause();
	}
	
	@Override
	public void printStackTrace()
	{
		efectiveEx.printStackTrace();
	}
	
	@Override
	public void printStackTrace(PrintStream s)
	{
		efectiveEx.printStackTrace(s);
	}
	
	
	@Override
	public void printStackTrace(PrintWriter s)
	{
		super.printStackTrace(s);
	}
}
