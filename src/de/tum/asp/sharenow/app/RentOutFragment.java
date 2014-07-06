package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * In diesem Fragment kann ein Nutzer einen Parkplatz vermieten.
 */
public class RentOutFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_rent_out, container,
				false);
		return rootView;
	}

}
