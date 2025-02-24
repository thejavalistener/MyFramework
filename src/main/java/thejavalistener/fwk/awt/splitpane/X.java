package thejavalistener.fwk.awt.splitpane;

import java.awt.*;
import javax.swing.*;

public class X {
    public static void main(String[] args) {
        JFrame f = new JFrame("separator");
        JPanel p = new JPanel();

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");

        p.add(label1);
        p.add(separator);
        p.add(label2);
        p.setLayout(new GridLayout(1, 0));

        f.add(p);
        f.setSize(400, 400);
        f.setVisible(true);
    }
}
