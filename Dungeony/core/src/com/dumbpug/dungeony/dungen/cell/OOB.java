package com.dumbpug.dungeony.dungen.cell;

/**
 * An out-of-bounds cell.
 */
public class OOB implements ICell {
	
	@Override
	public CellType getType() { return CellType.OOB; }
}
