package thejavalistener.fwk.awt.panel;

import java.awt.Component;
import java.awt.FlowLayout;

public class MyRightLayout extends MyPanel 
{
    public MyRightLayout()
    {
    	this(null,0,0,0,0);
    }

	public MyRightLayout(Component c)
    {
    	this(c,0,0,0,0);
    }
    
    public MyRightLayout(int top, int left, int bottom, int right) 
    {
    	this(null,top,left,bottom,right);
    }
    
    public MyRightLayout(Component c,int top, int left, int bottom, int right) 
    {
    	super(top,left,bottom,right);
    	setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
    	if( c!=null )
    	{
    		add(c);
    	}
    }
}
