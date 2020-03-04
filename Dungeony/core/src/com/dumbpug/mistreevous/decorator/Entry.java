package com.dumbpug.mistreevous.decorator;

public class Entry extends Decorator {
    /**
     * Creates a new instance of the Entry class.
     * @param method The board method.
     */
    public Entry(String method) {
        super(method);
    }

    @Override
    public Type getType() {
        return Type.ENTRY;
    }
}
