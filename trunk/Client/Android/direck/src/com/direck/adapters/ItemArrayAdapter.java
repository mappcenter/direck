package com.direck.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;











import com.direck.R;
import com.direck.interfaces.ItemView;
import com.direck.models.Item;
import com.direck.utils.util;


public class ItemArrayAdapter extends ArrayAdapter<Item> implements Filterable{
	Context context;
	Item item;
	int resource;
	private ArrayList<Item> items;
	private ArrayList<Item> originalItems;
	ImageView imgLogo;
	TextView txtName;
	TextView txtAddress;

	public ItemArrayAdapter(Context context, int textViewResourceId,
			ArrayList<Item> itemlist) {

		super(context, textViewResourceId, R.layout.list_item, itemlist);

		this.context = context;
		this.resource = textViewResourceId;
		this.items = itemlist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ItemView holder = null;
		
		if (convertView ==null){
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_item, null);
			holder = new ItemView();
			holder.txtName = (TextView) convertView.findViewById(R.id.txtItemName);
			holder.txtAddress = (TextView) convertView.findViewById(R.id.txtItemAddress);
			holder.txtShareBy = (TextView) convertView.findViewById(R.id.txtShareBy);
			holder.txtCreateDate = (TextView) convertView.findViewById(R.id.txtCreateDate);
			holder.txtShareByLabel= (TextView) convertView.findViewById(R.id.txtShareByLabel);
			holder.imgNew = (ImageView) convertView.findViewById(R.id.imgNew);
			holder.imgPlay = (ImageView) convertView.findViewById(R.id.imgPlay);
			holder.imgView =(ImageView) convertView.findViewById(R.id.logo);
			convertView.setTag(holder);
			convertView.setBackgroundResource(R.drawable.item_list);
		}else {
			holder  = (ItemView) convertView.getTag();
		}
		try {
			Item item = (Item) items.get(position);
			holder.txtName.setText(item.getName());
			holder.txtAddress.setText(item.getAddress());
			holder.txtShareBy.setText(item.getShareby());
			holder.txtCreateDate.setText(item.getCreateDate());
			
			int typ = item.getType();

			if (typ == util.ShareType) {
				holder.imgView.setImageResource(R.drawable.share);
				//convertView.setBackgroundColor(convertView.getResources().getColor(R.color.shareBackgroundColor));
			} else if (typ == util.BeSharedType) {
				holder.imgView.setImageResource(R.drawable.beshared);
				//convertView.setBackgroundColor(convertView.getResources().getColor(R.color.beSharedBackgroundColor));
			} else {
				holder.imgView.setImageResource(R.drawable.bookmark);
				//convertView.setBackgroundColor(convertView.getResources().getColor(R.color.bookmarkBackgroundColor));
			}
			
			boolean isRead = item.isViewStatus();
			if (isRead) {
				holder.imgNew.setVisibility(View.INVISIBLE);
				holder.imgPlay.setVisibility(View.VISIBLE);
			}
			else {
				holder.imgNew.setVisibility(View.VISIBLE);
				holder.imgPlay.setVisibility(View.INVISIBLE);
			
			}
			holder.imgView.setTag(item);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
			
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).hashCode();
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				items = (ArrayList<Item>) results.values; // has
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
				ArrayList<Item> FilteredArrList = new ArrayList<Item>();

				if (originalItems == null) {
					originalItems = new ArrayList<Item>(items); // saves
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
					results.count = originalItems.size();
					results.values = originalItems;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < originalItems.size(); i++) {
						Item data = originalItems.get(i);
						if (constraint.toString().toLowerCase().equals("all")){
							FilteredArrList = originalItems;
						}else {
							int typ= data.getType();
							if (getItemType(constraint) == typ){
								FilteredArrList.add(data);
							}
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
	
	public int getItemType(CharSequence constraint){
		if (((String) constraint).toLowerCase().equals("share")) return util.ShareType;
		if (((String) constraint).toLowerCase().equals("be shared")) return util.BeSharedType;
		else return util.BookmarkType;
	}

}
