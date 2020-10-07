package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;
import com.dumbpug.dungeony.game.character.npc.NPCState;
import com.dumbpug.dungeony.game.character.player.PlayerState;
import com.dumbpug.dungeony.game.object.GameObjectState;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.dungeony.game.weapon.WeaponState;
import com.dumbpug.dungeony.game.weapon.WeaponType;

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
                put(sprite, new Sprite(new Texture("images/character/player/" + sprite + ".png")));
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
        return new Animation(new Texture("images/character/player/" + state + ".png"), columns, 1, 1/8f);
    }

    /**
     * Gets the sprite for the specified enemy sprite and type.
     * @param enemySprite The enemy sprite.
     * @param type The enemy type.
     * @return The sprite for the specified enemy sprite and type.
     */
    public static Sprite getSprite(EnemySprite enemySprite, EnemyType type) {
        // TODO Cache these in resource sprite maps.
        return new Sprite(new Texture("images/character/enemy/" + type  + "/" + enemySprite + ".png"));
    }

    /**
     * Gets the animation for the specified enemy state type.
     * @param state The state type.
     * @param type The enemy type.
     * @return The animation for the specified enemy state and type.
     */
    public static Animation getEnemyAnimation(NPCState state, EnemyType type) {
        // The number of animation frame columns will differ between animations.
        int columns = 0;
        switch (state) {
            case HIDDEN:
                columns = 1;
            case IDLE_LEFT:
            case IDLE_RIGHT:
                columns = 4;
                break;
            case RUNNING_LEFT:
            case RUNNING_RIGHT:
                columns = 6;
                break;
        }
        return new Animation(new Texture("images/character/enemy/" + type  + "/" + state + ".png"), columns, 1, 1/8f);
    }

    /**
     * Gets the sprite for the specified friendly sprite and type.
     * @param friendlySprite The friendly sprite.
     * @param type The friendly type.
     * @return The sprite for the specified friendly sprite and type.
     */
    public static Sprite getSprite(FriendlySprite friendlySprite, FriendlyType type) {
        // TODO Cache these in resource sprite maps.
        return new Sprite(new Texture("images/character/friendly/" + type  + "/" + friendlySprite + ".png"));
    }

    /**
     * Gets the animation for the specified friendly state type.
     * @param state The state type.
     * @param type The friendly type.
     * @return The animation for the specified friendly state and type.
     */
    public static Animation getFriendlyAnimation(NPCState state, FriendlyType type) {
        // The number of animation frame columns will differ between animations.
        int columns = 0;
        switch (state) {
            case HIDDEN:
                columns = 1;
            case IDLE_LEFT:
            case IDLE_RIGHT:
                columns = 4;
                break;
            case RUNNING_LEFT:
            case RUNNING_RIGHT:
                columns = 6;
                break;
        }
        return new Animation(new Texture("images/character/friendly/" + type  + "/" + state + ".png"), columns, 1, 1/8f);
    }

    /**
     * Gets the animation for the specified game object state type.
     * @param state The state type.
     * @param type The game object type.
     * @return The animation for the specified game object state and type.
     */
    public static Animation getGameObjectAnimation(GameObjectState state, GameObjectType type) {
        return new Animation(new Texture("images/game_object/" + type  + "/" + state + ".png"), 4, 1, 1/8f);
    }

    /**
     * Gets the animation for the specified weapon and weapon state type.
     * @param state The weapon state type.
     * @param type The weapon type.
     * @return The animation for the specified weapon state and type.
     */
    public static Animation getWeaponAnimation(WeaponState state, WeaponType type) {
        return new Animation(new Texture("images/weapon/" + type  + "/" + state + ".png"), 4, 1, 1/8f);
    }
}
