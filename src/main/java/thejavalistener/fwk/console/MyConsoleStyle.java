package thejavalistener.fwk.console;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import thejavalistener.fwk.util.string.MyString;

public class MyConsoleStyle
{
	// font
	public String fontFamily = "Consolas";
	public int fontStyle = Font.PLAIN;
	public int fontSize = 12;
	
	// textArea
	public Color background = Color.BLACK;
	public Color foreground = Color.LIGHT_GRAY;
	public Color caretColor = foreground;
	
	public String defaultStyle = null;
	public String defaultInputStyle = "[fg(GREEN)]";
	
	// progress
	public char progressFill = ' ';
	public String progressStyle = "[bg(GREEN)]";
}
