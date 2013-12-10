package com.direck.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetail {
	String ID;
	String Name;
	String Address;
	String LocX;
	String LocY;
	String Owner;
	String CreateDate;
	String ModifiedDate;
	String Status;
	
	public ItemDetail(){
		
	}
	public ItemDetail (JSONObject json){
		try {
			ID = json.getString("Id");
	         Name = json.getString("Name"); 
	         Address = json.getString("Address"); 
	         LocX = json.getString("LocX");
	         LocY = json.getString("LocY");
	         Owner = json.getString("Owner");
	         CreateDate = json.getString("CreateDate");
	         ModifiedDate = json.getString("ModifiedDate");
	         Status = json.getString("Status");
 
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getLocX() {
		return LocX;
	}
	public void setLocX(String locX) {
		LocX = locX;
	}
	public String getLocY() {
		return LocY;
	}
	public void setLocY(String locY) {
		LocY = locY;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
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
