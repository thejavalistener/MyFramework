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
		msp.setDividerLocation(150);
		MyTestUI.test(msp.c())
		.addButton("Hide 0",mt->msp.showComponent(0,false))
		.addButton("Show 0",mt->msp.showComponent(0,true))
		.addButton("Hide 1",mt->msp.showComponent(1,false))
		.addButton("Show 1",mt->msp.showComponent(1,true))
		.run();
		
		
	}
}