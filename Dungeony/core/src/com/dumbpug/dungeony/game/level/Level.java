package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.character.Player;
import com.dumbpug.dungeony.game.character.Players;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.tile.Tile;
import java.util.ArrayList;

/**
 * An in-game level.
 */
public class Level {
    /**
     * The level tiles.
     */
    private ArrayList<Tile> tiles;
    /**
     * The game objects in the level.
     */
    private ArrayList<GameObject> objects;
    /**
     * The enemies in the level.
     */
    private ArrayList<Enemy> enemies;
    /**
     * The players in the level.
     */
    private Players players = new Players();
    /**
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelGrid grid = new LevelGrid();

    /**
     * Creates a new instance of the Level class.
     * @param tiles
     * @param objects
     * @param enemies
     */
    public Level(ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies) {
        this.tiles   = tiles;
        this.objects = objects;
        this.enemies = enemies;

        // Add the tiles, objects and enemies to our level grid.
        this.grid.add(tiles);
        this.grid.add(objects);
        this.grid.add(enemies);
    }

    /**
     * Add the players to the level.
     * @param players
     */
    public void addPlayers(Players players) {
        this.players = players;

        // TODO Assign each player a valid position in the level.

        // Add each player to the level grid.
        for (Player player : players.getAll()) {
            this.grid.add(player);
        }
    }

    /**
     * Update the level.
     */
    public void update() {
        // TODO Update players, applying any player input.
        // TODO Update enemies.
        // TODO Update projectiles.
        // TODO Update game objects.
    }

    /**
     * Render the level.
     * @param batch
     */
    public void render(SpriteBatch batch) {
        // TODO Draw the ground.
        // TODO Draw ever non-empty tile and game entity in order of the y-position of their origins.
        // TODO We may need a 'renderables' list with all renderable entities in it so we can sort before every rendering.
    }
}
