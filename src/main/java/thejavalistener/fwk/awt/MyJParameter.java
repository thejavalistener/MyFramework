package thejavalistener.fwk.awt;

import java.util.Arrays;
import java.util.List;

public class MyJParameter
{
	private String paramName;
	private String paramDefValue;
	private List<MyJParameterMask> paramMasks;

	public MyJParameter(String pName,String pDefValue)
	{
		this(pName,pDefValue,new MyJParameterMask[]{});
	}
	
	public MyJParameter(String pName,String pDefValue,MyJParameterMask ...masks)
	{
		this.paramName = pName;
		this.paramDefValue = pDefValue;
		paramMasks = Arrays.asList(masks);
	}
	
	public void addMask(MyJParameterMask mask)
	{
		paramMasks.add(mask);
	}

	public String getParamName()
	{
		return paramName;
	}

	public void setParamName(String paramName)
	{
		this.paramName=paramName;
	}

	public String getParamDefValue()
	{
		return paramDefValue;
	}

	public void setParamDefValue(String paramDefValue)
	{
		this.paramDefValue=paramDefValue;
	}

	public List<MyJParameterMask> getParamMasks()
	{
		return paramMasks;
	}
}
