package com.example.younotif;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

public class Notifications {
	private NotifView notifView;
	private ArrayList<NotificationModel> notifications= new ArrayList<NotificationModel>();
	public Notifications(NotifView notifView) {
		this.notifView = notifView;
		notifications = new ArrayList<NotificationModel>();
	} 
	public boolean synchroServer(NotificationModel notif,Context ct) throws JSONException{

		String url = "http://mesmoyennes.fr/notifications/controller/addNotif.php?title="+notif.getTitle()+"&content="+notif.getContent()+"&type="+notif.getType()+"&day="+notif.getDay()+"&beginDay="+notif.getBeginDate()+"&endDay="+notif.getEndDate()+"&beginHour="+notif.getBeginHour()+"&endHour="+notif.getEndHour()+"&period="+notif.getPeriod()+"&target="+notif.getGroup();
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url.replace(' ', '+'));
		YouNotifDatabase db = new YouNotifDatabase(ct);

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
				return false;
			} else if (val == 1) {
				notifications.add(notif);
				String dateCrea = temp1.optString("date");
				String code = temp1.optString("code");

				db.addNotif(notif.getTitle(), "me", dateCrea, notif.getContent(), notif.getType(), notif.getPeriod(), notif.getDay(), notif.getBeginHour(), notif.getBeginDate(), notif.getEndDate(), notif.getEndHour(), code, notif.getGroup());
				return true;

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
		return false;

	}
	public boolean addNotification(NotificationModel notif,Context ct) throws JSONException{
		boolean result = synchroServer(notif,ct);
		
		notifView.refresh(notifications);
		return result;
	}
	
}
