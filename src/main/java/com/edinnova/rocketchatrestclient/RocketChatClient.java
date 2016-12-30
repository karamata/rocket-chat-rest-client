package com.edinnova.rocketchatrestclient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.edinnova.rocketchatrestclient.model.ChannelHistoryRequest;
import com.edinnova.rocketchatrestclient.model.Room;
import com.edinnova.rocketchatrestclient.model.RoomNameRequest;
import com.edinnova.rocketchatrestclient.model.RoomRequest;
import com.edinnova.rocketchatrestclient.model.RoomUserRequest;
import com.edinnova.rocketchatrestclient.model.ServerInfo;
import com.edinnova.rocketchatrestclient.model.User;

/**
 * Client for Rocket.Chat which relies on the REST API v1.
 * <p>
 * Please note, this client does <strong>not</strong> cache any of the results.
 *
 * @version 0.1.0
 * @since 0.0.1
 */
public class RocketChatClient {
	private static final String USER_ID_PARAM_KEY = "userId";
	private static final String ROOM_ID_PARAM_KEY = "roomId";
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

	private RocketChatClientCallBuilder callBuilder;

	/**
	 * Initialize a new instance of the client providing the server's url along
	 * with username and password to use.
	 *
	 * @param serverUrl
	 *            of the Rocket.Chat server, with or without it ending in
	 *            "/api/"
	 * @param user
	 *            which to authenticate with
	 * @param password
	 *            of the user to authenticate with
	 */
	public RocketChatClient(String serverUrl, String user, String password) {
		this.callBuilder = new RocketChatClientCallBuilder(serverUrl, user, password);
	}

	/**
	 * Forces a logout and clears the auth token if no exception happened.
	 *
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void logout() throws IOException {
		this.callBuilder.logout();
	}

	/**
	 * Gets the {@link ServerInfo} from the server, containing the version.
	 * 
	 * @return the {@link ServerInfo}
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public ServerInfo getServerInformation() throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.Info);

		if (!res.isSuccessful())
			throw new IOException("The call out to get the server information was unsuccessful.");

		if (!res.hasServerInfo())
			throw new IOException("The server information was not retrieved from the server.");

		return res.getServerInfo();
	}

	/**
	 * Gets <strong>all</strong> of the users from a Rocket.Chat server, if you
	 * have a ton this will take some time.
	 *
	 * @return an array of {@link User}s
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public User[] getUsers() throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.UsersList);

		if (!res.isSuccessful())
			throw new IOException("The call to get the Users was unsuccessful.");

		if (!res.hasUsers())
			throw new IOException("Get User Information failed to retrieve the users.");

		return res.getUsers();
	}

	/**
	 * Retrieves a {@link User} from the Rocket.Chat server.
	 *
	 * @param userId
	 *            of the user to retrieve
	 * @return an instance of the {@link User}
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful or there is no user
	 */
	public User getUser(String userId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.UsersInfo,
				new RocketChatQueryParams(USER_ID_PARAM_KEY, userId));

		if (!res.isSuccessful())
			throw new IOException("The call to get the User's Information was unsuccessful.");

		if (!res.hasUser())
			throw new IOException("Get User Information failed to retrieve a user.");

		return res.getUser();
	}

	/**
	 * Gets <strong>all</strong> of the public channels from a Rocket.Chat
	 * server, if you have a ton this will take some time.
	 *
	 * @return an array of {@link Room}s that are channels
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public Room[] getChannels() throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsList);

		if (!res.isSuccessful())
			throw new IOException("The call to get the Public Channels was unsuccessful.");

		if (!res.hasChannels())
			throw new IOException("Get Channels failed to retrieve the channels.");

		return res.getChannels();
	}

	/**
	 * Creates a new channel with only the creator added
	 * 
	 * @param channelName
	 *            the of the channel to create
	 * @return the {@link Room} which is the newly created channel
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public Room createChannel(String channelName) throws IOException {

		Room room = new Room();
		room.setName(channelName);

		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsCreate, null, room);

		if (!res.isSuccessful())
			throw new IOException("The call to create a Channel was unsuccessful.");

		if (!res.hasChannel())
			throw new IOException("The response does not contain any channel information.");

		return res.getChannel();
	}

	/**
	 * Adds <strong>all</strong> of the users of the Rocket.Chat server to the
	 * channel.
	 * 
	 * @param channelId
	 *            the "_id" of the room to add all users to
	 * @return {@link Room} which is the channel
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public Room addAllUsersToChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsAddAll,
				null, new RoomRequest(channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to get the Channel's Information was unsuccessful.");

		if (!res.hasChannel())
			throw new IOException("The response does not contain any channel information.");

		return res.getChannel();
	}

	/**
	 * Archives a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to archive
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void archiveChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsArchive, null,
				new RoomRequest(channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to archive the Channel was unsuccessful.");

	}
	
	/**
	 * Unarchives a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to unarchive
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void unarchiveChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsUnarchive, null,
				new RoomRequest(channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to unarchive the Channel was unsuccessful.");

	}

	/**
	 * Cleans up a channel, removing messages from the provided time range.
	 * 
	 * @param channelId
	 *            the "_id" of the room to clean the history of
	 * @param latest
	 *            {@link java.util.Date} end date of time range of messages to
	 *            clean
	 * @param oldest
	 *            {@link java.util.Date} start date of time range of messages to
	 *            clean
	 * @param inclusive
	 *            {@link java.lang.Boolean} whether messages which land on
	 *            latest and oldest should be included
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void cleanHistoryOfChannel(String channelId, Date latest, Date oldest, Boolean inclusive)
			throws IOException {

		ChannelHistoryRequest request = new ChannelHistoryRequest();
		
		if (latest != null && oldest != null && inclusive != null) {
			request.setLatest(df.format(latest));
			request.setOldest(df.format(oldest));
			request.setInclusive(inclusive.toString());
		} else {
			throw new IOException("latest, oldest and inclusive must not be null");
		}

		request.setRoomId(channelId);

		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsCleanHistory, null, request);

		if (!res.isSuccessful())
			throw new IOException("The call to clean the Channel was unsuccessful.");

	}

	/**
	 * Closes a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to close
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void closeChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsClose, null,
				new RoomRequest(channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to close the Channel was unsuccessful.");

	}

	/**
	 * Opens a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to open
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void openChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsOpen, null, 
				new RoomRequest(channelId));
		
		if (!res.isSuccessful())
			throw new IOException("The call to open the Channel was unsuccessful.");

	}

	/**
	 * Retrieves the information about the channel.
	 * 
	 * @param channelId
	 *            the "_id" of the room to get information
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 * @returns The {$link Room} of the channel
	 */
	public Room getChannelInfo(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsInfo,
				new RocketChatQueryParams(ROOM_ID_PARAM_KEY, channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to get the Channel's information failed.");

		if (!res.hasChannel())
			throw new IOException("The call to get the Channel's information failed.");

		return res.getChannel();

	}

	/**
	 * Invites a user to a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to invite user to
	 * @param userId
	 *            the "_id" of the user to invite
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void inviteUserToChannel(String channelId, String userId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsInvite, null, 
					new RoomUserRequest(channelId, userId));

		if (!res.isSuccessful())
			throw new IOException("The call to invite a User to a Channel was unsuccessful.");
	}
	
	/**
	 * Removes a user from a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to remove user from
	 * @param userId
	 *            the "_id" of the user to remove
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void removeUserFromChannel(String channelId, String userId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsKick, null, 
				new RoomUserRequest(channelId, userId));

		if (!res.isSuccessful())
			throw new IOException("The call to remove a User from a Channel was unsuccessful.");
	}
  	
	/**
	 * Removes the callee from a channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to leave
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void leaveChannel(String channelId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsLeave, null, 
				new RoomRequest(channelId));

		if (!res.isSuccessful())
			throw new IOException("The call to leave a Channel was unsuccessful.");
	}
	
	/**
	 * Renames the channel
	 * 
	 * @param channelId
	 *            the "_id" of the room to rename
	 * @param name
	 *            the new name of the room
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void renameChannel(String channelId, String name) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.ChannelsRename, null, 
				new RoomNameRequest(channelId, name));
		
		if (!res.isSuccessful())
			throw new IOException("The call to rename a Channel was unsuccessful");
	}
	
  	 /**
     * Gets <strong>all</strong> of the private groups the calling user has access to from a Rocket.Chat server, if you have a ton
     * this will take some time.
     *
     * @return an array of {@link Room}s that are groups
     * @throws IOException is thrown if there was a problem connecting, including if the result
     *             wasn't successful
     */
    public Room[] getGroups() throws IOException {
        RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsList);

        if (!res.isSuccessful())
            throw new IOException("The call to get the private Groups was unsuccessful.");

        if (!res.hasGroups())
            throw new IOException("Get Groups failed to retrieve the groups.");

        return res.getGroups();
    }
    
    /**
	 * Creates a new private group with only the creator added
	 * 
	 * @param groupName
	 *            the of the group to create
	 * @return the {@link Room} which is the newly created channel
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public Room createGroup(String groupName) throws IOException {

		Room room = new Room();
		room.setName(groupName);

		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsCreate, null, room);

		if (!res.isSuccessful())
			throw new IOException("The call to create a Group was unsuccessful.");

		if (!res.hasGroup())
			throw new IOException("The response does not contain any group information.");

		return res.getGroup();
	}

	/**
	 * Archives a group
	 * 
	 * @param groupId
	 *            the "_id" of the room to archive
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void archiveGroup(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsArchive, null, 
				new RoomRequest(groupId));
		
		if (!res.isSuccessful())
			throw new IOException("The call to archive the Group was unsuccessful.");

	}
	
	/**
	 * Unarchives a group
	 * 
	 * @param groupId
	 *            the "_id" of the room to unarchive
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void unarchiveGroup(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsUnarchive, null, 
				new RoomRequest(groupId));
		
		if (!res.isSuccessful())
			throw new IOException("The call to unarchive the Group was unsuccessful.");

	}

	/**
	 * Closes a group
	 * 
	 * @param groupId
	 *            the "_id" of the room to close
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void closeGroup(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsClose, null, 
				new RoomRequest(groupId));
		
		if (!res.isSuccessful())
			throw new IOException("The call to close the Group was unsuccessful.");

	}
	
	/**
	 * Opens a group
	 * 
	 * @param groupId
	 *            the "_id" of the room to open
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void openGroup(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsOpen, null, 
				new RoomRequest(groupId));
		
		if (!res.isSuccessful())
			throw new IOException("The call to open the Group was unsuccessful.");

	}

	/**
	 * Retrieves the information about the group.
	 * 
	 * @param groupId
	 *            the "_id" of the room to get information
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 * @returns The {$link Room} of the channel
	 */
	public Room getGroupInfo(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsInfo,
				new RocketChatQueryParams(ROOM_ID_PARAM_KEY, groupId));

		if (!res.isSuccessful())
			throw new IOException("The call to get the Group's information failed.");

		if (!res.hasGroup())
			throw new IOException("The call to get the Group's information failed.");

		return res.getGroup();

	}

	/**
	 * Invites a user to a private group
	 * 
	 * @param groupId
	 *            the "_id" of the room to invite user to
	 * @param userId
	 *            the "_id" of the user to invite
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void inviteUserToGroup(String groupId, String userId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsInvite, null, 
				new RoomUserRequest(groupId, userId));

		if (!res.isSuccessful())
			throw new IOException("The call to invite a User to a Group was unsuccessful.");
	}

	/**
	 * Removes a user from a private group
	 * 
	 * @param groupId
	 *            the "_id" of the room to remove user from
	 * @param userId
	 *            the "_id" of the user to remove
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void removeUserFromGroup(String groupId, String userId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsKick, null, 
				new RoomUserRequest(groupId, userId));

		if (!res.isSuccessful())
			throw new IOException("The call to remove a User from a Group was unsuccessful.");
	}
	
	/**
	 * Removes the callee from a group
	 * 
	 * @param groupId
	 *            the "_id" of the room to leave
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void leaveGroup(String groupId) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsLeave, null, 
				new RoomRequest(groupId));

		if (!res.isSuccessful())
			throw new IOException("The call to leave a Group was unsuccessful.");
	}
	
	/**
	 * Renames the group
	 * 
	 * @param groupId
	 *            the "_id" of the room to rename
	 * @param name
	 *            the new name of the room
	 * @throws IOException
	 *             is thrown if there was a problem connecting, including if the
	 *             result wasn't successful
	 */
	public void renameGroup(String groupId, String name) throws IOException {
		RocketChatClientResponse res = this.callBuilder.buildCall(RocketChatRestApiV1.GroupsRename, null, 
				new RoomNameRequest(groupId, name));
		
		if (!res.isSuccessful())
			throw new IOException("The call to rename a Group was unsuccessful");
	}
}