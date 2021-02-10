package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;

/**
 * Very basic enemy NPC behaviour.
 * @param <TNPC> The type of NPC.
 */
public class BasicEnemyBehaviour<TNPC extends NPC> extends NPCBehaviour<TNPC> {
    /**
     * Tick the NPC behaviour.
     */
    public void onTick() {
        // There is nothing to do if the enemy is dead.
        if (inState(GameCharacterState.DEAD)) {
            return;
        }

        // Do nothing if the subject is sleeping.
        if (inState(GameCharacterState.SLEEPING)) {
            return;
        }

        // Find the closest player entity.
        Entity closestPlayer = getClosestPlayerEntity();

        // Can the subject actually see the closest player?
        if (canSee(closestPlayer)) {
            // Aim at the player.
            setAngleOfView(angleTo(closestPlayer));

            // Attack the player using our weapon if we can.
            if (canAttack(closestPlayer)) {
                attack();
            }

            // Walk towards the closest player.
            walkTowards(closestPlayer);
            return;
        }

        // We are not aiming at anything.
        setAngleOfView(null);

        // We fall back to the IDLE state.
        setState(GameCharacterState.IDLE);
    }
}
