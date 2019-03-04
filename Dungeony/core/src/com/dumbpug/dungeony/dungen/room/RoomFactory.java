package com.dumbpug.dungeony.dungen.room;

import org.json.JSONObject;

/**
 * Factory for creating Room and RoomGroup instances. 
 */
public class RoomFactory {
	
	/**
	 * Create a Room object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Room object based on a definition in JSON.
	 */
	public static Room createRoom(JSONObject object) {
		// Get the room name.
		String name = object.getString("name");
		
		// Get the minimum/maximum generation count.
		Integer minimum = object.has("minimum") ? object.getInt("minimum") : null;
		Integer maximum = object.has("maximum") ? object.getInt("maximum") : null;
		
		// Get the room generation chance.
		Double chance = object.has("chance") ? object.getDouble("chance") : null;
		
		// Create the room depth.
		Depth depth = object.has("depth") ? createDepthRange(object.getJSONObject("depth")) : null;
		
		// TODO Get the room cells.
		
		return new Room(name, minimum, maximum, chance, depth, null);
	}
	
	public static RoomGroup createRoomGroup(JSONObject object) {
		return null;
	}
	
	/**
	 * Create a Cell object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Cell object based on a definition in JSON.
	 */
	private static Cell createRoomCell(JSONObject object) {
		return null;
	}
	
	/**
	 * Create a Depth object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Depth object based on a definition in JSON.
	 */
	private static Depth createDepthRange(JSONObject object) {
		Integer minimum = object.has("minimum") ? object.getInt("minimum") : null;
		Integer maximum = object.has("maximum") ? object.getInt("maximum") : null;
		return new Depth(minimum, maximum);
	}
	
	/**
	 * Create a Position object based on a definition in JSON.
	 * @param object The definition.
	 * @return A Position object based on a definition in JSON.
	 */
	private static Position createPosition(JSONObject object) {
		return new Position(object.getInt("x"), object.getInt("y"));
	}
}
