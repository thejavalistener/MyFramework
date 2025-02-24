package thejavalistener.fwk.frontend.texttable3;

public class Producto
{
	private String descripcion;
	private int id;
	private double precio;

	public Producto(int i,String d,double p)
	{
		descripcion=d;
		id=i;
		precio=p;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion=descripcion;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public double getPrecio()
	{
		return precio;
	}

	public void setPrecio(double precio)
	{
		this.precio=precio;
	}

}
