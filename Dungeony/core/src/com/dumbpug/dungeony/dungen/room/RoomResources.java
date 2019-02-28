package com.dumbpug.dungeony.dungen.room;

import java.util.ArrayList;

/**
 * Holds rooms resources.
 */
public class RoomResources {
	/**
	 * The list of all rooms.
	 */
	private ArrayList<Room> rooms;
	/**
	 * The list of all room groups.
	 */
	private ArrayList<RoomGroup> groups;
	
	/**
	 * Create a new instance of the RoomResources class.
	 * @param rooms The list of all rooms.
	 * @param groups The list of all room groups.
	 */
	public RoomResources(ArrayList<Room> rooms, ArrayList<RoomGroup> groups) {
		this.rooms  = rooms;
		this.groups = groups;
	}
	
	/**
	 * Get a list of all rooms.
	 * @return A list of all rooms.
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Get a list of all rooms groups.
	 * @return A list of all room groups.
	 */
	public ArrayList<RoomGroup> getRoomGroups() {
		return groups;
	}
}
