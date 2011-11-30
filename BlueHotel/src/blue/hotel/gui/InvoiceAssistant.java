package blue.hotel.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

public class InvoiceAssistant extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel_1 = new JLabel("Total amount:");
	private JTextField textDeparture;
	public InvoiceAssistant() {
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Customer:");
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		
		try {
			for (Customer c: DAO.getInstance().getAll(Customer.class)) {
				comboBox.addItem(c);
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblReservationsToPay = new JLabel("Reservations to pay:");
		panel.add(lblReservationsToPay);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setRowHeaderView(list);
		panel.add(lblNewLabel_1);
		
		list.setModel(new InvoiceAssistantReservationListModelLongNamesInJavaAreFun());
		
		JLabel lblTotalAmount = new JLabel("<...>");
		panel.add(lblTotalAmount);
		
		JLabel lblDepartureDate = new JLabel("Departure date:");
		panel.add(lblDepartureDate);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		textDeparture = new JTextField();
		panel_1.add(textDeparture);
		textDeparture.setColumns(10);
		
		JButton btnNewButton = new JButton("Today");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    textDeparture.setText("YYYY-MM-DD");
			}
		});
		panel_1.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvoiceAssistant.this.setVisible(false);
			}
		});
		panel.add(btnCancel);
		
		JButton btnPrintInvoice = new JButton("Print invoice");
		btnPrintInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(InvoiceAssistant.this, "not implemented");
				InvoiceAssistant.this.setVisible(false);
			}
		});
		panel.add(btnPrintInvoice);
		
		setSize(400, 300);
	}
	
}
