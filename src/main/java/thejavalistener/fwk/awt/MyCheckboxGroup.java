package thejavalistener.fwk.awt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCheckboxGroup implements ActionListener
{
	private List<JComponent> components;
	private List<JCheckBox> checkboxes;
	private List<String> actionCommands;
	
	public int current=0;
	
	public MyCheckboxGroup()
	{
		components = new ArrayList<>();
		checkboxes = new ArrayList<>();
		actionCommands= new ArrayList<>();
	}
	
	public JCheckBox createCheckbox(JComponent cmp)
	{
		return createCheckbox(cmp,Integer.toString(checkboxes.size()));
	}
	
	public JCheckBox createCheckbox(JComponent cmp,String actionCommand)
	{
		boolean enabled = components.size()==0;
		
		cmp.setEnabled(enabled);
		
		JCheckBox chb = new JCheckBox();
		chb.setActionCommand(actionCommand);
		chb.setSelected(enabled);
		chb.addActionListener(this);
		chb.setFocusable(false);

		// agrego todo
		checkboxes.add(chb);
		actionCommands.add(actionCommand);
		components.add(cmp);
		
		return chb;
	}

	public JCheckBox getCheckbox(int idx)
	{
		return checkboxes.get(idx);
	}
	
	public JCheckBox getCheckbox(String actionCommand)
	{
//		for(int i=0; i<checkboxes.size(); i++)
//		{
//			JCheckBox ch = checkboxes.get(i);
//			if( ch.getActionCommand().equals(actionCommand) )
//			{
//				return getCheckbox(i);
//			}
//		}
		
		int idx = actionCommands.indexOf(actionCommand);
		return checkboxes.get(idx);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JCheckBox chb = (JCheckBox)e.getSource();
		int idx = actionCommands.indexOf(e.getActionCommand());
		
		checkboxes.get(current).setSelected(false);
		components.get(current).setEnabled(false);
		
		checkboxes.get(idx).setSelected(true);
		components.get(idx).setEnabled(true);
		
		current = idx;
	}

	public void setSelectedCheckbox(JCheckBox cb)
	{
		cb.setSelected(true);
		ActionEvent e = new ActionEvent(cb,1,cb.getActionCommand());
		actionPerformed(e);
	}
	
	public void setSelectedCheckbox(int i)
	{
		setSelectedCheckbox(checkboxes.get(i));
	}
	
	public void setSelectedCheckbox(String actionCommand)
	{
		JCheckBox ch = getCheckbox(actionCommand);
		setSelectedCheckbox(ch);
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		

		MyCheckboxGroup cbg = new MyCheckboxGroup();
		
		JTextField tf1 = new JTextField(5);
		JCheckBox ch1 = cbg.createCheckbox(tf1);
		p.add(ch1);
		p.add(tf1);
		
		JTextField tf2 = new JTextField(5);
		JCheckBox ch2 = cbg.createCheckbox(tf2);
		p.add(ch2);
		p.add(tf2);
		
		JTextField tf3 = new JTextField(5);
		JCheckBox ch3 = cbg.createCheckbox(tf3);
		p.add(ch3);
		p.add(tf3);
		
		JButton b = new JButton("Select");
		b.addActionListener(l->cbg.setSelectedCheckbox(1));
		f.add(b,BorderLayout.SOUTH);
		
		
		f.add(p,BorderLayout.CENTER);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
