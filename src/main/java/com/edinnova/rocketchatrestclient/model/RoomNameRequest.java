package com.edinnova.rocketchatrestclient.model;

public class RoomNameRequest extends RoomRequest{

	private String name;
	
	public RoomNameRequest() {
		super();
	}
	
	public RoomNameRequest (String roomId, String name){
		super(roomId);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
