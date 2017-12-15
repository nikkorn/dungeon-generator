package com.dumbpug.dungeondoom.dungen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.dumbpug.dungeondoom.dungen.cell.ICell;

/**
 * Generates dungeons.
 */
public class DungeonGenerator {
	
	/** The RNG to use in generating the dungeon. */
	private Random random;
	
	/** The cell to position mappings. */
	private HashMap<Point, ICell> cells;
	
	/** The configuration to use in generating the dungeon. */
	private Configuration configuration;
	
	/**
	 * Generate a dungeon using the specified configuration.
	 * @param configuration
	 * @return generated dungeon
	 */
	public Dungeon generate(Configuration configuration) {
		
		// Set the configuration to use in generating this dungeon.
		this.configuration = configuration;
		
		// Set the RNG to use in generating this dungeon.
		this.random = new Random(configuration.seed);
		
		// Create a new cell map.
		this.cells = new HashMap<Point, ICell>();
		
		// Generate our dungeon rooms.
		ArrayList<Room> rooms = this.generateRooms();
		
		// TODO Generate and draw some corridors between our rooms.
		
		// TODO Go over every space in the dungeon area and compare a series of patterns to the position.
		
		// TODO Determine which walls are actually reachable (not surrounded by other walls)
		
		return null;
	}
	
	/**
	 * Generate a dungeon using a purely default configuration.
	 * @return generated dungeon
	 */
	public Dungeon generate() {
		return generate(new Configuration());
	}
	
	/**
	 * Generate the dungeon rooms.
	 * @return list of rooms
	 */
	public ArrayList<Room> generateRooms() {
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		// Generate as many rooms as we need.
		while (rooms.size() < configuration.rooms) {
			
			// Randomly generate a room.
			Room room = Room.generate(random, configuration);
			
			// Check that the room is within the bounds of the dungeon.
		    // Also, check that it does not overlap an existing room.
		    if (roomIsWithinDungeonBounds(room) && !overlaps(room, rooms)) {
		      rooms.add(room);
		    }
		}
		
		// Return our generated rooms.
		return rooms;
	}

	/**
	 * Gets whether a room is within the bounds of the dungeon area.
	 * @param room
	 * @return is within dungeon bounds
	 */
	private boolean roomIsWithinDungeonBounds(Room room) {
		int min  = 1;
		int maxX = configuration.width - 2;
		int maxY = configuration.height - 2;
		
		// Check the vertical/horizontal bounds of the dungeon.
		boolean inVerticalBounds   = room.getY() >= min && (room.getY() + room.getHeight()) <= maxY;
		boolean inHorizontalBounds = room.getX() >= min && (room.getX() + room.getWidth()) <= maxX;
		
		// Return whether the entire room is within the horizontal and vertical dungeon bounds.
		return inVerticalBounds && inHorizontalBounds;
	}
	
	/**
	 * Gets whether the specified room interacts with an other rooms.
	 * @param room
	 * @param rooms
	 * @return has overlaps
	 */
	private boolean overlaps(Room room, ArrayList<Room> rooms) {
		
		// Check whether the specified room interacts with an other rooms.
		for (Room a : rooms) {
		    Room b = room;
		    
		    // Check for an overlap.
		    if(a.getX() < (b.getX() + b.getWidth()) && 
		    		(a.getX() + a.getWidth()) > b.getX() && 
		    		a.getY() < (b.getY() + b.getHeight()) && 
		    		(a.getY() + a.getHeight()) > b.getY()) {
		      // There was an overlap!
		      return true;
		    }
		}

		// There were no overlaps.
		return false;
	}
}
