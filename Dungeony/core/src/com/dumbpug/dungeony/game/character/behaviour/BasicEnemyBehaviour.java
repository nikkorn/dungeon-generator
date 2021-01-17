package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.Direction;
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

        // Move the NPC north-east at their normal movement speed.
        // environment.moveByDirection(subject, Direction.NORTH_EAST, subject.getMovementSpeed(), delta);

        // TODO The Environment methods that move an entity don't handle character state. Options:
        // Add an onMove(float xOffset, float yOffset) to all entities and set running state here for GameCharacter.
        // Call a walk()/walkTowards()/walkInDirection() on GameCharacter that calls environment move methods but handles running/idle state and facing direction changes.
        subject.walk(environment, -20f, -20f, delta);

        // We are running to the right.
        subject.setState(GameCharacterState.RUNNING);
    }
}
