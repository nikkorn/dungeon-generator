package com.dumbpug.dungeony.game.level;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {

    public static Level createLevel(String name) {
        return new Level();
    }

    public static Level createLevel(LevelCategory category, int level) {
        return new Level();
    }

    public static Level createTestLevel() {
        return new Level();
    }
}
