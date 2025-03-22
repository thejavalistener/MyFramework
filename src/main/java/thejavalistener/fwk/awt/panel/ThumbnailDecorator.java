package thejavalistener.fwk.awt.panel;

import java.awt.Color;

import thejavalistener.fwk.awt.link.MyLink;

public interface ThumbnailDecorator
{
	public Color getThumbnailBackground();
	public void decoreTitle(MyLink lnk);
	public void decoreArtist(MyLink lnk);
	public void decoreReleasedYear(MyLink lnk);
	public void decoreRecordedYear(MyLink lnk);
}
