package thejavalistener.fwk.util.string;

import java.util.List;
import java.util.stream.Collectors;

import thejavalistener.fwk.util.MyReflection;

public class ParametrizedString
{
	private String parametrizedString;
	private String parametrizedStringBKP;
	private List<String> parameters = null;
	private String leftDelimiter = null;
	private String rightDelimiter = null;
	
	public ParametrizedString(String s)
	{
		this(s,"${","}");
	}
	
	public void reset()
	{
		parametrizedString = parametrizedStringBKP;
	}
	
	public ParametrizedString(String s,String lDelim,String rDelim)
	{
		this.parametrizedString = s;
		this.parametrizedStringBKP = s;
		
		this.leftDelimiter = lDelim;
		this.rightDelimiter = rDelim;
		
		// resguardo los parametros del string
		parameters = MyString.pickBetween(s,lDelim,rDelim);
		parameters = parameters.stream().distinct().collect(Collectors.toList());
	}

	public void setParameterValue(String param,String paramValue)
	{	
		parametrizedString = MyString.replace(parametrizedString,leftDelimiter+param+rightDelimiter,leftDelimiter+paramValue+rightDelimiter);
	}
	
	public void setParameterValueFromField(String param,Object target)
	{
		List<String> x = MyString.split(param,".");

		Object aux = target;
		while(x.size()>1)
		{
			if(!x.get(0).equals("this"))
			{
				aux = MyReflection.object.invokeGetter(target,x.get(0));
			}
			x.remove(0);
		}
//		Object fieldValue = MyObject.invokeGetter(aux,x.get(0));
		Object fieldValue = MyReflection.object.invokeGetter(aux,x.get(0));
		String ret = fieldValue!=null?fieldValue.toString():"null";
		setParameterValue(param,ret);
	}

	public String unaFuncion()
	{
		return "HolaMundo";
	}
		
	public void setParameterValueByInvoke(String param,Object target)
	{
		List<String> x = MyString.split(param,".");

		Object aux = target;
		while(x.size()>1)
		{
			if(!x.get(0).equals("this"))
			{
//				aux = MyObject.getFieldValue(target,x.get(0));				
				aux = MyReflection.object.getFieldValue(target,x.get(0));
			}
			x.remove(0);
		}
		
//		Object val = MyObject.invokeMethod(aux,x.get(0));
		Object val = MyReflection.object.invokeMethod(aux,x.get(0));
		String ret = val!=null?val.toString():"null";
		setParameterValue(param,ret);
	}

	public void setParameterValuesByInvoke(Object target)
	{
		for(String param:getParameters())
		{
			setParameterValueByInvoke(param,target);
		}
	}

	
	public void setParameterValuesFromFields(Object target)
	{
		for(String param:getParameters())
		{
			setParameterValueFromField(param,target);
		}
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		// locomia $[WWW] se la $[p2.y] come [p3.z] trolazo
		// 01234567890123456789012345678901234567890123456

		int p0 = 0;
		int p1 = parametrizedString.indexOf(leftDelimiter,p0);			
		
		while( p1>=0 )
		{
			sb.append(parametrizedString.substring(p0,p1));
			
			p0 = p1+leftDelimiter.length();
			p1 = parametrizedString.indexOf(rightDelimiter,p0);
			sb.append(parametrizedString.substring(p0,p1));

			p0 = p1+=rightDelimiter.length();
			p1 = parametrizedString.indexOf(leftDelimiter,p0);			
		}
		
		return sb.toString()+parametrizedString.substring(p0);
	}

	public String getParametrizedString()
	{
		return parametrizedString;
	}

	public List<String> getParameters()
	{
		return parameters;
	}

	public String getLeftDelimiter()
	{
		return leftDelimiter;
	}

	public String getRightDelimiter()
	{
		return rightDelimiter;
	}
	
	protected void setParametrizedString(String s)
	{
		this.parametrizedString = s;
	}	
	
	public String contenidoDinamico()
	{
		String s="";
		s+="* Grupo: Los monchos cabeza de japi\n";
		s+="* Pregunta: Locomía se la come?\n";
		s+="* Opciones: [1], [2], [3]\n";
		s+="* Fecha de finalización: 2.10.2000\n";
		s+="* Mostrar resultados parciales: Si\n";
		return s;
	}

		
	public void demo()
	{
		String s="Revisa los datos que ingresaste y presiona Confirmar si son correctos. Puedes usar los comandos👉[/Inicio] para cancelar o 👉[/Anterior] para corregir.\n\n";
		s+="$[contenidoDinamico]\n\n";
		s+="¿Deseas confirmar y guardar estos datos?";				
		ParametrizedString ps = new ParametrizedString(s,"$[","]");
		ps.setParameterValuesByInvoke(this);
		System.out.println(ps);		
	}
	
	public static void main(String[] args)
	{
		ParametrizedString x = new ParametrizedString("xx");
		x.demo();
	}
}
