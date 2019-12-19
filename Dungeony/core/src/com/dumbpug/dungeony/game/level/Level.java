package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemies;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.character.Players;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjects;
import com.dumbpug.dungeony.game.rendering.Renderables;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.Tiles;
import java.util.ArrayList;

/**
 * An in-game level.
 */
public class Level {
    /**
     * The level tiles.
     */
    private Tiles tiles;
    /**
     * The game objects in the level.
     */
    private GameObjects objects;
    /**
     * The enemies in the level.
     */
    private Enemies enemies;
    /**
     * The players in the level.
     */
    private Players players;
    /**
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelGrid grid = new LevelGrid();
    /**
     * The list of level renderables.
     */
    private Renderables renderables = new Renderables();

    /**
     * Creates a new instance of the Level class.
     * @param playerDetails The player details.
     * @param tiles The level tiles.
     * @param objects The level objects.
     * @param enemies The level enemies.
     */
    public Level(ArrayList<PlayerDetails> playerDetails, ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies) {
        this.tiles   = new Tiles(tiles, this.grid, this.renderables);
        this.objects = new GameObjects(objects, this.grid, this.renderables);
        this.enemies = new Enemies(enemies, this.grid, this.renderables);
        this.players = new Players(playerDetails, this.grid, this.renderables, this.tiles.getSpawnPositions());
    }

    /**
     * Gets the level renderables.
     * @return The level renderables.
     */
    public Renderables getRenderables() {
        return this.renderables;
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
     * @param batch The sprite batch to use in rendering the level.
     */
    public void render(SpriteBatch batch) {
        // TODO Might have to have a camera here to follow the player/players and update/reapply the batch projection matrix.
        // TODO Alternatively, We could pass the camera thorough from 'Game' and update/reset it here.

        // Render every level renderable, this will be done in render order.
        this.renderables.render(batch);
    }
}
