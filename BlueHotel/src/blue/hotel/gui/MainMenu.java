package blue.hotel.gui;

import javax.swing.JDialog;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainMenu extends JDialog {
	public MainMenu() {
		setTitle("[BlueHotel] Hotel Booking System");
		getContentPane().setLayout(new GridLayout(1, 2, 10, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 10, 0));
		
		JButton btnCustomerList = new JButton("Customers");
		panel.add(btnCustomerList);
		btnCustomerList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ObjectList<Customer>(Customer.class).setVisible(true);
			}
		});
		btnCustomerList.setIcon(new ImageIcon(MainMenu.class.getResource("/blue/hotel/data/users.png")));
		
		JButton btnRoomsList = new JButton("Rooms");
		panel.add(btnRoomsList);
		btnRoomsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ObjectList<Room>(Room.class).setVisible(true);
			}
		});
		btnRoomsList.setIcon(new ImageIcon(MainMenu.class.getResource("/blue/hotel/data/room.png")));
		
		JButton btnOtherList = new JButton("Reservations");
		panel.add(btnOtherList);
		btnOtherList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ObjectList<Reservation>(Reservation.class).setVisible(true);
			}
		});
		btnOtherList.setIcon(new ImageIcon(MainMenu.class.getResource("/blue/hotel/data/edit.png")));
		
		setSize(500, 120);
	}

}
