package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.dungeony.game.character.PlayerState;
import java.util.HashMap;

public class Resources {
    /**
     * Tile sprite map.
     */
    private static HashMap<TileSprite, Sprite> tileSpriteMap;
    /**
     * Game object sprite map.
     */
    private static HashMap<GameObjectSprite, Sprite> gameObjectMap;

    static {
        tileSpriteMap = new HashMap<TileSprite, Sprite>() {{
            put(TileSprite.WALL, new Sprite(new Texture("images/tile/WALL.png")));
            put(TileSprite.WALL_BLOCKED, new Sprite(new Texture("images/tile/WALL_BLOCKED.png")));
            put(TileSprite.WALL_LIP, new Sprite(new Texture("images/tile/WALL_LIP.png")));
            put(TileSprite.EMPTY_GROUND, new Sprite(new Texture("images/tile/EMPTY_GROUND.png")));
        }};
        gameObjectMap = new HashMap<GameObjectSprite, Sprite>() {{
            put(GameObjectSprite.POT, new Sprite(new Texture("images/game_object/POT.png")));
        }};
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
