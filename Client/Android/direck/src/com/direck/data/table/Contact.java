package com.direck.data.table;

import org.json.JSONException;
import org.json.JSONObject;

import android.media.audiofx.AcousticEchoCanceler;

public class Contact {
	String ID;
	String AccountID;
	String ContactName;
	String ContactNumber;
	String FriendID;
	String ModifiedDate;
	String Status;

	public Contact() {
	}
	
	public Contact(JSONObject json){
		try {
			ID = json.getString("Id");
	         AccountID = json.getString("AccountID"); 
	         ContactName = json.getString("ContactName"); 
	         ContactNumber = json.getString("ContactNumber"); 
	         FriendID = json.getString("FriendID "); 
	         ModifiedDate = json.getString("ModifiedDate");
	         Status = json.getString("Status");
		} catch (JSONException e) {
			// TODO: handle exception
			
		}
	}

	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	public String getFriendID() {
		return FriendID;
	}

	public void setFriendID(String friendID) {
		FriendID = friendID;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return ContactName + "-" + ContactNumber;
	}
}
