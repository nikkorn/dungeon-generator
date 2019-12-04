package com.dumbpug.dungeony.game;

public final class EntityCollisionFlag {
    public static final int NOTHING    = 0;
    public static final int WALL       = 1;
    public static final int PLAYER     = 2;
    public static final int ENEMY      = 4;
    public static final int PICKUP     = 8;
    public static final int PROJECTILE = 16;
    public static final int OBJECT     = 32;
}
