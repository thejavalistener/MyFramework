package thejavalistener.fwk.awt.panel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;

public class MyLeftLayout extends MyPanel 
{
    public MyLeftLayout() 
    {
    	this(0,0,0,0);
    }

    public MyLeftLayout(Insets insets) 
    {
    	this(insets.top,insets.left,insets.bottom,insets.right);    	
    }
    
	public MyLeftLayout(int top, int left, int bottom, int right) 
    {
    	super(top,left,bottom,right);
    	setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    }
    
    public MyLeftLayout(Component cmp,Insets insets)
    {
    	this(cmp,insets.top,insets.left,insets.bottom,insets.right);
    }
    
    public MyLeftLayout(Component cmp,int top, int left, int bottom, int right) 
    {
    	this(top,left,bottom,right);
    	add(cmp);
    }
}
