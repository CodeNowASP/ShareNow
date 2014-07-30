package de.tum.asp.sharenow.tasks;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import de.tum.asp.sharenow.app.MapViewActivity;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.Geocoder;
import de.tum.asp.sharenow.util.Place;

/**
 * Klasse um asynchron auf die Koordinaten einer Adresse zu warten.
 */
public class GetLocationTask extends AsyncTask<String, Void, Location> {

	private ProgressDialog dialog;
	private MapViewActivity mapViewActivity;

	public GetLocationTask(MapViewActivity mapViewActivity) {
		this.mapViewActivity = mapViewActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mapViewActivity, "Retrieving Location",
				"Please wait...", true, true);
	}

	@Override
	protected Location doInBackground(String... params) {
		Geocoder geocoder = new Geocoder();
		LocalDatabase db = new LocalDatabase(mapViewActivity);
		for (Place place : db.getPlaces(-1)) {
			if (place.getLocationLat() == 0 || place.getLocationLong() == 0) {
				Location l = geocoder.getLocationInfo(place.getAddress());
				place.setLocationLat(l.getLatitude());
				place.setLocationLong(l.getLongitude());
				db.update(place);
			}
		}
		return geocoder.getLocationInfo(params[0]);
	}

	@Override
	protected void onPostExecute(Location result) {
		super.onPostExecute(result);
		dialog.dismiss();
		if (result != null) {
			mapViewActivity.findSpots(result);
		}
	}

}
