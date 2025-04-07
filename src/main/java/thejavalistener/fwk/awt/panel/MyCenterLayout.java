package thejavalistener.fwk.awt.panel;

import java.awt.Component;
import java.awt.FlowLayout;

public class MyCenterLayout extends MyPanel 
{
    public MyCenterLayout()
    {
    	this(0,0,0,0);
    }

    public MyCenterLayout(Component c)
    {
    	this(c,0,0,0,0);
    }
    
    public MyCenterLayout(int top, int left, int bottom, int right) 
    {
    	this(null,top,left,bottom,right);
    }
    
    public MyCenterLayout(Component c,int top, int left, int bottom, int right) 
    {
    	super(top,left,bottom,right);
    	setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    	if( c!=null )
    	{
    		add(c);
    	}
    }
}
