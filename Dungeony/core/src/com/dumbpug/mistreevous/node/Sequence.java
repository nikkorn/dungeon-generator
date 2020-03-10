package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.util.ArrayList;

/**
 * A SEQUENCE composite node.
 * The child nodes are executed in sequence until one fails or all succeed.
 */
public class Sequence extends Composite {
    /**
     * Creates a new instance of the Sequence class.
     * @param decorators The node decorators.
     * @param children The child node of this composite node.
     */
    public Sequence(Decorators decorators, ArrayList<Node> children) {
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

            // If the current child has a state of 'SUCCEEDED' then we should move on to the next child.
            if (child.is(State.SUCCEEDED)) {
                // Find out if the current child is the last one in the sequence.
                // If it is then this sequence node has also succeeded.
                if (this.getChildren().indexOf(child) == this.getChildren().size() - 1) {
                    // This node is a 'SUCCEEDED' node.
                    this.setState(State.SUCCEEDED);

                    // There is no need to check the rest of the sequence as we have completed it.
                    return;
                } else {
                    // The child node succeeded, but we have not finished the sequence yet.
                    continue;
                }
            }

            // If the current child has a state of 'FAILED' then this node is also a 'FAILED' node.
            if (child.is(State.FAILED)) {
                // This node is a 'FAILED' node.
                this.setState(State.FAILED);

                // There is no need to check the rest of the sequence.
                return;
            }

            // The node should be in the 'RUNNING' state.
            if (child.is(State.RUNNING)) {
                // This node is a 'RUNNING' node.
                this.setState(State.RUNNING);

                // There is no need to check the rest of the sequence as the current child is still running.
                return;
            }

            // The child node was not in an expected state.
            throw new RuntimeException("A child node of a SEQUENCE node was not in an expected state.");
        }
    }

    @Override
    public void validate() {
        // A sequence node must have at least a single child.
        if (this.getChildren().isEmpty()) {
            throw new RuntimeException("A SEQUENCE node must have at least a single child node.");
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SEQUENCE;
    }
}
