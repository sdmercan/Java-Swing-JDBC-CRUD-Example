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
import daoImplements.JobsDAOImplements;
import data.Employees;
import data.Jobs;

public class JobsFrame extends BaseFrame {
	JobsDAOImplements jobsDAOImplements = new JobsDAOImplements();
	private JTable tableJobs;
	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Id", "Job Title", "Min Salary", "Max Salary" };
	Object[] rows = new Object[4];
	private JTextField txtJobId;
	private JTextField txtJobTitle;
	private JTextField txtMinSalary;
	private JTextField txtMaxSalary;
	private JTextField textField;

	@Override
	public void emptyTextFields() {
		txtJobId.setText("");
		txtJobTitle.setText("");
		txtMinSalary.setText("");
		txtMaxSalary.setText("");
	}

	@Override
	public void enabledTrueTextFields() {
		txtJobId.setEnabled(true);
		txtJobTitle.setEnabled(true);
		txtMinSalary.setEnabled(true);
		txtMaxSalary.setEnabled(true);

	}

	@Override
	public void enabledFalseTextFields() {
		txtJobId.setEnabled(false);
		txtJobTitle.setEnabled(false);
		txtMinSalary.setEnabled(false);
		txtMaxSalary.setEnabled(false);
	}

	@Override
	public void buttonAddClick() throws SQLException {
		try {
			if (!txtJobTitle.getText().equals("") && !txtJobId.getText().equals("")) {
				Jobs jobs = new Jobs(txtJobId.getText(), txtJobTitle.getText());
				if (!txtMinSalary.getText().equals("")) {
					jobs.setMinSalary(Integer.parseInt(txtMinSalary.getText()));
				}
				if (!txtMaxSalary.getText().equals("")) {
					jobs.setMaxSalary(Integer.parseInt(txtMaxSalary.getText()));
				}
				int status = jobsDAOImplements.add(jobs);

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
					JOptionPane.showMessageDialog(null, "Job Id can't be the same.Please write different Job Id",
							"Warning", 2);
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

		if (txtJobId.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete.");
		} else {
			int status = jobsDAOImplements.delete(txtJobId.getText());

			if (status == 1) {
				emptyTextFields();
				JOptionPane.showMessageDialog(null, "Deleted.");
				initialize();
			} else if (status == 0) {
				JOptionPane.showMessageDialog(null, "Unknown error.", "Warning", 2);
			}
		}

	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		try {
			if (!txtJobTitle.getText().equals("") && !txtJobId.getText().equals("")) {
				Jobs jobs = new Jobs(txtJobId.getText(), txtJobTitle.getText());
				if (!txtMinSalary.getText().equals("")) {
					jobs.setMinSalary(Integer.parseInt(txtMinSalary.getText()));
				}
				if (!txtMaxSalary.getText().equals("")) {
					jobs.setMaxSalary(Integer.parseInt(txtMaxSalary.getText()));
				}
				int status = jobsDAOImplements.update(jobs, txtJobId.getText());

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
				} else if (status == 4) {
					JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 2) {
					JOptionPane.showMessageDialog(null, "Mismatched field", "Warning", 2);
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				} else if (status == 3) {
					JOptionPane.showMessageDialog(null, "Job Id can't be the same.Please write different Job Id",
							"Warning", 2);
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

	private void initialize() {
		setTitle("JOBS");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 46, 757, 261);
		getContentPane().add(scrollPane);

		tableJobs = new JTable();
		tableJobs.setModel(myModel);
		tableJobs.setBounds(119, 331, 541, 191);
		scrollPane.setViewportView(tableJobs);
		myModel.setColumnIdentifiers(columns);
		JobsDAOImplements jobsDAO = new JobsDAOImplements();
		try {
			ResultSet mySet = jobsDAO.getData();
			while (mySet.next()) {
				rows[0] = mySet.getString("JOB_ID");
				rows[1] = mySet.getString("JOB_TITLE");
				rows[2] = "" + mySet.getString("MIN_SALARY");
				rows[3] = "" + mySet.getString("MAX_SALARY");
				myModel.addRow(rows);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tableJobs.setModel(myModel);

		JPanel panel = new JPanel();
		panel.setBounds(400, 377, 631, 294);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 100, 0, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Job Id");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		txtJobId = new JTextField();
		txtJobId.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 1;
		panel.add(txtJobId, gbc_textField);

		JLabel lblJobTitle = new JLabel("Job Title");
		GridBagConstraints gbc_lblJobTitle = new GridBagConstraints();
		gbc_lblJobTitle.anchor = GridBagConstraints.WEST;
		gbc_lblJobTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobTitle.gridx = 3;
		gbc_lblJobTitle.gridy = 3;
		panel.add(lblJobTitle, gbc_lblJobTitle);

		txtJobTitle = new JTextField();
		txtJobTitle.setColumns(10);
		GridBagConstraints gbc_txtJobTitle = new GridBagConstraints();
		gbc_txtJobTitle.anchor = GridBagConstraints.WEST;
		gbc_txtJobTitle.insets = new Insets(0, 0, 5, 5);
		gbc_txtJobTitle.gridx = 5;
		gbc_txtJobTitle.gridy = 3;
		panel.add(txtJobTitle, gbc_txtJobTitle);
		txtJobTitle.setColumns(10);

		JLabel lblMinSalary = new JLabel("Min Salary");
		GridBagConstraints gbc_lblMinSalary = new GridBagConstraints();
		gbc_lblMinSalary.anchor = GridBagConstraints.WEST;
		gbc_lblMinSalary.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinSalary.gridx = 3;
		gbc_lblMinSalary.gridy = 5;
		panel.add(lblMinSalary, gbc_lblMinSalary);

		txtMinSalary = new JTextField();
		txtMinSalary.setColumns(10);
		GridBagConstraints gbc_txtMinSalary = new GridBagConstraints();
		gbc_txtMinSalary.anchor = GridBagConstraints.WEST;
		gbc_txtMinSalary.insets = new Insets(0, 0, 5, 5);
		gbc_txtMinSalary.gridx = 5;
		gbc_txtMinSalary.gridy = 5;
		panel.add(txtMinSalary, gbc_txtMinSalary);
		txtMinSalary.setColumns(10);

		JLabel lblMaxSalary = new JLabel("Max Salary");
		GridBagConstraints gbc_lblMaxSalary = new GridBagConstraints();
		gbc_lblMaxSalary.anchor = GridBagConstraints.WEST;
		gbc_lblMaxSalary.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxSalary.gridx = 3;
		gbc_lblMaxSalary.gridy = 7;
		panel.add(lblMaxSalary, gbc_lblMaxSalary);

		txtMaxSalary = new JTextField();
		txtMaxSalary.setText("");
		txtMaxSalary.setColumns(10);
		GridBagConstraints gbc_txtMaxSalary = new GridBagConstraints();
		gbc_txtMaxSalary.anchor = GridBagConstraints.WEST;
		gbc_txtMaxSalary.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaxSalary.gridx = 5;
		gbc_txtMaxSalary.gridy = 7;
		panel.add(txtMaxSalary, gbc_txtMaxSalary);
		txtMaxSalary.setColumns(10);
		myModel.setColumnIdentifiers(columns);
		myModel.addRow(rows);

		tableJobs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableJobs.getSelectedRow() != -1) {
					txtJobId.setText(tableJobs.getValueAt(tableJobs.getSelectedRow(), 0).toString());
					txtJobTitle.setText(tableJobs.getValueAt(tableJobs.getSelectedRow(), 1).toString());
					if (tableJobs.getValueAt(tableJobs.getSelectedRow(), 2).toString().equals("null")) {
						txtMinSalary.setText("");
					} else {
						txtMinSalary.setText(tableJobs.getValueAt(tableJobs.getSelectedRow(), 2).toString());
					}
					if (tableJobs.getValueAt(tableJobs.getSelectedRow(), 3).toString().equals("null")) {
						txtMaxSalary.setText("");
					} else {
						txtMaxSalary.setText(tableJobs.getValueAt(tableJobs.getSelectedRow(), 3).toString());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please refresh the page");
				}

			}
		});
		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);

	}

	public JobsFrame() {
		initialize();

	}
}
