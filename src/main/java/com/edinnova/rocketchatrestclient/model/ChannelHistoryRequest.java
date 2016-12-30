package com.edinnova.rocketchatrestclient.model;

public class ChannelHistoryRequest {

	private String roomId;
	private String latest;
	private String oldest;
	private String inclusive;
	
	public ChannelHistoryRequest() {
	}

	public ChannelHistoryRequest(String roomId, String latest, String oldest, String inclusive) {
		this.roomId = roomId;
		this.latest = latest;
		this.oldest = oldest;
		this.inclusive = inclusive;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getLatest() {
		return latest;
	}

	public void setLatest(String latest) {
		this.latest = latest;
	}

	public String getOldest() {
		return oldest;
	}

	public void setOldest(String oldest) {
		this.oldest = oldest;
	}

	public String getInclusive() {
		return inclusive;
	}

	public void setInclusive(String inclusive) {
		this.inclusive = inclusive;
	}
}
