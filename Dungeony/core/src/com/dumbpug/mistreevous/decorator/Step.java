package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;

/**
 * A STEP decorator which defines a blackboard function to call when the decorated node is updated.
 */
public class Step extends Decorator {
    /**
     * Creates a new instance of the Step class.
     * @param method The board method.
     */
    public Step(String method) {
        super(method);
    }

    /**
     * Call the relevant board method.
     * @param board The board.
     */
    public void call(IBoard board) {
        // TODO Call the relevant board method.
    }

    @Override
    public Type getType() {
        return Type.STEP;
    }
}