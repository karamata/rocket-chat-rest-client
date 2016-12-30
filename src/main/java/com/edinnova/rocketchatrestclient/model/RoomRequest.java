package com.edinnova.rocketchatrestclient.model;

public class RoomRequest {

	private String roomId;

	public RoomRequest() {
	}
	
	public RoomRequest (String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
}
