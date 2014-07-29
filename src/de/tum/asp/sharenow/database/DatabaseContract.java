package de.tum.asp.sharenow.database;

import android.provider.BaseColumns;

/**
 * Diese Klasse legt das Schema der Datenbank fest.
 */
public final class DatabaseContract {

	// Instanziierung vermeiden
	private DatabaseContract() {
	};

	/**
	 * Schema sowie SQL-Anweisungen zum Erstellen/Löschen der Datenbank "users".
	 */
	public static abstract class Users implements BaseColumns {
		public static final String TABLE_NAME = "users";
		public static final String ID = "_id";
		public static final String COLUMN_NAME_MAIL = "mail";
		public static final String COLUMN_NAME_HASHED_PASSWORD = "hashedPassword";
		public static final String COLUMN_NAME_USER_IMAGE = "userImage";
		public static final String COLUMN_NAME_FIRSTNAME = "firstName";
		public static final String COLUMN_NAME_LASTNAME = "lastName";
		public static final String COLUMN_NAME_ACCOUNT_BALANCE = "accountBalance";

		/**
		 * SQL-Anweisung zum Erstellen der Datenbank "users".
		 */
		public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
				+ TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY, "
				+ COLUMN_NAME_MAIL + " TEXT, " + COLUMN_NAME_HASHED_PASSWORD
				+ " TEXT, " + COLUMN_NAME_USER_IMAGE + " BLOB, "
				+ COLUMN_NAME_FIRSTNAME + " TEXT, " + COLUMN_NAME_LASTNAME
				+ " TEXT, " + COLUMN_NAME_ACCOUNT_BALANCE + " DOUBLE" + ")";

		/**
		 * SQL-Anweisung zum Löschen der Datenbank "users".
		 */
		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/**
	 * Schema sowie SQL-Anweisungen zum Erstellen/Löschen der Datenbank
	 * "places".
	 */
	public static abstract class Places implements BaseColumns {
		public static final String TABLE_NAME = "places";
		public static final String ID = "_id";
		public static final String COLUMN_NAME_USER_ID = "userId";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_ADDRESS = "address";
		public static final String COLUMN_NAME_IMAGE = "image";
		public static final String COLUMN_NAME_LOCATION_LAT = "locationLat";
		public static final String COLUMN_NAME_LOCATION_LONG = "locationLong";
		public static final String COLUMN_NAME_PRICE_PER_HOUR = "pricePerHour";
		public static final String COLUMN_NAME_NUMBER_OF_BOOKINGS = "numberOfBookings";
		public static final String COLUMN_NAME_RATING = "rating";

		/**
		 * SQL-Anweisung zum Erstellen der Datenbank "places".
		 */
		public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
				+ TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY, "
				+ COLUMN_NAME_USER_ID + " INTEGER, " + COLUMN_NAME_DESCRIPTION
				+ " TEXT, " + COLUMN_NAME_ADDRESS + " TEXT, "
				+ COLUMN_NAME_IMAGE + " BLOB, " + COLUMN_NAME_LOCATION_LAT
				+ " DOUBLE, " + COLUMN_NAME_LOCATION_LONG + " DOUBLE, "
				+ COLUMN_NAME_PRICE_PER_HOUR + " DOUBLE, "
				+ COLUMN_NAME_NUMBER_OF_BOOKINGS + " INTEGER, "
				+ COLUMN_NAME_RATING + " DOUBLE" + ")";

		/**
		 * SQL-Anweisung zum Löschen der Datenbank "places".
		 */
		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/**
	 * Schema sowie SQL-Anweisungen zum Erstellen/Löschen der Datenbank "slots".
	 */
	public static abstract class Slots implements BaseColumns {
		public static final String TABLE_NAME = "slots";
		public static final String ID = "_id";
		public static final String COLUMN_NAME_PLACE_ID = "placeId";
		public static final String COLUMN_NAME_USER_ID = "userId";
		public static final String COLUMN_NAME_DATE_START = "dateStart";
		public static final String COLUMN_NAME_DATE_END = "dateEnd";
		public static final String COLUMN_NAME_RESERVED = "reserved";
		public static final String COLUMN_NAME_WEEKLY = "weekly";

		/**
		 * SQL-Anweisung zum Erstellen der Datenbank "users".
		 */
		public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
				+ TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY, "
				+ COLUMN_NAME_PLACE_ID + " INTEGER, " + COLUMN_NAME_USER_ID
				+ " INTEGER, " + COLUMN_NAME_DATE_START + " TIMESTAMP, "
				+ COLUMN_NAME_DATE_END + " TIMESTAMP, " + COLUMN_NAME_RESERVED
				+ " BOOLEAN, " + COLUMN_NAME_WEEKLY + " BOOLEAN" + ")";

		/**
		 * SQL-Anweisung zum Löschen der Datenbank "slots".
		 */
		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

}
