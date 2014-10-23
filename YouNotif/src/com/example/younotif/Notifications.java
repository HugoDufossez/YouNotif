package com.example.younotif;

import java.util.ArrayList;

public class Notifications {
	private NotifView notifView;
	private ArrayList<Notification> notifications= new ArrayList<Notification>();
	public Notifications(NotifView notifView) {
		this.notifView = notifView;
		notifications = new ArrayList<Notification>();
	} 
	
	public void addNotification(Notification notif){
		notifications.add(notif);
		notifView.refresh(notifications);
	}
	
}
