package screens;

import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import daoImplements.CountriesDAOImplements;
import data.Countries;

public class CountriesFrame extends BaseFrame {
	private CountriesDAOImplements countriesDAOImplements = new CountriesDAOImplements();
	private JTable tableCountries;
	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Country ID", "Country Name", "Region ID" };
	Object[] rows = new Object[3];
	private JTextField txtCountryName;
	private JTextField txtRegionID;
	private JTextField txtCountryId;

	@Override
	public void emptyTextFields() {
		txtCountryId.setText("");
		txtCountryName.setText("");
		txtRegionID.setText("");
	}

	@Override
	public void enabledTrueTextFields() {
		txtCountryName.setEnabled(true);
		txtRegionID.setEnabled(true);
		txtCountryId.setEnabled(true);
	}

	@Override
	public void enabledFalseTextFields() {
		txtCountryName.setEnabled(false);
		txtRegionID.setEnabled(false);
		txtCountryId.setEnabled(false);
	}

	@Override
	public void buttonAddClick() throws SQLException {

		try {
			if (!txtCountryId.getText().equals("")) {
				if (txtCountryId.getText().length() == 2) {
					Countries countries = new Countries(txtCountryId.getText());
					if (!txtCountryName.getText().equals("")) {
						countries.setCountryName(txtCountryName.getText());
					}
					if (!txtRegionID.getText().equals("")) {
						countries.setRegionID(Integer.parseInt(txtRegionID.getText()));
					}
					int status = countriesDAOImplements.add(countries);
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
					} else if (status == 2) {
						JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
						pnlSetButton1.setVisible(true);
						pnlSetButton2.setVisible(false);
					} else if (status == 3) {
						JOptionPane.showMessageDialog(null, "Mismatched field", "Warning", 2);
						pnlSetButton1.setVisible(true);
						pnlSetButton2.setVisible(false);
					} else if (status == 4) {
						JOptionPane.showMessageDialog(null,
								"Counrty Id can't be the same.Please write different Counrty Id", "Warning", 2);
						pnlSetButton1.setVisible(true);
						pnlSetButton2.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Enter Counrty Id as two characters", "Warning", 2);
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

		if (txtCountryId.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete");
		} else {
			int status = countriesDAOImplements.delete(txtCountryId.getText());
			if (status == 1) {
				emptyTextFields();
				JOptionPane.showMessageDialog(null, "Deleted.");
				initialize();
			} else if (status == 0) {
				JOptionPane.showMessageDialog(null, "Unknown error");
			}
		}
	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		try {
			if (!txtCountryId.getText().equals("")) {
				if (txtCountryId.getText().length() == 2) {
					Countries countries = new Countries(txtCountryId.getText());
					if (!txtCountryName.getText().equals("")) {
						countries.setCountryName(txtCountryName.getText());
					}
					if (!txtRegionID.getText().equals("")) {
						countries.setRegionID(Integer.parseInt(txtRegionID.getText()));
					}
					int status = countriesDAOImplements.update(countries,txtCountryId.getText());
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
						JOptionPane.showMessageDialog(null,
								"Counrty Id can't be the same.Please write different Counrty Id", "Warning", 2);
						pnlSetButton1.setVisible(true);
						pnlSetButton2.setVisible(false);
					} else if (status == 4) {
						JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
						pnlSetButton1.setVisible(true);
						pnlSetButton2.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Enter Counrty Id as two characters", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}

			} 
			else {
				JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You entered the wrong character.Please enter a number", "Warning", 2);
		}

	}

	private void initialize() {
		setTitle("COUNTRIES");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(112, 37, 757, 261);
		getContentPane().add(scrollPane);

		tableCountries = new JTable();
		tableCountries.setModel(myModel);
		tableCountries.setBounds(119, 331, 541, 191);
		scrollPane.setViewportView(tableCountries);

		myModel.setColumnIdentifiers(columns);
		CountriesDAOImplements countriesDAO = new CountriesDAOImplements();

		try {
			ResultSet mySet = countriesDAO.getData();
			while (mySet.next()) {
				rows[0] = "" + mySet.getString("COUNTRY_ID");
				rows[1] = "" + mySet.getString("COUNTRY_NAME");
				rows[2] = "" + mySet.getString("REGION_ID");
				myModel.addRow(rows);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tableCountries.setModel(myModel);

		JPanel panel = new JPanel();
		panel.setBounds(364, 391, 520, 269);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 100, 0, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblCountryId = new JLabel("Country ID");
		GridBagConstraints gbc_lblCountryId = new GridBagConstraints();
		gbc_lblCountryId.anchor = GridBagConstraints.WEST;
		gbc_lblCountryId.insets = new Insets(0, 0, 5, 5);
		gbc_lblCountryId.gridx = 3;
		gbc_lblCountryId.gridy = 1;
		panel.add(lblCountryId, gbc_lblCountryId);

		txtCountryId = new JTextField();
		txtCountryId.setEnabled(false);
		GridBagConstraints gbc_txtCountryId = new GridBagConstraints();
		gbc_txtCountryId.anchor = GridBagConstraints.WEST;
		gbc_txtCountryId.insets = new Insets(0, 0, 5, 5);
		gbc_txtCountryId.gridx = 5;
		gbc_txtCountryId.gridy = 1;
		panel.add(txtCountryId, gbc_txtCountryId);
		txtCountryId.setColumns(10);

		JLabel lblCountriesName = new JLabel("Country Name");
		GridBagConstraints gbc_lblCountriesName = new GridBagConstraints();
		gbc_lblCountriesName.anchor = GridBagConstraints.WEST;
		gbc_lblCountriesName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCountriesName.gridx = 3;
		gbc_lblCountriesName.gridy = 3;
		panel.add(lblCountriesName, gbc_lblCountriesName);

		txtCountryName = new JTextField();
		txtCountryName.setEnabled(false);
		txtCountryName.setColumns(10);
		GridBagConstraints gbc_txtCountryName = new GridBagConstraints();
		gbc_txtCountryName.anchor = GridBagConstraints.WEST;
		gbc_txtCountryName.insets = new Insets(0, 0, 5, 5);
		gbc_txtCountryName.gridx = 5;
		gbc_txtCountryName.gridy = 3;
		panel.add(txtCountryName, gbc_txtCountryName);
		txtCountryName.setColumns(10);

		JLabel lblRegionsId = new JLabel("Region ID");
		GridBagConstraints gbc_lblRegionsId = new GridBagConstraints();
		gbc_lblRegionsId.anchor = GridBagConstraints.WEST;
		gbc_lblRegionsId.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegionsId.gridx = 3;
		gbc_lblRegionsId.gridy = 5;
		panel.add(lblRegionsId, gbc_lblRegionsId);

		txtRegionID = new JTextField();
		txtRegionID.setEnabled(false);
		txtRegionID.setColumns(10);
		GridBagConstraints gbc_txtRegionID = new GridBagConstraints();
		gbc_txtRegionID.anchor = GridBagConstraints.WEST;
		gbc_txtRegionID.insets = new Insets(0, 0, 5, 5);
		gbc_txtRegionID.gridx = 5;
		gbc_txtRegionID.gridy = 5;
		panel.add(txtRegionID, gbc_txtRegionID);
		txtRegionID.setColumns(10);
		myModel.setColumnIdentifiers(columns);
		myModel.addRow(rows);

		tableCountries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableCountries.getSelectedRow() != -1) {
					txtCountryId.setText(tableCountries.getValueAt(tableCountries.getSelectedRow(), 0).toString());
					if (tableCountries.getValueAt(tableCountries.getSelectedRow(), 1).toString().equals("null")) {
						txtCountryName.setText("");
					} else {
						txtCountryName
								.setText(tableCountries.getValueAt(tableCountries.getSelectedRow(), 1).toString());
					}
					if (tableCountries.getValueAt(tableCountries.getSelectedRow(), 2).toString().equals("null")) {
						txtRegionID.setText("");
					} else {
						txtRegionID.setText(tableCountries.getValueAt(tableCountries.getSelectedRow(), 2).toString());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please refresh the page");
				}

			}
		});

		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);
	}

	public CountriesFrame() {
		initialize();
	}
}
