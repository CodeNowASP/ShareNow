package de.tum.asp.sharenow.app;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;

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
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Diese Seite enth�lt eine Google Maps Karte, auf der gefunden Parkpl�tze
 * angezeigt und gebucht werden k�nnen.
 */
public class MapViewActivity extends FragmentActivity {

	// Parameter die bei einer Suche an den Intent angeh�ngt werden
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
			// schnelle Suche ausgef�hrt
			double hours = getIntent().getExtras()
					.getDouble(INTENT_EXTRA_HOURS);
			distance = BASIC_DISTANCE_IN_KM;
			long currentTime = System.currentTimeMillis();
			start = new Timestamp(currentTime);
			end = new Timestamp((long) (currentTime + hours * 3600 * 1000));

			// aktuelle Position bestimmen
			GetPositionTask gp = new GetPositionTask(this);
			gp.execute();

		} else if (getIntent().hasExtra(INTENT_EXTRA_ADDRESS)) {
			// komplexe Suche ausgef�hrt
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

	/**
	 * Parkpl�tze suchen und auf Karte darstellen.
	 * 
	 * @param location
	 *            Position, bei der die Parkpl�tze gesucht werden.
	 */
	public void findSpots(Location location) {
		HashMap<Marker, Place> markers = new HashMap<Marker, Place>();
		Builder boundsBuilder = new LatLngBounds.Builder();
		// Postion auf Karte anzeigen
		MarkerOptions mOpt = new MarkerOptions().position(
				new LatLng(location.getLatitude(), location.getLongitude()))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
		map.addMarker(mOpt);
		boundsBuilder.include(mOpt.getPosition());
		// alle Parkpl�tze durchgehen
		for (Place place : db.getPlaces(-1)) {
			// �berpr�fen ob Parkplatz innerhalb Distanz & frei
			Location placeLocation = place.getLocation();
			double distToPlace = placeLocation.distanceTo(location);
			boolean hasFreeSlot = false;
			boolean slotReserved = false;
			Slot reservedSlot = null;
			for (Slot slot : db.getSlots(place.getId())) {
				boolean currentSlotHasFreeSlot = slot.getDateStart().before(
						start);
				currentSlotHasFreeSlot = currentSlotHasFreeSlot
						&& slot.getDateEnd().after(end);
				hasFreeSlot = currentSlotHasFreeSlot || hasFreeSlot;
				if (slot.isReserved()) {
					slotReserved = slot.overlapsWith(start, end);
					reservedSlot = slot;
				}
			}
			if ((distToPlace / 1000) <= distance && hasFreeSlot) {
				// Parkplatz frei & innerhalb Distanz, anzeigen
				MarkerOptions marker;
				DecimalFormat df = new DecimalFormat("#0.00");
				if (!slotReserved) {
					marker = new MarkerOptions()
							.position(
									new LatLng(placeLocation.getLatitude(),
											placeLocation.getLongitude()))
							.title(df.format(place.getPricePerHour()) + " �/h")
							.snippet(place.getDescription())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				} else {
					// Slot reserviert, andere Farbe + anderer Text
					String text;
					if (reservedSlot != null) {
						DateConverter dc = new DateConverter();
						text = getText(R.string.map_in_use_text_1).toString();
						text += " " + dc.toString(reservedSlot.getDateEnd())
								+ ".";
					} else {
						text = getText(R.string.map_in_use_text_2).toString();
					}
					marker = new MarkerOptions()
							.position(
									new LatLng(placeLocation.getLatitude(),
											placeLocation.getLongitude()))
							.title(getText(R.string.map_in_use_title)
									.toString())
							.snippet(text)
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				}
				Marker m = map.addMarker(marker);
				markers.put(m, place);
				boundsBuilder.include(marker.getPosition());
			}
		}
		// Karte anpassen falls Parkpl�tze gefunden
		if (!markers.isEmpty()) {
			map.moveCamera(CameraUpdateFactory.newLatLngBounds(
					boundsBuilder.build(), 50));
			// Listener f�r Klicks auf Marker Fenster
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
