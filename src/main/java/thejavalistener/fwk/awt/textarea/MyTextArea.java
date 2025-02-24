package thejavalistener.fwk.awt.textarea;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.dialog.MyDialog;
import thejavalistener.fwk.util.string.ParametrizedString;

public class MyTextArea implements KeyListener
{
	private JTextArea textArea;
	private boolean listenParametrizedString;
	
	public MyTextArea()
	{
		this(false);
	}
	
	public MyTextArea(boolean listenPS)
	{
		textArea=new JTextArea();
		textArea.addKeyListener(this);
		setListenParametrizedString(listenPS);
	}
	
	public void setListenParametrizedString(boolean listenPS)
	{
		this.listenParametrizedString = listenPS;
	}

	public void setLineWrap(boolean b)
	{
		textArea.setLineWrap(b);
	}

	public JTextArea c()
	{
		return textArea;
	}

	public void setFont(Font f)
	{
		textArea.setFont(f);
	}

	public String getText()
	{
		return textArea.getText();
	}

	public void setText(String txt)
	{
		textArea.setText(txt);
	}

	public void setBackground(Color c)
	{
		textArea.setBackground(c);
	}

	public void setForeground(Color c)
	{
		textArea.setForeground(c);
	}

	public void append(String txt)
	{
		textArea.append(txt);
	}

	public void insert(String txt, int pos)
	{
		textArea.insert(txt,pos);
	}

	public void clear()
	{
		textArea.setText("");
	}

	public void addKeyListener(KeyListener lst)
	{
		textArea.addKeyListener(lst);
	}

	public void keyPressed(KeyEvent e)
	{
		// CTRL +/-
		if(e.isControlDown()&&e.getKeyChar()=='+'||e.getKeyChar()==31)
		{
			int factor=e.getKeyChar()=='+'?1:-1;
			MyAwt.fontIncrease(textArea,factor);
			MyAwt.fontIncrease(textArea,factor);
		}
		
		// ALT+P
		if(listenParametrizedString && e.isAltDown()&&e.getKeyChar()=='p'||e.getKeyChar()=='P')
		{
			ParametrizedString ps = new ParametrizedString(getText());
			if( ps.getParameters().size()>0 )
			{
				MyParameterPane mp = new MyParameterPane(ps);
				MyDialog dlg = new MyDialog(MyAwt.getParent(textArea,JFrame.class),mp,"");
				Object x = dlg.configurator().pack().centerH(100).apply();
				setText(x.toString());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	public void requestFocus()
	{
		textArea.requestFocus();
	}

	public void setCaretPosition(int pos)
	{
		textArea.setCaretPosition(pos);
	}

	public String getSelectedText()
	{
		return textArea.getSelectedText();
	}

	public int getCaretPosition()
	{
		return textArea.getCaretPosition();
	}

	public void select(int start, int end)
	{
		textArea.select(start,end);
	}

	public void setEnabled(boolean b)
	{
		textArea.setEnabled(b);
	}

	public boolean isEnabled()
	{
		return textArea.isEnabled();
	}
}
