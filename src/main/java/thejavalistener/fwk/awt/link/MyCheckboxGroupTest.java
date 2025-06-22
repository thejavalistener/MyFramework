package thejavalistener.fwk.awt.link;

import javax.swing.JCheckBox;

import thejavalistener.fwk.awt.panel.MyCenterLayout;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyCheckboxGroupTest
{
	public static void main(String[] args)
	{
		MyCheckboxGroup grp = new MyCheckboxGroup();
		grp.setActionListener(l->System.out.println(l.getSource()));
		JCheckBox lnk;
		
		MyCenterLayout mlp = new MyCenterLayout();
		
		grp.addCheckbox(lnk=new JCheckBox("Rojo"));
		mlp.add(lnk);
		grp.addCheckbox(lnk=new JCheckBox("Verde"));
		mlp.add(lnk);
		grp.addCheckbox(lnk=new JCheckBox("Azul"));
		mlp.add(lnk);
		grp.addCheckbox(lnk=new JCheckBox("Violeta"));
		mlp.add(lnk);
		grp.addCheckbox(lnk=new JCheckBox("Blanco"));
		mlp.add(lnk);
		
//		grp.setSelected(0,true);
		
		MyTestUI.test(mlp).run();
	}
}
