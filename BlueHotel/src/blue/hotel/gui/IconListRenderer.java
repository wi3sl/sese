package blue.hotel.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;


import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class IconListRenderer extends JPanel implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel iconLabel = null;
    private JLabel textLabel = null;
    
    public IconListRenderer() {
        super(new BorderLayout());
        setOpaque(true);
        
        textLabel = new JLabel();
        textLabel.setOpaque(false);
        add(textLabel, BorderLayout.CENTER);  
        
        iconLabel = new JLabel();
        iconLabel.setOpaque(false);
        add(iconLabel, BorderLayout.EAST);         
    }
	 
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean chf)  {
    	iconLabel.setIcon(((IconListItem)value).getIcon());
    	textLabel.setText(((IconListItem)value).getObject().toString());
 
    	
    	if(index%2==0) {
    		setBackground(new Color(242, 242, 255));
    	} else {
    		setBackground(Color.WHITE);
    	}
    	
        if(isSelected) setBackground(new Color(232, 231, 255)); 
        
        return this;
    }
}