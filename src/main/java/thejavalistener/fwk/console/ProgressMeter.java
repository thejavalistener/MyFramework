package thejavalistener.fwk.console;

import java.awt.Graphics;

import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

import thejavalistener.fwk.util.MyThread;

public class ProgressMeter extends Progress
{
	public ProgressMeter(MyConsoleBase c,int top)
	{
		super(c);
		super.top = top;

	}
	
	@Override
	protected void begin()
	{
		initProgressTime=System.currentTimeMillis();
		curr=0;
		console.print("00%");
		console.skipBkp(3);
		
	}

	public void increase(String mssg)
	{
		curr++;
		int porc=(int)Math.floor(((double)curr/top)*100);

		if(porc<100)
		{
			console.print((porc<10?"0":"")+porc+"%");
			int pos = console.getCaretPosition();
			console.print(" "+mssg);
			console.setCaretPosition(pos);
			console.skipBkp(3);
		}
		else
		{
			console.print("100% ");//.print("%");
			console.skipFwd();
		}		
	}

	public void increase()
	{
		curr++;
		int porc=(int)Math.floor(((double)curr/top)*100);

		if(porc<100)
		{
			console.print((porc<10?"0":"")+porc+"%");			
			console.skipBkp(3);
		}
		else
		{
			console.print("100").print("%");
			console.skipFwd();
		}
	}
	
	public static void main(String[] args)
	{
		MyConsole c = MyConsole.io;
		c.print("Procesando: ");
		
		Progress p = c.progressMeter(100);
		p.execute(()->{
			for(int i=0; i<100; i++)
			{
				p.increase();
				MyThread.randomSleep(200);
			}
		});
	}

}


