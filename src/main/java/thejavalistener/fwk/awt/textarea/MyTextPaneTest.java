package thejavalistener.fwk.awt.textarea;

import java.awt.Color;
import java.awt.Font;

import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyTextPaneTest
{
	public static void main(String[] args)
	{
		MyTextPane mtp = new MyTextPane(true,true);
		mtp.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		MyTestUI tui = MyTestUI.test(mtp.c());
		mtp.setText("Soy [b]Pablo[x] y mi color favorito es [RED]rojo[x]\n\n\n");
		mtp.appendText("0         1         2         3         4         5         \n");
        mtp.appendText("012345678901234567890123456789012345678901234567890123456789\n");

        tui.addTextField("Texto")
		   .addTextField("Desde")
		   .addTextField("Hasta")
		   .addButton("cp",(s)->cp(mtp))
		   .addButton("Rmv",(s)->{mtp.deleteText(tui.getInt("Desde"),tui.getInt("Hasta"));cp(mtp);})
		   .addButton("Col",(s)->System.out.println(mtp.getCaretColumnPosition()))
		   .addButton("Insertar",(s)->{mtp.insertText(tui.getString("Texto"),tui.getInt("Desde"));cp(mtp);})
		   .addButton("Replace",(s)->{mtp.replaceText(tui.getString("Texto"),tui.getInt("Desde"),tui.getInt("Hasta"));;cp(mtp);})
		   .addButton("Rojo",(s)->mtp.fg(Color.RED))
		   .addButton("Verde",(s)->mtp.fg(Color.GREEN))
		   .addButton("Azul",(s)->mtp.fg(Color.BLUE))
		   .addButton("B",(s)->mtp.b())
		   .addButton("I",(s)->mtp.i())
		   .addButton("X",(s)->mtp.x(tui.getInt("Desde")))
		   .addButton("Formated",(s)->mtp.setFormatedText(mtp.getText()))
		   .run();
	}

	private static void cp(MyTextPane x)
	{
		System.out.println(x.getCaretPosition());
	}
}
