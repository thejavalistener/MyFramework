package thejavalistener.fwk.awt.splitpane;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyRandomColorPanel;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MySplitPaneTest
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		MySplitPane msp = new MySplitPane(MySplitPane.HORIZONTAL,new MyRandomColorPanel(),new MyRandomColorPanel());
		MyTestUI.test(msp.c()).run();
		
		
	}
}