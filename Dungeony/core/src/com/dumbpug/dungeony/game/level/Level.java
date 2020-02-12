package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.Enemies;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.character.Player;
import com.dumbpug.dungeony.game.character.PlayerIdentifier;
import com.dumbpug.dungeony.game.character.Players;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjects;
import com.dumbpug.dungeony.game.rendering.LevelSprite;
import com.dumbpug.dungeony.game.rendering.Renderables;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.Tiles;
import com.dumbpug.dungeony.input.IPlayerInputProvider;
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
        // Update players, applying any player input.
        updatePlayers();

        // TODO Update enemies.
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
        camera.zoom = 0.5f;

        // Get the camera to point at just the first player for now!
        Player player = this.players.getPlayer(PlayerIdentifier.PLAYER_1);
        camera.position.set(player.getX(), player.getY(), 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite levelUnderlay = Resources.getSprite(LevelSprite.UNDERLAY);
        levelUnderlay.setPosition(-Constants.WINDOW_WIDTH, -Constants.WINDOW_WIDTH);
        levelUnderlay.setSize(Constants.WINDOW_WIDTH * 4, Constants.WINDOW_HEIGHT * 4);
        levelUnderlay.draw(batch);

        // Render the ground sprite for every tile.
        for (Tile tile : this.tiles.getAll()) {
            // Get the ground sprite for this tile.
            Sprite sprite = Resources.getSprite(TileSprite.GROUND);

            // Set the width/height of the sprite to match the tile size.
            sprite.setSize(tile.getWidth(), tile.getHeight());

            // Set the x/y of the sprite to match the tile position.
            sprite.setPosition(tile.getX(), tile.getY());

            // Draw the sprite.
            // TODO Actually do this.
            // sprite.draw(batch);
        }

        // Render every level renderable, this will be done in render order.
        this.renderables.render(batch);

        // Reset the application camera to its original zoom/position.
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Update players and apply any player input.
     */
    private void updatePlayers() {
        // Process each player sequentially.
        for (Player player : this.players.getAll()) {
            // Get the input provider for the current player.
            IPlayerInputProvider playerInputProvider = player.getDetails().getInputProvider();

            // TODO Check for player conditions (etc death, buffs) and update.
            // TODO Check for player actions.

            // Process player input which would influence the movement of each player.
            // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
            this.grid.move(player, playerInputProvider.getMovementAxisX(), playerInputProvider.getMovementAxisY());
        }
    }
}
