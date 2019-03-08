package com.dumbpug.dungeony.dungen;

import java.util.ArrayList;
import java.util.HashMap;
import com.dumbpug.dungeony.dungen.tile.Empty;
import com.dumbpug.dungeony.dungen.tile.Tile;
import com.dumbpug.dungeony.dungen.tile.Wall;

/**
 * Generator of tiles based on positoned dungeon cells.
 */
public class TileGenerator {
	
	public static ArrayList<Tile> generateFromCells(PositionedCellList cells) {
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
			
			// ...
		}
		
		// Return a list of the generated tiles.
		return new ArrayList<Tile>(tileMap.values());
	}
}
