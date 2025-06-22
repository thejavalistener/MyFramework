package thejavalistener.fwk.awt.link;

import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyLinkGroupTest
{
	public static void main(String[] args)
	{
		MyLinkPanel mlp = new MyLinkPanel(MyLinkPanel.HORIZONTAL);
		MyLinkGroup grp = new MyLinkGroup();
		grp.setActionListener(l->System.out.println(l.getSource()));
		MyLink lnk;
		
		grp.addLink(lnk=new MyLink("Rojo"));
		mlp.addLink(lnk);
		grp.addLink(lnk=new MyLink("Verde"));
		mlp.addLink(lnk);
		grp.addLink(lnk=new MyLink("Azul"));
		mlp.addLink(lnk);
		grp.addLink(lnk=new MyLink("Violeta"));
		mlp.addLink(lnk);
		grp.addLink(lnk=new MyLink("Blanco"));
		mlp.addLink(lnk);
		
		grp.setSelected(0,true);
		
		MyTestUI.test(mlp.c()).run();
	}
}
