package com.example.younotif;

import android.R.layout;
import android.app.Activity;
import android.app.FragmentTransaction;
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
	private Notifications notifications;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		android.app.FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.hide(fm.findFragmentById(R.id.notifList));
		notifications = new Notifications(new NotifView((NotifList)fm.findFragmentById(R.id.notifList)));
		
		ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		android.app.FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		switch (item.getItemId()) {
		
		case R.id.action_refresh:
			// refresh
			ft.show(fm.findFragmentById(R.id.notifList));
			
			ft.commit();
			return true;
		case R.id.action_new:
			// help action
			ft.hide(fm.findFragmentById(R.id.notifList));
			AddNotif an = new AddNotif();
			an.notifications = notifications;
			ft.add(android.R.id.content,an);
			
			ft.addToBackStack("AddNotif");
			ft.commit();
			return true;
		case R.id.action_group:
			//setContentView(R.layout.activity_group);
            Intent intentApp = new Intent(MainActivity.this,  GroupActivity.class);
			//setContentView(R.layout.activity_group);
			this.startActivity(intentApp);
			return true;
		case R.id.action_consult:
			//setContentView(R.layout.activity_group);
            Intent intentApp1 = new Intent(MainActivity.this,  ConsultActivity.class);
			//setContentView(R.layout.activity_group);
			this.startActivity(intentApp1);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
