package blue.hotel.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import blue.hotel.logic.SaveReservation;
import blue.hotel.model.Customer;
import blue.hotel.model.Reservation;
import blue.hotel.model.Room;
import blue.hotel.storage.DAO;
import blue.hotel.storage.DAOException;
import blue.hotel.storage.DAOExtension;

@SuppressWarnings("serial")
public class ObjectList<T> extends JPanel {
	private final String PLACE_HOLDER_STRING = "No entry available...";

	Class<T> klass;
	JList list;
	List<T> objects;
	JTextField txtSearch;

	//displayed search result of given string
	private void displaySearchResult(String searchString) {
		getObjects(searchString);
	}

	//reload all objects
	private void reloadObjects() {
		getObjects("");
	}

	private void getObjects(String searchString) {
		try {
			objects = DAO.getInstance().getAll(klass);
		} catch (DAOException e) {
			objects = new LinkedList<T>();
			e.printStackTrace();
		}

		if (list != null) {
			DefaultListModel dlm = new DefaultListModel();
			list.setCellRenderer(new IconListRenderer());

			for (T o: objects) {
				boolean add = true;
				String iconName = null;

				// if search string is provided display only elements containing it
				if(!searchString.equals("")) {
					if(!o.toString().toUpperCase().contains(searchString.toUpperCase())) {
						add = false;
					}
				}

				//do not display canceled reservations
				if(o instanceof Reservation) {
					Reservation res = (Reservation)o;
					if(res.isStorno()) add = false;

					//display right icon
					if(res.getInvoice() != null) {
						iconName = IconNames.INVOICE_ICON_NAME;
					} else {
						iconName = IconNames.INVOICE_MISSING_ICON_NAME;
					}
				}

				if(add) {
					IconListItem ili;

					if(iconName != null) {
						ili = new IconListItem(iconName,o);
					} else {
						ili = new IconListItem("",o);
					}

					dlm.addElement(ili);
				}

			}
			list.setModel(dlm);

			if(dlm.size() == 0) {
				dlm.addElement(new IconListItem("", PLACE_HOLDER_STRING));
			}
		}
	}

	public String getSimpleName() {
		return klass.getSimpleName();
	}

	public ObjectList(Class<T> klass) {
		this.klass = klass;
		this.setLayout(new BorderLayout(0, 0));

		JPanel panel_search = new JPanel();
		panel_search.setBorder(new EmptyBorder(10, 0, 10, 0));
		BorderLayout panel_search_layout = new BorderLayout();
		panel_search_layout.setHgap(10);
		panel_search.setLayout(panel_search_layout);
		this.add(panel_search, BorderLayout.NORTH);

		/* Search UI */
		panel_search.add(new JLabel("Search:"), BorderLayout.WEST);
		JButton btnClear = new JButton("Clear");
		panel_search.add(btnClear, BorderLayout.EAST);

		txtSearch = new JTextField();
		panel_search.add(txtSearch, BorderLayout.CENTER);

		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			public void onTextChanged() {
				displaySearchResult(txtSearch.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				onTextChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				onTextChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				onTextChanged();
			}
		});
		txtSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				/* Clear the input field when the user presses Esc */
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					txtSearch.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(panel, BorderLayout.SOUTH);

		//handle clear search
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText("");
				reloadObjects();
			}
		});

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(ObjectList.this, "Nothing selected.");
					return;
				}

				T o = (T)((IconListItem)list.getSelectedValue()).getObject();

				if(o.equals(PLACE_HOLDER_STRING)) {
					JOptionPane.showMessageDialog(ObjectList.this, "Please select a valid entry.");
					return;
				}

				Editor<T> editor = EditorManager.openEditor(ObjectList.this.klass);
				editor.readFrom(o);

				if (editor.run()) {
					editor.writeTo(o);
					try {
						DAO.getInstance().update(o);
					} catch (DAOException e1) {
						JOptionPane.showMessageDialog(ObjectList.this, "Cannot update: " + e1);
						e1.printStackTrace();
					}
				}
			}
		});

		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Editor<T> editor = EditorManager.openEditor(ObjectList.this.klass);
					if (editor.run()) {
						T o = ObjectList.this.klass.newInstance();
						editor.writeTo(o);
						if( ObjectList.this.klass == Reservation.class){
							DAO.getInstance().update(o);
						} else{
							DAO.getInstance().create(o);
						}
						ObjectList.this.reloadObjects();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.setLayout(new GridLayout(0, 3, 10, 0));
		panel.add(btnNew);
		panel.add(btnEdit);

		JButton btnDelete;
		if (klass.getName().equals(Reservation.class.getName())) {
			btnDelete = new JButton("Cancellation");
		} else {
			btnDelete = new JButton("Delete");
		}

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(ObjectList.this, "Nothing selected.");
					return;
				}

				T o = (T)((IconListItem)list.getSelectedValue()).getObject();

				if (o instanceof Customer) {
					DAOExtension ext = new DAOExtension();
					try {
						if (ext.getAllReservationsFromCustomer((Customer)o).size() > 0) {
							JOptionPane.showMessageDialog(ObjectList.this, "Cannot delete customer - this customer is referred in reservations.");
							return;
						}
					} catch (DAOException e1) {
						e1.printStackTrace();
					}
				}

				if (o instanceof Room) {
					DAOExtension ext = new DAOExtension();
					try {
						if (ext.getAllReservationsFromRoom((Room)o).size() > 0) {
							JOptionPane.showMessageDialog(ObjectList.this, "Cannot delete room - this room is referred reservations.");
							return;
						}
					} catch (DAOException e1) {
						e1.printStackTrace();
					}
				}

				if(o.equals(PLACE_HOLDER_STRING)) {
					JOptionPane.showMessageDialog(ObjectList.this, "Please select a valid entry.");
					return;
				}

				try {
					if(o instanceof Reservation) {
						//do storno if o is a reservation
						int option = JOptionPane.showConfirmDialog(ObjectList.this,
								"Do you really want do cancel this reservation?", "Cancellation",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);


						if(option == JOptionPane.YES_OPTION) {
							Reservation res = (Reservation)o;
							res.setStorno(true);
							res = SaveReservation.save(res, res.getRooms());
						}
					} else {
						// Confirmation before real deletion
						int option = JOptionPane.showConfirmDialog(ObjectList.this,
								"Do you really want to delete this " +
										o.getClass().getSimpleName() + "?\n\n" + o.toString(),
										"Deletion",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);

						if (option == JOptionPane.YES_OPTION) {
							//delete selected entry
							DAO.getInstance().delete(o);
						}
					}

					ObjectList.this.reloadObjects();
				} catch (DAOException e1) {
					JOptionPane.showMessageDialog(null,
							"Reservation could not be canceled! \n\n"+
									"The following error occured: " + e1.getMessage(),
									"Error",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.getViewport().setView(list);

		setSize(500, 500);
		reloadObjects();

		//handle double click on list item
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int index = list.locationToIndex(e.getPoint());

					IconListItem item = (IconListItem)list.getModel().getElementAt(index);;
					list.ensureIndexIsVisible(index);

					if(!item.getObject().toString().equals(PLACE_HOLDER_STRING)) {
						//open editor if valid item is selcted
						T o = (T)item.getObject();
						Editor<T> editor = EditorManager.openEditor(ObjectList.this.klass);

						editor.readFrom(o);

						if (editor.run()) {
							editor.writeTo(o);

							try {
								DAO.getInstance().update(o);
							} catch (DAOException e1) {
								JOptionPane.showMessageDialog(ObjectList.this, "Cannot update: " + e1);
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
	}
}
