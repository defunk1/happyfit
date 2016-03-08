package com.uic.happyfit;

import java.util.List;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.uic.happyfit.data.DataConstants;

import android.app.Application;
import android.util.Log;

public class happyfit extends Application{
	
	protected static String LOGTAG = "LOG";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//Parse.enableLocalDatastore(this);
		// Enable Local Datastore. 
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "HPfqkJYx0yYsQlEPMJfOz4dakS6dofKwAI1c4zoB", "sRhP4GN0VP6fJx0QBRTzyia3VDNZDNL1PRG6HGDm");
		fetchDataCluster();
		
	}
	
	private void fetchDataCluster(){
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(DataConstants.COLLECTION_CLUSTER);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(final List<ParseObject> list, ParseException e) {
				if(e == null){
					ParseObject.unpinAllInBackground(DataConstants.COLLECTION_CLUSTER, new DeleteCallback() { 
						@Override
						public void done(ParseException e) {
							ParseObject.pinAllInBackground(DataConstants.COLLECTION_CLUSTER, list); 
							Log.i("Logtag", "Size of fetched Cluster data is " + list.size() );
							fetchDataMeals();
						}
					}); 
				}
				else{
					log("No internet access... retaining data");
				}
			}
		}); 
	}
	
	
	public void fetchDataMeals(){
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(DataConstants.COLLECTION_MEAL);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(final List<ParseObject> list, ParseException e) {
				if(e == null){
					ParseObject.unpinAllInBackground(DataConstants.COLLECTION_MEAL, new DeleteCallback() { 
						@Override
						public void done(ParseException e) {
							ParseObject.pinAllInBackground(DataConstants.COLLECTION_MEAL, list); 
						
							
							Log.i("Logtag", "Size of fetched Meal data is " + list.size() );
						}
					}); 
				}
				else{
					log("No internet access... retaining data");
				}
			}
		}); 
	}
	
	private void log(String message){
		Log.i(LOGTAG, message);
	}
}
