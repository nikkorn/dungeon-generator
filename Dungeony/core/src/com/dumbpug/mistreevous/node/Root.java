package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.decorator.Decorators;

import java.util.ArrayList;

/**
 * A root tree node.
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
        // TODO
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ROOT;
    }
}
