package thejavalistener.fwk.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import thejavalistener.fwk.awt.MyAwt;

public class MyMenu {
    private static final String SEPARATOR = "<SEPARATOR>";

    private final JPopupMenu popupMenu = new JPopupMenu();
    private final Map<String, Object> menuTree = new LinkedHashMap<>();

    public void addItem(String path) {
        String[] parts = path.split("/");
        if (parts.length < 2) {
            return; // path inválido
        }

        Map<String, Object> currentMap = menuTree;
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) continue;

            boolean isSeparator = part.equals("-----");

            if (i == parts.length - 1) {
                // Último nivel
                if (isSeparator) {
                    currentMap.put(UUID.randomUUID().toString(), SEPARATOR);
                } else {
                    currentMap.putIfAbsent(part, new LinkedHashMap<>());
                }
            } else {
                // Nivel intermedio
                currentMap = (Map<String, Object>) currentMap.computeIfAbsent(part, k -> new LinkedHashMap<>());
            }
        }
    }

    private void buildMenu(JComponent parent, Map<String, Object> currentLevel) {
        for (Map.Entry<String, Object> entry : currentLevel.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();

            if (SEPARATOR.equals(value)) {
                parent.add(new JSeparator());
            } else if (value instanceof Map) {
                Map<String, Object> subMap = (Map<String, Object>) value;
                if (!subMap.isEmpty()) {
                    JMenu menu = new JMenu(name);
                    buildMenu(menu, subMap);
                    parent.add(menu);
                } else {
                    JMenuItem item = new JMenuItem(name);
                    parent.add(item);
                }
            }
        }
    }

    public void show(Component parent, int align) {
        if (parent == null) return;

        popupMenu.removeAll(); // limpiar antes de construir

        buildMenu(popupMenu, menuTree);

        Dimension size = parent.getSize();
        Dimension menuSize = popupMenu.getPreferredSize();

        int x;
        if (align < 0) {
            x = 0;
        } else if (align == 0) {
            x = (size.width - menuSize.width) / 2;
        } else {
            x = size.width - menuSize.width;
        }

        int y = size.height; // Justo debajo del parent
        popupMenu.show(parent, x, y);
    }

    public static void main(String[] args) {
        MyAwt.setWindowsLookAndFeel();

        JButton button = new JButton("Mostrar Menú");
        MyMenu menu = new MyMenu();

        menu.addItem("/Item1");
        menu.addItem("/-----");
        menu.addItem("/Pepino");
        menu.addItem("/Item2/Item22");
        menu.addItem("/Item1/Item11/Item111");
        menu.addItem("/Item2/-----");
        menu.addItem("/Item2/Item23");
        menu.addItem("/Item2/Item22/Locomia");

        button.addActionListener(e -> menu.show(button, 1)); // 1: derecha

        JFrame frame = new JFrame("Custom Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
