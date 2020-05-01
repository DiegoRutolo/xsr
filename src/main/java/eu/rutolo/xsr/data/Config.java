package eu.rutolo.xsr.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static final String CONF_FILE = "/etc/xsrs.conf";
	public static final int DEFAULT_LOG_LEVEL = 3;

	private Properties prop;

	public Config() {
		prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(CONF_FILE);
			prop.load(is);
		} catch(Exception e) {
			Log.e("Error al leer configuración.");
			System.exit(1);
		}
	}

	public int getLogLevel() {
		int l = 3;
		try {
			l = Integer.parseInt(prop.getProperty("LOG_LEVEL"));
		} catch (Exception e) {
			l = 3;
		}
		return l;
	}
	
	public String getDBHost() {
		return prop.getProperty("MYSQL_HOST");
	}

	public int getDBPort() {
		int p = 3306;
		try {
			p = Integer.parseInt(prop.getProperty("MYSQL_PORT"));
		} catch (Exception e) {
			p = 3306;
		}
		return p;
	}

	public String getDBName() {
		return prop.getProperty("MYSQL_DATABASE");
	}

	public String getDBUser() {
		return prop.getProperty("MYSQL_USER");
	}

	public String getDBPasswd() {
		return prop.getProperty("MYSQL_PASSWD");
	}
}
