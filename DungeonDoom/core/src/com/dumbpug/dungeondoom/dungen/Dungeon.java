package com.dumbpug.dungeondoom.dungen;

import java.awt.Point;
import java.util.HashMap;
import com.dumbpug.dungeondoom.dungen.cell.ICell;

/**
 * A generated dungeon.
 */
public class Dungeon {
	
	/** The cell to position mappings. */
	private HashMap<Point, ICell> cells;
	
	/** The configuration used in generating this dungeon. */
	private Configuration configuration;
	
	/**
	 * Create a new instance of the Dungeon class.
	 * @param configuration The configuration used in generating this dungeon. 
	 * @param cells The dungeon cells
	 */
	public Dungeon(Configuration configuration, HashMap<Point, ICell> cells) {
		this.configuration = configuration;
		this.cells         = cells;
	}

	/**
	 * Get the dungeon cells map.
	 * @return cells map
	 */
	public HashMap<Point, ICell> getCells() { return cells; }
	
	/**
	 * Get the dungeon cell at the specified position.
	 * @param x
	 * @param y
	 * @return cell
	 */
	public ICell getCellAt(int x, int y) { return cells.get(new Point(x, y)); }

	/**
	 * Get the configuration used in generating this dungeon.
	 * @return configuration
	 */
	public Configuration getConfiguration() { return configuration; }
}
