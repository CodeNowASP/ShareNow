package de.tum.asp.sharenow.app;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.dialogs.DatePickerFragment;
import de.tum.asp.sharenow.dialogs.TimePickerFragment;
import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void register(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	/**
	 * Login basierend auf E-Mail und Passwort durchführen.
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
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

	/**
	 * Bild aus der Galerie auswählen.
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void selectImage(View view) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// http://viralpatel.net/blogs/pick-image-from-galary-android-app/
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0 && resultCode == Activity.RESULT_OK
				&& data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			Log.d("DEBUG", "" + "cursor: " + (cursor == null));
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			ImageView imageView = (ImageView) findViewById(R.id.rentout_image);
			imageView.setImageURI(Uri.parse(picturePath));
		}
	}

	/**
	 * Bild auf Standard zurücksetzen.
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void resetImage(View view) {
		ImageView imageView = (ImageView) findViewById(R.id.rentout_image);
		imageView.setImageResource(R.drawable.no_picture);
	}

	/**
	 * Zeit auswählen.
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void pickTime(View view) {
		DialogFragment newFragment = new TimePickerFragment((TextView) view);
		newFragment.show(getFragmentManager(), "timePicker");
	}

	/**
	 * Datum auswählen
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void pickDate(View view) {
		DialogFragment newFragment = new DatePickerFragment((TextView) view);
		newFragment.show(getFragmentManager(), "datePicker");
	}

	/**
	 * Parkplatz vermieten.
	 * 
	 * @param view
	 *            Element, von dem aus die Methode aufgerufen wird.
	 */
	public void rentOut(View view) {
		LocalDatabase db = new LocalDatabase(getApplicationContext());
		SessionManager sm = new SessionManager(getApplicationContext());
		
		if(sm.loggedIn()) {

		// Parkplatz erstellen
		Place place = new Place();
		place.setUserId(sm.getId());
		EditText address = (EditText) findViewById(R.id.rentout_address_input);
		place.setAddress(address.getText().toString());
		EditText description = (EditText) findViewById(R.id.rentout_description_input);
		place.setDescription(description.getText().toString());

		// Bild zuerst in Byte Array umwandeln
		ImageView image = (ImageView) findViewById(R.id.rentout_image);
		image.setDrawingCacheEnabled(true);
		image.buildDrawingCache();
		Bitmap bm = image.getDrawingCache();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		place.setImage(byteArray);

		EditText price = (EditText) findViewById(R.id.rentout_price_input);
		String pricePerHour = price.getText().toString().replace(",", ".");
		place.setPricePerHour(Double.parseDouble(pricePerHour));
		db.insert(place);

		// Slot erstellen
		Slot slot = new Slot();
		slot.setPlaceId(place.getId());
		slot.setReserved(false);

		// Datum & Uhrzeit aus Strings auslesen
		TextView dateView = (TextView) findViewById(R.id.rentout_slot_begin_date);
		TextView timeView = (TextView) findViewById(R.id.rentout_slot_begin_time);
		Date date = null;
		try {
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
					.parse(dateView.getText().toString() + " "
							+ timeView.getText().toString());
		} catch (ParseException e) {
		}
		slot.setDateStart(new Timestamp(date.getTime()));
		dateView = (TextView) findViewById(R.id.rentout_slot_end_date);
		timeView = (TextView) findViewById(R.id.rentout_slot_end_time);
		try {
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
					.parse(dateView.getText().toString() + " "
							+ timeView.getText().toString());
		} catch (ParseException e) {
		}
		slot.setDateEnd(new Timestamp(date.getTime()));
		RadioButton weekly = (RadioButton) findViewById(R.id.rentout_slot_regularly_button);
		slot.setWeekly(weekly.isChecked());
		db.insert(slot);
		// TODO: zu Parkplatzanzeige wechseln wenn implementiert
		mViewPager.setCurrentItem(3);
		} else {
			// Nutzer nicht eingeloggt
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.rentout_popup_text);
			builder.setPositiveButton(R.string.rentout_popup_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
}
