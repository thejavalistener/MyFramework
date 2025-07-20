package thejavalistener.fwk.awt.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MySectionPanelTest
{
	public static void main(String[] args)
	{
		MySectionPanel p = new MySectionPanel();
		p.addLeft(new JButton("Izq1")).addLeft(new JButton("Izq2"));
		p.addCenter(new JButton("center"));
		p.addRight(new JButton("right1")).addRight(new JButton("right2"));

		JFrame frame=new JFrame("MySectionPanel Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,400);
		frame.add(p.c(),BorderLayout.CENTER);
		frame.setVisible(true);
	}
}