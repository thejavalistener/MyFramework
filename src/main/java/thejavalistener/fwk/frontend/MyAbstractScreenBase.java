package thejavalistener.fwk.frontend;

import java.awt.Window;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.MyException;
import thejavalistener.fwk.awt.dialog.MyDialog;
import thejavalistener.fwk.awt.dialog.Returnable;
import thejavalistener.fwk.awt.panel.MyPanel;
import thejavalistener.fwk.awt.searchbox.MySearchBox;
import thejavalistener.fwk.awt.searchbox.MySearchBoxController;
import thejavalistener.fwk.frontend.messages.MyScreenMessageExchange;
import thejavalistener.fwk.frontend.messages.MyScreenValuesExchange;
import thejavalistener.fwk.util.MyFileProperties;

@Component
public abstract class MyAbstractScreenBase extends MyPanel implements Returnable
{
	@Autowired
	private MyScreenValuesExchange values;
	
	@Autowired
	private MyScreenMessageExchange messages;
		
	@Autowired
	private MyFileProperties properties;
	
	private MyApp myApp = null;
	
	public Object returnValue;
	public MyDialog myDialog;
	
	public MyDialog getMyDialog()
	{
		return myDialog;
	}

	public void setMyDialog(MyDialog myDialog)
	{
		this.myDialog=myDialog;
	}

	public Object getReturnValue()
	{
		return returnValue;
	}

	public MyFileProperties getProperties()
	{
		return properties;
	}
	
	public void setReturnValue(Object returnValue)
	{
		this.returnValue=returnValue;
	}

	public abstract MyAbstractScreen getOuter();
	
	public MyAbstractScreenBase()
	{
		super(0,0,0,0);
	}
	
	void setMyApp(MyApp app)
	{
		this.myApp = app;
	}
	
	public MyApp getMyApp()
	{
		return this.myApp;
	}
	
	/** Retorna JOptionPane.YES_OPTION o JOptionPane.NO_OPTION */
	public boolean showConfirmWarningMessage(String mssg,String title)
	{
		return JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	}
	
	/** Retorna JOptionPane.YES_OPTION o JOptionPane.NO_OPTION */
	public boolean showConfirmQuestionMessage(String mssg,String title)
	{
		return JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	}

	
	public void showErrorMessage(String mssg,String title)
	{
		JOptionPane.showMessageDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.ERROR_MESSAGE);		
	}
	
	public void showWarningMessage(String mssg,String title)
	{
		JOptionPane.showMessageDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.WARNING_MESSAGE);		
	}
	
	public void showInformationMessage(String mssg,String title)
	{
		JOptionPane.showMessageDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.INFORMATION_MESSAGE);		
	}

	public void showException(MyException ex)
	{
		JOptionPane.showMessageDialog(myApp.getMyAppContainer().c(),ex.getMessage(),ex.getTitle(),ex.getMessageType());
	}
	
	public void showExceptionMessage(Exception ex)
	{
		String mssg = ex.getMessage();
		String title = ex.getClass().getCanonicalName();
		JOptionPane.showMessageDialog(myApp.getMyAppContainer().c(),mssg,title,JOptionPane.ERROR_MESSAGE);
	}
	
	public void putValueFor(Class<? extends MyAbstractScreen> target,String valName,Object val)
	{
		values.putValueFor(target,valName,val,getOuter());
	}
	
	public Object getValueFrom(Class<? extends MyAbstractScreen> sender,String valName)
	{
		return values.getValueFrom(getOuter(),sender,valName);
	}
	
	public Object removeValueFrom(Class<? extends MyAbstractScreen> sender,String valName)
	{
		return values.removeValueFrom(getOuter(),sender,valName);
	}
	
//	public void sendEmail(SimpleMailMessage smm)
//	{
//		javaMailSender.send(smm);
//	}
//	
//	public void sendEmails(MyEmailDatasource dataSource)
//	{
//		emailDatasourceSender.sendEmails(dataSource,new MyEmailControllerBasicImple());
//	}
//	public void sendEmails(MyEmailDatasource dataSource,MyEmailController controller)
//	{
//		emailDatasourceSender.sendEmails(dataSource,controller);
//	}
	
	public <T> MySearchBox<T> createSearchBox(Class<T> clazz,MySearchBoxController<T> controller)
	{
		// parent
		Window parent = myApp.getMyAppContainer().c();
		
		// instancio
		MySearchBox<T> msb = new MySearchBox<>(parent,controller);
		msb.setAlternateRowColor(true);
		MyAwt.setProportionalSize(.6,msb.getDialog(),parent);
		MyAwt.centerH(150,msb.getDialog(),parent);
		
		return msb;
	}
	
	private Map<?,?> currState = null;
	public void setDisabledTemporally(boolean disable)
	{
		if( disable )
		{
			currState = MyAwt.disableTemporally(this);
		}
		else
		{
			MyAwt.restoreDisabled(currState);			
		}

	}
}
