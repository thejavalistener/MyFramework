package thejavalistener.fwk.awt.link;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class MyLinkStyle
{
	public Color background = Color.WHITE;
	
	public Insets linkInsets = new Insets(0,5,1,5);
	public Color linkBackground = null;
	public Insets linkBackgroundInsets = new Insets(6,5,2,5);
	public Font linkFont= new Font("Calibri",Font.PLAIN,12);
	
	// selected
	public Color linkForegroundSelected = new Color(0,102,204); // azul
	public Color linkBackgroundSelected = null; //new Color(243,246,246); // gris claro
	public Color linkForegroundRolloverSelected = linkForegroundSelected; // sin cambio
	public Color linkBackgroundRolloverSelected = linkBackgroundSelected; // sin cambio
	
	// unselected
	public Color linkForegroundUnselected = new Color(21,28,85);// gris oscuro
	public Color linkBackgroundUnselected = null; //background; // nada
	public Color linkForegroundRolloverUnselected = linkForegroundUnselected; // sin cambio
	public Color linkBackgroundRolloverUnselected = new Color(243,246,246);//backgroundSelected; // gris claro	

	// border
	public Color borderColor = Color.GREEN;
	public int borderWidth = 2;

	public int borderUnselected = 0;
	public int borderRollover = 0;
	public int borderSelected = 0;

	public MyLinkStyle setBackground(Color background)
	{
		this.background=background;
		return this;
	}
	public MyLinkStyle setLinkInsets(Insets linkInsets)
	{
		this.linkInsets=linkInsets;
		return this;
	}
	public MyLinkStyle setLinkBackground(Color linkBackground)
	{
		this.linkBackground=linkBackground;
		return this;
	}
	public MyLinkStyle setLinkBackgroundInsets(Insets linkBackgroundInsets)
	{
		this.linkBackgroundInsets=linkBackgroundInsets;
		return this;
	}
	public MyLinkStyle setLinkFont(Font linkFont)
	{
		this.linkFont=linkFont;
		return this;
	}
	public MyLinkStyle setLinkForegroundSelected(Color linkForegroundSelected)
	{
		this.linkForegroundSelected=linkForegroundSelected;
		return this;
	}
	public MyLinkStyle setLinkBackgroundSelected(Color linkBackgroundSelected)
	{
		this.linkBackgroundSelected=linkBackgroundSelected;
		return this;
	}
	public MyLinkStyle setLinkForegroundRolloverSelected(Color linkForegroundRolloverSelected)
	{
		this.linkForegroundRolloverSelected=linkForegroundRolloverSelected;
		return this;
	}
	public MyLinkStyle setLinkBackgroundRolloverSelected(Color linkBackgroundRolloverSelected)
	{
		this.linkBackgroundRolloverSelected=linkBackgroundRolloverSelected;
		return this;
	}
	public MyLinkStyle setLinkForegroundUnselected(Color linkForegroundUnselected)
	{
		this.linkForegroundUnselected=linkForegroundUnselected;
		return this;
	}
	public MyLinkStyle setLinkBackgroundUnselected(Color linkBackgroundUnselected)
	{
		this.linkBackgroundUnselected=linkBackgroundUnselected;
		return this;
	}
	public MyLinkStyle setLinkForegroundRolloverUnselected(Color linkForegroundRolloverUnselected)
	{
		this.linkForegroundRolloverUnselected=linkForegroundRolloverUnselected;
		return this;
	}
	public MyLinkStyle setLinkBackgroundRolloverUnselected(Color linkBackgroundRolloverUnselected)
	{
		this.linkBackgroundRolloverUnselected=linkBackgroundRolloverUnselected;
		return this;
	}
	
}
