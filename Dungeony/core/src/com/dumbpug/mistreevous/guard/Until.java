package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.IBoard;

public class Until extends Guard {
    /**
     * Creates a new instance of the Until class.
     * @param method The board method.
     */
    public Until(String method) {
        super(method);
    }

    @Override
    public boolean isSatisfied(IBoard board) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.UNTIL;
    }
}
