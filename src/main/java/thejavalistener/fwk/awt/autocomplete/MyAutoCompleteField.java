package thejavalistener.fwk.awt.autocomplete;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.util.string.MyString;

public class MyAutoCompleteField<T> 
{
	private CustomTextField textField;
    private List<T> items = new ArrayList<>();
    private Function<T, String> renderer = Object::toString;
    private boolean ignoreUpdate = false;
    private MyBorderLayout contentPane;;
    
    public MyAutoCompleteStyle style = new MyAutoCompleteStyle();

    private ActionListener actionListener;
    
    private T matchedItem;
    
    public MyAutoCompleteField()
    {
    	this(10);
    }
    
    public MyAutoCompleteStyle getStyle()
    {
    	return style;
    }
    
    public void setItems(List<T> items) {
        this.items = items;
    }
    
    public void setActionListener(ActionListener al)
    {
    	this.actionListener = al;
    }

    public void setTToString(Function<T, String> tToString) {
        this.renderer = tToString;
    }

    public MyAutoCompleteField(int cols) 
    {
    	textField = new CustomTextField(cols);
    	textField.getDocument().addDocumentListener(new DocumentListener() 
    	{
            public void insertUpdate(DocumentEvent e) { handleAutoComplete(); }
            public void removeUpdate(DocumentEvent e) { /* No completar */ }
            public void changedUpdate(DocumentEvent e) {}
        });
    	
    	textField.addActionListener(actionListener);
    	contentPane = new MyBorderLayout();
    	contentPane.add(textField,BorderLayout.CENTER);
    }
    
    public Component c()
    {
    	return contentPane;
    }
    
    public String getText()
    {
    	return textField.getText();
    }

    private void handleAutoComplete() {
        if (ignoreUpdate) return;

        String text = textField.getText();
        if (text.isEmpty()) return;

        for (T item : items) 
        {
            String value = renderer.apply(item);
            String valor = MyString.normalize(renderer.apply(item)).toLowerCase();
            String entrada = MyString.normalize(textField.getText()).toLowerCase();
            if (valor.startsWith(entrada) && !valor.equals(entrada)) {
                ignoreUpdate = true;

                matchedItem = item;
                SwingUtilities.invokeLater(() -> {
                	textField.setText(value);
                	textField.setCaretPosition(text.length());
                	textField.moveCaretPosition(value.length()); // selecciona el resto
                    ignoreUpdate = false;
                });
                

                break;
            }
        }
    }
    
    public T getMatchedItem() 
    {
        return matchedItem;
    }
    
    public JTextField getTextField()
    {
    	return textField;
    }

	public void applyStyle()
	{
		contentPane.setInsets(style.insets);
		contentPane.setBackground(style.background);
		textField.setBackground(style.background);
		textField.setForeground(style.foreground);
		textField.setForeground(style.foreground);
		textField.setBorder(style.border);		
		textField.setFont(style.font);
		textField.setCaretColor(style.caretColor);
		contentPane.validate();
	}
	
	
	// HINT
	private String hint;

	public void setHint(String hintText) 
	{
	    this.hint = hintText;
	}

	class CustomTextField extends JTextField
	{
		public CustomTextField(int c)
		{
			super(c);
		}
		
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        if (getText().isEmpty() && hint != null) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setColor(style.hintColor);
	            g2.setFont(getFont().deriveFont(Font.PLAIN));
	            Insets insets = getInsets();
	            g2.drawString(hint, insets.left + 2, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 4);
	            g2.dispose();
	        }
	    }
	}

	public void requestFocus()
	{
		textField.requestFocus();
	}
}