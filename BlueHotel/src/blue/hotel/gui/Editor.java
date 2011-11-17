package blue.hotel.gui;

public interface Editor<T> {
	/* Copy data from a model object into the UI elements */
	public void readFrom(T o);
	
	/* Copy data from the UI elements into the model object */
	public void writeTo(T o);
	
	/* Editor will run in foreground and will return:
	 * 
	 *  true .... if the user clicked "Save"
	 *  false ... if the user clicked "Cancel"
	 */
	public boolean run();
}
