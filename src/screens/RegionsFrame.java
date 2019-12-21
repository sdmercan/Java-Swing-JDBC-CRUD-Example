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
import daoImplements.EmployeesDAOImplements;
import daoImplements.RegionsDAOImplements;
import data.Regions;

import javax.swing.JTable;

public class RegionsFrame extends BaseFrame {

	RegionsDAOImplements regionsDAOImplements = new RegionsDAOImplements();
	private JTable tableRegions;
	private HashMap<String, Integer> hm_RegionsId;
	DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Region ID", "Region Name" };
	Object[] rows = new Object[2];
	private JTextField txtRegionName;
	private ResultSet mySet;
	private String oldName;


	public void enabledTrueTextFields() {
		txtRegionName.setEnabled(true);
	}

	public void emptyTextFields() {
		txtRegionName.setText("");
	}
	public void enabledFalseTextFields() {
		txtRegionName.setEnabled(false);
	}

	public void buttonAddClick() throws SQLException {
		
		if (!txtRegionName.getText().equals("")) {
			Regions regions = new Regions(txtRegionName.getText());

			int status = regionsDAOImplements.add(regions);

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
			else if(status==2) {
				JOptionPane.showMessageDialog(null, "Unknown error.Save failed.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
			pnlSetButton1.setVisible(true);
			pnlSetButton2.setVisible(false);
		}

	}

	@Override
	public void buttonUpdateClick() throws SQLException {
		
		if (hm_RegionsId.containsKey(oldName)) {
			Regions regions = new Regions(txtRegionName.getText());
			
			if (!txtRegionName.getText().equals("")) {
				regions.setRegionName(txtRegionName.getText());
			} 
			

			int status = regionsDAOImplements.update(regions, hm_RegionsId.get(oldName));

			if (status == 1) {
				emptyTextFields();
				JOptionPane.showMessageDialog(null, "Updated.");
				pnlSetButton1.setVisible(false);
				pnlSetButton2.setVisible(true);
				emptyTextFields();
				initialize();
			} else if (status == 0) {
				JOptionPane.showMessageDialog(null, "Fill in the blank fields.", "Warning", 2);
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
			} 

		}

	}
	
	public void showTable() {
		myModel.setColumnIdentifiers(columns);
		EmployeesDAOImplements employeesDAO = new EmployeesDAOImplements();
		try {
			mySet = employeesDAO.getData();
			while (mySet.next()) {

				hm_RegionsId.put(mySet.getString("NAME"), Integer.valueOf(mySet.getString("REGION_ID")));

				rows[0] = mySet.getInt("REGION_ID");
				rows[1] = "" + mySet.getString("REGION_NAME");
				myModel.addRow(rows);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tableRegions.setModel(myModel);
	}


	@Override
	public void buttonDeleteClick() throws SQLException {

		if(txtRegionName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Select from the table to delete");
		}
		else {
			if (hm_RegionsId.containsKey(txtRegionName.getText())) {
				
				int status = regionsDAOImplements.delete(hm_RegionsId.get(txtRegionName.getText()));
				if(status==1) {
					emptyTextFields();
					JOptionPane.showMessageDialog(null, "Deleted.");
					hm_RegionsId.remove(txtRegionName.getText());
					initialize();   
				}else if (status == 0) {
					JOptionPane.showMessageDialog(null, "Unknown error");
				}
				
			}
		}
	}
	
	

	private void initialize(){
		hm_RegionsId=new HashMap<>();
	
		setTitle("REGIONS");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(116, 42, 757, 261);
		getContentPane().add(scrollPane);

		tableRegions = new JTable();
		tableRegions.setModel(myModel);
		tableRegions.setBounds(119, 331, 541, 191);

		scrollPane.setViewportView(tableRegions);

		myModel.setColumnIdentifiers(columns);
		RegionsDAOImplements regionsDAO = new RegionsDAOImplements();
	
		try {
			ResultSet mySet = regionsDAO.getData();
			while (mySet.next()) {

				rows[1] = mySet.getString("REGION_NAME");
				rows[0] = mySet.getString("REGION_ID");
				myModel.addRow(rows);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tableRegions.setModel(myModel);

		JPanel panel = new JPanel();
		panel.setBounds(328, 389, 631, 294);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 48, 80, 0, 0, 0, 100, 0, 43, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 18, 0, 19, 0, 21, 0, 19, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblRegionName = new JLabel("Region Name");
		GridBagConstraints gbc_lblRegionName = new GridBagConstraints();
		gbc_lblRegionName.anchor = GridBagConstraints.WEST;
		gbc_lblRegionName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegionName.gridx = 3;
		gbc_lblRegionName.gridy = 1;
		panel.add(lblRegionName, gbc_lblRegionName);

		txtRegionName = new JTextField();
		txtRegionName.setColumns(10);
		GridBagConstraints gbc_txtRegionName = new GridBagConstraints();
		gbc_txtRegionName.anchor = GridBagConstraints.WEST;
		gbc_txtRegionName.insets = new Insets(0, 0, 5, 5);
		gbc_txtRegionName.gridx = 5;
		gbc_txtRegionName.gridy = 1;
		panel.add(txtRegionName, gbc_txtRegionName);
		txtRegionName.setColumns(10);
		myModel.setColumnIdentifiers(columns);
		myModel.addRow(rows);

		tableRegions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				txtRegionName.setText(tableRegions.getValueAt(tableRegions.getSelectedRow(), 1).toString());

			}
		});
		pnlSetButton1.setVisible(false);
		pnlSetButton2.setVisible(true);
	}
	
	public RegionsFrame(){
		initialize();
	}
}


