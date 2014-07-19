package de.tum.asp.sharenow.app;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import de.tum.asp.sharenow.R;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

/**
 * Diese Seite enthält eine Google Maps Karte, auf der gefunden Parkplätze
 * angezeigt und gebucht werden können.
 */
public class MapViewActivity extends FragmentActivity {

	private GoogleMap map;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	}

	@Override
	protected void onStart() {
		super.onStart();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		map.animateCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition.Builder().target(
						new LatLng(location.getLatitude(), location
								.getLongitude())).build()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
