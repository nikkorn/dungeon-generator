package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;

/**
 * An ENTRY decorator which defines a blackboard function to call
 * when the decorated node is updated and moves out of running state.
 */
public class Entry extends Decorator {
    /**
     * Creates a new instance of the Entry class.
     * @param method The board method.
     */
    public Entry(String method) {
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
        return Type.ENTRY;
    }
}
