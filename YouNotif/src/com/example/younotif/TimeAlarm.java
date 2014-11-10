package com.example.younotif;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TimeAlarm  extends BroadcastReceiver {
	 NotificationManager nm;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
        System.out.println("Notification built");
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		  CharSequence from = intent.getExtras().getString("title");
		  CharSequence message = intent.getExtras().getString("message");
		  PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		  Notification notif = new Notification(R.drawable.ic_launcher, "YouNotif vous notifie !", System.currentTimeMillis());
		  notif.setLatestEventInfo(context, from, message, contentIntent);
	      notif.vibrate = new long[] {0,200,100,200,100,200};

		  nm.notify(1, notif);
	} 

}
