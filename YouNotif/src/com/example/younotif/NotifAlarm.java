package com.example.younotif;

public class NotifAlarm {
	private String title;
	private String content;
	private int minute;
	public NotifAlarm(String title, String content, int minute) {
		super();
		this.title = title;
		this.content = content;
		this.minute = minute;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
}
