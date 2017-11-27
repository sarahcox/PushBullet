package com.user.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.user.service.internal.objects.User;
import com.user.service.request.objects.PostCreateUserRequest;
import com.user.service.response.objects.PostCreateUserResponse; 


public class UserServiceTest {
	
	private UserService classUnderTest = new UserService();

	@Mock
	private PostCreateUserRequest postCreateUserRequest;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSetUsernameOnPostCreateUserResponse() {
		when(postCreateUserRequest.getUsername()).thenReturn("bbcUser1");
		
		final PostCreateUserResponse postCreateUserResponse = classUnderTest.createUser(postCreateUserRequest);
		assertEquals("bbcUser1", postCreateUserResponse.getUsername());
	}
	
	@Test
	public void shouldSetAccessTokenOnPostCreateUserResponse() {
		when(postCreateUserRequest.getAccessToken()).thenReturn("anAccessToken");
		
		final PostCreateUserResponse postCreateUserResponse = classUnderTest.createUser(postCreateUserRequest);
		assertEquals("anAccessToken", postCreateUserResponse.getAccessToken());
	}
	
	@Test
	public void shouldSetCreatedTimeOnPostCreateUserResponse() {
		final PostCreateUserResponse postCreateUserResponse = classUnderTest.createUser(postCreateUserRequest);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		Date d= new Date(); 
		String dateAsString = sdf.format(d);
		assertEquals(dateAsString, postCreateUserResponse.getCreationTime());
	}
	
	
	@Test
	public void shouldSetNumberOfNotificationsPushedOnPostCreateUserResponse() {
		final PostCreateUserResponse postCreateUserResponse = classUnderTest.createUser(postCreateUserRequest);
		assertEquals(0, postCreateUserResponse.getNumOfNotificationsPushed());
	}
	
	@Test
	public void listOfUsersShouldHaveSizeOfOne() {
		classUnderTest.createUser(postCreateUserRequest);
		Collection<User> users = classUnderTest.getUsers();
		assertEquals(1, users.size());
	}
	
	@Test
	public void userInListShouldHaveUsernameSet() {
		when(postCreateUserRequest.getUsername()).thenReturn("bbcUser1");
		classUnderTest.createUser(postCreateUserRequest);
		Collection<User> users = classUnderTest.getUsers();
		User firstUser = users.iterator().next();
		assertEquals("bbcUser1", firstUser.getUsername());
	}
	
	@Test
	public void userInListShouldHaveAccessTokenSet() {
		when(postCreateUserRequest.getAccessToken()).thenReturn("anAccessToken");
		classUnderTest.createUser(postCreateUserRequest);
		Collection<User> users = classUnderTest.getUsers();
		User firstUser = users.iterator().next();
		assertEquals("anAccessToken", firstUser.getAccessToken());
	}

	@Test
	public void userInListShouldHaveCreationTimeSet() {
		classUnderTest.createUser(postCreateUserRequest);
		Collection<User> users = classUnderTest.getUsers();
		SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		Date d= new Date(); 
		String dateAsString = simpleDateFormat.format(d);
		User firstUser = users.iterator().next();
		assertEquals(dateAsString, firstUser.getCreationTime());
	}
	
	@Test
	public void userInListShouldHaveNumberOfNotificationsPushedSet() {
		classUnderTest.createUser(postCreateUserRequest);
		Collection<User> users = classUnderTest.getUsers();
		User firstUser = users.iterator().next();
		assertEquals(0, firstUser.getNumOfNotificationsPushed());
	}
}
