package Yelp;

import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class populate_Yelp_Reviews_Table {

	public void populate_Yelp_Reviews() throws SQLException {

		Connection connection_Database = null;
		PreparedStatement p_Reviews = null;
		String q_Reviews = "INSERT INTO USER_REVIEWS (R_ID, U_ID, B_ID, V_FUNNY, V_USEFUL, V_COOL, STARS, R_DATE, R_TEXT, R_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?)";

		JSONParser j_Parser = new JSONParser();

		try {

			connection_Database = get_DB_Connection();
			p_Reviews = connection_Database.prepareStatement(q_Reviews);

			FileReader file_Reader = new FileReader("D:/Jay/Assignment 3/YelpDataset/YelpDataset/yelp_review.json");
			BufferedReader buffer_Reader = new BufferedReader(file_Reader);
			String next;

			while ((next = buffer_Reader.readLine()) != null)
			{
				Object o_Parse = j_Parser.parse(next);
				JSONObject o_Json = (JSONObject) o_Parse;

				p_Reviews.setString(1, (String) o_Json.get("review_id"));
				p_Reviews.setString(2, (String) o_Json.get("user_id"));
				p_Reviews.setString(3, (String) o_Json.get("business_id"));

				JSONObject o_Votes = (JSONObject) o_Json.get("votes");

				p_Reviews.setInt(4, ((Long) o_Votes.get("funny")).intValue());
				p_Reviews.setInt(5, ((Long) o_Votes.get("useful")).intValue());
				p_Reviews.setInt(6, ((Long) o_Votes.get("cool")).intValue());

				p_Reviews.setInt(7, ((Long) o_Json.get("stars")).intValue());
				p_Reviews.setString(8, (String) o_Json.get("date"));
				p_Reviews.setString(9, (String) o_Json.get("text"));
				p_Reviews.setString(10, (String) o_Json.get("type"));

				p_Reviews.executeUpdate();
			}

			file_Reader.close();

		}catch(FileNotFoundException Ex) {
			Ex.printStackTrace();
		}catch(IOException Ex) {
			Ex.printStackTrace();
		}catch(ParseException Ex) {
			Ex.printStackTrace();
		}finally {

			if(p_Reviews != null) {p_Reviews.close();}
			if(connection_Database != null) {connection_Database.close();}
		}
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
		System.out.println("POPULATING USER_REVIEWS NOW!!!");
		populate_Yelp_Reviews_Table o = new populate_Yelp_Reviews_Table();
		o.populate_Yelp_Reviews();
	}
}
