package blue.hotel.gui;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JSpinner;

import blue.hotel.model.Customer;
import javax.swing.SpinnerNumberModel;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CustomerEditor extends JDialog implements Editor<Customer> {
	private final JButton btnSave = new JButton("Save");
	private JTextField tfName;
	private JTextField tfAddress;
	private JTextField tfCompany;
	private JTextField tfNotes;
	private JTextField tfPhone;
	private JTextField tfEmail;
	private JTextField tfWeb;
	private JTextField tfFax;
	private JSpinner spDiscount;
	private boolean accepted = false;
	
	public CustomerEditor(Customer c) {
		this();
		readFrom(c);
	}
	
	public CustomerEditor() {
		setTitle("New customer");
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerEditor.this.accepted = true;
				CustomerEditor.this.setVisible(false);
			}
		});
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerEditor.this.accepted = false;
				CustomerEditor.this.setVisible(false);
			}
		});
		panel.add(btnCancel);
		
		JPanel panel_editor = new JPanel();
		panel_editor.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel_editor, BorderLayout.CENTER);
		GridBagLayout gbl_panel_editor = new GridBagLayout();
		gbl_panel_editor.columnWidths = new int[]{0, 0, 0};
		gbl_panel_editor.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_editor.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_editor.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_editor.setLayout(gbl_panel_editor);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel_editor.add(lblName, gbc_lblName);
		
		tfName = new JTextField();
		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 5, 0);
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 0;
		panel_editor.add(tfName, gbc_tfName);
		tfName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 1;
		panel_editor.add(lblAddress, gbc_lblAddress);
		
		tfAddress = new JTextField();
		GridBagConstraints gbc_tfAddress = new GridBagConstraints();
		gbc_tfAddress.insets = new Insets(0, 0, 5, 0);
		gbc_tfAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfAddress.gridx = 1;
		gbc_tfAddress.gridy = 1;
		panel_editor.add(tfAddress, gbc_tfAddress);
		tfAddress.setColumns(10);
		
		JLabel lblCompany = new JLabel("Company:");
		GridBagConstraints gbc_lblCompany = new GridBagConstraints();
		gbc_lblCompany.anchor = GridBagConstraints.EAST;
		gbc_lblCompany.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompany.gridx = 0;
		gbc_lblCompany.gridy = 2;
		panel_editor.add(lblCompany, gbc_lblCompany);
		
		tfCompany = new JTextField();
		GridBagConstraints gbc_tfCompany = new GridBagConstraints();
		gbc_tfCompany.insets = new Insets(0, 0, 5, 0);
		gbc_tfCompany.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCompany.gridx = 1;
		gbc_tfCompany.gridy = 2;
		panel_editor.add(tfCompany, gbc_tfCompany);
		tfCompany.setColumns(10);
		
		JLabel lblNotes = new JLabel("Notes:");
		GridBagConstraints gbc_lblNotes = new GridBagConstraints();
		gbc_lblNotes.anchor = GridBagConstraints.EAST;
		gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotes.gridx = 0;
		gbc_lblNotes.gridy = 3;
		panel_editor.add(lblNotes, gbc_lblNotes);
		
		tfNotes = new JTextField();
		GridBagConstraints gbc_tfNotes = new GridBagConstraints();
		gbc_tfNotes.insets = new Insets(0, 0, 5, 0);
		gbc_tfNotes.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNotes.gridx = 1;
		gbc_tfNotes.gridy = 3;
		panel_editor.add(tfNotes, gbc_tfNotes);
		tfNotes.setColumns(10);
		
		JLabel lblDiscount = new JLabel("Discount:");
		GridBagConstraints gbc_lblDiscount = new GridBagConstraints();
		gbc_lblDiscount.anchor = GridBagConstraints.EAST;
		gbc_lblDiscount.insets = new Insets(0, 0, 5, 5);
		gbc_lblDiscount.gridx = 0;
		gbc_lblDiscount.gridy = 4;
		panel_editor.add(lblDiscount, gbc_lblDiscount);
		
		spDiscount = new JSpinner();
		spDiscount.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));
		GridBagConstraints gbc_spDiscount = new GridBagConstraints();
		gbc_spDiscount.insets = new Insets(0, 0, 5, 0);
		gbc_spDiscount.fill = GridBagConstraints.HORIZONTAL;
		gbc_spDiscount.gridx = 1;
		gbc_spDiscount.gridy = 4;
		panel_editor.add(spDiscount, gbc_spDiscount);
		
		JLabel lblPhone = new JLabel("Phone:");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 5;
		panel_editor.add(lblPhone, gbc_lblPhone);
		
		tfPhone = new JTextField();
		GridBagConstraints gbc_tfPhone = new GridBagConstraints();
		gbc_tfPhone.insets = new Insets(0, 0, 5, 0);
		gbc_tfPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPhone.gridx = 1;
		gbc_tfPhone.gridy = 5;
		panel_editor.add(tfPhone, gbc_tfPhone);
		tfPhone.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 6;
		panel_editor.add(lblEmail, gbc_lblEmail);
		
		tfEmail = new JTextField();
		GridBagConstraints gbc_tfEmail = new GridBagConstraints();
		gbc_tfEmail.insets = new Insets(0, 0, 5, 0);
		gbc_tfEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEmail.gridx = 1;
		gbc_tfEmail.gridy = 6;
		panel_editor.add(tfEmail, gbc_tfEmail);
		tfEmail.setColumns(10);
		
		JLabel lblWeb = new JLabel("Web:");
		GridBagConstraints gbc_lblWeb = new GridBagConstraints();
		gbc_lblWeb.anchor = GridBagConstraints.EAST;
		gbc_lblWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeb.gridx = 0;
		gbc_lblWeb.gridy = 7;
		panel_editor.add(lblWeb, gbc_lblWeb);
		
		tfWeb = new JTextField();
		GridBagConstraints gbc_tfWeb = new GridBagConstraints();
		gbc_tfWeb.insets = new Insets(0, 0, 5, 0);
		gbc_tfWeb.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfWeb.gridx = 1;
		gbc_tfWeb.gridy = 7;
		panel_editor.add(tfWeb, gbc_tfWeb);
		tfWeb.setColumns(10);
		
		JLabel lblFax = new JLabel("Fax:");
		GridBagConstraints gbc_lblFax = new GridBagConstraints();
		gbc_lblFax.anchor = GridBagConstraints.EAST;
		gbc_lblFax.insets = new Insets(0, 0, 5, 5);
		gbc_lblFax.gridx = 0;
		gbc_lblFax.gridy = 8;
		panel_editor.add(lblFax, gbc_lblFax);
		
		tfFax = new JTextField();
		GridBagConstraints gbc_tfFax = new GridBagConstraints();
		gbc_tfFax.insets = new Insets(0, 0, 5, 0);
		gbc_tfFax.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfFax.gridx = 1;
		gbc_tfFax.gridy = 8;
		panel_editor.add(tfFax, gbc_tfFax);
		tfFax.setColumns(10);
		
		setSize(340, 350);
	}

	@Override
	public void readFrom(Customer o) {
		setTitle("Edit customer:" + o);
		tfName.setText(o.getName());
		tfAddress.setText(o.getAddress());
		tfCompany.setText(o.getCompany());
		tfNotes.setText(o.getNotes());
		tfPhone.setText(o.getPhone());
		tfEmail.setText(o.getEmail());
		tfWeb.setText(o.getWeb());
		tfFax.setText(o.getFax());
		spDiscount.setValue(o.getDiscount());
	}

	@Override
	public void writeTo(Customer o) {
		o.setName(tfName.getText());
		o.setAddress(tfAddress.getText());
		o.setCompany(tfCompany.getText());
		o.setNotes(tfNotes.getText());
		o.setPhone(tfPhone.getText());
		o.setEmail(tfEmail.getText());
		o.setWeb(tfWeb.getText());
		o.setFax(tfFax.getText());
		o.setDiscount((Double)spDiscount.getValue());
	}

	@Override
	public boolean run() {
		accepted = false;
		setVisible(true);
		return accepted;
	}
}
