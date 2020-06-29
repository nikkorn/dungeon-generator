package com.dumbpug.dungeony.game.character.enemy.enemies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
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
    public float getWidth() {
        // Fishman is the same width as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getHeight() {
        // Fishman is the same height as the player.
        return Constants.GAME_PLAYER_SIZE;
    }

    @Override
    public float getMovementSpeed() {
        // Fishman can move at 80% of the default speed of the player.
        return Constants.GAME_PLAYER_MOVEMENT * 0.8f;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.FISHMAN;
    }
}
