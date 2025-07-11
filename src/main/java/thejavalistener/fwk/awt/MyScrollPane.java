package thejavalistener.fwk.awt;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollPane extends JScrollPane
{
	public MyScrollPane(Container viewPort)
	{
		super(viewPort);
		setBorder(null);
	}
	
	public void setBackground(Color bg)
	{
		super.setBackground(bg);
	}
	
	public void configureScrollBars(int w,Color fg)
	{
		configureScrollBars(w,getBackground(),fg);
	}
	
	public void repos()
	{
		getVerticalScrollBar().setValue(0);
		getHorizontalScrollBar().setValue(0);
	}
	
	public void configureScrollBars(int w,Color bg,Color fg)
	{
		UIManager.put("ScrollBar.width", w);
		getVerticalScrollBar().setUI(new MiBasicScrollBarUI(bg,fg));
		getHorizontalScrollBar().setUI(new MiBasicScrollBarUI(bg,fg));
		
		revalidate();
	}

    private JButton createZeroButton() 
    {
	    JButton jbutton = new JButton();
	    jbutton.setPreferredSize(new Dimension(0, 0));
	    jbutton.setMinimumSize(new Dimension(0, 0));
	    jbutton.setMaximumSize(new Dimension(0, 0));
	    return jbutton;
    }
    
    class MiBasicScrollBarUI extends BasicScrollBarUI
    {	    	
    	private Color bg,fg;
    	
    	public MiBasicScrollBarUI(Color bg, Color fg)
		{
    		this.bg = bg;
    		this.fg = fg;
		}
    	
    	
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
              return createZeroButton();
        }
        @Override 
        protected void configureScrollBarColors(){
            this.thumbColor = fg;
            this.thumbDarkShadowColor = fg;
            this.thumbHighlightColor = fg;
            this.thumbLightShadowColor = fg;
        }

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		{
			g.setColor(bg);
			super.paintTrack(g,c,trackBounds);
		}
    }

}


/*
package app.screen.thum;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollPane2 extends JScrollPane
{
	public MyScrollPane2(Container viewPort,int width,Color bg,Color fg)
	{
		super(viewPort);
		getVerticalScrollBar().setUI(new MiBasicScrollBarUI(bg,fg));
		getHorizontalScrollBar().setUI(new MiBasicScrollBarUI(bg,fg));
		
		setBackground(bg);
		UIManager.put("ScrollBar.width", width);
	}

    private JButton createZeroButton() 
    {
	    JButton jbutton = new JButton();
	    jbutton.setPreferredSize(new Dimension(0, 0));
	    jbutton.setMinimumSize(new Dimension(0, 0));
	    jbutton.setMaximumSize(new Dimension(0, 0));
	    return jbutton;
    }
    
    class MiBasicScrollBarUI extends BasicScrollBarUI
    {	    	
    	private Color bg,fg;
    	
    	public MiBasicScrollBarUI(Color bg, Color fg)
		{
    		this.bg = bg;
    		this.fg = fg;
		}
    	
    	
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
              return createZeroButton();
        }
        @Override 
        protected void configureScrollBarColors(){
            this.thumbColor = fg;
            this.thumbDarkShadowColor = fg;
            this.thumbHighlightColor = fg;
            this.thumbLightShadowColor = fg;
        }

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		{
			g.setColor(bg);
			super.paintTrack(g,c,trackBounds);
		}
    }

}

*/
