package thejavalistener.fwk.util;

import java.io.File;

public class MyFile
{
	/** Verifica $filename no existe => lo crea, retornando true o false si lo creó o no */
	public static boolean createIfNotExists(String filename)
	{
		try
		{
			File f = new File(filename);
			boolean existe = f.exists();
			if( !existe )
			{
				f.createNewFile();
			}				
			
			return !existe;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
