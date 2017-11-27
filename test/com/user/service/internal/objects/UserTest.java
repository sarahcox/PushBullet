package com.user.service.internal.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {
	
	@Test
	public void shouldCreateUser() {
		User user = new User("username", "accessToken", "creationTime", 12);
		assertEquals("username", user.getUsername());
		assertEquals("accessToken", user.getAccessToken());
		assertEquals("creationTime", user.getCreationTime());
		assertEquals(12, user.getNumOfNotificationsPushed());
	}

}
