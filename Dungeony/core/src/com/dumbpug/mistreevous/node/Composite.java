package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorator;
import com.dumbpug.mistreevous.decorator.Decorators;
import com.dumbpug.mistreevous.decorator.Exit;
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

    /**
     * Gets the children of the node.
     * @return The children of the node.
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * Gets whether this node is a leaf node.
     * @return Whether this node is a leaf node.
     */
    public boolean isLeafNode() {
        return false;
    }

    /**
     * Abort the running of this node.
     * @param board The board.
     */
    public void abort(IBoard board) {
        // There is nothing to do if this node is not in the running state.
        if (!this.is(State.RUNNING)) {
            return;
        }

        // Abort any child nodes.
        for (Node child : this.children) {
            child.abort(board);
        }

        // Reset the state of this node.
        this.reset();

        // Call the EXIT decorator function if it exists and pass an aborted flag.
        Exit exit = (Exit)this.getDecorators().getDecorator(Decorator.Type.EXIT);
        if (exit != null) {
            exit.call(board, false, true);
        }
    }

    /**
     * Reset the state of the node.
     */
    public void reset() {
        this.setState(State.READY);

        // Reset the state of any child nodes.
        for (Node child : this.children) {
            child.reset();
        }
    }
}
