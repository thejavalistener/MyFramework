package thejavalistener.fwk.awt.link;

import thejavalistener.fwk.awt.panel.MyRandomColorPanel;
import thejavalistener.fwk.awt.testui.MyTestUI;
import thejavalistener.fwk.util.string.MyString;

public class MyLinkedPaneTest
{
	public static void main(String[] args)
	{
//		MyPanel.DEBUG_MODE = true;
		
		MyLinkedPane lp = new MyLinkedPane(MyLinkedPane.HORIZONTAL);
		lp.setActionListener(l->System.out.println(l.getSource()));
		lp.addTab("Sexo",new MyRandomColorPanel());
		lp.addTab("Ibiza",new MyRandomColorPanel());
		lp.addTab("Locomía",new MyRandomColorPanel());
		lp.addTab("Abanico",new MyRandomColorPanel());
		
		lp.setSelectedTab(2,false);
		
		MyTestUI.test(lp.c())
		.addButton("RmvFst",x->lp.removeTab(0))
		.addButton("RmvLast",x->lp.removeLast())
		.addButton("Set selected 1 ",x->lp.setSelectedTab(1))
		.addButton("Add",x->lp.addTab(MyString.generateRandom(),new MyRandomColorPanel()))
		.addButton("Hide",x->lp.showLinks(false))
		.addButton("Show",x->lp.showLinks(true))
		.run();
	}
}
