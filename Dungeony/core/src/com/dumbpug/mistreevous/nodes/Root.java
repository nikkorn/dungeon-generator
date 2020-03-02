package com.dumbpug.mistreevous.nodes;

import java.util.ArrayList;

/**
 * A root tree node.
 */
public class Root extends Composite {
    /**
     * Creates a new instance of the Root class.
     * @param children The child nodes of this composite node.
     */
    public Root(ArrayList<Node> children) {
        super(NodeType.ROOT, children);
    }
}
