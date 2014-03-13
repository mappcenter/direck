package com.direck.utils;



import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	public static boolean debug=false;
	public static boolean usedPOSTMethod=false;
	public static int SUCCESS_NO_MSG = 0;
	public static int SUCCESS_MSG = 1;
	public static int ERROR_NO_MSG = 2;
	public static int ERROR_MSG = 3;
	public static String GCMToken= "GCMTokenKey";
	public static String MD5= "key@Direk";
	public static String hostURL = "http://192.168.0.122:89/Direck/";
	//public static String hostURL = "http://vnpon.com/Direck/";
	public static String OS="android";
	 
	
	public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	public static void moveMap(GoogleMap m,LatLng loc,int zoom){
		try {
			// Move the camera instantly tolocation with a zoom of 17.
			m.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,
					zoom));
			// Zoom in, animating the camera.
			m.animateCamera(CameraUpdateFactory.zoomTo(zoom-2), 2000,
					null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
        	util.ShowMessage("Util:","getCompleteAddressString: " + e.getMessage());
            //Log.w("My Current loction address", "Canont get Address!");
            //Toast.makeText(con, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(con, strAdd, Toast.LENGTH_LONG).show();
        return strAdd;
    }
	public static void ShowToastMessage(String text,Context con){
			Toast.makeText(con, text, Toast.LENGTH_LONG).show();
			//Log.d(con.getClass().getName(), text);
	}
	public static void ShowMessage(Object obj,String text){
		//if (debug){
			//Toast.makeText(con, text, Toast.LENGTH_LONG).show();
			//Log.d(con.getClass().getName(), text);
		//}
		System.out.println("Object:" +obj.toString() + ": " + text);
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
	public static String getPrefGCMToken(Context con){
		return getStringPref(con, util.GCMToken, "0");
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
	 public static JSONObject  getJSONfromURL(List<NameValuePair> params){
		 JSONParser jsonParser = new JSONParser();
		 JSONObject json = new JSONObject();
		 String method="";
		 if (usedPOSTMethod) method="POST"; 
		 	else method="GET";
		 String url = util.hostURL;
		 try {
			 json = jsonParser.makeHttpRequest(url,method, params);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	 public static String isNull(String value, String defaultValue){
		 if (value == null) return defaultValue;
		 if (value.equals("null")) return defaultValue;
		 return value;
	 }
	 public static void checkNotNull(Object reference, String name) {
	        if (reference == null) {
	            //throw new NullPointerException(
	              //      getString(R.string.error_config, name));
	        }
	    }
	 
	 public static String getMD5(String input) {
		    String result = input;
		    try {
		    	  if(input != null) {
				        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
				        md.update(input.getBytes());
				        BigInteger hash = new BigInteger(1, md.digest());
				        result = hash.toString(16);
				        while(result.length() < 32) { //40 for SHA-1
				            result = "0" + result;
				        }
				    }
			} catch (Exception e) {
				// TODO: handle exception
			}
		  
		    return result;
		}
	 public static double distFrom(double lat1, double lng1, double lat2, double lng2) { 
	      double earthRadius = 3958.75; 
	      double dLat = Math.toRadians(lat2-lat1); 
	      double dLng = Math.toRadians(lng2-lng1); 
	      double a = Math.sin(dLat/2) * Math.sin(dLat/2) + 
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * 
	               Math.sin(dLng/2) * Math.sin(dLng/2); 
	      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	      double dist = earthRadius * c; 

	      return dist; 
	    } 
}
