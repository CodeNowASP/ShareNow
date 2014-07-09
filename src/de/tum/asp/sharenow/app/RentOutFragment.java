package de.tum.asp.sharenow.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import de.tum.asp.sharenow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * In diesem Fragment kann ein Nutzer einen Parkplatz vermieten.
 */
public class RentOutFragment extends Fragment {

	// Standard Slot-Dauer
	private final int BASIC_INCREMENT = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_rent_out, container,
				false);
		setDateAndTime(rootView);
		return rootView;
	}

	// Zeit und Datum auf aktuelles Datum setzen und Größe anpassen
	private void setDateAndTime(View view) {
		final Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Europe/Berlin"));

		// Textgröße der Eingabefelder holen
		EditText editText = (EditText) view
				.findViewById(R.id.rentout_address_input);
		float px = editText.getTextSize();
		float scaledDensity = getActivity().getApplicationContext()
				.getResources().getDisplayMetrics().scaledDensity;
		float sp = px / scaledDensity;

		// aktuelles Datum als Start setzen
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.getDefault());
		sdf.setCalendar(calendar);
		TextView beginDate = (TextView) view
				.findViewById(R.id.rentout_slot_begin_date);
		beginDate.setTextSize(sp);
		beginDate.setText(sdf.format(calendar.getTime()));

		sdf.applyPattern("HH:mm");
		TextView beginTime = (TextView) view
				.findViewById(R.id.rentout_slot_begin_time);
		beginTime.setTextSize(sp);
		beginTime.setText(sdf.format(calendar.getTime()));

		// Standard Anzahl Stunden addieren & End-Datum setzen
		calendar.add(Calendar.HOUR, BASIC_INCREMENT);
		sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
		sdf.setCalendar(calendar);
		TextView endDate = (TextView) view
				.findViewById(R.id.rentout_slot_end_date);
		endDate.setTextSize(sp);
		endDate.setText(sdf.format(calendar.getTime()));

		sdf.applyPattern("HH:mm");
		TextView endTime = (TextView) view
				.findViewById(R.id.rentout_slot_end_time);
		endTime.setTextSize(sp);
		endTime.setText(sdf.format(calendar.getTime()));
	}
}
