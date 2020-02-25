package com.dumbpug.dungeony.game.character.enemies;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.Enemy;
import com.dumbpug.dungeony.game.character.EnemyType;

/**
 * A basic fishman enemy.
 */
public class Fishman extends Enemy {
    /**
     * Creates a new instance of the Fishman class.
     * @param origin The initial origin of the Fishman.
     */
    public Fishman(Position origin) {
        super(origin);
    }

    /**
     * Gets the enemy type.
     * @return The enemy type.
     */
    @Override
    public EnemyType getEnemyType() {
        return EnemyType.FISHMAN;
    }
}
