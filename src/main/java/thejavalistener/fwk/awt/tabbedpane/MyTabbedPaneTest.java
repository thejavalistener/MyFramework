package thejavalistener.fwk.awt.tabbedpane;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyRandomColorPanel;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyTabbedPaneTest
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();

		MyTabbedPane mtp = new MyTabbedPane();
		mtp.addTab(new MyRandomColorPanel(),true);
		mtp.addTab(new MyRandomColorPanel(),true);
		mtp.addTab(new MyRandomColorPanel(),true);
		mtp.addTab(new MyRandomColorPanel(),true);
		
		MyTestUI.test(mtp.c()).addButton("Add",l->mtp.addTab(new MyRandomColorPanel(),true)).run();
	}
}
