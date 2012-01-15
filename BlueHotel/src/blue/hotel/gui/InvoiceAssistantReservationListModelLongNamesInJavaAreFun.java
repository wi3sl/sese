package blue.hotel.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import blue.hotel.model.Reservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class InvoiceAssistantReservationListModelLongNamesInJavaAreFun extends DefaultListModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getElementAt(int index) {
		return l.get(index);
	}

	@Override
	public int getSize() {
		return l.size();
	}
	
	public List<Reservation> getSelectedReservations() {
		List<Reservation> reservations = new LinkedList<Reservation>();
		for (int i=0; i<l.size(); i++) {
			CheckListItem item = (CheckListItem)l.get(i);
			if (item.isSelected()) {
				reservations.add(item.getReservation());
			}			
		}
		return reservations;
	}

	//List<Reservation> l;
	List<CheckListItem> l;
	public InvoiceAssistantReservationListModelLongNamesInJavaAreFun() {
		l = new ArrayList<CheckListItem>();
		
		try {
			List<Reservation> rl = DAO.getInstance().getAll(Reservation.class);
						
			for(Reservation r : rl) {
				//do not display canceled reservations
				if(!r.isStorno()) {
					//do not display already booked reservations
					if(r.getInvoice() == null) {
						CheckListItem cli = new CheckListItem(r);
						cli.setSelected(false);
	
						l.add(cli);
					}
				}
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO This is just some code generated for GUI testing! Remove it when reservation creation is working fine..
//		for(int i = 0; i < 50; i++) {
//			Reservation r = new Reservation();
//			r.setId(i);
//			r.setPrice(10);
//			
//			
//			
//			Room room1= new Room();
//			room1.setName("Room name1");
//			Room room2= new Room();
//			room2.setName("Room name2");
//			Room room3= new Room();
//			room3.setName("Room name3");
//			
//			RoomReservation rr1 = new RoomReservation();
//			rr1.setRoom(room1);
//			RoomReservation rr2 = new RoomReservation();
//			rr2.setRoom(room2);
//			RoomReservation rr3 = new RoomReservation();
//			rr3.setRoom(room3);
//			
//			List rrlist = new ArrayList();
//			
//			if(i%2==0) {
//				rrlist.add(rr1);
//				rrlist.add(rr2);
//			} else if(i%3==0) {
//				rrlist.add(rr1);
//				rrlist.add(rr2);
//				rrlist.add(rr3);
//			} else {
//				rrlist.add(rr1);
//			}
//			r.setRooms(rrlist);
//			l.add(new CheckListItem(r));
//		}
			
	}
}
