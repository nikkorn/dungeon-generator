package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;

/**
 * An EXIT decorator which defines a blackboard function to call when the
 * decorated node is updated and moves to a finished state or is aborted.
 */
public class Exit extends Decorator {
    /**
     * Creates a new instance of the Exit class.
     * @param method The board method.
     */
    public Exit(String method) {
        super(method);
    }

    /**
     * Call the relevant board method.
     * @param board The board.
     * @param isSuccess Whether the decorated node was left with a success state.
     * @param isAborted Whether the decorated node was aborted.
     */
    public void call(IBoard board, boolean isSuccess, boolean isAborted) {
        // TODO Call the relevant board method.
    }

    @Override
    public Type getType() {
        return Type.EXIT;
    }
}