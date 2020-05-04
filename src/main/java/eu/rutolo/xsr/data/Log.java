package eu.rutolo.xsr.data;

import eu.rutolo.xsr.Main;

/**
 * Niveles de Log: * 1: error * 2: warning * 3: info * 4: debug
 */
public class Log {

	public static void e(String s) {
		if (Main.conf.getLogLevel() >= 1) {
			System.err.println("ERROR: " + s);
		}
	}

	public static void w(String s) {
		if (Main.conf.getLogLevel() >= 2) {
			System.out.println("WARNING: " + s);
		}
	}
	
	public static void i(String s) {
		if (Main.conf.getLogLevel() >= 3) {
			System.out.println("INFO: " + s);
		}
	}

	public static void d(String s) {
		if (Main.conf.getLogLevel() >= 4) {
			System.out.println("DEBUG: " + s);
		}
	}
}
