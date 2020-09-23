package com.dumbpug.dungeony.game.character.enemy.enemies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.character.behaviour.BasicEnemyBehaviour;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;

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

        // Give the Fishman some basic enemy behaviour.
        this.setBehaviour(new BasicEnemyBehaviour());
    }

    @Override
    public float getLengthX() {
        // Fishman is the same width as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthY() {
        // Fishman is the same height as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthZ() {
        // Fishman is the same height as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getMovementSpeed() {
        // Fishman can move at 80% of the default speed of the player.
        return Constants.PLAYER_MOVEMENT_PS * 0.8f;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.FISHMAN;
    }
}
