package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import blue.hotel.model.Reservation;

@SuppressWarnings("serial")
public class ReservationAssistant extends JPanel {
	private JPanel contentPanel = new JPanel();

	public ReservationAssistant(boolean isAllocation) {
		this.setSize(715, 650);
		this.setLayout(new BorderLayout());
		
		contentPanel.setLayout(new BorderLayout(0, 0));
		this.add(contentPanel, BorderLayout.CENTER);
		
		if (isAllocation) {
			loadContent(new ReservationTable());
		} else {
			loadContent(new ObjectList<Reservation>(Reservation.class));	
		}
	}
	
	private void loadContent(JPanel content) {
		contentPanel.removeAll();
		contentPanel.add(content, BorderLayout.CENTER);
		this.validate();
	}
}
