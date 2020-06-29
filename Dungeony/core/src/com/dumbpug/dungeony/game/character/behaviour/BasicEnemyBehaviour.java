package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCState;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;

/**
 * Very basic enemy NPC behaviour.
 * @param <TNPC> The type of NPC.
 */
public class BasicEnemyBehaviour<TNPC extends NPC> implements INPCBehaviour<TNPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param grid The level collision grid.
     */
    @Override
    public void tick(TNPC subject, LevelCollisionGrid grid) {
        // Get the movement on each axis that the enemy is trying to make.
        // TODO For now just walking up and right.
        float movementAxisX = 0.5f;
        float movementAxisY = 0.5f;

        // Process enemy input which would influence the movement of the enemy.
        // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
        grid.move(subject, movementAxisX, movementAxisY);

        // Update the actual state of the enemy to reflect and changes that have happened during this update.
        // Is the enemy idle and not moving in any direction?
        if (movementAxisX == 0f && movementAxisY == 0f) {
            // The enemy should be idle and facing whatever direction they already have been.
            switch (subject.getState()) {
                case RUNNING_LEFT:
                    subject.setState(NPCState.IDLE_LEFT);
                    return;
                case RUNNING_RIGHT:
                    subject.setState(NPCState.IDLE_RIGHT);
                    return;
                default:
                    return;
            }
        } else {
            // We are running because we are moving on either axis, but the X axis movement determines which way we face.
            subject.setState(movementAxisX < 0 ? NPCState.RUNNING_LEFT : NPCState.RUNNING_RIGHT);
        }
    }
}
