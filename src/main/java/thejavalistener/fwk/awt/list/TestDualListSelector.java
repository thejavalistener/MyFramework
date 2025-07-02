package thejavalistener.fwk.awt.list;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import thejavalistener.fwk.awt.MyAwt;

public class TestDualListSelector
{

	public static void main(String[] args)
	{
		MyAwt.setWindowsLookAndFeel();
		
		SwingUtilities.invokeLater(() -> {
			List<String> todos=Arrays.asList("Rojo","Verde","Azul","Amarillo","Negro","pepino","orep","qwe","CAno","234sf","RETEp","7opsp","Mopsp","p5p","uipsp","dopsp","4opsp","ytopsp","12psp","po4545sp");
			List<String> seleccionados=Arrays.asList("Verde","Negro");

			JFrame frame=new JFrame("Selector Doble Lista");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(500,350);

			MyDualListSelector<String> selector=new MyDualListSelector<>(MyDualListSelector.ALLOW_ADD+MyDualListSelector.ALLOW_RMV+MyDualListSelector.ALLOW_SALL+MyDualListSelector.ALLOW_RALL);
			selector.setItems(todos,seleccionados);

			selector.setDualListListener(new EscuchaSelector());

			frame.add(selector.c());
			frame.setVisible(true);
		});
	}
	
	static class EscuchaSelector implements MyDualListListener<String>
	{
		@Override
		public boolean removeItemRequested(String item)
		{
			System.out.println("Chau ["+item+"]...");
			return !item.startsWith("M");
		}

		@Override
		public String updateItemRequested(String item)
		{
			String x = JOptionPane.showInputDialog("Nuevo item:");
	        return x!=null && !x.isEmpty()?x:null;
		}

		@Override
		public String createItemRequested()
		{
			String x = JOptionPane.showInputDialog("Modifique el item:");
	        return x!=null && !x.isEmpty()?x:null;
		}

//		@Override
//		public void afterRequest(MyList<String> list)
//		{
//			list.sort((a,b)->a.compareTo(b));
//		}

		@Override
		public void afterItemChangeHook(MyList<String> list)
		{
		}

		@Override
		public void itemMoved(String item, MyList<String> fromList, MyList<String> toList)
		{
			System.out.println(item+" pasó de ...");
		}
	}
}
