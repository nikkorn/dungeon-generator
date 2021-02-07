package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.audio.AudioProvider;
import com.dumbpug.dungeony.audio.MusicTrack;
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
import com.dumbpug.dungeony.game.weapon.rifle.Uzi;
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
    private LevelEnvironmentCamera levelCamera;
    /**
     * The music to play on loop while the level is active.
     */
    private Music backgroundMusic;

    /**
     * Creates a new instance of the Level class.
     * @param camera The level camera.
     * @param playerDetails The player details.
     * @param tiles The level tiles.
     * @param objects The level objects.
     * @param enemies The level enemy NPCs.
     * @param friendlies The level friendly NPCs.
     * @param backgroundMusic The music track to play in the background while the level is active.
     */
    public Level(LevelEnvironmentCamera camera, ArrayList<PlayerDetails> playerDetails, ArrayList<Tile> tiles, ArrayList<GameObject> objects, ArrayList<Enemy> enemies, ArrayList<Friendly> friendlies, MusicTrack backgroundMusic) {
        // Create the environment config.
        EnvironmentConfiguration config = new EnvironmentConfiguration();
        config.gridCellSize             = Constants.LEVEL_GRID_CELL_SIZE;

        this.levelCamera = camera;
        this.environment = new LevelEnvironment(config, camera, new LevelEnvironmentAudioPlayer());

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

        // Get the music track that will be played in the background while the level is active.
        this.backgroundMusic = AudioProvider.getMusic(backgroundMusic);
    }

    /**
     * Gets the level camera.
     * @return The level camera.
     */
    public LevelEnvironmentCamera getCamera() {
        return this.levelCamera;
    }

    /**
     * Called when the level begins, before the first level update.
     */
    public void onBegin() {
        // Start playing the background music.
        this.backgroundMusic.play();
    }

    /**
     * Called when the level ends, after the last level update.
     */
    public void onEnd() {
        // Stop playing the background music.
        this.backgroundMusic.stop();
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
     */
    public void render(SpriteBatch batch) {
        // Get the level camera to point at just the first player for now.
        // This will eventually point to a place between all players in the level.
        this.levelCamera.setTarget(this.environment.getEntities().getGroup("player").get(0));

        // Grab the current batch projection matrix to reapply after the level render.
        Matrix4 batchProjectionMatrix = batch.getProjectionMatrix();

        // Update the sprite batch we are going to use in rendering the level to use the same view as our level camera.
        batch.setProjectionMatrix(this.levelCamera.getCombinedViewMatrix());

        // Start drawing the level to the sprite batch.
        batch.begin();

        // Render a level underlay sprite matching the colour at the top of walls across the entire window.
        Sprite underlay = Resources.getSprite(LevelSprite.UNDERLAY);
        underlay.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        underlay.setCenterX(this.levelCamera.getX());
        underlay.setCenterY(this.levelCamera.getY());
        underlay.draw(batch);

        // Render the game environment.
        this.environment.render(batch);

        // Stop drawing the level to the sprite batch.
        batch.end();

        // Reapply the original batch projection matrix.
        batch.setProjectionMatrix(batchProjectionMatrix);
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
