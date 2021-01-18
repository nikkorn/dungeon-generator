package com.dumbpug.dungeony.game.character.npc;

import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.behaviour.INPCBehaviour;
import com.dumbpug.levelgeneration.IEntityProperties;

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
    public NPC(Position origin, IEntityProperties properties) {
        super(origin);

        // Check for a character state override in the provided details.
        if (properties.has("state")) {
            this.setState(GameCharacterState.valueOf(properties.getString("state").toUpperCase()));
        }
    }

    @Override
    public int getCollisionLayers() {
        // An NPC should not collide with anything if they are dead.
        if (this.getState() == GameCharacterState.DEAD) {
            return EntityCollisionFlag.NOTHING;
        } else {
            return super.getCollisionLayers();
        }
    }

    @Override
    public int getCollisionMask() {
        // An NPC should not be collidable they are dead.
        if (this.getState() == GameCharacterState.DEAD) {
            return EntityCollisionFlag.NOTHING;
        } else {
            return super.getCollisionMask();
        }
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
        // If the character is running then stop.
        if (this.getState() == GameCharacterState.RUNNING) {
            // TODO: Should we do this? Come back to this after writing a few different behaviours.
            // this.setState(GameCharacterState.IDLE);
        }

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
