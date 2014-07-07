package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Hauptseite. Alle anderen Seiten werden durch diese als Fragmente aufgerufen.
 */
public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private SessionManager sessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sessionManager = new SessionManager(getApplicationContext());

		// ActionBar erstellen und Tabs als Navigation wählen
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adapter zur Fragment-Verwaltung erstellen & mit ViewPager verknüpfen
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), this.getApplicationContext());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Swipe-Navigation ermöglichen
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// Tabs zur Action Bar hinzufügen
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (sessionManager.loggedIn()) {
			// eingeloggt: Menü personalisieren
			menu.clear();
			getMenuInflater().inflate(R.menu.main, menu);
			menu.getItem(0).setTitle(
					getText(R.string.actionbar_menu_greeting) + ", "
							+ sessionManager.getName() + "!");
		} else {
			// nicht eingeloggt: Link zu Login bieten
			menu.clear();
			getMenuInflater().inflate(R.menu.main_collapsed, menu);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar_menu_title:
			// Menü wurde angeklickt
			if (!sessionManager.loggedIn()) {
				mViewPager.setAdapter(mSectionsPagerAdapter);
				mViewPager.setCurrentItem(2);
			}
			return true;
		case R.id.actionbar_menu_logout:
			// im Menü wurde Logout angeklickt
			if (sessionManager.loggedIn()) {
				sessionManager.logout();
				invalidateOptionsMenu();
				mViewPager.setAdapter(mSectionsPagerAdapter);
				mViewPager.setCurrentItem(2);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * Zur Register Aktivität wechseln.
	 * 
	 * @param view
	 *            View, von der aus gewechselt werden soll.
	 */
	public void register(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	/**
	 * Login basierend auf E-Mail und Passwort durchführen.
	 * 
	 * @param view
	 *            View, von der aus die Methode aufgerufen wird.
	 */
	public void login(View view) {
		EditText mail = (EditText) findViewById(R.id.profile_login_mail_input);
		EditText pw = (EditText) findViewById(R.id.profile_login_password_input);
		if (sessionManager.login(mail.getText().toString(), pw.getText()
				.toString())) {
			// Login erfolgreich
			invalidateOptionsMenu();
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setCurrentItem(2);
		} else {
			// Login nicht erfolgreich, Meldung ausgeben
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.profile_login_popup_text);
			builder.setPositiveButton(R.string.profile_login_popup_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
}
