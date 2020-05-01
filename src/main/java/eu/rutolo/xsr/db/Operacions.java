package eu.rutolo.xsr.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Operacions {

	Connection con;

	public Operacions() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con = DriverManager.getConnection(url, user, password)
	}
}