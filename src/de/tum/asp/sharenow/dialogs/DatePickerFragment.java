package de.tum.asp.sharenow.dialogs;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Fragment, um ein bestimmtes Datum auszuwählen.
 * 
 * http://developer.android.com/guide/topics/ui/controls/pickers.html
 */
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private TextView textView;

	/**
	 * Konstruktor.
	 * 
	 * @param textView
	 *            TextView, von der aus die Methode aufgerufen wird.
	 */
	public DatePickerFragment(TextView textView) {
		super();
		this.textView = textView;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Standard Werte
		final Calendar c = Calendar.getInstance(TimeZone
				.getTimeZone("Europe/Berlin"));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// neue Instanz zurückgeben
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Zeit wurde gewählt, Text anpassen
		DecimalFormat df = new DecimalFormat("00");
		textView.setText(df.format(day) + "." + df.format(month + 1) + "."
				+ df.format(year));
	}
}
