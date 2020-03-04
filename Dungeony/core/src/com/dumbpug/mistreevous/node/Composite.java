package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.decorator.Decorators;
import java.util.ArrayList;

/**
 * A composite node.
 */
public abstract class Composite extends Node {
    /**
     * The child node of this composite node.
     */
    private ArrayList<Node> children;

    /**
     * Creates a new instance of the Composite class.
     * @param decorators The node decorators.
     * @param children The child node of this composite node.
     */
    public Composite(Decorators decorators, ArrayList<Node> children) {
        super(decorators);
        this.children = children;
    }
}
