package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.game.level.LevelGrid;
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
    private LevelGrid levelGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the GameObjects class.
     * @param gameObjects The list of game objects.
     * @param levelGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public GameObjects(ArrayList<GameObject> gameObjects, LevelGrid levelGrid, Renderables renderables) {
        this.gameObjects = gameObjects;
        this.levelGrid   = levelGrid;
        this.renderables = renderables;

        // Add the initial list of game objects to the level grid.
        this.levelGrid.add(gameObjects);

        // Add the initial list of game objects to the renderables list.
        this.renderables.add(gameObjects);
    }

    // TODO Add 'add' that also updates renderables and level grid.
    // TODO Add 'remove' that also updates renderables and level grid.
}
