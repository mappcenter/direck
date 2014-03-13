package com.direck.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.direck.R;
import com.direck.models.Item;
import com.direck.utils.util;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetails extends FragmentActivity {

//static final LatLng namLAT = new LatLng(10.72973, 106.63722);
LatLng location;
Item selectedItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		
		Intent in = getIntent();
		if (in !=null){
			selectedItem = in.getParcelableExtra("selectedItem");
			if (selectedItem==null) {
				selectedItem= new Item(); 
				selectedItem.setId(0);
			}
		}

		// Check Google Play Service Available
		try {
			// Check status of Google Play Services
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		    if (status != ConnectionResult.SUCCESS) {
		        GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
		    }else {
		    	GoogleMap map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.ItemMap))
			               .getMap();
		    	map.setMyLocationEnabled(true);
				Marker mark = map.addMarker(new MarkerOptions().position(selectedItem.getLatLng()).title(selectedItem.getName()).snippet(selectedItem.getAddress()));
				mark.showInfoWindow();
			    util.moveMap(map, selectedItem.getLatLng(), 16);
	 
		    }
		    
		    TextView txtDistance = (TextView)findViewById(R.id.txtDistance);
		    LocationManager lm;
		    lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		    Criteria c = new Criteria();
		    String provider = lm.getBestProvider(c, false);
		    Location l = lm.getLastKnownLocation(provider);
		    if (l!=null){
		    	double lng=l.getLongitude();
		        double lat=l.getLatitude();
		        
		        //double dist=0;
		        //dist = util.distFrom(lat, lng, selectedItem.getLattitude(), selectedItem.getLongitude());
		        float[] rs={0};
		        Location.distanceBetween(lat, lng, selectedItem.getLattitude(), selectedItem.getLongitude(), rs);
		        if (rs!=null){
		        	txtDistance.setText(String.format("%.2f", rs[0]/1000) + "(km)");
		        }
		    }
		    
		} catch (Exception e) {
			util.ShowMessage(this,e.getMessage());
		}
		
		/*
		TextView txtShareBy = (TextView)findViewById(R.id.ShareBy);
		TextView txtCreateDate = (TextView)findViewById(R.id.CreateDate);
		
		txtShareBy.setText("Shared by: " + selectedItem.getShareby() );
		txtCreateDate.setText("Shared at: " + selectedItem.getCreateDate() );
		*/

	}
	
	
	
	public void share(View view){
		Intent intent =new Intent(this, FriendList.class);
		intent.putExtra("typeShare", "existed");
		intent.putExtra("Item", selectedItem);
		startActivity(intent); 
	}
	
	public void eventHome(View view){
		Intent intent = new Intent(getApplicationContext(),Home.class);
		startActivity(intent);
	}
	
	public void showDirection(View view){
		try {
			String uri="http://maps.google.com/maps?f=d&daddr="+selectedItem.getLattitude() + "," + selectedItem.getLongitude();
			
			Intent intent = new Intent(Intent.ACTION_VIEW, 
				    Uri.parse(uri));
				intent.setComponent(new ComponentName("com.google.android.apps.maps", 
				    "com.google.android.maps.MapsActivity"));
				startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this,"showDirection: " + e.getMessage());
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_details, menu);
		return true;
	}
	
	

}
