package thejavalistener.fwk.awt.textarea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.dialog.MyDialog;
import thejavalistener.fwk.util.MyColor;
import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.string.ParametrizedString;
import thejavalistener.fwk.util.string.StringCommandIterator;

public class MyTextPane
{
	private JTextPane textPane;
	private boolean listenParametrizedString;
	private boolean enableZoomInOut;

	// estilos
	private Stack<AttributeSet> estilos;
	private StyleContext styleContext=StyleContext.getDefaultStyleContext();

	public MyTextPane()
	{
		this(false,false);
	}

	/**
	 * Escucha ALT+P y abre un form dinámico para que el usuario ingrese valores
	 * con los cuales reemplazar los ${}
	 */
	public MyTextPane(boolean listenPS,boolean enableZoomInOut)
	{
		textPane=new JTextPane();
		textPane.setBorder(null);
		textPane.addKeyListener(new EscuchaALTP());
		setListenParametrizedString(listenPS);
		setEnableZoomInOut(enableZoomInOut);
		estilos=new Stack<>();
		estilos.push(textPane.getCharacterAttributes());
	}

	public Point getCaretCoords()
	{
		try
		{
			Point frameCoords=null;

			int caretPos=textPane.getCaretPosition();
			Shape caretCoordsShape=textPane.modelToView2D(caretPos);
			if(caretCoordsShape!=null)
			{
				Rectangle2D caretCoords=caretCoordsShape.getBounds2D();
				frameCoords=SwingUtilities.convertPoint(textPane,(int)caretCoords.getX(),(int)caretCoords.getY(),textPane);
			}

			return frameCoords;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void setEditable(boolean b)
	{
		textPane.setEditable(b);
	}

	public void setEnableZoomInOut(boolean b)
	{
		this.enableZoomInOut=b;
	}

	/** Habilita o deshabilita la escucha de ALT+P */
	public void setListenParametrizedString(boolean listenPS)
	{
		this.listenParametrizedString=listenPS;
	}

	public JTextPane c()
	{
		return textPane;
	}

	public int getCaretColumnPosition() {
	    String txt = getText();
	    int i = getCaretPosition() - 1;
	    int col = 0;

	    while(i >= 0) {
	        char c = txt.charAt(i);
	        if(c == '\r' || c == '\n') {
	            break;
	        }
	        col++;
	        i--;
	    }
	    
	    return col;
	}
	
	public void deleteText(int from,int to)
	{
		try {
		    Document doc = textPane.getDocument();
		    doc.remove(from, to - from);
		    textPane.setCaretPosition(from);
		} catch (BadLocationException e) {
		    e.printStackTrace();
		}
	}

	
	public void setFont(Font f)
	{
		textPane.setFont(f);
	}

	/** Retorna el contenido del JTextPane sin \r. Sólo con \n en los saltos de línea */
	public String getText()
	{
		return textPane.getText().replace("\r","");
	}

	public void setText(String txt)
	{
		textPane.setText(txt);
	}

	/** Setea el color de fondo de JTextPane */
	public void setBackground(Color c)
	{
		textPane.setBackground(c);
	}

	/** Setea el color de foreground del JTextPane */
	public void setForeground(Color c)
	{
		textPane.setForeground(c);
	}

	/** Asigna "" al contenido del JTextPane */
	public void clear()
	{
		textPane.setText("");
	}

	public void addKeyListener(KeyListener lst)
	{
		textPane.addKeyListener(lst);
	}

	public void removeKeyListener(KeyListener lst)
	{
		textPane.removeKeyListener(lst);
	}

	public void addMouseListener(MouseListener lst)
	{
		textPane.addMouseListener(lst);
	}

	public void requestFocus()
	{
		textPane.requestFocus();
	}

	/**
	 * Establece la ubicación del cursor SIN TENER EN CUENTA los \r. Es decir,
	 * considerando que cada salto de línea representa un único carácter. Por
	 * esto, en la cadena: "Hola\r\ncomo estás?", para ubicar el cursor al
	 * inicio de la c la posición será: 5. [H][o][l][a][\r\n].
	 */
//	public void setCaretPosition(int pos)
//	{
//		textPane.setCaretPosition(pos);
//	}

	public void setCaretPositionAtEndOfText()
	{
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
	
	

	/**
	 * Retorna la ubicación del cursor SIN TENER EN CUENTA los \r. Es decir,
	 * considerando que cada salto de línea representa un único carácter. Por
	 * esto, en la cadena: "Hola\r\ncomo estás?", si el cursor estuviera al
	 * inicio de la c la posición que retornaría será: 5. [H][o][l][a][\r\n].
	 */
	public int getCaretPosition()
	{
		return textPane.getCaretPosition();
	}

	public void setCaretColor(Color c)
	{
		textPane.setCaretColor(c);
	}

	public String getSelectedText()
	{
		return textPane.getSelectedText();
	}

	public void setSelectedText(int start, int end)
	{
		textPane.select(start,end);
	}

	public void setEnabled(boolean b)
	{
		textPane.setEnabled(b);
	}

	public boolean isEnabled()
	{
		return textPane.isEnabled();
	}

	public int getLen()
	{
		return getText().length();
	}

	public void appendText(String txt)
	{
		insertText(txt,getLen());
	}
	
	public void scrollToFindText(String txt)
	{
		try
		{
			int position=textPane.getText().indexOf(txt);
			if(position>=0)
			{
				textPane.setCaretPosition(position);
				Rectangle viewRect=textPane.modelToView2D(position).getBounds();
				int halfHeight=textPane.getVisibleRect().height/2;
				int startY=Math.max(viewRect.y-halfHeight,0);
				int maxScrollY=textPane.getHeight()-textPane.getVisibleRect().height;
				viewRect.y=Math.min(startY,maxScrollY);
				textPane.scrollRectToVisible(viewRect);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void appendFormatedText(String txt)
	{
		StringBuffer sb=new StringBuffer();
		StringCommandIterator sci=new StringCommandIterator(txt);
		while(sci.hasNext())
		{
			if(sci.next(sb))
			{
				_invocarComando(sb.toString());
			}
			else
			{
				insertText(sb.toString(),getLen());
			}
		}
	}
	
	public void setCaretPosition(int pos)
	{
		textPane.setCaretPosition(pos);
	}

	public void insertText(String txt, int pos)
	{
		try
		{
			StyledDocument doc=textPane.getStyledDocument();
			doc.insertString(pos,txt,estilos.peek());
			
			textPane.setCaretPosition(pos+txt.length());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void _invocarComando(String cmdName)
	{
		if(cmdName.equals("b"))
		{
			b();
			return;
		}
		if(cmdName.equals("i"))
		{
			i();
			return;
		}
		if(cmdName.equals("X"))
		{
			X();
			return;
		}
		if(cmdName.startsWith("x"))
		{
			int n=cmdName.length();
			for(int i=0; i<n; i++)
			{
				x();
			}
			return;
		}

		fg(cmdName);
	}

	public void setFormatedText(String txt)
	{
		textPane.setText("");
		appendFormatedText(txt);
	}

	public void insertFormatedText(String txt, int pos)
	{
		StringBuffer sb=new StringBuffer();
		StringCommandIterator sci=new StringCommandIterator(txt);
		while(sci.hasNext())
		{
			if(sci.next(sb))
			{
				_invocarComando(sb.toString());
			}
			else
			{
				insertText(sb.toString(),pos);
			}
		}
	}

	/** Foreground */
	public MyTextPane fg(Color c)
	{
		AttributeSet att=styleContext.addAttribute(estilos.peek(),StyleConstants.Foreground,c);
		estilos.push(att);
		textPane.setCharacterAttributes(att,false);
		return this;
	}

	public MyTextPane fg(String color)
	{
		return fg(MyColor.fromString(color));
	}

	/** Background */
	public MyTextPane bg(Color c)
	{
		AttributeSet att=styleContext.addAttribute(estilos.peek(),StyleConstants.Background,c);
		estilos.push(att);
		textPane.setCharacterAttributes(att,false);
		return this;
	}

	public MyTextPane bg(String color)
	{
		return bg(MyColor.fromString(color));
	}

	/** Background y foreground */
	public MyTextPane bf(Color bgCol, Color fgCol)
	{
		bg(bgCol);
		fg(fgCol);
		return this;
	}

	// public void removeStyle(StyleConstants att)
	// {
	// MyLog.out("OJOOOOO.... POR AHÍ DEBO VOLAR ESTE MÉTODO");
	// AttributeSet currentAttributes=estilos.pop();
	// MutableAttributeSet newAttributes=new
	// SimpleAttributeSet(currentAttributes);
	// newAttributes.removeAttribute(att);
	// estilos.push(newAttributes);
	// textPane.setCharacterAttributes(newAttributes,false);
	// }
	//
	public MyTextPane bf(String bgCol, String fgCol)
	{
		return bf(MyColor.fromString(bgCol),MyColor.fromString(fgCol));
	}

	/** Bold (negrita) */
	public MyTextPane b()
	{
		AttributeSet att=styleContext.addAttribute(estilos.peek(),StyleConstants.Bold,true);
		estilos.push(att);
		textPane.setCharacterAttributes(att,false);
		return this;
	}

	/** Italic (cursiva) */
	public MyTextPane i()
	{
		AttributeSet att=styleContext.addAttribute(estilos.peek(),StyleConstants.Italic,true);
		estilos.push(att);
		textPane.setCharacterAttributes(att,false);
		return this;
	}

	/** Remueve todos los estilos apilados */
	public MyTextPane X()
	{
		return x(estilos.size()-1);
	}

	/** Remueve el último estilo apiladi */
	public MyTextPane x()
	{
		return x(1);
	}

	/** Remueve los últimos n estilos apilados */
	public MyTextPane x(int n)
	{
		for(int i=0; i<n; i++)
			textPane.setCharacterAttributes(estilos.pop(),true);
		return this;
	}
	
	
	

	class EscuchaALTP implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			// CTRL +/-
			if(enableZoomInOut&&e.isControlDown()&&e.getKeyChar()=='+'||e.getKeyChar()==31)
			{
				int factor=e.getKeyChar()=='+'?1:-1;
				MyAwt.fontIncrease(textPane,factor);
				MyAwt.fontIncrease(textPane,factor);
			}

			// ALT+P
			if(listenParametrizedString&&e.isAltDown()&&(e.getKeyChar()=='p'||e.getKeyChar()=='P'))
			{
				ParametrizedString ps=new ParametrizedString(getText());
				if(ps.getParameters().size()>0)
				{
					MyParameterPane mp=new MyParameterPane(ps);
					MyDialog dlg=new MyDialog(MyAwt.getParent(textPane,JFrame.class),mp,"");
					Object x=dlg.configurator().pack().centerH(100).apply();
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
	}

	public void replaceText(String txt, int from, int to)
	{
		deleteText(from,to);
		insertText(txt,from);
	}
}
