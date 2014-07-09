package de.tum.asp.sharenow.dialogs;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
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
		final Calendar c = Calendar.getInstance(TimeZone
				.getTimeZone("Europe/Berlin"));
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// neue Instanz zurückgeben
		return new TimePickerDialog(getActivity(), this, hour, minute, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Zeit wurde gewählt, Text anpassen
		DecimalFormat df = new DecimalFormat("00");
		textView.setText(df.format(hourOfDay) + ":" + df.format(minute));
	}
}
