package thejavalistener.fwk.util;

public class Pair
{
	private Object a;
	private Object b;
	
	public Pair() {};
	public Pair(Object a,Object b) {this.a = a; this.b = b;};
	
	public Object getA()
	{
		return a;
	}
	public void setA(Object a)
	{
		this.a=a;
	}
	public Object getB()
	{
		return b;
	}
	public void setB(Object b)
	{
		this.b=b;
	}
	
	@Override
	public String toString()
	{
		return "{["+a.toString()+"], ["+b.toString()+"]}";
	}
}
