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

public class populate_Yelp_User_Table {

	public void populate_Yelp_User() throws SQLException {


		Connection connection_Database = null;
		PreparedStatement p_Statement_User = null;
		PreparedStatement p_Statement_Friends = null;
		PreparedStatement p_Statement_Elite = null;
		String q_User = "INSERT INTO YELP_USER (Y_SINCE, V_FUNNY, V_USEFUL, V_COOL, R_COUNT, U_NAME, U_ID, FANS, A_STARS, U_TYPE, C_FUNNY, C_COOL, C_WRITER, C_PHOTOS, C_HOT, C_MORE, C_PLAIN, C_NOTE, C_PROFILE, C_CUTE, C_LIST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String q_Friends = "INSERT INTO USER_FRIENDS (U_ID, F_ID) VALUES (?,?)";
		String q_Elite = "INSERT INTO USER_ELITE (U_ID, ELITE) VALUES (?,?)";

		JSONParser j_Parser = new JSONParser();

		try {

			connection_Database = get_DB_Connection();
			p_Statement_User = connection_Database.prepareStatement(q_User);
			p_Statement_Friends = connection_Database.prepareStatement(q_Friends);
			p_Statement_Elite = connection_Database.prepareStatement(q_Elite);
			FileReader file_Reader = new FileReader("D:/Jay/YelpDataset/YelpDataset/yelp_user.json");
			BufferedReader buffer_Reader = new BufferedReader(file_Reader);
			String next;

			while((next = buffer_Reader.readLine()) != null) {

				Object o_Parse = j_Parser.parse(next);
				JSONObject o_Json = (JSONObject) o_Parse;
				JSONObject o_Votes = (JSONObject) o_Json.get("votes");

				p_Statement_User.setString(1, (String) o_Json.get("yelping_since"));

				p_Statement_User.setInt(2, ((Long) o_Votes.get("funny")).intValue());
				p_Statement_User.setInt(3, ((Long) o_Votes.get("useful")).intValue());
				p_Statement_User.setInt(4, ((Long) o_Votes.get("cool")).intValue());

				p_Statement_User.setInt(5, ((Long) o_Json.get("review_count")).intValue());
				p_Statement_User.setString(6, (String) o_Json.get("name"));
				p_Statement_User.setString(7, (String) o_Json.get("user_id"));
				p_Statement_User.setInt(8, ((Long) o_Json.get("fans")).intValue());
				p_Statement_User.setFloat(9,  ((Double) o_Json.get("average_stars")).floatValue());
				p_Statement_User.setString(10, (String) o_Json.get("type"));

				JSONObject o_User_Compliments = (JSONObject) o_Json.get("compliments");

				p_Statement_User.setInt(11, 0);
				if(o_User_Compliments.get("funny") != null) {p_Statement_User.setInt(11, ((Long) o_User_Compliments.get("funny")).intValue());};

				p_Statement_User.setInt(12, 0);
				if(o_User_Compliments.get("cool") != null) {p_Statement_User.setInt(12, ((Long) o_User_Compliments.get("cool")).intValue());};

				p_Statement_User.setInt(13, 0);
				if(o_User_Compliments.get("writer") != null) {p_Statement_User.setInt(13, ((Long) o_User_Compliments.get("writer")).intValue());};

				p_Statement_User.setInt(14, 0);
				if(o_User_Compliments.get("photos") != null) {p_Statement_User.setInt(14, ((Long) o_User_Compliments.get("photos")).intValue());};

				p_Statement_User.setInt(15, 0);
				if(o_User_Compliments.get("hot") != null) {p_Statement_User.setInt(15, ((Long) o_User_Compliments.get("hot")).intValue());};

				p_Statement_User.setInt(16, 0);
				if(o_User_Compliments.get("more") != null) {p_Statement_User.setInt(16, ((Long) o_User_Compliments.get("more")).intValue());};

				p_Statement_User.setInt(17, 0);
				if(o_User_Compliments.get("plain") != null) {p_Statement_User.setInt(17, ((Long) o_User_Compliments.get("plain")).intValue());};

				p_Statement_User.setInt(18, 0);
				if(o_User_Compliments.get("note") != null) {p_Statement_User.setInt(18, ((Long) o_User_Compliments.get("note")).intValue());};

				p_Statement_User.setInt(19, 0);
				if(o_User_Compliments.get("profile") != null) {p_Statement_User.setInt(19, ((Long) o_User_Compliments.get("profile")).intValue());};

				p_Statement_User.setInt(20, 0);
				if(o_User_Compliments.get("cute") != null) {p_Statement_User.setInt(20, ((Long) o_User_Compliments.get("cute")).intValue());};

				p_Statement_User.setInt(21, 0);
				if(o_User_Compliments.get("list") != null) {p_Statement_User.setInt(21, ((Long) o_User_Compliments.get("list")).intValue());};

				p_Statement_User.executeUpdate();

				if(o_Json.get("friends") != null) {

					JSONArray a_Friends = (JSONArray) o_Json.get("friends");
					Iterator<String> i_Friends = a_Friends.iterator();

					while(i_Friends.hasNext()) {

						p_Statement_Friends.setString(1, (String) o_Json.get("user_id"));
						p_Statement_Friends.setString(2, i_Friends.next());
						p_Statement_Friends.executeUpdate();
					}
				}

				if(o_Json.get("elite") != null) {

					JSONArray a_Elite = (JSONArray) o_Json.get("elite");
					Iterator<Long> i_Elite = a_Elite.iterator();

					while(i_Elite.hasNext()){

						p_Statement_Elite.setString(1, (String) o_Json.get("user_id"));
						p_Statement_Elite.setInt(2, (i_Elite.next()).intValue());
						p_Statement_Elite.executeUpdate();
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

			if(p_Statement_User != null) p_Statement_User.close();
			if(p_Statement_Friends != null) p_Statement_Friends.close();
			if(p_Statement_Elite != null) p_Statement_Elite.close();
			if(connection_Database != null) connection_Database.close();
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
		System.out.println("POPULATING USER NOW!!!");
		populate_Yelp_User_Table o = new populate_Yelp_User_Table();
		o.populate_Yelp_User();
	}
}
