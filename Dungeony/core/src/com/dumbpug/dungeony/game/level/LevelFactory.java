package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.ITileFinder;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileFactory;
import com.dumbpug.dungeony.game.tile.TileType;
import com.dumbpug.levelgeneration.ILevelGenerator;
import com.dumbpug.levelgeneration.LevelDefinition;
import com.dumbpug.levelgeneration.TileDefinition;
import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {
    /**
     * Create the initial Level instance.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @return The initial Level instance.
     */
    public static Level createInitialLevel(ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails) {
        return createLevel(levelGenerator, playerDetails, "SPAWN.json");
    }

    /**
     * Create a Level instance.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @param category The level category.
     * @param difficulty The level difficulty.
     * @return A Level instance.
     */
    public static Level createLevel(ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails, LevelCategory category, Difficulty difficulty) {
        return createLevel(levelGenerator, playerDetails, category + "_" + difficulty + ".json");
    }

    /**
     * Create a Level instance.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @param file The file path
     * @return A Level instance.
     */
    private static Level createLevel(ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails, String file) {
        // Generate the level!
        final LevelDefinition levelDefinition = levelGenerator.generate(file);

        // Create the tile finder that is used by the tile factory in order to find the inspect the type of other tiles.
        ITileFinder tileFinder = new ITileFinder() {
            @Override
            public TileType find(int x, int y) {
                return TileType.valueOf(levelDefinition.getTileType(x, y));
            }
        };

        // Create the actual tiles based on the generated level tile definitions.
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (TileDefinition tileDefinition : levelDefinition.getTileDefinitions()) {
            tiles.add(
                    TileFactory.createTile(TileType.valueOf(tileDefinition.getType()), tileDefinition.getX(), tileDefinition.getY(), tileFinder)
            );
        }

        // Create and return the level instance.
        return new Level(
                playerDetails,
                tiles,
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }
}
