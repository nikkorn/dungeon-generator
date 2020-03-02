package com.dumbpug.mistreevous.nodes;

import java.util.ArrayList;

/**
 * A composite node.
 */
public class Composite extends Node {
    /**
     * The child nodes of this composite node.
     */
    private ArrayList<Node> children;

    /**
     * Creates a new instance of the Composite class.
     * @param type The node type.
     * @param children The child nodes of this composite node.
     */
    public Composite(NodeType type, ArrayList<Node> children) {
        super(type);
        this.children = children;
    }
}
