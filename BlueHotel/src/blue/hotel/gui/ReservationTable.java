package blue.hotel.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import blue.hotel.logic.CalculateOccupancy;

@SuppressWarnings("serial")
public class ReservationTable extends JPanel {

	private JPanel monthPanel = new JPanel();
	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;
	
	private Map<String, List<Date>> rooms;
	private Calendar calendar = Calendar.getInstance();
	
	private DefaultTableModel model = new DefaultTableModel();
	private ReservationTableColorRenderer renderer;

	public ReservationTable() {
		CalculateOccupancy co = new CalculateOccupancy();
		rooms = co.calculateRoomOccupancy();
		
		init();
	}
	
	private void init(){
		this.setSize(715, 650);
		this.setLayout(new BorderLayout());
		
		JTable table = new JTable(model);
		renderer = new ReservationTableColorRenderer(rooms, calendar.getTime());
		table.setDefaultRenderer(Object.class, renderer);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		model.addColumn("");
		for (String roomName : rooms.keySet()){
			model.addColumn(roomName);
		}
		
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setMaxWidth(30);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		monthChooser = new JMonthChooser();
		yearChooser = new JYearChooser();
		
		monthPanel.add(monthChooser);
		monthPanel.add(yearChooser);
		
		monthChooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				fillTable();
			}
		});
		
		yearChooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				fillTable();
			}
		});
		
		this.add(monthPanel, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	private void fillTable(){
		while (model.getRowCount() > 0){
			model.removeRow(0);
		}
		
		calendar.set(yearChooser.getYear(), monthChooser.getMonth(), 1);
		renderer.setDate(calendar.getTime());
		
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for (int i=1; i<=days; i++){
			model.addRow(new Object[]{i});
		}
	}
}
