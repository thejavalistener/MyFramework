package thejavalistener.fwk.awt.link;

import java.awt.Color;

public class MyLinkButton extends MyLink
{
	public MyLinkButton(String lbl)
	{
		this(lbl,null);
	}

	public MyLinkButton(String lbl,Color color)
	{
		super(lbl,MyLink.LINK);
		setClickeable(true);

		if( color!=null )
		{
			getStyle().linkForegroundUnselected=color;
			getStyle().linkForegroundRolloverUnselected=color;
		}
		
		getStyle().linkInsets.top=0;
		getStyle().linkInsets.bottom=0;
		
	}
}
