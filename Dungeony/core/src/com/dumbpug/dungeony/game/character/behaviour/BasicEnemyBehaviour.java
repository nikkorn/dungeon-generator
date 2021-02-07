package com.dumbpug.dungeony.game.character.behaviour;

import com.badlogic.gdx.ai.btree.utils.BehaviorTreeReader;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;

/**
 * Very basic enemy NPC behaviour.
 * @param <TNPC> The type of NPC.
 */
public class BasicEnemyBehaviour<TNPC extends NPC> implements INPCBehaviour<TNPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param environment The game environment.
     * @param delta The delta time.
     */
    public void tick (TNPC subject, InteractiveEnvironment environment, float delta) {
        // There is nothing to do if the enemy is dead.
        if (subject.getState() == GameCharacterState.DEAD) {
            return;
        }

        // Do nothing if the subject is sleeping.
        if (subject.getState() == GameCharacterState.SLEEPING) {
            return;
        }

        // Find the closest player entity.
        Entity closestPlayer = BehaviourUtilities.getClosestPlayerEntity(subject, environment);

        // TODO Is closest player entity within range? If not then set idle and return here.
        // TODO Replace with BehaviourUtilities.hasLineOfSightTo()!!!!!!!!!
        if (subject.distanceTo(closestPlayer) < subject.getMaxVisibilityDistance()) {
            // Aim at the player.
            subject.setAngleOfView(subject.angleTo(closestPlayer));

            // Attack the player using our weapon if we can.
            if (BehaviourUtilities.canAttackWithWeapon(subject.getWeapon(), closestPlayer)) {
                subject.getWeapon().use(environment, subject, true, delta);
            }

            // Move towards the closest player.
            subject.walkByAngle(environment, subject.angleTo(closestPlayer), subject.getMovementSpeed(), delta);
            return;
        }

        // We are not aiming at anything.
        subject.setAngleOfView(null);

        // We fall back to the IDLE state.
        subject.setState(GameCharacterState.IDLE);
    }
}
