package thejavalistener.fwk.frontend.hql.screen;
//package myframework.frontend.hql.screen;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.swing.JButton;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import myframework.awt.MyAwt;
//import myframework.awt.MyScrollPane;
//import myframework.awt.MyTextArea;
//import myframework.awt.link.MyLink;
//import myframework.awt.panel.MyBorderLayout;
//import myframework.awt.panel.MyInsets;
//import myframework.awt.panel.MyLeftLayout;
//import myframework.awt.splitpane.MySplitPane;
//import myframework.awt.tabbedpane.MyTabbedPane;
//import myframework.awt.table.MyTable;
//import myframework.frontend.MyAbstractScreen;
//import myframework.frontend.hql.statement.AbstractStatement;
//import myframework.frontend.hql.statement.FactoryStatement;
//import myframework.frontend.messages.MyScreenMessageEvent;
//import myframework.frontend.messages.MyScreenMessageListener;
//import myframework.frontend.messages.MyScreenValuesExchange;
//import myframework.frontend.texttable.MySingleTable;
//import myframework.properties.MyProperties;
//import myframework.util.NamedValue;
//import myframework.util.string.MyString;
//
//
//@Component
//public class HQLScreen extends MyAbstractScreen
//{
//	private MyTextArea taSQL;	
//	private MyTabbedPane tpResultados;
//	
//	private MySplitPane splitPane;
//	
//	private boolean hayUpdate = false;
//	
//	@Autowired
//	private HQLFacade facade;
//	
//	@Autowired
//	private MyScreenValuesExchange values;
//	
//	@Autowired
//	private FactoryStatement sqlStatementFactory;
//	
//	@PersistenceContext
//	private EntityManager em;
//	
//	private EscuchaConsola escuchaConsola;
//
//	@Override
//	protected void createUI()
//	{
//		setLayout(new BorderLayout());
//		
//		// SQL
//		taSQL=new MyTextArea();
//		taSQL.setFont(new Font("Consolas", Font.PLAIN, 12));
//		taSQL.addKeyListener(escuchaConsola = new EscuchaConsola());
//		MyScrollPane scrollSQL=new MyScrollPane(taSQL.c());
//		
//		// Resultados
//		tpResultados = new MyTabbedPane();
//		
//		// CENTER
//		splitPane = new MySplitPane(MySplitPane.HORIZONTAL,scrollSQL,tpResultados.c());
//		splitPane.setDividerLocation(300);
//		splitPane.setDividerSize(2);
//
//		JPanel pCenter = new JPanel(new BorderLayout());
//		pCenter.add(new MyInsets(splitPane.c(),10,10,0,0),BorderLayout.CENTER);
//		
//		// pSouth
//		
//		JPanel pLim = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		
//		JButton bLimpiar = new JButton("Limpiar");
//		bLimpiar.addActionListener(e->tpResultados.removeAllTabs());
//		pLim.add(bLimpiar);
//		pCenter.add(pLim,BorderLayout.SOUTH);
//		
//		MyLeftLayout  pNorth = new MyLeftLayout();
//		
//		_addLink("Execute:","[ALT+X]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_X,'X',pNorth);		
////		_addLink("|    Predefined Queries:","[ALT+Q]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_Q,'Q',pNorth);
////		_addLink("|    Save/Edit Query:","[ALT+E]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_E,'E',pNorth);
//		_addLink("|    Create New Object:","[ALT+N]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_N,'N',pNorth);
//		_addLink("|    Clear All:","[ALT+C]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_C,'C',pNorth);
//
//		pCenter.add(new MyInsets(pNorth,10,10,0,5),BorderLayout.NORTH);
//		
//		add(pCenter,BorderLayout.CENTER);
//		
//		taSQL.requestFocus();
//	}
//
//	private void _addLink(String desc,String hotKey,int comb,int vk,char c,JPanel p)
//	{
//		MyLink lnk = new MyLink(desc);
//		lnk.getStyle().linkForegroundUnselected = Color.GRAY;
//		lnk.getStyle().linkInsets.left=0;
//		lnk.getStyle().linkInsets.right=0;
//		p.add(lnk.c());
//		
//		lnk = new MyLink(hotKey,MyLink.LINK);
//		lnk.getStyle().linkInsets.left=0;
//		lnk.getStyle().linkInsets.right=0;
//		lnk.setActionListener(l->escuchaConsola.keyPressed(new KeyEvent(taSQL.c(),0,0,comb,vk,c)));
//		p.add(lnk.c());
//	}
//	
//	public MyTextArea getConsole()
//	{
//		return taSQL;
//	}
//	
//	public void addResult(String sql,Object result)
//	{
//		addResult(sql,result,null);
//	}
//	public void addResult(String sql,Object result,String[] headers)
//	{
//		MyLink lnk = new MyLink(sql,MyLink.LINK);
//		lnk.setActionListener(l->MyAwt.copyToClipboard(lnk.getText()));
//		MyLeftLayout pLabel = new MyLeftLayout(lnk.c(),5,0,5,0);
//		MyBorderLayout pResultado = new MyBorderLayout(pLabel,0,0,0,0,BorderLayout.NORTH);
//				
//		MySingleTable table = new MySingleTable();
//		table.showLineNumbers(true);
//		if( headers!=null ) table.headers(headers);
//		
//		table.loadData(result);
//		
//		table.autolayout();
//
//		
//		MyTextArea mta = new MyTextArea();
//		mta.setBackground(Color.BLACK);
//		mta.setForeground(Color.WHITE);
//		mta.setText(table.makeTable().drawTable());
//		
//		MyScrollPane jsp = new MyScrollPane(mta.c());
//		pResultado.add(jsp,BorderLayout.CENTER);    		
//		tpResultados.addTab(pResultado,true,sql);
//	}
//	
//	
//	@Override
//	public String getName()
//	{
//		return "Consola SQL";
//	}
//	
//	private void _accionEjecutarHQL(String sql) 
//	{
//		if(sql==null)
//		{
//			return;
//		}
//		
//		AbstractStatement astm = sqlStatementFactory.getStatement(sql,this);
//		astm.process();
//	}
//	
//	@Override
//	public void init()
//	{
//		String txt = (String) getProperties().get("myframework.hqlscreen.console");
//		taSQL.setText(MyString.ifNull(txt,""));
//	}
//	
//	@Override
//	public void destroy()
//	{
//		String txt = taSQL.getText();
//		getProperties().put("myframework.hqlscreen.console",txt);
//	}
//	
//	@Override
//	public void start()
//	{
//		hayUpdate = false;
//	}
//	
//	@Override
//	public void stop()
//	{
//		if( hayUpdate )
//		{
//			dataUpdated();
//		}
//	}
//			
//	class EscuchaConsola extends KeyAdapter
//	{
//	    public void keyPressed(KeyEvent e) 
//	    {
//	    	String txt = taSQL.getSelectedText();	    
//	    	
//	    	// ALT+C - Clear all
//	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'c') 
//	        {
//	    		tpResultados.removeAllTabs();
//	    		taSQL.setText("");
//	    		taSQL.requestFocus();
//		    	return;
//	        }
//
//	    	
//	    	// ALT+X - Ejecutar
//	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'x') 
//	        {
//		    	if(txt==null)
//		    	{
//		    		txt = _selectTextLine(taSQL.getText(),taSQL.getCaretPosition());
//		    	}
//
//		    	_accionEjecutarHQL(txt);
//
//		    	return;
//	        }
//	    	
////	    	// ALT+E - Grabar
////	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'e') 
////	        {
////	    		txt = txt==null?"":txt;
////	    		values.putValueFor(HQLSelectPredefQueryEdit.class,"newHQL",txt,getOuter());
//////		    	NamedValue<String> querySeleccionado=(NamedValue)getMyApp().showScreen(HQLSelectPredefQueryEdit.class).size(.7).centerH(150).apply();		    	
////		    	getMyApp().showScreen(HQLSelectPredefQueryEdit.class).size(.7).centerH(150).apply();	
//////		    	_ejecutarPredefQuery(querySeleccionado);		    	
////		    	return;
////	        }
//	        
////	    	// ALT+Q - Ejecutar predefinido
////	        if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'q') 
////	        {
////		    	NamedValue<String> querySeleccionado;
////		    	querySeleccionado = (NamedValue)getMyApp().showScreen(HQLSelectPredefQuery.class).pack().centerH(150).apply();
////		    	_ejecutarPredefQuery(querySeleccionado);
////		    	return;
////	        }
//	        
//	    	// ALT+N - New Object
//	        if(e.isAltDown() && Character.toLowerCase(e.getKeyCode()) == 'n') 
//	        {
//				getMyApp().showScreen(HQLCreateNewEntity.class).pack().centerH(150).apply();
//	        }
//	    }
//	}
//	
//	private void _ejecutarPredefQuery(NamedValue<String> qSelected)
//	{
//		if( qSelected!=null)
//		{
//			String hql = qSelected.getValue();
//			taSQL.insert(hql+"\n\n",0);
//			taSQL.setCaretPosition(0);
//			_selectTextLine(taSQL.getText(),0);
//			taSQL.requestFocus();
//			escuchaConsola.keyPressed(new KeyEvent(taSQL.c(),0,0,KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_X,'X'));
//		}
//	}
//
//	class EscuchaNewEntityCreated implements MyScreenMessageListener
//	{
//		@Override
//		public void onMessageEvent(MyScreenMessageEvent e)
//		{
//			System.out.println("OJO ACAAA--> Se creo una nueva fila...");
//		}
//	}
//	
//
//	
//	private String _selectTextLine(String txt,int curr)
//	{
//		int bounds[] = MyString.findParagraphBounds(txt,curr);
//		if( bounds!=null )
//		{
//			taSQL.select(bounds[0],bounds[1]);
//			return txt.substring(bounds[0],bounds[1]);
//		}
//		
//		return "";
//		
//	}
//
//}