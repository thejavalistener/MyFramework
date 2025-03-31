package thejavalistener.fwk.awt.panel;

import java.awt.Dimension;

import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.testui.MyTestUI;
import thejavalistener.fwk.util.MyNumber;

public class MatrixLayoutTest
{
	public static void main(String[] args)
	{
		JPanel panel = new JPanel(new MatrixLayout(3,10,10,MatrixLayout.CENTER_ALIGN));
		MyScrollPane scroll = new MyScrollPane(panel);
		
		MyTestUI.test(new MyScrollPane(scroll)).addButton("Add",e->{panel.add(_createRandom());scroll.validate();})
		 		.maximize().run();
	}

	public static JPanel _createRandom()
	{
		JPanel ret = new MyRandomColorPanel();
		int w = MyNumber.rndInt(50,300);
		int h = MyNumber.rndInt(50,300);
		ret.setPreferredSize(new Dimension(w,h));
		return ret;
	}
	
}
