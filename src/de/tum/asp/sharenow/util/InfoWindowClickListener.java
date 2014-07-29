package de.tum.asp.sharenow.util;

import java.sql.Timestamp;
import java.util.HashMap;

import de.tum.asp.sharenow.R;
import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.Marker;

import de.tum.asp.sharenow.dialogs.BookFragment;

/**
 * Listener für Klicks auf das Info Fenster auf der Karte.
 */
public class InfoWindowClickListener implements OnInfoWindowClickListener {

	private Context context;
	private HashMap<Marker, Place> map;
	private Timestamp start;
	private Timestamp end;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Kontext der Anwendung.
	 */
	public InfoWindowClickListener(Context context, HashMap<Marker, Place> map,
			Timestamp start, Timestamp end) {
		super();
		this.context = context;
		this.map = map;
		this.start = start;
		this.end = end;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		marker.hideInfoWindow();
		if (!marker.getTitle().equals(
				context.getText(R.string.map_in_use_title).toString())) {
			BookFragment newFragment = new BookFragment(map.get(marker), start,
					end);
			newFragment.show(((Activity) context).getFragmentManager(),
					"dialog");
		}
	}

}
