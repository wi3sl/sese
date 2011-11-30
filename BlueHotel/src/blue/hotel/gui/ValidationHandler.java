package blue.hotel.gui;

import javax.swing.JOptionPane;

public class ValidationHandler {
	public static boolean validate(Editor<?> editor) {
		if (!editor.validateInput()) {
			JOptionPane.showMessageDialog(null,
					"Cannot save:\n\n" +
			        editor.inputErrors());
			return false;
		}
		
		return true;
	}
}
