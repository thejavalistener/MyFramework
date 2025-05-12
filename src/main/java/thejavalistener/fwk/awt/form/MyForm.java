package thejavalistener.fwk.awt.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.panel.GridLayout2;
import thejavalistener.fwk.util.MyCollection;

public class MyForm 
{
	private JPanel formPanel;
	private JPanel contentPane;

	private int width;
	private List<MyFormRow> mfRows = null;
	
	public MyForm()
	{
		// ancho por defecto
		this(215);
	}

	public MyForm(int width)
	{
		setWidth(width);
		contentPane = new JPanel(new BorderLayout());		
		formPanel = new JPanel(new GridLayout2(0,1));
		contentPane.add(formPanel,BorderLayout.NORTH);
		
		mfRows = new ArrayList<>();
	}
	
	public void setWidth(int w)
	{
		width = w;
	}
	
	public MyFormRow addRow()
	{
		return addRow(null);		
	}

	public MyFormRow addRow(String rowName)
	{
		return addRow(FlowLayout.CENTER,rowName);
	}

	public MyFormRow addRowL()
	{
		return addRowL(null);
	}

	public MyFormRow addRowL(String rowName)
	{
		return addRow(FlowLayout.LEFT,rowName);
	}
	
	public MyFormRow addRowR()
	{
		return addRowR(null);
	}
	
	public MyFormRow addRowR(String rowName)
	{
		return addRow(FlowLayout.RIGHT,rowName);
	}
	
	public MyFormRow addRow(int align)
	{
		return addRow(align,null);
	}
	
	public MyFormRow addRow(int align,String rowName)
	{
		MyFormRow r = new MyFormRow(width,align,rowName);
		mfRows.add(r);
		return r;
	}

	public void setVisibleRow(String rowName,boolean b)
	{
		MyFormRow row = MyCollection.findElm(mfRows,mfr->mfr.getName().equals(rowName));
		if( row!=null )
		{
			row.setVisible(b);
		}
	}
		
	public void addSeparator()
	{
		addSeparator(0.8);
	}

	public void addSeparator(double p)
	{
		RowSeparator sep = new RowSeparator(p);
		MyAwt.setPreferredWidth((int)(width*p),sep);
		addRow(FlowLayout.CENTER).add(sep);
	}
	
	public MyForm reset()
	{
		formPanel.removeAll();
		mfRows.clear();
		return this;
	}
	
	public void makeForm()
	{
		// Recorro las filas
		for(MyFormRow r:mfRows)
		{
			int align = r.getAlign();
			JPanel rowPanel = new JPanel(new FlowLayout(align));

			// Recorro cada fila
			for(int i=0; i<r.getComponents().size(); i++)
			{
				Component c = r.getComponents().get(i);
				
				// agrego al panel
				rowPanel.add(c);
			}

			formPanel.add(rowPanel);
		}
	}
	
	public void setEnabled(boolean b)
	{
		MyAwt.setEnabled(contentPane,b);
	}
		
	private Map<?,?> currState = null;
	public void setDiabledTemporally(boolean disable)
	{
		if( disable )
		{
			currState = MyAwt.disableTemporally(c());
		}
		else
		{
			MyAwt.restoreDisabled(currState);			
		}
	}

	public JPanel c()
	{
		return contentPane;
	}
	
	public void requestFocus()
	{
		for(MyFormRow row:mfRows)
		{
			for(Component cmp:row.getComponents())
			{
				if( _aceptaFocus(cmp) )
				{
					cmp.requestFocus();
					return;
				}
			}
		}
	}
	
	private boolean _aceptaFocus(Component cmp)
	{
		return cmp.isEnabled() && (cmp.getClass().isAssignableFrom(JLabel.class) || cmp.getClass().isAssignableFrom(JPanel.class));
	}

}
