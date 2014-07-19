package de.tum.asp.sharenow.app;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.util.DateConverter;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

/**
 * Diese Seite enthält eine Google Maps Karte, auf der gefunden Parkplätze
 * angezeigt und gebucht werden können.
 */
public class MapViewActivity extends FragmentActivity {

	// Parameter die bei einer Suche an den Intent angehängt werden
	public static String INTENT_EXTRA_BASIC_SEARCH = "basicSearchPerformed";
	public static String INTENT_EXTRA_HOURS = "hours";
	public static String INTENT_EXTRA_START_DATE = "dateStart";
	public static String INTENT_EXTRA_START_TIME = "timeStart";
	public static String INTENT_EXTRA_END_DATE = "dateEnd";
	public static String INTENT_EXTRA_END_TIME = "timeEnd";
	public static String INTENT_EXTRA_ADDRESS = "address";
	public static String INTENT_EXTRA_DISTANCE = "distance";

	private static double BASIC_DISTANCE_IN_KM = 1.0;

	private GoogleMap map;
	private double distance;
	private Location location;
	private Timestamp start, end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Parameter von Intent holen
		DateConverter dateConverter = new DateConverter();

		if (getIntent().hasExtra(INTENT_EXTRA_HOURS)) {
			// schnelle Suche ausgeführt
			int hours = getIntent().getExtras().getInt(INTENT_EXTRA_HOURS);
			distance = BASIC_DISTANCE_IN_KM;
			long currentTime = System.currentTimeMillis();
			start = new Timestamp(currentTime);
			end = new Timestamp(currentTime + hours * 3600 * 1000);

			// aktuelle Position bestimmen
			ProgressDialog dialog = ProgressDialog.show(MapViewActivity.this,
					"Retrieving position", "Please wait...", true);
			GetPosition gp = new GetPosition();
			gp.execute(dialog);
			try {
				location = gp.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		} else {
			// komplexe Suche ausgeführt
			String dateStart = getIntent().getExtras().getString(
					INTENT_EXTRA_START_DATE);
			String timeStart = getIntent().getExtras().getString(
					INTENT_EXTRA_START_TIME);
			start = dateConverter.fromString(dateStart, timeStart);
			String dateEnd = getIntent().getExtras().getString(
					INTENT_EXTRA_END_DATE);
			String timeEnd = getIntent().getExtras().getString(
					INTENT_EXTRA_END_TIME);
			end = dateConverter.fromString(dateStart, timeStart);
			String address = getIntent().getExtras().getString(
					INTENT_EXTRA_ADDRESS);
			distance = getIntent().getExtras().getDouble(INTENT_EXTRA_DISTANCE);
		}

		// TODO: alle daten eingetragen, was damit machen
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.animateCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition.Builder().target(
						new LatLng(location.getLatitude(), location
								.getLongitude())).build()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	// Klasse um asynchron auf eine GPS-Position zu warten
	private class GetPosition extends AsyncTask<ProgressDialog, Void, Location> {

		@Override
		protected Location doInBackground(ProgressDialog... params) {

			// Location Services aktivieren
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Intent intent = new Intent(getApplicationContext(),
					MapViewActivity.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);
			String provider = locationManager.getBestProvider(new Criteria(),
					true);
			locationManager.requestLocationUpdates(provider, 0, 0,
					pendingIntent);

			// auf Signal warten
			Location location = null;
			while (location == null) {
				location = locationManager.getLastKnownLocation(provider);
			}
			params[0].dismiss();
			return location;
		}
	}

}
