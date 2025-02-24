package thejavalistener.fwk.util;

import java.awt.Color;

public class MyColor
{
	public static Color fromString(String s)
	{
		switch(s.toUpperCase())
		{
			case "BLACK": return Color.BLACK;
			case "WHITE": return Color.WHITE;
			case "RED": return Color.RED;
			case "GREEN": return Color.GREEN;
			case "BLUE": return Color.BLUE;
			case "ORANGE": return Color.ORANGE;
			case "YELLOW": return Color.YELLOW;
			case "PINK": return Color.PINK;
			case "MAGENTA": return Color.MAGENTA;
			case "GRAY": return Color.GRAY;
			case "LIGHTGRAY": return Color.LIGHT_GRAY;
			case "LIGHT_GRAY": return Color.LIGHT_GRAY;
			case "DARKGRAY": return Color.DARK_GRAY;
			case "DARK_GRAY": return Color.DARK_GRAY;
			case "CYAN": return Color.CYAN;
			default: return Color.decode(s);
		}
	}

}
