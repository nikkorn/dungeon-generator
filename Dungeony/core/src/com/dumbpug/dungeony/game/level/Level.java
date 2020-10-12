package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.Environment;
import com.dumbpug.dungeony.engine.EnvironmentConfiguration;
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
     * The game environment.
     */
    private Environment environment;
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
     * The level camera.
     */
    LevelCamera levelCamera;

    /**
     * Creates a new instance of the Level class.
     * @param playerDetails The player details.
     * @param tiles The level tiles.
     * @param objects The level objects.
     * @param enemies The level enemy NPCs.
     * @param friendlies The level friendly NPCs.
     */
    public Level(ArrayList<PlayerDetails> playerDetails, ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies, ArrayList<Friendly> friendlies) {
        this.environment = createEnvironment();
        this.tiles       = new Tiles(tiles, this.environment);
        this.objects     = new GameObjects(objects, this.environment);
        this.enemies     = new Enemies(enemies, this.environment);
        this.friendlies  = new Friendlies(friendlies, this.environment);
        this.players     = new Players(playerDetails, this.environment, this.tiles.getSpawns());
        this.levelCamera = new LevelCamera(0,0, Constants.LEVEL_TILE_SIZE * 3, Constants.LEVEL_TILE_SIZE * 3);
    }

    /**
     * Update the level.
     */
    public void update() {
        // Update the game environment, passing the delta time to use for frame-independent operations.
        this.environment.update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Render the level.
     * @param batch The sprite batch to use in rendering the level.
     * @param camera The application camera.
     */
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Update camera and sprite batch to zoom and focus on players.
        camera.zoom = Constants.LEVEL_DEFAULT_ZOOM;

        // Get the LIBGDX and level camera to point at just the first player for now!
        Player player = this.players.getPlayer(PlayerIdentifier.PLAYER_1);
        camera.position.set(player.getX(), player.getY(), 0);
        this.levelCamera.set(player.getX(), player.getY(), camera.viewportWidth * Constants.LEVEL_DEFAULT_ZOOM, camera.viewportHeight * Constants.LEVEL_DEFAULT_ZOOM);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite underlay = Resources.getSprite(LevelSprite.UNDERLAY);
        underlay.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        underlay.setCenterX(camera.position.x);
        underlay.setCenterY(camera.position.y);
        underlay.draw(batch);

        // Render an empty ground sprite for every tile.
        // TODO: Maybe add these as entities so that we can have them excluded when not in level camera?
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

        // Render the game environment, passing in the level camera to constrain which renderables are actually rendered.
        this.environment.render(batch, this.levelCamera);

        // Reset the application camera to its original zoom/position.
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Creates the game environment.
     * @return The game environment.
     */
    private static Environment createEnvironment() {
        // Create the environment config.
        EnvironmentConfiguration config = new EnvironmentConfiguration();

        // Set the environment grid cell size.
        config.gridCellSize = Constants.LEVEL_GRID_CELL_SIZE;

        return new Environment(config);
    }
}
