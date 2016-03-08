package com.uic.happyfit.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parse.ParseObject;

public class ProgressDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	public static String LOGTAG = "happyfit";

	public ProgressDataSource(Context context) {
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
 
	public Long insert(  
						String previous,
						String current, 
						String body_type, 
						String target,
						String weekOfTheYear,
						String year ) {
		
		ContentValues values = new ContentValues(); 
		values.put(DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS, previous);
		values.put(DataConstants.COLUMN_PROGRESS_BODY_CURRENT, current); 
		values.put(DataConstants.COLUMN_PROGRESS_BODY_TYPE, body_type);
		values.put(DataConstants.COLUMN_PROGRESS_BODY_TARGET, target);
		values.put(DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR, weekOfTheYear);
		values.put(DataConstants.COLUMN_PROGRESS_YEAR, year);
		
		long i = database.insert(DataConstants.TABLE_PROGRESS, null, values);
		return i;
	} 

	public boolean isEmpty(){
		Boolean isEmpty = true;
		String[] columns = { DataConstants.COLUMN_ROW_ID,  }; 
		Cursor c = database.query(DataConstants.TABLE_PROGRESS, columns,
				DataConstants.COLUMN_ROW_ID+" = 1", null, null, null, null);
		if( c.getCount() >= 1 ){
			Log.i("logtag", ""+c.getCount());
			isEmpty = false;
		}
		Log.i("logtag", "value of ismepty is "+isEmpty.toString());
		return isEmpty;
	}
	
	
	public HashMap<String, Object> getLatest() {
		HashMap<String, Object> progress = new HashMap<String, Object>();
		String[] columns = { DataConstants.COLUMN_ROW_ID,
				DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS,
				DataConstants.COLUMN_PROGRESS_BODY_CURRENT,
				DataConstants.COLUMN_PROGRESS_BODY_CURRENT, 
				DataConstants.COLUMN_PROGRESS_BODY_TARGET ,
				DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR,
				DataConstants.COLUMN_PROGRESS_YEAR };
		/*
		String condition = DataConstants.COLUMN_ROW_ID + " = " + row_id;*/
		Cursor c = database.query(DataConstants.TABLE_PROGRESS, columns,
				null, null, null, null, DataConstants.COLUMN_ROW_ID );
		
		if (c != null) {
			c.moveToLast();
			progress.put(DataConstants.COLUMN_ROW_ID,
					c.getInt(c.getColumnIndex(columns[0])));
			progress.put(DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS,
					c.getString(c.getColumnIndex(columns[1]))); 
			progress.put(DataConstants.COLUMN_PROGRESS_BODY_CURRENT,
					c.getString(c.getColumnIndex(columns[2]))); 
			progress.put(DataConstants.COLUMN_PROGRESS_BODY_TYPE,
					c.getString(c.getColumnIndex(columns[3])));
			progress.put(DataConstants.COLUMN_PROGRESS_BODY_TARGET,
					c.getString(c.getColumnIndex(columns[4])));
			progress.put(DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR,
					c.getString(c.getColumnIndex(columns[5])));
			progress.put(DataConstants.COLUMN_PROGRESS_YEAR,
					c.getString(c.getColumnIndex(columns[6])));
		}
		return progress;
	}
	
	public Boolean updatedProgress_btn(String pressedBtn, String rowId ){
		ContentValues values = new ContentValues();
		values.put(DataConstants.COLUMN_PROGRESS_BODY_TARGET, pressedBtn);
		int rowsAffected = database.update(DataConstants.TABLE_PROGRESS, 
				values, 
				DataConstants.COLUMN_ROW_ID +" = "+ rowId ,
				null);
		
		if(rowsAffected > 1){
			return true;
		}
		 return false;
	}

	public List<ParseObject> getAll() {
		//HashMap<String, Object> progress = new HashMap<String, Object>();
		String[] columns = { 
				DataConstants.COLUMN_ROW_ID,
				DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS,
				DataConstants.COLUMN_PROGRESS_BODY_CURRENT,
				DataConstants.COLUMN_PROGRESS_BODY_CURRENT, 
				DataConstants.COLUMN_PROGRESS_BODY_TARGET ,
				DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR,
				DataConstants.COLUMN_PROGRESS_YEAR };
		/*
		String condition = DataConstants.COLUMN_ROW_ID + " = " + row_id;*/
		Cursor c = database.query(DataConstants.TABLE_PROGRESS, columns,
				null, null, null, null, null );
		List<ParseObject> list = new ArrayList<ParseObject>();
		
			while( c.moveToNext() ){
			ParseObject po = new ParseObject("Progress");
		  	
			po.put(DataConstants.COLUMN_ROW_ID,
					c.getInt(c.getColumnIndex(columns[0])));
			po.put(DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS,
					c.getString(c.getColumnIndex(columns[1]))); 
			po.put(DataConstants.COLUMN_PROGRESS_BODY_CURRENT,
					c.getString(c.getColumnIndex(columns[2]))); 
			po.put(DataConstants.COLUMN_PROGRESS_BODY_TYPE,
					c.getString(c.getColumnIndex(columns[3])));
			po.put(DataConstants.COLUMN_PROGRESS_BODY_TARGET,
					c.getString(c.getColumnIndex(columns[4])));
			po.put(DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR,
					c.getString(c.getColumnIndex(columns[5])));
			po.put(DataConstants.COLUMN_PROGRESS_YEAR,
					c.getString(c.getColumnIndex(columns[6])));
			list.add(po);
		}
		return list;
	}
	
}
