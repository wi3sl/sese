package blue.hotel.gui;

import java.util.List;

import javax.swing.DefaultListModel;

import blue.hotel.model.Reservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class InvoiceAssistantReservationListModelLongNamesInJavaAreFun extends DefaultListModel {
	@Override
	public Object getElementAt(int index) {
		return l.get(index);
	}

	@Override
	public int getSize() {
		return l.size();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Reservation> l;
	
	public InvoiceAssistantReservationListModelLongNamesInJavaAreFun() {
		try {
			l = DAO.getInstance().getAll(Reservation.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
