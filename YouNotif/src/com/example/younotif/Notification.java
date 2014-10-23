package com.example.younotif;

import java.util.HashMap;

public class Notification {
	private String group;
	private String title;
	private String content;
	private String beginDate;
	private String endDate;
	private String beginHour;
	private String endHour;
	private String day;
	HashMap<String, String> itemMap = new HashMap<String, String>();
	public Notification(String group, String title, String beginDate,
			String endDate, String day,String content) {
		super();
		setGroup(group);
		setTitle(title);
		setBeginDate(beginDate);
		setEndDate(endDate);
		setDay(day);
		setContent(content);
		createItemMap(title, content, String.valueOf(R.drawable.ic_action_forward));
	}
	
	
	
	
	
	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
	}





	public HashMap<String, String> getItemMap() {
		return itemMap;
	}
	public void setItemMap(HashMap<String, String> itemMap) {
		this.itemMap = itemMap;
	}
	public void createItemMap(String title, String description, String drawableImg) {
		this.itemMap = new HashMap<String, String>();
		itemMap.put("titre", title);
		itemMap.put("description", description);
		itemMap.put("img", drawableImg);
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
	
	
}
