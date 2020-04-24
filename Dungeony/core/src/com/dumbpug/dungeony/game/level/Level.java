package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dumbpug.dungeony.Constants;
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
    private LevelGrid grid = new LevelGrid();
    /**
     * The list of level renderables.
     */
    private Renderables renderables = new Renderables();

    FrameBuffer lightBuffer;
    TextureRegion lightBufferRegion;
    Texture lightSprite;
    SpriteBatch lightBatch;

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

        // TODO Do we need to make the lighting frame buffer cover the entire playable level and just draw onto it lights that are in the camera view?
        lightBatch  = new SpriteBatch();
        lightSprite = new Texture("bloom.png");
        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, false);
        lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        lightBufferRegion.flip(false, false);
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







        // TODO We should end the main application batch now to make way for the lighting one?
        batch.end();

        // TODO I think this needs to be uncommented when the lights are rendered in the correct places and i work out the framebuffer dealio.
        // lightBatch.setProjectionMatrix(camera.combined);

        // RENDER LOOP
        lightBuffer.begin();

        // set the ambient color values, this is the "global" light of your scene
        // imagine it being the sun.  Usually the alpha value is just 1, and you change the darkness/brightness with the Red, Green and Blue values for best effect
        Gdx.gl.glClearColor(0.7f,0.7f,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // RENDER LIGHTS
        lightBatch.enableBlending();
        lightBatch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);

        // SPRITEBATCH
        lightBatch.begin();
        lightBatch.setColor(1f,1f,1f,1);
        lightBatch.draw(lightSprite, 0,0, Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2);
        lightBatch.end();
        // SPRITEBATCH END

        lightBuffer.end();

        // Render the lightBuffer to the default "frame buffer"
        // Set Blend Function of Spritebatch
        lightBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        lightBatch.begin();
        lightBatch.draw(lightBufferRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lightBatch.end();

        // TODO We should resume the main application batch now after the lighting one?
        batch.begin();







        // Reset the application camera to its original zoom/position.
        camera.zoom = 1f;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
}
