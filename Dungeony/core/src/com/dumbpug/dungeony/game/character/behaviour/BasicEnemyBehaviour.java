package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.game.Direction;
import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCState;
import com.dumbpug.dungeony.game.level.InteractiveLevel;

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
    public void tick (TNPC subject, InteractiveLevel level) {
        // Move the NPC north-east at 60% their usual speed.
        level.moveByDirection(subject, Direction.NORTH_EAST, 0.6f);

        // We are running to the right.
        subject.setState(NPCState.RUNNING_RIGHT);
    }
}
