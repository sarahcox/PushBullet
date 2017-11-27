package com.user.service.internal.objects;

public class User {
	
	private String username;
	private String accessToken;
	private String creationTime;
	private int numOfNotificationsPushed;

	public User(final String username, final String accessToken, final String creationTime, final int numOfNotificationsPushed) {
		this.username = username;
		this.accessToken = accessToken;
		this.creationTime = creationTime;
		this.numOfNotificationsPushed = numOfNotificationsPushed;
	}

	public String getUsername() {
		return username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public int getNumOfNotificationsPushed() {
		return numOfNotificationsPushed;
	}

	public void setNumOfNotificationsPushed(int numOfNotificationsPushed) {
		this.numOfNotificationsPushed = numOfNotificationsPushed;
	}
}
