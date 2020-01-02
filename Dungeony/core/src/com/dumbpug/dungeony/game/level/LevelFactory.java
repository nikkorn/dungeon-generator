package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileFactory;
import com.dumbpug.dungeony.game.tile.TileType;
import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {
    /**
     * Create the initial Level instance.
     * @param playerDetails The player details.
     * @return The initial Level instance.
     */
    public static Level createInitialLevel(ArrayList<PlayerDetails> playerDetails) {
        // TODO Create some test tiles!
        ArrayList<Tile> testTiles = new ArrayList<Tile>() {{
            add(TileFactory.createTile(TileType.WALL, 0, 0));
            add(TileFactory.createTile(TileType.WALL, 1, 0));
            add(TileFactory.createTile(TileType.WALL, 2, 0));
            add(TileFactory.createTile(TileType.WALL, 3, 0));
            add(TileFactory.createTile(TileType.WALL, 0, 1));
            add(TileFactory.createTile(TileType.EMPTY, 1, 1));
            add(TileFactory.createTile(TileType.EMPTY, 2, 1));
            add(TileFactory.createTile(TileType.WALL, 3, 1));
            add(TileFactory.createTile(TileType.WALL, 0, 2));
            add(TileFactory.createTile(TileType.EMPTY, 1, 2));
            add(TileFactory.createTile(TileType.EMPTY, 2, 2));
            add(TileFactory.createTile(TileType.WALL, 3, 2));
            add(TileFactory.createTile(TileType.WALL, 0, 3));
            add(TileFactory.createTile(TileType.WALL, 1, 3));
            add(TileFactory.createTile(TileType.WALL, 2, 3));
            add(TileFactory.createTile(TileType.WALL, 3, 3));
        }};

        return new Level(
                playerDetails,
                testTiles,
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }

    public static Level createLevel(ArrayList<PlayerDetails> playerDetails, String name) {
        return new Level(
                playerDetails,
                new ArrayList<Tile>(),
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }

    public static Level createLevel(ArrayList<PlayerDetails> playerDetails, LevelCategory category, int level) {
        return new Level(
                playerDetails,
                new ArrayList<Tile>(),
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }
}
