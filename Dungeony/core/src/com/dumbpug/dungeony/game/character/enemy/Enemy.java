package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCType;
import com.dumbpug.dungeony.game.rendering.GameCharacterSprite;
import com.dumbpug.dungeony.game.rendering.Resources;

/**
 * An enemy NPC.
 */
public abstract class Enemy extends NPC {
    /**
     * Creates a new instance of the Enemy class.
     * @param origin The initial origin of the Enemy.
     */
    public Enemy(Position origin) {
        super(origin);

        // Populate the enemy animation map.
        for (GameCharacterState state : GameCharacterState.values()) {
            this.animations.put(state, Resources.getCharacterAnimation(state, this.getEnemyType()));
        }

        // Set the enemy shadow sprite.
        this.shadowSprite = Resources.getCharacterSprite(GameCharacterSprite.SHADOW, this.getEnemyType());
    }

    /**
     * Gets the enemy type.
     * @return The enemy type.
     */
    public abstract EnemyType getEnemyType();

    /**
     * Gets the NPC type.
     * @return The NPC type.
     */
    @Override
    public NPCType getNPCType() {
        return NPCType.ENEMY;
    }
}
