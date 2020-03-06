package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.node.Node;
import java.util.ArrayList;

/**
 * Represents a path of node guards along a root-to-leaf tree path.
 */
public class GuardPath {
    /**
     * The nodes in the guard path.
     */
    private ArrayList<Node> nodes;

    /**
     * Creates a new instance of the GuardPath class.
     * @param nodes The nodes in the guard path.
     */
    public GuardPath(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Evaluate guard conditions for all guards in the tree path, moving outwards from the root.
     * @param board The blackboard, required for guard evaluation.
     */
    public void evaluate(IBoard board) {
        // We need to evaluate guard conditions for nodes up the tree, moving outwards from the root.
        for (Node node : this.nodes) {
            // There can be multiple guards per node.
            for (Guard guard : node.getDecorators().getGuards()) {
                // Check whether the guard condition passes, and throw an exception if not.
                if (!guard.isSatisfied(board)) {
                    throw new GuardUnsatisfiedException(node);
                }
            }
        }
    }
}
