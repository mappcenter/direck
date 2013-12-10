package com.direck.activities;

import com.direck.R;
import com.direck.R.layout;
import com.direck.R.menu;
import com.direck.utils.util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class PinOnMap extends FragmentActivity {
	static GoogleMap map;
	static LatLng newLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_on_map);
		double Lat = 0;
		double Lon = 0;
		Intent in = getIntent();
		if (in != null) {
			Lat = in.getDoubleExtra("LATloc", 0);
			Lon = in.getDoubleExtra("LONloc", 0);
			newLocation = new LatLng(Lat, Lon);
		}
			
		//newLocation= new LatLng(0, 0);
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
				map.clear();
				Marker newMark = map.addMarker(new MarkerOptions().position(newLocation).title("Here"));
				newMark.showInfoWindow();
				util.moveMap(map, newLocation, 16);
				
				
				map.setOnMapLongClickListener(new OnMapLongClickListener() {
					
					@Override
					public void onMapLongClick(LatLng arg0) {
						// TODO Auto-generated method stub
						map.clear();
						Marker newMark = map.addMarker(new MarkerOptions().position(arg0).title("Here"));
						newMark.showInfoWindow();
						newLocation = arg0;
						//Toast.makeText(getApplicationContext(), newLocation.toString(), Toast.LENGTH_LONG).show();;
						
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void DonePin(View view){
		Intent intent = new Intent(getApplicationContext(),ShareItem.class);
		intent.putExtra("CustomLat", newLocation.latitude);
		intent.putExtra("CustomLon", newLocation.longitude);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pin_on_map, menu);
		return true;
	}

}
