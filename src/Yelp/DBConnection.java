package Yelp;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getDBConnection() {

		Connection connection_Database = null;

		try {

			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			String s_Host = "localhost";
			String s_Port = "1521";
			String s_Database_Name = "jay";
			String s_User_Name = "jay";
			String s_pass = "jay";
			String s_Database_U_link = "jdbc:oracle:thin:@" + s_Host + ":" + s_Port + ":" + s_Database_Name;

			connection_Database = DriverManager.getConnection(s_Database_U_link, s_User_Name, s_pass);

			if(connection_Database != null)
			{
				System.out.println("Connected to Database Successfully!");
			}

			return connection_Database;

		}catch(SQLException Ex) {

			System.out.println(Ex.getMessage());
		}

		return connection_Database;
	}
}
