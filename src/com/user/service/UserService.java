package com.user.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.user.service.internal.objects.User;
import com.user.service.request.objects.PostCreateUserRequest;
import com.user.service.request.objects.SendNotificationRequest;
import com.user.service.response.objects.PostCreateUserResponse;
import com.user.service.response.objects.SendNotificationResponse;

/**
 * @author Sarah Cox
 *
 */
@Path("/UserService") 
public class UserService {
	
	private static final String CREATE_PUSH_URL = "https://api.pushbullet.com/v2/pushes"; 
	
	private static Map<String, User> users = new HashMap<>();
	
	/**
	 * Creates a new user 
	 * @param postCreateUserRequest - contains the user name and access token to register a new user
	 * @return postCreateUserResponse - contains user name, access token, creation time and number of notifications pushed
	 */
	@POST 
	@Path("/User") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostCreateUserResponse createUser(final PostCreateUserRequest postCreateUserRequest) { 
		final String username = postCreateUserRequest.getUsername();
		final String accessToken = postCreateUserRequest.getAccessToken();
		final String creationTimeAsString = createCreationTimeAsString();
		users.put(username, new User(username, accessToken, creationTimeAsString, 0));
		return new PostCreateUserResponse(username, accessToken, creationTimeAsString, 0);
	}

	/**
	 * Creates a date and returns it formatted as a String
	 * @return formatted date
	 */
	private String createCreationTimeAsString() {
		final SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		final Date date = new Date(); 
		return simpleDateFormat.format(date);
	}
	
	/**
	 * Gets all registered users
	 * @return registered users
	 */
	@GET
	@Path("/Users") 
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers() {
		return users.values();
	}
	
	/**
	 * Sends a notification to a registered user using the user name
	 * 
	 * @param sendNotificationRequest - contains the user name 
	 * @return SendNotificationResponse - contains result message
	 * @throws IOException
	 */
	@POST
	@Path("/Notification") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SendNotificationResponse sendNotification(final SendNotificationRequest sendNotificationRequest) throws IOException {

		final String username = sendNotificationRequest.getUsername();
		final User user = users.get(username);
		
		final URL url = new URL(CREATE_PUSH_URL); 
		final HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection(); 
		 
		httpUrlConnection.setRequestMethod("POST"); 
		httpUrlConnection.setRequestProperty("Access-Token", user.getAccessToken());
		httpUrlConnection.setRequestProperty("Content-Type", "application/json");
		httpUrlConnection.setDoOutput(true); 
		 
		final JsonObject json = Json.createObjectBuilder()
				.add("type", "note")
				.add("title", "Note title")
				.add("body","Note content")
				.build();
		
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpUrlConnection.getOutputStream());
		outputStreamWriter.write(json.toString());
		outputStreamWriter.flush();

		final int responseCode = httpUrlConnection.getResponseCode(); 
		return buildSendNotificationResponse(username, responseCode);
	}
	
	/**
	 * Builds a send notification response 
	 * @return a send notification response
	 */
	private SendNotificationResponse buildSendNotificationResponse(final String username, final int responseCode) {
		
		final SendNotificationResponse sendNotificationResponse = new SendNotificationResponse();
		
		if (responseCode == 200) {
			sendNotificationResponse.setResultMessage("Notification was sent");
			updateNumOfNotificationsPushed(username);
		} else {
			sendNotificationResponse.setResultMessage("Something went wrong, the result code was" + responseCode);
		}
		return sendNotificationResponse;
	}

	/**
	 * Updates the number of notifications pushed
	 */
	private void updateNumOfNotificationsPushed(final String username) {
	
		final User user = users.remove(username);
		int numOfNotificationsPushed = user.getNumOfNotificationsPushed();
		numOfNotificationsPushed++;
		user.setNumOfNotificationsPushed(numOfNotificationsPushed);
		users.put(user.getUsername(), user);
	}		
}
