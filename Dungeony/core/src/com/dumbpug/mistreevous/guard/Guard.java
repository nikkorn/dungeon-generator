package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.decorator.Decorator;

/**
 * A node guard decorator.
 */
public abstract class Guard extends Decorator {
    /**
     * Creates a new instance of the Guard class.
     * @param method The board method.
     */
    public Guard(String method) {
        super(method);
    }

    /**
     * Gets whether the guard is satisfied.
     * @param board The board.
     * @returns Whether the guard is satisfied.
     */
    public abstract boolean isSatisfied(IBoard board);
}
