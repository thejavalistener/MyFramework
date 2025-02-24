package thejavalistener.fwk.frontend.messages;

public interface MyScreenMessageListener
{
	/** Se invoca cada vez que una pantalla, a la que la clase
	 * esta escuchando, genera un mensaje. 
	 */
	public void onMessageEvent(MyScreenMessageEvent e);
}
