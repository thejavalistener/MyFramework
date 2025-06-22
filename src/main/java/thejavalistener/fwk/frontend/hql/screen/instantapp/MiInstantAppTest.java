package thejavalistener.fwk.frontend.hql.screen.instantapp;

import javax.swing.JCheckBox;

import thejavalistener.fwk.util.MyColor;
import thejavalistener.fwk.util.MyLog;

public class MiInstantAppTest
{
	public static void main(String[] args)
	{
		MyInstantApp x = new MyInstantApp();
		x.addButton("Capo","capo");
		x.addButton("Total","total");
		x.addButton("Loccooo","loco");
		x.addScreenPanel("1",MiPanel1.class);
		x.addScreenPanel("2",MiPanel2.class);
		x.setSelected(MiPanel1.class);
		
		x.init();
		
		x.size(450,300).show();
	}
	
	static class MiPanel1 extends MyInstantAppScreen
	{
		private JCheckBox chb;
		
		@Override
		public void onButtonPressed(String action)
		{
			System.out.println("p1 ----------> "+action);
		}
		
		public MiPanel1()
		{
			setBackground(MyColor.random());
			
			chb = new JCheckBox("Evitar cambio...");
			add(chb);
		}

		@Override
		public void init(Object... args)
		{
			MyLog.println();
		}

		

		@Override
		public void dataUpdated()
		{
		}

		@Override
		public void start()
		{
			MyLog.println();
		}

		@Override
		public boolean stop()
		{
			MyLog.println();
			return !chb.isSelected();
		}
	}
	
	static class MiPanel2 extends MyInstantAppScreen
	{
		public MiPanel2()
		{
			setBackground(MyColor.random());
		}
		
		@Override
		public void onButtonPressed(String action)
		{
			System.out.println("p2 ----------> "+action);
		}
		


		@Override
		public void init(Object... args)
		{
			MyLog.println();
		}

		@Override
		public void dataUpdated()
		{
			MyLog.println();
		}

		@Override
		public void start()
		{
			MyLog.println();
		}

		@Override
		public boolean stop()
		{
			MyLog.println();
			return true;
		}
	}

}
