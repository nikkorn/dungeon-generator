package com.dumbpug.mistreevous.nodes;

/**
 * An action node.
 */
public class Action extends Node {
    /**
     * The action name.
     */
    private String actionName;

    /**
     * Creates a new instance of the Action class.
     * @param actionName The action name.
     */
    public Action(String actionName) {
        super(NodeType.ACTION);
        this.actionName = actionName;
    }
}
