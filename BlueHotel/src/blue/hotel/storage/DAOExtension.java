package blue.hotel.storage;

import java.util.List;

import javax.persistence.Query;

import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;

public class DAOExtension{
	
	private static DAO inst;
	
	public DAOExtension(){
		inst = DAO.getInstance();
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservationsFromRoom(Room r) throws DAOException {
		try {
			Query query = inst.getEntityManager().createQuery("select o from "+Reservation.class.getName()+" o join o.rooms r where r.roomReservationId.room.id= :roomId", Reservation.class)
            	    .setParameter("roomId", r.getId());
            return (List<Reservation>)query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservationsFromCustomer(Customer c) throws DAOException {
		try {
            Query query = inst.getEntityManager().createQuery("select o from "+Reservation.class.getName()+" o join o.customers c where c.id= :custId", Reservation.class)
            	    .setParameter("custId", c.getId());
            return (List<Reservation>)query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

}
