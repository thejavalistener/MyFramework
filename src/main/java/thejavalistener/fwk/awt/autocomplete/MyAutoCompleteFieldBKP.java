package thejavalistener.fwk.awt.autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MyAutoCompleteFieldBKP<T> extends JTextField {
    private List<T> items = new ArrayList<>();
    private Function<T, String> renderer = Object::toString;
    private boolean ignoreUpdate = false;

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setRenderer(Function<T, String> renderer) {
        this.renderer = renderer;
    }

    public MyAutoCompleteFieldBKP() {
        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { handleAutoComplete(); }
            public void removeUpdate(DocumentEvent e) { /* No completar */ }
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void handleAutoComplete() {
        if (ignoreUpdate) return;

        String text = getText();
        if (text.isEmpty()) return;

        for (T item : items) {
            String value = renderer.apply(item);
            if (value.toLowerCase().startsWith(text.toLowerCase()) && !value.equals(text)) {
                ignoreUpdate = true;

                SwingUtilities.invokeLater(() -> {
                    setText(value);
                    setCaretPosition(text.length());
                    moveCaretPosition(value.length()); // selecciona el resto
                    ignoreUpdate = false;
                });

                break;
            }
        }
    }
}