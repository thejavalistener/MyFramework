package thejavalistener.fwk.awt.link;

import java.awt.Color;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyLinkTest 
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		
		int border[] = new int[]{0};
		
		MyCenterLayout c = new MyCenterLayout();
		
		MyLinkButton lnk = new MyLinkButton("Mi link copado");
		lnk.setActionListener(l->System.out.println("click"));
		
		
		c.add(lnk.c());		
		MyTestUI.test(c)
		.addButton("Select/Unselect",l->lnk.setSelected(!lnk.isSelected()))
		.addButton("Border",l->{
			border[0]=border[0]==0?1:border[0]<=4?border[0]*2:0;
			lnk.setBorder(border[0],3,Color.GREEN);	
		})
		.run();
	}	
}
