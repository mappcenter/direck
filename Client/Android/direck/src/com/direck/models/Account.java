package com.direck.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.direck.utils.util;


public class Account {
	String Id;
	String Name;
	String PhoneNumber;
	String CreatedDate;
	String ModifiedDate;
	String Status;

	public Account(JSONObject json,Context con) {
		try {
			Id = json.getString("Id");
	         Name = json.getString("Name"); 
	         PhoneNumber = json.getString("PhoneNumber"); 
	         CreatedDate = json.getString("CreateDate"); 
	         ModifiedDate = json.getString("ModifiedDate"); 
	         Status = json.getString("Status");
		} catch (JSONException e) {
			// TODO: handle exception
			util.ShowMessage(e.getMessage(), con);
			
		}
		 

	}
	public Account(){
		
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
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

}
