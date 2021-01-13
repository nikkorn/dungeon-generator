package com.dumbpug.dungeony.game.character.npc;

import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.behaviour.INPCBehaviour;

/**
 * An NPC character.
 */
public abstract class NPC extends GameCharacter {
    /**
     * The NPC behaviour.
     */
    private INPCBehaviour behaviour = null;

    /**
     * Creates a new instance of the NPC class.
     * @param origin The initial origin of the NPC.
     */
    public NPC(Position origin) {
        super(origin);
    }

    @Override
    public int getCollisionMask() {
        // An NPC should not collide with anything if they are dead.
        if (this.getState() == GameCharacterState.DEAD_LEFT || this.getState() == GameCharacterState.DEAD_RIGHT) {
            return EntityCollisionFlag.NOTHING;
        }

        return super.getCollisionMask();
    }

    /**
     * Sets the NPC behaviour.
     * @param behaviour The NPC behaviour.
     */
    protected void setBehaviour(INPCBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // Tick the enemy behaviour if any has been defined.
        if (this.behaviour != null) {
            this.behaviour.tick(this, environment, delta);
        }
    }

    /**
     * Gets the NPC type.
     * @return The NPC type.
     */
    public abstract NPCType getNPCType();
}
