package Yelp;

import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class populate_Yelp_Business_Table {

	public void populate_Yelp_Business() throws SQLException{

		Connection connection_Database = null;
		PreparedStatement p_Business = null;
		PreparedStatement p_Business_Hours = null;
		PreparedStatement p_Yelp_Main_Category = null;
		PreparedStatement p_Yelp_Sub_Category = null;
		PreparedStatement p_Neighbors = null;
		PreparedStatement p_Attributes = null;

		String q_Business = "INSERT INTO BUSINESS (B_ID, F_ADDRESS, OPEN, CITY, R_COUNT, B_NAME, LONGITUDE, STATE, STARS, LATITUDE, B_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		String q_Business_Hours = "INSERT INTO BUSINESS_HOURS (D_OF_WEEK, F_HOUR, T_HOUR, B_ID) VALUES (?,?,?,?)";
		String q_Yelp_Main_Category = "INSERT INTO YELP_MAIN_CATEGORIES (C_NAME, B_ID) VALUES (?,?)";
		String q_Yelp_Sub_Category = "INSERT INTO YELP_SUB_CATEGORIES (C_NAME, B_ID) VALUES (?,?)";
		String q_Neighbors = "INSERT INTO NEIGHBORHOODS (N_NAME, B_ID) VALUES (?,?)";
		String q_Attributes ="INSERT INTO BUSINESS_ATTRIBUTES (A_NAME, B_ID) VALUES (?,?)";

		JSONParser j_Parser = new JSONParser();

		try {
			connection_Database = get_DB_Connection();
			p_Business = connection_Database.prepareStatement(q_Business);
			p_Business_Hours = connection_Database.prepareStatement(q_Business_Hours);
			p_Yelp_Main_Category = connection_Database.prepareStatement(q_Yelp_Main_Category);
			p_Yelp_Sub_Category = connection_Database.prepareStatement(q_Yelp_Sub_Category);
			p_Neighbors = connection_Database.prepareStatement(q_Neighbors);
			p_Attributes = connection_Database.prepareStatement(q_Attributes);

			FileReader file_Reader = new FileReader("D:/Jay/Assignment 3/YelpDataset/YelpDataset/yelp_business.json");
			BufferedReader buffer_Reader = new BufferedReader(file_Reader);
			String next;

			while((next = buffer_Reader.readLine()) != null) {

				Object o_Parse = j_Parser.parse(next);
				JSONObject o_Json = (JSONObject) o_Parse;

				p_Business.setString(1, (String) o_Json.get("business_id"));
				p_Business.setString(2, (String) o_Json.get("full_address"));

				if((Boolean) o_Json.get("open")) { p_Business.setInt(3, 1); }
				else { p_Business.setInt(3, 0); }

				p_Business.setString(4, (String) o_Json.get("city"));
				p_Business.setInt(5, ((Long) o_Json.get("review_count")).intValue());
				p_Business.setString(6, (String) o_Json.get("name"));
				p_Business.setFloat(7, ((Double) o_Json.get("longitude")).floatValue());
				p_Business.setString(8, (String) o_Json.get("state"));
				p_Business.setFloat(9, ((Double) o_Json.get("stars")).floatValue());
				p_Business.setFloat(10, ((Double) o_Json.get("latitude")).floatValue());
				p_Business.setString(11, (String) o_Json.get("type"));

				p_Business.executeUpdate();

				if(o_Json.get("neighborhoods") != null)
				{
					JSONArray a_Neighbors = (JSONArray) o_Json.get("neighborhoods");
					Iterator<String> i_Neighbors = a_Neighbors.iterator();

					while(i_Neighbors.hasNext())
					{
						p_Neighbors.setString(1, i_Neighbors.next());
						p_Neighbors.setString(2, (String) o_Json.get("business_id"));
						p_Neighbors.executeUpdate();
					}
				}

				JSONArray a_Categories = (JSONArray) o_Json.get("categories");
				Iterator<String> i_Categories = a_Categories.iterator();
				String s_Cat;

				while(i_Categories.hasNext())
				{
					s_Cat = i_Categories.next();
					if(s_Cat.equals("Active Life") || s_Cat.equals("Arts & Entertainment") || s_Cat.equals("Automotive") ||
							s_Cat.equals("Car Rental") || s_Cat.equals("Cafes") || s_Cat.equals("Beauty & Spas") ||
							s_Cat.equals("Convenience Stores") || s_Cat.equals("Dentists") || s_Cat.equals("Doctors") ||
							s_Cat.equals("Drugstores") || s_Cat.equals("Department Stores") || s_Cat.equals("Education") ||
							s_Cat.equals("Event Planning & Services") || s_Cat.equals("Flowers & Gifts") ||
							s_Cat.equals("Food") || s_Cat.equals("Health & Medical") || s_Cat.equals("Home Services") ||
							s_Cat.equals("Home & Garden") || s_Cat.equals("Hospitals") || s_Cat.equals("Hotels & Travel") ||
							s_Cat.equals("Hardware Stores") || s_Cat.equals("Grocery") || s_Cat.equals("Medical Centers") ||
							s_Cat.equals("Nurseries & Gardening") || s_Cat.equals("Nightlife") || s_Cat.equals("Restaurants") ||
							s_Cat.equals("Shopping") || s_Cat.equals("Transportation"))
					{
						p_Yelp_Main_Category.setString(1, s_Cat);
						p_Yelp_Main_Category.setString(2, (String) o_Json.get("business_id"));
						p_Yelp_Main_Category.executeUpdate();
					}
					else
					{
						p_Yelp_Sub_Category.setString(1, s_Cat);
						p_Yelp_Sub_Category.setString(2, (String) o_Json.get("business_id"));
						p_Yelp_Sub_Category.executeUpdate();
					}

				}

				if(o_Json.get("attributes") != null)
				{
					JSONObject o_Attributes = (JSONObject) o_Json.get("attributes");
					for (Object objKey : o_Attributes.keySet())
					{
						String s_Key = (String)objKey;
				        Object o_KeyValue = o_Attributes.get(s_Key);

				        if (o_KeyValue instanceof JSONObject)
				        {
				        	JSONObject o_Attributes_1 = (JSONObject) o_Attributes.get(objKey);
				        	for (Object objKey_1 : o_Attributes_1.keySet())
				        	{
				        		String s_Key_1 = (String)objKey_1;
				        		Object o_KeyValue_1 = o_Attributes_1.get(s_Key_1);
				        		if (o_KeyValue_1 instanceof Integer)
				        		{
				        			String s_AttributeValue = ((Long) o_Attributes_1.get(s_Key_1)).toString();
				        			s_Key_1 = s_Key_1 + "_" + s_AttributeValue;
									p_Attributes.setString(1, s_Key_1);
									p_Attributes.setString(2, (String) o_Json.get("business_id"));
									p_Attributes.executeUpdate();
				        		}
				        		else if (o_KeyValue_1 instanceof String)
				        		{
				        			String s_AttributeValue = (String) o_Attributes_1.get(s_Key_1);
				        			s_Key_1 = s_Key_1 + "_" + s_AttributeValue;
									p_Attributes.setString(1, s_Key_1);
									p_Attributes.setString(2, (String) o_Json.get("business_id"));
									p_Attributes.executeUpdate();
				        		}
				        		else if (o_KeyValue_1 instanceof Boolean)
				        		{
				        			boolean a = (Boolean) o_Attributes_1.get(s_Key_1);
				        			String s_AttributeValue = String.valueOf(a);
				        			s_Key_1 = s_Key_1 + "_" + s_AttributeValue;
									p_Attributes.setString(1, s_Key_1);
									p_Attributes.setString(2, (String) o_Json.get("business_id"));
									p_Attributes.executeUpdate();
				        		}
				        	}
				        }
				        else
				        {
				        	if (o_KeyValue instanceof Integer)
			        		{
			        			String s_AttributeValue = ((Long) o_Attributes.get(s_Key)).toString();
			        			s_Key = s_Key + "_" + s_AttributeValue;
								p_Attributes.setString(1, s_Key);
								p_Attributes.setString(2, (String) o_Json.get("business_id"));
								p_Attributes.executeUpdate();
			        		}
			        		else if (o_KeyValue instanceof String)
			        		{
			        			String s_AttributeValue = (String) o_Attributes.get(s_Key);
			        			s_Key = s_Key + "_" + s_AttributeValue;
								p_Attributes.setString(1, s_Key);
								p_Attributes.setString(2, (String) o_Json.get("business_id"));
								p_Attributes.executeUpdate();
			        		}
			        		else if (o_KeyValue instanceof Boolean)
			        		{
			        			boolean a = (Boolean) o_Attributes.get(s_Key);
			        			String s_AttributeValue = String.valueOf(a);
			        			s_Key = s_Key + "_" + s_AttributeValue;
								p_Attributes.setString(1, s_Key);
								p_Attributes.setString(2, (String) o_Json.get("business_id"));
								p_Attributes.executeUpdate();
			        		}
				        }

				    }
				}

				//

				if(o_Json.get("hours") != null)
				{
					JSONObject objHours = (JSONObject) o_Json.get("hours");
					for (Object objKey : objHours.keySet())
					{
				        String s_Key = (String)objKey;
				        JSONObject o_Hours_1 = (JSONObject) objHours.get(s_Key);
				        String s_Open_Hour = (String) o_Hours_1.get("open");
				        Float f_Open_Hour = funcCastHour(s_Open_Hour);
				        String s_Close_Hour = (String) o_Hours_1.get("close");
				        Float f_Close_Hour = funcCastHour(s_Close_Hour);
				        p_Business_Hours.setString(1, s_Key);
				        p_Business_Hours.setFloat(2, f_Open_Hour);
				        p_Business_Hours.setFloat(3, f_Close_Hour);
				        p_Business_Hours.setString(4, (String) o_Json.get("business_id"));
				        p_Business_Hours.executeUpdate();
				    }
				}
			}

			file_Reader.close();

		}catch(FileNotFoundException Ex) {
			Ex.printStackTrace();
		}catch(IOException Ex) {
			Ex.printStackTrace();
		}catch(ParseException Ex) {
			Ex.printStackTrace();
		}catch(Exception Ex) {
			Ex.printStackTrace();
		}finally {

			if(p_Business != null) { p_Business.close();}
			if(p_Business_Hours != null) { p_Business_Hours.close();}
			if(p_Yelp_Main_Category != null) { p_Yelp_Main_Category.close();}
			if(p_Yelp_Sub_Category != null) { p_Yelp_Sub_Category.close();}
			if(p_Neighbors != null) {p_Neighbors.close();}
			if(p_Attributes != null) {p_Attributes.close();}
			if(connection_Database != null) {connection_Database.close();}
		}

	}

	public static Float funcCastHour(String strHour)
	{
		String[] a_Hour = strHour.split(":");
		float f_Hour = Float.parseFloat(a_Hour[0]);
		float f_Min = Float.parseFloat(a_Hour[1]);
		f_Min = f_Min / 100;
		f_Hour = f_Hour + f_Min;
		return f_Hour;
	}

	public static Connection get_DB_Connection() {

		Connection connection_Database = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

		}catch(ClassNotFoundException Ex) {

			System.out.println(Ex.getMessage());
		}

		try {

			connection_Database = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAY", "Yelp_Database", "jay");
			return connection_Database;

		}catch(SQLException Ex) {

			System.out.println(Ex.getMessage());
		}

		return connection_Database;
	}

	public static void main(String[] args) throws SQLException {
		System.out.println("POPULATING BUSINESS NOW!!!");
		populate_Yelp_Business_Table o = new populate_Yelp_Business_Table();
		o.populate_Yelp_Business();
	}

}
