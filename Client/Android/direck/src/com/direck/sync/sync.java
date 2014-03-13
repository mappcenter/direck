package com.direck.sync;

import static com.direck.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.direck.gcm.CommonUtilities.EXTRA_MESSAGE;
import static com.direck.gcm.CommonUtilities.SENDER_ID;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.direck.data.dao.DAOContact;
import com.direck.data.table.Contact;
import com.direck.gcm.ServerUtilities;
import com.direck.utils.util;
import com.google.android.gcm.GCMRegistrar;

import android.content.BroadcastReceiver;
import android.content.ContextWrapper;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

public class sync {
	
	public static void syncContactToLocalDB(Context context) {
		try {
			DAOContact dao = new DAOContact(context);
			dao.open();
			// dao.deleteAllContact();
			List<Contact> lstDBContact = dao.getAllContacts();

			Cursor cursor = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					null, null, null);
			
			while (cursor.moveToNext()) {
				String name = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

				String phoneNumber = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				phoneNumber = phoneNumber.replace("-", "");
				phoneNumber= phoneNumber.replace(" ", "");
				if (!(isPhoneExistedinDB(lstDBContact, phoneNumber))) {
					dao.createContact("", name, phoneNumber, "", "");
					//add to list 'difference'
					
					//format : Name1::phone1|Name2::Phone2|....
				}
	
			}
			//sync list 'difference'
			//account ID = 
			dao.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static boolean isPhoneExistedinDB(List<Contact> lst,
			String phoneNumber) {
		if (phoneNumber.trim().length()==0) return true;
		for (int i = 0; i < lst.size(); i++) {
			Contact c = lst.get(i);
			if (c.getContactNumber().replace(" ","").equals(phoneNumber))
				return true;
		}
		return false;
	}
	public static boolean syncFromServerToLocalDB(){
		return true;
	}
	public static void syncContactServer(Context context,String accountID){
		try {
			// sync contact from mobile to Local db
			sync.syncContactToLocalDB(context);
			// upload contact
			JSONArray arrayContact = uploadContact(context,accountID);
			// sync contact from local mobile with server db
			DAOContact dao = new DAOContact(context);
			dao.open();
			dao.deleteAllContact();
			// create friend list
			for (int i = 0; i < arrayContact.length(); i++) {
				JSONObject c = arrayContact.getJSONObject(i);
				Contact con = new Contact(c);
				dao.createContact(con.getAccountID(), con.getContactName(),
						con.getContactNumber(), con.getFriendID(),
						con.getStatus());
			}
			dao.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	private static JSONArray uploadContact(Context con,String accID) {
		JSONArray jArr = new JSONArray();
		try {
			DAOContact dao = new DAOContact(con);
			dao.open();
			List<Contact> contactLst = dao.getAllContactsWithoutFriendID();
			dao.close();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("action", "upload-contact"));
			params.add(new BasicNameValuePair("accountid", accID));
			params.add(new BasicNameValuePair("os", util.OS));
			StringBuilder strContactLst = new StringBuilder();
			
			for (int i = 0; i < contactLst.size(); i++) {
				Contact contact = contactLst.get(i);
				strContactLst.append(contact.getContactName() + "::" + contact.getContactNumber() + "|");				
			}
			if (strContactLst.length()>0){
				strContactLst.deleteCharAt(strContactLst.lastIndexOf("|"));
			}
			params.add(new BasicNameValuePair("contact", strContactLst.toString()));
			
			JSONObject jContact = util.getJSONfromURL(params);
			int errorCode = jContact.getInt("ErrorCode");
			String msg = jContact.getString("Message");
			if (errorCode <= 1) {
				jArr = jContact.getJSONArray("Data");
			}
		} catch (JSONException je) {

		} catch (Exception e) {
			// TODO: handle exception
			util.ShowMessage("sync.uploadContact:", e.getMessage());
		}
		return jArr;
	}
	
	

}
