package blue.hotel.logic;

import java.util.ArrayList;
import java.util.List;

import blue.hotel.model.Reservation;
import blue.hotel.model.RoomReservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class SaveReservation {
	public static Reservation save(Reservation reservation, List<RoomReservation> roomReservations) throws DAOException{
		System.out.println("save reservation:");
		DAO dao = DAO.getInstance();
		if (reservation.getId() == 0){
			reservation = dao.create(reservation);
			System.out.println("new");
		}
		List<RoomReservation> tmp = new ArrayList<RoomReservation>();
		for (int i=0; i<roomReservations.size(); i++){
			RoomReservation rr = roomReservations.get(i);
			if(exists(reservation.getRooms(), rr)){
				rr = dao.update(rr);
				System.out.println("update: " + rr);
			} else{
				rr.setReservation(reservation);
				rr = dao.create(rr);
				System.out.println("create: " + rr);
			}
			tmp.add(rr);
		}
		List<RoomReservation> old = reservation.getRooms();
		reservation.setRooms(tmp);
		delete(dao, old, tmp);
		return reservation;
	}
	
	/**
	 * checks if a room is already present in the reservation 
	 * @param rrs old rooms
	 * @param rr current room
	 * @return
	 */
	private static boolean exists(List<RoomReservation> rrs, RoomReservation rr){
		if (rrs == null){
			return false;
		}
		if (rr.getRoom() == null || rr.getReservation() == null){
			return false;
		}
		
		for (RoomReservation tmp : rrs){
			if (tmp.getReservation().getId() == rr.getReservation().getId() &&
					tmp.getRoom().getId() == rr.getRoom().getId()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * deletes the unused rooms
	 * @param dao
	 * @param old old roomlist
	 * @param rr current roomlist
	 */
	private static void delete(DAO dao, List<RoomReservation> old, List<RoomReservation> rr) throws DAOException{
		for (RoomReservation tmpOld : old){
			boolean notPresent = true;
			for (RoomReservation tmpNew : rr){
				if (tmpNew.getRoomReservationId() == tmpOld.getRoomReservationId()){
					notPresent = false;
				}
			}
			if (notPresent){
				System.out.println("delete: " + tmpOld);
				dao.delete(tmpOld);
			}
		}
	}
}
 