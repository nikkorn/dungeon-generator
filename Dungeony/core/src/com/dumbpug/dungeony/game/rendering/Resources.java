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
    private static HashMap<GameObjectSprite, Sprite> gameObjectMap;

    static {
        levelSpriteMap = new HashMap<LevelSprite, Sprite>() {{
            put(LevelSprite.UNDERLAY, new Sprite(new Texture("images/level/UNDERLAY.png")));
        }};
        tileSpriteMap = new HashMap<TileSprite, Sprite>() {{
            put(TileSprite.BUSH, new Sprite(new Texture("images/tile/BUSH.png")));
            put(TileSprite.WALL, new Sprite(new Texture("images/tile/WALL.png")));
            put(TileSprite.WALL_BOTTOM, new Sprite(new Texture("images/tile/WALL_BOTTOM.png")));
            put(TileSprite.WALL_BOTTOM_LEFT, new Sprite(new Texture("images/tile/WALL_BOTTOM_LEFT.png")));
            put(TileSprite.WALL_BOTTOM_RIGHT, new Sprite(new Texture("images/tile/WALL_BOTTOM_RIGHT.png")));
            put(TileSprite.WALL_TOP, new Sprite(new Texture("images/tile/WALL_TOP.png")));
            put(TileSprite.WALL_TOP_LEFT, new Sprite(new Texture("images/tile/WALL_TOP_LEFT.png")));
            put(TileSprite.WALL_TOP_RIGHT, new Sprite(new Texture("images/tile/WALL_TOP_RIGHT.png")));
            put(TileSprite.WALL_LEFT, new Sprite(new Texture("images/tile/WALL_LEFT.png")));
            put(TileSprite.WALL_RIGHT, new Sprite(new Texture("images/tile/WALL_RIGHT.png")));
            put(TileSprite.WALL_CORNER_BOTTOM_LEFT, new Sprite(new Texture("images/tile/WALL_CORNER_BOTTOM_LEFT.png")));
            put(TileSprite.WALL_CORNER_BOTTOM_RIGHT, new Sprite(new Texture("images/tile/WALL_CORNER_BOTTOM_RIGHT.png")));
            put(TileSprite.WALL_CORNER_TOP_LEFT, new Sprite(new Texture("images/tile/WALL_CORNER_TOP_LEFT.png")));
            put(TileSprite.WALL_CORNER_TOP_RIGHT, new Sprite(new Texture("images/tile/WALL_CORNER_TOP_RIGHT.png")));
            put(TileSprite.GROUND, new Sprite(new Texture("images/tile/GROUND.png")));
        }};
        gameObjectMap = new HashMap<GameObjectSprite, Sprite>() {{
            put(GameObjectSprite.POT, new Sprite(new Texture("images/game_object/POT.png")));
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
        return gameObjectMap.get(gameObjectSprite);
    }

    /**
     * Gets the animation for the specified player state type.
     * @param state The state type.
     * @return The animation for the specified player state type.
     */
    public static Animation getPlayerAnimation(PlayerState state) {
        // TODO Eventually take player type/colour into account.
        return new Animation(new Texture("images/player/" + state + ".png"), 2, 1, 1/16f);
    }
}
