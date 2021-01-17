package com.dumbpug.dungeony.game.level;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.enemy.EnemyFactory;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.character.friendly.Friendly;
import com.dumbpug.dungeony.game.character.friendly.FriendlyFactory;
import com.dumbpug.dungeony.game.character.friendly.FriendlyType;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectFactory;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.dungeony.game.tile.ITileFinder;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileFactory;
import com.dumbpug.dungeony.game.tile.TileType;
import com.dumbpug.levelgeneration.EntityDefinition;
import com.dumbpug.levelgeneration.EntityOffset;
import com.dumbpug.levelgeneration.ILevelGenerator;
import com.dumbpug.levelgeneration.LevelDefinition;
import com.dumbpug.levelgeneration.TileDefinition;
import java.util.ArrayList;

/**
 * Factory for creating Level instances.
 */
public class LevelFactory {
    /**
     * Create the initial Level instance.
     * @param camera The level camera.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @return The initial Level instance.
     */
    public static Level createInitialLevel(LevelCamera camera, ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails) {
        return createLevel(camera, levelGenerator, playerDetails, "SPAWN.json");
    }

    /**
     * Create a Level instance.
     * @param camera The level camera.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @param category The level category.
     * @param difficulty The level difficulty.
     * @return A Level instance.
     */
    public static Level createLevel(LevelCamera camera, ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails, LevelCategory category, Difficulty difficulty) {
        return createLevel(camera, levelGenerator, playerDetails, category + "_" + difficulty + ".json");
    }

    /**
     * Create a Level instance.
     * @param camera The level camera.
     * @param levelGenerator The level generator to use.
     * @param playerDetails The player details.
     * @param file The file path
     * @return A Level instance.
     */
    private static Level createLevel(LevelCamera camera, ILevelGenerator levelGenerator, ArrayList<PlayerDetails> playerDetails, String file) {
        // Generate the level!
        final LevelDefinition levelDefinition = levelGenerator.generate(file);

        // Create the tile finder that is used by the tile factory in order to find the inspect the type of other tiles.
        ITileFinder tileFinder = new LevelTileFinder(levelDefinition);

        // Create empty lists to hold the game entities.
        ArrayList<Tile> tiles             = new ArrayList<Tile>();
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
        ArrayList<Enemy> enemies          = new ArrayList<Enemy>();
        ArrayList<Friendly> friendlies    = new ArrayList<Friendly>();

        // Create the game objects enemies and actual tiles based on the generated level tile definitions.
        for (TileDefinition tileDefinition : levelDefinition.getTileDefinitions()) {
            // Create the actual tile.
            Tile tile = TileFactory.createTile(TileType.valueOf(tileDefinition.getType()), tileDefinition.getX(), tileDefinition.getY(), tileFinder);

            // Add the tile to the list of all tiles.
            tiles.add(tile);

            // Create game objects, enemies and other things based on the current tile entities.
            for (EntityDefinition entity : tileDefinition.getEntities()) {
                // Get the entity position relative to the tile position.
                Position entityPosition = new Position(tile.getOrigin().getX(), tile.getOrigin().getY());

                if (GameObjectType.isValue(entity.getName())) {
                    // Create the game object.
                    GameObject gameObject = GameObjectFactory.create(GameObjectType.valueOf(entity.getName().toUpperCase()), entityPosition, entity.getProperties());

                    // Apply the positional offset of the entity relative to the tile position.
                    applyEntityPositionOffset(gameObject, entity.getOffset());

                    // Call the game objects 'onPositioned' method to let it know that it has been given its proper position.
                    gameObject.onPositioned();

                    // Add the game object to the list of game objects.
                    gameObjects.add(gameObject);
                } else if (EnemyType.isValue(entity.getName())) {
                    // Create the enemy.
                    Enemy enemy = EnemyFactory.create(EnemyType.valueOf(entity.getName().toUpperCase()), entityPosition, entity.getProperties());

                    // Apply the positional offset of the entity relative to the tile position.
                    applyEntityPositionOffset(enemy, entity.getOffset());

                    // Add the enemy to the list of enemies.
                    enemies.add(enemy);
                } else if (FriendlyType.isValue(entity.getName())) {
                    // Create the friendly.
                    Friendly friendly = FriendlyFactory.create(FriendlyType.valueOf(entity.getName().toUpperCase()), entityPosition, entity.getProperties());

                    // Apply the positional offset of the entity relative to the tile position.
                    applyEntityPositionOffset(friendly, entity.getOffset());

                    // Add the enemy to the list of friendlies.
                    friendlies.add(friendly);
                } else {
                    throw new RuntimeException("cannot create entity instance for entity definition: " + entity.getName());
                }
            }
        }

        // Create and return the level instance.
        return new Level(camera, playerDetails, tiles, gameObjects, enemies, friendlies);
    }

    /**
     * Apply the positional offset of the entity relative to the tile position.
     * @param entity The entity to offset.
     * @param offset The offset to apply.
     */
    private static void applyEntityPositionOffset(Entity entity, EntityOffset offset) {
        float halfTileSize           = Constants.LEVEL_TILE_SIZE / 2f;
        float entityVerticalOffset   = halfTileSize - (entity.getLengthY() / 2F);
        float entityHorizontalOffset = halfTileSize - (entity.getLengthX() / 2F);

        // Are we moving the entity to the top or bottom of the tile?
        if (offset == EntityOffset.TOP_LEFT || offset == EntityOffset.TOP || offset == EntityOffset.TOP_RIGHT) {
            entity.setY(entity.getY() + entityVerticalOffset);
        } else if (offset == EntityOffset.BOTTOM_LEFT || offset == EntityOffset.BOTTOM || offset == EntityOffset.BOTTOM_RIGHT) {
            entity.setY(entity.getY() - entityVerticalOffset);
        }
        // Are we moving the entity to the left or right of the tile?
        if (offset == EntityOffset.TOP_LEFT || offset == EntityOffset.LEFT || offset == EntityOffset.BOTTOM_LEFT) {
            entity.setX(entity.getX() - entityHorizontalOffset);
        } else if (offset == EntityOffset.TOP_RIGHT || offset == EntityOffset.RIGHT || offset == EntityOffset.BOTTOM_RIGHT) {
            entity.setX(entity.getX() + entityHorizontalOffset);
        }
    }
}
