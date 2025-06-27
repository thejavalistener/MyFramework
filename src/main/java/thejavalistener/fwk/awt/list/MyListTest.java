package thejavalistener.fwk.awt.list;

import java.util.List;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.testui.MyTestUI;
import thejavalistener.fwk.awt.textarea.MyTextField;

public class MyListTest
{
	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		
		MyList<String> lst = new MyList<>();
		List<String> items = List.of("Pablo","Carlos","Pedro","Cacho","Rolo","Tito","Beni","Cumpa");
		lst.setItems(items);

		MyTestUI ui = MyTestUI.test(new MyScrollPane(lst.c()));
		ui.addTextField("tfItem")
		   .addButton("Add",l->lst.addItem(ui.getString("tfItem")))
		   .run();
		
		lst.setListListener(e->{
			MyListEvent<String> evt = (MyListEvent)e;
			
			switch( evt.getClickCount() )
			{
				case 1:
					System.out.println("Que cappooo:"+evt.getItem());
					break;
				case 2:
					String item = lst.removeSelectedItem();
					ui.getTextField("tfItem").setText(item);
			}
			
		});
		
	}
	
	static class EscuchaLista implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
		}
	}
}
