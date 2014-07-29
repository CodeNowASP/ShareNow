package de.tum.asp.sharenow.database;

import de.tum.asp.sharenow.database.DatabaseContract;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Diese Klasse vereinfacht die Erstellung einer SQL-Datenbank.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 20;
	public static final String DATABASE_NAME = "ShareNowDatabase";

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Android Context der aufrufenden Anwendung.
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Tabellen anlegen
		db.execSQL(DatabaseContract.Users.SQL_CREATE_ENTRIES);
		db.execSQL(DatabaseContract.Places.SQL_CREATE_ENTRIES);
		db.execSQL(DatabaseContract.Slots.SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Daten löschen & Tabellen neu anlegen
		db.execSQL(DatabaseContract.Users.SQL_DELETE_ENTRIES);
		db.execSQL(DatabaseContract.Places.SQL_DELETE_ENTRIES);
		db.execSQL(DatabaseContract.Slots.SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// gleich verfahren wie bei Upgrade
		onUpgrade(db, oldVersion, newVersion);
	}

}
