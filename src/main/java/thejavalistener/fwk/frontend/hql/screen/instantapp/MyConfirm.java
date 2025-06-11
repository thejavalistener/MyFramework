//package thejavalistener.fwk.frontend.hql.screen.instantapp;
//
//import java.awt.Container;
//
//import javax.swing.JOptionPane;
//
//import thejavalistener.fwk.awt.MyAwt;
//
//public class MyConfirm
//{
//	public static final int INF = JOptionPane.INFORMATION_MESSAGE;
//	public static final int ERR = JOptionPane.ERROR_MESSAGE;
//	public static final int WAR = JOptionPane.WARNING_MESSAGE;
//	public static final int CONF = -1;
//
//	private String mssg;
//	private int mssgType;
//
//	public MyConfirm(String mssg,int mssgType)
//	{
//		this.mssg= mssg;
//		this.mssgType = mssgType;
//	}
//	
//	protected boolean show(Container parent)
//	{
//		if( mssgType==CONF )
//		{
//			return MyAwt.showConfirmYES_NO(mssg,"Confirmación",parent)==0;
//		}
//		else
//		{
//			MyAwt.showMessage(mssg,"Mensaje",mssgType,parent);
//			return mssgType!=ERR;
//		}
//	}
//}
