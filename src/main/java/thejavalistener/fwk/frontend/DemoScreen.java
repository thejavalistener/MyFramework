package thejavalistener.fwk.frontend;

import javax.swing.JButton;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.string.MyString;

@Component
@Scope("prototype")
public class DemoScreen extends MyAbstractScreen
{
	private String name = null;
	
	@Override
	protected void createUI()
	{
		JButton bPush = new JButton("Push");
		bPush.addActionListener(l->getMyApp().pushScreen(DemoScreen.class));
		add(bPush);

		JButton bPop = new JButton("Pop");
		bPop.addActionListener(l->getMyApp().popScreen());
		add(bPop);

		JButton bShow = new JButton("Show");
		bShow.addActionListener(l->getMyApp().showScreen(DemoScreen.class).size(.5).apply());
		add(bShow);
		
		setBackground(MyAwt.randomColor());		
	}

	@Override
	public String getName()
	{
		return name!=null?name:(name=MyString.wordCapitalize(MyString.generateRandom('a','z',5,10)));
	}
}
