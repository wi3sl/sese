package blue.hotel.gui;

import java.util.List;

import blue.hotel.model.Reservation;

/**
 * Ich hab' den Pausenrap, und der ist ziemlich fett!
 */
public class Pausenrap {
	public static double taschenrechnerBorgen(List<Reservation> reservations) {
		double totalPrice = 0.;
		for (Reservation r : reservations) {
			totalPrice += r.getPrice() * (100.-r.getDiscount())/100.;
		}
		return totalPrice;
	}
}
