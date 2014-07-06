package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * In diesem Fragment kann ein Nutzer nach Parkplätzen suchen.
 */
public class SearchFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);
		return rootView;
	}

}
