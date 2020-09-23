package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.game.character.npc.NPC;

/**
 * Represents behaviour for an NPC character.
 */
public interface INPCBehaviour<TNPC extends NPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param environment The game environment.
     * @param delta The delta time.
     */
    void tick (TNPC subject, InteractiveEnvironment environment, float delta);
}
