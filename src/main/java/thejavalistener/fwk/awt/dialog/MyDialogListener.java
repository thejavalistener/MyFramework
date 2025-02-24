package thejavalistener.fwk.awt.dialog;

public interface MyDialogListener
{
	public static final int WindowClosing = 1;
	
	public void onEvent(int eventType);
}
