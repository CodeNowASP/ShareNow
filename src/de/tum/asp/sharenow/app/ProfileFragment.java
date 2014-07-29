package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Dieses Fragment bietet entweder eine Möglichkeit zum Login oder stellt das
 * Profil eines eingeloggten Nutzers dar.
 */
public class ProfileFragment extends Fragment {

	private LocalDatabase db;
	private SessionManager sm;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container,
				false);
		db = new LocalDatabase(getActivity());
		sm = new SessionManager(getActivity());
		user = db.getUser(sm.getId());
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Text
		TextView tv = (TextView) getView()
				.findViewById(R.id.profile_view_title);
		String text = user.getFirstName();
		text += " " + user.getLastName();
		tv.setText(text);
		tv = (TextView) getView().findViewById(R.id.profile_view_subtitle);
		tv.setText(user.getMail());
		onResume();
	}
}
