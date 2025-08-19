package thejavalistener.fwk.awt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

public class MyTextScroller 
{
	public static final int SCROLLMODE_BOUNCE=0;
	public static final int SCROLLMODE_LOOP=1;

	private JPanel contentPane;
	
	private final JLabel label;
	private final JScrollPane scrollPane;
	private Timer timer;
	private int scrollPos=0;
	private int direccion=1;
	private int scrollMode=SCROLLMODE_BOUNCE;

	private String rawText="";
	private int loopWindowSize=500; // caracteres visibles en modo LOOP

	public MyTextScroller()
	{
		contentPane = new JPanel();
		
		label=new JLabel();
		scrollPane=new JScrollPane(label);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane,BorderLayout.CENTER);
	}
	
	public JPanel c()
	{
		return contentPane;
	}

	public void scroll(String text, int delayMillis, int scrollMode)
	{
		stop();

		this.scrollMode=scrollMode;
		this.rawText=text;
		this.scrollPos=0;
		this.direccion=1;

		if(scrollMode==SCROLLMODE_LOOP)
		{
			// ajustar visibilidad si el texto es muy corto
			if(text.length()<loopWindowSize)
			{
				loopWindowSize=text.length();
			}
		}
		else
		{
			label.setText(text);
			label.setPreferredSize(null); // limpiar cualquier preferencia
											// anterior
		}

		timer=new Timer(delayMillis,(ActionEvent e) -> advanceScroll());
		timer.start();
	}

	private void advanceScroll()
	{
		if(scrollMode==SCROLLMODE_BOUNCE)
		{
			JScrollBar hBar=scrollPane.getHorizontalScrollBar();
			int max=hBar.getMaximum();
			int visible=hBar.getVisibleAmount();
			int limit=max-visible;

			if(limit<=0) return;

			scrollPos+=direccion*2;

			if(scrollPos>=limit)
			{
				scrollPos=limit;
				direccion=-1;
			}
			else if(scrollPos<=0)
			{
				scrollPos=0;
				direccion=1;
			}

			hBar.setValue(scrollPos);

		}
		else if(scrollMode==SCROLLMODE_LOOP)
		{
			String extendedText=rawText+rawText.substring(0,loopWindowSize);

			if(scrollPos>rawText.length())
			{
				scrollPos=0;
			}

			String visible=extendedText.substring(scrollPos,scrollPos+loopWindowSize);
			label.setText(visible);

			// Calcular ancho exacto para evitar truncamiento con ...
			FontMetrics fm=label.getFontMetrics(label.getFont());
			int charWidth=fm.charWidth('W');
			int width=charWidth*loopWindowSize;
			int height=fm.getHeight();
			label.setPreferredSize(new Dimension(width,height));
			label.revalidate();

			scrollPos++;
		}
	}
	
	public JLabel getLabel()
	{
		return label;
	}
	
	public void start()
	{
		if(timer!=null&&!timer.isRunning())
		{
			timer.start();
		}
	}

	public void stop()
	{
		if(timer!=null)
		{
			timer.stop();
		}
	}

	public void setLoopWindowSize(int chars)
	{
		this.loopWindowSize=chars;
	}
}
