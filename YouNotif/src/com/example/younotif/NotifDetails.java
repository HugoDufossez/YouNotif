package com.example.younotif;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotifDetails extends Activity implements OnClickListener {
	NotificationModel notif;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notif_detail_layout);
		YouNotifDatabase db = new YouNotifDatabase(this);
		List<NotificationModel> notifs = db.getAllNotifs();
		NotificationModel notif = notifs.get(YouNotifDatabase.currentNotifIndex);
		this.notif = notif;
		TextView txtGroup = (TextView) findViewById(R.id.detailTxtgroup);
		txtGroup.setText(notif.getGroup());
		TextView txtType = (TextView) findViewById(R.id.detailTxtType);
		txtType.setText(notif.getType());
		TextView txtPeriod = (TextView) findViewById(R.id.detailTxtPeriod);
		txtPeriod.setText(notif.getPeriod());
		TextView txtTitle = (TextView) findViewById(R.id.detailTxtTitle);
		txtTitle.setText(notif.getTitle());
		TextView txtContent = (TextView) findViewById(R.id.detailTxtContent);
		txtContent.setText(notif.getContent());
		TextView txtDay = (TextView) findViewById(R.id.detailTxtDay);
		txtDay.setText(notif.getDay());
		TextView txtBeginD = (TextView) findViewById(R.id.detailTxtBeginD);
		txtBeginD.setText(notif.getBeginDate());
		TextView txtBeginHour = (TextView) findViewById(R.id.detailTxtBeginHour);
		txtBeginHour.setText(notif.getBeginHour());
		TextView txtEndD = (TextView) findViewById(R.id.detailTxtEndD);
		txtEndD.setText(notif.getEndDate());
		TextView txtEndHour = (TextView) findViewById(R.id.detailTxtEndHour);
		txtEndHour.setText(notif.getEndHour());

		Button btn = (Button) findViewById(R.id.btnCalendar);
		btn.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (notif != null) {
			String y = "";
			String m = "";
			String d = "";
			StringTokenizer tokens = new StringTokenizer(notif.getBeginDate(),
					"[.-/]");
			d = tokens.nextToken();
			m = tokens.nextToken();
			y = tokens.nextToken();
			Calendar beginTime = Calendar.getInstance();
			String[] beginH = notif.getBeginHour().split(":");
			beginTime.set(Integer.parseInt(y), Integer.parseInt(m),
					Integer.parseInt(d), Integer.parseInt(beginH[0]),
					Integer.parseInt(beginH[1]));
			Calendar endTime = Calendar.getInstance();
			tokens = new StringTokenizer(notif.getEndDate(), "[.-/]");

			d = tokens.nextToken();
			m = tokens.nextToken();
			y = tokens.nextToken();
			String[] endH = notif.getEndHour().split(":");
			endTime.set(Integer.parseInt(m), Integer.parseInt(y),
					Integer.parseInt(d), Integer.parseInt(endH[0]),
					Integer.parseInt(endH[1]));

			Intent intent = new Intent(Intent.ACTION_EDIT)
					.setType("vnd.android.cursor.item/event")
					// Title
					.putExtra("title", notif.getTitle())
					// Content
					.putExtra("description", notif.getContent())

					// Date Begin/End
					.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
							beginTime.getTimeInMillis())
					.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
							endTime.getTimeInMillis());

			startActivity(intent);
		}
		// intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
		// calDate.getTimeInMillis());
		// intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
		// calDate.getTimeInMillis());
	}

}
