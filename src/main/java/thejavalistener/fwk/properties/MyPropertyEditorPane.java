//package thejavalistener.fwk.properties;
////package myframework.properties;
////
////import java.awt.event.ActionEvent;
////import java.awt.event.ActionListener;
////import java.awt.event.ItemEvent;
////import java.awt.event.ItemListener;
////import java.util.List;
////
////import javax.swing.JButton;
////import javax.swing.JOptionPane;
////import javax.swing.JScrollPane;
////import javax.swing.event.ListSelectionEvent;
////import javax.swing.event.ListSelectionListener;
////
////import myframework.awt.MyJTextField;
////import myframework.awt.MyTextField;
////import myframework.awt.form.MyForm;
////import myframework.awt.link.MyLink;
////import myframework.awt.list.MyJList;
////import myframework.awt.list.MyList;
////import myframework.awt.list.MyListEvent;
////import myframework.awt.mytextarea.MyTextArea;
////import myframework.awt.panel.MyCenterLayout;
////import myframework.util.NamedValue;
////import myframework.util.Pair;
////
////public class MyPropertyEditorPane extends MyCenterLayout
////{
////	private MyForm form;
////	private MyTextField tfPropname;
////	private MyList<Pair> lstQueries;
////	private JButton bRmv; 
////	private JButton bAdd;
////	private JButton bSave;
////	
////	private boolean editando=false;
////	private boolean agregando=false;
////
////	private int newQueryNo=1;
////
////	private String queSonLasProperties;
////	private List<Pair> properties;
////	
////	private MyTextArea taConsole;
////	
////	
////	public MyPropertyEditorPane(List<Pair> properties)
////	{
////		this.properties = properties;
////
////		form = new MyForm();
////		double wL = .3;
////		double wC = .7;
////
////		lstQueries = new MyJList<>();
////		lstQueries.setVisibleRowCount(13);
////		lstQueries.setTToString(f->f.getA().toString());		
////		lstQueries.setItems(properties);
////		lstQueries.setListSelectionListener(new EscuchaLista());
////		
////		form.addRow().add(new MyLink(queSonLasProperties).c()).layout(1);
////		
////		JScrollPane sp = new JScrollPane(lstQueries.c());
////		sp.setBorder(null);
////		form.addRow().add(sp).layout(1);
////		
////		tfPropname = new MyJTextField();
////
////		MyLink lnkPropname = new MyLink("Name");
////		
////		form.addRow().add(lnkPropname.c()).layout(1);
////		form.addRow().add(tfPropname.c()).layout(1);
////
////		bRmv = new JButton("Remove");
////		bRmv.addActionListener(new EscuchaRmv());
////		bAdd = new JButton("Add");
////		bAdd.addActionListener(new EscuchaAdd());
////		bSave = new JButton("Save");
////		bSave.addActionListener(new EscuchaSave());
////		form.addRow().add(bRmv).add(bAdd).add(bSave);
////		form.makeForm();
////	}
////		
////	class EscuchaLista implements ListSelectionListener
////	{
////		@Override
////		public void valueChanged(ListSelectionEvent evt)
////		{
////			MyListEvent<NamedValue<String>> e = (MyListEvent)evt;
////			if(e.getItem()!=null || e.getListEventType()==MyListEvent.ITEM_UNSELECTED)
////			{
////			}
////			
////			if( e.getClickCount()==2)
////			{
////				editando=true;
////			}
////		}
////	}
////	
////	class EscuchaSave implements ActionListener
////	{
////		@Override
////		public void actionPerformed(ActionEvent e)
////		{
////			// tomo el item seleccionado (puede ser null)
////			Pair selected = lstQueries.getSelectedItem();
////			
////			// work tiene el nuevo nombre y el nuevo valor de la propiedad
////			
////			NamedValue<String> work = new NamedValue<>();
////			
////			work.setName(tfPropname.getText());
////			work.setValue(getConsole().getText());
////			
////			// si estaba editando borro la anterior
////			if( selected!=null )
////			{
////				// remuevo el seleccionado para volverlo a agregar
////				String oldName = selected.getName();
////				properties.remove(oldName);
////				lstQueries.removeItem(nv->nv.getName().equals(oldName));
////				lstQueries.addItem(work,true);
////			}
////			
////			properties.put(work);
////			
////			editando=false;
////			agregando=false;
////			_cargarQueriesYDominios(true);
////			_setEstadoComponentes();
////		}
////	}
////
////	class EscuchaAdd implements ActionListener
////	{
////		@Override
////		public void actionPerformed(ActionEvent e)
////		{
////			lstQueries.setUnselected();
////			
////			agregando=true;
////			editando=true;
////			_setEstadoComponentes();
////			tfPropname.selectAll();
////			tfPropname.requestFocus();
////		}
////	}
////
////	class EscuchaRmv implements ActionListener
////	{
////		@Override
////		public void actionPerformed(ActionEvent e)
////		{
////			if( !editando )
////			{
////				if(properties.size()==1) //lstQueries.getItems().size()<=1 )
////				{
////					showErrorMessage("Al menos debes dejar un query predefinido","Error");
////				}
////				else
////				{
////					int ok = showConfirmWarningMessage("Eliminar este query?","Confirmación");
////					if( ok==JOptionPane.YES_OPTION )
////					{
////						NamedValue<String> removed = lstQueries.removeSelectedItem();
////						properties.remove(removed);
////						properties.store();
////					}
////				}
////			}
////
////			_cargarQueriesYDominios(!editando);
////			editando=false;
////			agregando=false;
////			_setEstadoComponentes();
////		}
////	}
////	
////	class EscuchaEjecutar implements ActionListener
////	{
////		@Override
////		public void actionPerformed(ActionEvent e)
////		{
////			NamedValue<String> nv = lstQueries.getSelectedItem();
////			Object[] args = nv!=null?new Object[]{nv.getName()}:new Object[0];
//////			getMyApp().changeTopScreen(HQLSelectPredefQuery.class,getOuter(),args);
////		}
////	}
////	
////	class EscuchaDomain implements ItemListener
////	{
////		@Override
////		public void itemStateChanged(ItemEvent e)
////		{
////			_cargarQueriesYDominios(false);
////			editando = false;
////			agregando = false;
////			_setEstadoComponentes();
////		}
////	}
////}