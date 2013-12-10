package com.direck.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class dblite extends SQLiteOpenHelper{
	// table name
	public static final String TABLE_CONTACT = "CONTACTS";

	// common column name
	public static final String COLUMN_ID = "id";
	// contact table columns name
	public static final String COLUMN_AccountID = "AccountID";
	public static final String COLUMN_ContactName = "ContactName";
	public static final String COLUMN_ContactNumber = "ContactNumber";
	public static final String COLUMN_FriendID = "FriendID";
	public static final String COLUMN_ModifiedDate = "ModifiedDate";
	public static final String COLUMN_Status = "Status";
	// database info
	private static final String DATABASE_NAME = "direckDB";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String Create_Table_CONTACT = "create table "
			+ TABLE_CONTACT + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_AccountID
			+ " text, " + COLUMN_ContactName + " text not null, "
			+ COLUMN_ContactNumber + " text not null, " + COLUMN_FriendID
			+ " text, " + COLUMN_ModifiedDate + " text, "
			+ COLUMN_Status + " text);";

	public dblite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		try {
			database.execSQL(Create_Table_CONTACT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(dblite.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
			onCreate(db);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
