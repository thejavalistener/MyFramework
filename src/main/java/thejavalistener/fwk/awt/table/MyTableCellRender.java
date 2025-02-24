package thejavalistener.fwk.awt.table;

import java.awt.Graphics;

import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRender extends DefaultTableCellRenderer
{
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		Graphics2D g2=(Graphics2D)g;
//		g2.setStroke(new BasicStroke(2)); // Cambia el grosor aquí
//		g2.setColor(Color.BLACK); // Cambia el color aquí
//		g2.drawLine(getWidth()-1,0,getWidth()-1,getHeight());
//		System.out.println( getClass() );		
	}
}
