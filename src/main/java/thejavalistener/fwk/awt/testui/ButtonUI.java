package thejavalistener.fwk.awt.testui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonUI extends JButton implements ActionListener
{
	private MyTestUI myTestUI;
	private ActionUIListener listener;
	
	public ButtonUI(String lbl,MyTestUI myTestUI)
	{
		super(lbl);
		addActionListener(this);
		this.myTestUI = myTestUI;
	}
	
	public void setActionUIListener(ActionUIListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( listener!=null ) listener.onClick(myTestUI);
	}
}
