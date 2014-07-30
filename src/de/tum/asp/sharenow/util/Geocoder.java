package de.tum.asp.sharenow.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

/**
 * Klasse um die Koordinaten einer Adresse von der Google Maps API abzufragen.
 * 
 * http://stackoverflow.com/questions/15182853/android-geocoder-
 * getfromlocationname-always-returns-null
 */
public class Geocoder {

	/**
	 * Koordinaten per HTTP Request abfragen.
	 * 
	 * @param address
	 *            Adresse, deren Koordinaten abgefragt werden.
	 * @return Die Koordinaten.
	 */
	public Location getLocationInfo(String address) {

		// Adresse codieren
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
		}

		// Anfrage senden
		HttpGet httpGet = new HttpGet(
				"http://maps.google.com/maps/api/geocode/json?address="
						+ address + "&sensor=true");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		// Antwort auswerten
		Location loc = null;
		try {
			JSONObject jsonObject = new JSONObject(stringBuilder.toString());
			JSONObject result = jsonObject.getJSONArray("results")
					.getJSONObject(0);
			JSONObject location = result.getJSONObject("geometry")
					.getJSONObject("location");
			loc = new Location("");
			loc.setLatitude(location.getDouble("lat"));
			loc.setLongitude(location.getDouble("lng"));
		} catch (JSONException e) {
		}

		return loc;
	}

}
