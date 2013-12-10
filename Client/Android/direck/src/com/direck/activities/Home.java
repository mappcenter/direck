package com.direck.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.R;
import com.direck.adapters.ItemArrayAdapter;
import com.direck.models.Item;
import com.direck.sync.sync;
import com.direck.utils.PullToRefreshListView;
import com.direck.utils.PullToRefreshListView.OnRefreshListener;
import com.direck.utils.util;
import com.parse.ParseObject;
import com.parse.ParseAnalytics;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Home extends Activity {

	static private ArrayList<Item> items;
	static private ItemArrayAdapter arr;
	public PullToRefreshListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		

		loadFilter();
		loadItemList();
		//sync.syncContactToLocalDB(this);

		// Show the Up button in the action bar.
		// setupActionBar();
	}

	private void loadFilter() {
		Spinner spinner = (Spinner) findViewById(R.id.typeFilter);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.ItemType, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// An item was selected. You can retrieve the selected item
				// using
				// parent.getItemAtPosition(pos)
				if (arr !=null) arr.getFilter()
						.filter(parent.getItemAtPosition(pos).toString());
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});
	}

	private void loadItemList() {

		try {
			items = new ArrayList<Item>();

			setItem();
			lv = (PullToRefreshListView) findViewById(R.id.listItem);
			arr = new ItemArrayAdapter(this, R.layout.list_item, items);
			lv.setAdapter(arr);
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Item itm = (Item) items.get(position);
					itm.setViewStatus(true);

					Intent intent = new Intent(getApplicationContext(),
							ItemDetails.class);
					intent.putExtra("selectedItem", itm);
					startActivity(intent);
				}

			});
			lv.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					// Your code to refresh the list contents

					// ...
					loadItemList();
					arr.notifyDataSetChanged();

					// Make sure you call listView.onRefreshComplete()
					// when the loading is done. This can be done from here or
					// any
					// other place, like on a broadcast receive from your
					// loading
					// service or the onPostExecute of your AsyncTask.

					lv.onRefreshComplete();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(e.getMessage(), this);
		}

	}

	public void shareNew(View view) {
		Intent intent = new Intent(this, ShareItem.class);
		startActivity(intent);

	}

	public void setItem() {
		items = new ArrayList<Item>();
		String accountID = util.getPrefAccountID(this);
		if (accountID.equals("0")) {
			Toast.makeText(getApplicationContext(), "UID is not available",
					Toast.LENGTH_LONG).show();
			return;
		}else Toast.makeText(getApplicationContext(), "UID:" + accountID,
				Toast.LENGTH_LONG).show();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "get-list-point"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("accountid", accountID));

			JSONObject jUser = util.getJSONfromURL(util.hostURL, "GET", params);
			//String jsonS ="{\"ErrorCode\":0,\"Message\":\"Get List Successful\",\"Data\":[{\"Id\":\"1\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2e2e2 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"122223\",\"PointLocY\":\"2223456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386407342\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"2\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"123\",\"PointLocY\":\"3456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386553726\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"3\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"123\",\"PointLocY\":\"3456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386553726\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"5\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"7\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"8\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"9\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"11\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"13\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"14\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"15\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"17\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"}]}";
			//JSONObject jUser = new JSONObject(jsonS);
			int errorCode = jUser.getInt("ErrorCode");
			String msg = jUser.getString("Message");
			JSONArray json = jUser.getJSONArray("Data");

			// success : 0: success_no_msg or 1: success_msg

			if (errorCode <= 1) {
				for (int i = 0; i <= json.length(); i++) {
					JSONObject obj = json.getJSONObject(i);
					Item itm = new Item(obj);
					items.add(itm);
				}
			}
		} catch (JSONException e) {
			// TODO: handle exception
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy(); // Always call the superclass

		// Stop method tracing that the activity started during onCreate()
		android.os.Debug.stopMethodTracing();
	}

	@Override
	public void onPause() {
		super.onPause(); // Always call the superclass method first

	}

	@Override
	public void onResume() {
		super.onResume(); // Always call the superclass method first
		if (arr !=null)	arr.notifyDataSetChanged();
	}

	// save state information
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	// call after onCreate only if InstanceState are saved
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
