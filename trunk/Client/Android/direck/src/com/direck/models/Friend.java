package com.direck.models;

public class Friend {
	String contactName;
	String contactNUmber;
	int FriendID;
	boolean selected =false;

	public Friend(String contactName, String contactNUmber, int friendID,
			boolean selected) {
		super();
		this.contactName = contactName;
		this.contactNUmber = contactNUmber;
		FriendID = friendID;
		this.selected = selected;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNUmber() {
		return contactNUmber;
	}
	public void setContactNUmber(String contactNUmber) {
		this.contactNUmber = contactNUmber;
	}
	public int getFriendID() {
		return FriendID;
	}
	public void setFriendID(int friendID) {
		FriendID = friendID;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
