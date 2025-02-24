package thejavalistener.fwk.awt;

import javax.swing.JComponent;

public interface MyComponent
{
	public JComponent c();
	public Object getValue();
	public void resetValue();
	public void requestFocus();
	public void setEnabled(boolean b);
}
