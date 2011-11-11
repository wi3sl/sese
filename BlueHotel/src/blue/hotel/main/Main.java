package blue.hotel.main;

import blue.hotel.model.Customer;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class Main {
	public static void main(String [] args) {
		try {
			for (Customer c: DAO.getInstance().getAll(Customer.class)) {
				System.out.println("ID: " + c.getId() + ", Name: " + c.getName());
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
