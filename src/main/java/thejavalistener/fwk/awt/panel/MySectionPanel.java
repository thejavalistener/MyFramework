package thejavalistener.fwk.awt.panel;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//public class MySectionPanel
//{
//	JPanel contentPane;
//	JPanel left,center,right;
//	JPanel leftWrapper,centerWrapper,rightWrapper;
//
//	public MySectionPanel()
//	{
//		left=new MyLeftLayout();
//		center=new MyCenterLayout();
//		right=new MyRightLayout();
//
//		// Envolver cada panel en un contenedor para centrar verticalmente
//		leftWrapper=createVerticalCenterWrapper(left);
//		centerWrapper=createVerticalCenterWrapper(center);
//		rightWrapper=createVerticalCenterWrapper(right);
//
//		// Configurar el layout principal como GridBagLayout
//		contentPane=new JPanel(new GridBagLayout());
//		GridBagConstraints constraints=new GridBagConstraints();
//		constraints.gridy=0; // Todos los paneles en la misma fila
//		constraints.weightx=1.0; // Distribuir espacio horizontal
//		constraints.fill=GridBagConstraints.BOTH; // Expandir horizontalmente
//
//		// Agregar los paneles al layout
//		constraints.gridx=0; // Primera columna
//		contentPane.add(leftWrapper,constraints);
//
//		constraints.gridx=1; // Segunda columna
//		contentPane.add(centerWrapper,constraints);
//
//		constraints.gridx=2; // Tercera columna
//		contentPane.add(rightWrapper,constraints);		
//		
//		equalizeHeights();
//	}
//
//	// Método para crear un contenedor que centre su contenido verticalmente
//	private JPanel createVerticalCenterWrapper(JPanel panel)
//	{
//		JPanel wrapper=new JPanel();
//		wrapper.setLayout(new BoxLayout(wrapper,BoxLayout.Y_AXIS)); // Centrar
//																	// verticalmente
//		wrapper.add(Box.createVerticalGlue()); // Espacio flexible arriba
//		wrapper.add(panel); // Tu panel real
//		wrapper.add(Box.createVerticalGlue()); // Espacio flexible abajo
//		return wrapper;
//	}
//
//	public MySectionPanel addLeft(Component c)
//	{
//		left.add(c);
//		return this;
//	}
//
//	public MySectionPanel addCenter(Component c)
//	{
//		center.add(c);
//		return this;
//	}
//
//	public MySectionPanel addRight(Component c)
//	{
//		right.add(c);
//		return this;
//	}
//
//	public Component c()
//	{
//		return contentPane;
//	}
//
//	public void setBackground(Color background)
//	{
//		contentPane.setBackground(background);
//		left.setBackground(background);
//		center.setBackground(background);
//		right.setBackground(background);
//		leftWrapper.setBackground(background);
//		centerWrapper.setBackground(background);
//		rightWrapper.setBackground(background);
//
//	}
//
//	public static void mainx(String[] args)
//	{
//		MySectionPanel p=new MySectionPanel();
//		p.addLeft(new JButton("Izq1")).addLeft(new JButton("Izq2"));
//		p.addCenter(new JButton("center"));
//		p.addRight(new JButton("right1")).addRight(new JButton("right2"));
//
//		JFrame frame=new JFrame("MySectionPanel Test");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(800,400);
//		frame.add(p.c(),BorderLayout.CENTER);
//		frame.setVisible(true);
//	}
//}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.EnumMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MySectionPanel {
    public enum VerticalAlign { TOP, CENTER, BOTTOM }

    private JPanel contentPane;
    private JPanel left, center, right;
    private JPanel leftWrapper, centerWrapper, rightWrapper;

    private EnumMap<VerticalAlign, Integer> alignMap = new EnumMap<>(VerticalAlign.class);

    public MySectionPanel() {
        left = new MyLeftLayout();
        center = new MyCenterLayout();
        right = new MyRightLayout();

        // Default alignment: CENTER
        leftWrapper = createVerticalAlignedWrapper(left, VerticalAlign.CENTER);
        centerWrapper = createVerticalAlignedWrapper(center, VerticalAlign.CENTER);
        rightWrapper = createVerticalAlignedWrapper(right, VerticalAlign.CENTER);

        contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        contentPane.add(leftWrapper, constraints);

        constraints.gridx = 1;
        contentPane.add(centerWrapper, constraints);

        constraints.gridx = 2;
        contentPane.add(rightWrapper, constraints);
    }

    private JPanel createVerticalAlignedWrapper(JPanel panel, VerticalAlign alignment) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        if (alignment == VerticalAlign.TOP) {
            wrapper.add(panel);
            wrapper.add(Box.createVerticalGlue());
        } else if (alignment == VerticalAlign.BOTTOM) {
            wrapper.add(Box.createVerticalGlue());
            wrapper.add(panel);
        } else {
            wrapper.add(Box.createVerticalGlue());
            wrapper.add(panel);
            wrapper.add(Box.createVerticalGlue());
        }

        return wrapper;
    }

    public MySectionPanel addLeft(Component c) {
        left.add(c);
        return this;
    }

    public MySectionPanel addCenter(Component c) {
        center.add(c);
        return this;
    }

    public MySectionPanel addRight(Component c) {
        right.add(c);
        return this;
    }

    public Component c() {
        return contentPane;
    }

    public void setBackground(Color background) {
        contentPane.setBackground(background);
        left.setBackground(background);
        center.setBackground(background);
        right.setBackground(background);
        leftWrapper.setBackground(background);
        centerWrapper.setBackground(background);
        rightWrapper.setBackground(background);
    }

    public void setMinimumHeight(int h) {
        contentPane.setMinimumSize(new Dimension(1, h));
    }

    public void setVerticalAlignment(VerticalAlign leftAlign, VerticalAlign centerAlign, VerticalAlign rightAlign) {
        contentPane.removeAll();

        leftWrapper = createVerticalAlignedWrapper(left, leftAlign);
        centerWrapper = createVerticalAlignedWrapper(center, centerAlign);
        rightWrapper = createVerticalAlignedWrapper(right, rightAlign);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        contentPane.add(leftWrapper, constraints);

        constraints.gridx = 1;
        contentPane.add(centerWrapper, constraints);

        constraints.gridx = 2;
        contentPane.add(rightWrapper, constraints);

        contentPane.revalidate();
        contentPane.repaint();
    }

    public void debugBorders() {
        leftWrapper.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerWrapper.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        rightWrapper.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    public static void main(String[] args) {
        MySectionPanel p = new MySectionPanel();
        p.addLeft(new JButton("Izq1")).addLeft(new JButton("Izq2"));
        p.addCenter(new JButton("center"));
        p.addRight(new JButton("right1")).addRight(new JButton("right2"));

        // Opcional: probar alineación
        p.setVerticalAlignment(VerticalAlign.CENTER, VerticalAlign.CENTER, VerticalAlign.CENTER);
        p.setMinimumHeight(120);
        p.debugBorders();

        JFrame frame = new JFrame("MySectionPanel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.add(p.c(), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}