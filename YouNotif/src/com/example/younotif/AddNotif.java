package com.example.younotif;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddNotif extends Activity implements OnClickListener{
	private Button btn;
	private View v;
	public Notifications notifications;
    public Context ct;
	public Context getCt(){
		return this.ct;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.group, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		getActionBar().setTitle("Nouvelle notif");
		return true;

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		YouNotifDatabase db = new YouNotifDatabase(this);
	    ArrayList<String> list = new ArrayList<String>();
	    this.ct = this;
	    this.notifications = new Notifications(new NotifView());
		setContentView(R.layout.add_notif_layout);
		Spinner spinner = (Spinner) findViewById(R.id.spGroup);
	    ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	    for(String str: db.getAllGroups()) 	{
	    	list.add(str);
	    }
	    
	    if(db.getAllGroups().size() == 0){
	    	list.add("Pas de groupe");
	    }
	    ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myAdapter1.notifyDataSetChanged();

		spinner.setAdapter(myAdapter1);
		myAdapter1.notifyDataSetChanged();
		Button button = (Button) findViewById(R.id.btnAdd);
		final Context locCt = this.ct;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
        		String title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
            	String group = ((Spinner) findViewById(R.id.spGroup)).getSelectedItem().toString();;

        		String type = ((Spinner) findViewById(R.id.spType)).getSelectedItem().toString();
        		String beginD =((EditText) findViewById(R.id.txtBeginDate)).getText().toString();
        		String endD = ((EditText) findViewById(R.id.txtEndDate)).getText().toString();
        		String day = ((Spinner) findViewById(R.id.spDay)).getSelectedItem().toString();
        		String content = ((EditText) findViewById(R.id.txtContent)).getText().toString();
        		String period = ((EditText) findViewById(R.id.periodField)).getText().toString();
        		String endHour = ((TimePicker) findViewById(R.id.TEndHour)).getCurrentHour()+":"+((TimePicker) findViewById(R.id.TEndHour)).getCurrentMinute();
        		String beginHour = ((TimePicker) findViewById(R.id.TBeginHour)).getCurrentHour()+":"+((TimePicker) findViewById(R.id.TBeginHour)).getCurrentMinute();

        		NotificationModel toAdd = new NotificationModel(group,  title, beginD, endD, beginHour,  endHour,  day, content, type, period);
        		
        		toAdd.setType(type);
        		toAdd.createItemMap();
                try {
        			boolean bool = notifications.addNotification(toAdd,locCt);
        			if(bool == false){
        				Toast.makeText(AddNotif.this, "Une erreur est survenue",
        						Toast.LENGTH_SHORT).show();

        			} else if(bool == true){
        				Toast.makeText(AddNotif.this, "Ajout réussi",
        						Toast.LENGTH_SHORT).show();

        			}
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
        });

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
		switch (item.getItemId()) {

		case R.id.action_refresh:
			// refresh
			return true;
		case R.id.action_new:
			// help action
            Intent intentAppb = new Intent(AddNotif.this,  AddNotif.class);
			this.startActivity(intentAppb);

			return true;
		case R.id.action_group:
			
			Intent intentApp = new Intent(AddNotif.this,
					GroupActivity.class);
			
			this.startActivity(intentApp);
			return true;
		case R.id.action_consult:
			// setContentView(R.layout.activity_group);
			Intent intentApp1 = new Intent(AddNotif.this,
					ConsultActivity.class);
			// setContentView(R.layout.activity_group);
			this.startActivity(intentApp1);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void onClick(View vo) {
		
    }
}