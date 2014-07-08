package de.tum.asp.sharenow.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Fragment, um eine bestimmte Zeit auszuwählen.
 * 
 * http://developer.android.com/guide/topics/ui/controls/pickers.html
 */
public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private TextView textView;

	/**
	 * Konstruktor.
	 * 
	 * @param textView
	 *            TextView, von der aus die Methode aufgerufen wird.
	 */
	public TimePickerFragment(TextView textView) {
		super();
		this.textView = textView;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Standard Werte
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// neue Instanz zurückgeben
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Zeit wurde gewählt, Text anpassen
		textView.setText(hourOfDay + ":" + minute);
	}
}
