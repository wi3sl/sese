package blue.hotel.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import blue.hotel.logic.CalculateReservation;
import blue.hotel.logic.SaveReservation;
import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;
import blue.hotel.model.RoomReservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

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
	private int reservationId = 0;
	
	public ReservationEditor(Reservation r) {
		this();
		readFrom(r);
	}

	public ReservationEditor() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("new Reservation");
		setSize(400, 748);
		setLocationRelativeTo(null);
		
		customerListModel = new DefaultListModel();
		
		roomReservationListModel = new DefaultListModel();
		
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel customerPanel = new JPanel();
		panel_2.add(customerPanel);
		customerPanel.setBorder(new TitledBorder(null, "Customer", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		customerPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("344px"),},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				RowSpec.decode("45px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		customerBox = new JComboBox();
		customerPanel.add(customerBox, "2, 2, fill, top");
		customerList = new JList(customerListModel);
		customerList.setVisibleRowCount(3);
		customerList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerPanel.add(customerList, "2, 3, 1, 2, fill, fill");
		
		customerList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (customerListModel.getSize() > 0){
					btnRemoveCustomer.setEnabled(true);
				} else{
					btnRemoveCustomer.setEnabled(false);
				}
			}
		});
		
		JPanel customerButtonPanel = new JPanel();
		customerButtonPanel.setBorder(null);
		customerPanel.add(customerButtonPanel, "2, 5, center, top");
		customerButtonPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("185px"),
				ColumnSpec.decode("75px"),
				ColumnSpec.decode("84px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		JButton btnAddCustomer = new JButton("Add");
		customerButtonPanel.add(btnAddCustomer, "2, 2, right, top");
		
		btnRemoveCustomer = new JButton("Remove");
		btnRemoveCustomer.setEnabled(false);
		customerButtonPanel.add(btnRemoveCustomer, "3, 2, right, top");
		
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
					calculate();
				}
			}
		});
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		
		JPanel roomPanel = new JPanel();
		panel_3.add(roomPanel);
		roomPanel.setBorder(new TitledBorder(null, "Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		roomPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("344px"),},
			new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("60px"),
				RowSpec.decode("max(40dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("33px"),}));
		
		roomBox = new JComboBox();
		roomPanel.add(roomBox, "2, 2, fill, default");
		roomReservationList = new JList(roomReservationListModel);
		roomReservationList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		roomPanel.add(roomReservationList, "2, 4, fill, fill");
		
		JPanel roomReservationPanel = new JPanel();
		roomPanel.add(roomReservationPanel, "2, 5, fill, fill");
		roomReservationPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("149px"),
				ColumnSpec.decode("left:195px"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("max(7dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
				RowSpec.decode("26px"),}));
		
		JLabel lblAduldts = new JLabel("Adult(s):");
		roomReservationPanel.add(lblAduldts, "1, 3, left, top");
		
		adultSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		roomReservationPanel.add(adultSpinner, "2, 3, fill, top");
		
		JLabel lblKids = new JLabel("Kid(s):");
		roomReservationPanel.add(lblKids, "1, 4, left, top");
		
		kidSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		roomReservationPanel.add(kidSpinner, "2, 4, fill, top");
		
		JPanel roomButtonPanel = new JPanel();
		roomPanel.add(roomButtonPanel, "2, 7, right, bottom");
		roomButtonPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("185px"),
				ColumnSpec.decode("75px"),
				ColumnSpec.decode("84px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		JButton btnAddRoomReservation = new JButton("Add");
		roomButtonPanel.add(btnAddRoomReservation, "2, 2, right, top");
		
		btnRemoveRoomReservation = new JButton("Remove");
		btnRemoveRoomReservation.setEnabled(false);
		roomButtonPanel.add(btnRemoveRoomReservation, "3, 2, right, top");
		
		btnAddRoomReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int amount = (Integer)adultSpinner.getValue() + (Integer)kidSpinner.getValue();
				Room room = (Room)roomBox.getSelectedItem();
				if(amount > room.getMaxPersons()){
					JOptionPane.showConfirmDialog(null, "Too many persons in one room!", "Error", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
					adultSpinner.setValue(1);
					kidSpinner.setValue(0);
				} else{
					addRoomReservation();
					calculate();
				}
			}
		});
		
		btnRemoveRoomReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (roomReservationList.getSelectedIndex() != -1){
					removeRoomReservation();
					calculate();
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
			}
		});
		
		JPanel panel_editor = new JPanel();
		getContentPane().add(panel_editor);
		panel_editor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel stayPanel = new JPanel();
		stayPanel.setBorder(new TitledBorder(null, "Stay", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_editor.add(stayPanel);
		stayPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("150px"),
				ColumnSpec.decode("195px"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("30px"),
				RowSpec.decode("30px"),}));
		
		JLabel lblArrival = new JLabel("Arrival:");
		stayPanel.add(lblArrival, "2, 2, left, top");
		arrivalDateField = new JDateChooser(cal.getTime());
		stayPanel.add(arrivalDateField, "3, 2, fill, top");
		
		arrivalDateField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("enter");
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("enter");
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				calculate();
				System.out.println("enter");
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("enter");
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("enter");
			}
		});
		
		
		JLabel lblDeparture = new JLabel("Departure:");
		stayPanel.add(lblDeparture, "2, 3, left, top");
		departureDateField = new JDateChooser(cal.getTime());
		stayPanel.add(departureDateField, "3, 3, fill, top");
		
		JPanel panel_5 = new JPanel();
		getContentPane().add(panel_5);
		
		JPanel billPanel = new JPanel();
		panel_5.add(billPanel);
		billPanel.setBorder(new TitledBorder(null, "Bill", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		billPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("15px"),
				ColumnSpec.decode("150px"),
				ColumnSpec.decode("195px"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("30px"),
				RowSpec.decode("50px"),}));
		
		JLabel lblPrice = new JLabel("Price:");
		billPanel.add(lblPrice, "2, 2, left, top");
		
		priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		billPanel.add(priceSpinner, "3, 2, fill, top");
		
		JLabel lblDiscount = new JLabel("Discount:");
		billPanel.add(lblDiscount, "2, 3, left, top");
		
		discountSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		billPanel.add(discountSpinner, "3, 3, fill, top");
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ValidationHandler.validate(ReservationEditor.this)) {
					ReservationEditor.this.accepted = true;
					ReservationEditor.this.setVisible(false);
				}
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
		o.setId(reservationId);
		o.setCustomers(tmpList);
		o.setPrice((Double) priceSpinner.getValue());
		o.setDiscount((Double) discountSpinner.getValue());
		o.setArrival(arrivalDateField.getDate());
		o.setDeparture(departureDateField.getDate());
		
		List<RoomReservation> rr = new ArrayList<RoomReservation>();
		for (int i=0; i<roomReservationListModel.getSize(); i++){
			RoomReservation tmp = (RoomReservation)roomReservationListModel.get(i);
			rr.add(tmp);		
		}
		try {
			o = SaveReservation.save(o, rr);
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
			roomReservationListModel.getSize() >0){
			
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

	@Override
	public boolean validateInput() {
		return inputErrors().equals("") ? true : false;
	}

	@Override
	public String inputErrors() {
		String errorMsg = "";
		if(customerListModel.getSize() < 1){
			errorMsg += "Please add a customer!\n";
		}
		
		if(roomReservationListModel.getSize() < 1){
			errorMsg += "Please add a room!\n";
		}
		
		Date arr = arrivalDateField.getDate();
		Date dep = departureDateField.getDate();
		if (dep.before(arr)){
			errorMsg += "The departure date should be after the arrival date!\n";
		}
		
		if ((Double)priceSpinner.getValue() <= 0.0){
			errorMsg += "Please set the price!\n";
		}
		
		
		Reservation tmp = new Reservation();
		List<RoomReservation> rooms = new ArrayList<RoomReservation>();
		for (int i=0; i<roomReservationListModel.getSize(); i++){
			RoomReservation rr = (RoomReservation)roomReservationListModel.get(i);
			rooms.add(rr);		
		}
		tmp.setRooms(rooms);
		tmp.setId(reservationId);
		tmp.setArrival(arrivalDateField.getDate());
		tmp.setDeparture(departureDateField.getDate());
		String roomReserved;
		try {
			roomReserved = SaveReservation.checkReservation(tmp);
			if(!roomReserved.equals("")){
				errorMsg += roomReserved + " is already reserved in this period!\n";
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return errorMsg.trim();
	}

}
