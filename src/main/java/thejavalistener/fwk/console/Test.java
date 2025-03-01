package thejavalistener.fwk.console;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.MyThread;
import thejavalistener.fwk.util.string.MyString;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		MyAwt.setWindowsLookAndFeel();

		MyConsole.io.banner("You are Welcome!");
		MyConsole.io.println("Plesae, press any key continue...").pressAnyKey();

		MyConsole c = MyConsole.io;
		
		String pwd = c.print("Password:").readlnPassword();
		c.println("Tu pwd es:["+pwd+"]");			

		c.beginProgressBar(20,100);
		for(int i=0; i<100; i++)c.increaseProgress();
		c.finishProgress();

		String name = c.print("What's your name? ").readlnString();
		c.println("Hi, "+name);
		
		int age = c.print("How years old are you ?").readlnInteger();
		c.println("You are "+age+" years old...");
		
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		String email = c.print("What's your email? ").readlnString(emailRegex);
		c.println("Your email is: "+email);
		
		String ops[] = {"Mar del Plata","Pinamar","Necochea","Mar de Ajo","Miramar"};
		int op = c.print("A dónde viajarás en las vacas? ").menuln(ops);
		c.println("Qué capo! Your hollidays will be in: "+ops[op]);
		
		String x = c.print("Contento? ").input().valid(s->MyString.oneOf(s,"SI","NO")).mask(MyConsole.UPPERCASE).readln();
		c.println("Veo que "+x+" estás contento...");
				
		c.println("Press any key to finish...").pressAnyKey();
		c.closeAndExit();

//		System.out.println("-----------------------------------------------------------");
//		
//		MyAwt.setWindowsLookAndFeel();
//		
//		JFrame jf = new JFrame();
//		MyConsole c = MyConsole.io;//MyConsole.get(jf);
//
//		JButton b = new JButton("XX");
//		JButton bb = new JButton("BB");
//		bb.addActionListener(l->c.textPane.getCaretCoords());
//		jf.add(bb,BorderLayout.EAST);
//		
//		jf.add(b,BorderLayout.WEST);
//		jf.setSize(300,300);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setVisible(true);
//		b.addActionListener(l->
//		{
//			MyConsole.io.banner("You are Welcome!");
//			MyConsole.io.println("Plesae, press any key continue...").pressAnyKey();
//
//			String pwd = c.print("Password:").readlnPassword();
//			c.println("Tu pwd es:["+pwd+"]");			
//
//			c.beginProgressBar(20,100);
//			MyThread.start(()->{
//				for(int i=0; i<100; i++)c.increaseProgress();
//			});
//			c.finishProgress();
//			
//			b.setText("LOCOMIA");
//			
//			
//			String name = c.print("What's your name? ").readlnString();
//			c.println("Hi, "+name);
//			
//			int age = c.print("How years old are you ?").readlnInteger();
//			c.println("You are "+age+" years old...");
//			
//			String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//			String email = c.print("What's your email? ").readlnString(emailRegex);
//			c.println("Your email is: "+email);
//			
//			String ops[] = {"Mar del Plata","Pinamar","Necochea","Mar de Ajo","Miramar"};
//			int op = c.print("A dónde viajarás en las vacas? ").menuln(ops);
//			c.println("Qué capo! Your hollidays will be in: "+ops[op]);
//			
//			String x = c.print("Contento? ").input().valid(s->MyString.oneOf(s,"SI","NO")).mask(MyConsole.UPPERCASE).readln();
//			c.println("Veo que "+x+" estás contento...");
//					
//			c.println("Press any key to finish...").pressAnyKey();
//			c.closeAndExit();
//			
//		});
	}
}

