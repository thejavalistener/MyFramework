package thejavalistener.fwk.awt.panel;

import java.awt.BorderLayout;
import java.awt.Component;

public class MyInsets extends MyPanel 
{
    public MyInsets(Component c,int top, int left, int bottom, int right) 
    {
    	super(top,left,bottom,right);
    	setLayout(new BorderLayout(0,0));
    	add(c,BorderLayout.CENTER);
    }
}
