package blue.hotel.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RoomReservationId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Reservation reservation;
	@ManyToOne
	private Room room;
	
	public RoomReservationId(){}
	
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
}
