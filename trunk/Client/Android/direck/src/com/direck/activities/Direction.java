package com.direck.activities;

import com.direck.R;
import com.direck.R.layout;
import com.direck.R.menu;
import com.direck.models.Item;
import com.direck.utils.MyLocation;
import com.direck.utils.util;
import com.direck.utils.MyLocation.LocationResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class Direction extends FragmentActivity {
	
	private Item selectedItem;
	private LatLng currentLAT;
	private GoogleMap map;
	private MyLocation myloc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direction);
		
		Intent in = getIntent();
		if (in !=null){
			selectedItem = in.getParcelableExtra("selectedItem");
			if (selectedItem==null) selectedItem= new Item(); 
		}

		// Check Google Play Service Available
		try {
			// Check status of Google Play Services
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		    if (status != ConnectionResult.SUCCESS) {
		        GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
		    }else {
		    	map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.ItemMap))
			               .getMap();
		    	map.setMyLocationEnabled(true);
				Marker mark1 = map.addMarker(new MarkerOptions().position(selectedItem.getLatLng()).title(selectedItem.getName()).snippet(selectedItem.getAddress()));
				mark1.showInfoWindow();
			    util.moveMap(map, selectedItem.getLatLng(), 16);
			    
			    //get current lat
			    LocationResult locationResult = new LocationResult(){
				    @Override
				    public void gotLocation(Location loc){
				        //Got the location!
				    	currentLAT = new LatLng(loc.getLatitude(), loc.getLongitude());
				    	util.moveMap(map, currentLAT, 16);
				    }
				};
				myloc=new MyLocation();
				myloc.getLocation(this, locationResult);
			    //create marker for current
				Marker mark2 = map.addMarker(new MarkerOptions().position(currentLAT).title("Your are here!"));
				mark2.showInfoWindow();
			    util.moveMap(map, selectedItem.getLatLng(), 16);
	 
		    }
		    
		} catch (Exception e) {
		    Log.e("Error: GooglePlayServiceUtil: ", "" + e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.direction, menu);
		return true;
	}

}
