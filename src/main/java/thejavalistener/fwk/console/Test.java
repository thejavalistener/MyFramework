package thejavalistener.fwk.console;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.MyThread;
import thejavalistener.fwk.util.UTime;
import thejavalistener.fwk.util.string.MyString;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		MyAwt.setWindowsLookAndFeel();		
		MyConsole c = MyConsole.io;
		
		MyConsole.io.banner("You are Welcome!");
		MyConsole.io.println("Plesae, press any key continue...").pressAnyKey();

		Progress p = c.progressBar(20,100);
		p.execute(()->{
			for(int i=0; i<100; i++)
			{
				MyThread.randomSleep(100);
				p.increase();
			}});
		
		c.println();
		
		String pwd = c.print("Password:").readlnPassword();
		c.println("Tu pwd es:["+pwd+"]");			


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
//		c.closeAndExit();
//
//		System.out.println("-----------------------------------------------------------");
//		
//		MyAwt.setWindowsLookAndFeel();
//		
//		JFrame jf = new JFrame();
//		MyConsole c = MyConsole.io;//MyConsole.get(jf);
//
//		JButton b = new JButton("Start Console");
//		b.addActionListener(e->{
//			
//			MyConsole.io.banner("You are Welcome!");
//			MyConsole.io.println("Plesae, press any key continue...").pressAnyKey();
//	
//			Progress p = c.progressBar(20,100);
//			p.execute(()->{
//				for(int i=0; i<100; i++)
//				{
//					MyThread.randomSleep(100);
//					p.increase();
//				}});
//			
//			b.setText("OK!!");
//			
//			c.println();
//			
//			String pwd = c.print("Password:").readlnPassword();
//			c.println("Tu pwd es:["+pwd+"]");			
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
////			c.closeAndExit();
//
//		});
//	
//		jf.add(b,BorderLayout.WEST);
//		jf.setSize(300,300);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setVisible(true);
	}
}

