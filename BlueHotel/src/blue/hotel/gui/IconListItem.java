package blue.hotel.gui;

import javax.swing.*;

public class IconListItem {
    private ImageIcon icon = null;
    private Object o;
 
    public IconListItem(String iconName, Object o) 
    {    	
    	if(iconName.equals(IconNames.INVOICE_MISSING_ICON_NAME)) {
    		icon = new ImageIcon(IconListItem.class.getResource(IconNames.INVOICE_MISSING_ICON_RES));
    	} else if(iconName.equals(IconNames.INVOICE_ICON_NAME)) {
    		icon = new ImageIcon(IconListItem.class.getResource(IconNames.INVOICE_ICON_RES));
    	} else {
    		icon = new ImageIcon(IconListItem.class.getResource(IconNames.FILLER_ICON_RES));
    	}
    		
        this.o = o;
    }       
 
    public ImageIcon getIcon() {
    	return icon;
    }
 

    public Object getObject() 
    {
        return o;	
    }
}