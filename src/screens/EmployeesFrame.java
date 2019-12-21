package screens;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import daoImplements.EmployeesDAOImplements;
import data.Employees;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.ResultSet;

public class EmployeesFrame extends BaseFrame {

	private EmployeesDAOImplements employeesDAOImplements = new EmployeesDAOImplements();
	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Id", "First Name", "Last Name", "Email", "Phone Number", "Hire Date", "Job Id", "Salary",
			"Commission PCT", "Manager Id", "Department Id" };
	Object[] rows = new Object[11];
	private HashMap<String, Integer> hm_EmployeesId;
	private static JTextField txtFirstName;
	private static JTextField txtLastName;
	private static JTextField txtEmail;
	private static JTextField txtPhoneNumber;
	private static JTextField txtHireDate;
	private static JTextField txtJob;
	private static JTextField txtSalary;
	private static JTextField txtCommission;
	private static JTextField txtManager;
	private static JTextField txtDepartment;
	private JTable tableEmployees;
	private ResultSet mySet;
	private String oldEmail;

	@Override
	public void emptyTextFields() {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtEmail.setText("");
		txtPhoneNumber.setText("");
		txtHireDate.setText("");
		txtJob.setText("");
		txtSalary.setText("");
		txtCommission.setText("");
		txtManager.setText("");
		txtDepartment.setText("");
	}

	@Override
	public void enabledTrueTextFields() {
		txtFirstName.setEnabled(true);
		txtLastName.setEnabled(true);
		txtEmail.setEnabled(true);
		txtPhoneNumber.setEnabled(true);
		txtHireDate.setEnabled(true);
		txtJob.setEnabled(true);
		txtSalary.setEnabled(true);
		txtCommission.setEnabled(true);
		txtManager.setEnabled(true);
		txtDepartment.setEnabled(true);
	}

	@Override
	public void enabledFalseTextFields() {
		txtFirstName.setEnabled(false);
		txtLastName.setEnabled(false);
		txtEmail.setEnabled(false);
		txtPhoneNumber.setEnabled(false);
		txtHireDate.setEnabled(false);
		txtJob.setEnabled(false);
		txtSalary.setEnabled(false);
		txtCommission.setEnabled(false);
		txtManager.setEnabled(false);
		txtDepartment.setEnabled(false);
	}

	@Override
	public void buttonAddClick() throws SQLException {

		try {
			if (!txtLastName.getText().equals("") && !txtEmail.getText().equals("") && !txtHireDate.getText().equals("")
					&& !txtJob.getText().equals("")) {
				Employees employees = new Employees(txtLastName.getText(), txtEmail.getText(), txtHireDate.getText(),
						txtJob.getText());

				if (!txtFirstName.getText().equals("")) {
					employees.setFirstName(txtFirstName.getText());
				}
				if (!txtPhoneNumber.getText().equals("")) {
					employees.setPhoneNumber(txtPhoneNumber.getText());
				}
				if (!txtSalary.getText().equals("")) {
					employees.setSalary(Integer.valueOf(txtSalary.getText().toString()));
				}
				if (!txtCommission.getText().equals("")) {
					employees.setCommission(Float.valueOf(txtCommission.getText().toString()));
				}
				if (!txtManager.getText().equals("")) {
					employees.setManagerId(Integer.valueOf(txtManager.getText().toString()));
				}
				if (!txtDepartment.getText().equals("")) {
					employees.setDepartmentId(Integer.valueOf(txtDepartment.getText().toString()));
				}

				int status = employeesDAOImplements.add(employees);

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
					JOptionPane.showMessageDialog(null, "Email can't be the same.Please write different Email",
							"Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 3) {
					JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}
				else if(status==4) {
					JOptionPane.showMessageDialog(null, "Mismatched field", "Warning", 2);
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
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Date wrong format.(Sample format: dd/mm/yyyy or ddmmyyyy or dd.mm.yyyy)", "Warning", 2);

		}

	}

	@Override
	public void buttonDeleteClick() throws SQLException {

		if (txtEmail.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete.");
		} else {
			if (hm_EmployeesId.containsKey(txtEmail.getText())) {

				int status = employeesDAOImplements.delete(hm_EmployeesId.get(txtEmail.getText()));

				if (status == 1) {
					emptyTextFields();
					JOptionPane.showMessageDialog(null, "Deleted.");
					hm_EmployeesId.remove(txtEmail.getText());
					initialize();
				} else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Unknown error.", "Warning", 2);
				}
			}
		}

	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		try {
			if (hm_EmployeesId.containsKey(oldEmail)) {
				Employees employees = new Employees(txtLastName.getText(), txtEmail.getText(), txtHireDate.getText(),
						txtJob.getText());

				if (!txtFirstName.getText().equals("")) {
					employees.setFirstName(txtFirstName.getText());
				}
				if (!txtPhoneNumber.getText().equals("")) {
					employees.setPhoneNumber(txtPhoneNumber.getText());
				}
				if (!txtSalary.getText().equals("")) {
					employees.setSalary(Integer.parseInt(txtSalary.getText()));
				}
				if (!txtCommission.getText().equals("")) {
					employees.setCommission(Float.parseFloat(txtCommission.getText()));
				}
				if (!txtManager.getText().equals("")) {
					employees.setManagerId(Integer.parseInt(txtManager.getText()));
				}
				if (!txtDepartment.getText().equals("")) {
					employees.setDepartmentId(Integer.parseInt(txtDepartment.getText()));
				}

				int status = employeesDAOImplements.update(employees, hm_EmployeesId.get(oldEmail));

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
					JOptionPane.showMessageDialog(null, "Mismatched field.Enter the defined job, department or manager.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 3) {
					JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}
			}
		
		}catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You entered the wrong character.Please enter a number", "Warning", 2);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Date wrong format.(Sample format: dd/mm/yyyy or ddmmyyyy or dd.mm.yyyy)", "Warning", 2);

		}
	}

	public void showTable() {
		myModel.setColumnIdentifiers(columns);
		EmployeesDAOImplements employeesDAO = new EmployeesDAOImplements();
		try {
			mySet = employeesDAO.getData();
			while (mySet.next()) {

				hm_EmployeesId.put(mySet.getString("EMAIL"), Integer.valueOf(mySet.getString("EMPLOYEE_ID")));

				rows[0] = mySet.getInt("EMPLOYEE_ID");
				rows[1] = "" + mySet.getString("FIRST_NAME");
				rows[2] = mySet.getString("LAST_NAME");
				rows[3] = mySet.getString("EMAIL");
				rows[4] = "" + mySet.getString("PHONE_NUMBER");
				rows[5] = mySet.getString("HIRE_DATE");
				rows[6] = "" + mySet.getString("JOB_ID");
				rows[7] = "" + mySet.getString("SALARY");
				if (mySet.getString("COMMISSION_PCT") == null) {
					rows[8] = "" + mySet.getString("COMMISSION_PCT");
				} else {
					rows[8] = "0" + mySet.getString("COMMISSION_PCT");
				}
				rows[9] = "" + mySet.getString("MANAGER_ID");
				rows[10] = "" + mySet.getString("DEPARTMENT_ID");
				myModel.addRow(rows);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tableEmployees.setModel(myModel);
	}

	private void initialize() {
		hm_EmployeesId = new HashMap<>();
		setTitle("EMPLOYEES");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 38, 757, 261);
		getContentPane().add(scrollPane);

		tableEmployees = new JTable();
		tableEmployees.setBounds(119, 331, 541, 191);
		scrollPane.setViewportView(tableEmployees);
		showTable();

		JPanel panel = new JPanel();
		panel.setBounds(166, 324, 631, 294);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 76, 68, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("First Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		txtFirstName = new JTextField();
		txtFirstName.setEnabled(false);
		GridBagConstraints gbc_txtFirstName = new GridBagConstraints();
		gbc_txtFirstName.anchor = GridBagConstraints.WEST;
		gbc_txtFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFirstName.gridx = 5;
		gbc_txtFirstName.gridy = 1;
		panel.add(txtFirstName, gbc_txtFirstName);
		txtFirstName.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Job Id");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 10;
		gbc_lblNewLabel_5.gridy = 1;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		txtJob = new JTextField();
		txtJob.setEnabled(false);
		GridBagConstraints gbc_txtJob = new GridBagConstraints();
		gbc_txtJob.anchor = GridBagConstraints.WEST;
		gbc_txtJob.insets = new Insets(0, 0, 5, 0);
		gbc_txtJob.gridx = 12;
		gbc_txtJob.gridy = 1;
		panel.add(txtJob, gbc_txtJob);
		txtJob.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Last Name");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtLastName = new JTextField();
		txtLastName.setEnabled(false);
		GridBagConstraints gbc_txtLastName = new GridBagConstraints();
		gbc_txtLastName.anchor = GridBagConstraints.WEST;
		gbc_txtLastName.insets = new Insets(0, 0, 5, 5);
		gbc_txtLastName.gridx = 5;
		gbc_txtLastName.gridy = 3;
		panel.add(txtLastName, gbc_txtLastName);
		txtLastName.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Salary");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 10;
		gbc_lblNewLabel_6.gridy = 3;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);

		txtSalary = new JTextField();
		txtSalary.setEnabled(false);
		txtSalary.setText("");
		GridBagConstraints gbc_txtSalary = new GridBagConstraints();
		gbc_txtSalary.insets = new Insets(0, 0, 5, 0);
		gbc_txtSalary.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSalary.gridx = 12;
		gbc_txtSalary.gridy = 3;
		panel.add(txtSalary, gbc_txtSalary);
		txtSalary.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Email");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 5;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		txtEmail.setText("");
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.WEST;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridx = 5;
		gbc_txtEmail.gridy = 5;
		panel.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Commision");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 10;
		gbc_lblNewLabel_7.gridy = 5;
		panel.add(lblNewLabel_7, gbc_lblNewLabel_7);

		txtCommission = new JTextField();
		txtCommission.setEnabled(false);
		GridBagConstraints gbc_txtCommission = new GridBagConstraints();
		gbc_txtCommission.insets = new Insets(0, 0, 5, 0);
		gbc_txtCommission.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCommission.gridx = 12;
		gbc_txtCommission.gridy = 5;
		panel.add(txtCommission, gbc_txtCommission);
		txtCommission.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Phone Number");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 7;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setEnabled(false);
		txtPhoneNumber.setText("");
		GridBagConstraints gbc_txtPhoneNumber = new GridBagConstraints();
		gbc_txtPhoneNumber.anchor = GridBagConstraints.WEST;
		gbc_txtPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_txtPhoneNumber.gridx = 5;
		gbc_txtPhoneNumber.gridy = 7;
		panel.add(txtPhoneNumber, gbc_txtPhoneNumber);
		txtPhoneNumber.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Manager Id");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 10;
		gbc_lblNewLabel_8.gridy = 7;
		panel.add(lblNewLabel_8, gbc_lblNewLabel_8);

		txtManager = new JTextField();
		txtManager.setEnabled(false);
		txtManager.setText("");
		GridBagConstraints gbc_txtManager = new GridBagConstraints();
		gbc_txtManager.insets = new Insets(0, 0, 5, 0);
		gbc_txtManager.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtManager.gridx = 12;
		gbc_txtManager.gridy = 7;
		panel.add(txtManager, gbc_txtManager);
		txtManager.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Hire Date");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 9;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		txtHireDate = new JTextField();
		txtHireDate.setEnabled(false);
		GridBagConstraints gbc_txtHireDate = new GridBagConstraints();
		gbc_txtHireDate.insets = new Insets(0, 0, 0, 5);
		gbc_txtHireDate.anchor = GridBagConstraints.WEST;
		gbc_txtHireDate.gridx = 5;
		gbc_txtHireDate.gridy = 9;
		panel.add(txtHireDate, gbc_txtHireDate);
		txtHireDate.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Department Id");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_9.gridx = 10;
		gbc_lblNewLabel_9.gridy = 9;
		panel.add(lblNewLabel_9, gbc_lblNewLabel_9);

		txtDepartment = new JTextField();
		txtDepartment.setEnabled(false);
		txtDepartment.setText("");
		GridBagConstraints gbc_txtDepartment = new GridBagConstraints();
		gbc_txtDepartment.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDepartment.gridx = 12;
		gbc_txtDepartment.gridy = 9;
		panel.add(txtDepartment, gbc_txtDepartment);
		txtDepartment.setColumns(10);

		tableEmployees.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableEmployees.getSelectedRow() != -1) {
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 1).toString().equals("null")) {
						txtFirstName.setText("");
					} else {
						txtFirstName.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 1).toString());
					}
					txtLastName.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 2).toString());
					txtEmail.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 3).toString());
					oldEmail = txtEmail.getText();

					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 4).toString().equals("null")) {
						txtPhoneNumber.setText("");
					} else {
						txtPhoneNumber
								.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 4).toString());
					}
					txtHireDate.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 5).toString());
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 6).toString().equals("null")) {
						txtJob.setText("");
					} else {
						txtJob.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 6).toString());
					}
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 7).toString().equals("null")) {
						txtSalary.setText("");
					} else {
						txtSalary.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 7).toString());
					}
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 8).toString().equals("null")) {
						txtCommission.setText("");
					} else {
						txtCommission.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 8).toString());
					}
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 9).toString().equals("null")) {
						txtManager.setText("");
					} else {
						txtManager.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 9).toString());
					}
					if (tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 10).toString().equals("null")) {
						txtDepartment.setText("");
					} else {
						txtDepartment
								.setText(tableEmployees.getValueAt(tableEmployees.getSelectedRow(), 10).toString());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please refresh the page");
				}

			}
		});

		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);

	}

	public EmployeesFrame() {
		initialize();
	}
}
