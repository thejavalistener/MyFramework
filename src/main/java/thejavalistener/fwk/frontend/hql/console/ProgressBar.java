package thejavalistener.fwk.frontend.hql.console;

import java.awt.EventQueue;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import thejavalistener.fwk.console.MyConsoleBase;

public class ProgressBar extends Progress
{
	private int size;
	private int top;

	public ProgressBar(MyConsoleBase c,int size,int top)
	{
		super(c);
		this.size = size;
		this.top = top;
	}
	
	protected void begin()
	{
		curr=0;
		console.print("[");
		for(int i=0; i<size; i++)
		{
			console.print(" ");
		}
		
		console.print("]");
		console.skipBkp(size+1);
		console.cs(console.getStyle().progressStyle);

		initProgressTime=System.currentTimeMillis();
	}

	public void increase()
	{
		curr++;
		double porc=((double)curr/top)*size;

		if(ant!=(int)porc)
		{				
			console.print(console.getStyle().progressFill);
			ant=(int)porc;
		}

		if(ant==size)
		{
			ant=0;
			console.skipFwd();
			console.X();
		}
	}
}







