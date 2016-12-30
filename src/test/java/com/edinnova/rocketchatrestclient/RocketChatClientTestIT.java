package com.edinnova.rocketchatrestclient;

import static org.junit.Assert.*;

import org.junit.Test;

import com.edinnova.rocketchatrestclient.RocketChatClient;
import com.edinnova.rocketchatrestclient.model.Room;
import com.edinnova.rocketchatrestclient.model.ServerInfo;
import com.edinnova.rocketchatrestclient.model.User;

/**
 * This IT tests basic functionality of the client.
 *
 * @author Ryne Fagin (rynefagin)
 * @since 0.1.1
 * @version 0.0.1
 */
public class RocketChatClientTestIT {

	private static final String TEST_CASE_8 = "test0008";
	private static final String TEST_CASE_7 = "test0007";
	private static final String TEST_CASE_6 = "test0006";
	private static final String TEST_CASE_5 = "test0005";
	private static final String TEST_CASE_4 = "test0004";
	private static final String TEST_CASE_3 = "test0003";
	private static final String TEST_CASE_2 = "test0002";
	private static final String TEST_CASE_1 = "test0001";
	private static final String TEST_CASE_1234 = "test1234";
	private static final String TEST_CASE_0 = "test0000";
	
	String serverUrl = "http://localhost/api/";
	String user = "admin";
	String password = "supersecret";
	RocketChatClient rc = new RocketChatClient(serverUrl, user, password);

	@Test
	public void testRocketCatExists() throws Exception {

		ServerInfo info = rc.getServerInformation();
		assertTrue("The Rocket.Chat Version is empty, when it shouldn't be.", !info.getVersion().isEmpty());

		User rocketCat = rc.getUser("rocket.cat");
		assertTrue("The Rocket.Cat user's id doesn't match what it should be.", "rocket.cat".equals(rocketCat.getId()));

	}
	
	@Test
	public void testCreateCloseAndOpenChannel() throws Exception{
		String roomNameTest = TEST_CASE_0;
		Room room = rc.createChannel(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
	
		boolean isClosed = false;
		Room[] rooms = rc.getChannels();
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.closeChannel(room.getId());
				isClosed = true;
				break;
			}
		}
		
		assertTrue("Error, channel did not exist for closing",isClosed);

		boolean isOpened = false;
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.openChannel(room.getId());
				isOpened = true;
				break;
			}
		}
		
		assertTrue("Error, channel did not exist for opening",isOpened);
	}
	
	@Test
	public void testCreateChannel() throws Exception {
		Room room = rc.createChannel(TEST_CASE_1234);
		assertTrue("Room Id shouldn't be null if the room was created",
				(room.getId() != null && !room.getId().isEmpty()));
	}
	
	@Test
	public void testCreateArchiveAndUnarchiveChannel() throws Exception{
		String roomNameTest = TEST_CASE_1;
		Room room = rc.createChannel(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
	
		boolean isArchived = false;
		Room[] rooms = rc.getChannels();
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.archiveChannel(room.getId());
				isArchived = true;
				break;
			}
		}
		
		assertTrue("Error, channel did not exist for archiving",isArchived);
		
		boolean isUnarchived = false;
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.unarchiveChannel(room.getId());
				isUnarchived = true;
				break;
			}
		}
		
		assertTrue("Error, channel did not exist for unarchiving",isUnarchived);
	}
	
	@Test
	public void testCreateCloseAndOpenGroup() throws Exception{
		String roomNameTest = TEST_CASE_2;
		Room room = rc.createGroup(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
	
		boolean isCalled = false;
		Room[] rooms = rc.getGroups();
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.closeGroup(room.getId());
				isCalled = true;
				break;
			}
		}
		
		assertTrue("Error, group did not exist for closing",isCalled);
	}
	
	@Test
	public void testCreateArchiveAndUnarchiveGroup() throws Exception{
		String roomNameTest = TEST_CASE_3;
		Room room = rc.createGroup(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
	
		boolean isArchived = false;
		Room[] rooms = rc.getGroups();
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.archiveGroup(room.getId());
				isArchived = true;
				break;
			}
		}
		
		assertTrue("Error, group did not exist for archiving",isArchived);

		boolean isUnarchived = false;
		for(Room room1: rooms ){
			if(room1.getName().equals(roomNameTest))
			{
				rc.unarchiveGroup(room.getId());
				isUnarchived = true;
				break;
			}
		}
		
		assertTrue("Error, group did not exist for unarchiving",isUnarchived);
	}
	
	@Test
	public void testCreateAndGetGroup() throws Exception{
		String roomNameTest = TEST_CASE_4;
		Room room = rc.createGroup(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
	
		Room room1 = rc.getGroupInfo(room.getId());
		
		assertTrue("Error, room was null", room1 != null);
		assertTrue("Error, group names were not equal", room1.getName().equals(roomNameTest));
	}
	
	@Test
	public void testRenameChannel() throws Exception {
		String roomNameTest = TEST_CASE_5;
		Room room = rc.createChannel(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
		assertEquals(TEST_CASE_5, room.getName());
		
		rc.renameChannel(room.getId(), TEST_CASE_6);
		room = rc.getChannelInfo(room.getId());
		assertEquals(TEST_CASE_6, room.getName());
	}

	@Test
	public void testRenameGroup() throws Exception {
		String roomNameTest = TEST_CASE_7;
		Room room = rc.createGroup(roomNameTest);
		assertTrue( "Room Id shouldn't be null if the room was created", (room.getId() !=null && !room.getId().isEmpty()));
		assertEquals(TEST_CASE_7, room.getName());
		
		rc.renameGroup(room.getId(), TEST_CASE_8);
		room = rc.getGroupInfo(room.getId());
		assertEquals(TEST_CASE_8, room.getName());
	}	
}
