package thejavalistener.fwk.awt.link;

import java.awt.Color;
import java.awt.Insets;

public class MyLinkedPaneStyle
{
	public Color linkPanelBackground = Color.WHITE;
	public Insets linkPanelInsets = new Insets(2,0,2,0);
	public int dividerSize = 0;
	public MyLinkStyle linkStyle = new MyLinkStyle().setBackground(linkPanelBackground);	
	
	public MyLinkedPaneStyle setLinkPanelBackground(Color c) {this.linkPanelBackground=c;return this;}
	public MyLinkedPaneStyle setLinkPaneInsets(Insets i) {this.linkPanelInsets=i;return this;}
	public MyLinkedPaneStyle setDividerSize(int s) {this.dividerSize=s;return this;}
	public MyLinkedPaneStyle setLinkStyle(MyLinkStyle ls) {this.linkStyle=ls;return this;}
}
