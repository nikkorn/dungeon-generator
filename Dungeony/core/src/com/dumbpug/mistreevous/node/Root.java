package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.util.ArrayList;

/**
 * A root tree node with a single child.
 */
public class Root extends Composite {
    /**
     * Creates a new instance of the Root class.
     * @param decorators The node decorators.
     * @param children The child node of this composite node.
     */
    public Root(Decorators decorators, ArrayList<Node> children) {
        super(decorators, children);
    }

    @Override
    public void onUpdate(IBoard board) {
        // Get the child node.
        Node child = this.getChildren().get(0);

        // If the child has never been updated or is running then we will need to update it now.
        if (child.is(State.READY) || child.is(State.RUNNING)) {
            // Update the child of this node.
            child.update(board);
        }

        // The state of the root node is the state of its child.
        this.setState(child.getState());
    }

    @Override
    public void validate() {
        // A root node must have a single child.
        if (this.getChildren().size() != 1) {
            throw new RuntimeException("A ROOT node must have a single child node.");
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ROOT;
    }
}
