package blue.hotel.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import blue.hotel.model.Customer;

public class EditorManager {
	static Map<Class, Class> editors;
	
	static {
		editors = new HashMap<Class, Class>();
		
		editors.put(Customer.class, CustomerEditor.class);
		/* TODO: Add editors for your model classes here */
	}
	
	static <T> Editor<T> openEditor(Class<T> c) {
		for (Entry<Class, Class> entry : editors.entrySet()) {
			if (entry.getKey().equals(c)) {
				try {
					return (Editor<T>)entry.getValue().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
