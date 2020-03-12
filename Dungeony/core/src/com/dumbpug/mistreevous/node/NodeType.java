package com.dumbpug.mistreevous.node;

/**
 * Enumeration of node types.
 */
public enum NodeType {
    /**
     * Leaf.
     */
    ACTION,
    CONDITION,
    WAIT,

    /**
     * Composite.
     */
    ROOT,
    SEQUENCE,
    SELECTOR,
    REPEAT
}
