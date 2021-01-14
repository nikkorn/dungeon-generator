package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dumbpug.dungeony.game.character.FacingDirection;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;
import com.dumbpug.dungeony.game.character.player.PlayerType;
import com.dumbpug.dungeony.game.object.GameObjectState;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.dungeony.game.projectile.ProjectileType;
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
     * Projectile texture map.
     */
    private static HashMap<ProjectileType, Texture> projectileTextureMap;

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
        projectileTextureMap = new HashMap<ProjectileType, Texture>() {{
            for (ProjectileType type : ProjectileType.values()) {
                put(type, new Texture("images/projectile/" + type + ".png"));
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

    /**
     * Gets the animation for the specified projectile type.
     * @param type The projectile type.
     * @return The animation for the specified projectile type.
     */
    public static Animation getProjectileAnimation(ProjectileType type) {
        return new Animation(projectileTextureMap.get(type), 4, 1, 1/16f);
    }

    /**
     * Gets the sprite for the specified game character sprite and player type.
     * @param sprite The game character sprite type.
     * @param type The player type.
     * @return The sprite for the specified game character sprite and player type.
     */
    public static Sprite getCharacterSprite(GameCharacterSprite sprite, PlayerType type) {
        return new Sprite(new Texture("images/character/player/" + type  + "/" + sprite + ".png"));
    }

    /**
     * Gets the sprite for the specified game character sprite and enemy type.
     * @param sprite The game character sprite type.
     * @param type The enemy type.
     * @return The sprite for the specified game character sprite and enemy type.
     */
    public static Sprite getCharacterSprite(GameCharacterSprite sprite, EnemyType type) {
        return new Sprite(new Texture("images/character/enemy/" + type  + "/" + sprite + ".png"));
    }

    /**
     * Gets the sprite for the specified game character sprite and friendly type.
     * @param sprite The game character sprite type.
     * @param type The friendly type.
     * @return The sprite for the specified game character sprite and friendly type.
     */
    public static Sprite getCharacterSprite(GameCharacterSprite sprite, FriendlyType type) {
        return new Sprite(new Texture("images/character/friendly/" + type  + "/" + sprite + ".png"));
    }

    /**
     * Gets the animation for the specified player state type.
     * @param state The state type.
     * @param type The enemy type.
     * @param direction The character facing direction.
     * @return The animation for the specified player state and type.
     */
    public static Animation getCharacterAnimation(GameCharacterState state, PlayerType type, FacingDirection direction) {
        return getCharacterAnimation(state, direction, "player", type.toString());
    }

    /**
     * Gets the animation for the specified enemy state type.
     * @param state The state type.
     * @param type The enemy type.
     * @param direction The character facing direction.
     * @return The animation for the specified enemy state and type.
     */
    public static Animation getCharacterAnimation(GameCharacterState state, EnemyType type, FacingDirection direction) {
        return getCharacterAnimation(state, direction, "enemy", type.toString());
    }

    /**
     * Gets the animation for the specified friendly state type.
     * @param state The state type.
     * @param type The friendly type.
     * @param direction The character facing direction.
     * @return The animation for the specified friendly state and type.
     */
    public static Animation getCharacterAnimation(GameCharacterState state, FriendlyType type, FacingDirection direction) {
        return getCharacterAnimation(state, direction, "friendly", type.toString());
    }

    /**
     * Gets the animation for the specified game character state type.
     * @param state The state type.
     * @param direction The character facing direction.
     * @param category The character category (enemy/friendly/player).
     * @param type The character type.
     * @return The animation for the specified game character state and type.
     */
    private static Animation getCharacterAnimation(GameCharacterState state, FacingDirection direction, String category, String type) {

        // TODO Should have a 'loop' flag that Animation takes as an argument, as the death animation shouldn't loop.

        // The number of animation frame columns will differ between animations.
        int columns = 0;
        switch (state) {
            case HIDDEN:
                columns = 1;
                break;
            case IDLE:
                columns = category.equals("player") ? 24 : 4;
                break;
            case RUNNING:
            case DODGING:
                columns = category.equals("player") ? 8 : 6;
                break;
            case DEAD:
                columns = 4;
                break;
            default:
                throw new RuntimeException("unknown game character state: " + state);
        }
        return new Animation(new Texture("images/character/" + category.toLowerCase() + "/" + type.toUpperCase()  + "/" + state +  "_" + direction +".png"), columns, 1, 1/12f);
    }

}
