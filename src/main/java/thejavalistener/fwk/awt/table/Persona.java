package thejavalistener.fwk.awt.table;

public class Persona
{
	int dni;String nombre;String direccion;
	public Persona(int dni,String nom,String dir)
	{
		this.dni=dni;nombre=nom;direccion=dir;
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
	public String getDireccion()
	{
		return direccion;
	}
	public void setDireccion(String direccion)
	{
		this.direccion=direccion;
	}
	
	

}
