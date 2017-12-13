package com.dumbpug.dungeondoom.dungen.cell;

/**
 * A room cell.
 */
public class Room implements ICell {

	@Override
	public CellType getType() { return CellType.ROOM; }
}
