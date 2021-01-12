package com.dumbpug.dungeony.game.character.friendly;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCType;
import com.dumbpug.dungeony.game.rendering.GameCharacterSprite;
import com.dumbpug.dungeony.game.rendering.Resources;

/**
 * A friendly NPC.
 */
public abstract class Friendly extends NPC {
    /**
     * Creates a new instance of the NPC class.
     * @param origin The initial origin of the NPC.
     */
    public Friendly(Position origin) {
        super(origin);

        // Populate the enemy animation map.
        for (GameCharacterState state : GameCharacterState.values()) {
            this.animations.put(state, Resources.getCharacterAnimation(state, this.getFriendlyType()));
        }

        // Set the friendly shadow sprite.
        this.shadowSprite = Resources.getCharacterSprite(GameCharacterSprite.SHADOW, this.getFriendlyType());
    }

    /**
     * Gets the friendly type.
     * @return The friendly type.
     */
    public abstract FriendlyType getFriendlyType();

    /**
     * Gets the NPC type.
     * @return The NPC type.
     */
    @Override
    public NPCType getNPCType() {
        return NPCType.FRIENDLY;
    }
}
