//package thejavalistener.fwk.console;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Font;
//import java.awt.Window;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowFocusListener;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Stack;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.text.StyledDocument;
//
//import thejavalistener.fwk.awt.MyAwt;
//import thejavalistener.fwk.awt.textarea.MyTextPane;
//import thejavalistener.fwk.util.MyCollection;
//import thejavalistener.fwk.util.MyColor;
//import thejavalistener.fwk.util.TriFunction;
//import thejavalistener.fwk.util.string.MyString;
//
//public abstract class MyConsoleBase_BKP
//{
//	public static final TriFunction<Character,Integer,String,Character> STRING=(c, kc, s) -> c;
//	public static final TriFunction<Character,Integer,String,Character> HEX=(c, kc, s) -> MyString.isHexDigit(c)||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> CHAR=(c, kc, s) -> s.length()<3||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> INTEGER=(c, kc, s) -> Character.isDigit(c)||c=='-'&&s.length()==0||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> DOUBLE=(c, kc, s) -> s.isEmpty()&&c=='-'||Character.isDigit(c)||c=='.'&&s.indexOf('.')<0||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> BOOLEAN=(c, kc, s) -> MyString.parseBoolean(s)!=null||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> UPPERCASE=(c, kc, s) -> Character.toUpperCase(c);
//	public static final TriFunction<Character,Integer,String,Character> LOWERCASE=(c, kc, s) -> Character.toLowerCase(c);
//	public static final TriFunction<Character,Integer,String,Character> PASSWORD=(c, kc, s) -> '*';
//	public static final TriFunction<Character,Integer,String,Character> FLAG=(c, kc, s) -> c=='1'||c=='0'&&s.length()<2||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> AÑZ=(c, kc, s) -> c>='A'&&c<='Z'||c=='Ñ'||_validKeyCode(kc)?c:null;
//	public static final TriFunction<Character,Integer,String,Character> AZ=(c, kc, s) -> c>='A'&&c<='Z'||_validKeyCode(kc)?c:null;
//
//	protected abstract String _readString(InputConfigurator isconfig);
//
//	protected abstract int _pressAnyKey(Integer k, Runnable r);
//
//	protected abstract String _readPassword();
//
//	protected abstract int _menu(int menuRange[][]);
//
//	protected int inputPosition;
//
//	protected boolean closable=false;
//
//	protected boolean initialized=false;
//
//	protected MyTextPane textPane;
//	protected JScrollPane scrollPane;
//	protected Container container;
//	protected Window parent;
//	
//	private List<MyConsoleListener> listeners = new ArrayList<>();
//
//	// STYLE
//	private MyConsoleStyle style;
//	private Map<String,String> customStyles=new HashMap<>();
//
//	private SimpleFontBanner defaultFontBanner=new SimpleFontBanner();
//
//	protected boolean reading=false;
//
//	private MyConsoleBase_BKP outer=null;
//
//	// // progress
//	// protected boolean progressBar=true;
//	// protected long top=0;
//	// protected long curr=0;
//	// protected int size=0;
//	// protected int ant=0;
//	// protected long initProgressTime;
//
//	public MyTextPane getTextPane()
//	{
//		return textPane;
//	}
//
//	public void banner(String title)
//	{
//		banner(title,defaultFontBanner);
//	}
//
//	public void banner(String title, AbstractFontBanner font)
//	{
//		int height=font.getHeight(); // Obtener la altura de los caracteres
//
//		// Crear un array de StringBuffer para construir las líneas del título
//		StringBuffer[] output=new StringBuffer[height];
//		for(int i=0; i<height; i++)
//		{
//			output[i]=new StringBuffer();
//		}
//
//		// Construir el título línea por línea
//		for(char c:title.toCharArray())
//		{
//			String[][] charRepresentation=font.getChar(c);
//			for(int i=0; i<height; i++)
//			{
//				output[i].append(charRepresentation[i][0]); // Agregar la fila
//															// correspondiente
//															// de cada carácter
//			}
//		}
//
//		// fg("GREEN");
//		// Imprimir cada línea del título
//		for(StringBuffer line:output)
//		{
//			// MyThread.randomSleep(250);
//			print(line.toString()).ln();
//		}
//		// x();
//
//		ln();
//	}
//
//	protected MyConsoleBase_BKP()
//	{
//		this.outer=this;
//
//		this.style=new MyConsoleStyle();
//		customStyles.put("defaultInputStyle",style.defaultInputStyle);
//		customStyles.put("defaultStyle",style.defaultStyle);
//
//		container = getContainer();
//
//		textPane=new MyTextPane(false,true);
//		textPane.addKeyListener(new EscuchaCTRLCyESC());
//		scrollPane=new JScrollPane(textPane.c());
//
//		if(container instanceof Window || container instanceof JFrame)
//		{
//			container.add(scrollPane,BorderLayout.CENTER);
//
//			Window wcont=(Window)container;
//			MyAwt.setProportionalSize(.7,wcont,null);
//			MyAwt.center(wcont,null);
//
//			EscuchaWindow escuchaWindow=new EscuchaWindow();
//
//			wcont.addWindowListener(escuchaWindow);
//			wcont.addWindowFocusListener(escuchaWindow);
//		}
//
//		textPane.addMouseListener(new EscuchaMouse());
//
//		init();
//	}
//
//	public JScrollPane getScrollPane()
//	{
//		return scrollPane;
//	}
//
//	protected void init()
//	{
//		if(!initialized)
//		{
//			initialized=true;
//			MyConsoleStyle style=getStyle();
//			Font font=new Font(style.fontFamily,style.fontStyle,style.fontSize);
//			textPane.setFont(font);
//			textPane.setBackground(style.background);
//			textPane.setForeground(style.foreground);
//			textPane.setCaretColor(style.caretColor);
//			textPane.setEditable(false);
//			cs(getDefaultStyle());
//			scrollPane.setBorder(null);
//		}
//	}
//
//	public Container getContainer()
//	{
//		return container!=null?container:(container=new JFrame());
//	}
//
//	private void _stringToCommand(String sCmd)
//	{
//		if(sCmd.equals("[b]"))
//		{
//			b();
//			return;
//		}
//
//		if(sCmd.equals("[i]"))
//		{
//			i();
//			return;
//		}
//
//		if(sCmd.equals("[x]"))
//		{
//			x();
//			return;
//		}
//
//		if(sCmd.startsWith("[fg("))
//		{
//			String sCol=MyString.extract(sCmd,"(",")")[0];
//			fg(sCol.substring(1,sCol.length()-1));
//			return;
//		}
//
//		if(sCmd.startsWith("[bg("))
//		{
//			String sCol=MyString.extract(sCmd,"(",")")[0];
//			bg(sCol.substring(1,sCol.length()-1));
//			return;
//		}
//	}
//
//
//	public MyConsoleBase_BKP print(Object o)
//	{
//		init();
//		open();
//
//		String s=o==null?"null":o.toString();
//		String[][] toPrint=_extractFormattedText(s);
//
//		for(int i=0; i<toPrint.length; i++)
//		{
//			String txt=toPrint[i][0];
//			String style=toPrint[i][1];
//
//			if(!style.isEmpty())
//			{
//				cs(style);
//				_print(txt);
//				x();
//			}
//			else
//			{
//				_print(txt);
//			}
//		}
//
//		return this;
//	}
//
//	private MyConsoleBase_BKP _print(String s)
//	{
//		String[] reemAña=_reemplazarOAñadir(textPane.getText(),textPane.getCaretPosition(),s);
//		if(reemAña[0].length()>0)
//		{
//			int pos=textPane.getCaretPosition();
//			textPane.replaceText(reemAña[0],pos,pos+reemAña[0].length());
//		}
//
//		if(reemAña[1].length()>0)
//		{
//			textPane.insertText(reemAña[1],textPane.getCaretPosition());
//		}
//
//		return this;
//	}
//
//	public boolean _isFormatedText(String s)
//	{
//		Stack<String> stack=new Stack<>();
//		Pattern pattern=Pattern.compile("\\[(b|i|fg\\([^\\]]+\\)|bg\\([^\\]]+\\)|x)\\]");
//		Matcher matcher=pattern.matcher(s);
//
//		boolean hasValidContent=false;
//		int lastEnd=0; // Para verificar contenido entre tags
//
//		while(matcher.find())
//		{
//			String tag=matcher.group(1);
//
//			// Verificar si hay contenido entre los tags
//			if(matcher.start()>lastEnd)
//			{
//				hasValidContent=true;
//			}
//			lastEnd=matcher.end();
//
//			if(tag.equals("x"))
//			{
//				if(stack.isEmpty()) return false; // No hay nada que cerrar
//				stack.pop(); // Cerramos el último formato abierto
//			}
//			else
//			{
//				stack.push(tag); // Apilamos el formato abierto
//			}
//		}
//
//		// Asegurar que la pila está vacía y que hubo contenido válido
//		return stack.isEmpty()&&hasValidContent;
//	}
//
//	public String[][] _extractFormattedText(String s)
//	{
//		if(!_isFormatedText(s))
//		{
//			return new String[][] {{s, ""}};
//		}
//
//		List<String[]> result=new ArrayList<>();
//		StringBuilder cmd=new StringBuilder();
//		StringBuilder txt=new StringBuilder();
//		List<String> activeStyles=new ArrayList<>();
//		boolean onCmd=false;
//
//		for(int i=0; i<s.length(); i++)
//		{
//			char c=s.charAt(i);
//
//			if(c=='[')
//			{
//				onCmd=true;
//				if(txt.length()>0)
//				{
//					result.add(new String[] {txt.toString(), String.join("",activeStyles)});
//					txt.setLength(0);
//				}
//				cmd.setLength(0);
//			}
//			else if(c==']')
//			{
//				onCmd=false;
//				String command=cmd.toString();
//				if(command.equals("x"))
//				{
//					if(!activeStyles.isEmpty())
//					{
//						activeStyles.remove(activeStyles.size()-1);
//					}
//				}
//				else
//				{
//					activeStyles.add("["+command+"]");
//				}
//			}
//			else
//			{
//				if(onCmd)
//				{
//					cmd.append(c);
//				}
//				else
//				{
//					txt.append(c);
//				}
//			}
//		}
//
//		if(txt.length()>0)
//		{
//			result.add(new String[] {txt.toString(), String.join("",activeStyles)});
//		}
//
//		return result.toArray(new String[0][2]);
//	}
//
//	public MyConsoleBase_BKP backspace()
//	{
//		try
//		{
//			int pos=textPane.getCaretPosition();
//			if(pos>0)
//			{
//				StyledDocument doc=textPane.c().getStyledDocument();
//				doc.remove(pos-1,1);
//			}
//
//			return this;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	private static String[] _reemplazarOAñadir(String original, int posicion, String reemplazo)
//	{
//		int x=MyString.charCount(original,'\r');
//		int longitudOriginal=original.length()-x;
//		int longitudReemplazo=reemplazo.length();
//
//		String parteReemplaza;
//		String parteAñadida="";
//
//		if(posicion+longitudReemplazo<=longitudOriginal)
//		{
//			parteReemplaza=reemplazo;
//		}
//		else
//		{
//			parteReemplaza=reemplazo.substring(0,longitudOriginal-posicion);
//			parteAñadida=reemplazo.substring(longitudOriginal-posicion);
//		}
//
//		return new String[] {parteReemplaza, parteAñadida};
//	}
//
//	public MyConsoleStyle getStyle()
//	{
//		return style;
//	}
//
//	public void setStyle(MyConsoleStyle cs)
//	{
//		this.style=cs;
//	}
//
//	public MyConsoleBase_BKP cls()
//	{
//		textPane.setText("");
//		return this;
//	}
//
//	public MyConsoleBase_BKP fg(String s)
//	{
//		return fg(MyColor.fromString(s));
//	}
//
//	public MyConsoleBase_BKP fg(Color c)
//	{
//		textPane.fg(c);
//		return this;
//	}
//
//	public MyConsoleBase_BKP bg(String s)
//	{
//		return bg(MyColor.fromString(s));
//	}
//
//	public MyConsoleBase_BKP bg(Color c)
//	{
//		textPane.bg(c);
//		return this;
//	}
//
//	public MyConsoleBase_BKP bf(String sb, String sf)
//	{
//		return bf(MyColor.fromString(sb),MyColor.fromString(sf));
//	}
//
//	public MyConsoleBase_BKP bf(Color b, Color f)
//	{
//		textPane.bf(b,f);
//		return this;
//	}
//
//	public MyConsoleBase_BKP b()
//	{
//		textPane.b();
//		return this;
//	}
//
//	public MyConsoleBase_BKP i()
//	{
//		textPane.i();
//		return this;
//	}
//
//	public MyConsoleBase_BKP X()
//	{
//		textPane.X();
//		cs(getDefaultStyle());
//		return this;
//	}
//
//	public MyConsoleBase_BKP x()
//	{
//		textPane.x();
//		return this;
//	}
//
//	public MyConsoleBase_BKP x(int n)
//	{
//		textPane.x(n);
//		return this;
//	}
//
//	public MyConsoleBase_BKP println(Object s)
//	{
//		print(s+"\n");
//		return this;
//	}
//
//	public MyConsoleBase_BKP println()
//	{
//		println("");
//		return this;
//	}
//
//	public MyConsoleBase_BKP ln()
//	{
//		return println();
//	}
//
//	public MyConsoleBase_BKP skipBkp()
//	{
//		textPane.setCaretPosition(0);
//		return this;
//	}
//
//	public MyConsoleBase_BKP skipBkp(int n)
//	{
//		textPane.setCaretPosition(textPane.getCaretPosition()-n);
//		return this;
//	}
//
//	public MyConsoleBase_BKP skipFwd(int n)
//	{
//		textPane.setCaretPosition(textPane.getCaretPosition()+n);
//		return this;
//	}
//
//	public MyConsoleBase_BKP skipFwd()
//	{
//		textPane.setCaretPosition(textPane.getLen());
//		return this;
//	}
//
//	public InputConfigurator input()
//	{
//		InputConfigurator ic=new InputConfigurator(this);
//		return ic;
//	}
//
//	public String readlnString(String regex)
//	{
//		String x=readString(regex);
//		println();
//		return x;
//	}
//
//	public String readString(String regex)
//	{
//		return input().regex(regex).read();
//	}
//
//	public String readPassword()
//	{
//		return _readPassword();
//	}
//
//	public String readlnPassword()
//	{
//		String x=_readPassword();
//		println();
//		return x;
//	}
//
//	public String readString()
//	{
//		return input().read();
//	}
//
//	public String readlnString()
//	{
//		String x=readString();
//		println();
//		return x;
//	}
//
//	public Integer readInteger()
//	{
//		String rg="^-?(214748364[0-7]|21474836[0-3][0-9]|2147483[0-5][0-9]{2}|214748[0-2][0-9]{3}|21474[0-7][0-9]{4}|2147[0-3][0-9]{5}|214[0-6][0-9]{6}|21[0-3][0-9]{7}|2[01][0-9]{8}|1[0-9]{9}|[1-9][0-9]{0,8}|0)$";
//		String s=input().mask(INTEGER).regex(rg).read();
//		return s!=null?Integer.parseInt(s):null;
//	}
//
//	public Integer readlnInteger()
//	{
//		Integer i=readInteger();
//		println();
//		return i;
//	}
//
//	public String readUppercaseString()
//	{
//		return input().mask(UPPERCASE).read();
//	}
//
//	public String readlnUppercaseString()
//	{
//		String x=readUppercaseString();
//		println();
//		return x;
//	}
//
//	public String readLowercaseString()
//	{
//		return input().mask(LOWERCASE).read();
//	}
//
//	public String readlnLowercaseString()
//	{
//		String x=readLowercaseString();
//		println();
//		return x;
//	}
//
//	public Double readDouble()
//	{
//		String s=input().mask(DOUBLE).valid(x -> MyString.isDouble(x)).read();
//		return s!=null?Double.parseDouble(s):null;
//	}
//
//	public Double readlnDouble()
//	{
//		Double d=readDouble();
//		println();
//		return d;
//	}
//
//	public void open()
//	{
//		init();
//
//		if(!container.isVisible())
//		{
//			container.setVisible(true);
//		}
//	}
//
//	public void close()
//	{
//		if(container.isVisible())
//		{
//			container.setVisible(false);
//		}
//	}
//
//	public Progress progressBar(int size, int top)
//	{
//		return new ProgressBar(this,size,top);
//	}
//
//	public Progress progressMeter(int top)
//	{
//		return new ProgressMeter(this,top);
//	}
//
//	public void addListener(MyConsoleListener lst)
//	{
//		listeners.add(lst);
//	}
//	
//	public List<MyConsoleListener> getListeners()
//	{
//		return listeners;
//	}
//	
//	public int pressAnyKey()
//	{
//		return pressAnyKey(null,() -> {
//		});
//	}
//
//	public int pressAnyKey(Runnable r)
//	{
//		return pressAnyKey(null,r);
//	}
//
//	public int pressAnyKey(int k)
//	{
//		return pressAnyKey(k,() -> {
//		});
//	}
//
//	public int pressAnyKey(Integer k, Runnable r)
//	{
//		return _pressAnyKey(k,r);
//	}
//
//	protected void setReading(boolean r)
//	{
//		this.reading=r;
//	}
//
//	@Override
//	public String toString()
//	{
//		return textPane.getText();
//	}
//
//	public String fileExplorer()
//	{
//		return fileExplorer(".");
//	}
//
//	public String fileExplorer(String dir)
//	{
//		JFileChooser jfc=new JFileChooser(dir);
//		int rtdo=jfc.showOpenDialog(container);
//
//		if(rtdo==JFileChooser.APPROVE_OPTION)
//		{
//			File f=jfc.getSelectedFile();
//			return f.getAbsolutePath().replace('\\','/');
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	public void setDefaultInputStyle(String inputStyle)
//	{
//		customStyles.put("defaultInputStyle",inputStyle);
//	}
//
//	public void setDefaultStyle(String defaultStyle)
//	{
//		customStyles.put("defaultStyle",defaultStyle);
//	}
//
//	public String getDefaultStyle()
//	{
//		return customStyles.get("defaultStyle");
//	}
//
//	public String getDefaultInputStyle()
//	{
//		return customStyles.get("defaultInputStyle");
//	}
//
//	public void setCustomStyle(String customStyleName, String customStyle)
//	{
//		if(MyString.oneOf(customStyleName,"inputStyle","defaultStyle"))
//		{
//			throw new RuntimeException("Debe usar los métodos setDefaultInputStyle o setDefaultStyle");
//		}
//
//		customStyles.put(customStyleName,customStyle);
//	}
//
//	public String getCustomStyle(String customStyleName)
//	{
//		return customStyles.get(customStyleName);
//	}
//
//	public void setClosable(boolean b)
//	{
//		this.closable=b;
//	}
//
//	public boolean isClosable()
//	{
//		return closable;
//	}
//
//	/**
//	 * Aplica el estilo $customStyle, que puede identificarse por nombre o por
//	 * extensión. En caso de que sea null será omitido.
//	 */
//	public MyConsoleBase_BKP cs(String customStyle)
//	{
//		if(customStyle==null) return this;
//
//		String cmds;
//
//		// es un estilo por extensión...
//		if(customStyle.indexOf('[')>=0)
//		{
//			cmds=customStyle;
//		}
//		else
//		{
//			// es un estilo por nombre
//			cmds=getCustomStyle(customStyle);
//		}
//
//		for(String s:MyString.extract(cmds,"[","]"))
//		{
//			String cmd=s.substring(1,s.length()-1);
//
//			if(cmd.equals("b"))
//			{
//				b();
//			}
//			else
//			{
//				if(cmd.equals("i"))
//				{
//					i();
//				}
//				else
//				{
//					String color=s.substring(s.indexOf('(')+1,s.indexOf(')'));
//					if(cmd.startsWith("fg"))
//					{
//						fg(color);
//					}
//					else
//					{
//						if(cmd.startsWith("bg"))
//						{
//							bg(color);
//						}
//						else
//						{
//							String mssg="Error en:"+cmd+".\n";
//							mssg+="Los comandos deben ser [i][b][fg(color)] o [bg(color)]";
//							throw new RuntimeException(mssg);
//						}
//					}
//				}
//			}
//		}
//
//		return this;
//	}
//
//	public int menuln(String[] options)
//	{
//		int op=menu(options);
//		println();
//		return op;
//	}
//
//	public int menu(String[] options)
//	{
//		int[][] range=new int[options.length][2];
//
//		// normalizo opciones
//		String[] optionsOk=_normalizarItemsMenu(options);
//
//		int col=textPane.getCaretColumnPosition()+1; // por el "["
//
//		cs(getDefaultInputStyle());
//
//		// imprimo el menu
//		for(int i=0; i<optionsOk.length; i++)
//		{
//
//			int cp=textPane.getCaretPosition();
//
//			if(i==0)
//			{
//				range[i][0]=cp+1;
//				range[i][1]=cp+1+optionsOk[i].length();
//				print("[");
//				println(optionsOk[0]);
//			}
//			else
//			{
//				if(i==optionsOk.length-1)
//				{
//					range[i][0]=col+cp;
//					range[i][1]=col+cp+optionsOk[i].length();
//					String espacios=MyString.replicate(' ',col);
//					println(espacios+optionsOk[i]+"]");
//				}
//				else
//				{
//					range[i][0]=col+cp;
//					range[i][1]=col+cp+optionsOk[i].length();
//					String espacios=MyString.replicate(' ',col);
//					println(espacios+optionsOk[i]);
//				}
//			}
//		}
//
//		int op=_menu(range);
//
//		// borro el menu
//		cs(getDefaultInputStyle());
//		print("["+options[op]+"]");
//
//		X();
//
//		return op;
//	}
//
//	private String[] _normalizarItemsMenu(String[] options)
//	{
//		// dejo todas las opciones con el mismo len
//		int maxLen=MyCollection.reduce(options,0,(s, len) -> Math.max(s.length(),len));
//
//		String[] optionsOk=new String[options.length];
//		for(int i=0; i<options.length; i++)
//		{
//			optionsOk[i]=MyString.rpad(options[i],' ',maxLen);
//		}
//
//		return optionsOk;
//	}
//
//	private static boolean _validKeyCode(int kc)
//	{
//		return kc==KeyEvent.VK_LEFT||kc==KeyEvent.VK_RIGHT||kc==KeyEvent.VK_BACK_SPACE||kc==KeyEvent.VK_ENTER;
//	}
//
//	public void closeAndExit()
//	{
//		Container c=getContainer();
//		int r=JOptionPane.showConfirmDialog(c,"¿Esta acción finalizará el programa?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
//		if(r==JOptionPane.YES_OPTION)
//		{
//			// container.setVisible(false);
//			close();
//			if(container instanceof Window||container instanceof JFrame)
//			{
//				((Window)container).dispose();
//			}
//			System.exit(0);
//		}
//	}
//
//	class EscuchaMouse extends MouseAdapter
//	{
//		@Override
//		public void mousePressed(MouseEvent e)
//		{
//			int len=textPane.getText().length();
//			if(len>0&&textPane.getText().charAt(len-1)==']')
//			{
//				textPane.setCaretPosition(textPane.getLen()-1);
//			}
//			else
//			{
//				textPane.setCaretPositionAtEndOfText();
//			}
//		}
//	}
//
//	class EscuchaWindow extends WindowAdapter implements WindowFocusListener
//	{
//		@Override
//		public void windowClosing(WindowEvent e)
//		{
//			if(closable)
//			{
//				close();
//			}
//		}
//
//		@Override
//		public void windowLostFocus(WindowEvent e)
//		{
//			((Container)e.getComponent()).requestFocus();
//			super.windowLostFocus(e);
//		}
//	}
//
//	public class EscuchaCTRLCyESC extends KeyAdapter
//	{
//		@Override
//		public void keyPressed(KeyEvent e)
//		{
//			if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_C||e.getKeyCode()==KeyEvent.VK_ESCAPE)
//			{
//				e.consume();
//				if(!outer.isClosable())
//				{
//					outer.closeAndExit();
//				}
//				else
//				{
//					outer.close();
//				}
//			}
//		}
//	}
//
//	public int getCaretPosition()
//	{
//		return textPane.getCaretPosition();
//	}
//	
//	public void setCaretPosition(int pos)
//	{
//		textPane.setCaretPosition(pos);
//	}
//	
//}
