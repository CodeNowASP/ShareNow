package de.tum.asp.sharenow.app;

import java.util.Locale;

import de.tum.asp.sharenow.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private SearchFragment searchFragment;
	private RentOutFragment rentOutFragment;
	private LoginFragment loginFragment;
	private ProfileFragment profileFragment;

	/**
	 * Konstruktur.
	 * 
	 * @param fm
	 *            FragmentManager zur Verwaltung der Fragmente.
	 * @param context
	 *            Kontext der Anwendung.
	 */
	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		this.searchFragment = new SearchFragment();
		this.rentOutFragment = new RentOutFragment();
		this.loginFragment = new LoginFragment();
		this.profileFragment = new ProfileFragment();
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return searchFragment;
		case 1:
			return rentOutFragment;
		default:
			// abhängig von Login-Status unterschiedliches Fragment zurückgeben
			SessionManager sessionManager = new SessionManager(context);
			if (!sessionManager.loggedIn()) {
				return loginFragment;
			} else {
				return profileFragment;
			}
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_section2).toUpperCase(l);
		case 2:
			return context.getString(R.string.title_section3).toUpperCase(l);
		}
		return null;
	}
}
