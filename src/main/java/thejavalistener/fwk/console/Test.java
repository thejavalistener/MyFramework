package thejavalistener.fwk.console;

import thejavalistener.fwk.awt.MyAwt;
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
	
//		MyAwt.setWindowsLookAndFeel();
//		
//		JFrame jf = new JFrame();
//		MyConsole c = MyConsole.get();//MyConsole.get(jf);
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
//			c.banner("You are Welcome!");
//			c.println("Plesae, press any key continue...").pressAnyKey();
//			
//			String name = c.print("What's your name? ").readlnString();
//			c.println("Hi, "+name);
//			
//			int tecla = c.println("Presione otra tecla...").pressAnyKey();
//			c.println("Qué caaapppoooo!, tocaste: "+(char)tecla+". ").print("Contento? ").readlnString();
//			
//			int age = c.print("How years old are you ?").readlnInteger();
//			c.println("You are "+age+" years old...");
//			
//			String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//			String email = c.print("What's your email? ").readlnString(emailRegex);
//			c.println("Your email is: "+email);
//			
//			String pwd = c.print("Tu password? ").readlnPassword();
//			c.println("Your password is: "+pwd);
//			
//			int n = c.print("Ingrese un valor: ").readlnInteger();
//			for(int i=1; i<=n; i++)
//			{
//				c.println(i);
//			}
//			
//			String x = c.print("Contento? ").input().valid(s->MyString.oneOf(s,"SI","NO")).mask(MyConsoleBase.UPPERCASE).readln();
//			c.println("Veo que "+x+" estás contento...");
//			
//			
//			c.println("Press any key to finish...").pressAnyKey();
//			c.closeAndExit();
//		});

	}
}

