package thejavalistener.fwk.console;

public class ProgressMeter extends Progress
{
	private int top;
		
	public ProgressMeter(MyConsoleBase c,int top)
	{
		super(c);
	}
	
	@Override
	protected void begin()
	{
		initProgressTime=System.currentTimeMillis();
		curr=0;
		console.print("00%");
		console.skipBkp(3);
	}

	public void increase()
	{
		curr++;
		int porc=(int)Math.floor(((double)curr/top)*100);

		if(porc<100)
		{
			console.print((porc<10?"0":"")+porc+"%").skipBkp(3);
		}
		else
		{
			console.print("100").print("%");
			console.skipFwd();
		}
	}

}









