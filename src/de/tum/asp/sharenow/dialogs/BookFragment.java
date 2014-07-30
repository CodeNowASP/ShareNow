package de.tum.asp.sharenow.dialogs;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.app.SessionManager;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.BookingConfirmedClickListener;
import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookFragment extends DialogFragment {

	private Place place;
	private Timestamp start;
	private Timestamp end;

	public BookFragment(Place place, Timestamp start, Timestamp end) {
		this.place = place;
		this.start = start;
		this.end = end;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.book_dialog, container, false);

		// Bild
		Bitmap bm = BitmapFactory.decodeByteArray(place.getImage(), 0,
				place.getImage().length);
		ImageView image = (ImageView) v.findViewById(R.id.book_dialog_image);
		image.setImageBitmap(bm);

		// Texte
		View description = v.findViewById(R.id.book_dialog_description);
		((TextView) description).setText("\"" + place.getDescription() + "\"");
		View price = v.findViewById(R.id.book_dialog_price);
		DecimalFormat df = new DecimalFormat("#0.00");
		((TextView) price).setText(df.format(place.getPricePerHour())
				+ " € per hour");
		getDialog().setTitle(R.string.map_infowindow_title);

		// Button
		Button button = (Button) v.findViewById(R.id.book_dialog_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SessionManager sm = new SessionManager(getActivity());
				if (!sm.loggedIn()) {
					Toast.makeText(getActivity(), "Not logged in.",
							Toast.LENGTH_SHORT).show();
				} else {
					Slot slot = new Slot(place.getId(), sm.getId(), start, end,
							true, false);
					LocalDatabase db = new LocalDatabase(getActivity());
					db.insert(slot);
					BookFragment.this.dismiss();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle(R.string.book_dialog_confirmation_title);
					builder.setMessage(getText(R.string.book_dialog_confirmation_text_1)
							+ place.getAddress()
							+ getText(R.string.book_dialog_confirmation_text_2));
					builder.setPositiveButton(
							R.string.book_dialog_confirmation_ok,
							new BookingConfirmedClickListener(getActivity(),
									place.getAddress()));
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});

		return v;
	}

}
