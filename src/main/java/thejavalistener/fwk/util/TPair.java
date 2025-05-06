package thejavalistener.fwk.util;

public class TPair<A,B>
{
	private A a;
	private B b;
	
	public TPair() {};
	public TPair(A a,B b) {this.a = a; this.b = b;};
	
	public A getA()
	{
		return a;
	}
	
	public String getStringA()
	{
		return a!=null?a.toString():"null";
	}
	
	public String getStringB()
	{
		return b!=null?b.toString():"null";
	}
	
	public void setA(A a)
	{
		this.a=a;
	}
	public B getB()
	{
		return b;
	}
	public void setB(B b)
	{
		this.b=b;
	}
	
	@Override
	public String toString()
	{
		return "{["+a.toString()+"], ["+b.toString()+"]}";
	}
}
