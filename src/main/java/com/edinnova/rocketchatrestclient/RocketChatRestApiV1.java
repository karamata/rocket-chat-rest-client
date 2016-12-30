package com.edinnova.rocketchatrestclient;

import com.edinnova.rocketchatrestclient.model.ChannelHistoryRequest;
import com.edinnova.rocketchatrestclient.model.Room;
import com.edinnova.rocketchatrestclient.model.RoomNameRequest;
import com.edinnova.rocketchatrestclient.model.RoomRequest;
import com.edinnova.rocketchatrestclient.model.RoomUserRequest;
import com.edinnova.rocketchatrestclient.util.HttpMethods;

/**
 * Enumeration of the available REST API methods.
 *
 * @author Bradley Hilton (graywolf336)
 * @since 0.1.0
 * @version 0.0.1
 */
public enum RocketChatRestApiV1 {
    /** Retrieves a <strong>public</strong> channel's information. */
    ChannelsInfo("channels.info", HttpMethods.GET, true, null),
    /** Retrieves a list of all the <strong>public</strong> channels. */
    ChannelsList("channels.list", HttpMethods.GET, true, null),
    /** Creates a new <strong>public</strong> channel. */
    ChannelsCreate("channels.create", HttpMethods.POST, true, Room.class),
    /** Adds <strong>all</strong> users to the specified channel. */
    ChannelsAddAll("channels.addAll", HttpMethods.POST, true, RoomRequest.class),
    /** Archives a channel. */
    ChannelsArchive("channels.archive", HttpMethods.POST, true, RoomRequest.class),
    /** Unarchives a channel. */
    ChannelsUnarchive("channels.unarchive", HttpMethods.POST, true, RoomRequest.class),
    /** Closes a channel. */
    ChannelsClose("channels.close", HttpMethods.POST, true, RoomRequest.class),
    /** Cleans up a channel (removing messages) */
    ChannelsCleanHistory("channels.cleanHistory", HttpMethods.POST, true, ChannelHistoryRequest.class),
    /** Invites a user to join a channel **/
    ChannelsInvite("channels.invite", HttpMethods.POST, true, RoomUserRequest.class),
    /** Removes a user from the channel **/
    ChannelsKick("channels.kick", HttpMethods.POST, true, RoomUserRequest.class),
    /** Removes the callee from the channel **/
    ChannelsLeave("channels.leave", HttpMethods.POST, true, RoomRequest.class),
    /** Renames the channel **/
    ChannelsRename("channels.rename", HttpMethods.POST, true, RoomNameRequest.class),
    /** Adds the channel back to the user’s list of channels.**/
    ChannelsOpen("channels.open", HttpMethods.POST, true, RoomRequest.class),
    /** Deletes a chat message. */
    ChatDelete("chat.delete", HttpMethods.POST, true, null), //TODO: need a request class for this API
    /** Sends a new chat message */
    ChatPostMessage("chat.postMessage", HttpMethods.POST, true, null), //TODO need a request class for this API
    /** Retrieves information about a <strong>private</strong> group, but only if the user is part of it. */
    GroupsInfo("groups.info", HttpMethods.GET, true, null),
    /** Retrieves a list of all the <strong>private</strong> groups the auth'd user has joined. */
    GroupsList("groups.list", HttpMethods.GET, true, null),
    /** Creates a new <strong>private</strong> group. */
    GroupsCreate("groups.create", HttpMethods.POST, true, Room.class),
    /** Archives a group. */
    GroupsArchive("groups.archive", HttpMethods.POST, true, RoomRequest.class),
    /** Unarchives a group. */
    GroupsUnarchive("groups.unarchive", HttpMethods.POST, true, RoomRequest.class),
    /** Closes a group. */
    GroupsClose("groups.close", HttpMethods.POST, true, RoomRequest.class),
    /** Invites a user to join a group **/
    GroupsInvite("groups.invite", HttpMethods.POST, true, RoomUserRequest.class),
    /** Removes a user from the group **/
    GroupsKick("groups.kick", HttpMethods.POST, true, RoomUserRequest.class),
    /** Removes the callee from the group **/
    GroupsLeave("groups.leave", HttpMethods.POST, true, RoomRequest.class),
    /** Adds the group back to the user’s list of groups.**/
    GroupsOpen("groups.open", HttpMethods.POST, true, RoomRequest.class),
    /** Renames the group **/
    GroupsRename("groups.rename", HttpMethods.POST, true, RoomNameRequest.class),
    /** Retrieves a list of all the direct message rooms the auth'd user has. */
    ImsList("ims.list", HttpMethods.GET, true, null),
    /** Gets the information about the server, including version and build commit. */
    Info("info", HttpMethods.GET, false, null),
    /** Retrieves the user information from the server. */
    UsersInfo("users.info", HttpMethods.GET, true, null),
    /** Retrieves a list of all the users in the server. */
    UsersList("users.list", HttpMethods.GET, true, null);

    private String methodName;
    private HttpMethods httpMethod;
    private boolean requiresAuth;
    private Class bodyClass;

    private RocketChatRestApiV1(String methodName, HttpMethods httpMethod, boolean requiresAuth, Class bodyClass) {
        this.methodName = methodName;
        this.httpMethod = httpMethod;
        this.requiresAuth = requiresAuth;
        this.bodyClass = bodyClass;
    }

    /**
     * Gets the method name to be called to the server.
     *
     * @return the method name plus "v1/" at the start
     */
    public String getMethodName() {
        return "v1/" + this.methodName;
    }

    /**
     * Gets the {@link HttpMethods http method} which should be used.
     *
     * @return {@link HttpMethods http method} to be used
     */
    public HttpMethods getHttpMethod() {
        return this.httpMethod;
    }

    /**
     * Check whether the method requires authentication or not.
     *
     * @return whether this requires authentication or not
     */
    public boolean requiresAuth() {
        return this.requiresAuth;
    }
    
    /**
     * Gets the {@link Class class} which should be used as the body for the request.
     *
     * @return {@link Class class} to be used
     */
    public Class getBodyClass() {
    	return this.bodyClass;
    }
}
