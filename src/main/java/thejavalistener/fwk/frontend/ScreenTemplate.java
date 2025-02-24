package thejavalistener.fwk.frontend;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyPanel;
import thejavalistener.fwk.awt.textarea.MyTextArea;
import thejavalistener.fwk.awt.textarea.MyTextPane;

@Component
public abstract class ScreenTemplate extends MyAbstractScreen
{
	private MyTextPane console;
	private MyForm form;
				
	public ScreenTemplate()
	{
		setLayout(new BorderLayout());

		// instancio el form y la consola
		console = new MyTextPane();
		console.setFont(new Font("Consolas", Font.PLAIN, 11));

		form = new MyForm();
		
		// armo la UI
		MyPanel pCenter = new MyBorderLayout();
		
		// agrego la consola
		pCenter.add(configureConsoleArea());

		// segundo pido el form
		pCenter.add(configureFormArea(),BorderLayout.WEST);
		add(pCenter,BorderLayout.CENTER);
	}
	
	protected Container configureConsoleArea()
	{
		return new MyScrollPane(console.c());
	}
	
	protected Container configureFormArea()
	{
		return form.c();		
	}
	
	protected MyTextPane getConsole()
	{
		return console;
	}
	
	protected MyForm getForm()
	{
		return form;
	}
}
