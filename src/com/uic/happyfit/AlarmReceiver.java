package com.uic.happyfit;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

public class AlarmReceiver extends BroadcastReceiver  {
	static final int uniqueID = 1394885;
	@Override
    public void onReceive(Context context, Intent intent) {
		NotificationManager mNM;
		mNM = (NotificationManager)context.getSystemService( context.NOTIFICATION_SERVICE );
	    
        String message = "";
        Calendar c = Calendar.getInstance(); 
		int hour = c.get(Calendar.HOUR_OF_DAY);
		 
		if( hour == 6 ){ 
			message = "Prepare breakfast";
		}else if( hour ==  7){ 
			message = "Take your breakfast";
		}else if( hour == 11 ){ 
			message = "Prepare lunch";
		}else if( hour  == 12 ){ 
			message = "Take your lunch"; 
		}else if( hour == 14 ){ 
			message = "Prepare your snacks";
		}else if( hour == 15 ){ 
			message = "Take your snacks";
		}
		else if( hour == 18 ){
			message = "Prepare your dinner";
		}
		else if( hour == 19){
			message = "Take your dinner";
		}
		
		// Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, 
        		message,
        System.currentTimeMillis());
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(context, "REMINDER | HappyFit", message, contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number. We use it later to cancel.
        
       // notification.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        //notification.defaults = Notification.DEFAULT_ALL;
      
        // Send the notification.
        // We use a layout id because it is a unique number. We use it later to cancel.
       
    if( hour == 6 ){ 
   	 mNM.notify(uniqueID, notification);
	}else if( hour ==  7){ 
		 mNM.notify(uniqueID, notification);
	}else if( hour == 11 ){ 
		 mNM.notify(uniqueID, notification);
	}else if( hour  == 12 ){ 
		 mNM.notify(uniqueID, notification);
	}else if( hour == 14 ){ 
		 mNM.notify(uniqueID, notification);
	}else if( hour == 15 ){ 
		 mNM.notify(uniqueID, notification);
	}
	else if( hour == 18 ){
		 mNM.notify(uniqueID, notification);
	}
	else if( hour == 19){
		 mNM.notify(uniqueID, notification);
	}
      
		
	}
}
