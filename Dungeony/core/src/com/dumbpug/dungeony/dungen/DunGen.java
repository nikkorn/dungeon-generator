package com.dumbpug.dungeony.dungen;

import java.util.HashMap;
import com.dumbpug.dungeony.dungen.room.Anchor;
import com.dumbpug.dungeony.dungen.room.Cell;
import com.dumbpug.dungeony.dungen.room.Room;
import com.dumbpug.dungeony.dungen.room.RoomGroup;
import com.dumbpug.dungeony.dungen.room.RoomResources;
import com.dumbpug.dungeony.dungen.room.RoomResourcesReader;

/**
 * Generator of totally sweet dungeons.
 */
public class DunGen {
	
	/**
	 * Generate a dungeon!
	 * @param path The path to the directory containing the room/group files.
	 * @param configuration The configuration.
	 * @return The generated dungeon.
	 */
	public static Dungeon generate(String path, DunGenConfiguration configuration) {
		// Grab all of the room resources from disk.
		RoomResources resources = RoomResourcesReader.getResources("rooms");
		
		// Keep track of the number of times we have attempted to create the dungeon and failed.
		int dungeonGenerationFailureCount = 0;
		
		// Keep trying to generate the dungeon until we hit the attempt limit.
		while (dungeonGenerationFailureCount < configuration.dungeonGenerationRetries) {
			// Attempt to generate a dungeon.
			DunGenGenerationAttempt result = attemptDungeonGeneration(resources);

			// If we succeeded then we are done!
			if (result.getStatus() == DunGenGenerationAttemptStatus.SUCCESS) {
				return new Dungeon(result.getTiles(), configuration);
			}

			// We failed in this attempt to generate a dungeon!
			dungeonGenerationFailureCount++;
		}
		
		throw new RuntimeException("reached dungeon generation attempt limit!");
	}
	
	/**
	 * Attempt to generate a dungeon and return the result.
	 * @param resources The room resources.
	 * @return The result of attempting to generate a dungeon.
	 */
	private static DunGenGenerationAttempt attemptDungeonGeneration(RoomResources resources) {
		// Create the map to hold all of the placed dungeon cells.
		HashMap<Position, Cell> cells = new HashMap<Position, Cell>();
		
		// Create a map to hold the counts of generated rooms.
		RoomCountMap roomCounts = new RoomCountMap();
		
		// Add the spawn room to the center of the dungeon, this should always be a success.
		addRoom(0, 0, 0, resources.getRoom("spawn"), cells, roomCounts);

		
		
		
		
		
		// TODO Remove this!
		return new DunGenGenerationAttempt(DunGenGenerationAttemptStatus.FAIL, null);
	}
	
	/**
	 * Get whether the room can be generated at an anchor.
	 * @param resources The room resources.
	 * @param room The room to add.
	 * @param anchor The anchor.
	 * @param roomCounts The room counts.
	 * @param cells The existing placed dungeon cells.
	 * @return Whether the room can be generated at an anchor.
	 */
	private static boolean canRoomBeGenerated(RoomResources resources, Room room, Anchor anchor, RoomCountMap roomCounts, HashMap<Position, Cell> cells) {
		// Find the room group that the room is in (if there is one).
		RoomGroup roomGroup = null;
		for (RoomGroup group : resources.getRoomGroups()) {
			if (group.includesRoom(room)) {
				// We found a group that the room is in!
				roomGroup = group;
				break;
			}
		}
		
		// Check whether there is a restriction on the maximum number of times this room can be
		// generated. A max can be applied per group and per room, with the latter taking priority.
		if (room.getMaximum() != null) {
			// If the room has a max then return false if the count has already been met.
			if (roomCounts.getCount(room.getName()) >= room.getMaximum()) {
				return false;
			}
		} else if (roomGroup != null && roomGroup.getMaximum() != null) {
			// Get the total number of times that rooms in the same group have been generated.
			int roomGroupGenerationCount = 0;
			for (String roomName : roomGroup.getRoomNames()) {
				roomGroupGenerationCount += roomCounts.getCount(roomName);
			}

			// Have we already met the group max?
			if (roomGroupGenerationCount >= roomGroup.getMaximum()) {
				return false;
			}
		}
		
		// Check whether there is a restriction on the depth at which this room can be generated.
		// A depth range can be applied per group and per room, with the latter taking priority.
		if (room.getDepth() != null && !anchor.isWithinDepthRange(room.getDepth())) {
			return false;
		} else if (roomGroup != null && roomGroup.getDepth() != null && !anchor.isWithinDepthRange(roomGroup.getDepth())) {
			return false;
		}
		
		// Check to make sure that all of the cell positions that will be taken up by the room are available.
		for (Cell cell : room.getCells()) {
			// Get the absolute position of the room cell if it were to be generated.
			int cellPositionX = cell.getLocalPosition().getX() + anchor.getPosition().getX();
			int cellPositionY = cell.getLocalPosition().getY() + anchor.getPosition().getY();
			
			// If the cell position is already taken then we cannot generate the room.
			if (cells.containsKey(new Position(cellPositionX, cellPositionY))) {
				return false;
			}
		}
			
		// The room can be generated!
		return true;
	}
	
	/**
	 * Add a room to the dungeon.
	 * @param x The absolute x position at which to place the room entrance cell.
	 * @param y The absolute y position at which to place the room entrance cell.
	 * @param depth The depth at which the room is being added.
	 * @param room The room to add.
	 * @param cells The existing placed dungeon cells.
	 * @param roomCounts The counts of the generated rooms.
	 */
	private static void addRoom(int x, int y, int depth, Room room, HashMap<Position, Cell> cells, RoomCountMap roomCounts) {
		// Add a room count entry for this room.
		roomCounts.incrementCount(room.getName());

		// Increment the room count.
		roomCounts.put(room.getName(), roomCounts.get(room.getName()) + 1);
		
		// Add each cell of the room to the dungeon.
		for (Cell cell : room.getCells()) {
			// Get the absolute position of the cell.
			Position cellPosition = new Position(x + cell.getLocalPosition().getX(), y + cell.getLocalPosition().getY());
	
			cells.put(cellPosition, cell);		
		}
	}
}
