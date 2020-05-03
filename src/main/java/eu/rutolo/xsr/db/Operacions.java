package eu.rutolo.xsr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import eu.rutolo.xsr.Main;
import eu.rutolo.xsr.data.Log;

public class Operacions {

	private Connection con;

	public Operacions() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "jdbc:mysql://" + Main.conf.getDBHost() + ":" + Main.conf.getDBPort() + "/"
				+ Main.conf.getDBName();
		try {
			con = DriverManager.getConnection(url, Main.conf.getDBUser(), Main.conf.getDBPasswd());
			Log.i("Conexión a DB correcta");
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e("Error SQL: " + e.getMessage());
			System.exit(2);
		}
	}
}
