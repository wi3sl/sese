package blue.hotel.gui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("rawtypes")
class CheckListRenderer extends JCheckBox implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5698619425974874860L;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
				setEnabled(list.isEnabled());
				setSelected(((CheckListItem)value).isSelected());
				setFont(list.getFont());
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				setText(value.toString());
				
				return this;
			}
}