package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.guard.Guard;
import java.util.ArrayList;

/**
 * A collection of node decorators.
 */
public class Decorators {
    /**
     * The list of all decorators.
     */
    private ArrayList<Decorator> decorators;
    /**
     * The list of guard decorators.
     */
    private ArrayList<Guard> guards = new ArrayList<Guard>();

    /**
     * Creates an instance of the Decorators class.
     * @param decorators The list of decorators.
     */
    public Decorators(ArrayList<Decorator> decorators) {
        this.decorators = decorators;

        // Collect any guard decorators into their own list.
        for (Decorator decorator : decorators) {
            if (decorator.isGuard()) {
                guards.add((Guard)decorator);
            }
        }

        // Verify that there are no duplicate decorator types.
        verifyDecoratorsAreUnique();
    }

    /**
     * Gets the list of guard nodes.
     * @return The list of guard nodes.
     */
    public ArrayList<Guard> getGuards() {
        return this.guards;
    }

    /**
     * Attempt to get the decorator with the given type, or null if one isn't available.
     * @param type The decorator type.
     * @return The decorator with the given type, or null if one isn't available.
     */
    public Decorator getDecorator(Decorator.Type type) {
        for (Decorator decorator: this.decorators) {
            if (decorator.getType() == type) {
                return decorator;
            }
        }
        return null;
    }

    /**
     * Verify that there are no duplicate decorator types.
     */
    public void verifyDecoratorsAreUnique() {
        ArrayList<Decorator.Type> types = new ArrayList<Decorator.Type>();

        for (Decorator decorator: this.decorators) {
            if (types.contains(decorator.getType())) {
                throw new RuntimeException("duplicate decorator type '" + decorator.getType() + "' found for node");
            }

            types.add(decorator.getType());
        }
    }
}
