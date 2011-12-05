package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import blue.hotel.model.Customer;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

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
				if (ValidationHandler.validate(CustomerEditor.this)) {
					CustomerEditor.this.accepted = true;
					CustomerEditor.this.setVisible(false);
				}
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
		gbl_panel_editor.rowHeights = new int[]{0, 0, 0, 52, 0, 0, 0, 0, 50, 18, 0, 46, 0};
		gbl_panel_editor.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_editor.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_editor.setLayout(gbl_panel_editor);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "General ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.gridheight = 4;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_editor.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("118px"),
				ColumnSpec.decode("160px"),},
			new RowSpec[] {
				RowSpec.decode("2px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name:");
		panel_3.add(lblName, "2, 3, left, center");
		
		tfName = new JTextField();
		panel_3.add(tfName, "3, 3, fill, top");
		tfName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		panel_3.add(lblAddress, "2, 5, left, center");
		
		tfAddress = new JTextField();
		panel_3.add(tfAddress, "3, 5, fill, top");
		tfAddress.setColumns(10);
		
		JLabel lblCompany = new JLabel("Company:");
		panel_3.add(lblCompany, "2, 7, left, center");
		
		tfCompany = new JTextField();
		panel_3.add(tfCompany, "3, 7, fill, top");
		tfCompany.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Contact", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.gridheight = 5;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		panel_editor.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("118px"),
				ColumnSpec.decode("160px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFax = new JLabel("Fax:");
		panel_1.add(lblFax, "2, 2, left, center");
		
		tfFax = new JTextField();
		panel_1.add(tfFax, "3, 2, fill, top");
		tfFax.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone:");
		panel_1.add(lblPhone, "2, 4, left, center");
		
		tfPhone = new JTextField();
		panel_1.add(tfPhone, "3, 4, fill, top");
		tfPhone.setColumns(10);
		
		JLabel lblWeb = new JLabel("Web:");
		panel_1.add(lblWeb, "2, 6, left, center");
		
		tfWeb = new JTextField();
		panel_1.add(tfWeb, "3, 6, fill, top");
		tfWeb.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		panel_1.add(lblEmail, "2, 8, left, center");
		
		tfEmail = new JTextField();
		panel_1.add(tfEmail, "3, 8, fill, top");
		tfEmail.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Additional Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 3;
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 9;
		panel_editor.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("118px"),
				ColumnSpec.decode("160px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblDiscount = new JLabel("Discount:");
		panel_2.add(lblDiscount, "2, 2, left, center");
		
		spDiscount = new JSpinner();
		panel_2.add(spDiscount, "3, 2, fill, top");
		spDiscount.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));
		
		JLabel lblNotes = new JLabel("Notes:");
		panel_2.add(lblNotes, "2, 4, left, top");
		
		tfNotes = new JTextField();
		panel_2.add(tfNotes, "3, 4, fill, top");
		tfNotes.setColumns(10);
		
		setSize(340, 427);
		setLocationRelativeTo(null);
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

	@Override
	public boolean validateInput() {
		/* Fixes bug 1: Kunde ohne Name und Adresse kann erstellt
		 * werden (die zwei Felder sollten zumindes Pflichtfelder sein) */
		return (!"".equals(tfName.getText()) && !"".equals(tfAddress.getText()));
	}

	@Override
	public String inputErrors() {
		String result = "";
		
		if ("".equals(tfName.getText())) {
			result += "Please enter a name.\n";
		}
		
		if ("".equals(tfAddress.getText())) {
			result += "Please enter an address.\n";
		}
		
		return result.trim();
	}
}
