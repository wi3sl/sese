package blue.hotel.gui;

import javax.swing.JDialog;

import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
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
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class ReservationEditor extends JDialog implements Editor<Reservation>{
	private JDateChooser arrivalDateField;
	private JDateChooser departureDateField;
	private boolean accepted = false;
	private JButton btnRemove;
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
		
		JButton btnAdd = new JButton("Add");
		customerButtonPanel.add(btnAdd);
		
		btnRemove = new JButton("Remove");
		btnRemove.setEnabled(false);
		customerButtonPanel.add(btnRemove);
		
		customerBox = new JComboBox();
		customerPanel.add(customerBox, BorderLayout.CENTER);
		
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder(null, "Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_editor.add(roomPanel);
		roomPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		roomBox = new JComboBox();
		roomPanel.add(roomBox);
		
		JPanel roomPersonsPanel = new JPanel();
		roomPanel.add(roomPersonsPanel);
		roomPersonsPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblAduldts = new JLabel("Aduldt(s):");
		roomPersonsPanel.add(lblAduldts);
		
		adultSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		roomPersonsPanel.add(adultSpinner);
		
		JLabel lblKids = new JLabel("Kid(s):");
		roomPersonsPanel.add(lblKids);
		
		kidSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		roomPersonsPanel.add(kidSpinner);
		
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
		
		btnAdd.addActionListener(new ActionListener() {
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
		
		btnRemove.addActionListener(new ActionListener() {
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
					btnRemove.setEnabled(true);
				} else{
					btnRemove.setEnabled(false);
				}
				calculate();
			}
		});
		
		roomBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculate();
			}
		});
		
		adultSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				calculate();
			}
		});
		
		kidSpinner.addChangeListener(new ChangeListener() {		
			@Override
			public void stateChanged(ChangeEvent e) {
				calculate();
			}
		});
	}

	@Override
	public void readFrom(Reservation o) {
		setTitle("Edit reservation:" + o);
		for (Customer c: o.getCustomers()){
			customerListModel.addElement(c);
		}
		roomBox.setSelectedItem(o.getRoom());
		adultSpinner.setValue(o.getAdults());
		kidSpinner.setValue(o.getKids());
		priceSpinner.setValue(o.getPrice());
		discountSpinner.setValue(o.getDiscount());
		arrivalDateField.setDate(o.getArrival());
		departureDateField.setDate(o.getDeparture());
	}

	@Override
	public void writeTo(Reservation o) {
		List<Customer> tmpList = new LinkedList<Customer>();
		for (int i=0; i<customerListModel.getSize(); i++){
			tmpList.add((Customer) customerListModel.get(i));
		}
		o.setCustomers(tmpList);
		o.setRoom((Room) roomBox.getSelectedItem());
		o.setAdults((Integer) adultSpinner.getValue());
		o.setKids((Integer) kidSpinner.getValue());
		o.setPrice((Double) priceSpinner.getValue());
		o.setDiscount((Double) discountSpinner.getValue());
		o.setArrival(arrivalDateField.getDate());
		o.setDeparture(departureDateField.getDate());
	}

	@Override
	public boolean run() {
		accepted = false;
		setVisible(true);
		return accepted;
	}
	
	private void calculate(){
		if((Integer)adultSpinner.getValue() < customerListModel.getSize()){
			adultSpinner.setValue(customerListModel.getSize());
		}
		
		if (customerListModel.getSize() > 0 &&
			roomBox.getSelectedIndex() != -1 &&
			(Integer)adultSpinner.getValue() >0){
			int adults = (Integer)adultSpinner.getValue();
			int kids = (Integer)kidSpinner.getValue();
			Room room = (Room) roomBox.getSelectedItem();
			
			int days = (int)(departureDateField.getDate().getTime() - arrivalDateField.getDate().getTime())/ (1000 * 60 * 60 * 24);
			System.out.println(days);
			
			switch(adults){
				case 1:
					switch(kids){
					case 0:
						priceSpinner.setValue(days * room.getSinglePrice());
						break;
					case 1:
						priceSpinner.setValue(days * room.getSingleOneKidPrice());
						break;
					default:
						priceSpinner.setValue(days * room.getSingleTwoKidsPrice());
						break;
					}
					break;
				case 2:
					switch(kids){
					case 0:
						priceSpinner.setValue(days * room.getDoublePrice());
						break;
					default:
						priceSpinner.setValue(days * room.getDoubleOneKidPrice());
						break;
					}
					break;
				default:
					priceSpinner.setValue(days * room.getTriplePrice());
					break;
			}
			
			double discount = 0.0;
			int size = customerListModel.getSize();
			System.out.println(size);
			for (int i=0; i<size; i++){
				Customer c = (Customer) customerListModel.get(i);
				discount += c.getDiscount();
				System.out.println(c);
			}
			
			if (discount > 0.0){
				discountSpinner.setValue(discount / size);
			}
		}
		else{
			priceSpinner.setValue(0.0);
			discountSpinner.setValue(0.0);
		}
		
	}

}
