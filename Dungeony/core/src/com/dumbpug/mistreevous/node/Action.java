package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.decorator.Decorators;

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
     * @param decorators The node decorators.
     * @param actionName The action name.
     */
    public Action(Decorators decorators, String actionName) {
        super(decorators);
        this.actionName = actionName;
    }

    @Override
    public void onUpdate(IBoard board) {
        // TODO
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ACTION;
    }
}
