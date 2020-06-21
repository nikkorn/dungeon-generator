package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.enemy.Enemies;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.player.Player;
import com.dumbpug.dungeony.game.character.player.PlayerIdentifier;
import com.dumbpug.dungeony.game.character.player.Players;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjects;
import com.dumbpug.dungeony.game.rendering.Renderables;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
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
    private LevelCollisionGrid grid = new LevelCollisionGrid();
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
        this.players = new Players(playerDetails, this.grid, this.renderables, this.tiles.getSpawns());
    }

    /**
     * Update the level.
     */
    public void update() {
        // Update each of the players.
        this.players.update(this.grid);

        // Update each of the enemies.
        this.enemies.update(this.grid);

        // TODO Update projectiles.
        // TODO Update game objects.
    }

    /**
     * Render the level.
     * @param batch The sprite batch to use in rendering the level.
     * @param camera The application camera.
     */
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Update camera and sprite batch to zoom and focus on players.
        camera.zoom = 0.3f;

        // Get the camera to point at just the first player for now!
        Player player = this.players.getPlayer(PlayerIdentifier.PLAYER_1);
        camera.position.set(player.getX(), player.getY(), 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // TODO Render a level underlay sprite matching the colour at the top of walls across the entire window.

        // Render the ground sprite for every tile.
        for (Tile tile : this.tiles.getAll()) {
            // Get the ground sprite for this tile.
            Sprite sprite = Resources.getSprite(TileSprite.GROUND);

            // Set the width/height of the sprite to match the tile size.
            sprite.setSize(tile.getWidth(), tile.getHeight());

            // Set the x/y of the sprite to match the tile position.
            sprite.setPosition(tile.getX(), tile.getY());

            // Draw the sprite.
            sprite.draw(batch);
        }

        // Render every level renderable, this will be done in render order.
        // TODO Pass in a window for which we will render contained renderables.
        this.renderables.render(batch);

        // Reset the application camera to its original zoom/position.
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
}
