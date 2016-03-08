package com.uic.happyfit.data;

import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@Deprecated
public class FoodDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	public static String LOGTAG = "happyfit";
	public FoodDataSource(Context context) {
		dbhelper = new HappyFitOpenHelper(context); 
	}
	public void open(){
		database = dbhelper.getWritableDatabase();
		Log.i(LOGTAG, "opened database");
	}
	public void close(){
		database.close();
		Log.i(LOGTAG, "database closed");
	}
	 

	public Long insert(String foodname, String calorie, String foodtype) {
		ContentValues values = new ContentValues(); 
		values.put(DataConstants.COLUMN_FOOD_NAME, "hello" );
		values.put(DataConstants.COLUMN_FOOD_TYPE,  "world" );
		long i = database.insert(DataConstants.TABLE_NAME_FOOD, null, values); 
		return i;	
		}

	public List<HashMap<String , Object>> getAllFoods(){
		return null;
	}
	
	
	
	public HashMap<String, Object> findFood(int row_id) { 
		String[] columns = { DataConstants.COLUMN_ROW_ID,
				DataConstants.COLUMN_FOOD_NAME, DataConstants.COLUMN_FOOD_TYPE,
				DataConstants.COLUMN_FOOD_CALORIE };
		String condition =   DataConstants.COLUMN_ROW_ID + " = "
				+ row_id;
		Cursor c = database.query(DataConstants.TABLE_NAME_FOOD, columns,
				condition, null, null, null, null);
		HashMap<String, Object> food = new HashMap<String, Object>();
		if (c != null) {
				 c.moveToFirst();
				 food.put(DataConstants.COLUMN_ROW_ID,
						c.getInt(c.getColumnIndex(columns[0])));
				 food.put(DataConstants.COLUMN_FOOD_NAME,
							c.getString(c.getColumnIndex(columns[1])));
				 food.put(DataConstants.COLUMN_FOOD_TYPE,
							c.getString(c.getColumnIndex(columns[2]))); 
		}
		return food;
	}
}

