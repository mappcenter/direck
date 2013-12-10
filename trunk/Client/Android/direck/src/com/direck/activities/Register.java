package com.direck.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.R;
import com.direck.activities.Home;
import com.direck.data.dao.DAOContact;
import com.direck.data.table.Contact;
import com.direck.models.Account;
import com.direck.models.Friend;
import com.direck.sync.sync;
import com.direck.utils.ConnectionDetector;
import com.direck.utils.JSONParser;
import com.direck.utils.util;

import android.R.string;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Register extends Activity {
	RelativeLayout progressLayout;
	String AccountID;
	String phoneNumber;
	String displayName;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		if (!util.debug) {
			// if isFirstRegister = true ( 1) then show register form else show
			// Home screen
			String isFirstRegister = util.getStringPref(this, getString(R.string.isFirstRegister), "1");
					
			if (isFirstRegister.equals("1")){
				setContentView(R.layout.activity_register);
				progressLayout = (RelativeLayout) findViewById(R.id.progressbar_view);
				progressLayout.setVisibility(View.INVISIBLE);
				loadUserPhoneNumber();
			} else {
				// home screen
				Intent intent = new Intent(this, Home.class);
				startActivity(intent);
			}
		}else {
			progressLayout = (RelativeLayout) findViewById(R.id.progressbar_view);
			progressLayout.setVisibility(View.INVISIBLE);
			loadUserPhoneNumber();
		}

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
				util.ShowMessage("no itnernet connection", this);
				return 0;
			}
			// bSuccess=register();
			if (validate()) {
				EditText txtPhone = (EditText) findViewById(R.id.phone_no);
				EditText txtName = (EditText) findViewById(R.id.display_name);
				phoneNumber = txtPhone.getText().toString();
				displayName = txtName.getText().toString();
				// Synchronize
			    RegisterTask t =	new RegisterTask(this);
			    t.execute();

				// if create transaction successfully then mark isFirstRegister
				// = 0
				if (!util.debug) {
					util.setStringPref(this, getString(R.string.isFirstRegister), "0");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(e.getMessage(), this);
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

			JSONObject jUser = util.getJSONfromURL(serverUrl, "GET", params);
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

	private JSONArray uploadContact() {
		JSONArray jArr = new JSONArray();
		try {
			DAOContact dao = new DAOContact(this);
			List<Contact> contactLst = dao.getAllContacts();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "upload-contact"));
			params.add(new BasicNameValuePair("accountid", AccountID));
			params.add(new BasicNameValuePair("os", util.OS));
			for (int i = 0; i < contactLst.size(); i++) {
				Contact contact = contactLst.get(i);
				params.add(new BasicNameValuePair("contact", contact
						.getContactName() + "::" + contact.getContactNumber()));
			}
			JSONObject jContact = util.getJSONfromURL(util.hostURL, "GET",
					params);
			int errorCode = jContact.getInt("ErrorCode");
			String msg = jContact.getString("Message");
			if (errorCode <= 1) {
				jArr = jContact.getJSONArray("Data");
			}
		} catch (JSONException je) {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return jArr;
	}

	public boolean register() {
		try {
			// create account
			AccountID = createAccount();
			// create successfull do next action
			
			if (!AccountID.equals("0")) {
				// sync contact from mobile to Local db
				sync.syncContactToLocalDB(getApplicationContext());
				// upload contact
				JSONArray arrayContact = uploadContact();
				// sync contact from local mobile with server db
				DAOContact dao = new DAOContact(this);
				dao.deleteAllContact();
				// create friend list
				for (int i = 0; i < arrayContact.length(); i++) {
					JSONObject c = arrayContact.getJSONObject(i);
					Contact con = new Contact(c);
					dao.createContact(con.getAccountID(), con.getContactName(),
							con.getContactNumber(), con.getFriendID(),
							con.getStatus());
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage(e.getMessage(), this);
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
			util.ShowMessage(e.getMessage(), this);
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
			progressLayout.setVisibility(View.INVISIBLE);
			Intent intent = new Intent(context, Home.class);
			startActivity(intent);
			finish();
		}

		@Override
		protected Boolean doInBackground(String... params) {

			
			try {
				register();
				Thread.sleep(7000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
