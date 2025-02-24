package thejavalistener.fwk.util;

public class Persona
{
	private int dni;
	private String nombre;
	@Override
	public String toString()
	{
		return "Persona [dni="+dni+", nombre="+nombre+"]";
	}
	public int getDni()
	{
		return dni;
	}
	public void setDni(int dni)
	{
		this.dni=dni;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}
	
	
}
