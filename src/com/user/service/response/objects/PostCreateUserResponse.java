package com.user.service.response.objects;

public class PostCreateUserResponse {

	private String username;
	private String accessToken;
	private String creationTime;
	private int numOfNotificationsPushed;

	public PostCreateUserResponse(String username, String accessToken, String creationTime, int numOfNotificationsPushed) {
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

}
