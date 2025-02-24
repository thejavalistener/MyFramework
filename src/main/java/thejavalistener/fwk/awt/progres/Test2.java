package thejavalistener.fwk.awt.progres;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyRandomColorPanel;
import thejavalistener.fwk.awt.testui.MyTestUI;
import thejavalistener.fwk.util.MyThread;
import thejavalistener.fwk.util.string.MyString;

public class Test2
{
	public static void main(String[] args) throws Exception
	{
		MyAwt.setWindowsLookAndFeel();

		MyTestUI mt = MyTestUI.test(new MyRandomColorPanel());		
		MyProgressPane mp=new MyProgressPane("Progreso del proceso","Demo loca",null);
		
		mt.addButton("Start",l->new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int fin = 100;
				for(int i=1; i<=fin; i++)
				{
					int millis=(int)(Math.random()*1000);
					MyThread.sleep(millis);

					try
					{
						mp.increaseValue(MyString.generateRandom('A','Z',5,20)); 
						
						if( i%13==0 )
						{
							mp.increaseValueTo(mp.getProgressValue()+30,"Acelerando...");
						}						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			}
		}).start());

		mt.run();
		
		System.out.println(mp.getProgressValue());
	}
}
