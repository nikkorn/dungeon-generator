package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.node.Node;

/**
 * An exception thrown when evaluating node guard path conditions and a conditions fails.
 */
public class GuardUnsatisfiedException extends RuntimeException {
    /**
     * The source node.
     */
    private Node source;

    /**
     * Creates a new instance of the GuardUnsatisfiedException class.
     * @param source The source node.
     */
    public GuardUnsatisfiedException(Node source) {
        this.source = source;
    }

    /**
     * Gets whether the specified node is the node at which a guard condition failed.
     * @param node The node to check against the source node.
     * @returns Whether the specified node is the node at which a guard condition failed.
     */
    public boolean isSourceNode(Node node) {
        return node == source;
    }
}
