//package myframework.awt.progres2;
//
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.Window;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JProgressBar;
//
//import myframework.awt.MyAwt;
//import myframework.awt.link.MyLink;
//
//public class MyProgressPane extends MyOptionPane
//{
//	private MyLink lnkMessage;
//	private JProgressBar progress;
//	private int currentValue=0;
//	
//	private boolean disposed = false;
//	public boolean enableCancel = true;
//	
//	private static final String Aceptar = "Aceptar";
//	private static final String Cancelar = "Cancelar";
//
//	private MyProgressListener listener;
//	
//	private JButton bButton;
//	
//	public MyProgressPane(String title,String message,Window owner)
//	{
//		super(title,owner);
//		lnkMessage = new MyLink(message);
//	}
//	
//	@Override
//	public JPanel getMessagePane()
//	{
//		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		panel.add(lnkMessage.c());
//		return panel;
//	}
//	
//	@Override
//	public JPanel getComponentPane()
//	{
//		progress = new JProgressBar(1,100);
//		progress.setValue(0);
//		
//		MyAwt.setPreferredHeiht(20,progress);
//		
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.add(progress,BorderLayout.NORTH);
//		return panel;
//	}
//	
//	@Override
//	public JPanel getButtonPane()
//	{
//		JPanel pButtons = new JPanel();
//		
//		bButton = new JButton();
//		bButton.addActionListener(new EscuchaButton());
//		
//		if( enableCancel )
//		{
//			bButton.setText(Cancelar);
//			bButton.setEnabled(true);
//		}
//		else
//		{
//			bButton.setText(Aceptar);
//			bButton.setEnabled(false);			
//		}
//		
//		pButtons.add(bButton);
//		
//		return pButtons;
//	}
//	
//	public void setProgressListener(MyProgressListener lst)
//	{
//		this.listener = lst;
//	}
//	
//	public void setMessage(String mssg)
//	{
//		lnkMessage.setText(mssg);
//	}
//	
//	public void setEnableCancel(boolean b)
//	{
//		enableCancel = b;
//		setCloseable(b);
//	}
//	
//	@Override
//	public Object show()
//	{
//		return show(true);
//	}
//	
//	@Override
//	public Object getReturnValue()
//	{
//		throw new RuntimeException("No implementado");
//	}
//
//	public void increaseValueTo(int v) throws Exception
//	{
//		increaseValueTo(v,null);
//	}
//
//	public void increaseValueTo(int v,String mssg) throws Exception
//	{
//		if( mssg!=null ) lnkMessage.setText(mssg);
//		for(int i=currentValue; i<=Math.min(v,100); i++)
//		{
//			increaseValue();
//		}
//	}
//
//
//	public void increaseValue() throws Exception
//	{
//		increaseValue(null);
//	}
//	
//	public void increaseValue(String mssg) throws Exception
//	{
//		if( disposed )
//		{
//			throw new Exception("Process aborted");
//		}
//		
//		if( mssg!=null )
//		{
//			lnkMessage.setText(mssg);
//		}
//		
//		currentValue++;
//		progress.setValue(currentValue);
//
//		if( currentValue==100 )
//		{
//			if( enableCancel )
//			{
//				bButton.setText("Aceptar");
//			}
//			else
//			{
//				bButton.setEnabled(true);
//			}				
//		}		
//	}
//	
//	public int getProgressValue()
//	{
//		return currentValue;
//	}
//	
//	class EscuchaButton implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			boolean cancelado = false;
//			if( bButton.getText().equals(Cancelar) )
//			{
//				cancelado = true;
//			}
//
//			disposed = true;
//			dispose();	
//			
//			if( listener!=null ) listener.progressTerminated(cancelado,currentValue);
//		}
//	}
//	
//}

package thejavalistener.fwk.awt.progres;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.link.MyLink;

public class MyProgressPane extends MyOptionPane {
    private MyLink lnkMessage;
    private JProgressBar progress;
    private int currentValue = 0;
    private int top = 100; // Valor máximo por defecto
    
    private boolean disposed = false;
    public boolean enableCancel = true;
    
    private static final String Aceptar = "Aceptar";
    private static final String Cancelar = "Cancelar";

    private MyProgressListener listener;
    
    private JButton bButton;
    
    public MyProgressPane(String title, String message, Window owner) {
        super(title, owner);
        lnkMessage = new MyLink(message);
        progress = new JProgressBar(0, top);  // Inicialización temprana de la barra de progreso
        progress.setValue(0);
    }
    
    public void setTop(int top) {
        this.top = top;
        if (progress != null) {
            progress.setMaximum(top);
        }
        currentValue = 0;
        progress.setValue(0);
    }
    
    @Override
    public JPanel getMessagePane() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(lnkMessage.c());
        return panel;
    }
    
    @Override
    public JPanel getComponentPane() {
		MyAwt.setPreferredHeight(20,progress);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progress, BorderLayout.NORTH);
        return panel;
    }
    
    @Override
    public JPanel getButtonPane() {
        JPanel pButtons = new JPanel();
        
        bButton = new JButton();
        bButton.addActionListener(new EscuchaButton());
        
        if (enableCancel) {
            bButton.setText(Cancelar);
            bButton.setEnabled(true);
        } else {
            bButton.setText(Aceptar);
            bButton.setEnabled(false);            
        }
        
        pButtons.add(bButton);
        
        return pButtons;
    }
    
    public void setProgressListener(MyProgressListener lst) {
        this.listener = lst;
    }
    
    public void setMessage(String mssg) {
        lnkMessage.setText(mssg);
    }
    
    public void setEnableCancel(boolean b) {
        enableCancel = b;
        setCloseable(b);
    }
    
    @Override
    public Object show() {
        return show(true);
    }
    
    @Override
    public Object getReturnValue() {
        throw new RuntimeException("No implementado");
    }

    public void increaseValueTo(int v) throws Exception {
        increaseValueTo(v, null);
    }

    public void increaseValueTo(int v, String mssg) throws Exception {
        if (mssg != null) lnkMessage.setText(mssg);
        for (int i = currentValue; i <= Math.min(v, top); i++) {
            increaseValue();
        }
    }

    public void increaseValue() throws Exception {
        increaseValue(null);
    }
    
    public void increaseValue(String mssg) throws Exception {
        if (disposed) {
            throw new Exception("Process aborted");
        }
        
        if (mssg != null) {
            lnkMessage.setText(mssg);
        }
        
        currentValue++;
        progress.setValue(currentValue);

        if (currentValue >= top) {
            if (enableCancel) {
                bButton.setText("Aceptar");
            } else {
                bButton.setEnabled(true);
            }                
        }        
    }
    
    public int getProgressValue() {
        return currentValue;
    }
    
    class EscuchaButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean cancelado = false;
            if (bButton.getText().equals(Cancelar)) {
                cancelado = true;
            }

            disposed = true;
            dispose();    
            
            if (listener != null) listener.progressTerminated(cancelado, currentValue);
        }
    }
}
