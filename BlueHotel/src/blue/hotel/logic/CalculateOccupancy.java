package blue.hotel.logic;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;

import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;
import blue.hotel.storage.DAOExtension;
import blue.hotel.model.Room;
import blue.hotel.model.Reservation;

public class CalculateOccupancy {
	
	private DAO dao;
	private DAOExtension daoEx;
	
	/**
	 * 
	 * @return the occupancy of the all rooms or null
	 */
	public Map<String, List<Date>> calculateRoomOccupancy(){
		dao = DAO.getInstance();
		daoEx = new DAOExtension();
		List<Room> rooms;
		try {
			rooms = dao.getAll(Room.class);
		} catch (DAOException e) {
			return null;
		}
		
		Map<String, List<Date>> roomOccupancy = new HashMap<String, List<Date>>();
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		for(Room room : rooms){
			LinkedList<Date> dates = new LinkedList<Date>();
			List<Reservation> reservations;
			try {
				reservations = daoEx.getAllReservationsFromRoom(room);
			} catch (DAOException e) {
				return null;
			}
			for(Reservation reservation : reservations){
				dates.add(reservation.getArrival());
				System.out.println(reservation.getArrival());
				Date helper = new Date(reservation.getArrival().getTime());
				helper.setTime(helper.getTime() + MILLIS_IN_DAY);
				while(helper.getTime() < reservation.getDeparture().getTime()){
					dates.add(new Date(helper.getTime()));
					helper.setTime(helper.getTime() + MILLIS_IN_DAY);
				}
				dates.add(reservation.getDeparture());
				System.out.println(reservation.getDeparture());
			}
			roomOccupancy.put(room.getName(),dates);
		}
		
		return roomOccupancy;
	}

}
