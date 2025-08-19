package thejavalistener.fwk.awt;

import thejavalistener.fwk.awt.testui.MyTestUI;

public class MyTextScrollerTest
{
	public static void main(String[] args)
	{
		MyTextScroller scroll = new MyTextScroller();
		scroll.scroll("Esto es una cosa re copada porque no sé qué garcha voy a escrolear",100,MyTextScroller.SCROLLMODE_LOOP);
		scroll.start();
		MyTestUI t = MyTestUI.test(scroll.c()).size(100,100).run();		
	}
}
