package com.dumbpug.dungeony.dungen;

import java.util.ArrayList;

/**
 * A list of positioned cells.
 */
public class PositionedCellList extends ArrayList<PositionedCell> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Get whether there is a cell at the specified position.
	 * @param x The x position. 
	 * @param y The y position.
	 * @return Whether there is a cell at the specified position.
	 */
	public boolean isCellAt(int x, int y) {
		for (PositionedCell cell : this) {
			if (cell.getPosition().getX() == x && cell.getPosition().getY() == y) {
				// There was a cell at this position.
				return true;
			}
		}
		// There was no cell at the position.
		return false;
	}
	
	/**
	 * Get whether there is a cell at the specified position.
	 * @param position The position. 
	 * @return Whether there is a cell at the specified position.
	 */
	public boolean isCellAt(Position position) {
		return isCellAt(position.getX(), position.getY());
	}
}
