package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.IBoard;

public class While extends Guard {
    /**
     * Creates a new instance of the While class.
     * @param method The board method.
     */
    public While(String method) {
        super(method);
    }

    @Override
    public boolean isSatisfied(IBoard board) {
        return false;
    }

    @Override
    public Type getType() {
        return Type.WHILE;
    }
}
