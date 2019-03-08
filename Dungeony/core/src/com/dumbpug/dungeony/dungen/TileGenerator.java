package com.dumbpug.dungeony.dungen;

import java.util.ArrayList;
import com.dumbpug.dungeony.dungen.tile.Tile;
import com.dumbpug.dungeony.dungen.tile.Wall;

/**
 * Generator of tiles based on positoned dungeon cells.
 */
public class TileGenerator {
	
	public static ArrayList<Tile> generateFromCells(PositionedCellList cells) {
		// Create a list to hold all of the tiles.
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
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
					tiles.add(new Wall(tileX, tileY));
				}
			}
			
			// ...
		}
		
		// Return the generated tiles.
		return tiles;
	}
}
