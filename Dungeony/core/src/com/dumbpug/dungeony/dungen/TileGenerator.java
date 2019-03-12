package com.dumbpug.dungeony.dungen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.dumbpug.dungeony.dungen.Direction;
import com.dumbpug.dungeony.dungen.room.Entrance;
import com.dumbpug.dungeony.dungen.room.PositionedEntity;
import com.dumbpug.dungeony.dungen.tile.Door;
import com.dumbpug.dungeony.dungen.tile.Empty;
import com.dumbpug.dungeony.dungen.tile.Tile;
import com.dumbpug.dungeony.dungen.tile.Wall;

/**
 * Generator of tiles based on positoned dungeon cells.
 */
public class TileGenerator {
	
	/**
	 * Generate a list of tiles based on cell and entity information.
	 * @param cells The dungeon cells.
	 * @param random The RNG to use in resolving entity participants.
	 * @return A list of tiles based on cell and entity information.
	 */
	public static ArrayList<Tile> generateFromCells(PositionedCellList cells, Random random) {
		// Create a map to hold all of the tiles.
		HashMap<Position, Tile> tileMap = new HashMap<Position, Tile>();
		
		// Convert each cell into a bunch of tiles.
		for (PositionedCell cell : cells) {
			// Get the cell position.
			Position position = cell.getPosition();
			
			// Get the x/y position of the bottom left tile in the cell.
			int tileXMin = position.getX() * (Constants.CELL_TILE_SIZE + 1);
			int tileYMin = position.getY() * (Constants.CELL_TILE_SIZE + 1);
			int tileXMax = tileXMin + Constants.CELL_TILE_SIZE;
			int tileYMax = tileYMin + Constants.CELL_TILE_SIZE;
			
			// Create a wall tile for each tile position around the cell.
			for (int tileX = tileXMin - 1; tileX <= tileXMax; tileX++) {
				for (int tileY = tileYMin - 1; tileY <= tileYMax; tileY++) {
					tileMap.put(new Position(tileX, tileY), new Wall(tileX, tileY));
				}
			}
			
			// Create a tile for each tile position in the cell.
			for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileX, tileY), new Empty(tileX, tileY, cell.getDepth()));
				}
			}
			
			// Is the current cell the entrance cell for a room?
			if (cell.getCell().getEntrance() != null) {
				// Get the cell entrance info.
				Entrance entrance = cell.getCell().getEntrance();
				
				// Find the position of the door based on the entrance cell door direction.
				Position doorPosition = getDoorPosition(entrance.getDirection(), tileXMin, tileYMin, tileXMax, tileYMax);
				
				// Create the door tile.
				Door door = new Door(doorPosition.getX(), doorPosition.getY(), cell.getDepth(), entrance.getDoor(), entrance.getDirection());
			
				// Add the door to the tile map.
				tileMap.put(new Position(doorPosition.getX(), doorPosition.getY()), door);
			}
			
			// Is the cell above this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.NORTH)) {
				// Create a tile for each tile position in the cell.
				for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
					tileMap.put(new Position(tileX, tileYMax), new Empty(tileX, tileYMax, cell.getDepth()));
				}
			}
			
			// Is the cell below this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.SOUTH)) {
				// Create a tile for each tile position in the cell.
				for (int tileX = tileXMin; tileX < tileXMax; tileX++) {
					tileMap.put(new Position(tileX, tileYMin - 1), new Empty(tileX, tileYMin - 1, cell.getDepth()));
				}
			}
			
			// Is the cell to the left of this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.WEST)) {
				// Create a tile for each tile position in the cell.
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileXMin - 1, tileY), new Empty(tileXMin - 1, tileY, cell.getDepth()));
				}
			}
			
			// Is the cell to the right of this one in the same room?
			if (areRoomCellsBridged(cells, cell, Direction.EAST)) {
				// Create a tile for each tile position in the cell.
				for (int tileY = tileYMin; tileY < tileYMax; tileY++) {
					tileMap.put(new Position(tileXMax, tileY), new Empty(tileXMax, tileY, cell.getDepth()));
				}
			}
						
			// Generate any entites for the cell and attach them to their corresponding tiles.
			for (PositionedEntity positionedEntity : cell.getCell().generateEntities(random)) {
				// There is nothing to do if there is no tile on which to place the entity.
				if (!tileMap.containsKey(positionedEntity.getPosition())) {
					continue;
				}
				
				// Get the tile.
				Tile tile = tileMap.get(positionedEntity.getPosition());
				
				// There is nothing to do if the tile at the entity position cannot hold an entity.
				if (!(tile instanceof Empty)) {
					continue;
				}
				
				// Add the entity to its corresponding cell.
				((Empty)tile).setEntity(positionedEntity.getEntity());
			}
		}
		
		// Return a list of the generated tiles.
		return new ArrayList<Tile>(tileMap.values());
	}
	
	/**
	 * Get the absolute position of a door for a cell.
	 * @param direction The door direction.
	 * @param tileXMin
	 * @param tileYMin
	 * @param tileXMax
	 * @param tileYMax
	 * @return
	 */
	private static Position getDoorPosition(Direction direction, int tileXMin, int tileYMin, int tileXMax, int tileYMax) {
		// Find the position of the door based on the entrance cell door direction.
		switch (direction) {
			case NORTH:
				return new Position(tileXMin + Constants.CELL_DOOR_OFFSET, tileYMax);
			case SOUTH:
				return new Position(tileXMin + Constants.CELL_DOOR_OFFSET, tileYMin - 1);
			case EAST:
				return new Position(tileXMax, tileYMin + Constants.CELL_DOOR_OFFSET);
			case WEST:
				return new Position(tileXMin - 1, tileYMin + Constants.CELL_DOOR_OFFSET);
			default:
				throw new RuntimeException("unexpected direction enum value: " + direction);
		}
	}
	
	/**
	 * Gets whether the cell at the initial position and the cell in the specified direction are in the same room.
	 * @param cell The list of all positioned cells.
	 * @param initialCell The initial cell.
	 * @param direction The direction of the cell to check.
	 * @returns Whether the cell at the initial position and the cell in the specified direction are in the same room.
	 */
	private static boolean areRoomCellsBridged(PositionedCellList cells, PositionedCell initialCell, Direction direction) {
		// Get the position of the adjacent cell.
		Position adjacentCellPosition = initialCell.getPosition().getAdjacent(direction);
		
		// Check whether there is even a cell at the adjacent position.
		if (!cells.isCellAt(adjacentCellPosition)) {
			return false;
		}
		
		// Are the cells within the same room instance.
		return cells.getCellAt(adjacentCellPosition).getRoomInstanceId() == initialCell.getRoomInstanceId();
	}
}
