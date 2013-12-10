package com.direck.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.R;
import com.direck.adapters.FriendArrayAdapter;
import com.direck.data.dao.DAOContact;
import com.direck.models.Friend;
import com.direck.models.Item;
import com.direck.models.ItemDetail;
import com.direck.utils.util;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FriendList extends Activity {

	private DAOContact datasource;
	private FriendArrayAdapter friendAdapter;
	private Item ItmInfo;
	String typeShare = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		Intent in = getIntent();

		// String itemID="0";
		if (in != null) {
			typeShare = in.getStringExtra("typeShare");
			ItmInfo = in.getParcelableExtra("Item");

		}

		// List<contact> values = datasource.getAllContacts();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView

		// Generate list View from ArrayList
		displayListView();
		// setListAdapter(adapter);
	}

	public void eventHome(View view) {
		Intent intent = new Intent(getApplicationContext(), Home.class);
		startActivity(intent);
	}

	public void share(View view) {
		StringBuffer friendIDs = new StringBuffer();
		//responseText.append(" To Friend(s):\n");
		//ArrayList<String> selectedLst = new ArrayList<String>();

		ArrayList<Friend> friendlist = friendAdapter.friendlist;
		for (int i = 0; i < friendlist.size(); i++) {
			Friend friend = friendlist.get(i);
			if (friend.isSelected()) {
				//responseText.append("\n" + friend.getContactName() + "-"
				//		+ friend.getFriendID());
				//ItmInfo.setFriendID(friend.getFriendID());
				//selectedLst.add(String.valueOf(friend.getFriendID()));
				friendIDs.append(friend.getFriendID() + ",");
			}
		}
		if (friendIDs.equals("")) {
			Toast.makeText(getApplicationContext(),
					"Please select at least 1 friend to share",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			save(ItmInfo,friendIDs.toString());
		}
		/*String itemInfo = ItmInfo.getName() + "-" + ItmInfo.getAddress();
		itemInfo = itemInfo + "-" + ItmInfo.getLattitude() + ","
				+ ItmInfo.getLongitude();
		Toast.makeText(getApplicationContext(), itemInfo, Toast.LENGTH_LONG)
				.show();

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();*/

		Intent intent = new Intent(getApplicationContext(), Home.class);
		startActivity(intent);
	}

	

	private void save(Item itm,String friendList) {
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "share-point"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("accountid", String.valueOf(itm
					.getAccountID())));
			params.add(new BasicNameValuePair("friendids", friendList));
			params.add(new BasicNameValuePair("itemid", String.valueOf(itm
					.getItemID())));
			params.add(new BasicNameValuePair("pointname", itm.getName()));
			params.add(new BasicNameValuePair("address", itm.getAddress()));
			params.add(new BasicNameValuePair("locx", String.valueOf(itm
					.getLatLng())));
			params.add(new BasicNameValuePair("locy", String.valueOf(itm
					.getLongitude())));
			JSONObject jUser = util.getJSONfromURL(util.hostURL, "GET", params);

			int errorCode = jUser.getInt("ErrorCode");
			String msg = jUser.getString("Message");
			JSONObject json = jUser.getJSONObject("Data");

			// success : 0: success_no_msg or 1: success_msg

			if (errorCode <= 1) {
				
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}

	}

	private void displayListView() {

		// Array list of countries
		ArrayList<Friend> friendList = new ArrayList<Friend>();
		ListView lstFriend = (ListView) findViewById(R.id.listDBContact);

		datasource = new DAOContact(this);
		datasource.open();
		friendList = datasource.getFriendList();

		// create an ArrayAdaptar from the String Array
		friendAdapter = new FriendArrayAdapter(this, R.layout.list_friend,
				friendList);

		// Assign adapter to ListView
		lstFriend.setAdapter(friendAdapter);
		lstFriend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		EditText filter = (EditText) findViewById(R.id.filterFriend);
		filter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				friendAdapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_list, menu);
		return true;
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
