package thejavalistener.fwk.console;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.util.MyCollection;
import thejavalistener.fwk.util.string.MyString;

//@Component
public class MyConsole extends MyConsoleBase
{	
	// singleton
	public static MyConsole io = null; 

	// listeners
	private EscuchaRead escuchaRead = null;
	private EscuchaPressAnyKey escuchaPressAnyKey = null;
	private EscuchaPassword escuchaPassword = null;
	private EscuchaMenu escuchaMenu = null;
	
	// bloqueo
    private SecondaryLoop secondaryLoop = null; 
    
    static 
    {
    	MyAwt.setWindowsLookAndFeel();
    	io = new MyConsole();
    }
 
    private boolean _suggestCloseableMode()
    {
		for(StackTraceElement elm:Thread.currentThread().getStackTrace())
		{
			if(elm.getClassName().startsWith("javax.swing"))
			{
				return true;
			}
		}
		
		return false;
    }

	public MyConsole()
	{
		this(false);
		setClosable(_suggestCloseableMode());
	}
	
	public MyConsole(boolean closeable)
	{			
		this.closable=closeable;
		escuchaRead = new EscuchaRead();
		escuchaPressAnyKey = new EscuchaPressAnyKey();
		escuchaPassword = new EscuchaPassword();
		escuchaMenu = new EscuchaMenu();
	}
	
	public MyConsole(Container parent)
	{
		this(false);
	}
	
	public void setWaiting(boolean wait)
	{
		// notifico a los listeners
		MyCollection.invoke(getListeners(),(lst)->lst.waitingForUserInput(wait));
		
		
		if( wait )
		{			
			// bloquea
		    EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		    secondaryLoop = eventQueue.createSecondaryLoop();
			secondaryLoop.enter();		
		}
		else
		{
			// desbloquea
			secondaryLoop.exit();
		}
	}
	
	@Override
	protected String _readString(InputConfigurator iconfig)
	{
		init();
		textPane.requestFocus();
		
		escuchaRead.setInputConfig(iconfig);
		
		Object defValue = iconfig.getDefaultValue();
		
		try
		{
			cs(getDefaultInputStyle());
			
			print("["+defValue+"]");
			
			SwingUtilities.invokeLater(()->_selectInputText());
			
			textPane.setCaretPosition(textPane.getLen()-1);
			inputPosition=textPane.getCaretPosition();

			// comienzo a escuchar
			textPane.addKeyListener(escuchaRead);

			textPane.setEditable(true);
			X();

			setWaiting(true);
			
			// dejo de escuchar
			textPane.removeKeyListener(escuchaRead);
			textPane.setEditable(false);
			

			return escuchaRead.getReadedString();				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	
	
	private void _selectInputText()
	{
		String txt = textPane.getText();
		int p0 = txt.lastIndexOf('[');
		int p1 = txt.lastIndexOf(']');
		textPane.setSelectedText(p0+1,p1);
	}

	
	public class EscuchaRead extends KeyAdapter
	{
		private InputConfigurator iconfig;
		private String inputText;
		
		public void setInputConfig(InputConfigurator iconfig)
		{
			this.iconfig = iconfig;
		}
		
		public String getReadedString()
		{
			return inputText;
		}
		@Override
		public void keyPressed(KeyEvent e)
		{
			String txt = textPane.getText();
			int abre = txt.lastIndexOf('[');
			int cierra = txt.lastIndexOf(']');
			inputText = txt.substring(abre+1,cierra);

			int kc=e.getKeyCode();

			// enter
			if(kc==KeyEvent.VK_ENTER)
			{
				e.consume();
				
				if( !iconfig.getValid().apply(inputText) )
				{
					_selectInputText();
					return;
				}
				
				if( !iconfig.getRegex().matcher(inputText).matches())
				{
					_selectInputText();
					return;
				}
				
				textPane.setCaretPosition(textPane.getLen());
				textPane.setEditable(false);
				
				setWaiting(false);
				return;
			}

			if( _pisaCorchetes(e) )
			{
				e.consume();
				return;
			}		

		}
		
		@Override
		public void keyTyped(KeyEvent e)
		{			
			if( e.getKeyChar()=='[' || e.getKeyChar()==']')
			{
				e.consume();
				return;
			}

			Character c;
			if((c=iconfig.getMask().apply(e.getKeyChar(),e.getKeyCode(),inputText))==null)
			{
				e.consume();
				return;
			}

			e.setKeyChar(c);			
		}
	}
	
	@Override
	protected int _pressAnyKey(Integer k, Runnable r)
	{
		try
		{
			textPane.requestFocus();
			textPane.setEditable(true);
			escuchaPressAnyKey.setRunnnableAndKey(r,k);
			textPane.addKeyListener(escuchaPressAnyKey);

			setWaiting(true);
			
			int keyPressed = escuchaPressAnyKey.getPressedKey();
			textPane.removeKeyListener(escuchaPressAnyKey);
			textPane.setEditable(false);
			
			return keyPressed;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public class EscuchaPressAnyKey extends KeyAdapter
	{
		private Runnable runnable;
		private Integer keyEspected; // a la espera de esta tecla
		private Integer keyPressed;  // tecla efectivamente presionada
		private int descartarKeyEvent = 1;

		public int getPressedKey()
		{
			descartarKeyEvent=1;
			return keyPressed!=null?keyPressed:-1;
		}

		public void setRunnnableAndKey(Runnable r,Integer k)
		{
			this.runnable = r;
			this.keyEspected = k;
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			keyPressed = e.getKeyCode();
			e.consume();

			// ignoro estas teclas
			if( keyPressed == KeyEvent.VK_SHIFT
			 || keyPressed == KeyEvent.VK_CONTROL
			 || keyPressed == KeyEvent.VK_ALT
			 || keyPressed == KeyEvent.VK_ALT_GRAPH )
			{
				return;
			}

			if(keyEspected==null||keyEspected.equals(keyPressed))//||keyPressed==10)
			{
				runnable.run();

				setWaiting(false);
				
		        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() 
		        {
		            public boolean dispatchKeyEvent(KeyEvent e) 
		            {
		            	return descartarKeyEvent++==1;
		            }
	            });
			}
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			e.consume();
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			e.consume();
		}
	}
	
	@Override
	protected String _readPassword()
	{
		init();
		
		try
		{
			cs(getDefaultInputStyle());
			print("[]");
			
			textPane.setCaretPosition(textPane.getLen()-1);
			inputPosition=textPane.getCaretPosition();

			// comienzo a escuchar
			textPane.addKeyListener(escuchaPassword);

			textPane.setEditable(true);
			X();
			
			setWaiting(true);
			
			// dejo de escuchar
			textPane.removeKeyListener(escuchaPassword);
			textPane.setEditable(false);
			return escuchaPassword.getPassword();				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	
	
	class EscuchaPassword extends KeyAdapter
	{
		private boolean consume = false;
		private StringBuffer sb=new StringBuffer();
		
		public void keyPressed(KeyEvent e)
		{
			int lastCorch = textPane.getText().lastIndexOf('[');
			int pos=textPane.getCaretPosition()-lastCorch-1;
		
			int kc = e.getKeyCode();
			
			// no permito cosas raras...
			if( _pisaCorchetes(e) || textPane.getSelectedText()!=null && kc!=KeyEvent.VK_LEFT&&e.getKeyCode()!=KeyEvent.VK_RIGHT)
			{
				consume = true;
				e.consume();
				return;
			}
			
			if( kc==KeyEvent.VK_BACK_SPACE && pos>0 )
			{
				sb.deleteCharAt(pos-1);
				return;
			}

			if( kc==KeyEvent.VK_DELETE && sb.length()>pos )
			{
				sb.deleteCharAt(pos);
				return;
			}
			
			if( kc==KeyEvent.VK_ENTER )
			{
				e.consume();
				textPane.setCaretPosition(textPane.getLen());

				setWaiting(false);

				return;
			}
		}

		public void keyTyped(KeyEvent e)
		{
			if( consume )
			{
				consume = false;
				e.consume();
				return;
			}
		
			int lastCorch = textPane.getText().lastIndexOf('[');
			int pos=textPane.getCaretPosition()-lastCorch-1;
			char c = e.getKeyChar();
			
			if( MyString.isPrintableChar(c))
			{
				sb.insert(pos,c);
				e.setKeyChar('*');
			}
			else
			{
				e.consume();
			}		
		}

		public String getPassword()
		{
			String x = sb.toString();
			sb.delete(0,x.length());
			return x;
		}
	}
	
	private boolean _pisaCorchetes(KeyEvent e)
	{
		String txt = textPane.getText();
		int cierra = txt.lastIndexOf("]");
		int abre = txt.lastIndexOf("[");
		int caret = textPane.getCaretPosition();
		int kc = e.getKeyCode();
		return (kc==KeyEvent.VK_UP)||(kc==KeyEvent.VK_DOWN)||(kc==KeyEvent.VK_LEFT&&caret<=abre+1)||(kc==KeyEvent.VK_BACK_SPACE&&caret<=abre+1)||(kc==KeyEvent.VK_RIGHT&&caret>=cierra)
				||(kc==KeyEvent.VK_PAGE_DOWN||kc==KeyEvent.VK_PAGE_UP);
	}	
	
	@Override
	protected int _menu(int menuRange[][])
	{
		init();
		
		textPane.requestFocus();
		
		try
		{
			// comienzo a escuchar
			escuchaMenu.setMenuRange(menuRange);
			textPane.addKeyListener(escuchaMenu);
			textPane.c().addMouseListener(escuchaMenu);
			
			textPane.setEditable(true);

			setWaiting(true);
			
			// dejo de escuchar
			textPane.removeKeyListener(escuchaMenu);
			textPane.c().removeMouseListener(escuchaMenu);
			textPane.setEditable(false);
			return escuchaMenu.getSelectedOption();				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	
	
	class EscuchaMenu extends KeyAdapter implements MouseListener
	{
		private int [][] menuRange;
		private int curr = 0;
		private boolean consume = true;
		
		public void setMenuRange(int menuRange[][])
		{
			textPane.setSelectedText(menuRange[0][0],menuRange[0][1]);
			this.menuRange = menuRange;
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			consume = true;
			
			if( e.getKeyCode()==KeyEvent.VK_UP && curr!=0 )
			{
				curr--;
				consume = false;
			}
			
			if( e.getKeyCode()==KeyEvent.VK_DOWN && curr!=menuRange.length-1 )
			{
				curr++;
				consume = false;
			}
			
			if( e.getKeyCode()==KeyEvent.VK_ENTER && textPane.getSelectedText()!=null)
			{
				// borro el menu
				int from = menuRange[0][0]-1;
				int to = menuRange[menuRange.length-1][1]+1;
				textPane.deleteText(from,to+1); // queda un \n --> por eso el +1
				
				// escribo la opcion seleccionada
				
				textPane.setEditable(false);
				setWaiting(false);
				return;
			}


			if( !consume )
			{
				_selectMenuOption();
			}
			else
			{
				e.consume();
			}
		}
		
		private void _selectMenuOption()
		{
			int d = menuRange[curr][0];
			int h = menuRange[curr][1];

			SwingUtilities.invokeLater(new Runnable() {
			    @Override
			    public void run() {
			        textPane.c().setSelectionStart(d);
			        textPane.c().setSelectionEnd(h);
			    }
			});
			
		}
		
		@Override
		public void keyTyped(KeyEvent e)
		{
			if( consume )
			{
				consume = false;
				e.consume();
			}
		}
		
		public int getSelectedOption()
		{
			return curr;
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			_selectMenuOption();
		}

		@Override
		public void mousePressed(MouseEvent e)
		{			
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}
	}
}
