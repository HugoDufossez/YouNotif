package com.example.younotif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NotifView {
	private NotifList notifList;
	ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
	public NotifView(NotifList notifList) {
		super();
		this.notifList = notifList;
	
		
	}
	public void refresh(ArrayList<Notification> notifications){
		listItems = new ArrayList<HashMap<String,String>>();
		for (Iterator iterator = notifications.iterator(); iterator.hasNext();) {
			Notification notification = (Notification) iterator.next();
			listItems.add(notification.getItemMap());
		}
		notifList.setListItems(listItems);
		notifList.setListAdapter(notifList.getAdapter());
	}

	
}
