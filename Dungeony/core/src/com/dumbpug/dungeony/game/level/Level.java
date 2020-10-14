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
import com.dumbpug.dungeony.game.character.player.PlayerIdentifier;
import com.dumbpug.dungeony.game.character.player.Players;
import com.dumbpug.dungeony.game.lighting.Light;
import com.dumbpug.dungeony.game.lighting.LightType;
import com.dumbpug.dungeony.game.lighting.Lights;
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
    private LevelCamera levelCamera;
    /**
     * The level lights.
     */
    private Lights lights;

    /**
     * Creates a new instance of the Level class.
     * @param camera The level camera.
     * @param playerDetails The player details.
     * @param tiles The level tiles.
     * @param objects The level objects.
     * @param enemies The level enemy NPCs.
     * @param friendlies The level friendly NPCs.
     */
    public Level(LevelCamera camera, ArrayList<PlayerDetails> playerDetails, ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies, ArrayList<Friendly> friendlies) {
        // Create the environment config.
        EnvironmentConfiguration config = new EnvironmentConfiguration();
        config.gridCellSize             = Constants.LEVEL_GRID_CELL_SIZE;

        this.levelCamera = camera;
        this.lights      = new Lights();
        this.environment = new Environment(config, camera);
        this.tiles       = new Tiles(tiles, this.environment);
        this.objects     = new GameObjects(objects, this.environment);
        this.enemies     = new Enemies(enemies, this.environment);
        this.friendlies  = new Friendlies(friendlies, this.environment);
        this.players     = new Players(playerDetails, this.environment, this.tiles.getSpawns());

        // Add a light to follow the first player.
        // TODO Remove after testing!!!!!
        this.lights.add(new Light(LightType.SPOT, this.players.getPlayer(PlayerIdentifier.PLAYER_1)));
    }

    /**
     * Update the level.
     */
    public void update() {
        // Update the level camera, passing the delta time to use for frame-independent operations.
        this.levelCamera.update(Gdx.graphics.getDeltaTime());

        // Update the game environment, passing the delta time to use for frame-independent operations.
        this.environment.update(Gdx.graphics.getDeltaTime());
    }

    /**
     * Render the level.
     * @param batch The sprite batch to use in rendering the level.
     * @param camera The application camera.
     */
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Update the level camera zoom so that we aren't looking at the entire level and super tiny sprites.
        this.levelCamera.setZoom(Constants.LEVEL_DEFAULT_ZOOM);

        // Get the level camera to point at just the first player for now.
        // This will eventually point to a place between all players in the level.
        this.levelCamera.setTarget(this.players.getPlayer(PlayerIdentifier.PLAYER_1));

        // Update the sprite batch we are going to use in rendering the level to use the same view as our level camera.
        batch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite underlay = Resources.getSprite(LevelSprite.UNDERLAY);
        underlay.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        underlay.setCenterX(camera.position.x);
        underlay.setCenterY(camera.position.y);
        underlay.draw(batch);

        // Render an empty ground sprite for every tile.
        // TODO: Maybe add these as entities so that we can have them excluded when not in level camera?
        for (Tile tile : this.tiles.getAll()) {
            // TODO Maybe remove?
            if (!levelCamera.contains(tile)) {
                continue;
            }

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

        // Render the level lights over the environment entities.
        this.lights.render(batch, this.levelCamera);

        // Reset the application camera to its original level of zoom.
        this.levelCamera.setZoom(1f);

        // No that we have zoomed back out from the level we will need to update the sprite batch view.
        batch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());
    }
}
