package thejavalistener.fwk.awt.link;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyLeftLayout;
import thejavalistener.fwk.awt.panel.MyPanel;
import thejavalistener.fwk.util.MyLog;

public class MyLink
{
	public static final int NO_BORDER=0;
	public static final int TOP_BORDER = MyPanel.TOP_BORDER;
	public static final int LEFT_BORDER = MyPanel.LEFT_BORDER;
	public static final int BOTTOM_BORDER = MyPanel.BOTTOM_BORDER;
	public static final int RIGHT_BORDER = MyPanel.RIGHT_BORDER;
	
	public static final int LABEL = 1;
	public static final int LINK = 2;
	public static final int TOGGLE_ON = 3;
	public static final int TOGGLE_ONOF = 4;
	
	private JLabel jLabel = null;
	private MyPanel backgroundPane;
	private MyPanel contentPane;

	private Cursor defaultCursor = null;
	private Cursor clickCursor = null;
	
	private boolean clickeable = true;
	private boolean selected = false;
	private boolean selectable = false;
	private boolean unselectable = false;
			
	private ActionListener listener = null;
	private ActionListener rightButtonListener = null;

	private int currConfiguration;
	private int prevConfiguration;
	
	
	
	private HashMap<String,Object> relatedObjects = null;
	
	private MyLink outer;
	
	// estilo
	private MyLinkStyle style = new MyLinkStyle();
		
	// ctor
	public MyLink(String text)
	{
		this(text,LABEL);
	}
	
	// ctor
	public MyLink()
	{
		this("",LABEL);
	}

	public JPanel c()
	{
		applyStyle();
		return contentPane;
	}
	
	public MyLink(String text,int linkType)
	{
		this(text,linkType,null,null);
	}
	
	public MyLink(String text,int linkType,String actionKey,Object actionObject)
	{
		jLabel = new JLabel(text);
		backgroundPane = new MyLeftLayout();
		backgroundPane.add(jLabel);
		
		contentPane = new MyBorderLayout(backgroundPane,BorderLayout.CENTER);
		
		defaultCursor = Cursor.getDefaultCursor();
		clickCursor = new Cursor(Cursor.HAND_CURSOR);
		configureAs(linkType);
		setText(text);
		
		backgroundPane.addMouseListener(new EscuchaMouse());
		
		relatedObjects = new HashMap<>();
		if( actionKey!=null )
		{
			relatedObjects.put(actionKey,actionObject);
		}
		
		outer = this;
		applyStyle();
	}
	
	public void setEnabled(boolean b)
	{				
		if( !b )
		{
			prevConfiguration = currConfiguration;
			configureAs(LABEL);			
		}
		else
		{
			configureAs(prevConfiguration);						
		}
	}
	
	public void setBorder(int border,int px,Color c)
	{
		contentPane.drawLine(border,px,c);
	}
	
	public MyLinkStyle getStyle()
	{
		return style;
	}
	
	public void setTextAlign(int align)
	{
		((FlowLayout)backgroundPane.getLayout()).setAlignment(align);
	}	
		
	public void setText(String text)
	{
		jLabel.setText(text);
	}
	
	public String getText()
	{
		return jLabel.getText();
	}
	
	// setters

	public void setSelected(boolean b)
	{
		setSelected(b,false);		
	}
	
	public void setSelected(boolean b,boolean throwEvent)
	{
		selected = b;
		_applyStyle(selected,false);
		
		if( throwEvent )
		{
			listener.actionPerformed(new ActionEvent(this,1,getText()));
		}		
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setUnselectable(boolean b)
	{
		this.unselectable = b;
	}
	
	public boolean isUnselectable()
	{
		return unselectable;
	}
		
	public boolean isClickeable()
	{
		return clickeable;
	}
	
	public void setClickeable(boolean b)
	{
		clickeable = b;;
	}

	public boolean isSelectable()
	{
		return selectable;
	}
	
	public void setSelectable(boolean b)
	{
		this.selectable = b;
	}

	public void setRightButtonListener(ActionListener listener)
	{
		this.rightButtonListener = listener;
	}
	
	public void setActionListener(ActionListener listener)
	{
		this.listener = listener;
	}
	
	public void configureAs(int linkType)
	{
		currConfiguration = linkType;
		switch(linkType)
		{
			case LABEL:
				setClickeable(false);
				setSelectable(false);
				setUnselectable(false);
				break;
			case LINK:
				setClickeable(true);
				setSelectable(false);
				setUnselectable(false);
				break;
			case TOGGLE_ON:
				setClickeable(false);
				setSelectable(true);
				setUnselectable(false);
				break;
			case TOGGLE_ONOF:
				setClickeable(false);
				setSelectable(true);
				setUnselectable(true);
				break;
		}
	}
	
//	private void _applyFontStyle(String modo)
//	{
//		switch(modo)  //unselectedRolloverBackground
//		{
//			case "normal": // normal
//				jLabel.setForeground(unselectedForeground);
//				background.setBackground(unselectedBackground);
//				break;
//			case "rolloverOnSelected": // rollover
//				jLabel.setForeground(selectedRolloverForeground);
//				background.setBackground(selectedRolloverBackground);				
//				break;
//			case "rolloverOnUnselected": // rollover
//				jLabel.setForeground(unselectedRolloverForeground);
//				background.setBackground(unselectedRolloverBackground);				
//				break;
//			case "selected": // selected
//				jLabel.setForeground(selectedForeground);
//				background.setBackground(selectedBackground);	
//				MyAwt.setBackground(background,selectedBackground);
//				break;
//			default:
//				throw new RuntimeException("modo debe ser \"normal\", \"rolloverOnSelected\", \"rolloverOnUnselected\" o \"selected\"");
//		}
//	}

	
	public void addRelatedObject(String key, Object related)
	{
		relatedObjects.put(key,related);
	}

	public Object getRelatedObject(String key)
	{
		return relatedObjects.get(key);
	}

	public void removeRelatedObject(String key)
	{
		relatedObjects.remove(key);
	}
	
	class EscuchaMouse extends MouseAdapter
	{
		@Override
		public void mouseEntered(MouseEvent e)
		{
			if( clickeable ) // es un link tipo web
			{
				_applyStyle(false,true);
				jLabel.setCursor(clickCursor);
				backgroundPane.setCursor(clickCursor);
				contentPane.setCursor(clickCursor);
			}
			else 
			{
				if( selectable ) // puede ser TOGGLE_ON o TOGGLE_ONOF
				{
					if( !selected )
					{
						_applyStyle(false,true);
						jLabel.setCursor(clickCursor);
						backgroundPane.setCursor(clickCursor);
						contentPane.setCursor(clickCursor);
					}
					else
					{
						if( unselectable ) // TOGGLE_ONOF
						{
							_applyStyle(true,true);
							jLabel.setCursor(clickCursor);
							backgroundPane.setCursor(clickCursor);
							contentPane.setCursor(clickCursor);
						}
						else
						{
//							_applyFontStyle("rollover"); // YA ESTABA COMENTADO
							backgroundPane.setCursor(defaultCursor);
							jLabel.setCursor(defaultCursor);
							backgroundPane.setCursor(defaultCursor);
							contentPane.setCursor(defaultCursor);
						}
					}
				}		
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e)
		{		
			if( clickeable )
			{
				_applyStyle(false,false);
				jLabel.setCursor(defaultCursor);
				backgroundPane.setCursor(defaultCursor);
				contentPane.setCursor(defaultCursor);
			}
			else
			{
				if( selectable )
				{
					if( !selected )
					{
						_applyStyle(false,false);
						jLabel.setCursor(defaultCursor);
						backgroundPane.setCursor(defaultCursor);
						contentPane.setCursor(defaultCursor);
					}
					else
					{
						if( unselectable )
						{
							_applyStyle(true,false);
							jLabel.setCursor(clickCursor);
							backgroundPane.setCursor(clickCursor);
							contentPane.setCursor(clickCursor);
						}
					}
				}			
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			if( rightButtonListener!=null && SwingUtilities.isRightMouseButton(e) )
			{
				ActionEvent ae = new ActionEvent(this,1,getText());
				rightButtonListener.actionPerformed(ae);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			ActionEvent evt = new ActionEvent(outer,1,getText());

			if( clickeable )
			{
				_applyStyle(false,false);

				jLabel.setCursor(defaultCursor);
				backgroundPane.setCursor(defaultCursor);
				contentPane.setCursor(defaultCursor);
				if( listener!=null ) listener.actionPerformed(evt);
			}
			else
			{
				if( selectable )
				{
					if( !selected )
					{
						selected = true;
//						_applyFontStyle("selected");
						_applyStyle(true,false);

						jLabel.setCursor(defaultCursor);
						backgroundPane.setCursor(defaultCursor);
						contentPane.setCursor(defaultCursor);

						if( listener!=null) listener.actionPerformed(evt);
					}
					else
					{
						if( unselectable )
						{
							selected = false;
							
//							_applyFontStyle("normal");
							_applyStyle(false,false);

							jLabel.setCursor(defaultCursor);
							backgroundPane.setCursor(defaultCursor);
							contentPane.setCursor(defaultCursor);

							if( listener!=null ) listener.actionPerformed(evt);
						}
					}
				}
			}
			
			return;
		}
	}
	public void setStyle(MyLinkStyle style)
	{
		this.style = style;
	}
	
	public void applyStyle()
	{
		if( !xxx )
		{
			jLabel.setFont(style.linkFont);
	
			jLabel.setForeground(style.linkForegroundUnselected);
			contentPane.setInsets(style.linkInsets);
			contentPane.setBackground(style.linkBackground);
	
			backgroundPane.setBackground(style.linkBackground);
			backgroundPane.setInsets(style.linkBackgroundInsets);		
		}
	}
	
	boolean xxx=false;
	private void _applyStyle(boolean selected,boolean rollover)
	{
		xxx= true;
		if( selected )
		{
			if( !rollover )
			{
				jLabel.setForeground(style.linkForegroundSelected);
				backgroundPane.setBackground(style.linkBackgroundSelected);
			}
			else
			{
				jLabel.setForeground(style.linkForegroundRolloverSelected);
				backgroundPane.setBackground(style.linkBackgroundRolloverSelected);			
			}
			contentPane.drawLine(style.borderSelected,style.borderWidth,style.borderColor);
		}
		else
		{
			if( !rollover )
			{
				contentPane.drawLine(0,style.borderWidth,style.borderColor);
				jLabel.setForeground(style.linkForegroundUnselected);
				backgroundPane.setBackground(style.linkBackgroundUnselected);
			}
			else
			{
				contentPane.drawLine(style.borderRollover,style.borderWidth,style.borderColor);
				jLabel.setForeground(style.linkForegroundRolloverUnselected);
				backgroundPane.setBackground(style.linkBackgroundRolloverUnselected);			
			}			
		}	
		
	}
	
	@Override
	public String toString()
	{
		return getText()+". selected: "+isSelected();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return contentPane.equals(obj);
	}

	public void setOpaque(boolean b)
	{
		jLabel.setOpaque(b);
	}
}
