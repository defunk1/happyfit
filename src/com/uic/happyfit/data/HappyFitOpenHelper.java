package com.uic.happyfit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HappyFitOpenHelper extends SQLiteOpenHelper {
	public static final String CREATE_TABLE_FOOD = 
			" CREATE TABLE " +
			DataConstants.TABLE_NAME_FOOD +" ( "+
			DataConstants.COLUMN_ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
			DataConstants.COLUMN_FOOD_NAME+ " TEXT , "+
			DataConstants.COLUMN_FOOD_TYPE +" TEXT , " +
			DataConstants.COLUMN_FOOD_CALORIE +" TEXT ); ";
	
	
	public static final String CREATE_TABLE_USER = 
			" CREATE TABLE " +
			DataConstants.TABLE_USER + " ( " +
			DataConstants.COLUMN_ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , " +
			DataConstants.COLUMN_USER_NAME + " TEXT , " +
			DataConstants.COLUMN_USER_AGE + " TEXT , " +
			
			DataConstants.COLUMN_USER_GENDER + " TEXT ," +
			DataConstants.COLUMN_USER_HEIGHT + " TEXT , " +
			DataConstants.COLUMN_USER_WEIGHT + " TEXT , " +
			DataConstants.COLUMN_USER_BODY_TYPE + " TEXT ); ";
	public static final String CREATE_TABLE_PROGRESS = 
			"CREATE TABLE " +
			DataConstants.TABLE_PROGRESS + " ( "+
			DataConstants.COLUMN_ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
			DataConstants.COLUMN_PROGRESS_BODY_CURRENT +" TEXT , " +
			DataConstants.COLUMN_PROGRESS_BODY_PREVIOUS +" TEXT , "+
			DataConstants.COLUMN_PROGRESS_BODY_TARGET + " TEXT , "+
			DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR + " TEXT , "+
			DataConstants.COLUMN_PROGRESS_YEAR + " TEXT , "+
			DataConstants.COLUMN_PROGRESS_BODY_TYPE + " TEXT ); ";
	
	private static String DATABASE_NAME = "happyfit.db";
	private static int DATABASE_VERSION = 16;
	private static final String LOGTAG = "happyfit";
	
	public HappyFitOpenHelper(Context context ) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
		Log.i(LOGTAG , "open helper has been instantiated"); 
	}	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//db.execSQL( CREATE_TABLE_FOOD );
		//Log.i(LOGTAG, "table food has been created"); 
		//db.execSQL( CREATE_TABLE_DRINK ); 
		
		db.execSQL( CREATE_TABLE_USER );
		db.execSQL( CREATE_TABLE_PROGRESS );
		Log.i(LOGTAG," table Progress has been created");
		Log.i(LOGTAG, "table USERS has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ DataConstants.TABLE_USER );
		
		db.execSQL(" DROP TABLE IF EXISTS "+ DataConstants.TABLE_PROGRESS);
		Log.i(LOGTAG, "table progress has been dropped");
		
		onCreate(db);
		
	}
}