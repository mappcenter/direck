package com.direck.adapters;

import java.util.ArrayList;
import java.util.Locale;

import com.direck.R;
import com.direck.interfaces.FriendView;
import com.direck.models.Friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class FriendArrayAdapter extends ArrayAdapter<Friend> implements
		Filterable {

	public ArrayList<Friend> friendlist;
	public ArrayList<Friend> originalfriendlist;

	public FriendArrayAdapter(Context context, int textViewResourceId,
			ArrayList<Friend> contactList) {

		super(context, textViewResourceId, R.layout.list_item, contactList);

		this.friendlist = new ArrayList<Friend>();
		this.friendlist.addAll(contactList);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FriendView holder = null;

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_friend, null);

			holder = new FriendView();
			holder.friendName = (TextView) convertView
					.findViewById(R.id.FriendName);
			holder.friendPhone = (TextView) convertView
					.findViewById(R.id.FriendPhone);
			holder.friendID = (CheckBox) convertView
					.findViewById(R.id.chkSelect);
			convertView.setTag(holder);
			convertView.setBackgroundResource(R.drawable.friend_list);

			holder.friendID.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Friend friend = (Friend) cb.getTag();
					friend.setSelected(cb.isChecked());
				}
			});
		} else {
			holder = (FriendView) convertView.getTag();
		}
		try {
			Friend friend = friendlist.get(position);
			holder.friendName.setText(friend.getContactName());
			holder.friendPhone.setText(" (" + friend.getContactNUmber() + ")");
			holder.friendID.setChecked(friend.isSelected());
			holder.friendID.setTag(friend);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return convertView;
	}

	@Override
	public int getCount() {
		return friendlist.size();
	}

	@Override
	public Friend getItem(int position) {
		return friendlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return friendlist.get(position).hashCode();
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				friendlist = (ArrayList<Friend>) results.values; // has
																	// the
																	// filtered
																	// values

				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults(); // Holds the
																// results of a
																// filtering
																// operation in
																// values
				ArrayList<Friend> FilteredArrList = new ArrayList<Friend>();

				if (originalfriendlist == null) {
					originalfriendlist = new ArrayList<Friend>(friendlist); // saves
																			// the
																			// original
																			// data
																			// in
																			// mOriginalValues
				}

				/********
				 * 
				 * If constraint(CharSequence that is received) is null returns
				 * the mOriginalValues(Original) values else does the Filtering
				 * and returns FilteredArrList(Filtered)
				 * 
				 ********/
				if (constraint == null || constraint.length() == 0) {

					// set the Original result to return
					results.count = originalfriendlist.size();
					results.values = originalfriendlist;
				} else {
					constraint = constraint.toString().toLowerCase(Locale.US);
					for (int i = 0; i < originalfriendlist.size(); i++) {
						Friend data = originalfriendlist.get(i);
						if (data.getContactName().toLowerCase(Locale.US)
								.contains(constraint.toString())) {
							FilteredArrList.add(data);
						}
					}
					// set the Filtered result to return
					results.count = FilteredArrList.size();
					results.values = FilteredArrList;
				}
				return results;
			}
		};
		return filter;
	}

}
