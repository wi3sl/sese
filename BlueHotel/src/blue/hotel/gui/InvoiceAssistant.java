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

public class InvoiceAssistant extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JLabel lblNewLabel_1 = new JLabel("Total amount:");
	private JDateChooser textDeparture;
	public InvoiceAssistant() {
		setTitle("Invoice");
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{494, 0};
		gridBagLayout.rowHeights = new int[]{338, 87, 39, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 5, 10));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "General", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(298px;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(134dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default):grow"),}));
		
		JLabel lblNewLabel = new JLabel("Customer:");
		panel_3.add(lblNewLabel, "2, 2");
		
		JComboBox comboBox = new JComboBox();
		panel_3.add(comboBox, "4, 2, fill, default");
		
		JLabel lblReservationsToPay = new JLabel("Reservations to pay:");
		panel_3.add(lblReservationsToPay, "2, 4");
		

		
		JList list = new JList();
		list.setValueIsAdjusting(true);
		list.setVisibleRowCount(256);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setSize(new Dimension(400, 0));
		list.setFixedCellWidth(400);
		list.setModel(new InvoiceAssistantReservationListModelLongNamesInJavaAreFun());
		list.setCellRenderer(new CheckListRenderer());
		list.addMouseListener(new MouseAdapter() {
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
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane, "4, 4, fill, fill");
		
		JLabel lblDepartureDate = new JLabel("Departure date:");
		panel_3.add(lblDepartureDate, "2, 6, default, center");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_3.add(panel_4, "4, 6, fill, center");
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("329px"),
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 10, 5, 10));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Amount", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_5);
		panel_5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("122px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(411px;default)"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("47px"),}));
		panel_5.add(lblNewLabel_1, "2, 2, left, top");
		
		JLabel lblTotalAmount = new JLabel("<...>");
		panel_5.add(lblTotalAmount, "4, 2, left, top");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 10, 10, 10));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		getContentPane().add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("439px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("104px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),}));
		
		JButton btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel, "2, 2, right, top");
		
		JButton btnPrintInvoice = new JButton("Print invoice");
		panel_2.add(btnPrintInvoice, "4, 2, left, top");
		btnPrintInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(InvoiceAssistant.this, "not implemented");
				InvoiceAssistant.this.setVisible(false);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		setSize(582, 502);
		setLocationRelativeTo(null);
	}
	
}
