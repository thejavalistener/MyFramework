package thejavalistener.fwk.awt.panel;

import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyAwt;

public class MyRandomColorPanel extends MyPanel
{
	public MyRandomColorPanel()
	{
		super(0,0,0,0);
		setBackground(MyAwt.randomColor());
		setBorder(null);
	}
}
