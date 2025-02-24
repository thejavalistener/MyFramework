package thejavalistener.fwk.awt;

import java.util.Map;

public interface MyJParametersField
{
	public void addParameter(String pName,String pDefValue);
	public void addParameter(String pName,String pDefValue,MyJParameterMask mask);	
	public void addCustomMask(String pName,MyJParameterMask mask);
	public void setParameterValue(String pName,String pValue);
	public String getParameterValue(String pName);
	public void selectMyParameterValue(int idx);
	public boolean isMyParameterValueSelected(String pName);
	public Map<String,String> getParameterValues();
}
