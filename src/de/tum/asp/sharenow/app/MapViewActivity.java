package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Diese Seite enth�lt eine Google Maps Karte, auf der gefunden Parkpl�tze
 * angezeigt und gebucht werden k�nnen.
 */
public class MapViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
