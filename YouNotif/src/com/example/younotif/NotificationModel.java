package com.example.younotif;

import java.util.HashMap;

public class NotificationModel {
	private String group;
	private String title;
	private String type;
	private String content;
	private String beginDate;
	private String endDate;
	private String beginHour;
	private String endHour;
	private String day;
	private String period;
	private String notifCode;
	private String dataCreation;
	private String author;

	public String drawable = String.valueOf(R.drawable.ic_action_forward);
	HashMap<String, String> itemMap;
	public NotificationModel(String group, String title, String beginDate,
			String endDate, String day,String content) {
		super();
		setGroup(group);
		setTitle(title);
		setBeginDate(beginDate);
		setEndDate(endDate);
		setDay(day);
		setContent(content);
		createItemMap();
	}
	
	public NotificationModel(String group, String title, String beginDate,
			String endDate,String beginHour, String endHour, String day,String content,String type,String period) {
		super();
		this.group = group;
		this.title = title;
		this.beginDate = beginDate;
		this.beginHour = beginHour;
		this.day = day;
		this.content = content;
		this.period = period;
		this.endDate = endDate;
		this.endHour = endHour;
		this.type = type;
		
		
		createItemMap();
	}
	public void setAuthorDateCode(String notifCode, String dateCrea, String author) {
		this.notifCode = notifCode;
		this.dataCreation = dateCrea;
		this.author = author;
	}
	
	
	
	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
	}





	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	public void setDateCrea(String date) {
		this.dataCreation = date;
	}
	public String getDateCrea() {
		return dataCreation;
	}


	public void setNotifCode(String code) {
		this.notifCode = code;
	}
	
	public String getNotifCode() {
		return notifCode;
	}


	public HashMap<String, String> getItemMap() {
		return itemMap;
	}
	public void setItemMap(HashMap<String, String> itemMap) {
		this.itemMap = itemMap;
	}
	public void createItemMap() {
		this.itemMap = new HashMap<String, String>();
		itemMap.put("titre","Notification "+ type+" :");
		itemMap.put("description", "Groupe : "+group+" Titre : "+title);
		itemMap.put("img", drawable);
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBeginHour() {
		return beginHour;
	}
	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}

	public String getPeriod() {
		return period;
	}
	
	
}
