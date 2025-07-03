package thejavalistener.fwk.awt;

import java.awt.event.ActionListener;
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

//public class MyMenu
//{
//	private static final String SEPARATOR="<SEPARATOR>";
//
//	private ActionListener listener;
//	private final JPopupMenu popupMenu=new JPopupMenu();
//	private final Map<String,Object> menuTree=new LinkedHashMap<>();
//
//	public void setActionListener(ActionListener listener)
//	{
//		this.listener = listener;
//	}
//	
//	public void addItem(String path)
//	{
//		String[] parts=path.split("/");
//		if(parts.length<2)
//		{
//			return; // path inválido
//		}
//
//		Map<String,Object> currentMap=menuTree;
//		for(int i=1; i<parts.length; i++)
//		{
//			String part=parts[i];
//			if(part.isEmpty()) continue;
//
//			boolean isSeparator=!part.chars().anyMatch(ch -> ch!='-');
//
//			if(i==parts.length-1)
//			{
//				// Último nivel
//				if(isSeparator)
//				{
//					currentMap.put(UUID.randomUUID().toString(),SEPARATOR);
//				}
//				else
//				{
//					currentMap.putIfAbsent(part,new LinkedHashMap<>());
//				}
//			}
//			else
//			{
//				// Nivel intermedio
//				currentMap=(Map<String,Object>)currentMap.computeIfAbsent(part,k -> new LinkedHashMap<>());
//			}
//		}
//	}
//
//	private void buildMenu(JComponent parent, Map<String,Object> currentLevel)
//	{
//		for(Map.Entry<String,Object> entry:currentLevel.entrySet())
//		{
//			String name=entry.getKey();
//			Object value=entry.getValue();
//
//			if(SEPARATOR.equals(value))
//			{
//				parent.add(new JSeparator());
//			}
//			else if(value instanceof Map)
//			{
//				Map<String,Object> subMap=(Map<String,Object>)value;
//				if(!subMap.isEmpty())
//				{
//					JMenu menu=new JMenu(name);
//					buildMenu(menu,subMap);
//					parent.add(menu);
//				}
//				else
//				{
//					JMenuItem item=new JMenuItem(name);
//					parent.add(item);
//
//					if( listener!=null )
//					{
//						item.addActionListener(listener);
//					}
//				}
//			}
//		}
//	}
//	
//	public void show(Component parent, int align)
//	{
//		show(parent, align, 0, 0);
//	}
//
//	public void show(Component parent, int align, int hgap, int vgap)
//	{
//		if(parent==null) return;
//
//		popupMenu.removeAll(); // limpiar antes de construir
//
//		buildMenu(popupMenu,menuTree);
//
//		Dimension size=parent.getSize();
//		Dimension menuSize=popupMenu.getPreferredSize();
//
//		int x;
//		if(align<0)
//		{
//			x=0;
//		}
//		else if(align==0)
//		{
//			x=(size.width-menuSize.width)/2;
//		}
//		else
//		{
//			x=size.width-menuSize.width;
//		}
//
//		x += hgap;
//		int y=size.height + vgap; // Justo debajo del parent más vgap
//		popupMenu.show(parent,x,y);
//	}
//
//
//	public static void main(String[] args)
//	{
//		MyAwt.setWindowsLookAndFeel();
//
//		JButton button=new JButton("Mostrar Menú re copado, capoo");
//		MyMenu menu=new MyMenu();
//		menu.setActionListener(e->System.out.println(e.getActionCommand()));
//		
//		menu.addItem("/Item1");
//		menu.addItem("/-----");
//		menu.addItem("/Pepino");
//		menu.addItem("/Item2/Item22");
//		menu.addItem("/Item1/Item11/Item111");
//		menu.addItem("/Item2/-----");
//		menu.addItem("/Item2/Item23");
//		menu.addItem("/Item2/Item22/Locomia");
//
//		button.addActionListener(e -> menu.show(button,1,10,0)); // 1: derecha
//
//		JFrame frame=new JFrame("Custom Menu Example");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLayout(new FlowLayout());
//		frame.add(button);
//		frame.setSize(300,200);
//		frame.setVisible(true);
//	}
//}


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class MyMenuBKP
{
    private static final String SEPARATOR = "<SEPARATOR>";

    private final JPopupMenu popupMenu = new JPopupMenu();
    private final Map<String, Object> menuTree = new LinkedHashMap<>();
    private final Map<String, ActionListener> listeners = new LinkedHashMap<>();

    
    
    public void addItem(String path)
    {
    	addItem(path,null);
    }
    
    public void addItem(String path, ActionListener listener)
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

          
        // Mostrar el popup en la posición exacta indicada por el Point
        popupMenu.show(parent, location.x, location.y);
    }


    public static void main(String[] args)
    {
        MyAwt.setWindowsLookAndFeel();

        JButton button = new JButton("Mostrar Menú re copado, capoo");
        MyMenuBKP menu = new MyMenuBKP();

        menu.addItem("/Item1", e -> System.out.println("Click en Item1"));
        menu.addItem("/-----", null);
        menu.addItem("/Pepino", e -> System.out.println("Click en Pepino"));
        menu.addItem("/Item2/Item22", e -> System.out.println("Click en Item22"));
        menu.addItem("/Item1/Item11/Item111", e -> System.out.println("Click en Item111"));
        menu.addItem("/Item2/-----", null);
        menu.addItem("/Item2/Item23", e -> System.out.println("Click en Item23"));
        menu.addItem("/Item2/Item22/Locomia", e -> System.out.println("Click en Locomia"));

        button.addActionListener(e -> menu.show(button, 1, 10, 0)); // 1: derecha

        JFrame frame = new JFrame("Custom Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}

