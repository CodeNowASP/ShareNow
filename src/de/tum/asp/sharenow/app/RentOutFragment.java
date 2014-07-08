package de.tum.asp.sharenow.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
		Calendar calendar = new GregorianCalendar();

		// Textgröße der Eingabefelder holen
		EditText editText = (EditText) view
				.findViewById(R.id.rentout_address_input);
		float px = editText.getTextSize();
		float scaledDensity = getActivity().getApplicationContext()
				.getResources().getDisplayMetrics().scaledDensity;
		float sp = px / scaledDensity;

		// aktuelles Datum als Start setzen
		TextView beginDate = (TextView) view
				.findViewById(R.id.rentout_slot_begin_date);
		beginDate.setTextSize(sp);
		beginDate.setText(calendar.get(Calendar.DATE) + "."
				+ (calendar.get(Calendar.MONTH) + 1) + "."
				+ calendar.get(Calendar.YEAR));
		TextView beginTime = (TextView) view
				.findViewById(R.id.rentout_slot_begin_time);
		beginTime.setTextSize(sp);
		beginTime.setText(calendar.get(Calendar.HOUR) + ":"
				+ calendar.get(Calendar.MINUTE));

		// Standard Anzahl Stunden addieren & End-Datum setzen
		calendar.add(Calendar.HOUR, BASIC_INCREMENT);
		TextView endDate = (TextView) view
				.findViewById(R.id.rentout_slot_end_date);
		endDate.setTextSize(sp);
		endDate.setText(calendar.get(Calendar.DATE) + "."
				+ (calendar.get(Calendar.MONTH) + 1) + "."
				+ calendar.get(Calendar.YEAR));
		TextView endTime = (TextView) view
				.findViewById(R.id.rentout_slot_end_time);
		endTime.setTextSize(sp);
		endTime.setText(calendar.get(Calendar.HOUR) + ":"
				+ calendar.get(Calendar.MINUTE));
	}
}
