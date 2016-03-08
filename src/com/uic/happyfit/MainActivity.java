package com.uic.happyfit;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.uic.happyfit.data.DataConstants;
import com.uic.happyfit.data.FoodDataSource;
import com.uic.happyfit.data.ProgressDataSource;
import com.uic.happyfit.data.UserDataSource;
import com.uic.happyfit.data.WeightCalculator;

public class MainActivity extends Activity {
	String LOGTAG = "LOGTAG";
	Boolean isRegistered = false;
	Button fetchmeBtn;
	FoodDataSource fds;
	UserDataSource usrDataSource;
	ProgressDataSource pgressDataSource;
	
	HashMap<String, Object> mUser;
	HashMap<String, Object> mCurrentWeekProgress;
	
	EditText et1;
	EditText et2;
	
	ParseObject mParseObjectCluster;
	List<ParseObject> mListMeals;
	
	ParseObject food;
	String myFoodCluster = "";
	/*NotificationManager nm;*/
	 
	ButtonFloat increase;
	ButtonFloat maintain;
	ButtonFloat reduce;
	
	int mDayOfTheWeek;
	int mWeekOfTheMonth;
	int mWeekOfTheYear;
	int mYear;
	Menu mMenu;
	int increaseBackgroundColor = Color.parseColor("#0277bd");
	int maintainBackgroundColor = Color.parseColor("#2e7d32");
	int reduceBackgroundColor = Color.parseColor("#c62828");
	
	String dietTarget = ""; 
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		tv =(TextView) findViewById(R.id.main_text200);
		
		AlarmService as = new AlarmService(this);
		as.startAlarm();
		 
		increase = (ButtonFloat)findViewById(R.id.increase);
		increase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 toggleWeight();
				 tv.setText("( +200 )");
				 tv.setTextColor( increaseBackgroundColor );
				 dietTarget = "increase";

				 getFromLocalClusters();
				((ButtonFloat)v).setVisibility(View.GONE);
				// TODO 
				// UPDATE ROW CURRENT CLICKED 
				pgressDataSource.updatedProgress_btn(DataConstants.KEY_VALUE_INCREASE, 
						mCurrentWeekProgress.get(DataConstants.COLUMN_ROW_ID).toString() ); 
			}
		});
		
		
		maintain = (ButtonFloat)findViewById(R.id.maintain); 
		maintain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 toggleWeight();
				 tv.setText(" ");
				 dietTarget = "maintain";

				 getFromLocalClusters();
				((ButtonFloat)v).setVisibility(View.GONE);
				//TODO 
				// UPDATE ROW CURRENT CLICKED
				pgressDataSource.updatedProgress_btn(DataConstants.KEY_VALUE_MAINTAIN, 
						mCurrentWeekProgress.get(DataConstants.COLUMN_ROW_ID).toString() );

			}
		}); 
		reduce = (ButtonFloat)findViewById(R.id.reduce);
		reduce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleWeight();
				 tv.setText("( -200 )");
				 dietTarget = "reduce";
				 tv.setTextColor( reduceBackgroundColor );
				 getFromLocalClusters();
				((ButtonFloat)v).setVisibility(View.GONE);
				//TODO 
				// UPDATE ROW CURRENT CLICKED
				pgressDataSource.updatedProgress_btn(DataConstants.KEY_VALUE_REDUCE, 
						mCurrentWeekProgress.get(DataConstants.COLUMN_ROW_ID).toString() );
				
			}
		}); 
		
		//SEt visibility from database;
		 //todo update it from click listener later
			/*maintain.setVisibility(View.GONE);	*/ 
	}
	
	
	private void toggleWeight(){
		 increase.setVisibility(View.VISIBLE);
		 maintain.setVisibility(View.VISIBLE);
		 reduce.setVisibility(View.VISIBLE);
	}
	 
	

	private void getFromLocalClusters(){
		Log.i("logtag", myFoodCluster);
		
		//getFromLocalMeals();
		String adjustedFoodCluster = "";
		if(dietTarget.equalsIgnoreCase("increase")){
			adjustedFoodCluster = (Integer.parseInt(myFoodCluster)+200 )+""; 
		}else if(dietTarget.equalsIgnoreCase("reduce")){
			adjustedFoodCluster = (Integer.parseInt(myFoodCluster)-200 )+""; 
		}else{
			adjustedFoodCluster = myFoodCluster;	
		}
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(DataConstants.COLLECTION_CLUSTER); 
		query.fromLocalDatastore(); 
		//TODO
		query.whereEqualTo("type", adjustedFoodCluster);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if(e == null){
					Log.i("logtag", object.getObjectId() );
					mParseObjectCluster = object;
					try {
						updateEditTextFields();
					} catch (ParseException e1) { 
						e1.printStackTrace();
					}
					
				}
				else
				Log.i("Logtag", e.getMessage() +" why?");
			}
		});
	}
	private void logger(String message){
		Log.i(LOGTAG, message);
	}
	public void updateCurrentWeekProgress(){
		mCurrentWeekProgress = pgressDataSource.getLatest();
	}
	@Override
	protected void onStop() { 
		super.onStop();
		pgressDataSource.close();
		usrDataSource.close();
	}
	@Override
	protected void onResume() { 
		super.onResume();
		Calendar c = Calendar.getInstance();
		
		
		mDayOfTheWeek = c.get(Calendar.DAY_OF_WEEK);
		mWeekOfTheMonth =  c.get(Calendar.WEEK_OF_MONTH);
		mWeekOfTheYear =  c.get(Calendar.WEEK_OF_YEAR);
		mYear = c.get(Calendar.YEAR);
		 
		usrDataSource = new UserDataSource(this);
		usrDataSource.open();
		pgressDataSource = new ProgressDataSource(this);
		pgressDataSource.open();
		if( usrDataSource.isEmpty() == true){
			Intent i = new Intent(this, ActivityRegister.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);  
		}  
		else{
			updateCurrentWeekProgress();
			int weekOfTheYear_fromdb = Integer.parseInt(mCurrentWeekProgress.get(DataConstants.COLUMN_PROGRESS_WEEK_OF_YEAR).toString());
			/*Toast.makeText(this, "Comparing"+ weekOfTheYear_fromdb +" from db and today"+mWeekOfTheYear +" "+(weekOfTheYear_fromdb<mWeekOfTheYear) , Toast.LENGTH_LONG).show();
			*/
			if( mDayOfTheWeek == Calendar.SUNDAY && (weekOfTheYear_fromdb < mWeekOfTheYear) ){
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
				final View myView = getLayoutInflater().inflate(R.layout.progress_view, null);
				mBuilder.setView(myView);
				mBuilder.setTitle("Weekly Progress");
				mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 
						Spinner s =(Spinner)myView.findViewById(R.id.progress_body_type);
						String newBodyType = s.getSelectedItem().toString();
						
						EditText e = (EditText)myView.findViewById(R.id.progress_current);
						String newWeight = e.getText().toString();
						
						pgressDataSource.insert(mUser.get(DataConstants.COLUMN_USER_WEIGHT).toString(), 
												newWeight, 
												newBodyType, 
												dietTarget, 
												mWeekOfTheYear +"", 
												mYear +"" );
						//Replace old weight in user table
						usrDataSource.updateBodyWeight(newBodyType);
						usrDataSource.updateWeight(newWeight);
						updateCurrentWeekProgress();
						dialog.dismiss();	
					}
				});
				mBuilder.setCancelable(false);
				mBuilder.create().show();
				 
			
			}
			//------------------------------------------------------------------------------------------------
			// get updated row from progress datasource
			// get column maitain/reduce/increase
			// compare
			// hide button from retrieved row
			
			if( mCurrentWeekProgress.get(DataConstants.COLUMN_PROGRESS_BODY_TARGET).equals(DataConstants.KEY_VALUE_INCREASE) ){
				 tv.setText("( +200 )");
				 tv.setTextColor( increaseBackgroundColor );
				 dietTarget = "increase";
				increase.setVisibility(View.GONE);
			}else if(mCurrentWeekProgress.get(DataConstants.COLUMN_PROGRESS_BODY_TARGET).equals(DataConstants.KEY_VALUE_REDUCE)  ){
				tv.setText("( -200 )");
				 dietTarget = "reduce";
				 tv.setTextColor( reduceBackgroundColor );
				reduce.setVisibility(View.GONE);
			}else{
				 tv.setText(" ");
				 dietTarget = "maintain";

				maintain.setVisibility(View.GONE);
			}
			
			//-----------------------------------------------------------------------------------------------
			//pgressDataSource.insert( "21", "21", "sedentary", "increase" );
			//mCurrentWeekProgress = pgressDataSource.getLatest();
			//logger( mCurrentWeekProgress.get(DataConstants.COLUMN_PROGRESS_BODY_CURRENT)+"" );
			
			//mCurrentWeekProgress = pgressDataSource.getLatest();
			 
			et1 = (EditText) findViewById(R.id.editText1);
			et2 = (EditText) findViewById(R.id.editText2);
			 
		    mUser = usrDataSource.findUser(1); 
		    myFoodCluster = getCluster(); 
		    getFromLocalClusters(); 
		    double temp = Double.parseDouble( mUser.get(DataConstants.COLUMN_USER_WEIGHT).toString() ) ;

		    String bodyTypeFromDatabase = mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString();
			if(bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACITIVTY_BEDREST)){
				MenuItem item = mMenu.findItem(R.id.action_exercise);
				item.setVisible(false);
			}
			
			((TextView)findViewById(R.id.main_textView1)).setText( mUser.get(DataConstants.COLUMN_USER_NAME).toString() );
			((TextView)findViewById(R.id.main_textView2)).setText("Height :  "+mUser.get(DataConstants.COLUMN_USER_HEIGHT)+" ft" );
			
			((TextView)findViewById(R.id.main_textView_age)).setText("Age : "+ mUser.get(DataConstants.COLUMN_USER_AGE).toString() );
			
			((TextView)findViewById(R.id.main_textView_gender)).setText("Gender :  "+mUser.get(DataConstants.COLUMN_USER_GENDER).toString());
			 
			((TextView)findViewById(R.id.main_textView3)).setText("Weight :  "+ temp +" kilos");
			((TextView)findViewById(R.id.main_textView4)).setText( 
					"Activity level :  "+  mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString()); 
			((TextView)findViewById(R.id.main_textView5)).setText("Calorie : "+ myFoodCluster);
		} 
	}
	
	private int getBodyCode(){
		String bodyTypeFromDatabase = mUser.get(DataConstants.COLUMN_USER_BODY_TYPE).toString();
		if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACITIVTY_BEDREST) ){
			return DataConstants.CODE_BEDREST;
		}
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACITIVTY_SEDETARY) ){
			return DataConstants.CODE_SEDETARY;
		}
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_LIGHT) ){
			return DataConstants.CODE_LIGHT;
		} 	
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_MODERATE) ){
			return DataConstants.CODE_MODERATE;
		} 	
		else if( bodyTypeFromDatabase.equals(DataConstants.VALUE_USER_ACTIVITY_Active) ){
			return DataConstants.CODE_ACTIVE;
		} 	
		else return 0;
	}
	
	private String getCluster(){ 
		int bodyType = getBodyCode(); 
		WeightCalculator wc = new WeightCalculator();
		String b = mUser.get(DataConstants.COLUMN_USER_HEIGHT).toString() ;
		double result = 0;
		if( Double.parseDouble(b) < 5.0){
			result = wc.get_TER_BELOW( 
					mUser.get(DataConstants.COLUMN_USER_GENDER).toString(),	
					bodyType,   
					Double.parseDouble(b) );
		}
		else{
			result = wc.get_TER( 
					mUser.get(DataConstants.COLUMN_USER_GENDER).toString(),	
					bodyType,   
					b ); 
		}
	//	double result2 = wc.getWeightCluster("male", DataConstants.CODE_SEDETARY, 5.7);
		return (int)result  +"";
	}
	 
	private void updateEditTextFields() throws ParseException{
		//ParseRelation<ParseObject> relation = mParseObjectCluster.getRelation("breakfast"); 
		//ParseQuery<ParseObject> query = relation.getQuery();
		String currentMeal = ""; 
		Calendar c = Calendar.getInstance(); 
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if( hour >= 6 && hour <= 8 ){
				et1.setText("Breakfast Time");
				currentMeal = "breakfast";
			}else if( hour >= 11 && hour <= 13){
				et1.setText("Lunch time");
				currentMeal = "lunch";
			}else if( hour >= 14 && hour <= 16){
				et1.setText("Snacks");
				currentMeal = "snacks";
			}else if( hour >= 18 && hour <= 20 ){
				et1.setText("Dinner Time");
				currentMeal = "dinner";
			}
			else{
				//no time set
				et1.setText("No meal time");
				currentMeal = "notyet";
			}
				 
		if(currentMeal.equals("notyet")){
			//ParseObject parseMeal =  (ParseObject)mParseObjectCluster.get( currentMeal );
			et2.setText( "No scheduled food available" );
		}
		else{
			ParseObject parseMeal =  (ParseObject)mParseObjectCluster.get( currentMeal );
			parseMeal.fetchFromLocalDatastore();
			parseMeal.fetchIfNeeded();
			
			String concat = "Meal Calorie of the day: "+mParseObjectCluster.getString("type")+"\n";
			
			//ArrayList<String> list = new ArrayList<String>();     
			JSONArray jsonArray = parseMeal.getJSONArray("foods"); 
			if (jsonArray != null) { 
			   int len = jsonArray.length();
			   for (int i=0;i<len;i++){ 
			    try {
					concat += "\t "+jsonArray.get(i).toString()  +"\n"; 
			    	//list.add(jsonArray.get(i).toString());
				} catch (JSONException e) { 
					e.printStackTrace();
				}
			   } 
			}  
			Log.i("Logtag", parseMeal.getJSONArray("foods").toString() +"  -----------------prints here"  );
		 	et2.setText( concat ); 
		} 
	}
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
	 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		else if (id == R.id.action_reports) {
			Intent i = new Intent(this, ActivityReports.class);
			startActivity(i);
			return true;
		}
		else if (id == R.id.action_exercise) {
			Intent i = new Intent(this, ActivityExercise.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void quickToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
