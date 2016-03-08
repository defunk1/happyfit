package com.uic.happyfit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;
import com.uic.happyfit.adapters.BodyTypeListItem;
import com.uic.happyfit.adapters.ItemListExercise;
import com.uic.happyfit.adapters.ItemListProgressReports;
import com.uic.happyfit.data.DataConstants;
import com.uic.happyfit.data.ProgressDataSource;
import com.uic.happyfit.data.UserDataSource;

public class ActivityRegister extends Activity {

	Button btnMale;
	Button btnFemale;
	String mSelectedGender = "";
	
	Button registerButton;
	UserDataSource userDataSource;
	ProgressDataSource progressDataSource;
	Calendar mCalendar; 
	String LOGTAG = "LOGTAG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		registerButton = (Button) findViewById(R.id.button_register);

		progressDataSource = new ProgressDataSource(this);
		
		
		userDataSource = new UserDataSource(this);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(saveUser() == false){
					quickToast("Error occured while saving");
				}
				else
					navigateToMain();
			}
		});
		List<ParseObject> bodyTypes = new ArrayList<ParseObject>();
		ParseObject object = new ParseObject("bodyTypes");
		object.put("title", "bedrest");
		object.put("subtitle", "refers to voluntarily lying in bed as a treatment, and not being confined to bed because of a health impairment");
		bodyTypes.add(object);
		
		object = new ParseObject("bodyTypes");
		object.put("title", "light");
		object.put("subtitle", "a person who spends no more than 2 hours a day in walking");
		bodyTypes.add(object);
		
		object = new ParseObject("bodyTypes");
		object.put("title", "sedentary");
		object.put("subtitle", "lifestyle is a type of lifestyle with no or irregular physical activity. usually they are couch potatoes");
		bodyTypes.add(object);
		
		object = new ParseObject("bodyTypes");
		object.put("title", "moderate");
		object.put("subtitle", "you are always on your feet most of the work day and do no structured exercise");
		bodyTypes.add(object);
		
		object = new ParseObject("bodyTypes");
		object.put("title", "heavy");
		object.put("subtitle", "you do heavy manual labor");
		bodyTypes.add(object);
		 
		
		ImageView imgPopUp = (ImageView)findViewById(R.id.imgPopUp);
		imgPopUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder( ActivityRegister.this );
				final View myView = getLayoutInflater().inflate(R.layout.text_body_type_popup, null);
	            builder1.setView(myView);
	            builder1.setTitle("Activity Level Description");
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("Done",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                    dialog.dismiss();	
	                }
	            });
	            
	            AlertDialog alert11 = builder1.create();
	            alert11.show();
			}
		});
		
		//	ArrayAdapter<String> adapter = new ItemList(getActivity(), months);
		/*	 
		ArrayAdapter<ParseObject> adapter  = new BodyTypeListItem(this, bodyTypes);
		spinner_bodyType = (Spinner)findViewById(R.id.registration_input_body_type);
		spinner_bodyType.setAdapter(adapter);
		*/
	} 
	private void quickToast(String message){
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	private void navigateToMain(){
		Intent i = new Intent(this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}
	private Boolean saveUser(){
		EditText input_name = (EditText)findViewById(R.id.registration_input_name);
		String _name = input_name.getText().toString();
		
		EditText input_age = (EditText)findViewById(R.id.registration_input_age);
		String _age = input_age.getText().toString();
		
		Spinner spinner_height = (Spinner)findViewById(R.id.registration_input_height);
		String _height = spinner_height.getSelectedItem().toString();
		
		Spinner spinner_weight = (Spinner)findViewById(R.id.registration_input_weight);
		String _weight = spinner_weight.getSelectedItem().toString();
		
		Spinner spinner_bodyType = (Spinner)findViewById(R.id.registration_input_body_type);
	
		String _bodyType = spinner_bodyType.getSelectedItem().toString();
		
		//Note Gender is set on top
		String year = mCalendar.get(Calendar.YEAR)+"";
		String weekOfTheYear = mCalendar.get(Calendar.WEEK_OF_YEAR)+""; //convert to string
		 long progress = progressDataSource.insert( _weight, _weight, _bodyType, DataConstants.KEY_VALUE_MAINTAIN, weekOfTheYear, year);
		 long userSave = userDataSource.insert(_name,_age,_height,_weight, mSelectedGender, _bodyType );
		 if(progress > -1 && userSave > -1){
			 return true;
		 }
		 return false;
	}
	private void initializeToggleButtons(){
		btnMale =  (Button) findViewById(R.id.register_button_male); 
		btnFemale =  (Button) findViewById(R.id.register_button_female);
		
		btnFemale.setOnClickListener(btnToggler);
		btnMale.setOnClickListener(btnToggler);
	}
	 
	protected OnClickListener btnToggler = new OnClickListener() { 
		@Override
		public void onClick(View v) { 
			 btnMale.setBackgroundColor(Color.WHITE);
			 btnFemale.setBackgroundColor(Color.WHITE);
			 
		    Button sv = (Button)v;
		    sv.setBackgroundColor(Color.LTGRAY);
		    if( sv.getText().toString().equals("Female") ){
		    	mSelectedGender = DataConstants.VALUE_USER_GENDER_FEMALE;
		    }
		    else
		    	mSelectedGender = DataConstants.VALUE_USER_GENDER_MALE;
		}
	};
	private void logger(String message){
		Log.i(LOGTAG, message);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		userDataSource.open();
		progressDataSource.open();
		mCalendar = Calendar.getInstance();
		initializeToggleButtons();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		progressDataSource.close();
		userDataSource.close();
	}
}
