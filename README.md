Push Bullet

This rest service provides a mechanism to register a new user and gives the ability 
to send the user notifications using only their username


Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

Prerequisites

To run this you will need Java, Eclipse, Apache Tomcat and Postman. You can download these here: 
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen1a
https://tomcat.apache.org/download-80.cgi
https://www.getpostman.com/

Once downloaded Eclipse, if it won't run, open the ini file, and add this line, replacing theJDKlocation with the jdk location:

 -vm
<theJDKlocation>\bin\javaw.exe 

This should go above -vmargs

You will also need to visit https://www.pushbullet.com/ and sign up. You can then go to your account and create an access token. 
This access token should be saved somewhere. You should also install PushBullet to a device of your choice (e.g web browser or mobile)

Installing

1) In Eclipse, import the PushBullet project. Once the project is imported, right click on PushBullet and then click on export.
Choose Web -> war. On the following screen, in the web project text field type PushBullet and then in the destination field,
click on browse and find the webapps folder in the tomcat install folder (e.g D:\Apache Tomcat\apache-tomcat-8.5.23\webapps). 
Select this as the destination folder. 

2) Set the JAVA_HOME and JRE_HOME system variables.

3) Open the command line and navigate to the bin folder in the apache tomcat install folder and then run catalina.bat start 

Testing
Using Postman, there are 3 urls that can be tested: 
1.	 http://localhost:8080/PushBullet/rest/UserService/Users/
	This can be tested by choosing "GET" as the verb and clicking on send

2.	http://localhost:8080/PushBullet/rest/UserService/User/
	This can be tested by choosing "POST" as the verb,  adding a header of "Content-Type" as the key and "application/json" as the value, adding a body of the following (choosing the raw option and JSON as the type) and clicking on send: 
	{
		"username": "bbcUser1",
		"accessToken": "theAccessTokenGeneratedEarlier"
	}
	This is the response that should be returned: 
	{
		"username": "bbcUser1",
		"accessToken": "theAccessTokenGeneratedEarlier",
		"creationTime": "2017-11-26T17:25:20",
		"numOfNotificationsPushed": 0
	}
3.	http://localhost:8080/PushBullet/rest/UserService/Notification/ 
	This can be tested by choosing "POST" as the verb,  adding a header of "Content-Type" as the key and "application/json" as the value, adding a body of the following (choosing the raw option and JSON as the type)  and clicking on send: 
	{
		"username": "bbcUser1"
	}
	You should see a response which says that the notification was sent and when this url is called again http://localhost:8080/PushBullet/rest/UserService/User/ then the number of notifications pushed should have increased by 1

Built With

Java 
Tomcat
