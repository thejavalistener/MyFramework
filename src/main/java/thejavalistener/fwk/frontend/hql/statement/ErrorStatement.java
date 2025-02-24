package thejavalistener.fwk.frontend.hql.statement;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ErrorStatement extends AbstractStatement
{
	@Override
	public void process() 
	{
		getScreen().showErrorMessage("["+getSql()+"] no puede ejecutarse","Error");
	}
}
