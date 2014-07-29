package de.tum.asp.sharenow.lists;

import java.util.ArrayList;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.DateConverter;
import de.tum.asp.sharenow.util.Place;
import de.tum.asp.sharenow.util.Slot;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter für die Listen im Profil
 * 
 * Entnommen aus
 * http://www.codeofaninja.com/2013/09/android-listview-with-adapter-example.
 * html
 */
public class ArrayAdapterItem extends ArrayAdapter<Object> {

	private Context mContext;
	private int layoutResourceId;
	private ArrayList<Object> objects = null;

	public ArrayAdapterItem(Context mContext, int layoutResourceId,
			ArrayList<Object> objects) {
		super(mContext, layoutResourceId, objects);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/*
		 * The convertView argument is essentially a "ScrapView" as described is
		 * Lucas post
		 * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
		 * It will have a non-null value when ListView is asking you recycle the
		 * row layout. So, when convertView is not null, you should simply
		 * update its contents instead of inflating a new row layout.
		 */
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		// object item based on the position
		Object object = objects.get(position);
		LocalDatabase db = new LocalDatabase(mContext);
		DateConverter dc = new DateConverter();

		// get the TextView and then set the text (item name) and tag (item ID)
		// values
		if (object instanceof Place) {
			Place place = (Place) object;
			TextView textViewItem = (TextView) convertView
					.findViewById(R.id.list_text);
			String text = place.getAddress();
			textViewItem.setTag(place.getId());
			for (Slot slot : db.getSlots(place.getId())) {
				if (!slot.isReserved()) {
					String start = dc.toString(slot.getDateStart());
					String end = dc.toString(slot.getDateEnd());
					text += " (" + start + " - " + end + ")";
				}
			}
			textViewItem.setText(text);
		} else if (object instanceof Slot) {
			Slot slot = (Slot) object;
			TextView textViewItem = (TextView) convertView
					.findViewById(R.id.list_text);
			String address = db.getPlace(slot.getPlaceId()).getAddress();
			String start = dc.toString(slot.getDateStart());
			String end = dc.toString(slot.getDateEnd());
			textViewItem.setText(address + " (" + start + " - " + end + ")");
			textViewItem.setTag(slot.getId());
		}

		return convertView;

	}
}
