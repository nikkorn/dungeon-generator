package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.game.tile.ITileFinder;
import com.dumbpug.dungeony.game.tile.ITilePositionedEntity;
import com.dumbpug.dungeony.game.tile.TileOffset;
import com.dumbpug.dungeony.game.tile.TileType;
import com.dumbpug.levelgeneration.LevelDefinition;

/**
 * Finder of tile types from level definitions.
 */
public class LevelTileFinder implements ITileFinder {
    /**
     * The level definition to source tile info from.
     */
    private LevelDefinition levelDefinition;

    /**
     * Creates a new instance of the LevelTileFinder class.
     * @param levelDefinition The level definition to source tile info from.
     */
    public LevelTileFinder(LevelDefinition levelDefinition) {
        this.levelDefinition = levelDefinition;
    }

    /**
     * Gets the type of the tile at the specified position.
     * @param x The tile based x position.
     * @param y The tile based y position.
     * @return The type of the tile at the specified position.
     */
    @Override
    public TileType find(int x, int y) {
        return TileType.valueOf(levelDefinition.getTileType(x, y));
    }

    /**
     * Gets the type of the tile at the specified offset from the origin tile.
     * @param origin The origin tile.
     * @param offset The offset from the origin tile.
     * @return The type of the tile at the specified offset from the origin tile.
     */
    @Override
    public TileType find(ITilePositionedEntity origin, TileOffset offset) {
        // Find the origin position.
        int x = origin.getTileX();
        int y = origin.getTileY();

        // Apply the appropriate offset to our x/y values.
        switch (offset) {
            case ABOVE:
                y += 1;
                break;
            case ABOVE_LEFT:
                y += 1;
                x -= 1;
                break;
            case ABOVE_RIGHT:
                y += 1;
                x += 1;
                break;
            case BELOW:
                y -= 1;
                break;
            case BELOW_LEFT:
                y -= 1;
                x -= 1;
                break;
            case BELOW_RIGHT:
                y -= 1;
                x += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            default:
                throw new RuntimeException("unknown tile offset type: " + offset);
        }

        // Return the type of the tile at the offset position.
        return this.find(x, y);
    }
}
