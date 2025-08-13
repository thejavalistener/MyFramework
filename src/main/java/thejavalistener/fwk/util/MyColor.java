package thejavalistener.fwk.util;

import java.awt.Color;

public class MyColor
{
	/** "BLACK", "RED",... o */
	public static Color fromString(String s)
	{
		switch(s.toUpperCase())
		{
			case "BLACK":
				return Color.BLACK;
			case "WHITE":
				return Color.WHITE;
			case "RED":
				return Color.RED;
			case "GREEN":
				return Color.GREEN;
			case "BLUE":
				return Color.BLUE;
			case "ORANGE":
				return Color.ORANGE;
			case "YELLOW":
				return Color.YELLOW;
			case "PINK":
				return Color.PINK;
			case "MAGENTA":
				return Color.MAGENTA;
			case "GRAY":
				return Color.GRAY;
			case "LIGHTGRAY":
				return Color.LIGHT_GRAY;
			case "LIGHT_GRAY":
				return Color.LIGHT_GRAY;
			case "DARKGRAY":
				return Color.DARK_GRAY;
			case "DARK_GRAY":
				return Color.DARK_GRAY;
			case "CYAN":
				return Color.CYAN;
			default:
				return Color.decode(s);
		}
	}

	public static String toHTMLColor(int r, int g, int b)
	{
		return String.format("#%02X%02X%02X",r,g,b);
	}
	
	public static String toHexColor(int r, int g, int b,boolean without0x)
	{
		String x = toHexColor(r,g,b);
		return without0x?x.substring(2):x;
	}
	
	public static String toHexColor(int r, int g, int b)
	{
		return String.format("0x%02X%02X%02X",r,g,b);
	}

	public static String randomHtmlColorString()
	{
		int r=(int)(Math.random()*256);
		int g=(int)(Math.random()*256);
		int b=(int)(Math.random()*256);
		return toHTMLColor(r,g,b);
	}
	
	public static String randomHexColorString()
	{
		int r=(int)(Math.random()*256);
		int g=(int)(Math.random()*256);
		int b=(int)(Math.random()*256);
		return toHexColor(r,g,b);
	}

	public static Color random()
	{
		return fromString(randomHexColorString());
	}
}
