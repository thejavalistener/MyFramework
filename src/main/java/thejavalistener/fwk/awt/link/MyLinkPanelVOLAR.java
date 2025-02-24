package thejavalistener.fwk.awt.link;

import java.awt.Insets;
import java.awt.event.ActionListener;

import thejavalistener.fwk.awt.link.MyLink;

public interface MyLinkPanelVOLAR
{
	public void addLink(MyLink lbl);
	public void addLink(String text);
	public void setDefaultInsets(Insets i);
	public void setSelected(int i);
	public void setSelected(int i, boolean throwEvent);
	public void setActionListener(ActionListener listener);
	public MyLink getSelected();
	public int getSelectedIndex();
}
