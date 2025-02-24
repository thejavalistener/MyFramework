package thejavalistener.fwk.awt.splitpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Robot;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

import thejavalistener.fwk.awt.MyAwt;

public class MySplitPane 
{
	public static final int HORIZONTAL = JSplitPane.VERTICAL_SPLIT;
	public static final int VERTICAL = JSplitPane.HORIZONTAL_SPLIT;
	
	private JSplitPane splitPane;
	
	private Robot robot;
	
	public MySplitPane(int orientation,Component c1,Component c2)
	{
		splitPane = new JSplitPane(orientation,c1,c2);
		
		splitPane.addPropertyChangeListener("dividerLocation", new EscuchaDivider());
		
		BasicSplitPaneDivider divisorx = (BasicSplitPaneDivider) splitPane.getComponent(2);
		divisorx.setBackground(c1.getBackground());
		divisorx.setForeground(c1.getBackground());
		divisorx.setBorder(null);		
		divisorx.setDividerSize(1);
//		divisorx.addMouseListener(new EscuchaMouse());
		splitPane.setBorder(null);
		
		robot = MyAwt.createRobot();
	}
	
	public void setDividerLocation(int d)
	{
		splitPane.setDividerLocation(d);
	}
	
	
	
	public JSplitPane c()
	{
		return splitPane;
	}
	
	public void setBackground(Color c)
	{
		BasicSplitPaneDivider divisorx = (BasicSplitPaneDivider) splitPane.getComponent(2);
		divisorx.setBackground(c);
		divisorx.setForeground(c);
		splitPane.setBackground(c);
	}
	
	private MySplitPaneListener listener;
	public void setMySplitePaneListener(MySplitPaneListener lst)
	{
		this.listener = lst;
	}
	
	public void setDividerSize(int dividerSize)
	{
		splitPane.setDividerSize(dividerSize);
	}
	
//	class EscuchaMouse extends MouseAdapter
//	{
//		@Override
//		public void mouseEntered(MouseEvent e)
//		{
//			Point locationOnScreen = e.getComponent().getLocationOnScreen();
//	        int absoluteX = locationOnScreen.x + e.getX();
//	        int absoluteY = locationOnScreen.y + e.getY();
//
//	        // Mover el cursor a la posición absoluta
//	        Runnable r = ()->{robot.mouseMove(absoluteX, absoluteY);};
//	        
//	        MyThread.startDelayed(r,50);
//	        
//		}
//		
//		@Override
//		public void mouseExited(MouseEvent e)
//		{
//		}
//	}
	
	class EscuchaDivider implements PropertyChangeListener
	{
        @Override
        public void propertyChange(PropertyChangeEvent evt) 
        {
            int newDividerLocation = (int) evt.getNewValue();
            if( listener!=null ) listener.dividerMoved(newDividerLocation);
        }
	}
}
