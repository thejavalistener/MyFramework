package thejavalistener.fwk.awt.list;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyComboBoxTest
{
	public static void main(String[] args)
	{
		JPanel p = new JPanel();
		MyComboBox<String> combo = new MyComboBox<>();
		p.add(combo.c());
		combo.setItems(List.of("Azul","Rojo","Verde","Amarillo","Naranja","Gris","Violeta"));
		
		MyTestUI.test(p).addButton("Restaurar Anterior",x->{SwingUtilities.invokeLater(() -> combo.restorePreviousSelectedItem());})
		.run();
	}
	
	;
}
