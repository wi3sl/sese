package blue.hotel.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="reservation")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Customer> customers;
	@OneToMany
	private List<RoomReservation> rooms;
	private double discount;
	private double price;
	private Date arrival;
	private Date departure;
	private boolean storno;
	
	@ManyToOne(optional=true)
	private Invoice invoice;
	
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String tmpc = "";
		for (Customer c: customers){
			tmpc += c.getName() + ", ";
		}
		tmpc = tmpc.substring(0, tmpc.length() - 2);
		return "Reservation #" + id + "(" + tmpc + ") / " + 
				"Date: " +  df.format(arrival) + " - " + df.format(departure);
	}	
	
	public Reservation(){}
	
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
	public List<RoomReservation> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomReservation> rooms) {
		this.rooms = rooms;
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

	public boolean isStorno() {
		return storno;
	}

	public void setStorno(boolean storno) {
		this.storno = storno;
	}
}
