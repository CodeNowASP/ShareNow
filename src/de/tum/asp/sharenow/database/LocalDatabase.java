package de.tum.asp.sharenow.database;

import java.sql.Timestamp;

import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import de.tum.asp.sharenow.util.User;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Klasse zum Zugriff auf die Datenbank.
 */
public class LocalDatabase {

	private Context context;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Android Context der aufrufenden Anwendung.
	 */
	public LocalDatabase(Context context) {
		this.context = context;
	}

	/**
	 * Den Datensatz eines neuen Parkplatzes in die Datenbank einfügen.
	 * 
	 * @param place
	 *            Die Parkplatzdaten, die in die Datenbank geschrieben werden
	 *            sollen.
	 */
	public void insert(Place place) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Places.COLUMN_NAME_USER_ID,
				place.getUserId());
		values.put(DatabaseContract.Places.COLUMN_NAME_DESCRIPTION,
				place.getDescription());
		values.put(DatabaseContract.Places.COLUMN_NAME_ADDRESS,
				place.getAddress());
		values.put(DatabaseContract.Places.COLUMN_NAME_LOCATION_LAT,
				place.getLocationLat());
		values.put(DatabaseContract.Places.COLUMN_NAME_LOCATION_LONG,
				place.getLocationLong());
		values.put(DatabaseContract.Places.COLUMN_NAME_PRICE_PER_HOUR,
				place.getPricePerHour());
		values.put(DatabaseContract.Places.COLUMN_NAME_NUMBER_OF_BOOKINGS,
				place.getNumberOfBookings());
		values.put(DatabaseContract.Places.COLUMN_NAME_RATING,
				place.getRating());

		// Datensatz einfügen & erzeugte _id in place schreiben
		long id = db.insert(DatabaseContract.Places.TABLE_NAME, null, values);
		place.setId(id);

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den Datensatz eines neuen Slots in die Datenbank einfügen.
	 * 
	 * @param slot
	 *            Die Slotdaten, die in die Datenbank geschrieben werden sollen.
	 */
	public void insert(Slot slot) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Slots.COLUMN_NAME_PLACE_ID,
				slot.getPlaceId());
		values.put(DatabaseContract.Slots.COLUMN_NAME_DATE_START, slot
				.getDateStart().toString());
		values.put(DatabaseContract.Slots.COLUMN_NAME_DATE_END, slot
				.getDateEnd().toString());
		values.put(DatabaseContract.Slots.COLUMN_NAME_RESERVED,
				slot.isReserved());
		values.put(DatabaseContract.Slots.COLUMN_NAME_WEEKLY, slot.isWeekly());

		// Datensatz einfügen & erzeugte _id in slot schreiben
		long id = db.insert(DatabaseContract.Slots.TABLE_NAME, null, values);
		slot.setId(id);

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den Datensatz eines neuen Nutzers in die Datenbank einfügen.
	 * 
	 * @param user
	 *            Die Nutzerdaten, die in die Datenbank geschrieben werden
	 *            sollen.
	 */
	public void insert(User user) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Users.COLUMN_NAME_MAIL, user.getMail());
		values.put(DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD,
				user.getHashedPassword());
		values.put(DatabaseContract.Users.COLUMN_NAME_USER_IMAGE,
				user.getUserImage());
		values.put(DatabaseContract.Users.COLUMN_NAME_FIRSTNAME,
				user.getFirstName());
		values.put(DatabaseContract.Users.COLUMN_NAME_LASTNAME,
				user.getLastName());
		values.put(DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE,
				user.getAccountBalance());

		// Datensatz einfügen & erzeugte _id in user schreiben
		long id = db.insert(DatabaseContract.Users.TABLE_NAME, null, values);
		user.setId(id);

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den geänderten Datensatz eines Parkplatzes in die Datenbank einfügen.
	 * 
	 * @param place
	 *            Die Parkplatzdaten, welche die in der Datenbank gespeicherten
	 *            ersetzen sollen.
	 */
	public void update(Place place) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Places.COLUMN_NAME_USER_ID,
				place.getUserId());
		values.put(DatabaseContract.Places.COLUMN_NAME_DESCRIPTION,
				place.getDescription());
		values.put(DatabaseContract.Places.COLUMN_NAME_ADDRESS,
				place.getDescription());
		values.put(DatabaseContract.Places.COLUMN_NAME_LOCATION_LAT,
				place.getLocationLat());
		values.put(DatabaseContract.Places.COLUMN_NAME_LOCATION_LONG,
				place.getLocationLong());
		values.put(DatabaseContract.Places.COLUMN_NAME_PRICE_PER_HOUR,
				place.getPricePerHour());
		values.put(DatabaseContract.Places.COLUMN_NAME_NUMBER_OF_BOOKINGS,
				place.getNumberOfBookings());
		values.put(DatabaseContract.Places.COLUMN_NAME_RATING,
				place.getRating());

		// Datensatz überschreiben
		db.update(DatabaseContract.Places.TABLE_NAME, values, "_id=?",
				new String[] { Long.toString(place.getId()) });

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den geänderten Datensatz eines Slots in die Datenbank einfügen.
	 * 
	 * @param slot
	 *            Die Slotdaten, welche die in der Datenbank gespeicherten
	 *            ersetzen sollen.
	 */
	public void update(Slot slot) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Slots.COLUMN_NAME_PLACE_ID,
				slot.getPlaceId());
		values.put(DatabaseContract.Slots.COLUMN_NAME_DATE_START, slot
				.getDateStart().toString());
		values.put(DatabaseContract.Slots.COLUMN_NAME_DATE_END, slot
				.getDateEnd().toString());
		values.put(DatabaseContract.Slots.COLUMN_NAME_RESERVED,
				slot.isReserved());
		values.put(DatabaseContract.Slots.COLUMN_NAME_WEEKLY, slot.isWeekly());

		// Datensatz überschreiben
		db.update(DatabaseContract.Slots.TABLE_NAME, values, "_id=?",
				new String[] { Long.toString(slot.getId()) });

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den geänderten Datensatz eines Nutzers in die Datenbank einfügen.
	 * 
	 * @param user
	 *            Die Nutzerdaten, welche die in der Datenbank gespeicherten
	 *            ersetzen sollen.
	 */
	public void update(User user) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Datensatz erstellen
		ContentValues values = new ContentValues();
		values.put(DatabaseContract.Users.ID, user.getId());
		values.put(DatabaseContract.Users.COLUMN_NAME_MAIL, user.getMail());
		values.put(DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD,
				user.getHashedPassword());
		values.put(DatabaseContract.Users.COLUMN_NAME_USER_IMAGE,
				user.getUserImage());
		values.put(DatabaseContract.Users.COLUMN_NAME_FIRSTNAME,
				user.getFirstName());
		values.put(DatabaseContract.Users.COLUMN_NAME_LASTNAME,
				user.getLastName());
		values.put(DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE,
				user.getAccountBalance());

		// Datensatz überschreiben
		db.update(DatabaseContract.Users.TABLE_NAME, values, "_id=?",
				new String[] { Long.toString(user.getId()) });

		// Referenz auf Datenbank schließen
		dbHelper.close();
	}

	/**
	 * Den Datensatz eines Parkplatzes aus der Datenbank auslesen.
	 * 
	 * @param id
	 *            Die ID des Parkplatzes.
	 * @return Die Daten des Parkplatzes. Null, falls der gesuchte Parkplatz
	 *         nicht gefunden wurde.
	 */
	public Place getPlace(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// festlegen welche Spalten zurückgegeben werden
		String[] projection = { DatabaseContract.Places.COLUMN_NAME_USER_ID,
				DatabaseContract.Places.COLUMN_NAME_DESCRIPTION,
				DatabaseContract.Places.COLUMN_NAME_ADDRESS,
				DatabaseContract.Places.COLUMN_NAME_IMAGE,
				DatabaseContract.Places.COLUMN_NAME_LOCATION_LAT,
				DatabaseContract.Places.COLUMN_NAME_LOCATION_LONG,
				DatabaseContract.Places.COLUMN_NAME_PRICE_PER_HOUR,
				DatabaseContract.Places.COLUMN_NAME_NUMBER_OF_BOOKINGS,
				DatabaseContract.Places.COLUMN_NAME_RATING };

		// Parkplatz abfragen
		Cursor cursor = db.query(DatabaseContract.Places.TABLE_NAME,
				projection, DatabaseContract.Places.ID + "=?",
				new String[] { Long.toString(id) }, null, null, null);
		cursor.moveToFirst();
		Place place = null;

		if (cursor.getCount() > 0) {
			// Parkplatz gefunden, neues Place-Objekt erstellen & füllen
			place = new Place();
			place.setId(id);
			place.setUserId(cursor.getLong(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_USER_ID)));
			place.setDescription(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_DESCRIPTION)));
			place.setAddress(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_ADDRESS)));
			place.setImage(cursor.getBlob(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_IMAGE)));
			place.setLocationLat(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_LOCATION_LAT)));
			place.setLocationLong(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_LOCATION_LONG)));
			place.setPricePerHour(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_PRICE_PER_HOUR)));
			place.setNumberOfBookings(cursor.getInt(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_NUMBER_OF_BOOKINGS)));
			place.setRating(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Places.COLUMN_NAME_RATING)));
		}

		// Referenz auf Datenbank schließen
		dbHelper.close();

		// Place-Objekt zurückgeben
		return place;
	}

	/**
	 * Den Datensatz eines Slots aus der Datenbank auslesen.
	 * 
	 * @param id
	 *            Die ID des Slots.
	 * @return Die Daten des Slots. Null, falls der Slot nicht gefunden wurde.
	 */
	@SuppressLint("SimpleDateFormat")
	public Slot getSlot(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// festlegen welche Spalten zurückgegeben werden
		String[] projection = { DatabaseContract.Slots.COLUMN_NAME_PLACE_ID,
				DatabaseContract.Slots.COLUMN_NAME_DATE_START,
				DatabaseContract.Slots.COLUMN_NAME_DATE_END,
				DatabaseContract.Slots.COLUMN_NAME_RESERVED };

		// Slot abfragen
		Cursor cursor = db.query(DatabaseContract.Slots.TABLE_NAME, projection,
				DatabaseContract.Slots.ID + "=?",
				new String[] { Long.toString(id) }, null, null, null);
		cursor.moveToFirst();

		Slot slot = null;
		if (cursor.getCount() > 0) {
			// Slot gefunden, neues Slot-Objekt erstellen & mit Daten füllen
			slot = new Slot();
			slot.setId(id);
			slot.setPlaceId(cursor.getLong(cursor
					.getColumnIndexOrThrow(DatabaseContract.Slots.COLUMN_NAME_PLACE_ID)));
			slot.setReserved(cursor.getInt(cursor
					.getColumnIndexOrThrow(DatabaseContract.Slots.COLUMN_NAME_RESERVED)) == 1);
			slot.setWeekly(cursor.getInt(cursor
					.getColumnIndexOrThrow(DatabaseContract.Slots.COLUMN_NAME_WEEKLY)) == 1);

			// Start- und Enddatum von String in java.sql.Timestamp umwandeln
			String dateStart = cursor
					.getString(cursor
							.getColumnIndexOrThrow(DatabaseContract.Slots.COLUMN_NAME_DATE_START));
			String dateEnd = cursor
					.getString(cursor
							.getColumnIndexOrThrow(DatabaseContract.Slots.COLUMN_NAME_DATE_END));
			slot.setDateStart(Timestamp.valueOf(dateStart));
			slot.setDateEnd(Timestamp.valueOf(dateEnd));
		}

		// Referenz auf Datenbank schließen
		dbHelper.close();

		// Slot-Objekt zurückgeben
		return slot;
	}

	/**
	 * Den Datensatz eines Nutzers aus der Datenbank auslesen.
	 * 
	 * @param id
	 *            Die ID des Nutzers.
	 * @return Die Daten des Nutzers. Null, falls der Nutzer nicht gefunden
	 *         wurde.
	 */
	public User getUser(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// festlegen welche Spalten zurückgegeben werden
		String[] projection = { DatabaseContract.Users.COLUMN_NAME_MAIL,
				DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD,
				DatabaseContract.Users.COLUMN_NAME_USER_IMAGE,
				DatabaseContract.Users.COLUMN_NAME_FIRSTNAME,
				DatabaseContract.Users.COLUMN_NAME_LASTNAME,
				DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE };

		// Nutzer abfragen
		Cursor cursor = db.query(DatabaseContract.Users.TABLE_NAME, projection,
				DatabaseContract.Users.ID + "=?",
				new String[] { Long.toString(id) }, null, null, null);
		cursor.moveToFirst();

		User user = null;
		if (cursor.getCount() > 0) {
			// User gefunden, neues User-Objekt erstellen & mit Daten füllen
			user = new User();
			user.setId(id);
			user.setMail(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_MAIL)));
			user.setHashedPassword(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD)));
			user.setUserImage(cursor.getBlob(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_USER_IMAGE)));
			user.setFirstName(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_FIRSTNAME)));
			user.setLastName(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_LASTNAME)));
			user.setAccountBalance(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE)));
		}

		// Referenz auf Datenbank schließen
		dbHelper.close();

		// User-Objekt zurückgeben
		return user;
	}

	/**
	 * Den Datensatz eines Nutzers aus der Datenbank auslesen.
	 * 
	 * @param mail
	 *            Die E-Mail Adresse des Nutzers.
	 * @return Die Daten des Nutzers. Null, falls der Nutzer nicht gefunden
	 *         wurde.
	 */
	public User getUser(String mail) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// festlegen welche Spalten zurückgegeben werden
		String[] projection = { DatabaseContract.Users.ID,
				DatabaseContract.Users.COLUMN_NAME_MAIL,
				DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD,
				DatabaseContract.Users.COLUMN_NAME_USER_IMAGE,
				DatabaseContract.Users.COLUMN_NAME_FIRSTNAME,
				DatabaseContract.Users.COLUMN_NAME_LASTNAME,
				DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE };

		// Nutzer abfragen
		Cursor cursor = db.query(DatabaseContract.Users.TABLE_NAME, projection,
				DatabaseContract.Users.COLUMN_NAME_MAIL + "=?",
				new String[] { mail }, null, null, null);
		cursor.moveToFirst();
		User user = null;
		if (cursor.getCount() > 0) {
			// User gefunden, neues User-Objekt erstellen & mit Daten füllen
			user = new User();
			user.setId(cursor.getLong(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.ID)));
			user.setMail(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_MAIL)));
			user.setHashedPassword(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_HASHED_PASSWORD)));
			user.setUserImage(cursor.getBlob(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_USER_IMAGE)));
			user.setFirstName(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_FIRSTNAME)));
			user.setLastName(cursor.getString(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_LASTNAME)));
			user.setAccountBalance(cursor.getDouble(cursor
					.getColumnIndexOrThrow(DatabaseContract.Users.COLUMN_NAME_ACCOUNT_BALANCE)));
		}

		// Referenz auf Datenbank schließen
		dbHelper.close();

		// User-Objekt zurückgeben
		return user;
	}

	/**
	 * Den Datensatz eines Parkplatzes aus der Datenbank löschen.
	 * 
	 * @param id
	 *            Die ID des zu löschenden Parkplatzes.
	 */
	public void deletePlace(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DatabaseContract.Places.TABLE_NAME,
				DatabaseContract.Places.ID + "=?",
				new String[] { Long.toString(id) });
	}

	/**
	 * Den Datensatz eines Slots aus der Datenbank löschen.
	 * 
	 * @param id
	 *            Die ID des zu löschenden Slots.
	 */
	public void deleteSlot(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DatabaseContract.Slots.TABLE_NAME, DatabaseContract.Slots.ID
				+ "=?", new String[] { Long.toString(id) });
	}

	/**
	 * Den Datensatz eines Users aus der Datenbank löschen.
	 * 
	 * @param id
	 *            Die ID des zu löschenden Users.
	 */
	public void deleteUser(long id) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(DatabaseContract.Users.TABLE_NAME, DatabaseContract.Users.ID
				+ "=?", new String[] { Long.toString(id) });
	}
}
