package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.dungeony.game.character.PlayerState;
import java.util.HashMap;

/**
 * Game resources provider.
 */
public class Resources {
    /**
     * Level sprite map.
     */
    private static HashMap<LevelSprite, Sprite> levelSpriteMap;
    /**
     * Tile sprite map.
     */
    private static HashMap<TileSprite, Sprite> tileSpriteMap;
    /**
     * Game object sprite map.
     */
    private static HashMap<GameObjectSprite, Sprite> gameObjectSpriteMap;
    /**
     * Player sprite map.
     */
    private static HashMap<PlayerSprite, Sprite> playerSpriteMap;

    static {
        levelSpriteMap = new HashMap<LevelSprite, Sprite>() {{
            put(LevelSprite.UNDERLAY, new Sprite(new Texture("images/level/UNDERLAY.png")));
        }};
        tileSpriteMap = new HashMap<TileSprite, Sprite>() {{
            for (TileSprite sprite : TileSprite.values()) {
                put(sprite, new Sprite(new Texture("images/tile/" + sprite + ".png")));
            }
        }};
        gameObjectSpriteMap = new HashMap<GameObjectSprite, Sprite>() {{
            put(GameObjectSprite.POT, new Sprite(new Texture("images/game_object/POT.png")));
        }};
        playerSpriteMap = new HashMap<PlayerSprite, Sprite>() {{
            for (PlayerSprite sprite : PlayerSprite.values()) {
                put(sprite, new Sprite(new Texture("images/player/" + sprite + ".png")));
            }
        }};
    }

    /**
     * Gets the sprite for the specified level sprite type.
     * @param levelSprite
     * @return
     */
    public static Sprite getSprite(LevelSprite levelSprite) {
        return levelSpriteMap.get(levelSprite);
    }

    /**
     * Gets the sprite for the specified tile sprite type.
     * @param tileSprite
     * @return
     */
    public static Sprite getSprite(TileSprite tileSprite) {
        return tileSpriteMap.get(tileSprite);
    }

    /**
     * Gets the sprite for the specified game object sprite type.
     * @param gameObjectSprite
     * @return
     */
    public static Sprite getSprite(GameObjectSprite gameObjectSprite) {
        return gameObjectSpriteMap.get(gameObjectSprite);
    }

    /**
     * Gets the sprite for the specified player sprite type.
     * @param playerSprite
     * @return
     */
    public static Sprite getSprite(PlayerSprite playerSprite) {
        return playerSpriteMap.get(playerSprite);
    }

    /**
     * Gets the animation for the specified player state type.
     * @param state The state type.
     * @return The animation for the specified player state type.
     */
    public static Animation getPlayerAnimation(PlayerState state) {
        // The number of animation frame columns will differ between animations.
        int columns = 0;
        switch (state) {
            case IDLE_LEFT:
            case IDLE_RIGHT:
                columns = 4;
                break;
            case RUNNING_LEFT:
            case RUNNING_RIGHT:
            case DODGING_LEFT:
            case DODGING_RIGHT:
                columns = 6;
                break;
        }
        // TODO Eventually take player type/colour into account.
        return new Animation(new Texture("images/player/" + state + ".png"), columns, 1, 1/8f);
    }
}
