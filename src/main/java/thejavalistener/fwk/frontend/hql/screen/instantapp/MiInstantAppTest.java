package thejavalistener.fwk.frontend.hql.screen.instantapp;

import javax.swing.JCheckBox;

import thejavalistener.fwk.awt.panel.MyPanel;
import thejavalistener.fwk.util.MyColor;
import thejavalistener.fwk.util.MyLog;

public class MiInstantAppTest
{
	public static void main(String[] args)
	{
		MyInstantApp x = new MyInstantApp();
		x.addScreenPanel("1",MiPanel1.class);
		x.addScreenPanel("2",MiPanel2.class);
		x.setSelected(0);
		
		x.init();
		
		x.size(450,300).show();
	}
	
	static class MiPanel1 extends MyPanel implements MyInstantAppScreen
	{
		private JCheckBox chb;
		
		public MiPanel1()
		{
			super(1,1,1,1);
			setBackground(MyColor.random());
			
			chb = new JCheckBox("Evitar cambio...");
			add(chb);
		}

		@Override
		public void init(MyInstantApp app, Object... args)
		{
			MyLog.println();
		}

		@Override
		public void onAccept()
		{
		}

		@Override
		public boolean onClose()
		{
			return true;
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
	
	static class MiPanel2 extends MyPanel implements MyInstantAppScreen
	{
		public MiPanel2()
		{
			super(1,1,1,1);
			setBackground(MyColor.random());
		}

		@Override
		public void init(MyInstantApp app, Object... args)
		{
			MyLog.println();
		}

		@Override
		public void onAccept()
		{
			MyLog.println();
		}

		@Override
		public boolean onClose()
		{
			MyLog.println();
			return true;
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
