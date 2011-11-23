package blue.hotel.logic;

import java.util.Date;
import java.util.List;

import blue.hotel.model.Customer;
import blue.hotel.model.RoomReservation;

public class CalculateReservation {

	
	public static double calcualtePrice(List<RoomReservation> rooms, Date arrivalDate, Date departureDate){
		double price = 0.0;
		for (RoomReservation rr : rooms){
			int adults = rr.getAdults();
			int kids = rr.getKids();
			switch(adults){
				case 1:
					switch(kids){
					case 0:
						price += rr.getRoom().getSinglePrice();
						break;
					case 1:
						price += rr.getRoom().getSingleOneKidPrice();
						break;
					default:
						price += rr.getRoom().getSingleTwoKidsPrice();
						break;
					}
					break;
				case 2:
					switch(kids){
					case 0:
						price += rr.getRoom().getDoublePrice();
						break;
					default:
						price += rr.getRoom().getDoubleOneKidPrice();
						break;
					}
					break;
				default:
					price += rr.getRoom().getTriplePrice();
					break;
			}
		}
		int days = (int)(departureDate.getTime() - arrivalDate.getTime())/ (1000 * 60 * 60 * 24);
		
		if (price > 0.0){
			price *= days;
		}
		
		return price;
	}
	
	public static double calcualteDiscount(List<Customer> customers){
		double discount = 0;
		for (Customer c : customers){
			discount += c.getDiscount();
		}
		
		if (discount > 0.0){
			discount /= customers.size();
		}
		
		return discount;
	}
}
