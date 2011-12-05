package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	JPanel panContent;
	JPanel panContentBox;
	JToggleButton btnInvoiceButton;
	JToggleButton btnOtherList;
	JToggleButton btnRoomsList;
	JToggleButton btnCustomerList;
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			// XXX: Don't do that - it looks shit under Linux, basically because Swing sucks.
	        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getContentPane().setLayout(new GridLayout(1, 2, 10, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panMenuBox = new JPanel();
		panMenuBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(panMenuBox, BorderLayout.NORTH);
		GridBagLayout gbl_panMenuBox = new GridBagLayout();
		gbl_panMenuBox.columnWidths = new int[]{190, 190, 190, 190, 0};
		gbl_panMenuBox.rowHeights = new int[]{77, 0};
		gbl_panMenuBox.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panMenuBox.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panMenuBox.setLayout(gbl_panMenuBox);
		
		btnOtherList = new JToggleButton("Reservations");
		btnRoomsList = new JToggleButton("Rooms");
		btnCustomerList = new JToggleButton("Customers");
		btnInvoiceButton = new JToggleButton("Invoices");

		btnCustomerList.setIcon(new ImageIcon(MainFrame.class.getResource("/blue/hotel/data/users.png")));
		GridBagConstraints gbc_btnCustomerList = new GridBagConstraints();
		gbc_btnCustomerList.fill = GridBagConstraints.BOTH;
		gbc_btnCustomerList.insets = new Insets(0, 0, 0, 5);
		gbc_btnCustomerList.gridx = 0;
		gbc_btnCustomerList.gridy = 0;
		panMenuBox.add(btnCustomerList, gbc_btnCustomerList);
		btnRoomsList.setIcon(new ImageIcon(MainFrame.class.getResource("/blue/hotel/data/room.png")));
		GridBagConstraints gbc_btnRoomsList = new GridBagConstraints();
		gbc_btnRoomsList.fill = GridBagConstraints.BOTH;
		gbc_btnRoomsList.insets = new Insets(0, 0, 0, 5);
		gbc_btnRoomsList.gridx = 1;
		gbc_btnRoomsList.gridy = 0;
		panMenuBox.add(btnRoomsList, gbc_btnRoomsList);
		

		btnInvoiceButton.setIcon(new ImageIcon(MainFrame.class.getResource("/blue/hotel/data/invoice.png")));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		panMenuBox.add(btnInvoiceButton, gbc_btnNewButton);
		btnOtherList.setIcon(new ImageIcon(MainFrame.class.getResource("/blue/hotel/data/edit.png")));
		GridBagConstraints gbc_btnOtherList = new GridBagConstraints();
		gbc_btnOtherList.fill = GridBagConstraints.BOTH;
		gbc_btnOtherList.gridx = 3;
		gbc_btnOtherList.gridy = 0;
		panMenuBox.add(btnOtherList, gbc_btnOtherList);
		
		panContentBox = new JPanel();
		panel.add(panContentBox, BorderLayout.CENTER);
		panContentBox.setLayout(new BorderLayout(0, 0));
		
		//action listeners for menu buttons
		btnInvoiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InvoiceAssistant().setVisible(true);
				btnOtherList.setSelected(false);
				btnRoomsList.setSelected(false);
				btnCustomerList.setSelected(false);
			}
		});
		
		btnOtherList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadContent(new ObjectList<Reservation>(Reservation.class));
				btnOtherList.setSelected(true);
				btnInvoiceButton.setSelected(false);
				btnRoomsList.setSelected(false);
				btnCustomerList.setSelected(false);
			}
		}); 
		
		btnRoomsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadContent(new ObjectList<Room>(Room.class));
				btnRoomsList.setSelected(true);
				btnInvoiceButton.setSelected(false);
				btnOtherList.setSelected(false);
				btnCustomerList.setSelected(false);
			}
		});
		
		btnCustomerList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadContent(new ObjectList<Customer>(Customer.class));
				btnCustomerList.setSelected(true);
				btnInvoiceButton.setSelected(false);
				btnOtherList.setSelected(false);
				btnRoomsList.setSelected(false);
			}
		});
		
		//set reservarion as default content
		if(panContent == null) {
			loadContent(new ObjectList<Reservation>(Reservation.class));
			btnOtherList.setSelected(true);
		}
		
		setSize(950, 800);
		setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("rawtypes")
	private void loadContent(ObjectList content) {
		panContent = content;
		panContentBox.removeAll();
		panContentBox.add(panContent, BorderLayout.CENTER);
		
		this.setTitle("[BlueHotel] Hotel Booking System - " + content.getSimpleName());
		this.validate();
	}
}
