package com.dumbpug.dungeony.game.character.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.behaviour.INPCBehaviour;
import com.dumbpug.dungeony.game.rendering.Animation;
import java.util.HashMap;

/**
 * An NPC character.
 */
public abstract class NPC extends GameCharacter {
    /**
     * The current NPC state.
     */
    private NPCState state = NPCState.IDLE_LEFT;
    /**
     * The NPC state to animation map.
     */
    protected HashMap<NPCState, Animation> animations = new HashMap<NPCState, Animation>();
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

    /**
     * Sets the NPC behaviour.
     * @param behaviour The NPC behaviour.
     */
    protected void setBehaviour(INPCBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * Gets the NPC state.
     * @return The NPC state.
     */
    public NPCState getState() {
        return state;
    }

    /**
     * Set the NPC state.
     * @param state The NPC state.
     */
    public void setState(NPCState state) {
        this.state = state;
    }

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the enemy based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Render the enemies current animation frame.
        batch.draw(currentFrame, this.getX(), this.getY(), 0, 0, this.getLengthX(), this.getLengthZ(),1.2f, 1.2f, 0);
    }

    /**
     * Update the enemy as part of a single level update.
     * @param level The interactive level.
     */
    public void update(InteractiveEnvironment level) {
        // Tick the enemy behaviour if any has been defined.
        if (this.behaviour != null) {
            this.behaviour.tick(this, level);
        }
    }

    /**
     * Gets the NPC type.
     * @return The NPC type.
     */
    public abstract NPCType getNPCType();
}
