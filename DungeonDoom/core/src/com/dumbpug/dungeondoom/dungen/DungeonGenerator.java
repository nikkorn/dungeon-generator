package com.dumbpug.dungeondoom.dungen;

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
	private HashMap<String, ICell> cells;
	
	/**
	 * Generate a dungeon using the specified configuration.
	 * @param configuration
	 * @return generated dungeon
	 */
	public Dungeon generate(Configuration configuration) {
		
		// Set the RNG to use in generating this dungeon.
		this.random = new Random(configuration.seed);
		
		// Create a new cell map.
		this.cells = new HashMap<String, ICell>();
		
		
		return null;
	}
	
	/**
	 * Generate a dungeon using a purely default configuration.
	 * @return generated dungeon
	 */
	public Dungeon generate() {
		return generate(new Configuration());
	}
}
