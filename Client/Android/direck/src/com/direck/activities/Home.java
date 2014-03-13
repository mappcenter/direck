package com.direck.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.GCMIntentService;
import com.direck.R;
import com.direck.activities.Register.RegisterTask;
import com.direck.adapters.ItemArrayAdapter;
import com.direck.models.Item;
import com.direck.sync.RegisterGCM;
import com.direck.sync.sync;
import com.direck.utils.ActionItem;
import com.direck.utils.PullToRefreshListView;
import com.direck.utils.PullToRefreshListView.OnRefreshListener;
import com.direck.utils.ConnectionDetector;
import com.direck.utils.QuickAction;
import com.direck.utils.SwipeDismissListViewTouchListener;
import com.direck.utils.util;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseObject;
import com.parse.ParseAnalytics;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Home extends Activity {

	//static private ArrayList<Item> items;
	static private ItemArrayAdapter arr;
	public ListView lv;
	QuickAction mQuickAction;
	int selectedItemIndex=-1;
	RegisterGCM GCM;
	private IntentFilter receiveFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		

		//loadFilter();
		loadItemList();
		registerActionBar();
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
		
		//sync.syncContactToLocalDB(this);

		// Show the Up button in the action bar.
		// setupActionBar();
	}
	
	private void registerActionBar(){
		try {
			//Share action item
			ActionItem accAction = new ActionItem();
			 
			accAction.setTitle("Share");
			accAction.setIcon(getResources().getDrawable(R.drawable.ic_accept)); 
			//Delete action item
			ActionItem upAction = new ActionItem();
			 
			upAction.setTitle("Delete");
			upAction.setIcon(getResources().getDrawable(R.drawable.ic_up));
			
			mQuickAction  = new QuickAction(this);
			mQuickAction.addActionItem(accAction);
			mQuickAction.addActionItem(upAction);
			 
			//setup the action item click listener
			mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			    @Override
				public void onItemClick(QuickAction source, int pos, int actionId) {
					// TODO Auto-generated method stub
			    	int shareID = arr.getItem(selectedItemIndex).getId();
					if (pos == 0) { 
				           //Toast.makeText(getApplicationContext(), "Share ID:" + arr.getItem(selectedItemIndex).getId(), Toast.LENGTH_SHORT).show();
				           Intent intent =new Intent(getApplicationContext(), FriendList.class);
				   			intent.putExtra("typeShare", "existed");
				   			intent.putExtra("Item", arr.getItem(selectedItemIndex));
				   			startActivity(intent); 
				           
						
				        } else if (pos == 1) { 
				           //Toast.makeText(getApplicationContext(), "Delete ID:" + arr.getItem(selectedItemIndex).getId(), Toast.LENGTH_SHORT).show();
				        	try {
				        		   arr.remove(arr.getItem(selectedItemIndex));
						           arr.notifyDataSetChanged();
						           
						           DeleteItemTask t = new DeleteItemTask(String.valueOf(shareID));
						           t.execute();
							} catch (Exception e) {
								// TODO: handle exception
								Toast.makeText(getApplicationContext(), "delete fail ID:" + shareID, Toast.LENGTH_SHORT).show();
							}
				          
				        }
				}
			});
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					selectedItemIndex = arg2;
					mQuickAction.show(arg1);
					return false;
				}
				
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "registerActionBar() error", Toast.LENGTH_LONG).show();
		}
		
	}

	/*private void loadFilter() {
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
	}*/

	private void loadItemList() {

		try {
			ImageButton btnShare = (ImageButton) findViewById(R.id.btnShare);
			ConnectionDetector internet = new ConnectionDetector(this);
			
			if (!internet.isConnectingToInternet()) { 
				Toast.makeText(getApplicationContext(), "No Internet connection",
						Toast.LENGTH_LONG).show();
				btnShare.setEnabled(false);
				return ;
			}
			
			//ArrayList<Item> items = new ArrayList<Item>();
			
			String accountID = util.getPrefAccountID(this);
			if (accountID.equals("0")) {
				Toast.makeText(getApplicationContext(), "UID is not available",
						Toast.LENGTH_LONG).show();
			//if no UID , cannot share anything
			btnShare.setEnabled(false);
				return;
			}//else Toast.makeText(getApplicationContext(), "UID:" + accountID,
			//		Toast.LENGTH_LONG).show();
			
			//if have UID, then share is OK
			btnShare.setEnabled(true);
			
			lv = (ListView) findViewById(R.id.listItem);
			//RelativeLayout bar = (RelativeLayout) findViewById(R.id.TitleHome);
			
			LoadItemsTask t = new LoadItemsTask(this,accountID);
			t.execute();
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Item itm = (Item) arr.getItem(position);
					
					//itm.setViewStatus(true);
					boolean viewStatus = itm.isViewStatus();
				    if (!viewStatus) {
					    itm.setViewStatus(true);	
					    UpdateViewStatusTask t = new UpdateViewStatusTask(getApplicationContext(),String.valueOf(itm.getId()));
					    t.execute();
				    }

					Intent intent = new Intent(getApplicationContext(),
							ItemDetails.class);
					intent.putExtra("selectedItem", itm);
					startActivity(intent);
				}

			});

		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this,"LoadItemlist: " + e.getMessage());
		}

	}

	public void shareNew(View view) {
		Intent intent = new Intent(this, ShareItem.class);
		startActivity(intent);

	}
	
	public void refreshList(View view) {
		
		loadItemList();
		arr.notifyDataSetChanged();

	}

	public void setItem(String accountID) {
		ArrayList<Item> items = new ArrayList<Item>();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "get-list-point"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("accountid", accountID));

			JSONObject jUser = util.getJSONfromURL(params);
			//String jsonS ="{\"ErrorCode\":0,\"Message\":\"Get List Successful\",\"Data\":[{\"Id\":\"1\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2e2e2 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"122223\",\"PointLocY\":\"2223456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386407342\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"2\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"123\",\"PointLocY\":\"3456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386553726\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"3\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":\"Ky Hoa 2\",\"PointAddress\":\"Cao Thang\",\"PointLocX\":\"123\",\"PointLocY\":\"3456\",\"PointOwner\":\"2\",\"PointCreatedDate\":\"1386553726\",\"PointModifiedDate\":\"0\",\"PointStatus\":\"0\",\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"5\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"1\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386407342\"},{\"Id\":\"7\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"8\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"9\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"11\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"2\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"13\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"14\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"2\",\"FriendName\":\"\",\"Type\":\"1\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"15\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"3\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"},{\"Id\":\"17\",\"AccountID\":\"2\",\"AccountName\":\"\",\"PointID\":\"3\",\"PointName\":null,\"PointAddress\":null,\"PointLocX\":null,\"PointLocY\":null,\"PointOwner\":null,\"PointCreatedDate\":null,\"PointModifiedDate\":null,\"PointStatus\":null,\"FriendID\":\"4\",\"FriendName\":\"\",\"Type\":\"0\",\"ViewStatus\":\"0\",\"CreatedDate\":\"1386553726\"}]}";
			//JSONObject jUser = new JSONObject(jsonS);
			//Toast.makeText(this, jUser.toString(), Toast.LENGTH_LONG).show();
			int errorCode = jUser.getInt("ErrorCode");
			String msg = jUser.getString("Message");
			JSONArray json=new JSONArray();
			if (!(jUser.getString("Data").length()==0))
			 json = jUser.getJSONArray("Data");
			// success : 0: success_no_msg or 1: success_msg

			if (errorCode <= 1) {
				
				for (int i = 0; i < json.length(); i++) {
					JSONObject obj = json.getJSONObject(i);
					Item itm = new Item(obj);
					items.add(itm);
				}
			}
			arr = new ItemArrayAdapter(this, R.layout.list_item, items);
		} catch (JSONException e) {
			// TODO: handle exception
			//Toast.makeText(this,"setItem: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}
	
	private void updateViewStatus(String shareID) {
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "update-ViewStatus"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("shareid", shareID));
			JSONObject jUser = util.getJSONfromURL(params);
			
		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
			//Toast.makeText(getApplicationContext(),"error in saveItem(): "+  e.getMessage(), Toast.LENGTH_LONG)
			//.show();
		}

	}
	public void deleteItem(String shareID) {
		//items = new ArrayList<Item>();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "delete-point"));
			params.add(new BasicNameValuePair("os", util.OS));
			params.add(new BasicNameValuePair("shareid", shareID));

			JSONObject jUser = util.getJSONfromURL(params);
		} catch (Exception e) {
			// TODO: handle exception
			//Toast.makeText(this,"setItem: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onDestroy() {
		
		 if (GCM !=null){
		        if (GCM.getmRegisterTask() != null) {
		            GCM.getmRegisterTask().cancel(true);
		        }
		        unregisterReceiver(GCM.getmHandleMessageReceiver());
		        GCMRegistrar.onDestroy(this);
			  }
		        
		 
		super.onDestroy();
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
		loadItemList();
		//LoadItemsTask t = new LoadItemsTask(this,util.getPrefAccountID(this));
		//t.execute();
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
	class LoadItemsTask extends AsyncTask<String, Integer, Boolean> {
		Context context;
		ProgressBar p;
		String UID;
		public LoadItemsTask(Context con,String uid) {
			context = con;
			p = (ProgressBar) findViewById(R.id.progressBar1);
			UID = uid;
		}

		@Override
		protected void onPreExecute() {
			p.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			p.setVisibility(View.INVISIBLE);
			runOnUiThread(new Runnable() {
			       public void run() {
			    	   lv.setAdapter(arr);       
			}});
			
			super.onPostExecute(result);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				setItem(UID);
				GCM = new RegisterGCM();
				GCM.registerGCM(context, util.getPrefAccountID(context));
				//Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	class DeleteItemTask extends AsyncTask<String, Integer, Boolean> {
		//Context context;
		//ProgressBar p;
		//String UID;
		String shareID;
		public DeleteItemTask(String shareid) {
			//context = con;
			//p = (ProgressBar) findViewById(R.id.progressBar1);
			//UID = uid;
			shareID = shareid;
		}

		@Override
		protected void onPreExecute() {
			//p.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			
			super.onPostExecute(result);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				//setItem(UID);
				deleteItem(shareID);
				//Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	class UpdateViewStatusTask extends AsyncTask<String, Integer, Boolean> {
		Context context;
		String UID;
		public UpdateViewStatusTask(Context con,String uid) {
			context = con;
			UID = uid;
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
				updateViewStatus(UID);
				//Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
