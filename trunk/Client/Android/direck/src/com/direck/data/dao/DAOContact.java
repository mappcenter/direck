package com.direck.data.dao;

import java.util.ArrayList;
import java.util.List;

import com.direck.data.dblite;
import com.direck.data.table.Contact;
import com.direck.models.Friend;
import com.direck.utils.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DAOContact {
	// Database fields
		private SQLiteDatabase database;
		private dblite dbHelper;
		private String[] allColumns = { dblite.COLUMN_ID,
				dblite.COLUMN_AccountID, dblite.COLUMN_ContactName,
				dblite.COLUMN_ContactNumber,
				dblite.COLUMN_FriendID, dblite.COLUMN_ModifiedDate,
				dblite.COLUMN_Status };

		public DAOContact(Context context) {
			dbHelper = new dblite(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Contact createContact(String accountID, String contactName,
				String contactNumber, String FriendID, String status) {
			ContentValues values = new ContentValues();
			values.put(dblite.COLUMN_AccountID, accountID);
			values.put(dblite.COLUMN_ContactName, contactName);
			values.put(dblite.COLUMN_ContactNumber, contactNumber);
			values.put(dblite.COLUMN_FriendID, FriendID);
			values.put(dblite.COLUMN_ModifiedDate, util.getDateTime());
			values.put(dblite.COLUMN_Status, status);
			try {
				long insertId = database.insert(dblite.TABLE_CONTACT, null,
						values);
				Cursor cursor = database.query(dblite.TABLE_CONTACT,
						allColumns, dblite.COLUMN_ID + " = " + insertId,
						null, null, null, null);
				cursor.moveToFirst();
				Contact newContact = cursorToContact(cursor);
				cursor.close();
				return newContact;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;

		}

		public void deleteContact(Contact contact) {
			try {
				String id = contact.getID();
				System.out.println("contact with ID deleted: " + id);
				database.delete(dblite.TABLE_CONTACT,
						dblite.COLUMN_ID + " = " + id, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		public void deleteAllContact() {
			try {
				database.delete(dblite.TABLE_CONTACT,
						null, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		public List<Contact> getAllContacts() {
		    List<Contact> contacts = new ArrayList<Contact>();

		    Cursor cursor = database.query(dblite.TABLE_CONTACT,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Contact obj = cursorToContact(cursor);
		      contacts.add(obj);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return contacts;
		  }
		public ArrayList<Friend> getFriendList(){
			ArrayList<Friend> friends = new ArrayList<Friend>();
			Cursor cursor = database.query(dblite.TABLE_CONTACT,
			        allColumns, null, null, null, null, null);

			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Friend obj = cursorToFriend(cursor);
			      friends.add(obj);
			      cursor.moveToNext();
			    }
			    // make sure to close the cursor
			    cursor.close();
			    return friends;
		}

		private Contact cursorToContact(Cursor cursor) {
			Contact contact = new Contact();
			contact.setID(cursor.getString(0));
			contact.setAccountID(cursor.getString(1));
			contact.setContactName(cursor.getString(2));
			contact.setContactNumber(cursor.getString(3));
			contact.setFriendID(cursor.getString(4));
			contact.setModifiedDate(cursor.getString(5));
			contact.setStatus(cursor.getString(6));
			return contact;
		}
		private Friend cursorToFriend(Cursor cursor) {
			Friend friend = new Friend(cursor.getString(2),cursor.getString(3),cursor.getInt(4),false);
			return friend;
		}
}
