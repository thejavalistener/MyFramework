package thejavalistener.fwk.awt.autocomplete;

import java.awt.BorderLayout;
import java.util.List;

import thejavalistener.fwk.awt.panel.MyBorderLayout;
import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyAutoCompleteTest
{
	public static void main(String[] args)
	{
		MyAutoCompleteField<String> ac = new MyAutoCompleteField<>();
		ac.setItems(List.of("Alberto","Pablo","Andrea","Paola","Paula","Angi","Iván","Ignacio"));
		MyBorderLayout p = new MyBorderLayout();
		p.add(ac.c(),BorderLayout.NORTH);
		
		MyTestUI.test(p).run();
	}
}
