package thejavalistener.fwk.awt;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class MyScrollPane extends JScrollPane
{
	public MyScrollPane(Component view)
	{
		super(view);
		setBorder(BorderFactory.createLineBorder(view.getBackground()));
	}
}
