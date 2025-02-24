package thejavalistener.fwk.awt.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.util.MyAssert;
import thejavalistener.fwk.util.string.MyString;

public class MyFormRow
{
	private List<Component> rowComponents;
	private int align;
	private int width;
	private String rowName;
		
	public MyFormRow(int width,int align,String rowName)
	{
		this.align=align;
		this.width = width;
		this.rowName = rowName;
		rowComponents = new ArrayList<>();
	}
	
	public MyFormRow add(String label)
	{
		rowComponents.add(new MyLink(label).c());
		return this;
	}
	
	public MyFormRow add(Component c)
	{
		rowComponents.add(c);
		return this;
	}
	
	public void layout(double ...porc)
	{
		MyAssert.test(porc.length==rowComponents.size(),"Debe espeficicar "+rowComponents.size()+" porcentajes");

		for(int i=0; i<porc.length; i++)
		{
			MyAwt.setPreferredWidth((int)(width*porc[i]),rowComponents.get(i));				
		}
	}
	
	public void setVisible(boolean b)
	{
		for(Component c:rowComponents)
		{
			c.setVisible(b);
		}
	}
	
	public int getAlign()
	{
		return align;
	}
	
	public List<Component> getComponents()
	{
		return rowComponents;
	}
	
	public String getName()
	{
		return MyString.ifNull(rowName,"NULL");
	}
	
}
