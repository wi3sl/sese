package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import blue.hotel.model.Room;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class RoomEditor extends JDialog implements Editor<Room> {
	private final JButton btnSave = new JButton("Save");
	private JTextField tfName;
	private JSpinner spMaxPersons;
	private JSpinner spSinglePrice;
	private JSpinner spDoublePrice;
	private JSpinner spTriplePrice;
	private JSpinner spSingleTwoKidsPrice;
	private JSpinner spSingleOneKidPrice;
	private JSpinner spDoubleOneKidPrice;
	private boolean accepted = false;
	
	public RoomEditor(Room r) {
		this();
		readFrom(r);
	}
	
	public RoomEditor() {
		setTitle("New room");
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ValidationHandler.validate(RoomEditor.this)) {
					RoomEditor.this.accepted = true;
					RoomEditor.this.setVisible(false);
				}
			}
		});
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomEditor.this.accepted = false;
				RoomEditor.this.setVisible(false);
			}
		});
		panel.add(btnCancel);
		
		JPanel panel_editor = new JPanel();
		panel_editor.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel_editor, BorderLayout.CENTER);
		GridBagLayout gbl_panel_editor = new GridBagLayout();
		gbl_panel_editor.columnWidths = new int[]{0, 0, 0};
		gbl_panel_editor.rowHeights = new int[]{115, 0, 0};
		gbl_panel_editor.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_editor.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_editor.setLayout(gbl_panel_editor);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "General ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel_editor.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("179px"),
				ColumnSpec.decode("125px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name:");
		panel_1.add(lblName, "2, 2, left, center");
		
		tfName = new JTextField();
		panel_1.add(tfName, "3, 2, fill, top");
		tfName.setColumns(10);
		
		JLabel lblMaxPersons = new JLabel("Max. Persons:");
		panel_1.add(lblMaxPersons, "2, 4");
		
		spMaxPersons = new JSpinner();
		panel_1.add(spMaxPersons, "3, 4, fill, default");
		spMaxPersons.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spMaxPersons.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				enableDisableSpinners();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Prices", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.SOUTH;
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_editor.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("179px"),
				ColumnSpec.decode("125px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSinglePrice = new JLabel("Single Price:");
		panel_2.add(lblSinglePrice, "2, 2, left, default");
		
		spSinglePrice = new JSpinner();
		panel_2.add(spSinglePrice, "3, 2, fill, default");
		spSinglePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		JLabel lblDoublePrice = new JLabel("Double Price:");
		panel_2.add(lblDoublePrice, "2, 4");
		
		spDoublePrice = new JSpinner();
		panel_2.add(spDoublePrice, "3, 4, fill, default");
		spDoublePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		JLabel lblTriplePrice = new JLabel("Triple Price:");
		panel_2.add(lblTriplePrice, "2, 8");
		
		spTriplePrice = new JSpinner();
		panel_2.add(spTriplePrice, "3, 8, fill, default");
		spTriplePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		JLabel lblSPwithOneKid = new JLabel("Single Price with one Kid:");
		panel_2.add(lblSPwithOneKid, "2, 6");
		
		spSingleOneKidPrice = new JSpinner();
		panel_2.add(spSingleOneKidPrice, "3, 6, fill, default");
		spSingleOneKidPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		JLabel lblSPwithTwoKids = new JLabel("Single Price with two Kids:");
		panel_2.add(lblSPwithTwoKids, "2, 10");
		
		spSingleTwoKidsPrice = new JSpinner();
		panel_2.add(spSingleTwoKidsPrice, "3, 10, fill, default");
		spSingleTwoKidsPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		JLabel lblDPwithOneKid = new JLabel("Double Price with one Kid:");
		panel_2.add(lblDPwithOneKid, "2, 12");
		
		spDoubleOneKidPrice = new JSpinner();
		panel_2.add(spDoubleOneKidPrice, "3, 12, fill, default");
		spDoubleOneKidPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		
		setSize(379, 420);
		setLocationRelativeTo(null);
		enableDisableSpinners();
	}
	
	private void enableDisableSpinners() {
		int value = (Integer) spMaxPersons.getValue();
		
		spSinglePrice.setEnabled(value >= 1);
		spDoublePrice.setEnabled(value >= 2);
		spTriplePrice.setEnabled(value >= 3);
		spSingleTwoKidsPrice.setEnabled(value >= 3);
		spSingleOneKidPrice.setEnabled(value >= 2);
		spDoubleOneKidPrice.setEnabled(value >= 3);
	}

	@Override
	public void readFrom(Room o) {
		setTitle("Edit room:" + o);
		tfName.setText(o.getName());
		spMaxPersons.setValue(o.getMaxPersons());
		spSinglePrice.setValue(o.getSinglePrice());
		spDoublePrice.setValue(o.getDoublePrice());
		spTriplePrice.setValue(o.getTriplePrice());
		spSingleTwoKidsPrice.setValue(o.getSingleTwoKidsPrice());
		spSingleOneKidPrice.setValue(o.getSingleOneKidPrice());
		spDoubleOneKidPrice.setValue(o.getDoubleOneKidPrice());
	}

	@Override
	public void writeTo(Room o) {
			o.setName(tfName.getText());
			o.setMaxPersons((Integer)spMaxPersons.getValue());
			o.setSinglePrice((Double)spSinglePrice.getValue());
			o.setDoublePrice((Double)spDoublePrice.getValue());
			o.setTriplePrice((Double)spTriplePrice.getValue());
			o.setSingleTwoKidsPrice((Double)spSingleTwoKidsPrice.getValue());
			o.setSingleOneKidPrice((Double)spSingleOneKidPrice.getValue());
			o.setDoubleOneKidPrice((Double)spDoubleOneKidPrice.getValue());
	}

	@Override
	public boolean run() {
		accepted = false;
		setVisible(true);
		return accepted;
	}
	
	private List<JSpinner> getPriceSpinners() {
		List<JSpinner> priceSpinners = new LinkedList<JSpinner>();
		priceSpinners.add(spSinglePrice);
		priceSpinners.add(spDoublePrice);
		priceSpinners.add(spTriplePrice);
		priceSpinners.add(spSingleTwoKidsPrice);
		priceSpinners.add(spSingleOneKidPrice);
		priceSpinners.add(spDoubleOneKidPrice);
		return priceSpinners;
	}

	@Override
	public boolean validateInput() {
		if("".equals(tfName.getText()))
			return false;
		
		if((Integer)spMaxPersons.getValue() == 0)
			return false;
		
		for (JSpinner spinner : getPriceSpinners()) {
			if (spinner.isEnabled() && (Double)spinner.getValue() == 0) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String inputErrors() {
		String result = "";
		
		if ("".equals(tfName.getText())) {
			result += "Please enter a name.\n";
		}
		
		if ((Integer)spMaxPersons.getValue() == 0) {
			result += "The number of max. persons must be greater than 0.\n";
		}
		
		for (JSpinner spinner : getPriceSpinners()) {
			if (spinner.isEnabled() && (Double)spinner.getValue() == 0) {
				result += "All enabled prices must be greater than 0.\n";
				break;
			}
		}
		
		return result.trim();
	}
}
