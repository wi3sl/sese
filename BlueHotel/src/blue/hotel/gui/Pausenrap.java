package blue.hotel.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
	public static String nurNichtMich(String alle, Map<String, String> wollenSie) {
		String result = alle;
		for (Map.Entry<String, String> entry : wollenSie.entrySet()) {
			result = result.replace("{" + entry.getKey() + "}", entry.getValue());
		}
		return result;		
	}
	
	public static String dummesJavaHatKeinStrftime(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
	
	public static String istJoinZuVielVerlangt(List<?> list) {
		boolean first = true;
		StringBuffer s = new StringBuffer();
		
		for (Object o: list) {
			if (!first) {
				s.append(", ");
			}
			s.append(o.toString());
			first = false;
		}
		
		return s.toString();
	}
}
