package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorator;
import com.dumbpug.mistreevous.decorator.Decorators;
import com.dumbpug.mistreevous.decorator.Entry;
import com.dumbpug.mistreevous.decorator.Exit;
import com.dumbpug.mistreevous.decorator.Step;
import com.dumbpug.mistreevous.guard.GuardPath;
import com.dumbpug.mistreevous.guard.GuardUnsatisfiedException;

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
     * The guard path for this node.
     */
    private GuardPath guardPath = null;

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
     * Gets whether this node has a guard path assigned.
     * @return "hether this node has a guard path assigned.
     */
    public boolean hasGuardPath() {
        return guardPath != null;
    }

    /**
     * Sets the node guard path.
     * @param guardPath The node guard path.
     */
    public void setGuardPath(GuardPath guardPath) {
        this.guardPath = guardPath;
    }

    /**
     * Gets the node decorators.
     * @return The node decorators.
     */
    public Decorators getDecorators() {
        return decorators;
    }

    /**
     * Gets whether this node is a leaf node.
     * @return Whether this node is a leaf node.
     */
    public boolean isLeafNode() {
        return true;
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
     * Update the node.
     * @param board The board.
     */
    public void update(IBoard board) {
        // If this node is already in a 'SUCCEEDED' or 'FAILED' state then there is nothing to do.
        if (this.is(State.SUCCEEDED) || this.is(State.FAILED)) {
            // We have not changed state.
            return;
        }

        try {
            // Evaluate all of the guard path conditions for the current tree path.
            if (this.hasGuardPath()) {
                this.guardPath.evaluate(board);
            }

            // If this node is in the READY state then call the ENTRY decorator for this node if it exists.
            if (this.is(State.READY)) {
                // Call the ENTRY decorator function if it exists.
                Entry entry = (Entry)this.decorators.getDecorator(Decorator.Type.ENTRY);
                if (entry != null) {
                    entry.call(board);
                }
            }

            // Call the STEP decorator function if it exists.
            Step step = (Step)this.decorators.getDecorator(Decorator.Type.STEP);
            if (step != null) {
                step.call(board);
            }

            // Do the actual update.
            this.onUpdate(board);

            // If this node is now in a 'SUCCEEDED' or 'FAILED' state then call the EXIT decorator for this node if it exists.
            if (this.is(State.SUCCEEDED) || this.is(State.FAILED)) {
                Exit exit = (Exit)this.decorators.getDecorator(Decorator.Type.EXIT);
                if (exit != null) {
                    exit.call(board, this.is(State.SUCCEEDED), false);
                }
            }
        } catch (GuardUnsatisfiedException ex) {
            // If the error is a GuardUnsatisfiedException then we need to determine if this node is the source.
            if (ex.isSourceNode(this)) {
                // Abort the current node.
                this.abort(board);

                // Any node that is the source of an abort will be a failed node.
                this.setState(State.FAILED);
            } else {
                // A node higher up the tree will be the source of this error.
                throw ex;
            }
        } catch (Exception ex) {
            throw ex;
        }
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

        // Reset the state of this node.
        this.reset();

        // Call the EXIT decorator function if it exists and pass an aborted flag.
        Exit exit = (Exit)this.decorators.getDecorator(Decorator.Type.EXIT);
        if (exit != null) {
            exit.call(board, false, true);
        }
    }

    /**
     * Reset the state of the node.
     */
    public void reset() {
        this.setState(State.READY);
    }

    /**
     * Validate the node.
     */
    public void validate() {}

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
