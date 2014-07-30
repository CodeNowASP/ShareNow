package de.tum.asp.sharenow.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Listener für den Buchung bestätigen Button.
 */
public class BookingConfirmedClickListener implements
		DialogInterface.OnClickListener {

	private Context context;
	private String address;

	@Override
	public void onClick(DialogInterface dialog, int which) {
		ClipboardManager clipboard = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("address", address);
		clipboard.setPrimaryClip(clip);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Context der Anwendung.
	 * @param address
	 *            Adresse des gebuchten Parkplatzes.
	 */
	public BookingConfirmedClickListener(Context context, String address) {
		super();
		this.context = context;
		this.address = address;
	}

}
