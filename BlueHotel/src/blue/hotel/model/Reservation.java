package blue.hotel.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToMany()
	private List<Customer> customers;
	@ManyToOne
	private Room room;
	private double discount;
	private double price;
	private Date arrival;
	private Date departure;
	private int adults;
	private int kids;
	
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String tmpc = "";
		for (Customer c: customers){
			tmpc += c.getName() + ", ";
		}
		tmpc = tmpc.substring(0, tmpc.length() - 2);
		return "Reservation #" + id + "(" + tmpc + ") / " + 
				"Date: " +  df.format(arrival) + " - " + df.format(departure) + " / " +
				"Room: " + room.getName();
	}	
	
	public Reservation(){}
	
	public Reservation(int id, List<Customer> customers, Room room,
			double discount, double price, Date arrival, Date departure) {
		super();
		this.id = id;
		this.customers = customers;
		this.room = room;
		this.discount = discount;
		this.price = price;
		this.arrival = arrival;
		this.departure = departure;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getArrival() {
		return arrival;
	}
	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}
	public Date getDeparture() {
		return departure;
	}
	public void setDeparture(Date departure) {
		this.departure = departure;
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
