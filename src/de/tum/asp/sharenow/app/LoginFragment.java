package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Dieses Fragment bietet entweder eine M�glichkeit zum Login oder stellt das
 * Profil eines eingeloggten Nutzers dar.
 */
public class LoginFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);
		return rootView;
	}
}
