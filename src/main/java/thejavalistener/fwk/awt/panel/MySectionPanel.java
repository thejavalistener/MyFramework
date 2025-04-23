package thejavalistener.fwk.awt.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MySectionPanel
{
	JPanel contentPane;
	JPanel left,center,right;
	JPanel leftWrapper,centerWrapper,rightWrapper;

	public MySectionPanel()
	{

		left=new MyLeftLayout();
		center=new MyCenterLayout();
		right=new MyRightLayout();

		// Envolver cada panel en un contenedor para centrar verticalmente
		leftWrapper=createVerticalCenterWrapper(left);
		centerWrapper=createVerticalCenterWrapper(center);
		rightWrapper=createVerticalCenterWrapper(right);

		// Configurar el layout principal como GridBagLayout
		contentPane=new JPanel(new GridBagLayout());
		GridBagConstraints constraints=new GridBagConstraints();
		constraints.gridy=0; // Todos los paneles en la misma fila
		constraints.weightx=1.0; // Distribuir espacio horizontal
		constraints.fill=GridBagConstraints.BOTH; // Expandir horizontalmente

		// Agregar los paneles al layout
		constraints.gridx=0; // Primera columna
		contentPane.add(leftWrapper,constraints);

		constraints.gridx=1; // Segunda columna
		contentPane.add(centerWrapper,constraints);

		constraints.gridx=2; // Tercera columna
		contentPane.add(rightWrapper,constraints);
	}

	// Método para crear un contenedor que centre su contenido verticalmente
	private JPanel createVerticalCenterWrapper(JPanel panel)
	{
		JPanel wrapper=new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper,BoxLayout.Y_AXIS)); // Centrar
																	// verticalmente
		wrapper.add(Box.createVerticalGlue()); // Espacio flexible arriba
		wrapper.add(panel); // Tu panel real
		wrapper.add(Box.createVerticalGlue()); // Espacio flexible abajo
		return wrapper;
	}

	public MySectionPanel addLeft(Component c)
	{
		left.add(c);
		return this;
	}

	public MySectionPanel addCenter(Component c)
	{
		center.add(c);
		return this;
	}

	public MySectionPanel addRight(Component c)
	{
		right.add(c);
		return this;
	}

	public Component c()
	{
		return contentPane;
	}

	public void setBackground(Color background)
	{
		contentPane.setBackground(background);
		left.setBackground(background);
		center.setBackground(background);
		right.setBackground(background);
		leftWrapper.setBackground(background);
		centerWrapper.setBackground(background);
		rightWrapper.setBackground(background);
	}

	public static void main(String[] args)
	{
		MySectionPanel p=new MySectionPanel();
		p.addLeft(new JButton("Izq1")).addLeft(new JButton("Izq2"));
		p.addCenter(new JButton("center"));
		p.addRight(new JButton("right1")).addRight(new JButton("right2"));

		JFrame frame=new JFrame("MySectionPanel Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,400);
		frame.add(p.c(),BorderLayout.CENTER);
		frame.setVisible(true);
	}
}