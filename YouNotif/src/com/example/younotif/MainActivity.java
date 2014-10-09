package com.example.younotif;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		String[] myStringArray = new String[5];
		myStringArray[0]="cou";
		myStringArray[1]="cou";
		myStringArray[2]="cou";
		myStringArray[3]="cou";
		myStringArray[4]="cou";
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myStringArray);

		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.action_refresh:
			// refresh
			return true;
		case R.id.action_new:
			// help action
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
