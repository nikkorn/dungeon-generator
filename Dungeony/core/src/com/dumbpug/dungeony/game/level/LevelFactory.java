package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.Tile;
import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {

    public static Level createInitialLevel(ArrayList<PlayerDetails> playerDetails) {
        return new Level(
                playerDetails,
                new ArrayList<Tile>(),
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
