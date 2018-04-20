package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

	// declaring needed variables
	private static final long serialVersionUID = 1L;
	private static String title = "Product Manager";
	private int height = 1000;
	private int length = 800;
	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JTable table;
	private String[][] dataList;
	private String[] attributeNames;
	private JLabel label;
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JTextField descriptionTextField;
	private JScrollPane scrollPane;
	private DatabaseHelper databaseHelper;
	private DefaultTableModel model;
	
	public MainFrame() {
		// setting preliminary JFrame settings
		super(title);
		setSize(height, length);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		
		// Setting up the labels
		Font mainFont = new Font("Times New Roman", 20, 20);
		label = new JLabel("Product Manager");
		JLabel idLabel = new JLabel("ID: ");
		JLabel nameLabel = new JLabel("Name: ");
		JLabel priceLabel = new JLabel("Price: ");
		JLabel descriptionLabel = new JLabel("Description: ");
		
		// initializign the text fields
		nameTextField = new JTextField();
		priceTextField = new JTextField();
		descriptionTextField = new JTextField();
		
		// setting up the font
		idLabel.setFont(mainFont);
		nameLabel.setFont(mainFont);
		priceLabel.setFont(mainFont);
		descriptionLabel.setFont(mainFont);
		label.setFont(new Font("Times New Roman", 25, 25));
		
		// creating the buttons
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		saveButton = new JButton("Save");
		
		idTextField = new JTextField(50);
		scrollPane = new JScrollPane();

		// adding all the components to the mainframe
		add(label);
		add(idTextField);
		add(nameTextField);
		add(priceTextField);
		add(descriptionTextField);
		add(idLabel);
		add(nameLabel);
		add(priceLabel);
		add(descriptionLabel);
		add(addButton);
		add(removeButton);
		add(saveButton);
		
		// positioning all components onto the mainframe using absolute positioning
		idTextField.setBounds(210, 100, 100, 30);
		nameTextField.setBounds(210, 200, 100, 30);
		priceTextField.setBounds(210, 300, 100, 30);
		descriptionTextField.setBounds(210, 400, 100, 30);
		
		label.setBounds(140, 15, 200, 50);
		idLabel.setBounds(105, 100, 100, 30);
		nameLabel.setBounds(105, 200, 100, 30);
		priceLabel.setBounds(105, 300, 100, 30);
		descriptionLabel.setBounds(105, 400, 150, 30);
		
		addButton.setBounds(100, 500, 90, 50);
		saveButton.setBounds(200, 500, 90, 50);
		removeButton.setBounds(300, 500, 90, 50);

		setUpDatabase();
		
		// setting up the table
		setUpList();
		getData();
		model = new DefaultTableModel(4, 30);
		table = new JTable(model);
		model.setDataVector(dataList, attributeNames);
		scrollPane.setViewportView(table);
		add(scrollPane);
		scrollPane.setBounds(500, 55, 400, 505);
		
		// implementing action listeners onto button
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] values = extractInfo();
				databaseHelper.addRow(values);
				resetFields();
				getData();
				model.setDataVector(dataList, attributeNames);
			}
		});
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] values = extractInfo();
				databaseHelper.deleteRowWithId(values[0]);
				resetFields();
				getData();
				model.setDataVector(dataList, attributeNames);
			}	
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] values = extractInfo();
				databaseHelper.updateRow(values);
				resetFields();
				getData();
				model.setDataVector(dataList, attributeNames);
			}
		});
		
		setVisible(true);
	}
	
	// sets up the JList
	public void setUpList() {		
		attributeNames = new String[4];
		attributeNames[0] = "ID";
		attributeNames[1] =	"Name";
		attributeNames[2] = "Price";
		attributeNames[3] = "Description";
	}
	
	// retrieves all the data from the database
	public void getData() {
		dataList = new String[40][4];
		dataList = databaseHelper.getAllRows();
	}
	
	// sets up the database for use
	public boolean setUpDatabase() {
		boolean isSetUp = false;
		try {
			databaseHelper = new DatabaseHelper();
			isSetUp = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSetUp;
	}
	
	// extracts all information from text fields
	public String[] extractInfo() {
		String[] info = new String[4];
		info[0] = idTextField.getText();
		info[1] = nameTextField.getText();
		info[2] = priceTextField.getText();
		info[3] = descriptionTextField.getText();
		return info;
	}
	
	// empties all text fields
	public void resetFields() {
		idTextField.setText("");
		nameTextField.setText("");
		priceTextField.setText("");
		descriptionTextField.setText("");
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainFrame mainFrame = new MainFrame();
	}
	

}
