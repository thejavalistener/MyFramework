package thejavalistener.fwk.awt.panel;

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
    private int localDebugId;
    public static boolean DEBUG_MODE=false;

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
    
    public void setInsets(Insets insets) {
        this.topMargin = insets.top;
        this.leftMargin = insets.left;
        this.bottomMargin = insets.bottom;
        this.rightMargin = insets.right;
        setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));
        revalidate(); // Asegura que el layout se actualice
        repaint();    // Redibuja el componente
    }

//	public void setDebugName(String debugName)
//	{
//		setDebugName(debugName,null);
//	}
//	public void setDebugName(String debugName,Object owner)
//	{
//		String sOwner = owner!=null?owner.getClass().getSimpleName()+".":"";
//    	String toolTip = sOwner+debugName+" ("+localDebugId+")";
//    	setToolTipText(toolTip);
//	}
	
//	public void setDebugName(String debugName)
//	{
//		StackTraceElement ste = MyLog.currStackTrace();
//		String sClazz = MyString.substringAfterLast(ste.getClass().getSimpleName(),".");
//		String sMetho = ste.getMethodName();
//		int iLine = ste.getLineNumber();
//    	String toolTip = sOwner+debugName+" ("+localDebugId+")";
//    	setToolTipText(toolTip);
//	}


    
}
