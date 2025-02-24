package thejavalistener.fwk.awt;

import javax.swing.JOptionPane;

public class MyException extends Exception
{
	public static final int INFORMATION = JOptionPane.INFORMATION_MESSAGE;
	public static final int WARNING = JOptionPane.WARNING_MESSAGE;
	public static final int ERROR = JOptionPane.ERROR_MESSAGE;
	
	private boolean printStackStrace;
	private int messageType;
	private String title;
	
	public MyException(String mssg,String title,int type)
	{
		this(mssg,title,type,false);
	}
	
	public MyException(String mssg,String title,int type,boolean printStackTracke)
	{
		super(mssg);
		this.title = title;
		this.messageType = type;
		this.printStackStrace = printStackTracke;
	}

	public int getMessageType()
	{
		return messageType;
	}

	public String getTitle()
	{
		return title;
	}
	
	@Override
	public Throwable fillInStackTrace() 
	{
		return printStackStrace?super.fillInStackTrace():this;
    }
}
