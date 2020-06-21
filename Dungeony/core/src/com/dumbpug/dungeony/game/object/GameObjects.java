package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.game.level.LevelCollisionGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
import java.util.ArrayList;

/**
 * The list of in-level game objects.
 */
public class GameObjects {
    /**
     * The underlying list of game objects.
     */
    private ArrayList<GameObject> gameObjects;
    /**
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelCollisionGrid levelCollisionGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the GameObjects class.
     * @param gameObjects The list of game objects.
     * @param levelCollisionGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public GameObjects(ArrayList<GameObject> gameObjects, LevelCollisionGrid levelCollisionGrid, Renderables renderables) {
        this.gameObjects = gameObjects;
        this.levelCollisionGrid = levelCollisionGrid;
        this.renderables = renderables;

        // Add the initial list of game objects to the level grid.
        this.levelCollisionGrid.add(gameObjects);

        // Add the initial list of game objects to the renderables list.
        this.renderables.add(gameObjects);
    }

    // TODO Add 'add' that also updates renderables and level grid.
    // TODO Add 'remove' that also updates renderables and level grid.
}
