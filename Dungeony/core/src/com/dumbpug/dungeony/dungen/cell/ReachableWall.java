package com.dumbpug.dungeony.dungen.cell;

/**
 * A reachable wall cell.
 */
public class ReachableWall implements ICell {

	@Override
	public CellType getType() { return CellType.REACHABLE_WALL; }
}