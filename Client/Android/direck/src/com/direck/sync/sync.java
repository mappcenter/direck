package com.direck.sync;

import java.util.List;

import com.direck.data.dao.DAOContact;
import com.direck.data.table.Contact;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

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
		for (int i = 0; i < lst.size(); i++) {
			Contact c = lst.get(i);
			if (c.getContactNumber().equals(phoneNumber))
				return true;
		}
		return false;
	}
	public static boolean syncFromServerToLocalDB(){
		return true;
	}
}
