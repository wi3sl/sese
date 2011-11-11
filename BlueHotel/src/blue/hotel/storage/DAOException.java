package blue.hotel.storage;

public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DAOException(Exception cause) {
		super(cause);
	}
}
