package com.dumbpug.mistreevous.nodes;

import java.util.ArrayList;

/**
 * A tree node.
 */
public class Node {
    /**
     * The type of the node.
     */
    private NodeType type;

    /**
     * Creates a new instance of the Node class.
     * @param type The node type.
     */
    public Node(NodeType type) {
        this.type = type;
    }

    /**
     * Gets the node type.
     * @return The node type.
     */
    public NodeType getNodeType() {
        return this.type;
    }
}
