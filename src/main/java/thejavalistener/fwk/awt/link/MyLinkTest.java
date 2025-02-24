package thejavalistener.fwk.awt.link;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyLinkTest 
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		
		MyCenterLayout c = new MyCenterLayout();
		
		MyLinkButton lnk = new MyLinkButton("Mi link copado");
		lnk.setActionListener(l->System.out.println("click"));
		
		
		c.add(lnk.c());		
		MyTestUI.test(c)
			.addButton("Select/Unselect",l->lnk.setSelected(!lnk.isSelected()))
			.run();
	}	
}
