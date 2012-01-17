package blue.hotel.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="roomreservation")
public class RoomReservation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RoomReservationId roomReservationId = new RoomReservationId();
	private int adults;
	private int kids;

	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(roomReservationId.getRoom().toString());
		/* Wow, that's one ugly Java hack! Java doesn't really like us :/ */
		if (adults > 0 || kids > 0) {
			sb.append(" (");
			if (adults > 0) {
				sb.append(""+adults+" adult");
				if (adults > 1) sb.append("s");
			}
			if (adults > 0 && kids > 0) {
				sb.append(", ");
			}
			if (kids> 0) {
				sb.append(""+kids+" kid");
				if (kids > 1) sb.append("s");
			}
			sb.append(")");			
		}
		return sb.toString();
	}
	
	public RoomReservation(){}

	@Transient
	public Reservation getReservation(){
		return roomReservationId.getReservation();
	}
	
	public void setReservation(Reservation reservation){
		roomReservationId.setReservation(reservation);
	}
	
	@Transient
	public Room getRoom(){
		return roomReservationId.getRoom();
	}
	
	public void setRoom(Room room){
		roomReservationId.setRoom(room);
	}
	
	public RoomReservationId getRoomReservationId() {
		return roomReservationId;
	}
	
	public void setRoomReservationId(RoomReservationId roomReservationId) {
		this.roomReservationId = roomReservationId;
	}
	
	public int getAdults() {
		return adults;
	}
	
	public void setAdults(int adults) {
		this.adults = adults;
	}
	
	public int getKids() {
		return kids;
	}
	
	public void setKids(int kids) {
		this.kids = kids;
	}
	
	
}
