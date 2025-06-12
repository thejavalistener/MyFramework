package thejavalistener.fwk.frontend.hql.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.frontend.hql.screen.HQLScreen;
import thejavalistener.fwk.util.string.MyString;


@Component
public class FactoryStatement
{
	@Autowired
	private ApplicationContext ctx;
	
	public AbstractStatement getStatement(String sql,HQLScreen screen)
	{
		AbstractStatement stm = null;
		
		// tomo la primera palabra
		String w = MyString.getTokenAt(sql,' ',0).toLowerCase().trim();

		switch(w.trim().toLowerCase())
		{
			case "new":
//				stm = ctx.getBean(HQLNewObjectStatement.class);
				break;
			case "desc":
				stm = ctx.getBean(DescStatement.class);
				break;
			case "select":
			case "from":
				stm = ctx.getBean(QueryStatement.class);					
				break;
			case "delete":
			case "update":
				stm = ctx.getBean(UpdateStatement.class);										
				break;
			case "sql":
				stm = ctx.getBean(SqlStatement.class);										
				break;			
			case "alter":
			case "create":
			case "drop":
				stm = ctx.getBean(DDLStatement.class);										
				break;
			case "insert":
				stm = ctx.getBean(InsertStatement.class);
				break;
			default:
				stm = ctx.getBean(ErrorStatement.class);
		}
		
		stm.setSql(sql);
		stm.setScreen(screen);
		
		return stm;
	}
}
