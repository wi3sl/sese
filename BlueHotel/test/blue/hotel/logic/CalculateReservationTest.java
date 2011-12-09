package blue.hotel.logic;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import blue.hotel.model.Customer;
import blue.hotel.model.Room;
import blue.hotel.model.RoomReservation;

public class CalculateReservationTest {
	
	private List<Customer> customers;
	private Customer c1,c2,c3;
	private List<RoomReservation> rooms;
	private RoomReservation rr;
	
	@Before
	public void setup(){
		customers = new LinkedList<Customer>();
		c1 = new Customer();
		c1.setDiscount(10.0);
		c2 = new Customer();
		c2.setDiscount(20.0);
		c3 = new Customer();
		c3.setDiscount(0.0);
		
		rooms = new LinkedList<RoomReservation>();
		Room room = new Room(1, "room", 3, 10.0,
				8, 7, 5,
				4, 3);
		rr = new RoomReservation();
		rr.setAdults(2);
		rr.setKids(1);
		rr.setRoom(room);
	}
	
	@Test
	public void testCalcualteDiscount_ShouldReturnDiscount() {	
		customers.add(c1);
		assertEquals(10.0, CalculateReservation.calcualteDiscount(customers),0.0);
	}
	
	@Test
	public void testCalcualteDiscount_ShouldReturnMeanDiscount() {		
		customers.add(c1);
		customers.add(c2);
		assertEquals(15.0, CalculateReservation.calcualteDiscount(customers),0.0);
	}
	
	@Test
	public void testCalcualteDiscount_ShouldReturnZero() {		
		customers.add(c3);
		assertEquals(0.0, CalculateReservation.calcualteDiscount(customers),0.0);
	}
	
	@Test
	public void testcalCualtePrice_ShouldReturnPrice() {	
		rooms.add(rr);
		assertEquals(9.0, CalculateReservation.calcualtePrice(rooms, new Date(), new Date(500000L)),0.0);
	}
}
