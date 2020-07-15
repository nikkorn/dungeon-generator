package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;

/**
 * A vending machine that items can be purchased from.
 */
public class Vendor extends GameObject {
    /**
     * Creates a new instance of the Vendor class.
     * @param origin The initial origin of the Vendor.
     */
    public Vendor(Position origin) {
        super(origin);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.VENDOR;
    }

    @Override
    public float getLengthX() {
        return 40f;
    }

    @Override
    public float getLengthY() {
        return 20f;
    }

    @Override
    public float getLengthZ() {
        return 60f;
    }
}
