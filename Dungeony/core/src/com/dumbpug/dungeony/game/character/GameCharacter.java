package com.dumbpug.dungeony.game.character;

import com.dumbpug.dungeony.game.Entity;
import com.dumbpug.dungeony.game.Position;

public abstract class GameCharacter extends Entity {
    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public GameCharacter(Position origin) {
        super(origin);
    }
}
