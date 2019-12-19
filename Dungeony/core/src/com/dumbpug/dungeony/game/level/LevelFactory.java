package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileType;
import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {

    public static Level createInitialLevel(ArrayList<PlayerDetails> playerDetails) {
        // TODO Create some test tiles!
        ArrayList<Tile> testTiles = new ArrayList<Tile>() {{
           add(new Tile(TileType.WALL,0,0));
           add(new Tile(TileType.WALL,1,1));
           add(new Tile(TileType.WALL,2,2));
           add(new Tile(TileType.WALL,3,3));
           add(new Tile(TileType.WALL,4,4));
           add(new Tile(TileType.WALL,5,5));
           add(new Tile(TileType.WALL,6,6));
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
