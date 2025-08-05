package thejavalistener.fwk.awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MyMenu
{
    private static final String SEPARATOR = "<SEPARATOR>";

    private final JPopupMenu popupMenu = new JPopupMenu();
    private final Map<String, Object> menuTree = new LinkedHashMap<>();
    private final Map<String, ActionListener> listeners = new LinkedHashMap<>();
    private final Map<String, String> icons = new LinkedHashMap<>();

    public void addItem(String path)
    {
        addItem(path, (ActionListener) null);
    }

    public void addItem(String path, ActionListener listener)
    {
        addItem(path, null, listener);
    }

    public void addItem(String path, String iconFullPathname, ActionListener listener)
    {
        String[] parts = path.split("/");
        if (parts.length < 2)
        {
            return; // path inválido
        }

        Map<String, Object> currentMap = menuTree;
        for (int i = 1; i < parts.length; i++)
        {
            String part = parts[i];
            if (part.isEmpty()) continue;

            boolean isSeparator = !part.chars().anyMatch(ch -> ch != '-');

            if (i == parts.length - 1)
            {
                // Último nivel
                if (isSeparator)
                {
                    currentMap.put(UUID.randomUUID().toString(), SEPARATOR);
                }
                else
                {
                    currentMap.putIfAbsent(part, new LinkedHashMap<>());
                    if (listener != null)
                    {
                        listeners.put(part, listener);
                    }
                    if (iconFullPathname != null)
                    {
                        icons.put(part, iconFullPathname);
                    }
                }
            }
            else
            {
                // Nivel intermedio
                currentMap = (Map<String, Object>) currentMap.computeIfAbsent(part, k -> new LinkedHashMap<>());
            }
        }
    }

    private void buildMenu(JComponent parent, Map<String, Object> currentLevel)
    {
        for (Map.Entry<String, Object> entry : currentLevel.entrySet())
        {
            String name = entry.getKey();
            Object value = entry.getValue();

            if (SEPARATOR.equals(value))
            {
                parent.add(new JSeparator());
            }
            else if (value instanceof Map)
            {
                Map<String, Object> subMap = (Map<String, Object>) value;
                if (!subMap.isEmpty())
                {
                    JMenu menu = new JMenu(name);
                    buildMenu(menu, subMap);
                    parent.add(menu);
                }
                else
                {
                    JMenuItem item = new JMenuItem(name);

//                    String iconPath = icons.get(name);
//                    if (iconPath != null)
//                    {
//                        item.setIcon(new ImageIcon(iconPath));
//                    }
                    String iconPath = icons.get(name);
                    if (iconPath != null)
                    {
                        ImageIcon originalIcon = new ImageIcon(iconPath);
                        Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // tamaño estándar
                        item.setIcon(new ImageIcon(scaledImage));
                    }
                    parent.add(item);

                    ActionListener itemListener = listeners.get(name);
                    if (itemListener != null)
                    {
                        item.addActionListener(itemListener);
                    }
                }
            }
        }
    }

    public void show(Component parent, int align)
    {
        show(parent, align, 0, 0);
    }

    public void show(Component parent, int align, int hgap, int vgap)
    {
        if (parent == null) return;

        popupMenu.removeAll(); // limpiar antes de construir
        buildMenu(popupMenu, menuTree);

        Dimension size = parent.getSize();
        Dimension menuSize = popupMenu.getPreferredSize();

        int x;
        if (align < 0)
        {
            x = 0;
        }
        else if (align == 0)
        {
            x = (size.width - menuSize.width) / 2;
        }
        else
        {
            x = size.width - menuSize.width;
        }

        x += hgap;
        int y = size.height + vgap; // Justo debajo del parent más vgap
        popupMenu.show(parent, x, y);
    }

    public void show(Component parent, Point location)
    {
        if (parent == null || location == null) return;

        popupMenu.removeAll(); // limpiar antes de construir
        buildMenu(popupMenu, menuTree);

        popupMenu.show(parent, location.x, location.y);
    }

    public static void main(String[] args)
    {
        MyAwt.setWindowsLookAndFeel();

        JButton button = new JButton("Mostrar Menú con íconos");
        MyMenu menu = new MyMenu();

        menu.addItem("/Archivo/Abrir", "a.png", e -> System.out.println("Abrir"));
        menu.addItem("/Archivo/Guardar", "b.png", e -> System.out.println("Guardar"));
        menu.addItem("/Archivo/-----", null);
        menu.addItem("/Archivo/Salir", e -> System.exit(0));
        menu.addItem("/Edición/Deshacer", e -> System.out.println("Deshacer"));
        menu.addItem("/Edición/-----", null);
        menu.addItem("/Edición/Copiar", "/Java64/Workspace/MyJavaMusicLibrary/imagenes/roles/piano.png", e -> System.out.println("Copiar"));
        menu.addItem("/Edición/Pegar", "d.png", e -> System.out.println("Pegar"));

        button.addActionListener(e -> menu.show(button, 1, 10, 0)); // 1: derecha

        JFrame frame = new JFrame("Custom Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
