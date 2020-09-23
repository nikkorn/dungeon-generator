package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCState;

/**
 * Very basic enemy NPC behaviour.
 * @param <TNPC> The type of NPC.
 */
public class BasicEnemyBehaviour<TNPC extends NPC> implements INPCBehaviour<TNPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param level The interactive level.
     */
    public void tick (TNPC subject, InteractiveEnvironment level) {
        // Move the NPC north-east at their normal movement speed.
        level.moveByDirection(subject, Direction.NORTH_EAST, subject.getMovementSpeed());

        // We are running to the right.
        subject.setState(NPCState.RUNNING_RIGHT);
    }
}
