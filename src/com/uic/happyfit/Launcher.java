package com.uic.happyfit;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Launcher extends Activity {
	String LOGTAG = "LOGTAG";
	ImageView mImgView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		getActionBar().hide();
		 
		mImgView = (ImageView) findViewById(R.id.img_launcher);
		mImgView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity();
			}
		});
	} 
	
	private void logger(String message){
		Log.i(LOGTAG, message);
	}
	private void quickToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	 
	private void startActivity() { 
		Intent i = new Intent(this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}
 
}
