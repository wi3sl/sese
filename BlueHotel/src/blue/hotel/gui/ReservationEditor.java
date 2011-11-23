package blue.hotel.gui;

import javax.swing.JDialog;

import blue.hotel.logic.CalculateReservation;
import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;
import blue.hotel.model.RoomReservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSpinner;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class ReservationEditor extends JDialog implements Editor<Reservation>{
	private JDateChooser arrivalDateField;
	private JDateChooser departureDateField;
	private boolean accepted = false;
	private JButton btnRemoveCustomer;
	private DefaultListModel customerListModel;
	private JList customerList;
	private JSpinner priceSpinner;
	private JSpinner discountSpinner;
	private JComboBox customerBox;
	private JComboBox roomBox;
	private JSpinner adultSpinner;
	private JSpinner kidSpinner;
	private List<Customer> customers;
	private List<Room> rooms;
	private DefaultListModel roomReservationListModel;
	private JList roomReservationList;
	private JButton btnRemoveRoomReservation;
	private int reservationId = -1;
	
	public ReservationEditor(Reservation r) {
		this();
		readFrom(r);
	}

	public ReservationEditor() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("new Reservation");
		setSize(400, 600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_editor = new JPanel();
		panel_editor.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_editor);
		panel_editor.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(new TitledBorder(null, "Customer", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_editor.add(customerPanel);
		customerPanel.setLayout(new BorderLayout(0, 0));
		
		customerListModel = new DefaultListModel();
		customerList = new JList(customerListModel);
		customerList.setVisibleRowCount(3);
		customerList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerPanel.add(customerList, BorderLayout.WEST);
		
		JPanel customerButtonPanel = new JPanel();
		customerButtonPanel.setBorder(null);
		customerPanel.add(customerButtonPanel, BorderLayout.SOUTH);
		customerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnAddCustomer = new JButton("Add");
		customerButtonPanel.add(btnAddCustomer);
		
		btnRemoveCustomer = new JButton("Remove");
		btnRemoveCustomer.setEnabled(false);
		customerButtonPanel.add(btnRemoveCustomer);
		
		customerBox = new JComboBox();
		customerPanel.add(customerBox, BorderLayout.CENTER);
		
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(null, "Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_editor.add(roomPanel);
		roomPanel.setLayout(new BorderLayout(0, 0));
		
		roomReservationListModel = new DefaultListModel();
		roomReservationList = new JList(roomReservationListModel);
		roomPanel.add(roomReservationList, BorderLayout.WEST);
		
		JPanel roomReservationPanel = new JPanel();
		roomPanel.add(roomReservationPanel);
		roomReservationPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		roomBox = new JComboBox();
		roomReservationPanel.add(roomBox);
		
		JPanel roomPersonsPanel = new JPanel();
		roomReservationPanel.add(roomPersonsPanel);
		roomPersonsPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblAduldts = new JLabel("Aduldt(s):");
		roomPersonsPanel.add(lblAduldts);
		
		adultSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		roomPersonsPanel.add(adultSpinner);
		
		JLabel lblKids = new JLabel("Kid(s):");
		roomPersonsPanel.add(lblKids);
		
		kidSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		roomPersonsPanel.add(kidSpinner);
		
		JPanel roomButtonPanel = new JPanel();
		FlowLayout fl_roomButtonPanel = (FlowLayout) roomButtonPanel.getLayout();
		fl_roomButtonPanel.setAlignment(FlowLayout.RIGHT);
		roomPanel.add(roomButtonPanel, BorderLayout.SOUTH);
		
		JButton btnAddRoomReservation = new JButton("Add");
		roomButtonPanel.add(btnAddRoomReservation);
		
		btnRemoveRoomReservation = new JButton("Remove");
		roomButtonPanel.add(btnRemoveRoomReservation);
		
		JPanel stayPanel = new JPanel();
		stayPanel.setBorder(new TitledBorder(null, "Stay", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_editor.add(stayPanel);
		stayPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblArrival = new JLabel("Arrival:");
		stayPanel.add(lblArrival);
		
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		arrivalDateField = new JDateChooser(cal.getTime());
		stayPanel.add(arrivalDateField);
		
		JLabel lblDeparture = new JLabel("Departure:");
		stayPanel.add(lblDeparture);
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		departureDateField = new JDateChooser(cal.getTime());
		stayPanel.add(departureDateField);
		
		JPanel billPanel = new JPanel();
		billPanel.setBorder(new TitledBorder(null, "Bill", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_editor.add(billPanel);
		billPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblPrice = new JLabel("Price:");
		billPanel.add(lblPrice);
		
		priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		billPanel.add(priceSpinner);
		
		JLabel lblDiscount = new JLabel("Discount:");
		billPanel.add(lblDiscount);
		
		discountSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		billPanel.add(discountSpinner);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationEditor.this.accepted = true;
				ReservationEditor.this.setVisible(false);
			}
		});
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationEditor.this.accepted = false;
				ReservationEditor.this.setVisible(false);
			}
		});
		panel.add(btnCancel);
		
		DAO dao = DAO.getInstance();
		try {
			customers = dao.getAll(Customer.class);
			for (Customer c : customers){
				customerBox.addItem(c);
			}
			rooms = dao.getAll(Room.class);
			for (Room r : rooms){
				roomBox.addItem(r);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnAddCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Customer tmp = (Customer) customerBox.getSelectedItem();
				boolean notInList = true;
				for (int i=0; i<customerListModel.getSize(); i++){
					if (customerListModel.get(i).equals(tmp)){
						notInList = false;
					}
				}
				if (notInList){
					customerListModel.addElement(tmp);
					calculate();
				}
			}
		});
		
		btnRemoveCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(customerList.getSelectedIndex() != -1){
					customerListModel.remove(customerList.getSelectedIndex());
				}
			}
		});
		
		customerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (customerListModel.getSize() > 0){
					btnRemoveCustomer.setEnabled(true);
				} else{
					btnRemoveCustomer.setEnabled(false);
				}
				calculate();
			}
		});
		
		btnAddRoomReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int amount = (Integer)adultSpinner.getValue() + (Integer)kidSpinner.getValue();
				Room room = (Room)roomBox.getSelectedItem();
				if(room.getMaxPersons() >= amount){
					addRoomReservation();
					calculate();
				} else{
					JOptionPane.showConfirmDialog(null, "Too many persons in one room!", "Error", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
					adultSpinner.setValue(1.0);
					kidSpinner.setValue(0.0);
				}
			}
		});
		
		btnRemoveRoomReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (roomReservationList.getSelectedIndex() != -1){
					removeRoomReservation();
				}
			}
		});
		
		roomReservationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (roomReservationListModel.getSize() > 0){
					btnRemoveRoomReservation.setEnabled(true);
					if (roomReservationList.getSelectedIndex() != -1){
						setRoomReservationFields();
					}
				} else{
					btnRemoveRoomReservation.setEnabled(false);
				}
				calculate();
			}
		});
	}

	@Override
	public void readFrom(Reservation o) {
		setTitle("Edit reservation:" + o);
		reservationId = o.getId();
		for (Customer c: o.getCustomers()){
			customerListModel.addElement(c);
		}
		priceSpinner.setValue(o.getPrice());
		discountSpinner.setValue(o.getDiscount());
		arrivalDateField.setDate(o.getArrival());
		departureDateField.setDate(o.getDeparture());
		
		for(RoomReservation rr : o.getRooms()){
			roomReservationListModel.addElement(rr);
		}
	}

	@Override
	public void writeTo(Reservation o) {
		List<Customer> tmpList = new LinkedList<Customer>();
		for (int i=0; i<customerListModel.getSize(); i++){
			tmpList.add((Customer) customerListModel.get(i));
		}
		o.setCustomers(tmpList);
		o.setPrice((Double) priceSpinner.getValue());
		o.setDiscount((Double) discountSpinner.getValue());
		o.setArrival(arrivalDateField.getDate());
		o.setDeparture(departureDateField.getDate());
		
		try {
			if (reservationId == -1){
				DAO dao = DAO.getInstance();
				o = dao.create(o);
				System.out.println(o.getId());
				
				List<RoomReservation> rr = new ArrayList<RoomReservation>();
				for (int i=0; i<roomReservationListModel.getSize(); i++){
					RoomReservation tmp = (RoomReservation)roomReservationListModel.get(i);
					if(tmp.getReservation() == null){
						tmp.setReservation(o);
						tmp = dao.create(tmp);
					} else{
						tmp.setReservation(o);
						tmp = dao.update(tmp);
					}
					rr.add(tmp);		
				}
				
				o.setRooms(rr);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean run() {
		accepted = false;
		setVisible(true);
		return accepted;
	}
	
	private void addRoomReservation(){
		
		RoomReservation rr = new RoomReservation();
		rr.setKids((Integer)kidSpinner.getValue());
		rr.setAdults((Integer)adultSpinner.getValue());
		rr.setRoom((Room) roomBox.getSelectedItem());
		
		int id = -1;
		for (int i=0; i<roomReservationListModel.getSize(); i++){
			RoomReservation tmp = (RoomReservation)roomReservationListModel.get(i);
			if(tmp.getRoom().equals(rr.getRoom())){
				id = i;
			}
		}
		
		if (id == -1){
			roomReservationListModel.addElement(rr);
		} else {
			roomReservationListModel.remove(id);
			roomReservationListModel.add(id, rr);
		}
		
		adultSpinner.setValue((Integer)1);
		kidSpinner.setValue((Integer)0);
	}
	
	private void removeRoomReservation(){
		roomReservationListModel.remove(roomReservationList.getSelectedIndex());
	}
	
	private void setRoomReservationFields(){
		RoomReservation rr = (RoomReservation)roomReservationListModel.get(roomReservationList.getSelectedIndex());
		roomBox.setSelectedItem(rr.getRoom());
		adultSpinner.setValue(rr.getAdults());
		kidSpinner.setValue(rr.getKids());
	}
	
	private void calculate(){
		if((Integer)adultSpinner.getValue() < customerListModel.getSize()){
			adultSpinner.setValue(customerListModel.getSize());
		}
		
		if (customerListModel.getSize() > 0 &&
			roomBox.getSelectedIndex() != -1 &&
			(Integer)adultSpinner.getValue() >0){
			
			List<RoomReservation> rr = new ArrayList<RoomReservation>();
			for (int i=0; i<roomReservationListModel.getSize(); i++){
				rr.add((RoomReservation)roomReservationListModel.get(i));
			}
			
			priceSpinner.setValue(CalculateReservation.calcualtePrice(rr, arrivalDateField.getDate(), departureDateField.getDate()));
			
			List<Customer> c = new ArrayList<Customer>();
			for (int i=0; i<customerListModel.getSize(); i++){
				c.add((Customer)customerListModel.get(i));
			}
			discountSpinner.setValue(CalculateReservation.calcualteDiscount(c));
		}
		else{
			priceSpinner.setValue(0.0);
			discountSpinner.setValue(0.0);
		}
		
	}

}
