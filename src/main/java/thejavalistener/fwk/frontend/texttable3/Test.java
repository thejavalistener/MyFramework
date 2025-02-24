package thejavalistener.fwk.frontend.texttable3;

public class Test
{
	public static void main(String[] args)
	{
//		MySingleTable t = new MySingleTable();
//		t.headers("Col 0","Col 1","Col 2").layout(4,6,8).aligns(-1,0,1);
//		t.showLineNumbers(true);
//		t.addRow( "00","01","02");
//		t.addRow( "10","11","12");
//		t.addRow( "20","21","22");
//		t.addLabeledBox("Prueba","Abanico Locomía",1);
//		t.makeTable();
//		System.out.println(t.drawTable());

		MyTwoEntryTable t2 = new MyTwoEntryTable("DEMO",10,Column.CENTER_ALIGN);
		t2.headers("Fila0","[b,i,RED]Fila1","[b,GREEN]Fila2").layout(7,10,12).aligns(0,1,1);
		t2.setBorders(0);
		t2.newEntry("Label 0").addRow("00",6.2,"02");
		t2.newEntry("[b]Label 1").addRow("[RED,b]10",12.46,"01");
		t2.newEntry("Label 2").addRow("20",8.1245,"22");
		t2.addLabeledBox("Prueba","Esta es mi prueba",.65);
		t2.addLabeledBox("Otra","[GREEN]Más pequeña",.35);
		t2.showLineSeparators(true);
		t2.makeTable();
		System.out.println(t2.drawTable());
		
//		MySingleTable tt = new MySingleTable();
//		tt.showLineNumbers(true);
//		tt.headers("c0").layout(10);
//		tt.addRow(4.1);
//		tt.addRow(25.23);
//		tt.addRow(6.123);
//		tt.addRow(2);
//		tt.addRow(1.0);
//		tt.makeTable();
//		System.out.println(tt.drawTable());

//		List<Producto> lst = new ArrayList<>();
//		lst.add(new Producto(10,"Papa",23.24));
//		lst.add(new Producto(20,"Pera",4.24));
//		lst.add(new Producto(30,"Manzana Verde",50.22));
//		lst.add(new Producto(40,"Zanahoria",30.24));
//				
//		MySingleTable tt = new MySingleTable();
//		tt.showLineNumbers(true);
//		tt.loadData(new FromObjectList(lst));
//		tt.autolayout();
//		tt.makeTable();
//		System.out.println(tt.drawTable());

//		List<Object[]> lst = new ArrayList<>();
//		lst.add(new Object[]{10,"Papa",23.12,new Date(System.currentTimeMillis())});
//		lst.add(new Object[]{10,"Zapallo",14.15,new Timestamp(System.currentTimeMillis())});
//		lst.add(new Object[]{10,"Sopa",2.5,new Timestamp(System.currentTimeMillis())});
//		lst.add(new Object[]{10,"Zanahoria",8.1,new Timestamp(System.currentTimeMillis())});
//		lst.add(new Object[]{10,"Pera",23.12,new Timestamp(System.currentTimeMillis())});
//		
//		MySingleTable tt = new MySingleTable();
//		tt.showLineNumbers(true);
//		tt.loadData(new FromObjectArrayList(lst));
//		tt.autolayout();
//		tt.makeTable();
//		System.out.println(tt.drawTable());
	}
}
