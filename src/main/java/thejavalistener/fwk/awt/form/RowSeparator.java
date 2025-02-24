 package thejavalistener.fwk.awt.form;

 import java.awt.Color;
 import java.awt.Graphics;

 import javax.swing.JPanel;

 public class RowSeparator extends JPanel {

 private double porcentaje;

 public RowSeparator(double p) {
 this.porcentaje = p;
 }

 @Override
 protected void paintComponent(Graphics g)
 {
 super.paintComponent(g);

 // Obtenemos el ancho y alto del panel
 int ancho = getPreferredSize().width;
 int alto = getPreferredSize().height;

 // Calculamos la posición y el tamaño de la línea
 int x1 = (int) (ancho * (1 - porcentaje) / 2);
 int y1 = alto / 2;
 int x2 = (int) (ancho * (1 + porcentaje) / 2);
 int y2 = y1;

 // Dibujamos la línea
 g.setColor(Color.LIGHT_GRAY);
 g.drawLine(x1, y1, x2, y2);
 }
 }
