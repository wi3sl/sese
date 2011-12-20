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
	private JToggleButton listButton;
	private JToggleButton graphicButton;
	
	public ReservationAssistant() {
		this.setSize(715, 650);
		this.setLayout(new BorderLayout());
		
		listButton = new JToggleButton("Show List");
		graphicButton = new JToggleButton("Show Graphic");
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 1));
		buttons.add(listButton);
		buttons.add(graphicButton);
		this.add(buttons, BorderLayout.EAST);
		
		contentPanel.setLayout(new BorderLayout(0, 0));
		this.add(contentPanel, BorderLayout.CENTER);
		
		loadContent(new ObjectList<Reservation>(Reservation.class));
		listButton.setSelected(true);
		
		listButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadContent(new ObjectList<Reservation>(Reservation.class));
				listButton.setSelected(true);
				graphicButton.setSelected(false);
			}
		});
		
		graphicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadContent(new ReservationTable());
				graphicButton.setSelected(true);
				listButton.setSelected(false);
			}
		});
	}
	
	private void loadContent(JPanel content) {
		contentPanel.removeAll();
		contentPanel.add(content, BorderLayout.CENTER);
		this.validate();
	}
}
