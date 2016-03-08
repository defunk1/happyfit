package com.uic.happyfit;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmService { 
	    private Context context;
	    private PendingIntent mAlarmSender;
	    
	    AlarmManager am;
	    AlarmManager am2; 
	    AlarmManager am3;
	    AlarmManager am4;
	    public AlarmService(Context context) {
	        this.context = context;
	        mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
	    }
	     
	    public void startAlarm(){
	    	 
	        //Set the alarm to 10 seconds from now
	        Calendar c = Calendar.getInstance();  
	        c.set(Calendar.HOUR_OF_DAY, 00);  
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	      
	        // FOR 7AM MORNING 
	        am2 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        am2.setRepeating(AlarmManager.RTC_WAKEUP, 
	        				c.getTimeInMillis(), 
	        				AlarmManager.INTERVAL_HOUR, 
	        				mAlarmSender);
	
	   
	    } 
	    public void cancelAlarm(){
	    	//am.cancel(mAlarmSender);
	    	//am2.cancel(mAlarmSender);
	    }
}
