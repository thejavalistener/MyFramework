package thejavalistener.fwk.awt.stacknavigator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyStackNavigatorTest
{
	static int i=0;
	static String arr[] = {"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};

	static MyStackNavigator<String> stack;

	static JLabel curr;
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		stack = new MyStackNavigator<>();
		
		stack.setActionListener(new EscuchaStack());
		f.add(stack.c(),BorderLayout.NORTH);
	
		curr = new JLabel();
		f.add(curr,BorderLayout.CENTER);
		
		JButton bAdd = new JButton("Add");
		bAdd.addActionListener(new EscuchaAdd());
		f.add(bAdd,BorderLayout.SOUTH);
		
		f.setSize(300,300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	static class EscuchaAdd implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String text = arr[(i++)%10];
			curr.setText(text);
			stack.push(text);
			
			stack.mostarEstado();
		}
	}
	
	static class EscuchaStack implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			curr.setText(stack.getPoppedElement());
			stack.mostarEstado();
		}
	}

}
