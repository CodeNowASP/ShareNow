package de.tum.asp.sharenow.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.widget.TextView;

/**
 * Diese Klasse bietet Methoden zur Konvertierung von Timestamp Objekten in eine
 * String Repräsentation und umgekehrt.
 */
public class DateConverter {

	/**
	 * Aus zwei Strings, die ein Datum und eine Uhrzeit enthalten, einen
	 * Timestamp erstellen.
	 * 
	 * @param date
	 *            Ein Datum im Format "tt.mm.jj".
	 * @param time
	 *            Eine Zeitangabe im Format "ss:mm".
	 * @return Ein Timestamp mit den gegebenen Werten.
	 */
	public Timestamp fromString(String date, String time) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
					.parse(date + " " + time);
		} catch (ParseException e) {
		}
		return new Timestamp(d.getTime());
	}

	public Timestamp fromTextViews(TextView dateView, TextView timeView) {
		String date = dateView.getText().toString();
		String time = timeView.getText().toString();
		return fromString(date, time);
	}

	public Timestamp fromString(String timestamp) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
					.parse(timestamp);
		} catch (ParseException e) {
		}
		return new Timestamp(d.getTime());
	}

	public String toString(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm",
				Locale.getDefault());
		return sdf.format(timestamp);
	}
}
