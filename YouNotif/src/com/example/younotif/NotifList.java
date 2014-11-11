package com.example.younotif;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.ListFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class NotifList extends Activity implements OnItemClickListener {
	private ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> itemMap = new HashMap<String, String>();
	private ListItemAdapter adapter;
	private List<NotificationModel> lfinal;
	
	public static final int ID_NOTIFICATION = 2014;

	public void notifManager(String notificationTitle, String content) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher,
				notificationTitle, 100000);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, NotifList.class), 0);

		notification.setLatestEventInfo(this, notificationTitle, content,
				pendingIntent);
		notification.vibrate = new long[] { 0, 200, 100, 200, 100, 200 };

		notificationManager.notify(ID_NOTIFICATION, notification);

	}

	public boolean estValide(int annee, int mois, int jour) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.set(annee, mois, jour);
		try {
			c.getTime();
		} catch (IllegalArgumentException iAE) {
			return false;
		}

		return true;
	}

	public void planNotifFor48Hour(List<NotificationModel> list) {
		YouNotifDatabase db = new YouNotifDatabase(this);
		List<NotificationModel> notifs = list;
		Date todayD = new Date();
		int id = 0;
		if (notifs.size() > 0) {
			for (NotificationModel notif : notifs) {
				String notDate = notif.getBeginDate();
				String endDate = notif.getBeginDate();

				String[] dateSplit;
				String[] endDateSplit;
				String[] beginHourSplit;
				GregorianCalendar currentDate = new GregorianCalendar();
				GregorianCalendar endDateCal = new GregorianCalendar();

				if (notDate.contains("-")) {
					id++;
					dateSplit = notDate.split("-");
					endDateSplit = endDate.split("-");

					if (estValide(Integer.parseInt(dateSplit[2]),
							Integer.parseInt(dateSplit[1]),
							Integer.parseInt(dateSplit[0]))) {
						currentDate = new GregorianCalendar(
								Integer.parseInt(dateSplit[2]),
								Integer.parseInt(dateSplit[1])-1,
								Integer.parseInt(dateSplit[0]));
						endDateCal = new GregorianCalendar(
								Integer.parseInt(endDateSplit[2]),
								Integer.parseInt(endDateSplit[1])-1,
								Integer.parseInt(endDateSplit[0]));

					    Date aujourdhui = new Date(System.currentTimeMillis());
					 
					    // Obtenir la difference en milliseconde
					    long diff = -aujourdhui.getTime() + currentDate.getTimeInMillis();
					    	if(diff > 0) {
								planifierNotif(id, notif.getTitle(), notif.getContent(),
										diff);
								/*Toast.makeText(
										NotifList.this,
										notif.getTitle()+" - Temps : "+diff+" - "+currentDate.toString(),
										Toast.LENGTH_LONG).show();*/
						    }
					}

				} else if (notDate.contains("/")) {
					id++;
					dateSplit = notDate.split("/");
					endDateSplit = endDate.split("/");
					beginHourSplit = notif.getBeginHour().split(":");
					if (estValide(Integer.parseInt(dateSplit[2]),
							Integer.parseInt(dateSplit[1]),
							Integer.parseInt(dateSplit[0]))) {

						currentDate = new GregorianCalendar(
								Integer.parseInt(dateSplit[2]),
								Integer.parseInt(dateSplit[1])-1,
								Integer.parseInt(dateSplit[0]),
								Integer.parseInt(beginHourSplit[0]),
								Integer.parseInt(beginHourSplit[1])
								);
						endDateCal = new GregorianCalendar(
								Integer.parseInt(endDateSplit[2]),
								Integer.parseInt(endDateSplit[1])-1,
								Integer.parseInt(endDateSplit[0]));

					    Date aujourdhui = new Date(System.currentTimeMillis());
					    long diff = -(long)aujourdhui.getTime() + (long)currentDate.getTimeInMillis();
					    long diffEnd = - aujourdhui.getTime() + endDateCal.getTimeInMillis();
						if(diff > 0 || diffEnd < 0){
						       Calendar calendar = Calendar.getInstance();
					           calendar.set(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[1])-1,Integer.parseInt(dateSplit[0]),Integer.parseInt(beginHourSplit[0]),Integer.parseInt(beginHourSplit[1]));

								if(notif.getType().compareTo("Periodique") == 0) {
									diff = -(long)aujourdhui.getTime() + (long)currentDate.getTimeInMillis();
									diff = calendar.getTimeInMillis();

									if (diff > 0 && diffEnd < 0) {
										if (Integer.parseInt(notif.getPeriod()) < 21) {
											
											diff = -(long)aujourdhui.getTime() + (long)currentDate.getTimeInMillis();
											int days = 21/Integer.parseInt(notif.getPeriod());
											for(int i = 1; i <= days; i++){
												long diff1 = calendar.getTimeInMillis();
												/*Toast.makeText(
														NotifList.this,
														notif.getTitle()+" - "+days+" - "+diff,
														Toast.LENGTH_LONG).show();*/

											    planifierNotif(id, notif.getTitle(), notif.getContent(),
														diff1+(Integer.parseInt(notif.getPeriod())*24*60*60*1000));
											    id++;

											}

										}
									    

									} else if(diff < 0 && diffEnd < 0){
									       calendar = Calendar.getInstance();
								           calendar.set(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[1])-1,Integer.parseInt(dateSplit[0]),Integer.parseInt(beginHourSplit[0]),Integer.parseInt(beginHourSplit[1]));

										diff = -(long)aujourdhui.getTime() + (long)currentDate.getTimeInMillis();
										diff = calendar.getTimeInMillis();

										if (diff > 0 && diffEnd < 0) {

											diff = calendar.getTimeInMillis();

										    planifierNotif(id, notif.getTitle(), notif.getContent(),
													diff);
											/*Toast.makeText(
													NotifList.this,
													notif.getTitle()+" - - "+diff,
													Toast.LENGTH_LONG).show();*/

										}

									}
								} else if(notif.getType().compareTo("Ponctuelle") == 0) {
									   calendar = Calendar.getInstance();
							           calendar.set(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[1])-1,Integer.parseInt(dateSplit[0]),Integer.parseInt(beginHourSplit[0]),Integer.parseInt(beginHourSplit[1]));

									diff = -(long)aujourdhui.getTime() + (long)currentDate.getTimeInMillis();
									diff = calendar.getTimeInMillis();
									/*Toast.makeText(
											NotifList.this,
											notif.getTitle()+" | "+diff,
											Toast.LENGTH_LONG).show();*/
									if (diff > 0 && diffEnd < 0) {

										diff = calendar.getTimeInMillis();

									    planifierNotif(id, notif.getTitle(), notif.getContent(),
												diff);
									}
									
								}
							
						}
					}
				}
			}

		}

	}

	public void planifierNotif(int id, String notificationTitle,
			String content, long ms) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// Date dateToNotif = new Date(year,month,day,hour,min);


		Intent intent = new Intent(getApplicationContext(), TimeAlarm.class);
		intent.putExtra("title", notificationTitle);
		intent.putExtra("message", content);
		intent.putExtra("ms", ms);

		/*PendingIntent operation = PendingIntent.getBroadcast(this,
				(int) cal.getTimeInMillis(), intent,
				PendingIntent.FLAG_ONE_SHOT);*/
		PendingIntent Notifysender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.RTC_WAKEUP, ms,Notifysender);
		/*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, ms,0, Notifysender);*/
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		YouNotifDatabase db = new YouNotifDatabase(this);
		List<NotificationModel> notifs = db.getAllNotifs();
		
		

		
		setContentView(R.layout.activity_notif_list_layout);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.addHeaderView(new View(this));
		lfinal = new LinkedList<NotificationModel>();
		lv.addFooterView(new View(this));
		if (notifs.size() > 0) {
			for (NotificationModel notif : notifs) {
						notif.createItemMap();
						HashMap<String, String> itemMap1 = notif.itemMap;
						listItems.add(itemMap1);
						lfinal.add(notif);
						/*Toast.makeText(
								NotifList.this,
								notif.getType(),
								Toast.LENGTH_LONG).show();*/

			}
			if(lfinal.size() > 0){
				planNotifFor48Hour(lfinal);

			}
		} else {
			itemMap.put("titre", "Avertissement");
			itemMap.put("description", "Aucune notification à afficher");
			/* itemMap.put("img", String.valueOf(R.drawable.ic_action_forward)); */

			listItems.add(itemMap);

		}

		adapter = new ListItemAdapter(
				this,
				listItems,
				R.layout.list_item_layout,
				new String[] { "titre2", "titre", "description","originGroup","img" },
				new int[] { R.id.titre2, R.id.titre, R.id.description,R.id.originGroup ,R.id.img });
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(this);
	}
	
	public boolean extractNewNotifsFromInternet() throws JSONException {
		boolean returned = false;
		String url = "http://mesmoyennes.fr/notifications/controller/api.notif.php";
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url.replace(' ', '+'));
		YouNotifDatabase db = new YouNotifDatabase(this);

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
			int nbOfNotif = temp1.length();
			for (int i = 0; i < temp1.length(); i++) {
				JSONObject obj = temp1.getJSONObject("notif" + i);
				if (db.existGroup(obj.optString("CIBLE"))) {
					NotificationModel currentNotif = new NotificationModel(
							obj.optString("CIBLE"), obj.optString("TITLE"),
							obj.optString("BEGIN_DAY"),
							obj.optString("END_DAY"),
							obj.optString("BEGIN_HOUR"),
							obj.optString("END_HOUR"), obj.optString("DAY"),
							obj.optString("CONTENT"), obj.optString("TYPE"),
							obj.optString("PERIOD"));
					currentNotif.setAuthorDateCode(obj.optString("NOTIF_CODE"),
							obj.optString("DATE_CREATION"),
							obj.optString("AUTHOR"));
					db.addNotif(currentNotif);
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returned;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		getActionBar().setTitle("Notifications");

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// notifManager("Bienvenue sur YouNotif !","Consultez vos derniÃ¨res notifications");
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		switch (item.getItemId()) {

		case R.id.action_refresh:
			// refresh
			try {
				this.extractNewNotifsFromInternet();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			this.recreate();
			return true;
		case R.id.action_new:
			// help action
			Intent intentAppb = new Intent(NotifList.this, AddNotif.class);
			ActivityOptions options = ActivityOptions.makeThumbnailScaleUpAnimation(findViewById(R.id.action_new), Bitmap.createBitmap(100, 100, Config.RGB_565),-500,500);
			this.startActivity(intentAppb,options.toBundle());
			
			

			return true;
		case R.id.action_group:
			// setContentView(R.layout.activity_group);
			Intent intentApp = new Intent(NotifList.this, GroupActivity.class);
			options = ActivityOptions.makeThumbnailScaleUpAnimation(item.getActionView(), Bitmap.createBitmap(100, 100, Config.RGB_565),0,500);
			this.startActivity(intentApp,options.toBundle());
			
			return true;
		case R.id.action_consult:
			// setContentView(R.layout.activity_group);
			Intent intentApp1 = new Intent(NotifList.this,
					ConsultActivity.class);
			// setContentView(R.layout.activity_group);
			this.startActivity(intentApp1);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public ArrayList<HashMap<String, String>> getListItems() {
		return listItems;
	}

	public void setListItems(ArrayList<HashMap<String, String>> listItems) {
		this.listItems.clear();
		this.listItems.addAll(listItems);
		adapter.notifyDataSetChanged();
	}

	public SimpleAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ListItemAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		YouNotifDatabase.currentNotifIndex = position;
		//YouNotifDatabase db = new YouNotifDatabase(this);
		if (lfinal.size() > 0) {
			Intent intentApp1 = new Intent(NotifList.this, NotifDetails.class);
			ActivityOptions options = ActivityOptions.makeThumbnailScaleUpAnimation(view, Bitmap.createBitmap(100, 100, Config.RGB_565),0,500);
			this.startActivity(intentApp1,options.toBundle());

		}
	}
	public List<NotificationModel> getDisplayedNotifs() {
		return lfinal;
	}

}
