package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.EnvironmentConfiguration;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.player.Player;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.rendering.LevelSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.rendering.TileSprite;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileSpawn;
import com.dumbpug.dungeony.game.tile.TileType;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.handgun.Pistol;
import java.util.ArrayList;

/**
 * An in-game level.
 */
public class Level {
    /**
     * The game environment.
     */
    private LevelEnvironment environment;
    /**
     * The level camera.
     */
    private LevelCamera levelCamera;

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
        this.environment = new LevelEnvironment(config, camera);

        // Find all of the player spawn locations defined by the specified tiles.
        ArrayList<TileSpawn> playerSpawns = getPlayerSpawns(tiles);

        // Check that there are enough spawn positions for our players.
        if (playerDetails.size() > playerSpawns.size()) {
            throw new RuntimeException("not enough spawns for players");
        }

        // Create and add all of the player entities to the game environment.
        addPlayerEntities(playerDetails, playerSpawns);

        // Add all of the tile entities to the game environment.
        this.environment.getEntities().add(tiles, "tile");

        // Add all of the game objects to the game environment.
        this.environment.getEntities().add(objects, "object");

        // Add all of the enemies to the game environment.
        this.environment.getEntities().add(enemies, "enemy");

        // Add all of the friendlies to the game environment.
        this.environment.getEntities().add(friendlies, "friendly");
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
        this.levelCamera.setTarget(this.environment.getEntities().getGroup("player").get(0));

        // Update the sprite batch we are going to use in rendering the level to use the same view as our level camera.
        batch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite underlay = Resources.getSprite(LevelSprite.UNDERLAY);
        underlay.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        underlay.setCenterX(camera.position.x);
        underlay.setCenterY(camera.position.y);
        underlay.draw(batch);

        // Render an empty ground sprite for every wall tile.
        // TODO: Maybe add these as entities so that we can have them excluded when not in level camera?
        for (Tile tile : this.environment.getEntities().<Tile>getGroup("tile")) {
            if (tile.getTileType() != TileType.WALL || !levelCamera.contains(tile)) {
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

        // Reset the application camera to its original level of zoom.
        this.levelCamera.setZoom(1f);

        // No that we have zoomed back out from the level we will need to update the sprite batch view.
        batch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());
    }

    /**
     * Add new player entities to the level environment.
     * @param playerDetails The list of player details.
     * @param spawns The available player spawns.
     */
    private void addPlayerEntities(ArrayList<PlayerDetails> playerDetails, ArrayList<TileSpawn> spawns) {
        // Create an in-level Player instance for each player, giving them each an initial spawn position.
        for (PlayerDetails playerDetail : playerDetails) {
            // TODO Eventually handle the fact that a tile spawn can represent a spawn path to follow before the level begins.

            // Create a new player instance based on the player details and assign them an initial spawn.
            Player player = new Player(playerDetail, spawns.get(playerDetails.indexOf(playerDetail)).getLocation());

            // TODO: Remove this weapon test.
            player.setWeapon(new Pistol(WeaponQuality.AVERAGE));

            // Add the player to the game environment.
            this.environment.getEntities().add(player, "player");
        }
    }

    /**
     * Finds and returns a list of any spawn locations defined by the specified tiles.
     * @param tiles The level tiles.
     * @return A list of any spawn locations defined by the specified tiles.
     */
    private static ArrayList<TileSpawn> getPlayerSpawns(ArrayList<Tile> tiles) {
        ArrayList<TileSpawn> spawns = new ArrayList<TileSpawn>();

        for (Tile tile : tiles) {
            if (tile.getTileSpawns() != null) {
                spawns.addAll(tile.getTileSpawns());
            }
        }

        return spawns;
    }
}
