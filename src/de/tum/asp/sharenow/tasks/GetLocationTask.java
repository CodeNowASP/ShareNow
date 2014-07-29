package de.tum.asp.sharenow.tasks;

import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import de.tum.asp.sharenow.app.MapViewActivity;

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

		// Koordinaten aus Adresse holen
		Geocoder coder = new Geocoder(mapViewActivity);
		List<Address> addresses = null;
		Location placeLocation = null;
		try {
			addresses = coder.getFromLocationName(params[0], 1);
		} catch (IOException e) {
		}
		if (addresses != null) {
			placeLocation = new Location("");
			double lat = addresses.get(0).getLatitude();
			double lon = addresses.get(0).getLongitude();
			placeLocation.setLatitude(lat);
			placeLocation.setLongitude(lon);
		}
		return placeLocation;
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
