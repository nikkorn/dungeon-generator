package com.dumbpug.dungeony.dungen.tile;

/**
 * Represents an empty tile in a room.
 */
public class Empty extends Tile {
	/**
	 * The entity that is on the empty tile.
	 */
	private Entity entity = null;

	/**
	 * Create a new instance of the Empty class.
	 * @param x The tile x position.
	 * @param y The tile y position.
	 * @param depth The depth of the tile as the number of rooms that were passed through to reach it.
	 */
	public Empty(int x, int y, int depth) {
		super(x, y, depth);
	}
	
	/**
	 * Get the entity that is on the empty tile. 
	 * @return The entity that is on the empty tile.
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Set the entity that is on the empty tile.
	 * @param entity The entity that is on the empty tile.
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public TileType getType() {
		return TileType.EMPTY;
	}
}
