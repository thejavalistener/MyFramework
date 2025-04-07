package thejavalistener.fwk.awt.splitpane;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

public class MySplitPane 
{
	public static final int HORIZONTAL = JSplitPane.VERTICAL_SPLIT;
	public static final int VERTICAL = JSplitPane.HORIZONTAL_SPLIT;
	
	private JSplitPane splitPane;
	
	public MySplitPane(int orientation,Component c1,Component c2)
	{
		splitPane = new JSplitPane(orientation,c1,c2);
		
		splitPane.addPropertyChangeListener("dividerLocation", new EscuchaDivider());
		
		BasicSplitPaneDivider divisorx = (BasicSplitPaneDivider) splitPane.getComponent(2);
		divisorx.setBackground(c1.getBackground());
		divisorx.setForeground(c1.getBackground());
		divisorx.setBorder(null);		
		divisorx.setDividerSize(1);
		splitPane.setBorder(null);		
	}
	
	public void setDividerColor(Color c)
	{
		BasicSplitPaneDivider divisorx = (BasicSplitPaneDivider) splitPane.getComponent(2);
		divisorx.setBackground(c);
		divisorx.setForeground(c);
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
	
	class EscuchaDivider implements PropertyChangeListener
	{
        @Override
        public void propertyChange(PropertyChangeEvent evt) 
        {
            int newDividerLocation = (int) evt.getNewValue();
            if( listener!=null ) listener.dividerMoved(newDividerLocation);
        }
	}

    private Map<Integer, Integer> dividerPositions = new HashMap<>();

    public void showComponent(int i, boolean b) {
        Component c = splitPane.getComponent(i);
        if (!b) {
            // Guardamos la posición actual del divider asociada al componente
            dividerPositions.put(i, splitPane.getDividerLocation());
        } else if (dividerPositions.containsKey(i)) {
            // Restauramos la posición del divider previamente guardada
            splitPane.setDividerLocation(dividerPositions.get(i));
        }

        // Ocultamos o mostramos el componente
        c.setVisible(b);
    }

}
