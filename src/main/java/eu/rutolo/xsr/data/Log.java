package eu.rutolo.xsr.data;

import eu.rutolo.xsr.Main;

/**
 * Niveles de Log: * 1: error * 2: warning * 3: info * 4: debug
 */
public class Log {

	private static int logLevel = Main.conf.getLogLevel();

	public static void e(String s) {
		if (logLevel <= 1) {
			System.out.println(s);
		}
	}
	
	public static void i(String s) {
		if (logLevel <= 3) {
			System.out.println(s);
		}
	}
}
