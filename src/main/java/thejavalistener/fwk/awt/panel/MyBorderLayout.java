package thejavalistener.fwk.awt.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

public class MyBorderLayout extends MyPanel 
{
    public MyBorderLayout() 
    {
    	this(0,0,0,0);
    }
    
    public MyBorderLayout(Insets insets) 
    {
    	this(insets.top,insets.left,insets.bottom,insets.right);
    }

    public MyBorderLayout(Component cmp,Object borderSection)
    {
    	this(cmp,new Insets(0,0,0,0),borderSection);
    }
    
    public MyBorderLayout(Component cmp,Insets insets,Object borderSection)
    {
    	this(cmp,insets.top,insets.left,insets.bottom,insets.right,borderSection);
    }
    
    public MyBorderLayout(Component cmp,int top, int left, int bottom, int right,Object borderSection)
    {
    	this(top,left,bottom,right);
    	add(cmp,borderSection);
    }
    
    public MyBorderLayout(int top, int left, int bottom, int right) 
    {
    	super(top,left,bottom,right);
    	setLayout(new BorderLayout(0,0));    	
    }
}
