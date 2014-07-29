package de.tum.asp.sharenow.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Diese Klasse bietet die Möglichkeit zur Verwaltung einer Benutzersitzung.
 */
public class SessionManager {

	private final String KEY_LOGGED_IN = "loggedIn";
	private final String KEY_ID = "id";
	private final String KEY_NAME = "firstName";

	private SharedPreferences pref;
	private Context context;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Der Kontext der aufrufenden Anwendung.
	 */
	public SessionManager(Context context) {
		this.context = context;
		pref = context.getSharedPreferences("SessionPreferences", 0);
	}

	/**
	 * Einloggen.
	 * 
	 * @param mail
	 *            Die E-Mail Adresse des Nutzers.
	 * @param password
	 *            Das unverschlüsselte Passwort des Nutzers.
	 * @return Wahr wenn der Login erfolgreich war.
	 */
	public boolean login(String mail, String password) {
		LocalDatabase db = new LocalDatabase(context);
		User user = db.getUser(mail);
		if (user != null) {
			// Nutzer existiert in Datenbank
			String hashedPassword = hashPassword(password);
			if (hashedPassword.equals(user.getHashedPassword())) {
				// Passwort richtig, Sitzung anlegen
				Editor editor = pref.edit();
				editor.putBoolean(KEY_LOGGED_IN, true);
				editor.putLong(KEY_ID, user.getId());
				editor.putString(KEY_NAME, user.getFirstName());
				editor.commit();
				return true;
			}
		}
		return false;
	}

	/**
	 * Ausloggen.
	 */
	public void logout() {
		// Sitzungsdaten löschen
		Editor editor = pref.edit();
		editor.clear();
		editor.putBoolean(KEY_LOGGED_IN, false);
		editor.commit();
	}

	/**
	 * @return Die ID des eingeloggten Nutzers.
	 */
	public long getId() {
		return pref.getLong(KEY_ID, 0);
	}

	/**
	 * @return Der Vorname des eingeloggten Nutzers.
	 */
	public String getName() {
		return pref.getString(KEY_NAME, "X");
	}

	/**
	 * @return Wahr, wenn ein Nutzer eingeloggt ist.
	 */
	public boolean loggedIn() {
		// wenn eingeloggt, überprüfen ob nutzer noch existiert
		if (pref.getBoolean(KEY_LOGGED_IN, false)) {
			LocalDatabase db = new LocalDatabase(context);
			User user = db.getUser(pref.getLong(KEY_ID, -1));
			if (user == null) {
				logout();
			}
		}
		return pref.getBoolean(KEY_LOGGED_IN, false);
	}

	/**
	 * Ein Passwort hashen.
	 * 
	 * @param password
	 *            das zu hashende Passwort.
	 * @return Das gehashte Passwort.
	 */
	public static String hashPassword(String password) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

}
