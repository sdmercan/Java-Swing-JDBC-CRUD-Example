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

import daoImplements.DepartmentsDAOImplements;
import data.Departments;
import java.awt.Color;

public class DepartmentsFrame extends BaseFrame {
	DepartmentsDAOImplements departmentsDAOImplements = new DepartmentsDAOImplements();

	private JTable tableDepartments;
	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Department ID", "Department Name", "Manager ID", "Location ID" };
	Object[] rows = new Object[4];
	private HashMap<String, Integer> hm_DepartmentsId;
	private JTextField txtDepartmentName;
	private JTextField txtManagerID;
	private JTextField txtLocationID;
	private ResultSet mySet;

	public void emptyTextFields() {
		txtDepartmentName.setText("");
		txtLocationID.setText("");
		txtManagerID.setText("");
	}

	public void enabledTrueTextFields() {

		txtDepartmentName.setEnabled(true);
		txtLocationID.setEnabled(true);
		txtManagerID.setEnabled(true);
	}

	public void enabledFalseTextFields() {
		txtDepartmentName.setEnabled(false);
		txtLocationID.setEnabled(false);
		txtManagerID.setEnabled(false);
	}

	@Override
	public void buttonAddClick() throws SQLException {

		if (!txtDepartmentName.getText().equals("")) {

			Departments departments = new Departments(txtDepartmentName.getText());
			if (!txtLocationID.getText().equals("")) {
				departments.setLocationID(Integer.valueOf(txtLocationID.getText()));
			}

			if (!txtManagerID.getText().equals("")) {
				departments.setManagerID(Integer.valueOf(txtManagerID.getText()));
			}
			int status = departmentsDAOImplements.add(departments);
			System.out.println("status" + status);
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
			}

			else if (status == 2) {
				JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			}
		}
	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		try {
			Departments departments = new Departments(txtDepartmentName.getText());
			if (!txtDepartmentName.getText().equals("")) {
				departments.setDepartmentName(txtDepartmentName.getText());
			}

			if (!txtLocationID.getText().equals("")) {
				departments.setLocationID(Integer.valueOf(txtLocationID.getText()));
			}

			if (!txtManagerID.getText().equals("")) {
				departments.setManagerID(Integer.valueOf(txtManagerID.getText()));
			}

			int status = departmentsDAOImplements.update(departments, hm_DepartmentsId.get(txtDepartmentName));

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
				JOptionPane.showMessageDialog(null, "Foreign key problem.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			} else if (status == 3) {
				JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			}

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You entered the wrong character.Please enter a number", "Warning", 2);
		}
	}

	@Override
	public void buttonDeleteClick() throws SQLException {

		if (txtDepartmentName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete");
		} else {
			if (hm_DepartmentsId.containsKey(txtDepartmentName.getText())) {

				int status = departmentsDAOImplements.delete(hm_DepartmentsId.get(txtDepartmentName.getText()));
				if (status == 1) {
					emptyTextFields();
					JOptionPane.showMessageDialog(null, "Deleted.");
					hm_DepartmentsId.remove(txtDepartmentName.getText());
					initialize();
				} else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Unknown error", "Warning", 2);
				}

			}
		}

	}

	public void showTable() {

		myModel.setColumnIdentifiers(columns);
		DepartmentsDAOImplements departmentsDAO = new DepartmentsDAOImplements();

		try {
			mySet = departmentsDAO.getData();
			while (mySet.next()) {
				hm_DepartmentsId.put(mySet.getString("DEPARTMENT_NAME"),
						Integer.valueOf(mySet.getString("DEPARTMENT_ID")));
				rows[0] = mySet.getString("DEPARTMENT_ID");
				rows[1] = mySet.getString("DEPARTMENT_NAME");
				rows[2] = "" + mySet.getString("MANAGER_ID");
				rows[3] = "" + mySet.getString("LOCATION_ID");
				myModel.addRow(rows);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableDepartments.setModel(myModel);
	}

	private void initialize() {
		hm_DepartmentsId = new HashMap<>();

		setTitle("DEPARTMENTS");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 39, 757, 327);
		getContentPane().add(scrollPane);

		tableDepartments = new JTable();

		tableDepartments.setBounds(119, 331, 541, 191);
		scrollPane.setViewportView(tableDepartments);
		showTable();

		JPanel panel = new JPanel();
		panel.setBounds(382, 392, 631, 294);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 100, 0, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblDepartmentName = new JLabel("Department Name");
		GridBagConstraints gbc_lblDepartmentName = new GridBagConstraints();
		gbc_lblDepartmentName.anchor = GridBagConstraints.WEST;
		gbc_lblDepartmentName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartmentName.gridx = 3;
		gbc_lblDepartmentName.gridy = 1;
		panel.add(lblDepartmentName, gbc_lblDepartmentName);

		txtDepartmentName = new JTextField();
		txtDepartmentName.setColumns(10);
		GridBagConstraints gbc_txtDepartmentName = new GridBagConstraints();
		gbc_txtDepartmentName.anchor = GridBagConstraints.WEST;
		gbc_txtDepartmentName.insets = new Insets(0, 0, 5, 5);
		gbc_txtDepartmentName.gridx = 5;
		gbc_txtDepartmentName.gridy = 1;
		panel.add(txtDepartmentName, gbc_txtDepartmentName);
		txtDepartmentName.setColumns(10);

		JLabel lblManagerId = new JLabel("Manager ID");
		GridBagConstraints gbc_lblManagerId = new GridBagConstraints();
		gbc_lblManagerId.anchor = GridBagConstraints.WEST;
		gbc_lblManagerId.insets = new Insets(0, 0, 5, 5);
		gbc_lblManagerId.gridx = 3;
		gbc_lblManagerId.gridy = 3;
		panel.add(lblManagerId, gbc_lblManagerId);

		txtManagerID = new JTextField();
		txtManagerID.setColumns(10);
		GridBagConstraints gbc_txtManagerID = new GridBagConstraints();
		gbc_txtManagerID.anchor = GridBagConstraints.WEST;
		gbc_txtManagerID.insets = new Insets(0, 0, 5, 5);
		gbc_txtManagerID.gridx = 5;
		gbc_txtManagerID.gridy = 3;
		panel.add(txtManagerID, gbc_txtManagerID);
		txtManagerID.setColumns(10);

		JLabel lblLocationId = new JLabel("Location ID");
		GridBagConstraints gbc_lblLocationId = new GridBagConstraints();
		gbc_lblLocationId.anchor = GridBagConstraints.WEST;
		gbc_lblLocationId.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocationId.gridx = 3;
		gbc_lblLocationId.gridy = 5;
		panel.add(lblLocationId, gbc_lblLocationId);

		txtLocationID = new JTextField();
		txtLocationID.setText("");
		txtLocationID.setColumns(10);
		GridBagConstraints gbc_txtLocationID = new GridBagConstraints();
		gbc_txtLocationID.anchor = GridBagConstraints.WEST;
		gbc_txtLocationID.insets = new Insets(0, 0, 5, 5);
		gbc_txtLocationID.gridx = 5;
		gbc_txtLocationID.gridy = 5;
		panel.add(txtLocationID, gbc_txtLocationID);
		txtLocationID.setColumns(10);
		myModel.setColumnIdentifiers(columns);
		myModel.addRow(rows);

		tableDepartments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (tableDepartments.getSelectedRow() != -1) {
					if (tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 1).toString().equals("null")) {
						txtDepartmentName.setText("");
					} else {
						txtDepartmentName
								.setText(tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 1).toString());
					}

					if (tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 2).toString().equals("null")) {
						txtDepartmentName.setText("");
					} else {
						txtManagerID
								.setText(tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 2).toString());
					}
					if (tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 3).toString().equals("null")) {
						txtDepartmentName.setText("");
					} else {
						txtLocationID
								.setText(tableDepartments.getValueAt(tableDepartments.getSelectedRow(), 3).toString());
					}

				} else {
					JOptionPane.showMessageDialog(null, "Please refresh the page");
				}

			}
		});

		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);
	}

	public DepartmentsFrame() {

		initialize();

	}
}
