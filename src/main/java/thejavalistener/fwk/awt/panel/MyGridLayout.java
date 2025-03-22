package thejavalistener.fwk.awt.panel;

import java.awt.Insets;

public class MyGridLayout extends MyPanel 
{
	private GridLayout2 gridLayout;


    public MyGridLayout()
    {
    	this(0,0,0,0,0,0);
    }

	public MyGridLayout(int rows,int cols)
    {
    	this(rows,cols,0,0,0,0);
    }
    
    public MyGridLayout(Insets insets)
    {
    	this(insets.top,insets.left,insets.bottom,insets.right);
    }
    
    public MyGridLayout(int rows,int cols,Insets insets)
    {
    	this(rows,cols,insets.top,insets.left,insets.bottom,insets.right);
    }

    public MyGridLayout(int top, int left, int bottom, int right)
    {
    	this(1,0,top,left,bottom,right);
    }
    public MyGridLayout(int rows,int cols,int top, int left, int bottom, int right) 

    {
    	super(top,left,bottom,right);
    	setLayout(gridLayout = new GridLayout2(rows,cols,0,0));
    	setGridDimensions(rows,cols);
    }
    
    public void setGridDimensions(int rows,int cols)
    {
    	gridLayout.setColumns(cols);
    	gridLayout.setRows(rows);
    }
}
