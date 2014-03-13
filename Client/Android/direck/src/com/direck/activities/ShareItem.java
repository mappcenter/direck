package com.direck.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.R;
import com.direck.R.layout;
import com.direck.R.menu;
import com.direck.models.Item;
import com.direck.models.ItemDetail;
import com.direck.utils.MyLocation;
import com.direck.utils.MyLocation.LocationResult;
import com.direck.utils.util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareItem extends FragmentActivity {

	private GoogleMap map;
	static LatLng location;
	private Item newItem;
	MyLocation myloc;
	String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_share_item);
		double customLat = 0;
		double customLon = 0;
		location = new LatLng(customLat, customLon);
		Intent in = getIntent();
		if (in != null) {
			customLat = in.getDoubleExtra("CustomLat", 0);
			customLon = in.getDoubleExtra("CustomLon", 0);
			location = new LatLng(customLat, customLon);
		}
		try {
			// Check status of Google Play Services
			int status = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(this);
			if (status != ConnectionResult.SUCCESS) {
				GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
			} else {
				map = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.ItemMap)).getMap();
				map.setMyLocationEnabled(true);
				//load custom location from user
				if (customLat != 0) {
					//address = util.getCompleteAddressString(location.latitude, location.longitude, this);
					Marker customLocation = map.addMarker(new MarkerOptions()
							.position(location));
					customLocation.showInfoWindow();
					util.moveMap(map, location, 16);
					
				}else {
					//load current location of user
					LocationResult locationResult = new LocationResult(){
					    @Override
					    public void gotLocation(Location loc){
					        //Got the location!
					    	location = new LatLng(loc.getLatitude(), loc.getLongitude());
					    	util.moveMap(map, location, 16);
					    	//address = util.getCompleteAddressString(location.latitude, location.longitude, getApplicationContext());
					    	//EditText txtAddress = (EditText) findViewById(R.id.txtItemAddress);
							//txtAddress.setText(address);
					    }
					};
					myloc=new MyLocation();
					myloc.getLocation(this, locationResult);
				}
				map.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								PinOnMap.class);
						if (location !=null){
						intent.putExtra("LATloc", location.latitude);
						intent.putExtra("LONloc", location.longitude);
						}
						startActivity(intent);

					}
				});
				//EditText txtAddress = (EditText) findViewById(R.id.txtItemAddress);
				//txtAddress.setText(address);
			}
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this,"OnCreate: " + e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_item, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    //respond to menu item selection
		switch (item.getItemId()) {
	    case R.id.mBookmark:
	    //eventBookmark(this);
	    return true;
	    default:
	    return super.onOptionsItemSelected(item);
	}
	}
	@Override
	public void onPause() {
		super.onPause(); // Always call the superclass method first
		if (myloc!=null){
			myloc.cancelTimer();
		}

	}
	boolean validate(){
		EditText txtName = (EditText) findViewById(R.id.txtItemName);
		if ( txtName.getText().toString().trim().length()==0){
			txtName.setError("Please input Name");
			 return false;
		}
		return true;
	}

	public void eventToFriend(View view) {
		if (validate()) {
			newItem = new Item();
			
			String accountID = util.getPrefAccountID(this);
			if (accountID.equals("0")) {
				Toast.makeText(getApplicationContext(), "UID is not available", Toast.LENGTH_LONG)
				.show();
				return;
			}
			newItem.setAccountID(Integer.parseInt(accountID));
			
			EditText txtName = (EditText) findViewById(R.id.txtItemName);
			newItem.setName(txtName.getText().toString());
			EditText txtAddress = (EditText) findViewById(R.id.txtItemAddress);
			newItem.setAddress(txtAddress.getText().toString());
			newItem.setLatLng(location.latitude, location.longitude);
			
			Intent intent = new Intent(getApplicationContext(), FriendList.class);
			intent.putExtra("typeShare", "new");
			intent.putExtra("Item", newItem);
			startActivity(intent);
		}
	}
	
	public void eventBookmark(View view) {
		if (validate()) {
			newItem = new Item();
			String accountID = util.getPrefAccountID(this);
			if (accountID.equals("0")) {
				Toast.makeText(getApplicationContext(), "UID is not available", Toast.LENGTH_LONG)
				.show();
				return;
			}
			newItem.setAccountID(Integer.parseInt(accountID));
			
			EditText txtName = (EditText) findViewById(R.id.txtItemName);
			newItem.setName(txtName.getText().toString());
			EditText txtAddress = (EditText) findViewById(R.id.txtItemAddress);
			newItem.setAddress(txtAddress.getText().toString());
			newItem.setLatLng(location.latitude, location.longitude);
			
			bookmarkItemTask t = new bookmarkItemTask(newItem);
			t.execute();
			
			Intent intent = new Intent(getApplicationContext(),Home.class);
			startActivity(intent);
		}
	}
	
	private String bookmarkItem(Item itm) {
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "bookmark-point"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("accountid", String.valueOf(itm
					.getAccountID())));
			params.add(new BasicNameValuePair("itemid", String.valueOf(itm
					.getItemID())));
			params.add(new BasicNameValuePair("pointname", itm.getName()));
			params.add(new BasicNameValuePair("address", itm.getAddress()));
			params.add(new BasicNameValuePair("locx", String.valueOf(itm
					.getLattitude())));
			params.add(new BasicNameValuePair("locy", String.valueOf(itm
					.getLongitude())));
			JSONObject jUser = util.getJSONfromURL( params);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "0";

	}
	public void eventHome(View view){
		Intent intent = new Intent(getApplicationContext(),Home.class);
		startActivity(intent);
	}
	public void eventGenAddress(View view){
		address = util.getCompleteAddressString(location.latitude, location.longitude, this);
		if(address.length()==0) address="no name";
		EditText txtAddress = (EditText) findViewById(R.id.txtItemAddress);
		txtAddress.setText(address);
	}
	
	class bookmarkItemTask extends AsyncTask<String, Integer, Boolean> {
		Item item;
		public bookmarkItemTask(Item i) {
			item=i;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				bookmarkItem(item);
				//Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
