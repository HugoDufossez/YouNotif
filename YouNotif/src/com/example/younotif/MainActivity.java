package com.example.younotif;

import java.util.Calendar;

import android.R.layout;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//private Notifications notifications;
	public void planifierNotif(int id,String notificationTitle,String content,int day,int month,int year, int hour,int min){
		AlarmManager am =  (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);        
    	 
    	Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, min);
 
        Intent intent = new Intent(this, TimeAlarm.class);
        PendingIntent operation = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), operation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		YouNotifDatabase db = new YouNotifDatabase(this);
		planifierNotif(42,"test", "test", 2, 11, 2014, 10, 46);

		//android.app.FragmentManager fm = getFragmentManager();
		//FragmentTransaction ft = fm.beginTransaction();
		//ft.hide(fm.findFragmentById(R.id.notifList));
		//notifications = new Notifications(new NotifView((NotifList)fm.findFragmentById(R.id.notifList)));
		
		//ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

        Intent intentApp = new Intent(MainActivity.this,  NotifList.class);
		this.startActivity(intentApp);


		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*android.app.FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();*/
		switch (item.getItemId()) {
		
		case R.id.action_refresh:
			// refresh
			this.recreate();
			return true;
		case R.id.action_new:
			// help action
			//ft.hide(fm.findFragmentById(R.id.notifList));
			//AddNotif an = new AddNotif();
			//an.notifications = notifications;
			//ft.add(android.R.id.content,an);
			
			//ft.addToBackStack("AddNotif");
			//ft.commit();
            Intent intentApp = new Intent(MainActivity.this,  AddNotif.class);
			this.startActivity(intentApp);

			return true;
		case R.id.action_group:
			//setContentView(R.layout.activity_group);
            Intent intentApp1 = new Intent(MainActivity.this,  GroupActivity.class);
			//setContentView(R.layout.activity_group);
			this.startActivity(intentApp1);
			return true;
		case R.id.action_consult:
			//setContentView(R.layout.activity_group);
            Intent intentApp2 = new Intent(MainActivity.this,  ConsultActivity.class);
			//setContentView(R.layout.activity_group);
			this.startActivity(intentApp2);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
