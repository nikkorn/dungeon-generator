package com.dumbpug.dungeony.dungen;

import java.util.HashMap;
import com.dumbpug.dungeony.dungen.room.Cell;
import com.dumbpug.dungeony.dungen.room.Room;
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
		HashMap<String, Integer> roomCounts = new HashMap<String, Integer>();
		
		// Add the spawn room to the center of the dungeon, this should always be a success.
		addRoom(0, 0, 0, resources.getRoom("spawn"), cells, roomCounts);

		
		
		
		
		
		// TODO Remove this!
		return new DunGenGenerationAttempt(DunGenGenerationAttemptStatus.FAIL, null);
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
	private static void addRoom(int x, int y, int depth, Room room, HashMap<Position, Cell> cells, HashMap<String, Integer> roomCounts) {
		// Add a room count entry for this room if one does not already exist.
		if (!roomCounts.containsKey(room.getName())) {
			roomCounts.put(room.getName(), 0);
		}
		
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
