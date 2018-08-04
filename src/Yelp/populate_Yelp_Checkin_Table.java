package Yelp;

import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class populate_Yelp_Checkin_Table {

	public void populate_Yelp_Checkin() throws SQLException {

		Connection connection_Database = null;
		PreparedStatement p_CheckIn = null;
		String q_CheckIn = "INSERT INTO USER_CHECKIN (C_DAY, C_HOUR, C_COUNT, B_ID) VALUES (?,?,?,?)";

		JSONParser j_Parser = new JSONParser();

		try {
			connection_Database = get_DB_Connection();
			p_CheckIn = connection_Database.prepareStatement(q_CheckIn);

			FileReader file_Reader = new FileReader("D:/Jay/Assignment 3/YelpDataset/YelpDataset/yelp_checkin.json");
			BufferedReader buffer_Reader = new BufferedReader(file_Reader);
			String next;

			while((next = buffer_Reader.readLine()) != null) {

				Object o_Parse = j_Parser.parse(next);
				JSONObject o_Json = (JSONObject) o_Parse;

				p_CheckIn.setString(4, (String) o_Json.get("business_id"));
				JSONObject objJson_1 = (JSONObject) o_Json.get("checkin_info");
				String s_Day;
				int hr;
				int cn;

				for (Object o_Key : objJson_1.keySet())
				{
			        String s_Key = (String)o_Key;
			        Object o_KeyValue = objJson_1.get(s_Key);
			        s_Day = funcParseDay(s_Key);
			        hr = funcParseHour(s_Key);
			        cn = ((Long) o_KeyValue).intValue();

			        p_CheckIn.setString(1, s_Day);
			        p_CheckIn.setInt(2, hr);
			        p_CheckIn.setInt(3, cn);
			        p_CheckIn.executeUpdate();
			    }
			}

			file_Reader.close();

		}catch(FileNotFoundException Ex) {
			Ex.printStackTrace();
		}catch(IOException Ex) {
			Ex.printStackTrace();
		}catch(ParseException Ex) {
			Ex.printStackTrace();
		}finally {

			if(p_CheckIn != null) {p_CheckIn.close();}
			if(connection_Database != null) {connection_Database.close();}
		}
	}

	public static String funcParseDay(String s_Day)
	{
		String[] a_Day = s_Day.split("-");
		String s_Day_1 = "Incorrect Value!!!";

		if (a_Day[1].equals("0"))
		{
			s_Day_1 = "SUNDAY";
		}
		if (a_Day[1].equals("1"))
		{
			s_Day_1 = "MONDAY";
		}
		if (a_Day[1].equals("2"))
		{
			s_Day_1 = "TUESDAY";
		}
		if (a_Day[1].equals("3"))
		{
			s_Day_1 = "WEDNESDAY";
		}
		if (a_Day[1].equals("4"))
		{
			s_Day_1 = "THURSDAY";
		}
		if (a_Day[1].equals("5"))
		{
			s_Day_1 = "FRIDAY";
		}
		if (a_Day[1].equals("6"))
		{
			s_Day_1 = "SATURDAY";
		}

		return s_Day_1;
	}

	public static int funcParseHour(String s_Hour)
	{
		String[] a_Hour = s_Hour.split("-");
		return Integer.parseInt(a_Hour[0]);
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
		System.out.println("POPULATING USER_CHECKIN NOW!!!");
		populate_Yelp_Checkin_Table o = new populate_Yelp_Checkin_Table();
		o.populate_Yelp_Checkin();
	}
}
