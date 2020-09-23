package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.engine.Environment;
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
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates an instance of the GameObjects class.
     * @param environment The game environment.
     */
    public GameObjects(ArrayList<GameObject> gameObjects, Environment environment) {
        this.gameObjects = gameObjects;
        this.environment = environment;

        // Add all of the game objects to the game environment.
        this.environment.addEntities(gameObjects, "object");
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

        // Add the game object to the game environment.
        this.environment.addEntity(gameObject, "object");
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

        // Remove the game object from the game environment.
        this.environment.removeEntity(gameObject);
    }
}
