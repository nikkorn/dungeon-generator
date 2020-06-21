package com.dumbpug.dungeony.game.character.enemy.enemies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.character.npc.NPCState;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;

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

    @Override
    public void update(LevelCollisionGrid grid) {
        // Get the movement on each axis that the enemy is trying to make.
        // TODO For now just walking up and right.
        float movementAxisX = 0.5f;
        float movementAxisY = 0.5f;

        // Process enemy input which would influence the movement of the enemy.
        // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
        grid.move(this, movementAxisX, movementAxisY);

        // Update the actual state of the enemy to reflect and changes that have happened during this update.
        // Is the enemy idle and not moving in any direction?
        if (movementAxisX == 0f && movementAxisY == 0f) {
            // The enemy should be idle and facing whatever direction they already have been.
            switch (this.state) {
                case RUNNING_LEFT:
                    this.state = NPCState.IDLE_LEFT;
                    return;
                case RUNNING_RIGHT:
                    this.state = NPCState.IDLE_RIGHT;
                    return;
                default:
                    return;
            }
        } else {
            // We are running because we are moving on either axis, but the X axis movement determines which way we face.
            this.state = movementAxisX < 0 ? NPCState.RUNNING_LEFT : NPCState.RUNNING_RIGHT;
        }
    }
}
