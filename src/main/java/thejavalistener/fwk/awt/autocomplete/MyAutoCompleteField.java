package thejavalistener.fwk.awt.autocomplete;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MyAutoCompleteField<T> 
{
	private JTextField textField;
    private List<T> items = new ArrayList<>();
    private Function<T, String> renderer = Object::toString;
    private boolean ignoreUpdate = false;

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setTToString(Function<T, String> tToString) {
        this.renderer = tToString;
    }

    public MyAutoCompleteField() 
    {
    	textField = new JTextField();
    	textField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { handleAutoComplete(); }
            public void removeUpdate(DocumentEvent e) { /* No completar */ }
            public void changedUpdate(DocumentEvent e) {}
        });
    }
    
    public Component c()
    {
    	return textField;
    }
    
    public String getText()
    {
    	return textField.getText();
    }

    private void handleAutoComplete() {
        if (ignoreUpdate) return;

        String text = textField.getText();
        if (text.isEmpty()) return;

        for (T item : items) {
            String value = renderer.apply(item);
            if (value.toLowerCase().startsWith(text.toLowerCase()) && !value.equals(text)) {
                ignoreUpdate = true;

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
}