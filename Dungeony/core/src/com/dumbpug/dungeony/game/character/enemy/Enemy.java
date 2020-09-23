package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.character.npc.NPCState;
import com.dumbpug.dungeony.game.character.npc.NPCType;
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
        for (NPCState state : NPCState.values()) {
            this.animations.put(state, Resources.getEnemyAnimation(state, this.getEnemyType()));
        }
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
