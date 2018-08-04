package Yelp;


import javax.swing.*;
import java.awt.Component;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooser.*;
import java.awt.Font;

@SuppressWarnings("serial")
public class Main extends JFrame {


	private JTabbedPane tabbed_Pane;
	private JPanel panel_Business;
	private JPanel panel_User;
	private JPanel panel_Yelp_Main_Category;
	private JScrollPane scrollPane_Sub_Category;
	private JPanel panel_Sub_Category;
	private JScrollPane scrollPane_Attributes;
	private JPanel panel_Attributes;
	private JPanel panel_Business_Review;
	private JScrollPane scrollPane_Yelp_Main_Category;
	private JLabel label_Business_From;
	private JDateChooser dateChooser_Business_From_Date;
	private JLabel label_Business_To;
	private JDateChooser dateChooser_Business_To_Date;
	private JLabel label_Business_Stars;
	private JComboBox<String> comboBox_Business_Star;
	private JLabel label_Business_Star_Value;
	private JTextField textField_Business_Star_Value;
	private JLabel label_Business_Votes ;
	private JComboBox<String> comboBox_Business_Votes;
	private JLabel label_Business_Votes_Value;
	private JTextField textField_Business_Votes_Value;
	private JPanel panel_Business_Query;
	private JScrollPane scrollPane_Business_Query;
	private JTextArea textArea_Business_Query;
	private JButton button_Business_Execute;
	private JButton button_Review_Filter;
	private JScrollPane scrollPane_Business_Results;
	private JLabel label_Business_And_Or;
	private JComboBox<String> comboBox_Business_And_Or;
	private JPanel panel_User_Search;
	private JLabel label_Member_Since;
	private JLabel label_Review_Count;
	private JComboBox<String> comboBox_Review_Count;
	private JComboBox<String> comboBox_Yelping_Since_Month;
	private JLabel label_Review_Count_Value;
	private JTextField textField_Review_Count_Value;
	private JLabel label_Friends;
	private JComboBox<String> comboBox_Friends;
	private JLabel label_Friends_Value;
	private JTextField textField_Friends_Value;
	private JLabel label_Avegare_Stars;
	private JComboBox<String> comboBox_Average_Stars;
	private JLabel label_Average_Stars_Value;
	private JTextField textField_Average_Stars_Value;
	private JLabel label_Votes;
	private JComboBox<String> comboBox_Votes;
	private JComboBox<String> comboBox_Compare_Yelping;
	private JLabel label_Votes_Value;
	private JTextField textField_Votes_Value;
	private JPanel panel_User_Query;
	private JLabel label_User_And_Or;
	private JComboBox<String> comboBox_User_And_Or;
	private JTextArea textArea_User_Query;
	private JButton button_User_Query;
	private JScrollPane scrollPane_User_Results;
	private JPanel panel_User_Results;
	private JScrollPane scroll_User_Reviews;
	private JPanel panel_User_Reviews;
	private PreparedStatement preparedStatement_Query;
	private JTable tabel_Business_Results;
	private JTable tabel_User_Results;
	private JTable tabel_User_Reviews;
	@SuppressWarnings("unused")
	private DefaultTableModel tableModel_Business_Results;
	private DefaultTableModel tableModel_User_Results;
	private DefaultTableModel tableModel_User_Reviews;
	private JCheckBoxList checkBox_Yelp_Main_Category;
	private DefaultListModel<JCheckBox> listModel_Yelp_Main_Category = new DefaultListModel<JCheckBox>();
	private JCheckBoxList checkBox_Yelp_Sub_Category;
	private DefaultListModel<JCheckBox> listModel_Yelp_Sub_Category = new DefaultListModel<JCheckBox>();
	private String string_And_Or = "INTERSECT";
	private HashSet<String> a_Checked_Yelo_Main_Category = new HashSet<String>();
	private HashSet<String> a_Checked_Yelp_Sub_Category = new HashSet<String>();
	private DefaultListModel<JCheckBox> listModel_Attributes = new DefaultListModel<JCheckBox>();
	private JComboBox<String> comboBox_Yelping_Since_Year;
	private JCheckBoxList checkBox_Attributes;
	private HashSet<String> hash_Checked_Attributes = new HashSet<String>();
	private JScrollPane scrollPane_Business_Reviews;
	private JTable tabel_Business_Reviews;
	private DefaultTableModel tabelModel_Business_Reviews;
	boolean check_Main_Category = false;
	boolean check_Sub_Category = false;
	String User_Query = null;



	public Main() {
		setTitle("Data Analysis Application");
		  initializeInterface();
		  select_And_Or();
		  populate_Yelp_Main_Category();
		  textArea_Business_Query.setText("");
	  }


	private void initializeInterface() {
		getContentPane().setLayout(null);

		tabbed_Pane = new JTabbedPane();
		tabbed_Pane.setBounds(0, 0, 1366, 768);
		getContentPane().add(tabbed_Pane);


		panel_Business = new JPanel();
		panel_Business.setBackground(new Color(0, 0, 139));

		tabbed_Pane.addTab("Business Search", panel_Business);
		panel_Business.setLayout(null);


		scrollPane_Yelp_Main_Category = new JScrollPane();
		scrollPane_Yelp_Main_Category.setBounds(131, 10, 292, 245);
		panel_Business.add(scrollPane_Yelp_Main_Category);

		panel_Yelp_Main_Category = new JPanel();
		panel_Yelp_Main_Category.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Category", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		scrollPane_Yelp_Main_Category.setViewportView(panel_Yelp_Main_Category);
		panel_Yelp_Main_Category.setLayout(new BoxLayout(panel_Yelp_Main_Category, BoxLayout.LINE_AXIS));

		checkBox_Yelp_Main_Category = new JCheckBoxList();
		panel_Yelp_Main_Category.add(checkBox_Yelp_Main_Category);

		scrollPane_Sub_Category = new JScrollPane();
		scrollPane_Sub_Category.setBounds(496, 10, 327, 245);
		panel_Business.add(scrollPane_Sub_Category);

		panel_Sub_Category = new JPanel();
		panel_Sub_Category.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Sub-Category", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		scrollPane_Sub_Category.setViewportView(panel_Sub_Category);
		panel_Sub_Category.setLayout(new BoxLayout(panel_Sub_Category, BoxLayout.LINE_AXIS));

		checkBox_Yelp_Sub_Category = new JCheckBoxList();
		panel_Sub_Category.add(checkBox_Yelp_Sub_Category);

		scrollPane_Attributes = new JScrollPane();
		scrollPane_Attributes.setBounds(896, 10, 321, 245);
		panel_Business.add(scrollPane_Attributes);

		panel_Attributes = new JPanel();
		panel_Attributes.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Attribute", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		scrollPane_Attributes.setViewportView(panel_Attributes);
		panel_Attributes.setLayout(new BoxLayout(panel_Attributes, BoxLayout.LINE_AXIS));

		checkBox_Attributes = new JCheckBoxList();
		panel_Attributes.add(checkBox_Attributes);

		panel_Business_Review = new JPanel();
		panel_Business_Review.setBounds(16, 267, 644, 123);
		panel_Business_Review.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Reviews", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_Business.add(panel_Business_Review);
		panel_Business_Review.setLayout(null);

		label_Business_From = new JLabel("From:");
		label_Business_From.setBounds(10, 28, 52, 30);
		panel_Business_Review.add(label_Business_From);

		dateChooser_Business_From_Date = new JDateChooser();
		dateChooser_Business_From_Date.setDateFormatString("yyyy-MM-dd");
		dateChooser_Business_From_Date.setBounds(51, 28, 105, 30);
		panel_Business_Review.add(dateChooser_Business_From_Date);

		label_Business_To = new JLabel("To:");
		label_Business_To.setBounds(10, 74, 52, 30);
		panel_Business_Review.add(label_Business_To);

		dateChooser_Business_To_Date = new JDateChooser();
		dateChooser_Business_To_Date.setDateFormatString("yyyy-MM-dd");
		dateChooser_Business_To_Date.setBounds(51, 74, 105, 30);
		panel_Business_Review.add(dateChooser_Business_To_Date);

		label_Business_Stars = new JLabel("Star:");
		label_Business_Stars.setBounds(168, 28, 52, 30);
		panel_Business_Review.add(label_Business_Stars);

		comboBox_Business_Star = new JComboBox<String>();
		comboBox_Business_Star.setBounds(210, 29, 105, 30);
		comboBox_Business_Star.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_Business_Review.add(comboBox_Business_Star);

		label_Business_Star_Value = new JLabel("Value:");
		label_Business_Star_Value.setBounds(168, 74, 52, 30);
		panel_Business_Review.add(label_Business_Star_Value);

		textField_Business_Star_Value = new JTextField();
		textField_Business_Star_Value.setBounds(210, 74, 105, 30);
		panel_Business_Review.add(textField_Business_Star_Value);
		textField_Business_Star_Value.setColumns(10);

		label_Business_Votes = new JLabel("Votes:");
		label_Business_Votes.setBounds(327, 28, 105, 30);
		panel_Business_Review.add(label_Business_Votes);

		comboBox_Business_Votes = new JComboBox<String>();
		comboBox_Business_Votes.setBounds(389, 29, 105, 30);
		comboBox_Business_Votes.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_Business_Review.add(comboBox_Business_Votes);

		label_Business_Votes_Value = new JLabel("Value:");
		label_Business_Votes_Value.setBounds(327, 74, 105, 30);
		panel_Business_Review.add(label_Business_Votes_Value);

		textField_Business_Votes_Value = new JTextField();
		textField_Business_Votes_Value.setBounds(389, 74, 105, 30);
		panel_Business_Review.add(textField_Business_Votes_Value);
		textField_Business_Votes_Value.setColumns(10);

		panel_Business_Query = new JPanel();
		panel_Business_Query.setBounds(682, 267, 625, 123);
		panel_Business_Query.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Query", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_Business.add(panel_Business_Query);
		panel_Business_Query.setLayout(null);

		button_Business_Execute = new JButton("Execute Query");
		button_Business_Execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				System.out.println("in execute button");
				populate_Results();
			}
		});
		button_Business_Execute.setBounds(479, 24, 140, 30);
		panel_Business_Query.add(button_Business_Execute);

		button_Review_Filter = new JButton("Apply Filter");
		button_Review_Filter.setBounds(506, 57, 140, 30);
		panel_Business_Review.add(button_Review_Filter);
		button_Review_Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				Apply_Review_Filter(a_Checked_Yelo_Main_Category, a_Checked_Yelp_Sub_Category, hash_Checked_Attributes);
			}
		});

		label_Business_And_Or = new JLabel("Search for:");
		label_Business_And_Or.setBounds(29, 23, 120, 30);
		panel_Business_Query.add(label_Business_And_Or);

		comboBox_Business_And_Or = new JComboBox<String>();
		comboBox_Business_And_Or.setBounds(135, 23, 340, 30);
		comboBox_Business_And_Or.setModel(new DefaultComboBoxModel<String>(new String[] { "AND", "OR" }));
		panel_Business_Query.add(comboBox_Business_And_Or);

		scrollPane_Business_Query = new JScrollPane();
		scrollPane_Business_Query.setBounds(29, 53, 590, 62);
		panel_Business_Query.add(scrollPane_Business_Query);

		textArea_Business_Query = new JTextArea();
		textArea_Business_Query.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane_Business_Query.setViewportView(textArea_Business_Query);
		textArea_Business_Query.setEditable(true);

		scrollPane_Business_Results = new JScrollPane();
		scrollPane_Business_Results.setBounds(20, 403, 640, 295);
		panel_Business.add(scrollPane_Business_Results);

		tableModel_Business_Results = new DefaultTableModel();
		tabel_Business_Results = new JTable();
		tabel_Business_Results.setBackground(Color.LIGHT_GRAY);
		tableModel_Business_Results.addColumn("Business ID");
		tableModel_Business_Results.addColumn("Business Name");
		tableModel_Business_Results.addColumn("City");
		tableModel_Business_Results.addColumn("State");
		tableModel_Business_Results.addColumn("Stars");
		tabel_Business_Results.setModel(tableModel_Business_Results);
		tabel_Business_Results.setBounds(820, 10, 500, 350);
		scrollPane_Business_Results.setViewportView(tabel_Business_Results);

		scrollPane_Business_Reviews = new JScrollPane();
		scrollPane_Business_Reviews.setBounds(682, 403, 625, 295);
		panel_Business.add(scrollPane_Business_Reviews);

		tabelModel_Business_Reviews = new DefaultTableModel();
		tabel_Business_Reviews = new JTable();
		tabel_Business_Reviews.setBackground(Color.LIGHT_GRAY);
		tabelModel_Business_Reviews.addColumn("User ID");
		tabelModel_Business_Reviews.addColumn("Review ID");
		tabelModel_Business_Reviews.addColumn("Review Date");
		tabelModel_Business_Reviews.addColumn("Review Text");
		tabel_Business_Reviews.setModel(tabelModel_Business_Reviews);
		tabel_Business_Reviews.setBounds(820, 378, 500, 320);
		scrollPane_Business_Reviews.setViewportView(tabel_Business_Reviews);

		tabel_Business_Results.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("In Business mouse listener");
                Result_Business_Mouse_Clicked(evt);
			}
		});


		panel_User = new JPanel();
		panel_User.setBackground(new Color(0, 0, 139));
		tabbed_Pane.addTab("User Search", panel_User);
		panel_User.setLayout(null);

		panel_User_Search = new JPanel();
		panel_User_Search.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Users", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_User_Search.setBounds(10, 11, 718, 314);
		panel_User.add(panel_User_Search);
		panel_User_Search.setLayout(null);

		label_Member_Since = new JLabel("Member Since:");
		label_Member_Since.setBounds(21, 25, 115, 30);
		panel_User_Search.add(label_Member_Since);

		comboBox_Yelping_Since_Month = new JComboBox<String>();
		comboBox_Yelping_Since_Month.setBounds(449, 25, 43, 30);
		comboBox_Yelping_Since_Month.setModel(new DefaultComboBoxModel<String>(new String[] { "01","02","03","04","05","06","07","08","09","10","11","12" }));
		panel_User_Search.add(comboBox_Yelping_Since_Month);

		label_Review_Count = new JLabel("Review Count:");
		label_Review_Count.setBounds(21, 74, 115, 30);
		panel_User_Search.add(label_Review_Count);

		comboBox_Review_Count = new JComboBox<String>();
		comboBox_Review_Count.setBounds(146, 74, 150, 30);
		comboBox_Review_Count.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_User_Search.add(comboBox_Review_Count);

		label_Review_Count_Value = new JLabel("Value:");
		label_Review_Count_Value.setBounds(384, 74, 105, 30);
		panel_User_Search.add(label_Review_Count_Value);

		textField_Review_Count_Value = new JTextField();
		textField_Review_Count_Value.setBounds(499, 74, 150, 30);
		panel_User_Search.add(textField_Review_Count_Value);
		textField_Review_Count_Value.setColumns(10);

		label_Friends = new JLabel("No. of Friends:");
		label_Friends.setBounds(21, 130, 115, 30);
		panel_User_Search.add(label_Friends);

		comboBox_Friends = new JComboBox<String>();
		comboBox_Friends.setBounds(146, 135, 150, 30);
		comboBox_Friends.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_User_Search.add(comboBox_Friends);

		label_Friends_Value = new JLabel("Value:");
		label_Friends_Value.setBounds(384, 137, 105, 30);
		panel_User_Search.add(label_Friends_Value);

		textField_Friends_Value = new JTextField();
		textField_Friends_Value.setBounds(499, 135, 150, 30);
		panel_User_Search.add(textField_Friends_Value);
		textField_Friends_Value.setColumns(10);

		label_Avegare_Stars = new JLabel("Average Stars:");
		label_Avegare_Stars.setBounds(21, 187, 115, 30);
		panel_User_Search.add(label_Avegare_Stars);

		comboBox_Average_Stars = new JComboBox<String>();
		comboBox_Average_Stars.setBounds(146, 187, 150, 30);
		comboBox_Average_Stars.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_User_Search.add(comboBox_Average_Stars);

		label_Average_Stars_Value = new JLabel("Value:");
		label_Average_Stars_Value.setBounds(384, 187, 105, 30);
		panel_User_Search.add(label_Average_Stars_Value);

		textField_Average_Stars_Value = new JTextField();
		textField_Average_Stars_Value.setBounds(499, 187, 150, 30);
		panel_User_Search.add(textField_Average_Stars_Value);
		textField_Average_Stars_Value.setColumns(10);

		label_Votes = new JLabel("No. of Votes:");
		label_Votes.setBounds(21, 250, 115, 30);
		panel_User_Search.add(label_Votes);

		comboBox_Votes = new JComboBox<String>();
		comboBox_Votes.setBounds(146, 250, 150, 30);
		comboBox_Votes.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_User_Search.add(comboBox_Votes);

		label_Votes_Value = new JLabel("Value:");
		label_Votes_Value.setBounds(384, 250, 105, 30);
		panel_User_Search.add(label_Votes_Value);

		textField_Votes_Value = new JTextField();
		textField_Votes_Value.setBounds(499, 250, 150, 30);
		panel_User_Search.add(textField_Votes_Value);
		textField_Votes_Value.setColumns(10);

		JLabel lblMonth = new JLabel("Month");
		lblMonth.setBounds(384, 25, 43, 30);
		panel_User_Search.add(lblMonth);

		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(522, 25, 43, 30);
		panel_User_Search.add(lblYear);

		comboBox_Compare_Yelping = new JComboBox<String>();
		comboBox_Compare_Yelping.setBounds(146, 25, 150, 30);
		comboBox_Compare_Yelping.setModel(new DefaultComboBoxModel<String>(new String[] { "=", "<", ">", "<=", ">=" }));
		panel_User_Search.add(comboBox_Compare_Yelping);

		comboBox_Yelping_Since_Year = new JComboBox<String>();
		comboBox_Yelping_Since_Year.setBounds(575, 25, 54, 30);
		comboBox_Yelping_Since_Year.setModel(new DefaultComboBoxModel<String>(new String[] { "2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014" }));
		panel_User_Search.add(comboBox_Yelping_Since_Year);

		panel_User_Query = new JPanel();
		panel_User_Query.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Query", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		panel_User_Query.setBounds(732, 11, 594, 314);
		panel_User.add(panel_User_Query);
		panel_User_Query.setLayout(null);

		label_User_And_Or = new JLabel("Search for:");
		label_User_And_Or.setBounds(21, 34, 105, 30);
		panel_User_Query.add(label_User_And_Or);

		comboBox_User_And_Or = new JComboBox<String>();
		comboBox_User_And_Or.setBounds(100, 34, 292, 30);
		comboBox_User_And_Or.setModel(new DefaultComboBoxModel<String>(new String[] { "AND", "OR" }));
		panel_User_Query.add(comboBox_User_And_Or);

		textArea_User_Query = new JTextArea();
		textArea_User_Query.setEditable(false);
		textArea_User_Query.setBounds(21, 87, 496, 200);
		panel_User_Query.add(textArea_User_Query);

		button_User_Query = new JButton("Execute Query");
		button_User_Query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				populate_User_Results();
			}
		});
		button_User_Query.setBounds(393, 35, 155, 30);
		panel_User_Query.add(button_User_Query);

		scrollPane_User_Results = new JScrollPane();
		scrollPane_User_Results.setBounds(10, 356, 718, 304);
		panel_User.add(scrollPane_User_Results);

		tableModel_User_Results = new DefaultTableModel();
		tabel_User_Results = new JTable();
		tabel_User_Results.setBackground(Color.LIGHT_GRAY);
		tableModel_User_Results.addColumn("User ID");
		tableModel_User_Results.addColumn("Username");
		tableModel_User_Results.addColumn("Yelping Since");
		tableModel_User_Results.addColumn("Average Stars");
		tableModel_User_Results.addColumn("No. of Friends");
		tabel_User_Results.setModel(tableModel_User_Results);
		tabel_User_Results.setBounds(738, 21, 588, 304);
		scrollPane_User_Results.setViewportView(tabel_User_Results);

		scroll_User_Reviews = new JScrollPane();
		scroll_User_Reviews.setBounds(738, 356, 588, 304);
		panel_User.add(scroll_User_Reviews);

		tableModel_User_Reviews = new DefaultTableModel();
		tabel_User_Reviews = new JTable();
		tabel_User_Reviews.setBackground(Color.LIGHT_GRAY);
		tableModel_User_Reviews.addColumn("User ID");
		tableModel_User_Reviews.addColumn("Review ID");
		tableModel_User_Reviews.addColumn("Review Date");
		tableModel_User_Reviews.addColumn("Review Text");
		tabel_User_Reviews.setModel(tableModel_User_Reviews);
		tabel_User_Reviews.setBounds(738, 356, 588, 304);
		scroll_User_Reviews.setViewportView(tabel_User_Reviews);

		tabel_User_Results.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("In reviews mouse listener");
                Result_Mouse_Clicked(evt);
			}
		});

	}



	public void populate_Yelp_Main_Category() {

		Connection connection_Database;
		String get_Yelp_Main_Categories = "SELECT DISTINCT CATEGORY_NAME FROM MAIN_CATEGORIES ORDER BY CATEGORY_NAME\n";
		ResultSet result_Yelp_Main_Categories;
		try {

			connection_Database = DBConnection.getDBConnection();
			preparedStatement_Query = connection_Database.prepareStatement(get_Yelp_Main_Categories);
			result_Yelp_Main_Categories = preparedStatement_Query.executeQuery();

			while(result_Yelp_Main_Categories.next()) {
				listModel_Yelp_Main_Category.addElement(new JCheckBox(result_Yelp_Main_Categories.getString("CATEGORY_NAME")));
			}

			preparedStatement_Query.close();
			result_Yelp_Main_Categories.close();

			checkBox_Yelp_Main_Category.setModel(listModel_Yelp_Main_Category);

		}catch(Exception Ex) {
			Ex.printStackTrace();
		};


		MouseListener mouseListener_Main_Category = new MouseAdapter()
		{

			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent mEvent) {

				if(mEvent.getClickCount() == 1) {
				check_Main_Category = true;
				a_Checked_Yelp_Sub_Category.clear();
				hash_Checked_Attributes.clear();
				textArea_Business_Query.setText("");
				JList<JCheckBox> list_selected_Yelp_Main_Category = (JList<JCheckBox>) mEvent.getSource();
				ArrayList<JCheckBox> a_Yelp_Main_Category = (ArrayList<JCheckBox>) list_selected_Yelp_Main_Category.getSelectedValuesList();
				for(JCheckBox checkBox1: a_Yelp_Main_Category) {
					if(checkBox1.isSelected()){
						a_Checked_Yelo_Main_Category.add(checkBox1.getText());
						System.out.println(checkBox1.getText() + "-added");
					}
					else {
						if(a_Checked_Yelo_Main_Category.size() != 0) {
							a_Checked_Yelo_Main_Category.remove(checkBox1.getText());
						}
					}
				}
				populate_Yelp_Sub_Category(a_Checked_Yelo_Main_Category);
				}
			}

		};
		checkBox_Yelp_Main_Category.addMouseListener(mouseListener_Main_Category);
	}



	public void populate_Yelp_Sub_Category(final HashSet<String> a_Checked_Yelo_Main_Category) {

		select_And_Or();
		Connection connection_Database;
		build_Business_Query(a_Checked_Yelo_Main_Category, a_Checked_Yelp_Sub_Category, hash_Checked_Attributes);
		listModel_Yelp_Sub_Category.removeAllElements();
		checkBox_Yelp_Sub_Category.setModel(listModel_Yelp_Sub_Category);

		if(a_Checked_Yelo_Main_Category.size() != 0) {

			String get_Yelp_Sub_Categories = "SELECT DISTINCT SC.CATEGORY_NAME FROM SUB_CATEGORIES SC, MAIN_CATEGORIES MC\r\n" +
				"WHERE SC.BUSINESS_ID = MC.BUSINESS_ID AND MC.CATEGORY_NAME ";
			String string_Final_Query = get_Yelp_Sub_Categories;

			for(String s: a_Checked_Yelo_Main_Category) {

				string_Final_Query += " LIKE '" + s + "' " + string_And_Or + " " + get_Yelp_Sub_Categories;
			}

			string_Final_Query = string_Final_Query.substring(0, string_Final_Query.length() - (string_And_Or.length() + get_Yelp_Sub_Categories.length() + 1));

			System.out.println(string_Final_Query);
			ResultSet result_Yelp_Sub_Categories = null;

			try {

				connection_Database = DBConnection.getDBConnection();
				preparedStatement_Query = connection_Database.prepareStatement(string_Final_Query);
				result_Yelp_Sub_Categories = preparedStatement_Query.executeQuery();

				while(result_Yelp_Sub_Categories.next()) {
					listModel_Yelp_Sub_Category.addElement(new JCheckBox(result_Yelp_Sub_Categories.getString("CATEGORY_NAME")));
				}

				preparedStatement_Query.close();
				result_Yelp_Sub_Categories.close();

				checkBox_Yelp_Sub_Category.setModel(listModel_Yelp_Sub_Category);

			}catch(Exception Ex) {
				Ex.printStackTrace();
			}
		}
		else {
			listModel_Yelp_Sub_Category.removeAllElements();
			checkBox_Yelp_Sub_Category.setModel(listModel_Yelp_Sub_Category);
		}

		checkBox_Yelp_Sub_Category.addMouseListener(new MouseAdapter() {
				@SuppressWarnings("unchecked")
				public void mouseClicked(MouseEvent mEvent) {

					if(mEvent.getClickCount() == 1) {
					check_Sub_Category = true;
					hash_Checked_Attributes.clear();
					textArea_Business_Query.setText("");
					JList<JCheckBox> list_selected_Yelp_Sub_Categories = (JList<JCheckBox>) mEvent.getSource();
					ArrayList<JCheckBox> a_Yelp_Sub_Categories = (ArrayList<JCheckBox>) list_selected_Yelp_Sub_Categories.getSelectedValuesList();
					System.out.println("Size of Arr sub cat is : "+a_Yelp_Sub_Categories.size());
					for(JCheckBox checkBox1 : a_Yelp_Sub_Categories) {
						if(checkBox1.isSelected()){
							a_Checked_Yelp_Sub_Category.add(checkBox1.getText());
							System.out.println(checkBox1.getText() + "-added"+" and the size of a_Checked_Yelp_Sub_Category now is : "+a_Checked_Yelp_Sub_Category.size());
						}
						else {
							if(a_Checked_Yelp_Sub_Category.size() != 0) {
								a_Checked_Yelp_Sub_Category.remove(checkBox1.getText());
							}
						}
					}
					populate_Attributes(a_Checked_Yelo_Main_Category, a_Checked_Yelp_Sub_Category);
				}
				}
		});
	}



	public void populate_Attributes(final HashSet<String> a_Checked_Yelo_Main_Category, final HashSet<String> a_Checked_Yelp_Sub_Category) {

		select_And_Or();
		Connection connection_Database;
		System.out.println("count of array yelp sub category in populate attributes is : "+a_Checked_Yelp_Sub_Category.size());
		build_Business_Query(a_Checked_Yelo_Main_Category, a_Checked_Yelp_Sub_Category, hash_Checked_Attributes);
		listModel_Attributes.removeAllElements();
		checkBox_Attributes.setModel(listModel_Attributes);

		if(a_Checked_Yelp_Sub_Category.size() != 0) {
				String string_Final_Query = "";

				for(String i : a_Checked_Yelo_Main_Category) {

					for(String j : a_Checked_Yelp_Sub_Category) {

						string_Final_Query += "SELECT DISTINCT BA.ATTRIBUTE_NAME FROM\r\n" +
								"MAIN_CATEGORIES MC,  SUB_CATEGORIES SC, BUSINESS_ATTRIBUTES BA, BUSINESS BU\r\n" +
								"WHERE BU.BUSINESS_ID = BA.BUSINESS_ID AND BU.BUSINESS_ID = SC.BUSINESS_ID AND MC.BUSINESS_ID = BU.BUSINESS_ID AND\r\n" +
								"MC.CATEGORY_NAME LIKE '" + i + "' AND SC.CATEGORY_NAME LIKE '" + j + "'\n" + string_And_Or + "\n";
					}
				}

				string_Final_Query = string_Final_Query.substring(0, string_Final_Query.length() - (string_And_Or.length() + 1));

				System.out.println(string_Final_Query);

				ResultSet resultSet_Attributes = null;

				try {

					connection_Database = DBConnection.getDBConnection();
					preparedStatement_Query = connection_Database.prepareStatement(string_Final_Query);
					resultSet_Attributes = preparedStatement_Query.executeQuery();

					while(resultSet_Attributes.next()) {
						listModel_Attributes.addElement(new JCheckBox(resultSet_Attributes.getString("ATTRIBUTE_NAME")));
					}

					preparedStatement_Query.close();
					resultSet_Attributes.close();

					checkBox_Attributes.setModel(listModel_Attributes);

				}catch(Exception Ex) {
					Ex.printStackTrace();
				}

				checkBox_Attributes.addMouseListener(new MouseAdapter() {

					@SuppressWarnings("unchecked")
					public void mouseClicked(MouseEvent mEvent) {
						JList<JCheckBox> selectedAttributes = (JList<JCheckBox>) mEvent.getSource();
						ArrayList<JCheckBox> arrAttributes = (ArrayList<JCheckBox>) selectedAttributes.getSelectedValuesList();
						for(JCheckBox checkBox1: arrAttributes) {
							if(checkBox1.isSelected()){
								hash_Checked_Attributes.add(checkBox1.getText());
								System.out.println(checkBox1.getText() + "-added");
							}
							else {
								if(hash_Checked_Attributes.size() != 0) {
									hash_Checked_Attributes.remove(checkBox1.getText());
								}
							}
						}
						build_Business_Query(a_Checked_Yelo_Main_Category, a_Checked_Yelp_Sub_Category, hash_Checked_Attributes);
					}
				});

		}
		else {
			listModel_Attributes.removeAllElements();
			checkBox_Attributes.setModel(listModel_Attributes);
		}
	}



	public void build_Business_Query(HashSet<String> a_Checked_Yelo_Main_Category, HashSet<String> a_Checked_Yelp_Sub_Category, HashSet<String> hash_Checked_Attributes) {

		String business_Query = "";
		textArea_Business_Query.setText(business_Query);
		System.out.println("size of main category :"+a_Checked_Yelo_Main_Category.size()+", size of sub category :"+a_Checked_Yelp_Sub_Category.size());


		if(!a_Checked_Yelo_Main_Category.isEmpty() && a_Checked_Yelp_Sub_Category.isEmpty() && hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU\r\n" +
						"WHERE BU.BUSINESS_ID IN\r\n" +
						"(SELECT MC.BUSINESS_ID FROM MAIN_CATEGORIES MC WHERE MC.CATEGORY_NAME LIKE '" + i + "')\n" + string_And_Or + "\n";
			}
		}


		if(!a_Checked_Yelo_Main_Category.isEmpty() && !a_Checked_Yelp_Sub_Category.isEmpty() && hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				for(String j : a_Checked_Yelp_Sub_Category) {

					business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU\r\n" +
							"WHERE BU.BUSINESS_ID IN\r\n" +
							"(SELECT BU.BUSINESS_ID FROM MAIN_CATEGORIES MC,  SUB_CATEGORIES SC, BUSINESS_ATTRIBUTES BA, BUSINESS BU\r\n" +
							"WHERE BU.BUSINESS_ID = BA.BUSINESS_ID AND BU.BUSINESS_ID = SC.BUSINESS_ID AND MC.BUSINESS_ID = BU.BUSINESS_ID AND\r\n" +
							"MC.CATEGORY_NAME LIKE '" + i + "' AND SC.CATEGORY_NAME LIKE '" + j + "' )\n" + string_And_Or + "\n";

				}
			}
		}


		if(!a_Checked_Yelo_Main_Category.isEmpty() && !a_Checked_Yelp_Sub_Category.isEmpty() && !hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				for(String j : a_Checked_Yelp_Sub_Category) {

					for(String k : hash_Checked_Attributes) {

						business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU\r\n" +
								"WHERE BU.BUSINESS_ID IN\r\n" +
								"(SELECT BU.BUSINESS_ID FROM MAIN_CATEGORIES MC,  SUB_CATEGORIES SC, BUSINESS_ATTRIBUTES BA, BUSINESS BU\r\n" +
								"WHERE BU.BUSINESS_ID = BA.BUSINESS_ID AND BU.BUSINESS_ID = SC.BUSINESS_ID AND MC.BUSINESS_ID = BU.BUSINESS_ID AND\r\n" +
								"MC.CATEGORY_NAME LIKE '" + i + "' AND SC.CATEGORY_NAME LIKE '" + j + "' and BA.ATTRIBUTE_NAME LIKE '" +
								k + "' )\n" + string_And_Or + "\n";

					}
				}
			}
		}

		business_Query = business_Query.substring(0, business_Query.length() - (string_And_Or.length() + 1));
		System.out.println(business_Query);
		textArea_Business_Query.setText(business_Query);

	}



	public void populate_Results() {
		tableModel_Business_Results.setRowCount(0);
		Connection connection_Database = null;
		String string_Final_Query = "";
		String[] rowObj = new String[5];
		System.out.println("In populate results");



			string_Final_Query = textArea_Business_Query.getText();
			System.out.println("In populate");
			System.out.println("Final Query : "+string_Final_Query);


			ResultSet resultSet_Results = null;

			try {

				connection_Database = DBConnection.getDBConnection();
				preparedStatement_Query = connection_Database.prepareStatement(string_Final_Query);
				resultSet_Results = preparedStatement_Query.executeQuery();
				System.out.println("connection successfull");

				while(resultSet_Results.next()) {

					rowObj = new String[] {resultSet_Results.getString("BUSINESS_ID"),resultSet_Results.getString("BUSINESS_NAME"), resultSet_Results.getString("CITY"), resultSet_Results.getString("STATE"), resultSet_Results.getString("STARS")};
					tableModel_Business_Results.addRow(rowObj);

				}

				preparedStatement_Query.close();
				resultSet_Results.close();



			}catch(Exception Ex) {
				Ex.printStackTrace();
				System.out.println("In exception "+ Ex);
			}


	}


	public void populate_User_Results() {
		tableModel_User_Results.setRowCount(0);
		Connection connection_Database = null;
		System.out.println("In populate results");
		String[] rowObj = new String[5];
		String yelping_converted = comboBox_Yelping_Since_Year.getSelectedItem().toString() + "-" + comboBox_Yelping_Since_Month.getSelectedItem().toString();
		User_Query = "Select DISTINCT Y.USER_ID, Y.USERNAME, Y.YELPING_SINCE, Y.AVERAGE_STARS, COUNT(F.FRIEND_ID) AS FRIEND_COUNT FROM YELP_USER Y, FRIENDS F WHERE Y.USER_ID = F.USER_ID AND REVIEWS_COUNT "+comboBox_Review_Count.getSelectedItem().toString()+" "+textField_Review_Count_Value.getText().toString()+" "+
				comboBox_User_And_Or.getSelectedItem().toString()+" "+"(VOTES_FUNNY + VOTES_USEFUL + VOTES_COOL) " + comboBox_Votes.getSelectedItem().toString() + " "+ textField_Votes_Value.getText().toString() + " "+
				comboBox_User_And_Or.getSelectedItem().toString()+ " " + "YELPING_SINCE " + comboBox_Compare_Yelping.getSelectedItem().toString() + " '"+yelping_converted+"' " + comboBox_User_And_Or.getSelectedItem().toString() + " Y.AVERAGE_STARS " + comboBox_Average_Stars.getSelectedItem().toString()+ textField_Average_Stars_Value.getText().toString() + " GROUP BY Y.USER_ID, Y.USERNAME, Y.YELPING_SINCE, Y.AVERAGE_STARS HAVING COUNT(F.FRIEND_ID)" + comboBox_Friends.getSelectedItem().toString() + textField_Friends_Value.getText().toString();
		textArea_User_Query.setText(User_Query);


		ResultSet resultSet_Results = null;

			try {

				connection_Database = DBConnection.getDBConnection();
				preparedStatement_Query = connection_Database.prepareStatement(User_Query);
				resultSet_Results = preparedStatement_Query.executeQuery();
				System.out.println("connection successfull");

				while(resultSet_Results.next()) {

					rowObj = new String[] {resultSet_Results.getString("USER_ID"), resultSet_Results.getString("USERNAME"), resultSet_Results.getString("YELPING_SINCE"), resultSet_Results.getString("AVERAGE_STARS"), resultSet_Results.getString("FRIEND_COUNT")};
					tableModel_User_Results.addRow(rowObj);

				}

				preparedStatement_Query.close();
				resultSet_Results.close();



			}catch(Exception Ex) {
				Ex.printStackTrace();
				System.out.println("In exception "+ Ex);
			}


	}

	 private void Result_Mouse_Clicked(java.awt.event.MouseEvent evt) {
		 tableModel_User_Reviews.setRowCount(0);
		 String s_Selected = "";
		 String s_Query = "";
		 Connection connection_Database = null;
		 String[] rowObj = new String[4];
		 ResultSet resultSet_Results = null;
		 System.out.println("In Result mouse clicked");


			try {
				connection_Database = DBConnection.getDBConnection();
				int row_ID = tabel_User_Results.getSelectedRow();
				s_Selected = (String) tabel_User_Results.getValueAt(row_ID, 0);
				s_Query="select r.user_id, r.reviews_id, r.review_date, r.review_text from reviews r where r.user_id = '"+s_Selected+"'";
				preparedStatement_Query = connection_Database.prepareStatement(s_Query);
				resultSet_Results = preparedStatement_Query.executeQuery();
				System.out.println("connection successfull in reviews");

				while(resultSet_Results.next()) {

					rowObj = new String[] {resultSet_Results.getString("USER_ID"),resultSet_Results.getString("REVIEWS_ID"), resultSet_Results.getString("REVIEW_DATE"), resultSet_Results.getString("REVIEW_TEXT")};
					tableModel_User_Reviews.addRow(rowObj);
				}
			}
			catch(Exception Ex) {
				Ex.printStackTrace();
				System.out.println("In exception "+ Ex);
			}
	 }

	private void Result_Business_Mouse_Clicked(java.awt.event.MouseEvent evt) {
		 tabelModel_Business_Reviews.setRowCount(0);
		 String s_Selected = "";
		 String s_Query = "";
		 Connection connection_Database = null;
		 String[] rowObj = new String[4];
		 ResultSet resultSet_Results = null;
		 System.out.println("In Result Business mouse clicked");


			try {
				connection_Database = DBConnection.getDBConnection();
				int row_ID = tabel_Business_Results.getSelectedRow();
				s_Selected = (String) tabel_Business_Results.getValueAt(row_ID, 0);
				s_Query="select r.user_id, r.reviews_id, r.review_date, r.review_text from reviews r where r.business_id = '"+s_Selected+"'";
				preparedStatement_Query = connection_Database.prepareStatement(s_Query);
				resultSet_Results = preparedStatement_Query.executeQuery();
				System.out.println("connection successfull in reviews");

				while(resultSet_Results.next()) {

					rowObj = new String[] {resultSet_Results.getString("USER_ID"),resultSet_Results.getString("REVIEWS_ID"), resultSet_Results.getString("REVIEW_DATE"), resultSet_Results.getString("REVIEW_TEXT")};
					tabelModel_Business_Reviews.addRow(rowObj);
				}
			}
			catch(Exception Ex) {
				Ex.printStackTrace();
				System.out.println("In exception "+ Ex);
			}
	 }

	public void Apply_Review_Filter(HashSet<String> a_Checked_Yelo_Main_Category, HashSet<String> a_Checked_Yelp_Sub_Category, HashSet<String> hash_Checked_Attributes) {
		String d1  = ((JTextField)dateChooser_Business_From_Date.getDateEditor().getUiComponent()).getText();
		String d2 = ((JTextField)dateChooser_Business_To_Date.getDateEditor().getUiComponent()).getText();
		String Star_cmp = comboBox_Business_Star.getSelectedItem().toString();
		String Votes_cmp = comboBox_Business_Votes.getSelectedItem().toString();
		String Star_Value = textField_Business_Star_Value.getText().toString();
		String Votes_value = textField_Business_Votes_Value.getText().toString();

		String business_Query = "";
		textArea_Business_Query.setText(business_Query);
		System.out.println("size of main category :"+a_Checked_Yelo_Main_Category.size()+", size of sub category :"+a_Checked_Yelp_Sub_Category.size());


		if(!a_Checked_Yelo_Main_Category.isEmpty() && a_Checked_Yelp_Sub_Category.isEmpty() && hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU, REVIEWS R\r\n" +
						"WHERE R.BUSINESS_ID = BU.BUSINESS_ID AND R.REVIEW_DATE >= '"+d1+"' AND R.REVIEW_DATE <= '"+d2+"' AND R.STARS "+ Star_cmp + " " + Star_Value +" AND (R.VOTES_FUNNY+R.VOTES_COOL+R.VOTES_USEFUL) "+ Votes_cmp+" "+Votes_value+ " AND " + "BU.BUSINESS_ID IN\r\n" +
						"(SELECT MC.BUSINESS_ID FROM MAIN_CATEGORIES MC WHERE MC.CATEGORY_NAME LIKE '" + i + "')\n" + string_And_Or + "\n";
			}
		}


		if(!a_Checked_Yelo_Main_Category.isEmpty() && !a_Checked_Yelp_Sub_Category.isEmpty() && hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				for(String j : a_Checked_Yelp_Sub_Category) {

					business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU, REVIEWS R\r\n" +
							"WHERE R.BUSINESS_ID = BU.BUSINESS_ID AND R.REVIEW_DATE >= '"+d1+"' AND R.REVIEW_DATE <= '"+d2+"' AND R.STARS "+ Star_cmp + " " + Star_Value +" AND (R.VOTES_FUNNY+R.VOTES_COOL+R.VOTES_USEFUL) "+ Votes_cmp+" "+Votes_value+ " AND " + "BU.BUSINESS_ID IN\r\n" +
							"(SELECT BU.BUSINESS_ID FROM MAIN_CATEGORIES MC,  SUB_CATEGORIES SC, BUSINESS_ATTRIBUTES BA, BUSINESS BU\r\n" +
							"WHERE BU.BUSINESS_ID = BA.BUSINESS_ID AND BU.BUSINESS_ID = SC.BUSINESS_ID AND MC.BUSINESS_ID = BU.BUSINESS_ID AND\r\n" +
							"MC.CATEGORY_NAME LIKE '" + i + "' AND SC.CATEGORY_NAME LIKE '" + j + "' )\n" + string_And_Or + "\n";

				}
			}
		}


		if(!a_Checked_Yelo_Main_Category.isEmpty() && !a_Checked_Yelp_Sub_Category.isEmpty() && !hash_Checked_Attributes.isEmpty()) {

			for(String i : a_Checked_Yelo_Main_Category) {

				for(String j : a_Checked_Yelp_Sub_Category) {

					for(String k : hash_Checked_Attributes) {

						business_Query += "SELECT BU.BUSINESS_ID, BU.BUSINESS_NAME, BU.CITY, BU.STATE, BU.STARS FROM BUSINESS BU, REVIEWS R\r\n" +
								"WHERE R.BUSINESS_ID = BU.BUSINESS_ID AND R.REVIEW_DATE >= '"+d1+"' AND R.REVIEW_DATE <= '"+d2+"' AND R.STARS "+ Star_cmp + " " + Star_Value +" AND (R.VOTES_FUNNY+R.VOTES_COOL+R.VOTES_USEFUL) "+ Votes_cmp+" "+Votes_value+ " AND " + "BU.BUSINESS_ID IN\r\n" +
								"(SELECT BU.BUSINESS_ID FROM MAIN_CATEGORIES MC,  SUB_CATEGORIES SC, BUSINESS_ATTRIBUTES BA, BUSINESS BU\r\n" +
								"WHERE BU.BUSINESS_ID = BA.BUSINESS_ID AND BU.BUSINESS_ID = SC.BUSINESS_ID AND MC.BUSINESS_ID = BU.BUSINESS_ID AND\r\n" +
								"MC.CATEGORY_NAME LIKE '" + i + "' AND SC.CATEGORY_NAME LIKE '" + j + "' and BA.ATTRIBUTE_NAME LIKE '" +
								k + "' )\n" + string_And_Or + "\n";

					}
				}
			}
		}

		business_Query = business_Query.substring(0, business_Query.length() - (string_And_Or.length() + 1));
		System.out.println(business_Query);
		textArea_Business_Query.setText(business_Query);

	}




	public void select_And_Or() {
		comboBox_Business_And_Or.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cmbSelected = (JComboBox<String>) event.getSource();
				String strSelected = (String) cmbSelected.getSelectedItem();
				if(strSelected.equals("OR")) {string_And_Or = "UNION";}
				else {string_And_Or = "INTERSECT";}
			}
		});
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Main t = new Main();
		t.setVisible(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		t.pack();
		t.setSize(screenSize);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setResizable(false);
		t.setLocationRelativeTo(null);
	}

	public class JCheckBoxList extends JList<JCheckBox> {
		  protected Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

		  public JCheckBoxList() {
		    setCellRenderer(new CellRenderer());
		    addMouseListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent e) {
		        int index = locationToIndex(e.getPoint());
		        if (index != -1) {
		          JCheckBox checkbox2 = (JCheckBox) getModel().getElementAt(index);
		          checkbox2.setSelected(!checkbox2.isSelected());
		          repaint();
		        }
		      }
		    });
		    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  }

		  public JCheckBoxList(ListModel<JCheckBox> model){
		    this();
		    setModel(model);
		  }

		  protected class CellRenderer implements ListCellRenderer<JCheckBox> {
		    public Component getListCellRendererComponent(
		        JList<? extends JCheckBox> list, JCheckBox value, int index,
		        boolean isSelected, boolean cellHasFocus) {
		      JCheckBox checkbox2 = value;
		      checkbox2.setBackground(isSelected ? getSelectionBackground()
		          : getBackground());
		      checkbox2.setForeground(isSelected ? getSelectionForeground()
		          : getForeground());
		      checkbox2.setEnabled(isEnabled());
		      checkbox2.setFont(getFont());
		      checkbox2.setFocusPainted(false);
		      checkbox2.setBorderPainted(true);
		      checkbox2.setBorder(isSelected ? UIManager
		          .getBorder("List.focusCellHighlightBorder") : noFocusBorder);
		      return checkbox2;
		    }
		  }
		}
}
