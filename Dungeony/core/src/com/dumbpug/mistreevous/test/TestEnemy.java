package com.dumbpug.mistreevous.test;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;

/**
 * A test enemy board.
 */
public class TestEnemy implements IBoard {
    /** The enemy hunger level. */
    private int hungerLevel = 0;

    /**
     * Gets whether the enemy is hungry.
     * @return whether the enemy is hungry.
     */
    public boolean isHungry() {
        return hungerLevel > 5;
    }

    public State roar() {
        System.out.println("Rawr!!!");

        // Roaring makes us hungry!
        this.hungerLevel++;

        // We succeeded to roar.
        return State.SUCCEEDED;
    }

    public State scream() {
        System.out.println("Ahhhh!!!");

        // Screaming makes us hungry!
        this.hungerLevel++;

        // We succeeded to scream.
        return State.SUCCEEDED;
    }

    public State eat() {
        System.out.println("Nom!! Nom!! Nom!!");

        // We will become less hungry.
        this.hungerLevel -= 5;

        if (this.hungerLevel < 0) {
            this.hungerLevel = 0;
        }

        // We succeeded to eat.
        return State.SUCCEEDED;
    }
}
