package de.tum.asp.sharenow.app;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.tasks.GetLocationTask;
import de.tum.asp.sharenow.tasks.GetPositionTask;
import de.tum.asp.sharenow.util.DateConverter;
import de.tum.asp.sharenow.util.InfoWindowClickListener;
import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

	private static double BASIC_DISTANCE_IN_KM = 2.0;

	private LocalDatabase db;
	private GoogleMap map;

	private double distance;
	private Timestamp start, end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

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
			GetPositionTask gp = new GetPositionTask(this);
			gp.execute();

		} else if (getIntent().hasExtra(INTENT_EXTRA_ADDRESS)) {
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
			end = dateConverter.fromString(dateEnd, timeEnd);
			String address = getIntent().getExtras().getString(
					INTENT_EXTRA_ADDRESS);
			distance = getIntent().getExtras().getDouble(INTENT_EXTRA_DISTANCE);

			// Adresse bestimmen
			GetLocationTask gl = new GetLocationTask(this);
			gl.execute(address);
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		db = new LocalDatabase(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	/**
	 * Parkplätze suchen und auf Karte darstellen.
	 * 
	 * @param location
	 *            Position, bei der die Parkplätze gesucht werden.
	 */
	public void findSpots(Location location) {
		HashMap<Marker, Place> markers = new HashMap<Marker, Place>();
		Builder boundsBuilder = new LatLngBounds.Builder();
		// Postion auf Karte anzeigen
		MarkerOptions mOpt = new MarkerOptions().position(
				new LatLng(location.getLatitude(), location.getLongitude()))
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		map.addMarker(mOpt);
		boundsBuilder.include(mOpt.getPosition());
		// alle Parkplätze durchgehen
		Geocoder coder = new Geocoder(this);
		for (Place place : db.getPlaces()) {
			// Adresse in Koordinaten umwandeln
			List<Address> addresses = null;
			try {
				addresses = coder.getFromLocationName(place.getAddress(), 1);
			} catch (IOException e) {
			}
			if (addresses != null) {
				// Überprüfen ob Parkplatz innerhalb Distanz & frei
				Location placeLocation = new Location("");
				double lat = addresses.get(0).getLatitude();
				double lon = addresses.get(0).getLongitude();
				placeLocation.setLatitude(lat);
				placeLocation.setLongitude(lon);
				double distToPlace = placeLocation.distanceTo(location);
				boolean hasFreeSlot = false;
				boolean slotReserved = false;
				for (Slot slot : db.getSlots(place.getId())) {
					boolean currentSlotHasFreeSlot = slot.getDateStart()
							.before(start);
					currentSlotHasFreeSlot = currentSlotHasFreeSlot
							&& slot.getDateEnd().after(end);
					hasFreeSlot = currentSlotHasFreeSlot || hasFreeSlot;
					if (slot.isReserved()) {
						slotReserved = slot.overlapsWith(start, end);
					}
				}
				if ((distToPlace / 1000) <= distance && hasFreeSlot) {
					// Parkplatz frei & innerhalb Distanz, anzeigen
					MarkerOptions marker;
					if (!slotReserved) {
						marker = new MarkerOptions()
								.position(new LatLng(lat, lon))
								.title(place.getPricePerHour() + " €/h")
								.snippet(place.getDescription())
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					} else {
						// Slot reserviert, andere Farbe
						marker = new MarkerOptions()
								.position(new LatLng(lat, lon))
								.title(getText(R.string.map_in_use_title)
										.toString())
								.snippet(
										getText(R.string.map_in_use_text)
												.toString())
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
					}
					Marker m = map.addMarker(marker);
					markers.put(m, place);
					boundsBuilder.include(marker.getPosition());
				}
			}
		}
		// Karte anpassen falls Parkplätze gefunden
		if (!markers.isEmpty()) {
			map.moveCamera(CameraUpdateFactory.newLatLngBounds(
					boundsBuilder.build(), 50));
			// Listener für Klicks auf Marker Fenster
			map.setOnInfoWindowClickListener(new InfoWindowClickListener(this,
					markers, start, end));
		} else {
			map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location
					.getLatitude(), location.getLongitude())));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.map_nothing_found_popup_text);
			builder.setPositiveButton(R.string.map_nothing_found_popup_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
}
