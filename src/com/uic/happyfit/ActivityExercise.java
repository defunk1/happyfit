package com.uic.happyfit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.uic.happyfit.adapters.ItemListExercise;
import com.uic.happyfit.data.DataConstants;
import com.uic.happyfit.data.UserDataSource;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityExercise extends ListActivity {
	HashMap<String, Object> mUser;
	UserDataSource usrDataSource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		usrDataSource = new UserDataSource(this);
		usrDataSource.open();
		mUser = usrDataSource.findUser(1);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ActvitityListExercises.class);
		startActivity(i);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		usrDataSource.close();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
		List<String> exercises = new ArrayList<String>(); 
		//If body type sedentary or light
		String bodyType = mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString();
		if( mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString().equals(DataConstants.VALUE_USER_ACITIVTY_SEDETARY) ||
				mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString().equals(DataConstants.VALUE_USER_ACTIVITY_LIGHT)){  
			exercises.add("["+ bodyType +"]"+" Chest Exercises");
			exercises.add("["+ bodyType +"]"+" Shoulder Exercises");
			exercises.add("["+ bodyType +"]"+" Back Exercises");
			exercises.add("["+ bodyType +"]"+" Arm Exercises");
			exercises.add("["+ bodyType +"]"+" Torso Exercises");
			exercises.add("["+ bodyType +"]"+" Leg Exercises Exercises");
			
		} 
		else if( mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString().equals(DataConstants.VALUE_USER_ACTIVITY_MODERATE) ||
				mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString().equals(DataConstants.VALUE_USER_ACITIVTY_SEDETARY)){ 
			exercises.add("["+ bodyType +"]"+" Tennis");
			exercises.add("["+ bodyType +"]"+" Swimming"); 
			exercises.add("["+ bodyType +"]"+" Frisbee"); 
			exercises.add("["+ bodyType +"]"+" Biking"); 
			exercises.add("["+ bodyType +"]"+" Jogging");  
				
		} 
				exercises.add("Office Work \t- Calories Burned : 53");
				exercises.add("House Cleaning \t- Calories Burned : 89");
				exercises.add("Gardening \t- Calories Burned : 179");
				exercises.add("Fishing \t- Calories Burned : 143");
				exercises.add("Bowling \t- Calories Burned : 107"); 
				exercises.add("Aerobics \t- Calories Burned : 214"); 
				exercises.add("Walking \t- Calories Burned : 148"); 
				exercises.add("Yoga \t- Calories Burned : 103"); 
			ArrayAdapter<String> adapter  = new ItemListExercise(this, exercises);
			
			 
		 
			this.setListAdapter(adapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_exercise, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
