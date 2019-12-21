package screens;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import daoImplements.EmployeesDAOImplements;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BaseFrame extends JFrame {

	int EkranX, EkranY;
	Toolkit kit = Toolkit.getDefaultToolkit();
	Container c;
	JPanel pnlSetButton1, pnlSetButton2;
	EmployeesFrame employeesFrame;
	DepartmentsFrame departmentsFrame;
	JobsFrame jobsFrame;
	LocationsFrame locationsFrame;
	RegionsFrame regionsFrame;
	CountriesFrame countriesFrame;
	public static int choiseAddORUpdate=0;

	
	public void emptyTextFields() {

	}

	public void enabledTrueTextFields() {
		
	}
	
	public void enabledFalseTextFields() {
		
	}
	
	public void buttonAddClick() throws SQLException {

	}

	public void buttonDeleteClick() throws SQLException {

	}

	public void buttonUpdateClick() throws SQLException{
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseFrame window = new BaseFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BaseFrame() {
		setTitle("HOME");
		this.setResizable(false);
		setSize(994, 700);
		setBounds(100, 100, 800, 700);
		c = this.getContentPane();
		this.getContentPane().setLayout(null);
		EkranX = (int) kit.getScreenSize().width;
		EkranY = (int) kit.getScreenSize().height;
		this.setSize(900, 700);
		setLocation((EkranX - 800) / 2, (EkranY - 700) / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 11, 101, 22);
		getContentPane().add(menuBar);

		pnlSetButton1 = new JPanel();
		pnlSetButton1.setBounds(184, 565, 679, 42);
		getContentPane().add(pnlSetButton1);
		pnlSetButton1.setVisible(false);

		pnlSetButton2 = new JPanel();
		pnlSetButton2.setBounds(39, 618, 845, 42);
		getContentPane().add(pnlSetButton2);
		pnlSetButton2.setVisible(false);

		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(77, 0, 196, 43);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiseAddORUpdate=1;
				enabledTrueTextFields();
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
				emptyTextFields();

			}
		});
		pnlSetButton2.setLayout(null);
		pnlSetButton2.add(btnAdd);

		JButton btnReplace = new JButton("UPDATE");
		btnReplace.setBounds(273, 0, 196, 43);
		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choiseAddORUpdate=2;
				enabledTrueTextFields();
				pnlSetButton1.setVisible(true);
				pnlSetButton2.setVisible(false);
				JOptionPane.showMessageDialog(null, "Select from the table to update.");
			}
		});
		pnlSetButton2.add(btnReplace);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choise = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Message",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (choise == JOptionPane.YES_OPTION) {
					try {
						buttonDeleteClick();
						enabledFalseTextFields();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				emptyTextFields();
			}
		});
		btnDelete.setBounds(468, 0, 196, 43);
		pnlSetButton2.add(btnDelete);

		GridBagLayout gbl_pnlSetButton1 = new GridBagLayout();
		gbl_pnlSetButton1.columnWidths = new int[] { 238, 77, 77, 0 };
		gbl_pnlSetButton1.rowHeights = new int[] { 26, 0 };
		gbl_pnlSetButton1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlSetButton1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlSetButton1.setLayout(gbl_pnlSetButton1);

		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(choiseAddORUpdate==1) {
						buttonAddClick();
					}
					else if(choiseAddORUpdate==2) {
						buttonUpdateClick();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 0;
		pnlSetButton1.add(btnSave, gbc_btnSave);

		JButton btnIptal = new JButton("CANCEL");
		btnIptal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choise = JOptionPane.showOptionDialog(null, "Are you sure you want to exit without saving changes?",
						"Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (choise == JOptionPane.YES_OPTION) {
					pnlSetButton1.setVisible(false);
					pnlSetButton2.setVisible(true);
					enabledFalseTextFields();
				} else {
					pnlSetButton1.setVisible(true);
					pnlSetButton2.setVisible(false);
				}
				emptyTextFields();

			}
		});

		GridBagConstraints gbc_btnIptal = new GridBagConstraints();
		gbc_btnIptal.fill = GridBagConstraints.BOTH;
		gbc_btnIptal.gridx = 2;
		gbc_btnIptal.gridy = 0;
		pnlSetButton1.add(btnIptal, gbc_btnIptal);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(664, 0, 196, 43);
		pnlSetButton2.add(btnExit);

		JMenu mnNewMenu = new JMenu("MENU");
		menuBar.add(mnNewMenu);

		JMenuItem menuEmployees = new JMenuItem("EMPLOYEES");
		menuEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (employeesFrame == null)
					employeesFrame = new EmployeesFrame();
				setVisible(false);
			}
		});

		JMenuItem menuHome = new JMenuItem("HOME");
		mnNewMenu.add(menuHome);
		mnNewMenu.add(menuEmployees);

		JMenuItem menuDepartments = new JMenuItem("DEPARTMENTS");
		menuDepartments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (departmentsFrame == null)
					departmentsFrame = new DepartmentsFrame();
				setVisible(false);
			}
		});
		mnNewMenu.add(menuDepartments);

		JMenuItem menuJobs = new JMenuItem("JOBS");
		menuJobs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jobsFrame == null)
					jobsFrame = new JobsFrame();
				setVisible(false);
			}
		});
		mnNewMenu.add(menuJobs);

		JMenuItem menuLocations = new JMenuItem("LOCATIONS");
		menuLocations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (locationsFrame == null)
					locationsFrame = new LocationsFrame();
				setVisible(false);
			}
		});
		mnNewMenu.add(menuLocations);

		JMenuItem menuCountries = new JMenuItem("COUNTRIES");
		menuCountries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countriesFrame == null)
					countriesFrame = new CountriesFrame();
	
				setVisible(false);
			}
		});
		mnNewMenu.add(menuCountries);

		JMenuItem menuRegions = new JMenuItem("REGIONS");
		menuRegions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (regionsFrame == null)
					regionsFrame = new RegionsFrame();
				setVisible(false);
			}
		});
		mnNewMenu.add(menuRegions);

		JMenuItem menuExit = new JMenuItem("EXIT");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(menuExit);

	}
}
