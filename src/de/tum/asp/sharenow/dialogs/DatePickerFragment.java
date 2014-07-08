package de.tum.asp.sharenow.dialogs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Fragment, um ein bestimmtes Datum auszuw�hlen.
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
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// neue Instanz zur�ckgeben
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Zeit wurde gew�hlt, Text anpassen
		textView.setText(day + "." + month + "." + year);
	}
}
