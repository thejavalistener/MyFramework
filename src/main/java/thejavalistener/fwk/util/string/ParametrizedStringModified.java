package thejavalistener.fwk.util.string;

import java.util.List;
import java.util.function.Function;

public class ParametrizedStringModified extends ParametrizedString
{
	private List<String> transformedParameters = null;
	
	public ParametrizedStringModified(String s,Function<String,String> fTransf)
	{
		this(s,"${","}",fTransf);		
	}
	
	public ParametrizedStringModified(String s,String lDelim,String rDelim,Function<String,String> fTransf)
	{
		super(s,lDelim,rDelim);
		
		// transformo los parametros (fTransf podria ser trivial: (s)->s
		String ps = MyString.transformBetween(s,lDelim,rDelim,fTransf);
		setParametrizedString(ps);
		
		// resguardo los parametros transformados
		transformedParameters = MyString.pickBetween(ps,lDelim,rDelim);
	}

	public List<String> getTransformedParameters()
	{
		return transformedParameters;
	}
	
	public List<String> getOriginalParameters()
	{
		return getParameters();
	}
	
	public String getTransformedParameterFor(String originalParam)
	{
		int pos = getParameters().indexOf(originalParam);
		return transformedParameters.get(pos);
	}
		
	public String getOriginalParameterFor(String transformedParam)
	{
		int pos = transformedParameters.indexOf(transformedParam);
		return getParameters().get(pos);
	}
									
	public static void main(String[] args)
	{
		String s = "locomia ${p1.x} se la ${p2.y} come ${p3.z} trolazo";
		ParametrizedStringModified p = new ParametrizedStringModified(s,(pp)->pp.replace(".","QQQ"));
		p.setParameterValue("p1QQQx","WWW");
		System.out.println(p);

	}
}
