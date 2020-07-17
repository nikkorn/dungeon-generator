package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.enemy.enemies.Fishman;
import org.json.JSONObject;

/**
 * Factory for creating enemy NPC instances.
 */
public class EnemyFactory {
    /**
     * Create an Enemy instance of the given type.
     * @param type The enemy type.
     * @param position The initial position of the enemy.
     * @param details A JSON object containing any additional details about the enemy.
     * @return An Enemy instance of the given type and position.
     */
    public static Enemy create(EnemyType type, Position position, JSONObject details) {
        switch (type) {
            case FISHMAN:
                return new Fishman(position);
            default:
                throw new RuntimeException("cannot create Enemy instance for unknown type: " + type);
        }
    }
}
