package thejavalistener.fwk.frontend.texttable3;

import java.util.ArrayList;

import thejavalistener.fwk.util.string.MyString;

public class StyleManager
{
	private String[] styles;
	private String styledContent = null;
	private Object content = null;
	
	public StyleManager(Object cnt)
	{
		this(cnt,new String[]{"[","]"});
	}
	
	public StyleManager(Object cnt,String[] styleDelim)
	{
		// por defecto content es cnt y styledConent es null
		content = cnt;
	
		// si cnt tiene estilos
		if( cnt!=null && cnt instanceof String )
		{
			StringBuffer sb = new StringBuffer();
			styles = MyString.extract(cnt.toString(),styleDelim[0],styleDelim[1],sb);
			
			if( styles.length>0 )
			{					
				// content queda crudo y styled content queda full
				content = sb.toString();
				styledContent = cnt.toString();
			}	
			
			styles = _expandirEstilos(styles,styleDelim[0],styleDelim[1]);
		}
		else
		{
			styles = new String[0];
		}
	}
	
	private String[] _expandirEstilos(String st[],String oDelim,String cDelim) {
	    ArrayList<String> resultList = new ArrayList<>();
	    
	    for (String s : st) {
	        // Eliminar los corchetes de inicio y fin
	        String trimmed = s.substring(1, s.length() - 1);
	        
	        // Separar los elementos por coma
	        String[] parts = trimmed.split(",");
	        
	        // Agregar cada parte como una nueva cadena entre corchetes
	        for (String part : parts) {
	            resultList.add(oDelim + part + cDelim);
	        }
	    }
	    
	    return resultList.toArray(new String[0]);
	}

	
	public String getStyledContent()
	{
		return styledContent;
	}

	public Object getContent()
	{
		return content;
	}

	public String[] getStyles()
	{
		return styles;
	}
	
	public int countStyles()
	{
		return styles.length;
	}
	
	@Override
	public String toString()
	{
		String s = "content=\""+content+"\", styledContent=\""+styledContent+"\", styles=";
		for(String x:styles)
		{
			s+="\""+x+"\" ";
		}
		
		return s;
	}
	
	public static void main(String[] args)
	{
		String s = "[a,b,c][d][ROLO , f]locomia";
		StyleManager x = new StyleManager(s);
		System.out.println(x);
	}
}
