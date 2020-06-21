package com.dumbpug.dungeony.game.character.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;
import com.dumbpug.dungeony.game.rendering.Animation;
import java.util.HashMap;

/**
 * An NPC character.
 */
public abstract class NPC extends GameCharacter {
    /**
     * The current NPC state.
     */
    protected NPCState state = NPCState.IDLE_LEFT;
    /**
     * The NPC state to animation map.
     */
    protected HashMap<NPCState, Animation> animations = new HashMap<NPCState, Animation>();

    /**
     * Creates a new instance of the NPC class.
     * @param origin The initial origin of the NPC.
     */
    public NPC(Position origin) {
        super(origin);
    }

    @Override
    public float getRenderOrder() {
        // Enemy sprites and animations should be rendered a little higher than their position for a 3D effect.
        return this.getY() + (Constants.GAME_TILE_SIZE / 4f);
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
        batch.draw(currentFrame, this.getX(), this.getY() + (Constants.GAME_TILE_SIZE / 4f), 0, 0, this.getWidth(), this.getHeight(),1.2f, 1.2f, 0);
    }

    /**
     * Update the enemy as part of a single level update.
     * @param grid The level grid used to handle player movement during an update.
     */
    public abstract void update(LevelCollisionGrid grid);

    /**
     * Gets the NPC type.
     * @return The NPC type.
     */
    public abstract NPCType getNPCType();
}
