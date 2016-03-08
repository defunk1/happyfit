package com.uic.happyfit.data;

import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	public static String LOGTAG = "happyfit";

	public UserDataSource(Context context) {
		dbhelper = new HappyFitOpenHelper(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
		Log.i(LOGTAG, "opened database");
	}

	public void close() {
		database.close();
		Log.i(LOGTAG, "database closed");
	}

	public Long insert(String name, String height, String weight,
			String gender, String activityz) {
		ContentValues values = new ContentValues();
		values.put(DataConstants.COLUMN_USER_NAME, name);
		values.put(DataConstants.COLUMN_USER_HEIGHT, height);
		values.put(DataConstants.COLUMN_USER_WEIGHT, weight);
		values.put(DataConstants.COLUMN_USER_GENDER, gender);
		values.put(DataConstants.COLUMN_USER_BODY_TYPE, activityz);

		long i = database.insert(DataConstants.TABLE_USER, null, values);
		return i;
	} 
	public Long insert(String name, String age,String height, String weight,
			String gender, String bodyType) {
		ContentValues values = new ContentValues();
		values.put(DataConstants.COLUMN_USER_NAME, name);
		values.put(DataConstants.COLUMN_USER_AGE, age);
		values.put(DataConstants.COLUMN_USER_HEIGHT, height);
		values.put(DataConstants.COLUMN_USER_WEIGHT, weight);
		values.put(DataConstants.COLUMN_USER_GENDER, gender);
		values.put(DataConstants.COLUMN_USER_BODY_TYPE, bodyType);

		long i = database.insert(DataConstants.TABLE_USER, null, values);
		return i;
	} 

	public boolean isEmpty(){
		Boolean isEmpty = true;
		String[] columns = { DataConstants.COLUMN_ROW_ID,  }; 
		Cursor c = database.query(DataConstants.TABLE_USER, columns,
				DataConstants.COLUMN_ROW_ID+" = 1", null, null, null, null);
		if( c.getCount() >= 1 ){
			Log.i("logtag", ""+c.getCount());
			isEmpty = false;
		}
		Log.i("logtag", "value of ismepty is "+isEmpty.toString());
		return isEmpty;
	}

	
	public Boolean updateWeight(String weight){
		ContentValues values = new ContentValues();
		values.put(DataConstants.COLUMN_USER_WEIGHT, weight);
		
		int rowsAffected = database.update(DataConstants.TABLE_USER, 
				values,null, null);
		if(rowsAffected > 0){
			return true;
		}
		return false;
	}
	
	public Boolean updateBodyWeight(String bodyType){
		ContentValues values = new ContentValues();
		values.put(DataConstants.COLUMN_USER_BODY_TYPE, bodyType);
		
		int rowsAffected = database.update(DataConstants.TABLE_USER, 
				values,null, null);
		if(rowsAffected > 0){
			return true;
		}
		return false;
	}
	
	public HashMap<String, Object> findUser(int row_id) {
		HashMap<String, Object> user = new HashMap<String, Object>();
		String[] columns = { DataConstants.COLUMN_ROW_ID,
				DataConstants.COLUMN_USER_NAME,
				DataConstants.COLUMN_USER_AGE,
				DataConstants.COLUMN_USER_WEIGHT,
				DataConstants.COLUMN_USER_HEIGHT,
				DataConstants.COLUMN_USER_GENDER,
				DataConstants.COLUMN_USER_BODY_TYPE };
		
		String condition = DataConstants.COLUMN_ROW_ID + " = " + row_id;
		Cursor c = database.query(DataConstants.TABLE_USER, columns,
				condition, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
			user.put(DataConstants.COLUMN_ROW_ID,
					c.getInt(c.getColumnIndex(columns[0])));
			user.put(DataConstants.COLUMN_USER_NAME,
					c.getString(c.getColumnIndex(columns[1]))); 
			user.put(DataConstants.COLUMN_USER_AGE,
					c.getString(c.getColumnIndex(columns[2]))); 
			user.put(DataConstants.COLUMN_USER_WEIGHT,
					c.getString(c.getColumnIndex(columns[3])));
			user.put(DataConstants.COLUMN_USER_HEIGHT,
					c.getString(c.getColumnIndex(columns[4])));
			user.put(DataConstants.COLUMN_USER_GENDER,
					c.getString(c.getColumnIndex(columns[5])));
			user.put(DataConstants.COLUMN_USER_BODY_TYPE,
					c.getString(c.getColumnIndex(columns[6])));
	
		}
		return user;
	}
}