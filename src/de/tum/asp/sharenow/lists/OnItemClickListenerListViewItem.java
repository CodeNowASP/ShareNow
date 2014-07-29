package de.tum.asp.sharenow.lists;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * Listener für die Listen im Profil.
 * 
 * Nach http://www.codeofaninja.com/2013/09/android-listview-with-adapter
 * -example.html
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {

	private ArrayAdapterItem adapter;

	/**
	 * Konstruktor.
	 * 
	 * @param adapter
	 *            Adapter der Liste.
	 */
	public OnItemClickListenerListViewItem(ArrayAdapterItem adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Context context = view.getContext();
		LocalDatabase db = new LocalDatabase(context);

		// Item löschen
		TextView textViewItem = ((TextView) view.findViewById(R.id.list_text));
		Object object = adapter.getItem(position);
		if (object instanceof Place) {
			db.deletePlace((long) textViewItem.getTag());
		} else if (object instanceof Slot) {
			db.deleteSlot((long) textViewItem.getTag());
		}
		adapter.remove(object);
		adapter.notifyDataSetChanged();
	}

}
