package thejavalistener.fwk.frontend.hql.screen.instantapp;

public interface MyInstantAppScreen
{
	public void init(MyInstantApp app,Object ...args);
	public MyConfirm onAccept();
	public MyConfirm onClose();
	public void dataUpdated();
	public void start();
	public boolean stop();
}
