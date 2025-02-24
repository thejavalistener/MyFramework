package thejavalistener.fwk.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Separator extends JPanel {

    private double porcentaje;

    public Separator(double p) {
        this.porcentaje = p;
        setPreferredSize(new Dimension(getPreferredSize().width,1));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {	
        super.paintComponent(g);

        // Obtenemos el ancho y alto del panel
        int ancho = getWidth();
        int alto = getHeight();

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
