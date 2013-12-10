	package com.direck.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.direck.utils.util;
import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
	int accountID;
	int ItemID=0;
	int FriendID;
	int type;
	boolean ViewStatus;
	// more info
	String Name;
	String Address;
	double lattitude;
	double longitude;
	String shareby;
	String CreateDate;

	public Item() {

	}

	public Item(String name, String address, int type) {
		this.Name = name;
		this.Address = address;
		this.type = type;
	}

	public Item(int accountID, int itemID, int friendID, int type,
			boolean viewStatus, String name, String address, double lattitude,
			double longitude, String shareby, String createDate) {
		super();
		this.accountID = accountID;
		ItemID = itemID;
		FriendID = friendID;
		this.type = type;
		ViewStatus = viewStatus;
		Name = name;
		Address = address;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.shareby = shareby;
		this.CreateDate = createDate;
	}
	public Item(JSONObject json){
		try {
			
			this.accountID =Integer.parseInt(json.getString("AccountID"));
			ItemID = Integer.parseInt(json.getString("PointID"));
			FriendID = Integer.parseInt(json.getString("FriendID"));
			this.type = Integer.parseInt(json.getString("Type"));
			ViewStatus = Boolean.parseBoolean(json.getString("ViewStatus"));
			Name = json.getString("PointName");
			Address = json.getString("PointAddress");
			this.lattitude = Integer.parseInt(json.getString("PointLocX"));
			this.longitude = Integer.parseInt(json.getString("PointLocY"));
			
			this.CreateDate = json.getString("PointCreatedDate");
			if ((this.type == util.ShareType) ||(this.type == util.BookmarkType)){
				this.shareby =  json.getString("AccountName");
			}else {
				this.shareby =  json.getString("FriendName");
			}
		} catch (JSONException e) {
			// TODO: handle exception
			
		}
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getName() {
		return Name;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getItemID() {
		return ItemID;
	}

	public void setItemID(int itemID) {
		ItemID = itemID;
	}

	public int getFriendID() {
		return FriendID;
	}

	public void setFriendID(int friendID) {
		FriendID = friendID;
	}

	public boolean isViewStatus() {
		return ViewStatus;
	}

	public void setViewStatus(boolean viewStatus) {
		ViewStatus = viewStatus;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatLng(double lat, double lon) {
		setLattitude(lat);
		setLongitude(lon);
	}

	public LatLng getLatLng() {
		return new LatLng(getLattitude(), getLongitude());
	}

	public String getShareby() {
		return shareby;
	}

	public void setShareby(String shareby) {
		this.shareby = shareby;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * (int accountID, int itemID, int friendID, int type,
			boolean viewStatus, String name, String address, double lattitude,
			double longitude, String shareby, String createDate)(non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(accountID);
		dest.writeInt(ItemID);
		dest.writeInt(FriendID);
		dest.writeInt(type);
		dest.writeInt(ViewStatus ?1:0);
		dest.writeString(Name);
		dest.writeString(Address);
		dest.writeDouble(lattitude);
		dest.writeDouble(longitude);
		dest.writeString(shareby);
		dest.writeString(CreateDate);
		
		
		
		
		
	}

	Item(Parcel in) {
		this.accountID = in.readInt();
		this.ItemID = in.readInt();
		this.FriendID = in.readInt();
		this.type = in.readInt();
		this.ViewStatus = (in.readInt() == 1);
		this.Name = in.readString();
		this.Address = in.readString();
		this.lattitude = in.readDouble();
		this.longitude = in.readDouble();
		this.shareby = in.readString();
		this.CreateDate = in.readString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		public Item[] newArray(int size) {
			return new Item[size];
		}
	};
}
