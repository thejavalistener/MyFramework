package thejavalistener.fwk.awt.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.string.MyString;

public class MyPanel extends JPanel {

    private int topMargin;
    private int leftMargin;
    private int bottomMargin;
    private int rightMargin;
    
    private static int globalDebugId = 1;
    public static boolean DEBUG_MODE=false;
    
    private boolean allowChangeBackground = true;

    public MyPanel(int top, int left, int bottom, int right) 
    {
        this.topMargin = top;
        this.leftMargin = left;
        this.bottomMargin = bottom;
        this.rightMargin = right;
        setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        if( DEBUG_MODE )
        {
        	setBackground(MyAwt.randomColor());
        	
        	String thisPkg = getClass().getPackageName();
    		StackTraceElement ste = MyLog.currStackTrace(d->d.getClassName().startsWith(thisPkg));
    		String sOwner = MyString.substringAfterLast(ste.getClassName(),".");
    		int line = ste.getLineNumber();
        	globalDebugId++;
        	String toolTip = getClass().getSimpleName()+" ("+globalDebugId+"). In: "+sOwner+"#"+line;
        	setToolTipText(toolTip);
        }
    }
    
    public void setAllowChangeBackground(boolean b)
    {
    	allowChangeBackground = b;
    }
    
    public void requestChangeBackground(Color bg)
    {
    	if( allowChangeBackground )
    	{
    		setBackground(bg);
    	}
    }
    
    public boolean allowChangeBackground() 
    {
    	return allowChangeBackground;
    }

    
    public void setInsets(Insets insets) {
        this.topMargin = insets.top;
        this.leftMargin = insets.left;
        this.bottomMargin = insets.bottom;
        this.rightMargin = insets.right;
        setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
        revalidate(); // Asegura que el layout se actualice
        repaint();    // Redibuja el componente
    }

    public final static int NO_BORDER = 0;
    public final static int TOP_BORDER = 1;
    public final static int LEFT_BORDER = 2;
    public final static int BOTTOM_BORDER = 4;
    public final static int RIGHT_BORDER = 8;
    
//    public void drawLine(int border, int px, Color c) {
//        Graphics g = getGraphics();
//        if (g == null) return; // Aseguramos que el objeto Graphics no sea nulo
//
//        // Si border es 0, limpiamos el componente y salimos
//        if (border == 0) {
//            repaint(); // Redibuja el componente, eliminando cualquier dibujo previo
//            return;
//        }
//        
//        g.setColor(c); // Establecemos el color de la línea
//
//        int width = getWidth();
//        int height = getHeight();
//
//        if ((border & 1) == 1) { // Borde superior
//            g.fillRect(0, 0, width, px);
//        }
//        if ((border & 2) == 2) { // Borde izquierdo
//            g.fillRect(0, 0, px, height);
//        }
//        if ((border & 4) == 4) { // Borde inferior
//            g.fillRect(0, height - px, width, px);
//        }
//        if ((border & 8) == 8) { // Borde derecho
//            g.fillRect(width - px, 0, px, height);
//        }
//        revalidate();
//    }

    
    private int lineBorder;
    private int linePx;
    private Color lineColor;
    public void drawLine(int border, int px, Color c) {
        this.lineBorder = border;
        this.linePx = px;
        this.lineColor = c;
        repaint(); // ¡Esto sí llama a paintComponent!
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (lineBorder == 0 || lineColor == null) return;

        g.setColor(lineColor);
        int width = getWidth();
        int height = getHeight();

        if ((lineBorder & 1) == 1) { // Borde superior
            g.fillRect(0, 0, width, linePx);
        }
        if ((lineBorder & 2) == 2) { // Borde izquierdo
            g.fillRect(0, 0, linePx, height);
        }
        if ((lineBorder & 4) == 4) { // Borde inferior
            g.fillRect(0, height - linePx, width, linePx);
        }
        if ((lineBorder & 8) == 8) { // Borde derecho
            g.fillRect(width - linePx, 0, linePx, height);
        }
    }
    
}
