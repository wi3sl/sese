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

import blue.hotel.model.Room;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
				RoomEditor.this.accepted = true;
				RoomEditor.this.setVisible(false);
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
		
		JLabel lblMaxPersons = new JLabel("Max. Persons:");
		GridBagConstraints gbc_lblMaxPersons = new GridBagConstraints();
		gbc_lblMaxPersons.anchor = GridBagConstraints.EAST;
		gbc_lblMaxPersons.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxPersons.gridx = 0;
		gbc_lblMaxPersons.gridy = 1;
		panel_editor.add(lblMaxPersons, gbc_lblMaxPersons);
		
		spMaxPersons = new JSpinner();
		spMaxPersons.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		GridBagConstraints gbc_spMaxPersons = new GridBagConstraints();
		gbc_spMaxPersons.insets = new Insets(0, 0, 5, 0);
		gbc_spMaxPersons.fill = GridBagConstraints.HORIZONTAL;
		gbc_spMaxPersons.gridx = 1;
		gbc_spMaxPersons.gridy = 1;
		panel_editor.add(spMaxPersons, gbc_spMaxPersons);
		
		JLabel lblSinglePrice = new JLabel("Single Price:");
		GridBagConstraints gbc_lblSinglePrice = new GridBagConstraints();
		gbc_lblSinglePrice.anchor = GridBagConstraints.EAST;
		gbc_lblSinglePrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblSinglePrice.gridx = 0;
		gbc_lblSinglePrice.gridy = 2;
		panel_editor.add(lblSinglePrice, gbc_lblSinglePrice);
		
		spSinglePrice = new JSpinner();
		spSinglePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spSinglePrice = new GridBagConstraints();
		gbc_spSinglePrice.insets = new Insets(0, 0, 5, 0);
		gbc_spSinglePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spSinglePrice.gridx = 1;
		gbc_spSinglePrice.gridy = 2;
		panel_editor.add(spSinglePrice, gbc_spSinglePrice);
		
		JLabel lblDoublePrice = new JLabel("Double Price:");
		GridBagConstraints gbc_lblDoublePrice = new GridBagConstraints();
		gbc_lblDoublePrice.anchor = GridBagConstraints.EAST;
		gbc_lblDoublePrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblDoublePrice.gridx = 0;
		gbc_lblDoublePrice.gridy = 3;
		panel_editor.add(lblDoublePrice, gbc_lblDoublePrice);
		
		spDoublePrice = new JSpinner();
		spDoublePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spDoublePrice = new GridBagConstraints();
		gbc_spDoublePrice.insets = new Insets(0, 0, 5, 0);
		gbc_spDoublePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spDoublePrice.gridx = 1;
		gbc_spDoublePrice.gridy = 3;
		panel_editor.add(spDoublePrice, gbc_spDoublePrice);
		
		JLabel lblTriplePrice = new JLabel("Triple Price:");
		GridBagConstraints gbc_lblTriplePrice = new GridBagConstraints();
		gbc_lblTriplePrice.anchor = GridBagConstraints.EAST;
		gbc_lblTriplePrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblTriplePrice.gridx = 0;
		gbc_lblTriplePrice.gridy = 4;
		panel_editor.add(lblTriplePrice, gbc_lblTriplePrice);
		
		spTriplePrice = new JSpinner();
		spTriplePrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spTriplePrice = new GridBagConstraints();
		gbc_spTriplePrice.insets = new Insets(0, 0, 5, 0);
		gbc_spTriplePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spTriplePrice.gridx = 1;
		gbc_spTriplePrice.gridy = 4;
		panel_editor.add(spTriplePrice, gbc_spTriplePrice);
		
		JLabel lblSPwithOneKid = new JLabel("Single Price with one Kid:");
		GridBagConstraints gbc_lblSPwithOneKid = new GridBagConstraints();
		gbc_lblSPwithOneKid.anchor = GridBagConstraints.EAST;
		gbc_lblSPwithOneKid.insets = new Insets(0, 0, 5, 5);
		gbc_lblSPwithOneKid.gridx = 0;
		gbc_lblSPwithOneKid.gridy = 5;
		panel_editor.add(lblSPwithOneKid, gbc_lblSPwithOneKid);
		
		spSingleOneKidPrice = new JSpinner();
		spSingleOneKidPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spSingleOneKidPrice = new GridBagConstraints();
		gbc_spSingleOneKidPrice.insets = new Insets(0, 0, 5, 0);
		gbc_spSingleOneKidPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spSingleOneKidPrice.gridx = 1;
		gbc_spSingleOneKidPrice.gridy = 5;
		panel_editor.add(spSingleOneKidPrice, gbc_spSingleOneKidPrice);
		
		JLabel lblSPwithTwoKids = new JLabel("Single Price with two Kids:");
		GridBagConstraints gbc_lblSPwithTwoKids = new GridBagConstraints();
		gbc_lblSPwithTwoKids.anchor = GridBagConstraints.EAST;
		gbc_lblSPwithTwoKids.insets = new Insets(0, 0, 5, 5);
		gbc_lblSPwithTwoKids.gridx = 0;
		gbc_lblSPwithTwoKids.gridy = 6;
		panel_editor.add(lblSPwithTwoKids, gbc_lblSPwithTwoKids);
		
		spSingleTwoKidsPrice = new JSpinner();
		spSingleTwoKidsPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spSingleTwoKidsPrice = new GridBagConstraints();
		gbc_spSingleTwoKidsPrice.insets = new Insets(0, 0, 5, 0);
		gbc_spSingleTwoKidsPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spSingleTwoKidsPrice.gridx = 1;
		gbc_spSingleTwoKidsPrice.gridy = 6;
		panel_editor.add(spSingleTwoKidsPrice, gbc_spSingleTwoKidsPrice);
		
		JLabel lblDPwithOneKid = new JLabel("Double Price with one Kid:");
		GridBagConstraints gbc_lblDPwithOneKid = new GridBagConstraints();
		gbc_lblDPwithOneKid.anchor = GridBagConstraints.EAST;
		gbc_lblDPwithOneKid.insets = new Insets(0, 0, 5, 5);
		gbc_lblDPwithOneKid.gridx = 0;
		gbc_lblDPwithOneKid.gridy = 7;
		panel_editor.add(lblDPwithOneKid, gbc_lblDPwithOneKid);
		
		spDoubleOneKidPrice = new JSpinner();
		spDoubleOneKidPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5));
		GridBagConstraints gbc_spDoubleOneKidPrice = new GridBagConstraints();
		gbc_spDoubleOneKidPrice.insets = new Insets(0, 0, 5, 0);
		gbc_spDoubleOneKidPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_spDoubleOneKidPrice.gridx = 1;
		gbc_spDoubleOneKidPrice.gridy = 7;
		panel_editor.add(spDoubleOneKidPrice, gbc_spDoubleOneKidPrice);
		
		setSize(340, 420);
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
}
