package thejavalistener.fwk.frontend.hql.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyAwt;
import thejavalistener.fwk.awt.MyScrollPane;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.panel.MyInsets;
import thejavalistener.fwk.awt.panel.MyLeftLayout;
import thejavalistener.fwk.awt.splitpane.MySplitPane;
import thejavalistener.fwk.awt.tabbedpane.MyTabbedPane;
import thejavalistener.fwk.awt.table.MyTable;
import thejavalistener.fwk.awt.textarea.MyTextArea;
import thejavalistener.fwk.frontend.MyAbstractScreen;
import thejavalistener.fwk.frontend.hql.statement.AbstractStatement;
import thejavalistener.fwk.frontend.hql.statement.FactoryStatement;
import thejavalistener.fwk.frontend.messages.MyScreenMessageEvent;
import thejavalistener.fwk.frontend.messages.MyScreenMessageListener;
import thejavalistener.fwk.frontend.messages.MyScreenValuesExchange;
import thejavalistener.fwk.util.MyLog;
import thejavalistener.fwk.util.NamedValue;
import thejavalistener.fwk.util.string.MyString;


@Component
public class HQLScreen extends MyAbstractScreen
{
	private MyTextArea taSQL;	
	private MyTabbedPane tpResultados;
	
	private MySplitPane splitPane;
	
	private boolean hayUpdate = false;
	
	@Autowired
	private HQLFacade facade;
	
	@Autowired
	private MyScreenValuesExchange values;
	
	@Autowired
	private FactoryStatement sqlStatementFactory;
	
	@PersistenceContext
	private EntityManager em;
	
	private EscuchaConsola escuchaConsola;

	@Override
	protected void createUI()
	{
		setLayout(new BorderLayout());
		
		// SQL
		taSQL=new MyTextArea();
		taSQL.setFont(new Font("Consolas", Font.PLAIN, 12));
		taSQL.addKeyListener(escuchaConsola = new EscuchaConsola());
		MyScrollPane scrollSQL=new MyScrollPane(taSQL.c());
		
		// Resultados
		tpResultados = new MyTabbedPane();
		
		// CENTER
		splitPane = new MySplitPane(MySplitPane.VERTICAL,scrollSQL,tpResultados.c());
		
		String pkg = getClass().getName();
		
		Integer dividerLocation = getProperties().putObjectIfAbsent(pkg+".dividerLocation",200);
		splitPane.setDividerLocation(dividerLocation);
		splitPane.setMySplitePaneListener(i->getProperties().putObject(pkg+".dividerLocation",(Integer)i));
		splitPane.setDividerSize(1);

		JPanel pCenter = new JPanel(new BorderLayout());
		pCenter.add(new MyInsets(splitPane.c(),10,10,0,0),BorderLayout.CENTER);
		
		// pSouth
		
		JPanel pLim = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton bLimpiar = new JButton("Limpiar");
		bLimpiar.addActionListener(e->tpResultados.removeAllTabs());
		pLim.add(bLimpiar);
		pCenter.add(pLim,BorderLayout.SOUTH);
		
		MyLeftLayout  pNorth = new MyLeftLayout();
		
		_addLink("Execute:","[ALT+X]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_X,'X',pNorth);		
		_addLink("|    Create New Object:","[ALT+N]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_N,'N',pNorth);
		_addLink("|    Clear All:","[ALT+C]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_C,'C',pNorth);
		_addLink("|    Save:","[ALT+S]",KeyEvent.ALT_DOWN_MASK,KeyEvent.VK_S,'S',pNorth);

		pCenter.add(new MyInsets(pNorth,10,10,0,5),BorderLayout.NORTH);
		
		add(pCenter,BorderLayout.CENTER);
		
		taSQL.requestFocus();
	}

	private void _addLink(String desc,String hotKey,int comb,int vk,char c,JPanel p)
	{
		MyLink lnk = new MyLink(desc);
		lnk.getStyle().linkForegroundUnselected = Color.GRAY;
		lnk.getStyle().linkInsets.left=0;
		lnk.getStyle().linkInsets.right=0;
		p.add(lnk.c());
		
		lnk = new MyLink(hotKey,MyLink.LINK);
		lnk.getStyle().linkInsets.left=0;
		lnk.getStyle().linkInsets.right=0;
		lnk.setActionListener(l->escuchaConsola.keyPressed(new KeyEvent(taSQL.c(),0,0,comb,vk,c)));
		p.add(lnk.c());
	}
	
	public MyTextArea getConsole()
	{
		return taSQL;
	}
	
	public void addResult(String sql,Object result)
	{
		addResult(sql,result,null);
	}
	
	public void addResult(String sql,Object result,Object[] headers)
	{
		MyLink lnk = new MyLink(sql,MyLink.LINK);
		lnk.setActionListener(l->MyAwt.copyToClipboard(lnk.getText()));
		MyLeftLayout pLabel = new MyLeftLayout(lnk.c(),5,0,5,0);
		MyBorderLayout pResultado = new MyBorderLayout(pLabel,0,0,0,0,BorderLayout.NORTH);
				
		MyTable<?> table = new MyTable<>();
		table.setBackground(Color.BLACK);
		table.setForeground(Color.WHITE);
		if( headers!=null ) table.headers(headers);
		table.c().setRowHeight(table.c().getRowHeight()+5);
		
		
		table.setData(result);
//		table.setEnableAlternateRowColor(true);
		MyScrollPane jsp = new MyScrollPane(table.c());
		jsp.setBorder(null);
		pResultado.add(jsp,BorderLayout.CENTER);    		
		tpResultados.addTab(pResultado,true,sql);
	}
	
	
	@Override
	public String getName()
	{
		return "Consola SQL";
	}
	
	private void _accionEjecutarHQL(String sql) 
	{
		if(sql==null)
		{
			return;
		}
		
		AbstractStatement astm = sqlStatementFactory.getStatement(sql,this);
		astm.process();
	}
	
	@Override
	public void init()
	{
		String txt = getProperties().getString("hqlscreen.console");
		taSQL.setText(MyString.ifNull(txt,""));
	}
	
	@Override
	public void destroy()
	{
		_saveConsola();
	}
	
	private void _saveConsola()
	{
		String txt = taSQL.getText();
		getProperties().putString("hqlscreen.console",txt);		
	}
	
	@Override
	public void start()
	{
		hayUpdate = false;
	}
	
	@Override
	public void stop()
	{
		if( hayUpdate )
		{
			dataUpdated();
		}
	}
			
	class EscuchaConsola extends KeyAdapter
	{
	    public void keyPressed(KeyEvent e) 
	    {
	    	String txt = taSQL.getSelectedText();	    
	    	
	    	// ALT+C - Clear all
	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'c') 
	        {
	    		tpResultados.removeAllTabs();
	    		taSQL.requestFocus();
		    	return;
	        }

	    	// ALT+S - Save
	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 's') 
	        {
	    		_saveConsola();
	    		showInformationMessage("La consola ha sido gardada","Bien!");
	    		return;
	        }

	    	
	    	// ALT+X - Ejecutar
	    	if(e.isAltDown() && Character.toLowerCase(e.getKeyChar()) == 'x') 
	        {
		    	if(txt==null)
		    	{
		    		txt = _selectTextLine(taSQL.getText(),taSQL.getCaretPosition());
		    	}

		    	_accionEjecutarHQL(txt);

		    	_saveConsola();
		    	return;
	        }
	    		        
	    	// ALT+N - New Object
	        if(e.isAltDown() && Character.toLowerCase(e.getKeyCode()) == 'n') 
	        {
				getMyApp().showScreen(HQLCreateNewEntity.class).pack().centerH(150).apply();
				return;
	        }
	    }
	}

	class EscuchaNewEntityCreated implements MyScreenMessageListener
	{
		@Override
		public void onMessageEvent(MyScreenMessageEvent e)
		{
			MyLog.println("OJO ACAAA--> Se creo una nueva fila...");
		}
	}
	

	
	private String _selectTextLine(String txt,int curr)
	{
		int bounds[] = MyString.findParagraphBounds(txt,curr);
		if( bounds!=null )
		{
			taSQL.select(bounds[0],bounds[1]);
			return txt.substring(bounds[0],bounds[1]);
		}
		
		return "";
		
	}

}