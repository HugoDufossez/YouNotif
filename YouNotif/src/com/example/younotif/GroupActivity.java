package com.example.younotif;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.younotif.R.id;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
public class GroupActivity extends Activity {
	public Activity currentAct;
	public String drawable = String.valueOf(R.drawable.delete);
	private YouNotifDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new YouNotifDatabase(this);

		setContentView(R.layout.activity_group);
		
		ListView lv = (ListView)findViewById(id.groups);
		lv.addHeaderView(new View(this));
	
		lv.addFooterView(new View(this));
		
		EditText textView = (EditText) findViewById(R.id.editText1);
		textView.setSingleLine();
		currentAct = this;
	}

	public boolean synchroWithServer(EditText tx) throws JSONException {
		String nameOfGroup = tx.getText().toString();

		String url = "http://mesmoyennes.fr/notifications/controller/addGroup.php?group="
				+ nameOfGroup;
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url.replace(' ', '+'));

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {

			HttpResponse response = httpclient.execute(httpget);
			String json_string = EntityUtils.toString(response.getEntity());
			JSONObject temp1 = new JSONObject(json_string);
			String value = "Pas de correspondance";
			int val = temp1.optInt("response");
			if (val == 0) {
				tx.setText("");
				Toast.makeText(GroupActivity.this,
						"Le groupe a été créé avec succés sur le serveur !",
						Toast.LENGTH_LONG).show();
				db.addGroup(nameOfGroup);
				this.recreate();
			} else if (val == 1) {
				tx.setText("");

				Toast.makeText(
						GroupActivity.this,
						"Le groupe existe déjà sur le serveur, vous le suivez maintenant !",
						Toast.LENGTH_LONG).show();
				db.addGroup(nameOfGroup);
				this.recreate();

			}else if (val == 3) {
				tx.setText("");
				String nameMod = "";
				JSONArray obj = temp1.getJSONArray("groups");
				
				for(int i=0 ; i< obj.length(); i++){   // iterate through jsonArray
					nameMod = obj.getString(i);
					 //nameMod =  jsonObject.toString();
					db.addGroup(nameMod);
				}
				Toast.makeText(
						GroupActivity.this,
						"Synchronisation avec mesmoyennes.fr réussie !"+ obj.length() +" modules sont liées !",
						Toast.LENGTH_LONG).show();
				//db.addGroup(nameOfGroup);
				this.recreate();

			} 
			else {
				Toast.makeText(GroupActivity.this,
						"Les champs n'ont pas été saisis correctement !",
						Toast.LENGTH_LONG).show();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(GroupActivity.this,
					"Erreur d'URL dans la requete serveur", Toast.LENGTH_SHORT)
					.show();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Toast.makeText(GroupActivity.this, "Problème d'encodage",
					Toast.LENGTH_SHORT).show();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Toast.makeText(GroupActivity.this, "Erreur de protocole client",
					Toast.LENGTH_SHORT).show();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			Toast.makeText(GroupActivity.this, "Connexion socket trop longue",
					Toast.LENGTH_SHORT).show();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			Toast.makeText(GroupActivity.this, "Connexion serveur trop lente",
					Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(GroupActivity.this, "IO Exception",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		getActionBar().setTitle("Groupes");
		final YouNotifDatabase db = new YouNotifDatabase(this);
		List<String> groups = db.getAllGroups();
		final List<String> ordre = new LinkedList<String>();
		ArrayList<HashMap<String, String>> myStringArray = null;
		if(groups.size()> 0){
			 myStringArray = new ArrayList<HashMap<String,String>>();
			int counter = 0;
		    for(String str:groups) 	{
		    	HashMap<String, String> tmp = new HashMap<String, String>();
		    	ordre.add(str);
		    	tmp.put("imgGroup",drawable);
		    	tmp.put("groupName",str);
		    	myStringArray.add(counter,tmp );
		    	counter++;
		    }

		} else {
			myStringArray = new ArrayList<HashMap<String,String>>();
			HashMap<String, String> tmp = new HashMap<String, String>();
			
			tmp.put("imgGroup",drawable);
			tmp.put("groupName","Aucun groupe");
	    	myStringArray.add(tmp);

		}
		SimpleAdapter adapter = new SimpleAdapter(this, myStringArray,
				R.layout.group_list_item_layout, new String[] { "imgGroup","groupName"}, new int[] {  R.id.imgGroup,R.id.groupName});
		
		

		ListView listView1 = (ListView) findViewById(R.id.groups);
		listView1.setAdapter(adapter);
		
	     OnItemClickListener listener = new OnItemClickListener() {
	         public void onItemClick(AdapterView<?> parent, View view, int position,
	             long id) {
	     		ListView listView2 = (ListView) findViewById(R.id.groups);

	 			db.deleteGroup(ordre.get(position-1));
	 		    
				/*Toast.makeText(GroupActivity.this, "Position : "+position,
						Toast.LENGTH_SHORT).show();*/

	 			currentAct.recreate();
	         }
	       };
	       listView1.setOnItemClickListener(listener);
		TextView tv = (TextView) findViewById(R.id.editText1);
		EditText textMessage = (EditText) findViewById(R.id.editText1);
		textMessage.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
		textMessage.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if (keyCode == EditorInfo.IME_ACTION_SEARCH
						|| keyCode == EditorInfo.IME_ACTION_DONE
						|| event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					// Toast.makeText(GroupActivity.this, "Mise à jour",
					// Toast.LENGTH_SHORT).show();
					try {
						synchroWithServer((EditText) findViewById(R.id.editText1));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});
		return true;
	
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
			this.recreate();
			return true;
		case R.id.action_new:
			// help action
			Intent intentApp2 = new Intent(GroupActivity.this,
					AddNotif.class);
			// setContentView(R.layout.activity_group);
			this.startActivity(intentApp2);

			return true;
		case R.id.action_group:
			// setContentView(R.layout.activity_group);
			Intent intentApp = new Intent(GroupActivity.this,
					GroupActivity.class);
			// setContentView(R.layout.activity_group);
			this.startActivity(intentApp);
			return true;
		case R.id.action_consult:
			// setContentView(R.layout.activity_group);
			Intent intentApp1 = new Intent(GroupActivity.this,
					ConsultActivity.class);
			// setContentView(R.layout.activity_group);
			this.startActivity(intentApp1);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
