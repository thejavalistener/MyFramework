package thejavalistener.fwk.frontend.hql.screen;
//package framework.frontend.hql.screen;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.util.Collections;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import framework.awt.MyJTextField;
//import framework.awt.MyTextField;
//import framework.awt.form.MyForm;
//import framework.awt.link.MyLink;
//import framework.awt.list.MyComboBox;
//import framework.awt.list.MyJComboBox;
//import framework.awt.list.MyJList;
//import framework.awt.list.MyList;
//import framework.awt.list.MyListEvent;
//import framework.frontend.ScreenTemplate;
//import framework.frontend.messages.MyScreenValuesExchange;
//import framework.util.MyProperties;
//import framework.util.NamedValue;
//
//
//@Component
//public class HQLSelectPredefQueryEdit extends ScreenTemplate
//{
//	private MyProperties properties;
//	
//	private MyForm form;
//	private MyTextField tfPropname;
//	private MyList<NamedValue<String>> lstQueries;
//	private MyComboBox<String> cbDomain;
//	private JButton bRmv; 
//	private JButton bAdd;
//	private JButton bSave;
//	private JButton bEjecutar;
//	
//	private boolean editando=false;
//	private boolean agregando=false;
//
//	private int newQueryNo=1;
//	
//	@Autowired
//	private MyScreenValuesExchange values;
//	
//	
//	@Override
//	protected void createUI()
//	{
//		form = getForm().reset();
//		double wL = .3;
//		double wC = .7;
//
//		cbDomain = new MyJComboBox<>();
//		cbDomain.setItemListener(new EscuchaDomain());
//		
////		form.addRow().add(new MyLink("Domain").c(),wL).add(cbDomain.c(),wC);
//		form.addRow().add(new MyLink("Domain").c()).add(cbDomain.c()).layout(wL,wC);;
//				
//		lstQueries = new MyJList<>();
//		lstQueries.setVisibleRowCount(13);
//		lstQueries.setTToString(f->f.getName());		
//		lstQueries.setListSelectionListener(new EscuchaLista());
//		
////		form.addRow().add(new MyLink("Stored Queries").c(),1);
//		form.addRow().add(new MyLink("Stored Queries").c()).layout(1);
//		
//		JScrollPane sp = new JScrollPane(lstQueries.c());
//		sp.setBorder(null);
////		form.addRow().add(sp,1);
//		form.addRow().add(sp).layout(1);
//		
//		tfPropname = new MyJTextField();
//
//		MyLink lnkPropname = new MyLink("Query Name");
//		
//		form.addRow().add(lnkPropname.c()).layout(1);
//		form.addRow().add(tfPropname.c()).layout(1);
//
////		form.addSeparator();
//		bRmv = new JButton("Remove");
//		bRmv.addActionListener(new EscuchaRmv());
//		bAdd = new JButton("Add");
//		bAdd.addActionListener(new EscuchaAdd());
//		bSave = new JButton("Save");
//		bSave.addActionListener(new EscuchaSave());
//		form.addRow().add(bRmv).add(bAdd).add(bSave);
//		form.makeForm();
//		
//		bEjecutar = new JButton("Ejecutar");
//		bEjecutar.addActionListener(new EscuchaEjecutar());
//		bEjecutar.setEnabled(!editando);
//	}
//	
//	public void init()
//	{
//		properties = new MyProperties("hql.properties");
//	}
//	
//	@Override
//	public void start()
//	{
//		_cargarQueriesYDominios(true);
//		_setEstadoComponentes();
//	}
//	
//	private void _cargarQueriesYDominios(boolean alsoDomain)
//	{
//		List<NamedValue<String>> lstNV = properties.getProperties();
//
//		if( alsoDomain )
//		{
//			List<String> lstDomains = Hql.toDomainList(lstNV);
//			Collections.sort(lstDomains,(a,b)->a.compareTo(b));
//			cbDomain.setItems(lstDomains);
//			cbDomain.setSpecialItem(" ");
//			cbDomain.selectSpecialItem();
//
//		}
//
//		NamedValue<String> selected = lstQueries.getSelectedItem();
//		
//		if( cbDomain.isSpecialItemSelected() )
//		{
//			lstQueries.setItems(lstNV);
//		}
//		else
//		{
//			String domain = cbDomain.getSelectedItem();			
//			lstQueries.setItems(Hql.toNamedValue(lstNV,h->h.getDomain().equals(domain)));			
//		}
//		
//		if( selected!=null)
//		{
//			lstQueries.setSelectedItem(nv->nv.getName().equals(selected.getName()));
//		}
//
//		lstQueries.sort((a,b)->a.getName().compareTo(b.getName()));
//		super.validate();
//	}
//	
//	
//	@Override
//	public void stop()
//	{
//		editando=false;
//		agregando=false;
//	}
//	
//	private void _setEstadoComponentes()
//	{
//		String sAgregando="";
//		String newHQL = (String)values.removeValueFrom(this,HQLScreen.class,"newHQL");
//		if( newHQL!=null && !newHQL.trim().isEmpty())
//		{
//			agregando=true;
//			editando=true;
//			sAgregando=newHQL;
//			lstQueries.setUnselected();
//		}
//		lstQueries.setEnabled(!editando);
//		bSave.setEnabled(editando);
//		bRmv.setText(editando?"Cancel":"Remove");
//		
//		bEjecutar.setEnabled(!lstQueries.isUnselected());
//
//		
//		boolean unselected = lstQueries.isUnselected();
//		bRmv.setEnabled(editando||!lstQueries.isUnselected());
//		tfPropname.setEnabled(editando);
//		tfPropname.setText(agregando?"New Query"+newQueryNo++:unselected?"":lstQueries.getSelectedItem().getName());
//		getConsole().setEnabled(editando);
//		getConsole().setText(agregando?sAgregando:unselected?"":lstQueries.getSelectedItem().getValue());
//		bAdd.setEnabled(!editando);
//		
//		if( tfPropname.c().isEnabled() )
//		{
//			tfPropname.selectAll();
//		}
//		
//	}
//		
////	@Override
////	public JPanel getButtonsArea()
////	{
////		JPanel pLim = new JPanel(new FlowLayout(FlowLayout.RIGHT));
////		pLim.add(bEjecutar);
////		return pLim;
////	}
////
////	@Override
////	public JPanel getConsoleArea()
////	{
////		JScrollPane scrollPane=new JScrollPane();
////		scrollPane.setBorder(null);
////		taConsola=new JTextArea();
////		taConsola.setFont(new Font("Consolas", Font.PLAIN, 11));
////		scrollPane.setViewportView(taConsola);
////		
////		JPanel p = new JPanel(new BorderLayout());
////		p.add(scrollPane,BorderLayout.CENTER);
////		return p;
////	}
////
//	
//	@Override
//	public String getName()
//	{
//		return "Stored Queries";
//	}
//	
//
//	
//	class EscuchaLista implements ListSelectionListener
//	{
//		@Override
//		public void valueChanged(ListSelectionEvent evt)
//		{
//			MyListEvent<NamedValue<String>> e = (MyListEvent)evt;
//			if(e.getItem()!=null || e.getListEventType()==MyListEvent.ITEM_UNSELECTED)
//			{
//				_setEstadoComponentes();
//			}
//			
//			if( e.getClickCount()==2)
//			{
//				editando=true;
//				_setEstadoComponentes();
//				getConsole().requestFocus();
//			}
//		}
//	}
//	
//	class EscuchaSave implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			// tomo el item seleccionado (puede ser null)
//			NamedValue<String> selected = lstQueries.getSelectedItem();
//			
//			// work tiene el nuevo nombre y el nuevo valor de la propiedad
//			
//			NamedValue<String> work = new NamedValue<>();
//			
////			if( !agregando )
////			{
//				work.setName(tfPropname.getText());
//				work.setValue(getConsole().getText());
////			}
//			
//			// si estaba editando borro la anterior
//			if( selected!=null )
//			{
//				// remuevo el seleccionado para volverlo a agregar
//				String oldName = selected.getName();
//				properties.remove(oldName);
//				lstQueries.removeItem(nv->nv.getName().equals(oldName));
//				lstQueries.addItem(work,true);
//			}
//			
//			properties.put(work);
//			
//			editando=false;
//			agregando=false;
//			_cargarQueriesYDominios(true);
//			_setEstadoComponentes();
//		}
//	}
//
//	class EscuchaAdd implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			lstQueries.setUnselected();
//			
//			agregando=true;
//			editando=true;
//			_setEstadoComponentes();
//			tfPropname.selectAll();
//			tfPropname.requestFocus();
//		}
//	}
//
//	class EscuchaRmv implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			if( !editando )
//			{
//				if(properties.size()==1) //lstQueries.getItems().size()<=1 )
//				{
//					showErrorMessage("Al menos debes dejar un query predefinido","Error");
//				}
//				else
//				{
//					int ok = showConfirmWarningMessage("Eliminar este query?","Confirmación");
//					if( ok==JOptionPane.YES_OPTION )
//					{
//						NamedValue<String> removed = lstQueries.removeSelectedItem();
//						properties.remove(removed);
//						properties.store();
//					}
//				}
//			}
//
//			_cargarQueriesYDominios(!editando);
//			editando=false;
//			agregando=false;
//			_setEstadoComponentes();
//		}
//	}
//	
//	class EscuchaEjecutar implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			NamedValue<String> nv = lstQueries.getSelectedItem();
//			Object[] args = nv!=null?new Object[]{nv.getName()}:new Object[0];
////			getMyApp().changeTopScreen(HQLSelectPredefQuery.class,getOuter(),args);
//		}
//	}
//	
//	class EscuchaDomain implements ItemListener
//	{
//		@Override
//		public void itemStateChanged(ItemEvent e)
//		{
//			_cargarQueriesYDominios(false);
//			editando = false;
//			agregando = false;
//			_setEstadoComponentes();
//		}
//	}
//}
