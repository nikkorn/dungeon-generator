package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.util.ArrayList;

/**
 * A SELECTOR composite node.
 * The child nodes are executed in sequence until one succeeds or all fail.
 */
public class Selector extends Composite {
    /**
     * Creates a new instance of the Selector class.
     * @param decorators The node decorators.
     * @param children The child node of this composite node.
     */
    public Selector(Decorators decorators, ArrayList<Node> children) {
        super(decorators, children);
    }

    @Override
    public void onUpdate(IBoard board) {
        // Iterate over all of the children of this node.
        for (Node child : this.getChildren()) {
            // If the child has never been updated or is running then we will need to update it now.
            if (child.is(State.READY) || child.is(State.RUNNING)) {
                // Update the child of this node.
                child.update(board);
            }

            // If the current child has a state of 'SUCCEEDED' then this node is also a 'SUCCEEDED' node.
            if (child.is(State.SUCCEEDED)) {
                // This node is a 'SUCCEEDED' node.
                this.setState(State.SUCCEEDED);

                // There is no need to check the rest of the selector nodes.
                return;
            }

            // If the current child has a state of 'FAILED' then we should move on to the next child.
            if (child.is(State.FAILED)) {
                // Find out if the current child is the last one in the selector.
                // If it is then this sequence node has also failed.
                if (this.getChildren().indexOf(child) == this.getChildren().size() - 1) {
                    // This node is a 'FAILED' node.
                    this.setState(State.FAILED);

                    // There is no need to check the rest of the selector as we have completed it.
                    return;
                } else {
                    // The child node failed, try the next one.
                    continue;
                }
            }

            // The node should be in the 'RUNNING' state.
            if (child.is(State.RUNNING)) {
                // This node is a 'RUNNING' node.
                this.setState(State.RUNNING);

                // There is no need to check the rest of the sequence as the current child is still running.
                return;
            }

            // The child node was not in an expected state.
            throw new RuntimeException("A child node of a SELECTOR node was not in an expected state.");
        }
    }

    @Override
    public void validate() {
        // A sequence node must have at least a single child.
        if (this.getChildren().isEmpty()) {
            throw new RuntimeException("A SELECTOR node must have at least a single child node.");
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SELECTOR;
    }
}
