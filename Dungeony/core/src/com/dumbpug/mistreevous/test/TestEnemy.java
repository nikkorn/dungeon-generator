package com.dumbpug.mistreevous.test;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;

/**
 * A test enemy board.
 */
public class TestEnemy implements IBoard {

    public boolean isHungry() {
        return true;
    }

    public State roar() {
        System.out.println("Rawr!!!");
        return State.SUCCEEDED;
    }

    public State scream() {
        System.out.println("Ahhhh!!!");
        return State.SUCCEEDED;
    }
}
