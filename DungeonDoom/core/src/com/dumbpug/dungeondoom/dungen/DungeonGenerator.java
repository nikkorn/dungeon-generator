package com.dumbpug.dungeondoom.dungen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.dumbpug.dungeondoom.dungen.cell.*;

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
		// Generate some corridors between our rooms.
		this.generateCorridors(rooms);
		// Set the room cells.
		for (Room room : rooms) {
			this.setCell(new com.dumbpug.dungeondoom.dungen.cell.Room(), room.getX(), room.getY(), room.getWidth(), room.getHeight());
		}
		
		// TODO Go over every space in the dungeon area and compare a series of patterns to the position.
		
		// Determine which walls are actually reachable (not surrounded by other walls)
		this.findReachableWalls();
		
		// Return our generated dungeon.
		return new Dungeon(configuration, this.cells);
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
	private ArrayList<Room> generateRooms() {
		// Create our empty rooms list.
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
	
	/**
	 * Generate a number of corridors between rooms in the dungeon.
	 * @param rooms
	 */
	private void generateCorridors(ArrayList<Room> rooms) {
		// The previous room centre point.
		Point previous = null;
		// Process each room, carving out corridors between them.
		for (Room room : rooms) {
			// Make the current point the centre of the current room.
			Point current = room.getCentre();
			// Do we have tow rooms to connect?
			if (previous != null) {
				// Are we going vertically or horizontally first? Flip a coin.
				if (random.nextDouble() >= 0.5) {
					carveVerticalCorridor(current.x, Math.min(current.y, previous.y), Math.max(current.y, previous.y));
					carveHorizontalCorridor(previous.y, Math.min(current.x, previous.x), Math.max(current.x, previous.x));
				} else {
					carveHorizontalCorridor(current.y, Math.min(current.x, previous.x), Math.max(current.x, previous.x));
					carveVerticalCorridor(previous.x, Math.min(current.y, previous.y), Math.max(current.y, previous.y));
				}
			}
			// Set the current room centre to be the previous one.
			previous = current;
		}
	}
	
	/**
	 * Carve out a vertical corridor.
	 * @param x
	 * @param minY
	 * @param maxY
	 */
	private void carveVerticalCorridor(int x, int minY, int maxY) {
		for (int y = minY; y <= maxY; y++)
	    {
	      setCell(new Corridor(), x, y);
	    }
	}
	
	/**
	 * Carve out a horizontal corridor.
	 * @param y
	 * @param minX
	 * @param maxX
	 */
	private void carveHorizontalCorridor(int y, int minX, int maxX) {
		for (int x = minX; x <= maxX; x++)
	    {
	      setCell(new Corridor(), x, y);
	    }
	}
	
	/**
	 * Find all reachable walls.
	 * These are walls which are reachable by the player.
	 */
	private void findReachableWalls()
	{
	  // Find any walls which have anything other than walls or the dungeon edge on each side.
	  for (int x = 0; x < this.configuration.width; x++) {
	    for (int y = 0; y < this.configuration.height; y++) {
	      // Get the type of the current space.
	      CellType currentCellType = this.getCell(x, y).getType();
	      // Is the current space a wall?
	      if (currentCellType == CellType.WALL) {
	    	// Are we surrounded by any cells an entity can be is
	        if (isCellReachable(x + 1, y) || isCellReachable(x - 1, y) || isCellReachable(x, y + 1) || isCellReachable(x, y - 1)) {
	          // This wall is reachable by entities within the dungeon! Set the reachable wall.
	          this.setCell(new ReachableWall(), x, y);
	        }
	      }
	    }
	  }
	}
	
	/**
	 * Determines whether the cell at the specified position is reachable.
	 * @param x
	 * @param y
	 * @return is reachable
	 */
	private boolean isCellReachable(int x, int y) {
		// Get the cell at the position.
		ICell target = this.getCell(x, y);
		// return whether this is this a reachable cell?
		return target.getType() != CellType.WALL && 
			target.getType() != CellType.REACHABLE_WALL && 
			target.getType() != CellType.OOB;
	}
	
	
	/**
	 * Set the cell at the specified position.
	 * @param cell
	 * @param x
	 * @param y
	 */
	private void setCell(ICell cell, int x, int y) {
		setCell(cell, x, y, 1, 1);
	}
	
	/**
	 * Set the cell at the specified position.
	 * @param cell
	 * @param x
	 * @param y
	 */
	private void setCell(ICell cell, int x, int y, int width, int height) {
		for (int posX = x; posX < (x + width); posX++) {
			for (int posY = y; posY < (y + height); posY++) {
				this.cells.put(new Point(posX, posY), cell);
			}
		}
	}
	
	/**
	 * Get the cell at the specified position.
	 * @param x
	 * @param y
	 * @return cell
	 */
	private ICell getCell(int x, int y) {
		// Is this position outside the dungeon area?
		boolean isOutOfBounds = x < 0 || x >= configuration.width || y < 0 || y >= configuration.height;
		// If this position is out of bounds then return 'OOB'.
		if (isOutOfBounds) {
			return new OOB();
		}
		Point key = new Point(x, y);
		// Is there a cell at the specified position?
		if (cells.containsKey(key)) {
			return cells.get(key);
		} else {
			return new Wall();
		}
	}
}
