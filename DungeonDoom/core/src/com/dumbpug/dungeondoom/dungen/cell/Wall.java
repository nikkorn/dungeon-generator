package com.dumbpug.dungeondoom.dungen.cell;

/**
 * A wall cell.
 */
public class Wall implements ICell {

	@Override
	public CellType getType() { return CellType.WALL; }
}
