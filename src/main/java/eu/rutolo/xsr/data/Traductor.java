package eu.rutolo.xsr.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.rutolo.xsr.Main;

public class Traductor {

	public static Date string2date(String str) {
		Date date;
		DateFormat df = new SimpleDateFormat(Main.DATE_FORMAT);
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}
}