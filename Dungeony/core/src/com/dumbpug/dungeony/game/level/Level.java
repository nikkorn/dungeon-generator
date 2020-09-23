package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.enemy.Enemies;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.friendly.Friendlies;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.player.Player;
import com.dumbpug.dungeony.game.character.player.PlayerIdentifier;
import com.dumbpug.dungeony.game.character.player.Players;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjects;
import com.dumbpug.dungeony.game.rendering.LevelSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.Tiles;
import java.util.ArrayList;
import com.dumbpug.dungeony.Constants;

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
     * The enemy NPCs in the level.
     */
    private Enemies enemies;
    /**
     * The friendly NPCs in the level.
     */
    private Friendlies friendlies;
    /**
     * The players in the level.
     */
    private Players players;
    /**
     * The spatial grid to use in finding game entity collisions.
     */
    private EnvironmentCollisionGrid grid = new EnvironmentCollisionGrid();
    /**
     * The list of level renderables.
     */
    private Renderables renderables = new Renderables();

    /**
     * Creates a new instance of the Level class.
     * @param playerDetails The player details.
     * @param tiles The level tiles.
     * @param objects The level objects.
     * @param enemies The level enemy NPCs.
     * @param friendlies The level friendly NPCs.
     */
    public Level(ArrayList<PlayerDetails> playerDetails, ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies, ArrayList<Friendly> friendlies) {
        this.tiles      = new Tiles(tiles, this.grid, this.renderables);
        this.objects    = new GameObjects(objects, this.grid, this.renderables);
        this.enemies    = new Enemies(enemies, this.grid, this.renderables);
        this.friendlies = new Friendlies(friendlies, this.grid, this.renderables);
        this.players    = new Players(playerDetails, this.grid, this.renderables, this.tiles.getSpawns());
    }

    /**
     * Gets the level collision grid.
     * @return The level collision grid.
     */
    public EnvironmentCollisionGrid getGrid() {
        return grid;
    }

    /**
     * Update the level.
     */
    public void update() {
        // Create a wrapper around this level instance that exposes functionality and information
        // that would be required as part of the update of any individual level entities.
        InteractiveEnvironment InteractiveEnvironment = new InteractiveEnvironment(this);

        // Update each of the players.
        this.players.update(InteractiveEnvironment);

        // Update each of the friendly NPCs.
        this.friendlies.update(InteractiveEnvironment);

        // Update each of the enemy NPCs.
        this.enemies.update(InteractiveEnvironment);

        // Update each of the in-game objects.
        this.objects.update(InteractiveEnvironment);

        // TODO Update projectiles.
    }

    /**
     * Render the level.
     * @param batch The sprite batch to use in rendering the level.
     * @param camera The application camera.
     */
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Update camera and sprite batch to zoom and focus on players.
        camera.zoom = Constants.LEVEL_DEFAULT_ZOOM;

        // Get the camera to point at just the first player for now!
        Player player = this.players.getPlayer(PlayerIdentifier.PLAYER_1);
        camera.position.set(player.getX(), player.getY(), 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite underlay = Resources.getSprite(LevelSprite.UNDERLAY);
        underlay.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        underlay.setCenterX(camera.position.x);
        underlay.setCenterY(camera.position.y);
        underlay.draw(batch);

        // Render an empty ground sprite for every tile.
        for (Tile tile : this.tiles.getAll()) {
            // Get the ground sprite for this tile.
            Sprite sprite = Resources.getSprite(TileSprite.GROUND_0);

            // Set the width/height of the sprite to match the tile size.
            sprite.setSize(tile.getLengthX(), tile.getLengthY());

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
