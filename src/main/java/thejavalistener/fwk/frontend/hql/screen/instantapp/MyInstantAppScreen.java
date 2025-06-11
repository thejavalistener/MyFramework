package thejavalistener.fwk.frontend.hql.screen.instantapp;

public interface MyInstantAppScreen
{
	public void init(MyInstantApp app,Object ...args);
	public void onAccept();
	public boolean onClose();
	public void dataUpdated();
	public void start();
	public boolean stop();
}
