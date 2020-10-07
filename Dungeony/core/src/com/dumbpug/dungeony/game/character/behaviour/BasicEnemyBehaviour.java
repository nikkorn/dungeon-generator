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
        // Move the NPC north-east at their normal movement speed.
        environment.moveByDirection(subject, Direction.NORTH_EAST, subject.getMovementSpeed(), delta);

        // We are running to the right.
        subject.setState(GameCharacterState.RUNNING_RIGHT);
    }
}
