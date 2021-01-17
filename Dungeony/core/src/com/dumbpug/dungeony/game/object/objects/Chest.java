package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.levelgeneration.IEntityProperties;

public class Chest extends GameObject {
    /**
     * Creates a new instance of the Chest class.
     * @param origin The initial origin of the GameObject.
     */
    public Chest(Position origin, IEntityProperties properties) {
        super(origin, properties);
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

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) { }

    @Override
    public GameObjectType getType() {
        return GameObjectType.CHEST;
    }
}
