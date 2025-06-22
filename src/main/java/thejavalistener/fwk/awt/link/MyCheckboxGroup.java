package thejavalistener.fwk.awt.link;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import thejavalistener.fwk.util.MyCollection;

public class MyCheckboxGroup
{
    private List<JCheckBox> checkboxes;
    private JCheckBox selected;

    private ActionListener listener;
    private boolean listenerWorking = true;

    private EscuchaCheckboxes escuchaCheckboxes;

    public MyCheckboxGroup()
    {
        checkboxes = new ArrayList<>();
        escuchaCheckboxes = new EscuchaCheckboxes();
    }

    public void setEnabled(boolean b)
    {
        for (JCheckBox cb : checkboxes)
        {
            cb.setEnabled(b);
        }
    }

    public void setOthersEnabled(boolean b)
    {
        for (JCheckBox cb : checkboxes)
        {
            if (!cb.isSelected())
            {
                cb.setEnabled(b);
            }
        }
    }

    public void setActionListener(ActionListener listener)
    {
        this.listener = listener;
    }

    public void addCheckbox(JCheckBox cb)
    {
        boolean prev = setListenerWorking(false);

        cb.addActionListener(escuchaCheckboxes);
        cb.setSelected(false);
        checkboxes.add(cb);

        setListenerWorking(prev);
    }

    public void setSelected(int idx)
    {
        setSelected(idx, false);
    }

    public void setSelected(int idx, boolean throwEvent)
    {
        if (selected != null)
        {
            selected.setSelected(false);
        }

        selected = checkboxes.get(idx);
        selected.setSelected(true);

        if (listener != null && listenerWorking && throwEvent)
        {
            ActionEvent e = new ActionEvent(selected, 1, selected.getText());
            listener.actionPerformed(e);
        }
    }

    public JCheckBox removeCheckbox(int idx)
    {
        return checkboxes.remove(idx);
    }

    public List<JCheckBox> getCheckboxes()
    {
        return checkboxes;
    }

    public boolean setListenerWorking(boolean b)
    {
        boolean prev = listenerWorking;
        listenerWorking = b;
        return prev;
    }

    public int getSelectedIndex()
    {
        return MyCollection.findPos(checkboxes, cb -> cb.isSelected());
    }

    public JCheckBox getSelected()
    {
        return selected;
    }

    public void removeAll()
    {
        checkboxes.clear();
        selected = null;
    }

    class EscuchaCheckboxes implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JCheckBox cb = (JCheckBox) e.getSource();
            if (cb == selected)
            {
                // Permitir deseleccionar (opcional)
                cb.setSelected(true); // Evitar que el usuario lo desmarque
                return;
            }

            if (selected != null)
            {
                selected.setSelected(false);
            }

            selected = cb;
            selected.setSelected(true);

            if (listener != null && listenerWorking)
            {
                listener.actionPerformed(e);
            }
        }
    }
}