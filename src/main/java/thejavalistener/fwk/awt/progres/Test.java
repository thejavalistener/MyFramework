package thejavalistener.fwk.awt.progres;

import javax.swing.JFrame;
import javax.swing.UIManager;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.string.MyString;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		// seteo el look and feel
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		JFrame f = new JFrame();
		f.setSize(460,380);
		MyAwt.center(f,null);
		f.setVisible(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		
		MyProgressPane mp=new MyProgressPane("Progreso del proceso","Demo loca",f);
		mp.show();

		try
		{
			int fin = 100;
			for(int i=1; i<=fin; i++)
			{
				int millis=(int)(Math.random()*1000);
				Thread.sleep(millis);
				mp.increaseValue(MyString.generateRandom('A','Z',5,20)); 
				
				if( i%13==0 )
				{
					mp.increaseValueTo(mp.getProgressValue()+30,"Acelerando...");
				}
			}
		}		
		catch(Exception e)
		{
		}
		
		System.out.println(mp.getProgressValue());
	}
}
