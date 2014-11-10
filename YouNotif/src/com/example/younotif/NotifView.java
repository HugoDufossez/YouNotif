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
	public NotifView() {
		super();
	
		
	}

	public void refresh(ArrayList<NotificationModel> notifications){
		listItems = new ArrayList<HashMap<String,String>>();
		for (Iterator iterator = notifications.iterator(); iterator.hasNext();) {
			NotificationModel notification = (NotificationModel) iterator.next();
			listItems.add(notification.getItemMap());
		}
		//notifList.setListItems(listItems);
	}

	
}
