package com.direck.activities;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.R;
import com.direck.activities.Home;
import com.direck.models.Account;
import com.direck.sync.RegisterGCM;
import com.direck.sync.sync;
import com.direck.utils.ConnectionDetector;
import com.direck.utils.util;
import com.google.android.gcm.GCMRegistrar;






import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class Register extends Activity {
	RelativeLayout progressLayout;
	String AccountID;
	String phoneNumber;
	String displayName;
	
	RegisterGCM GCM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);		
		progressLayout = (RelativeLayout) findViewById(R.id.progressbar_view);
		progressLayout.setVisibility(View.INVISIBLE);
		loadUserPhoneNumber();
	}
	
	  @Override
	    protected void onDestroy() {
		  try {
			  if (GCM !=null){
			        if (GCM.getmRegisterTask() != null) {
			            GCM.getmRegisterTask().cancel(true);
			        }
			        unregisterReceiver(GCM.getmHandleMessageReceiver());
			        GCMRegistrar.onDestroy(this);
				  }
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("",e.getMessage());
		}
		  
	        super.onDestroy();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public boolean validate() {
		EditText txtPhone = (EditText) findViewById(R.id.phone_no);
		if (txtPhone.getText().toString().trim().length() <= 0) {
			txtPhone.setError("Please input phone number");
			return false;
		}
		EditText txtName = (EditText) findViewById(R.id.display_name);
		if (txtName.getText().toString().trim().length() <= 0) {
			txtName.setError("Please input display name");
			return false;
		}
		return true;
	}

	/** Called when the user clicks the Send button */
	public int startDireck(View view) {
		// boolean bSuccess= false;
		try {
			ConnectionDetector internet = new ConnectionDetector(this);
			
			if (!internet.isConnectingToInternet()) { 
				util.ShowToastMessage("No internet connection!", this);
				return 0;
			}
            
			ImageButton btn = (ImageButton)findViewById(R.id.btnSend);
			btn.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		    //imm.hideSoftInputFromInputMethod(btn.getWindowToken(), 0);
			// bSuccess=register();
			if (validate()) {
				EditText txtPhone = (EditText) findViewById(R.id.phone_no);
				EditText txtName = (EditText) findViewById(R.id.display_name);
				phoneNumber = txtPhone.getText().toString();
				displayName = txtName.getText().toString();
				// Synchronize
				
				 AlertDialog d=  new AlertDialog.Builder(this)
	                .setTitle(R.string.NumberDialog_Title)
	                .setMessage(getString(R.string.NumberDialog_Message,displayName, phoneNumber))
	                .setPositiveButton(R.string.NumberDialog_Yes, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {

	                        /* User clicked OK so do some stuff */
	                    	RegisterTask t =	new RegisterTask(getApplicationContext());
	        			    t.execute();
	                    }
	                })
	                .setNegativeButton(R.string.NumberDialog_No, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {

	                        /* User clicked Cancel so do some stuff */
	                    	EditText txtPhone = (EditText) findViewById(R.id.phone_no);
	                    	txtPhone.requestFocus();
	                    }
	                })
	                .create();
				 
				 d.show();
				
				
				
				//AlertDialog myDialog = MyDialog("Confirm", "Is this your phone number: " + phoneNumber  + " ?", this);
              //  myDialog.show();
			    //RegisterTask t =	new RegisterTask(this);
			    //t.execute();

				// if create transaction successfully then mark isFirstRegister
				// = 0
				

			}
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this,"startDireck: " + e.getMessage());
		}
		return 1;
	}
	


	private String createAccount() {
		String accID = "0";
		try {

			String serverUrl = util.hostURL;
			// serverUrl = serverUrl + "/?action=create-user";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "create-user"));
			params.add(new BasicNameValuePair("name", displayName));
			params.add(new BasicNameValuePair("phonenumber", phoneNumber));
			params.add(new BasicNameValuePair("os", util.OS));

			JSONObject jUser = util.getJSONfromURL(params);
			//String json="{\"ErrorCode\":1,\"Message\":\"Create Successful\",\"Data\":{\"Id\":\"10\",\"Name\":\"Liem2\",\"PhoneNumber\":\"123456789\",\"CreatedDate\":\"1386563805\",\"ModifiedDate\":\"0\",\"Status\":\"1\"}}";
			//JSONObject jUser = new JSONObject(json);  

			int errorCode = jUser.getInt("ErrorCode");
			String msg = jUser.getString("Message");
			JSONObject jAccount = jUser.getJSONObject("Data");

			// success : 0: success_no_msg or 1: success_msg
			if (errorCode <= 1) {
				// successfully created
				Account account = new Account(jAccount,this);
				accID = account.getId();
				// save Account ID in client
				util.setStringPref(this, getString(R.string.uid), accID);

			} else {
				// failed to create user
				accID = "0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return accID;
	}

	
	public boolean register() {
		try {
			// create account
			AccountID = createAccount();
			// create successfull do next action
			
			if (!AccountID.equals("0")) {
				GCM = new RegisterGCM();
				GCM.registerGCM(getApplicationContext(), AccountID);
				
				//registerGCM(AccountID);
				sync.syncContactServer(getApplicationContext(),AccountID);
				//register ok
				util.setStringPref(this, getString(R.string.isFirstRegister), "0");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this, "register: " + e.getMessage());
		}
		// Do something in response to button

		return true;
	}
	 

	public void loadUserPhoneNumber() {
		try {
			 
			TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String number = tm.getLine1Number();
			if (number == null)
				number = "";
			EditText phone = (EditText) findViewById(R.id.phone_no);
			phone.setText(number);
			if (number.length() == 0) {
				phone.requestFocus();
			} else {
				EditText user = (EditText) findViewById(R.id.display_name);
				user.requestFocus();
			}
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(this, "loadPhoneNumber: " + e.getMessage());
		}
	}

	class RegisterTask extends AsyncTask<String, Integer, Boolean> {
		Context context;
		public RegisterTask(Context con) {
			context = con;
		}

		@Override
		protected void onPreExecute() {
			progressLayout.bringToFront();
			// progressLayout.setBackgroundColor(Color.WHITE);
			// progressLayout.getBackground().setAlpha(51);
			progressLayout.setVisibility(View.VISIBLE);
			// listView.setVisibility(View.GONE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// listView.setVisibility(View.VISIBLE);
			// adapter.notifyDataSetChanged();
			
			super.onPostExecute(result);
			//progressLayout.setVisibility(View.INVISIBLE);
			finish();
			Intent intent = new Intent(context, Home.class);
			startActivity(intent);
			
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				register();
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	

   

}
