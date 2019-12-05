package com.dumbpug.dungeony.game.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
            put(TileSprite.WALL, new Sprite(new Texture("resources/tile/WALL.png")));
        }};
        gameObjectMap = new HashMap<GameObjectSprite, Sprite>() {{
            put(GameObjectSprite.POT, new Sprite(new Texture("resources/game_object/POT.png")));
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
}