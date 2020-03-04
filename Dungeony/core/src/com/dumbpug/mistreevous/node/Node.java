package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;

/**
 * A tree node.
 */
public abstract class Node {
    /**
     * The state of the node.
     */
    private State state = State.READY;
    /**
     * The node decorators.
     */
    private Decorators decorators;

    /**
     * Creates a new instance of the Node class.
     * @param decorators The node decorators.
     */
    public Node(Decorators decorators) {
        this.decorators = decorators;
    }

    /**
     * Gets the node state.
     * @return The node state.
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the node state.
     * @param state The node state.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets whether this node is in the specified state.
     * @param value The value to compare to the node state.
     * @returns Whether this node is in the specified state.
     */
    public boolean is(State value) {
        return this.state == value;
    }

    /**
     * Reset the state of the node.
     */
    public void reset() {
        this.setState(State.READY);
    }

    /**
     * Update the node.
     * @param board The board.
     */
    public void update(IBoard board) {
        // If this node is already in a 'SUCCEEDED' or 'FAILED' state then there is nothing to do.
        if (this.is(State.SUCCEEDED) || this.is(State.FAILED)) {
            // We have not changed state.
            return;
        }

        // ...
    }

    /**
     * Update the node and get whether the node state has changed.
     * @param board The board.
     */
    public abstract void onUpdate(IBoard board);

    /**
     * Gets the node type.
     * @return The node type.
     */
    public abstract NodeType getNodeType();
}
