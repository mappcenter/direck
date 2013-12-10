package com.direck.utils;



import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.direck.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


public class util {
	public static int ShareType = 0;
	public static int BeSharedType = 1;
	public static int BookmarkType = 2;
	public static boolean debug=true;
	public static int SUCCESS_NO_MSG = 0;
	public static int SUCCESS_MSG = 1;
	public static int ERROR_NO_MSG = 2;
	public static int ERROR_MSG = 3;
	public static String hostURL = "http://vnpon.com/Direck/";
	public static String OS="android";
	 
	
	public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	public static void moveMap(GoogleMap m,LatLng loc,int zoom){
		// Move the camera instantly tolocation with a zoom of 17.
		m.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,
				zoom));
		// Zoom in, animating the camera.
		m.animateCamera(CameraUpdateFactory.zoomTo(zoom-2), 2000,
				null);
	}
	public static String getCompleteAddressString(double LATITUDE, double LONGITUDE,Context con) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(con, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                strAdd = strReturnedAddress.toString();
                //Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
        	util.ShowMessage(e.getMessage(), con);
            //Log.w("My Current loction address", "Canont get Address!");
            //Toast.makeText(con, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(con, strAdd, Toast.LENGTH_LONG).show();
        return strAdd;
    }
	public static void ShowMessage(String text,Context con){
		//if (debug){
			//Toast.makeText(con, text, Toast.LENGTH_LONG).show();
			//Log.d(con.getClass().getName(), text);
		//}
	}
	public static String getStringPref(Context con,String key,String defaultValue){
		String val = defaultValue;
		try {
			SharedPreferences sharedPref = con.getSharedPreferences(con.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
			val = sharedPref.getString(key, defaultValue);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return val;
	}
	public static void setStringPref(Context con,String key,String value){
		try {
			SharedPreferences sharedPref = con.getSharedPreferences(con.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static String getPrefAccountID(Context con){
		return getStringPref(con, con.getString(R.string.uid), "0");
	}
	 public static void post(String endpoint, Map<String, String> params)
	            throws IOException {   	
	        
	        URL url;
	        try {
	            url = new URL(endpoint);
	        } catch (MalformedURLException e) {
	            throw new IllegalArgumentException("invalid url: " + endpoint);
	        }
	        StringBuilder bodyBuilder = new StringBuilder();
	        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
	        // constructs the POST body using the parameters
	        while (iterator.hasNext()) {
	            Entry<String, String> param = iterator.next();
	            bodyBuilder.append(param.getKey()).append('=')
	                    .append(param.getValue());
	            if (iterator.hasNext()) {
	                bodyBuilder.append('&');
	            }
	        }
	        String body = bodyBuilder.toString();
	        //Log.v(TAG, "Posting '" + body + "' to " + url);
	        byte[] bytes = body.getBytes();
	        HttpURLConnection conn = null;
	        try {
	        	Log.e("URL", "> " + url);
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setUseCaches(false);
	            conn.setFixedLengthStreamingMode(bytes.length);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type",
	                    "application/x-www-form-urlencoded;charset=UTF-8");
	            // post the request
	            OutputStream out = conn.getOutputStream();
	            out.write(bytes);
	            out.close();
	            // handle the response
	            int status = conn.getResponseCode();
	            if (status != 200) {
	              throw new IOException("Post failed with error code " + status);
	            }
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	      }
	 public static JSONObject  getJSONfromURL(String url, String method,List<NameValuePair> params){
		 JSONParser jsonParser = new JSONParser();
		 JSONObject json = jsonParser.makeHttpRequest(url,method, params);
		 return json;
	 }
	 public static String returnCode(int errorCode){
		 String message="";
		 if (errorCode == SUCCESS_MSG){
			 
		 }else if (errorCode == SUCCESS_NO_MSG) {
			 
		 }else if (errorCode== ERROR_MSG){
			 
		 }else if (errorCode == ERROR_NO_MSG){
			 
		 }
		 return message;
	 }
}
