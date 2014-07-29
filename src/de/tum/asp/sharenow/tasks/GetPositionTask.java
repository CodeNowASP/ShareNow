package de.tum.asp.sharenow.tasks;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import de.tum.asp.sharenow.app.MapViewActivity;

/**
 * Klasse um asynchron auf eine GPS-Position zu warten
 */
public class GetPositionTask extends AsyncTask<Void, Void, Location> {

	private ProgressDialog dialog;
	MapViewActivity mapViewActivity;

	public GetPositionTask(MapViewActivity mapViewActivity) {
		this.mapViewActivity = mapViewActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mapViewActivity, "Retrieving location",
				"Please wait...", true, true);
	}

	@Override
	protected Location doInBackground(Void... params) {

		// Location Services aktivieren
		LocationManager locationManager = (LocationManager) mapViewActivity
				.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(mapViewActivity, MapViewActivity.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				mapViewActivity, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		String provider = locationManager.getBestProvider(new Criteria(), true);
		locationManager.requestLocationUpdates(provider, 0, 0, pendingIntent);

		// auf Signal warten
		Location location = null;
		while (location == null) {
			location = locationManager.getLastKnownLocation(provider);
		}
		return location;

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
