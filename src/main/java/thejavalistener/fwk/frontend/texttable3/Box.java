package thejavalistener.fwk.frontend.texttable3;

public class Box
{
	private StyleManager stmLabel = null;
	private StyleManager stmContent = null;
	private String label;
	private Object content;
	
	private double width;
	private int chars;
	
	int charWidth;
	
	public Box(MyAbstractTextTable t,String label,Object content,double width)
	{
		this.width = width;

		// content
		stmContent = new StyleManager(content,t.getStyleDelimiters());
		this.content = stmContent.getContent();

		// label
		stmLabel= new StyleManager(label,t.getStyleDelimiters());
		this.label = label!=null?stmLabel.getContent().toString():null;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label=label;
	}

	int getChars()
	{
		return chars;
	}

	void setChars(int chars)
	{
		this.chars=chars;
	}

	public Object getContent()
	{
		return content;
	}
	
	public StyleManager getLabelStyleManager()
	{
		return stmLabel;
	}

	public StyleManager getContentStyleManager()
	{
		return stmContent;
	}

	public void setContent(Object content)
	{
		this.content=content;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width=width;
	}
	
	

}
