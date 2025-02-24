package thejavalistener.fwk.frontend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.MyPanel;

public class Main2
{
public static void main(String[] args)
{
	MyAwt.setWindowsLookAndFeel();

    ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:/spring.xml");

    // application container
	MyAppContainer appContainer = ctx.getBean(MyAppContainer.class);		
	appContainer.createApp("App1",DemoScreen.class);
	appContainer.createApp("App2",DemoScreen.class);
	appContainer.createApp("App3",DemoScreen.class);
	appContainer.init();
}
}
