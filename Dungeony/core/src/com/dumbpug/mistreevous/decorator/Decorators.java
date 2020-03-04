package com.dumbpug.mistreevous.decorator;

import java.util.ArrayList;

/**
 * A collection of node decorators.
 */
public class Decorators {
    /**
     * The list of decorators.
     */
    private ArrayList<Decorator> decorators;

    /**
     * Creates an instance of the Decorators class.
     * @param decorators The list of decorators.
     */
    public Decorators(ArrayList<Decorator> decorators) {
        this.decorators = decorators;

        // Verify that there are no duplicate decorator types.
        verifyDecoratorsAreUnique();
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
