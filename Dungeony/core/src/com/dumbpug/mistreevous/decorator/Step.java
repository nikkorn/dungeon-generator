package com.dumbpug.mistreevous.decorator;

public class Step extends Decorator {
    /**
     * Creates a new instance of the Step class.
     * @param method The board method.
     */
    public Step(String method) {
        super(method);
    }

    @Override
    public Type getType() {
        return Type.STEP;
    }
}