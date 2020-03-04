package com.dumbpug.mistreevous.decorator;

/**
 * A node decorator.
 */
public abstract class Decorator {
    /**
     * The board method.
     */
    protected String method;

    /**
     * Creates a new instance of the Decorator class.
     * @param method The board method.
     */
    public Decorator(String method) {
        this.method = method;
    }

    /**
     * Gets whether the decorator is a guard type.
     * @return Whether the decorator is a guard type.
     */
    public boolean isGuard() {
        switch (this.getType()) {
            case WHILE:
            case UNTIL:
                return true;
            default:
                return false;
        }
    }

    /**
     * Gets the decorator type.
     * @return The decorator type.
     */
    public abstract Decorator.Type getType();

    /**
     * Enumeration of node decorator types.
     */
    public enum Type {
        ENTRY,
        STEP,
        EXIT,
        WHILE,
        UNTIL
    }
}
