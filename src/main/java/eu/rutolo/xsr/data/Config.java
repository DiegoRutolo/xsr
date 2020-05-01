package eu.rutolo.xsr.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static final String CONF_FILE = "/etc/xsrs.conf";

	Properties prop;

	public Config() {
		prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(CONF_FILE);
			prop.load(is);
		} catch(Exception e) {

		}
	}
	
	public String getDBHost() {
		return prop.getProperty("MYSQL_HOST");
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
