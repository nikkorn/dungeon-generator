package dungen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class DungeonGenerator {
	/** The configuration to use in generating the dungeon. */
	private Configuration configuration;
	
	/** The RNG to use in generating the dungeon. */
	private Random rng;
	
	/** The cell to position mappings. */
	private Cells cells;
    
    /**
     * Creates a new instance of the DungeonGenerator class.
     * @param configuration The configuration.
     */
    private DungeonGenerator(Configuration configuration) {
    	this.configuration = configuration;
    	
    	// Set the RNG to use in generating this dungeon.
		this.rng = new Random(configuration.seed);
		
		// Create a cell map.
		this.cells = new Cells(configuration.width, configuration.height);
    }
    
    /**
     * Generate a dungeon.
     * @return A generated dungeon.
     */
    private Result generate() {
    	// Generate our dungeon rooms.
		ArrayList<Room> rooms = this.generateRooms();
		
		// Generate some corridors between our rooms.
		this.generateCorridors(rooms);
		
		// Set the room cells.
		for (Room room : rooms) {
			this.cells.set("ROOM", null, room.getX(), room.getY(), room.getWidth(), room.getHeight());
		}
		
		// TODO Go over every space in the dungeon area and compare a series of patterns to the position.
		
		// Determine which walls are actually reachable (not surrounded by other walls)
		// this.findReachableWalls();
		
		// Return our generated dungeon.
		return new Result(configuration, cells);
    }

	/**
     * Generates the dungeon rooms.
     * @return
     */
    private ArrayList<Room> generateRooms() {
    	ArrayList<Room> rooms = new ArrayList<Room>();
    	
    	// The number of times we have tried to generate a room.
    	int attempt = 0;
    	
    	// Generate as many rooms as we need.
		while (rooms.size() < configuration.rooms && attempt++ < configuration.maxRoomGenerationAttempts) {
			// Create a randomly sized/positioned room.
			Room room = Room.generate(rng, configuration);
			
			// Skip this room if it is not within the bounds of the dungeon.
			if (!room.isInBounds(configuration.width, configuration.height)) {
				continue;
			}
			
			// Check for an overlap with any existing rooms.
			boolean hasOverlap = false;
			for (Room existingRoom : rooms) {
				if (room.isOverlapping(existingRoom, configuration.roomBuffer)) {
					hasOverlap = true;
					break;
				}
			}
			
			// We can add the current room to our list of all rooms if it does not overlap others.
			if (!hasOverlap) {
				rooms.add(room);
			}
		}
		
		// Return the created rooms.
		return rooms;
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
				if (this.rng.nextBoolean()) {
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
		for (int posX = x; posX < x + this.configuration.corridorWidth; posX++) {
			for (int y = minY; y <= maxY; y++) {
		      this.cells.set("CORRIDOR", null, posX, y);
		    }
	    }
	}
	
	/**
	 * Carve out a horizontal corridor.
	 * @param y
	 * @param minX
	 * @param maxX
	 */
	private void carveHorizontalCorridor(int y, int minX, int maxX) {
		for (int posY = y; posY < y + this.configuration.corridorWidth; posY++) {
			for (int x = minX; x <= maxX; x++) {
				this.cells.set("CORRIDOR", null, x, posY);
		    }
	    }
	}
    
    public static Result Generate(Configuration configuration) {
    	return new DungeonGenerator(configuration).generate();
    }
}
