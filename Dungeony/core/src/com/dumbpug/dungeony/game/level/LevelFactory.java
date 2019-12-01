package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.Tile;

import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {

    public static Level createInitialLevel() {
        return new Level(
                new ArrayList<Tile>(),
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }

    public static Level createLevel(String name) {
        return new Level(
                new ArrayList<Tile>(),
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }

    public static Level createLevel(LevelCategory category, int level) {
        return new Level(
                new ArrayList<Tile>(),
                new ArrayList<GameObject>(),
                new ArrayList<Enemy>()
        );
    }
}
