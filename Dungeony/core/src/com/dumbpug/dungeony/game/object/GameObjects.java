package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.game.level.InteractiveLevel;
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

    /**
     * Add a game object to the list of in-game objects.
     * @param gameObject The game object to add.
     */
    public void add(GameObject gameObject) {
        // There is nothing to do if the game object is already in out list of existing game objects.
        if (this.gameObjects.contains(gameObject)) {
            return;
        }

        // Add the game object to our list of game objects.
        this.gameObjects.add(gameObject);

        // Add the game object to the level grid.
        this.levelCollisionGrid.add(gameObject);

        // Add the game object to the renderables list.
        this.renderables.add(gameObject);
    }

    /**
     * Remove a game object from the list of in-game objects.
     * @param gameObject The game object to remove.
     */
    public void remove(GameObject gameObject) {
        // There is nothing to do if the game object is not already in out list of existing game objects.
        if (!this.gameObjects.contains(gameObject)) {
            return;
        }

        // Remove the game object from our list of game objects.
        this.gameObjects.remove(gameObject);

        // Remove the game object from the level grid.
        this.levelCollisionGrid.remove(gameObject);

        // Remove the game object from the renderables list.
        this.renderables.remove(gameObject);
    }

    /**
     * Update each of the game objects sequentially.
     * @param level The interactive level.
     */
    public void update(InteractiveLevel level) {
        // Update each of the game objects sequentially.
        for (GameObject object : this.gameObjects) {
            object.update(level);
        }
    }
}
