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

	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new SearchFragment();
		case 1:
			return new RentOutFragment();
		default:
			SessionManager sessionManager = new SessionManager(context);
			if (!sessionManager.loggedIn()) {
				return new LoginFragment();
			} else {
				return new ProfileFragment();
			}
		}
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
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
