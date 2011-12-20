package blue.hotel.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ReservationTableColorRenderer extends DefaultTableCellRenderer {

	private Map<String, List<Date>> rooms;
	private Date date;

	public ReservationTableColorRenderer(Map<String, List<Date>> rooms,
			Date date) {
		this.rooms = rooms;
		this.date = date;
	}

	public void setDate(Date date){
		this.date = date;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (column == 0) {
			renderer.setBackground(Color.GRAY);
			renderer.setForeground(Color.WHITE);
			return renderer;
		} else if (column > 0){
			renderer.setBackground(Color.GREEN);
		}

		int day = (Integer) table.getModel().getValueAt(row, 0);
		String roomName = table.getModel().getColumnName(column);

		String tmpDate = (String) (day < 10 ? "0" + day : String.valueOf(day))
				+ "." + new SimpleDateFormat("MM.yyyy").format(date);

		for (Date d : rooms.get(roomName)) {
			if (tmpDate.equals(new SimpleDateFormat("dd.MM.yyyy").format(d))) {
				renderer.setBackground(Color.RED);
			}
		}

		return renderer;
	}
}
