package thejavalistener.fwk.awt.stacknavigator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import thejavalistener.fwk.awt.panel.MyCenterLayout;

public class MyStackNavigatorArrow
{
	public static final int LEFT=1;
	public static final int RIGHT=2;

	private static final int ENABLED=1;
	private static final int DISABLED=2;
	private static final int ROLLOVER=3;

	private ContentPane contentPane;

	private String actionCommand;

	private int direccion;
	private int estado=ENABLED;

	public MyStackNavigatorStyle style;
	
	public MyStackNavigatorArrow(int direccion,String actionCoString,MyStackNavigatorStyle style)
	{
		this.direccion=direccion;
		this.actionCommand=actionCoString;
		this.style = style;
		contentPane=new ContentPane();
		contentPane.setPreferredSize(new Dimension(30,30));
		contentPane.setOpaque(false);

		contentPane.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if(estado!=DISABLED)
				{
					estado=ROLLOVER;
					contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
					contentPane.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if(estado!=DISABLED)
				{
					estado=ENABLED;
					contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					contentPane.repaint();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(estado!=DISABLED&&actionListener!=null)
				{
					estado=ENABLED; // desactivar el rollover visual
					contentPane.setCursor(Cursor.getDefaultCursor()); // volver
																		// al
																		// cursor
																		// normal
					contentPane.repaint();

					actionListener.actionPerformed(new ActionEvent(MyStackNavigatorArrow.this,ActionEvent.ACTION_PERFORMED,actionCoString));
				}
			}
		});
	}

	public void setActionCommand(String ac)
	{
		this.actionCommand=ac;
	}

	public String getActionCommand()
	{
		return this.actionCommand;
	}
	
	public void setBackground(Color c)
	{
		contentPane.setBackground(c);
	}

	public Component c()
	{
		contentPane.setBackground(style.background);
		return contentPane;
	}

	public void setEnabled(boolean enabled)
	{
		this.estado=enabled?ENABLED:DISABLED;
		contentPane.repaint();
	}

	public boolean isEnabled()
	{
		return estado!=DISABLED;
	}

	class ContentPane extends MyCenterLayout
	{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);

//			if(estado==DISABLED)
//			{
//				return; // No dibujar nada
//			}

			Graphics2D g2=(Graphics2D)g.create();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			int w=getWidth();
			int h=getHeight();

			// Colores según estado
			Color colorRelleno;
			switch(estado)
			{
				case ENABLED:
					colorRelleno= style.foreground;
					break;
				case ROLLOVER:
					colorRelleno= style.foregroundRollover;
					break;
				case DISABLED:
					colorRelleno= style.disabled;
					break;
				default:
					colorRelleno= style.background;
					break;
			}
			Color colorBorde=null;
			switch(estado)
			{
				case ENABLED:
					colorBorde= style.border;
					break;
				case ROLLOVER:
					colorBorde= style.borderRollover;
					break;
				case DISABLED:
//					colorBorde= style.background;//new Color(140,140,140);
					colorBorde = style.disabled;
					break;
			}
			;

			// Flecha como polígono
			Polygon flecha=new Polygon();
			if(direccion==LEFT)
			{
				flecha.addPoint(w-10,10);
				flecha.addPoint(10,h/2);
				flecha.addPoint(w-10,h-10);
			}
			else
			{
				flecha.addPoint(10,10);
				flecha.addPoint(w-10,h/2);
				flecha.addPoint(10,h-10);
			}

			// Dibujar flecha con degradado y borde
			GradientPaint grad=new GradientPaint(0,0,colorRelleno,w,h,colorRelleno.brighter(),true);
			g2.setPaint(grad);
			g2.fillPolygon(flecha);

			g2.setColor(colorBorde);
			g2.setStroke(new BasicStroke(1.2f));
			g2.drawPolygon(flecha);

			g2.dispose();
		}
	}

	private ActionListener actionListener;

	public void setActionListener(ActionListener listener)
	{
		this.actionListener=listener;
	}
}