package de.tum.asp.sharenow.util;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Diese Klasse kombiniert einen Marker mit einem Parkplatz.
 */
public class CustomMarker {

	private MarkerOptions markerOptions;
	private Place place;

	/**
	 * @return Der Marker.
	 */
	public MarkerOptions getMarkerOptions() {
		return markerOptions;
	}

	/**
	 * @param marker
	 *            Der neue Marker.
	 */
	public void setMarker(MarkerOptions markerOptions) {
		this.markerOptions = markerOptions;
	}

	/**
	 * @return Der Parkplatz.
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            Der neue Parkplat.
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

}
