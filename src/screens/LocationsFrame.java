package screens;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import daoImplements.LocationsDAOImplements;
import data.Locations;

import javax.swing.JTable;

public class LocationsFrame extends BaseFrame {

	private LocationsDAOImplements locationsDAOImplements = new LocationsDAOImplements();

	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Location ID", "Street Address", "Postal Code", "City", "State Province", "Country ID" };
	Object[] rows = new Object[6];
	private JTextField txtStreetAddress;
	private JTextField txtPostalCode;
	private JTextField txtCity;
	private JTextField txtStateProvince;
	private JTextField txtCountryID;
	private ResultSet mySet;
	private JTable tableLocations;
	private HashMap<String, Integer> hm_LocationsId;

	@Override
	public void emptyTextFields() {
		txtCity.setText("");
		txtCountryID.setText("");
		txtPostalCode.setText("");
		txtStateProvince.setText("");
		txtStreetAddress.setText("");
	}

	@Override
	public void enabledTrueTextFields() {
		txtCity.setEnabled(true);
		txtCountryID.setEnabled(true);
		txtPostalCode.setEnabled(true);
		txtStateProvince.setEnabled(true);
		txtStreetAddress.setEnabled(true);

	}

	@Override
	public void enabledFalseTextFields() {
		txtCity.setEnabled(false);
		txtCountryID.setEnabled(false);
		txtPostalCode.setEnabled(false);
		txtStateProvince.setEnabled(false);
		txtStreetAddress.setEnabled(false);
	}

	public void buttonAddClick() throws SQLException {
		try {
			if (!txtCity.getText().equals("")) {
				Locations locations = new Locations(txtCity.getText());
				if (!txtCountryID.getText().equals("")) {
					locations.setCountryID(txtCountryID.getText());
				}
				if (!txtPostalCode.getText().equals("")) {
					locations.setCountryID(txtPostalCode.getText());
				}
				if (!txtStateProvince.getText().equals("")) {
					locations.setCountryID(txtStateProvince.getText());
				}
				if (!txtStreetAddress.getText().equals("")) {
					locations.setCountryID(txtStreetAddress.getText());
				}
				int status = locationsDAOImplements.add(locations);
				System.out.println("status " + status);

				if (status == 1) {
					JOptionPane.showMessageDialog(null, "Successfully saved.");
					pnlSetButton1.setVisible(false);
					pnlSetButton2.setVisible(true);
					emptyTextFields();
					initialize();
				} else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);

				} else if (status == 3) {
					JOptionPane.showMessageDialog(null, "Foreign key problem.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 2) {
					JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 4) {
					JOptionPane.showMessageDialog(null, "Mismatched failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);

			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You entered the wrong character.Please enter a number", "Warning", 2);
		}

	}

	@Override
	public void buttonDeleteClick() throws SQLException {

		if (txtCity.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete");
		} else {
			if (hm_LocationsId.containsKey(txtCity.getText())) {

				int status = locationsDAOImplements.delete(hm_LocationsId.get(txtCity.getText()));
				if (status == 1) {
					emptyTextFields();
					JOptionPane.showMessageDialog(null, "Deleted.");
					hm_LocationsId.remove(txtCity.getText());
					initialize();
				} else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Unknown error");
				}

			}
		}
	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		try {
			if (!txtCity.getText().equals("")) {
				Locations locations = new Locations(txtCity.getText());

				if (!txtCountryID.getText().equals("")) {
					locations.setCountryID(txtCountryID.getText());
				}
				if (!txtPostalCode.getText().equals("")) {
					locations.setPostalCode(txtPostalCode.getText());
				}
				if (!txtStateProvince.getText().equals("")) {
					locations.setStateProvince(txtStateProvince.getText());
				}
				if (!txtCity.getText().equals("")) {
					locations.setCity(txtCity.getText());
				}
				if (!txtStreetAddress.getText().equals("")) {
					locations.setStreetAddress(txtStreetAddress.getText());
				}

				int status = locationsDAOImplements.update(locations, hm_LocationsId.get(txtCity.getText()));
				if (status == 1) {
					JOptionPane.showMessageDialog(null, "Updated.");
					pnlSetButton1.setVisible(false);
					pnlSetButton2.setVisible(true);
					emptyTextFields();
					initialize();
				} else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 2) {
					JOptionPane.showMessageDialog(null, "Mismatched field", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 3) {
					JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You entered the wrong character.Please enter a number", "Warning", 2);
		}
	}

	public void showTable() {
		myModel.setColumnIdentifiers(columns);
		LocationsDAOImplements locationsDAO = new LocationsDAOImplements();
		try {
			mySet = locationsDAO.getData();
			while (mySet.next()) {
				hm_LocationsId.put(mySet.getString("CITY"), Integer.parseInt(mySet.getString("LOCATION_ID")));
				rows[0] = "" + mySet.getString("LOCATION_ID");
				rows[1] = "" + mySet.getString("STREET_ADDRESS");
				rows[2] = "" + mySet.getString("POSTAL_CODE");
				rows[3] = mySet.getString("CITY");
				rows[4] = "" + mySet.getString("STATE_PROVINCE");
				rows[5] = "" + mySet.getString("COUNTRY_ID");
				myModel.addRow(rows);
			}
			tableLocations.setModel(myModel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initialize(){
		hm_LocationsId = new HashMap<>();
		setTitle("LOCATIONS");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(111, 37, 757, 261);
		getContentPane().add(scrollPane);

		tableLocations = new JTable();
		tableLocations.setModel(myModel);
		tableLocations.setBounds(119, 331, 541, 191);

		scrollPane.setViewportView(tableLocations);
		showTable();

		JPanel panel = new JPanel();
		panel.setBounds(216, 330, 631, 294);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 100, 0, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblStreetAddress = new JLabel("Street Address");
		GridBagConstraints gbc_lblStreetAddress = new GridBagConstraints();
		gbc_lblStreetAddress.anchor = GridBagConstraints.WEST;
		gbc_lblStreetAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblStreetAddress.gridx = 3;
		gbc_lblStreetAddress.gridy = 1;
		panel.add(lblStreetAddress, gbc_lblStreetAddress);

		txtStreetAddress = new JTextField();
		txtStreetAddress.setEnabled(false);
		txtStreetAddress.setColumns(10);
		GridBagConstraints gbc_txtStreetAddress = new GridBagConstraints();
		gbc_txtStreetAddress.anchor = GridBagConstraints.WEST;
		gbc_txtStreetAddress.insets = new Insets(0, 0, 5, 5);
		gbc_txtStreetAddress.gridx = 5;
		gbc_txtStreetAddress.gridy = 1;
		panel.add(txtStreetAddress, gbc_txtStreetAddress);
		txtStreetAddress.setColumns(10);

		JLabel lblPostalCode = new JLabel("Postal Code");
		GridBagConstraints gbc_lblPostalCode = new GridBagConstraints();
		gbc_lblPostalCode.anchor = GridBagConstraints.WEST;
		gbc_lblPostalCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostalCode.gridx = 3;
		gbc_lblPostalCode.gridy = 3;
		panel.add(lblPostalCode, gbc_lblPostalCode);

		txtPostalCode = new JTextField();
		txtPostalCode.setEnabled(false);
		txtPostalCode.setColumns(10);
		GridBagConstraints gbc_txtPostalCode = new GridBagConstraints();
		gbc_txtPostalCode.anchor = GridBagConstraints.WEST;
		gbc_txtPostalCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtPostalCode.gridx = 5;
		gbc_txtPostalCode.gridy = 3;
		panel.add(txtPostalCode, gbc_txtPostalCode);
		txtPostalCode.setColumns(10);

		JLabel lblCity = new JLabel("City");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 3;
		gbc_lblCity.gridy = 5;
		panel.add(lblCity, gbc_lblCity);

		txtCity = new JTextField();
		txtCity.setEnabled(false);
		txtCity.setText("");
		txtCity.setColumns(10);
		GridBagConstraints gbc_txtCity = new GridBagConstraints();
		gbc_txtCity.anchor = GridBagConstraints.WEST;
		gbc_txtCity.insets = new Insets(0, 0, 5, 5);
		gbc_txtCity.gridx = 5;
		gbc_txtCity.gridy = 5;
		panel.add(txtCity, gbc_txtCity);
		txtCity.setColumns(10);

		JLabel lblStateProvince = new JLabel("State Province");
		GridBagConstraints gbc_lblStateProvince = new GridBagConstraints();
		gbc_lblStateProvince.anchor = GridBagConstraints.WEST;
		gbc_lblStateProvince.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateProvince.gridx = 3;
		gbc_lblStateProvince.gridy = 7;
		panel.add(lblStateProvince, gbc_lblStateProvince);

		txtStateProvince = new JTextField();
		txtStateProvince.setEnabled(false);
		txtStateProvince.setText("");
		txtStateProvince.setColumns(10);
		GridBagConstraints gbc_txtStateProvince = new GridBagConstraints();
		gbc_txtStateProvince.anchor = GridBagConstraints.WEST;
		gbc_txtStateProvince.insets = new Insets(0, 0, 5, 5);
		gbc_txtStateProvince.gridx = 5;
		gbc_txtStateProvince.gridy = 7;
		panel.add(txtStateProvince, gbc_txtStateProvince);
		txtStateProvince.setColumns(10);

		JLabel lblCountryId = new JLabel("Country ID");
		GridBagConstraints gbc_lblCountryId = new GridBagConstraints();
		gbc_lblCountryId.anchor = GridBagConstraints.WEST;
		gbc_lblCountryId.insets = new Insets(0, 0, 0, 5);
		gbc_lblCountryId.gridx = 3;
		gbc_lblCountryId.gridy = 9;
		panel.add(lblCountryId, gbc_lblCountryId);

		txtCountryID = new JTextField();
		txtCountryID.setEnabled(false);
		txtCountryID.setColumns(10);
		GridBagConstraints gbc_txtCountryID = new GridBagConstraints();
		gbc_txtCountryID.anchor = GridBagConstraints.WEST;
		gbc_txtCountryID.insets = new Insets(0, 0, 0, 5);
		gbc_txtCountryID.gridx = 5;
		gbc_txtCountryID.gridy = 9;
		panel.add(txtCountryID, gbc_txtCountryID);
		txtCountryID.setColumns(10);

		myModel.setColumnIdentifiers(columns);
		myModel.addRow(rows);

		tableLocations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (tableLocations.getSelectedRow() != -1) {
					if (tableLocations.getValueAt(tableLocations.getSelectedRow(), 1).toString().equals("null")) {
						txtStreetAddress.setText("");
					} else {
						txtStreetAddress
								.setText(tableLocations.getValueAt(tableLocations.getSelectedRow(), 1).toString());
					}

					if (tableLocations.getValueAt(tableLocations.getSelectedRow(), 2).toString().equals("null")) {
						txtPostalCode.setText("");
					} else {
						txtPostalCode.setText(tableLocations.getValueAt(tableLocations.getSelectedRow(), 2).toString());
					}
					if (tableLocations.getValueAt(tableLocations.getSelectedRow(), 3).toString().equals("null")) {
						txtCity.setText("");
					} else {
						txtCity.setText(tableLocations.getValueAt(tableLocations.getSelectedRow(), 3).toString());
					}

					if (tableLocations.getValueAt(tableLocations.getSelectedRow(), 4).toString().equals("null")) {
						txtStateProvince.setText("");
					} else {

						txtStateProvince
								.setText(tableLocations.getValueAt(tableLocations.getSelectedRow(), 4).toString());
					}

					if (tableLocations.getValueAt(tableLocations.getSelectedRow(), 5).toString().equals("null")) {
						txtCountryID.setText("");
					} else {

						txtCountryID.setText(tableLocations.getValueAt(tableLocations.getSelectedRow(), 5).toString());
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please refresh the page");
				}
			}
		});

		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);
	}

	public LocationsFrame(){
		initialize();
	}
}