package thejavalistener.fwk.awt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GenericKeyListener implements KeyListener
{
	private List<ProcEstr> process = new ArrayList<>();
	
	// p
	public void onAll(Consumer<KeyEvent> p)
	{
		process.add(new ProcEstr(null,null,p));
	}
	
	public void on(int keyCode,Consumer<KeyEvent> p)
	{
		process.add(new ProcEstr(null,keyCode,p));
	}
	
	public void on(char keyChar,Consumer<KeyEvent> p)
	{
		process.add(new ProcEstr((int)keyChar,null,p));
	}
	
	public GenericKeyListener avoid(String chars)
	{
		for(int i=0; i<chars.length();i++)
		{
			avoid(chars.charAt(i));
		}
		
		return this;
	}
	
	public GenericKeyListener avoid(char ...chars)
	{
		for(char c:chars)
		{
			on(c,e->e.consume());
		}
		
		return this;
	}
	
	private void _process(KeyEvent e)
	{
		for(ProcEstr p:process)
		{
			if(p.charCode!=null&&p.charCode==e.getKeyChar()
			|| p.keyCode!=null&&p.keyCode==e.getKeyCode() 
			|| p.charCode==null && p.keyCode==null)
			{
				p.proc.accept(e);
			}
		}
	}

	
	@Override
	public void keyPressed(KeyEvent e)
	{
		_process(e);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		_process(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	class ProcEstr
	{
		public Integer charCode;
		public Integer keyCode;
		public Consumer<KeyEvent> proc;

		public ProcEstr(Integer cc,Integer kc,Consumer<KeyEvent> p)
		{
			charCode = cc;
			keyCode = kc;
			proc = p;
		}
	}
}
