package blue.hotel.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blue.hotel.model.Reservation;
import blue.hotel.model.RoomReservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class SaveReservation {
	public static Reservation save(Reservation reservation,
			List<RoomReservation> roomReservations) throws DAOException {
		DAO dao = DAO.getInstance();
		if (reservation.getId() == 0) {
			reservation = dao.create(reservation);
		}
		List<RoomReservation> tmp = new ArrayList<RoomReservation>();
		for (int i = 0; i < roomReservations.size(); i++) {
			RoomReservation rr = roomReservations.get(i);
			if (exists(reservation.getRooms(), rr)) {
				rr = dao.update(rr);
			} else {
				rr.setReservation(reservation);
				rr = dao.create(rr);
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
	 * 
	 * @param rrs
	 *            old rooms
	 * @param rr
	 *            current room
	 * @return
	 */
	private static boolean exists(List<RoomReservation> rrs, RoomReservation rr) {
		if (rrs == null) {
			return false;
		}
		if (rr.getRoom() == null || rr.getReservation() == null) {
			return false;
		}

		for (RoomReservation tmp : rrs) {
			if (tmp.getReservation().getId() == rr.getReservation().getId()
					&& tmp.getRoom().getId() == rr.getRoom().getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * deletes the unused rooms
	 * 
	 * @param dao
	 * @param old
	 *            old roomlist
	 * @param rr
	 *            current roomlist
	 */
	private static void delete(DAO dao, List<RoomReservation> old,
			List<RoomReservation> rr) throws DAOException {
		if (old == null || old.size() == 0)
			return;
		for (RoomReservation tmpOld : old) {
			boolean notPresent = true;
			for (RoomReservation tmpNew : rr) {
				if (tmpNew.getRoomReservationId() == tmpOld
						.getRoomReservationId()) {
					notPresent = false;
				}
			}
			if (notPresent) {
				dao.delete(tmpOld);
			}
		}
	}

	public static String checkReservation(Reservation r) throws DAOException {
		DAO dao = DAO.getInstance();
		for (Reservation tmp : dao.getAll(Reservation.class)) {
			if (tmp.getId() != r.getId()) {
				Date from = tmp.getArrival();
				Date to = tmp.getDeparture();
				if ((from.before(r.getDeparture()) || from.equals(r
						.getDeparture()))
						&& (r.getArrival().before(to) || r.getArrival().equals(
								to))) {
					List<RoomReservation> rooms = r.getRooms();
					for (RoomReservation room1 : tmp.getRooms()){
						for (RoomReservation room2 : rooms){
							if (room1.getRoom().getId() == room2.getRoom().getId()){
								return room1.getRoom().toString();
							}
						}
					}
				}
			}
		}
		return "";
	}

}
