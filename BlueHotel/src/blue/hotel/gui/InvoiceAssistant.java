package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import blue.hotel.model.Customer;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;
import javax.swing.SpringLayout;

public class InvoiceAssistant extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel_1 = new JLabel("Total amount:");
	private JDateChooser textDeparture;
	public InvoiceAssistant() {
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.anchor = GridBagConstraints.NORTH;
		gbc_panel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 0;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.GROWING_BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("514px:grow"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("75px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("42px"),}));
		
		JPanel panel_3 = new JPanel();
		add(panel_3, "1, 2, fill, top");
		panel_3.setBorder(new TitledBorder(null, "General", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(510px;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:max(221dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default):grow"),}));
		
		JLabel lblNewLabel = new JLabel("Customer:");
		panel_3.add(lblNewLabel, "2, 2");
		
		JComboBox comboBox = new JComboBox();
		panel_3.add(comboBox, "4, 2, fill, default");
		
		JLabel lblReservationsToPay = new JLabel("Reservations to pay:");
		panel_3.add(lblReservationsToPay, "2, 4");
		

		
		JList list_1 = new JList();
		list_1.setValueIsAdjusting(true);
		list_1.setVisibleRowCount(30);
		list_1.setLayoutOrientation(JList.VERTICAL_WRAP);
		//list_1.setSize(new Dimension(400, 0));
		list_1.setFixedCellWidth(400);
		list_1.setModel(new InvoiceAssistantReservationListModelLongNamesInJavaAreFun());
		list_1.setCellRenderer(new CheckListRenderer());
		list_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();

				int index = list.locationToIndex(event.getPoint());
				CheckListItem item = (CheckListItem)
				list.getModel().getElementAt(index);
				
				// Toggle selected state
				item.setSelected(! item.isSelected());
				
				// Repaint cell
				list.repaint(list.getCellBounds(index, index));
			}
		}); 
		
		JScrollPane scrollPane = new JScrollPane(list_1);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane, "4, 4, fill, fill");
		
		JLabel lblDepartureDate = new JLabel("Departure date:");
		panel_3.add(lblDepartureDate, "2, 6, default, center");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_3.add(panel_4, "4, 6, fill, top");
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("129px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("26px"),}));
		
		textDeparture = new JDateChooser();
		panel_4.add(textDeparture, "1, 2, fill, center");
		//textDeparture.setColumns(10);
		
		JButton btnNewButton = new JButton("Today");
		panel_4.add(btnNewButton, "3, 2, fill, top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    textDeparture.setDate(new Date());
			}
		});
		
		JPanel panel_5 = new JPanel();
		add(panel_5, "1, 4, fill, top");
		panel_5.setBorder(new TitledBorder(null, "Amount", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("122px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(510px;default):grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("47px"),}));
		panel_5.add(lblNewLabel_1, "2, 2, left, top");
		
		JLabel lblTotalAmount = new JLabel("<...>");
		panel_5.add(lblTotalAmount, "4, 2, left, top");
		
		JPanel panel_2 = new JPanel();
		add(panel_2, "1, 6, right, top");
		panel_2.setBorder(new EmptyBorder(0, 10, 10, 10));
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("536px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("104px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),}));
		
		JButton btnPrintInvoice = new JButton("Print invoice");
		panel_2.add(btnPrintInvoice, "4, 2, left, top");
		btnPrintInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(InvoiceAssistant.this, "not implemented");
				InvoiceAssistant.this.setVisible(false);
			}
		});
		
		try {
			for (Customer c: DAO.getInstance().getAll(Customer.class)) {
				comboBox.addItem(c);
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setSize(715, 650);
//		setLocationRelativeTo(null);
	}
	
}
