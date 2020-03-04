package com.dumbpug.mistreevous.decorator;

public class Exit extends Decorator {
    /**
     * Creates a new instance of the Exit class.
     * @param method The board method.
     */
    public Exit(String method) {
        super(method);
    }

    @Override
    public Type getType() {
        return Type.EXIT;
    }
}