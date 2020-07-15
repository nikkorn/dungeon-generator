package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;

public class Chest extends GameObject {
    /**
     * Creates a new instance of the Chest class.
     * @param origin The initial origin of the GameObject.
     */
    public Chest(Position origin) {
        super(origin);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.CHEST;
    }

    @Override
    public float getLengthX() {
        return 24f;
    }

    @Override
    public float getLengthY() {
        return 24f;
    }

    @Override
    public float getLengthZ() {
        return 24f;
    }
}
