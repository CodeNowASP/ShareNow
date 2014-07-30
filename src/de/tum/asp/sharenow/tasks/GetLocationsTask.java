package de.tum.asp.sharenow.tasks;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import de.tum.asp.sharenow.app.MapViewActivity;
import de.tum.asp.sharenow.util.Geocoder;

/**
 * Klasse um asynchron auf die Koordinaten einer Adresse zu warten.
 */
public class GetLocationsTask extends AsyncTask<String, Void, Location> {

	private ProgressDialog dialog;
	private MapViewActivity mapViewActivity;

	public GetLocationsTask(MapViewActivity mapViewActivity) {
		this.mapViewActivity = mapViewActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mapViewActivity, "Searching",
				"Please wait...", true, true);
	}

	@Override
	protected Location doInBackground(String... params) {
		Geocoder geocoder = new Geocoder();
		return geocoder.getLocationInfo(params[0]);
	}

	@Override
	protected void onPostExecute(Location result) {
		super.onPostExecute(result);
		dialog.dismiss();
	}
}
