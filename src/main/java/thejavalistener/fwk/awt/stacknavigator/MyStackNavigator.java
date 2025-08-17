package thejavalistener.fwk.awt.stacknavigator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JPanel;

public class MyStackNavigator<T>
{
	// dos flechas
	private MyStackNavigatorArrow aBack;
	private MyStackNavigatorArrow aFwd;
	private JPanel contentPane;

	public MyStackNavigatorStyle style = new MyStackNavigatorStyle();
	
	// dos pilas
	public Stack<T> stackBack;
	public  Stack<T> stackFwd;

	// elemento recién sacado
	private T poppedElement=null;
	
	// elemento actual en la pantalla
	public T currElement;

	private ActionListener actionListener;

	private EscuchaArrow escuchaArrow;
	
	public MyStackNavigator()
	{
		contentPane=new JPanel();

		
		aBack=new MyStackNavigatorArrow(MyStackNavigatorArrow.LEFT,"back",style);
		aBack.setEnabled(false);
		
		contentPane.add(aBack.c());
		aFwd=new MyStackNavigatorArrow(MyStackNavigatorArrow.RIGHT,"fwd",style);
		aFwd.setEnabled(false);
		contentPane.add(aFwd.c());

		// escucho las flechas
		escuchaArrow=new EscuchaArrow();
		aBack.setActionListener(escuchaArrow);
		aFwd.setActionListener(escuchaArrow);

		stackBack=new Stack<>();
		stackFwd=new Stack<>();
	}
	
	public MyStackNavigatorStyle getStyle()
	{
		return style;
	}

	public JPanel getContentPane()
	{
		return contentPane;
	}
	
	public void push(T t)
	{
		if( currElement==null )
		{
			currElement = t;
			return;
		}
		
		aBack.setEnabled(true);
		stackBack.push(currElement);
		currElement = t;

		aFwd.setEnabled(false);
		stackFwd.clear();
	}

	public Component c()
	{
		contentPane.setBackground(style.background);
		return contentPane;
	}
	
	public void applyStyle()
	{
		contentPane.setBackground(style.background);
		aBack.setBackground(style.background);
		aFwd.setBackground(style.background);		
	}

	public void setActionListener(ActionListener l)
	{
		this.actionListener=l;
	}
	
	public T getElement()
	{
		return currElement;
	}
	
	public void popBack()
	{
		if( aBack.isEnabled() )
		{
			ActionEvent e = new ActionEvent(aBack,1,"back");
			escuchaArrow.actionPerformed(e);
		}
	}
	
	public void popFwd()
	{
		if( aFwd.isEnabled() )
		{
			ActionEvent e = new ActionEvent(aBack,1,"fwd");
			escuchaArrow.actionPerformed(e);
		}
	}

	class EscuchaArrow implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String action = e.getActionCommand();

			if (action.equals("back"))
			{
				// el actual va a fwd
				stackFwd.push(currElement);
				aFwd.setEnabled(true);

				// pop de back es el curr
				poppedElement = stackBack.pop();
				currElement = poppedElement;
				aBack.setEnabled(!stackBack.isEmpty());				
			}
			else
			{
				stackBack.push(currElement);
				aBack.setEnabled(true);
				
				poppedElement=stackFwd.pop();
				currElement = poppedElement;
				aFwd.setEnabled(!stackFwd.isEmpty());
			}
			
			if( actionListener!=null )
				actionListener.actionPerformed(e);
		}
	}
	 
	public T getPoppedElement()
	{
		return poppedElement;
	}

	public void mostarEstado()
	{	
		System.out.println("curr=["+currElement+"], back={"+stackBack+"}, fwd={"+stackFwd+"}");
	}
}


