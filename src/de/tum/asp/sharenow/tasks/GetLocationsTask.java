package de.tum.asp.sharenow.tasks;

import android.location.Location;
import android.os.AsyncTask;
import de.tum.asp.sharenow.util.Geocoder;

/**
 * Klasse um asynchron auf die Koordinaten einer Adresse zu warten.
 */
public class GetLocationsTask extends AsyncTask<String, Void, Location> {

	@Override
	protected Location doInBackground(String... params) {
		Geocoder geocoder = new Geocoder();
		return geocoder.getLocationInfo(params[0]);
	}
}
